package com.revenat.myresume.presentation.security.service;

import java.util.Optional;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.common.base.Objects;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.notification.NotificationManagerService;
import com.revenat.myresume.application.service.profile.EditProfileService;
import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.entity.ProfileRestore;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRestoreRepository;
import com.revenat.myresume.presentation.security.exception.InvalidRestoreAccessTokenException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

@Service
class SecurityServiceImpl implements SecurityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private final ProfileRepository profileRepo;
	private final EditProfileService profileService;
	private final ProfileRestoreRepository profileRestoreRepo;
	private final DataGenerator dataGenerator;
	private final PasswordEncoder passwordEncoder;
	private final NotificationManagerService notificationManagerService;
	private FilterChainProxy securityFilterChain;

	@Autowired
	public SecurityServiceImpl(ProfileRepository profileRepo, EditProfileService profileService,
			ProfileRestoreRepository profileRestoreRepo,
			DataGenerator dataGenerator, PasswordEncoder passwordEncoder,
			NotificationManagerService notificationManagerService) {
		this.profileRepo = profileRepo;
		this.profileService = profileService;
		this.profileRestoreRepo = profileRestoreRepo;
		this.dataGenerator = dataGenerator;
		this.passwordEncoder = passwordEncoder;
		this.notificationManagerService = notificationManagerService;
	}
	
	@Autowired
	public void setSpringSecurityFilterChain(Filter springSecurityFilterChain) {
		this.securityFilterChain = (FilterChainProxy) springSecurityFilterChain;
	}
	
	@Override
	@Transactional
	public void completeRegistration(AuthenticatedUser authenticatedUser) {
		Profile profile = profileRepo.findOne(authenticatedUser.getId());
		profile.setCompleted(true);
		profileRepo.save(profile);
		SecurityUtil.authenticate(profile);
	}

	@Override
	public Optional<AuthenticatedUser> findByEmail(String email) {
		Optional<Profile> optional = profileRepo.findByEmail(email);
		if (optional.isPresent()) {
			AuthenticatedUser foundUser = (AuthenticatedUser) SecurityUtil.authenticate(optional.get()).getPrincipal();
			return Optional.of(foundUser);
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public void restoreAccess(String anyUniqueId) {
		Optional<Profile> profile = profileRepo.findByUidOrEmailOrPhone(anyUniqueId, anyUniqueId, anyUniqueId);
		if (profile.isPresent()) {
			Profile p = profile.get();
			ProfileRestore restore = profileRestoreRepo.findOne(p.getId());
			if (restore == null) {
				restore = new ProfileRestore();
				restore.setId(p.getId());
			}
			restore.setToken(SecurityUtil.generateNewRestoreAccessToken());
			sendRestoreLinkNotificationIfTransactionSuccess(p, restore);
		} else {
			LOGGER.error("Profile not found by anyUniqueId: {}", anyUniqueId);
		}
	}

	@Override
	@Transactional
	public void processPasswordResetToken(String token) {
		Optional<ProfileRestore> restoreOptional = profileRestoreRepo.findByToken(token);
		if (!restoreOptional.isPresent()) {
			throw new InvalidRestoreAccessTokenException("Specified restore access token is invalid: " + token);
		}
		ProfileRestore restore = restoreOptional.get();
		Profile profile = restore.getProfile();
		SecurityUtil.authenticate(profile);
	}

	@Override
	@Transactional
	public void resetPassword(AuthenticatedUser authUser, String token, String newPassword) {
		Optional<ProfileRestore> restoreOptional = profileRestoreRepo.findByToken(token);
		if (!restoreOptional.isPresent()) {
			SecurityUtil.logout(securityFilterChain);
			throw new InvalidRestoreAccessTokenException("Specified restore access token is invalid: " + token);
		}
		ProfileRestore restore = restoreOptional.get();
		Profile profileToResetPassword = restore.getProfile();
		
		boolean attemptToResetForAnotherUser = !Objects.equal(profileToResetPassword.getId(), authUser.getId());
		if (attemptToResetForAnotherUser) {
			SecurityUtil.logout(securityFilterChain);
			LOGGER.warn("Attempt to restore password for another user: token for user='{}', but provided by user='{}'",
					profileToResetPassword.getUid(), authUser.getUsername());
			throw new InvalidRestoreAccessTokenException("Specified restore access token doesn't belong to given user: "
					+ "token for user with uid: '" + profileToResetPassword.getUid() + "' but current authenticated user: '"
					+ authUser.getUsername() + "'");
		}
		
		profileToResetPassword.setPassword(passwordEncoder.encode(newPassword));
		profileRepo.save(profileToResetPassword);
		SecurityUtil.authenticate(profileToResetPassword);
		profileRestoreRepo.delete(restore);
		
		sendPasswordChangedNotificationIfTransactionSuccess(profileToResetPassword);
	}

	@Override
	@Transactional
	public void updatePassword(AuthenticatedUser authUser, String oldPassword, String newPassword) {
		Profile profile = profileRepo.findOne(authUser.getId());
		if (!passwordEncoder.matches(oldPassword, profile.getPassword())) {
			throw new BadCredentialsException("Can not update password: provided user password is incorrect.");
		}
		profile.setPassword(passwordEncoder.encode(newPassword));
		profileRepo.save(profile);
		SecurityUtil.authenticate(profile);
		sendPasswordChangedNotificationIfTransactionSuccess(profile);
	}
	
	@Override
	@Transactional
	public void remove(AuthenticatedUser authUser) {
		profileService.removeProfile(authUser.getId());
		SecurityUtil.logout(securityFilterChain);
	}

	private void sendPasswordChangedNotificationIfTransactionSuccess(final Profile profile) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				notificationManagerService.sendPasswordChanged(profile);
			}
		});
		
	}

	private void sendRestoreLinkNotificationIfTransactionSuccess(final Profile profile, ProfileRestore restore) {
		final String restoreLink = dataGenerator.generateRestoreAccessLink(restore.getToken());
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				notificationManagerService.sendRestoreAccessLink(profile, restoreLink);
			}
		});
	}

}

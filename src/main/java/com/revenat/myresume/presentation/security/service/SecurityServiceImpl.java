package com.revenat.myresume.presentation.security.service;

import static com.revenat.myresume.infrastructure.util.TransactionUtils.executeIfTransactionSucceeded;

import java.util.Optional;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.revenat.myresume.application.config.transaction.EnableTransactionSynchronization;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.notification.NotificationManagerService;
import com.revenat.myresume.application.service.profile.RemoveProfileService;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.domain.document.ProfileRestore;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRestoreRepository;
import com.revenat.myresume.infrastructure.util.Checks;
import com.revenat.myresume.presentation.security.exception.InvalidRestoreAccessTokenException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

@Service
class SecurityServiceImpl implements SecurityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private final ProfileRepository profileRepo;
	private final RemoveProfileService profileService;
	private final ProfileRestoreRepository profileRestoreRepo;
	private final DataGenerator dataGenerator;
	private final PasswordEncoder passwordEncoder;
	private final NotificationManagerService notificationManagerService;
	private FilterChainProxy securityFilterChain;

	@Autowired
	public SecurityServiceImpl(ProfileRepository profileRepo, RemoveProfileService profileService,
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
	@EnableTransactionSynchronization
	public void restoreAccess(String anyUniqueId) {
		Checks.checkParam(anyUniqueId != null, "anyUniqueId to restore access can not be null");
		
		Optional<Profile> profile = profileRepo.findByUidOrEmailOrPhone(anyUniqueId, anyUniqueId, anyUniqueId);
		if (profile.isPresent()) {
			Profile p = profile.get();
			ProfileRestore restore = profileRestoreRepo.findOne(p.getId());
			if (restore == null) {
				restore = new ProfileRestore();
				restore.setProfile(p);
			}
			restore.setToken(SecurityUtil.generateNewRestoreAccessToken());
			profileRestoreRepo.save(restore);
			
			String restoreToken = restore.getToken();
			executeIfTransactionSucceeded( () -> sendRestoreLinkNotification(p, restoreToken));
		} else {
			LOGGER.error("Profile not found by anyUniqueId: {}", anyUniqueId);
		}
	}

	@Override
	public void verifyPasswordResetToken(String token) {
		Checks.checkParam(token != null, "token to verify can not be null");
		
		Optional<ProfileRestore> restoreOptional = profileRestoreRepo.findByToken(token);
		if (!restoreOptional.isPresent()) {
			throw new InvalidRestoreAccessTokenException("Specified restore access token is invalid: " + token);
		}
		ProfileRestore restore = restoreOptional.get();
		Profile profile = restore.getProfile();
		SecurityUtil.authenticate(profile);
	}

	@Override
	@EnableTransactionSynchronization
	public void resetPassword(AuthenticatedUser authUser, String token, String newPassword) {
		checkResetPasswordParams(authUser, token, newPassword);
		
		ProfileRestore restore = getProfileRestore(token);
		Profile profileToResetPassword = restore.getProfile();
		
		verifyResetAttemptValidity(authUser, profileToResetPassword);
		
		profileToResetPassword.setPassword(passwordEncoder.encode(newPassword));
		profileRepo.save(profileToResetPassword);
		
		SecurityUtil.authenticate(profileToResetPassword);
		profileRestoreRepo.delete(restore);
		
		executeIfTransactionSucceeded(() -> notificationManagerService.sendPasswordChanged(profileToResetPassword));
	}

	@Override
	@EnableTransactionSynchronization
	public void updatePassword(AuthenticatedUser authUser, String oldPassword, String newPassword) {
		checkUpdatePasswordParams(authUser, oldPassword, newPassword);
		
		Profile profile = profileRepo.findOne(authUser.getId());
		verifyOldPassword(oldPassword, profile);
		
		profile.setPassword(passwordEncoder.encode(newPassword));
		profileRepo.save(profile);
		
		SecurityUtil.authenticate(profile);
		executeIfTransactionSucceeded(() -> notificationManagerService.sendPasswordChanged(profile));
	}
	
	@Override
	public void remove(AuthenticatedUser authUser) {
		Checks.checkParam(authUser != null, "authUser to remove can not be null");
		
		profileService.removeProfile(authUser.getId());
		SecurityUtil.logout(securityFilterChain);
	}
	
	private void verifyOldPassword(String oldPassword, Profile profile) {
		if (!passwordEncoder.matches(oldPassword, profile.getPassword())) {
			throw new BadCredentialsException("Can not update password: provided user password is incorrect.");
		}
	}
	
	private void verifyResetAttemptValidity(AuthenticatedUser authUser, Profile profileToResetPassword) {
		boolean attemptToResetForAnotherUser = !Objects.equal(profileToResetPassword.getId(), authUser.getId());
		if (attemptToResetForAnotherUser) {
			SecurityUtil.logout(securityFilterChain);
			LOGGER.warn("Attempt to restore password for another user: token for user='{}', but provided by user='{}'",
					profileToResetPassword.getUid(), authUser.getUsername());
			throw new InvalidRestoreAccessTokenException("Specified restore access token doesn't belong to given user: "
					+ "token for user with uid: '" + profileToResetPassword.getUid() + "' but current authenticated user: '"
					+ authUser.getUsername() + "'");
		}
	}
	
	private ProfileRestore getProfileRestore(String token) {
		Optional<ProfileRestore> restoreOptional = profileRestoreRepo.findByToken(token);
		if (!restoreOptional.isPresent()) {
			SecurityUtil.logout(securityFilterChain);
			throw new InvalidRestoreAccessTokenException("Specified restore access token is invalid: " + token);
		}
		return restoreOptional.get();
	}

	private void sendRestoreLinkNotification(final Profile profile, String restoreToken) {
		final String restoreLink = dataGenerator.generateRestoreAccessLink(restoreToken);
		notificationManagerService.sendRestoreAccessLink(profile, restoreLink);
	}
	
	private static void checkResetPasswordParams(AuthenticatedUser authUser, String token, String newPassword) {
		Checks.checkParam(authUser != null, "authUser to reset password for can not be null");
		Checks.checkParam(token != null, "token to reset password with can not be null");
		Checks.checkParam(newPassword != null, "newPassword to set can not be null");
	}
	
	private static void checkUpdatePasswordParams(AuthenticatedUser authUser, String oldPassword, String newPassword) {
		Checks.checkParam(authUser != null, "authUser to update password for can not be null");
		Checks.checkParam(oldPassword != null, "oldPassword to update can not be null");
		Checks.checkParam(newPassword != null, "newPassword to set can not be null");
	}

}

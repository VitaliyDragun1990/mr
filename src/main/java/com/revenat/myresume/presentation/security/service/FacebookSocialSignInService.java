package com.revenat.myresume.presentation.security.service;

import static com.revenat.myresume.infrastructure.util.TransactionUtils.executeIfTransactionSucceeded;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revenat.myresume.application.config.transaction.EnableTransactionSynchronization;
import com.revenat.myresume.application.generator.DataGenerator;
import com.revenat.myresume.application.service.notification.NotificationManagerService;
import com.revenat.myresume.domain.document.Profile;
import com.revenat.myresume.infrastructure.exception.SocialNetworkGatewayException;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkAccount;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkGateway;
import com.revenat.myresume.infrastructure.repository.storage.ProfileRepository;
import com.revenat.myresume.infrastructure.util.Checks;
import com.revenat.myresume.infrastructure.util.CommonUtils;
import com.revenat.myresume.presentation.image.exception.ImageUploadingException;
import com.revenat.myresume.presentation.image.model.UploadedImageResult;
import com.revenat.myresume.presentation.image.service.ImageUploaderService;
import com.revenat.myresume.presentation.security.exception.SignUpException;
import com.revenat.myresume.presentation.security.exception.SocialSignInException;
import com.revenat.myresume.presentation.security.model.AuthenticatedUser;

@Service
class FacebookSocialSignInService implements SocialSignInService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookSocialSignInService.class);
	
	private final SocialNetworkGateway socialGateway;
	private final ProfileRepository profileRepo;
	private final SignUpService signUpService;
	private final ImageUploaderService imageUploadService;
	private final DataGenerator dataGenerator;
	private final NotificationManagerService notificationManagerService;

	@Autowired
	public FacebookSocialSignInService(SocialNetworkGateway socialGateway, ProfileRepository profileRepo,
			SignUpService signUpService, ImageUploaderService imageUploadService, DataGenerator dataGenerator,
			NotificationManagerService notificationManagerService) {
		this.socialGateway = socialGateway;
		this.profileRepo = profileRepo;
		this.signUpService = signUpService;
		this.imageUploadService = imageUploadService;
		this.dataGenerator = dataGenerator;
		this.notificationManagerService = notificationManagerService;
	}

	@Override
	public String getAuthorizeUrl() {
		return socialGateway.getAuthorizeUrl();
	}

	@Override
	@EnableTransactionSynchronization
	public AuthenticatedUser signIn(String verificationCode) {
		Checks.checkParam(verificationCode != null, "verificationCode to sign in via facebook can not be null");
		try {
			return signInFacebookUser(verificationCode);
		} catch (SocialNetworkGatewayException | SignUpException e) {
			throw new SocialSignInException("Error during facebook user authentication", e);
		}
	}

	private AuthenticatedUser signInFacebookUser(String verificationCode) {
		SocialNetworkAccount socialAccount = socialGateway.getSocialAccount(verificationCode);
		if (CommonUtils.isNotBlank(socialAccount.getEmail())) {
			Optional<Profile> userOptional = profileRepo.findByEmail(socialAccount.getEmail());
			if (userOptional.isPresent()) { // user data already present in the application
				AuthenticatedUser user = SecurityUtil.authenticateWithRememberMe(userOptional.get());
				LOGGER.debug("User {} signed in via Facebook", user.getUsername());
				return user;
			}
		}
		return registerNewUser(socialAccount);
	}

	private AuthenticatedUser registerNewUser(SocialNetworkAccount account) {
		Profile newProfile = new Profile();

		newProfile.setFirstName(account.getFirstName());
		newProfile.setLastName(account.getLastName());
		String generatedPassword = dataGenerator.generateRandomPassword();
		newProfile.setPassword(generatedPassword);
		newProfile.setEmail(account.getEmail());
		setPhoto(newProfile, account.getAvatarUrl());

		AuthenticatedUser authenticatedUser = signUpService.signUp(newProfile);
		newProfile.setUid(authenticatedUser.getUsername());
		
		executeIfTransactionSucceeded(() -> sendGeneratedPassword(newProfile, generatedPassword));
		return SecurityUtil.authenticateWithRememberMe(authenticatedUser);
	}

	private void setPhoto(Profile profile, String avatarUrl) {
		if (avatarUrl != null) {
			try {
				UploadedImageResult uploadedResult = imageUploadService.uploadNewProfilePhoto(new MultipartFromUrl(avatarUrl));
				profile.setLargePhoto(uploadedResult.getLargeImageLink());
				profile.setSmallPhoto(uploadedResult.getSmallImageLink());
			} catch (ImageUploadingException e) {
				LOGGER.warn("Can't extract social network avatar and set it as profile photo: " + e.getMessage(), e);
			}
		}
	}
	
	private void sendGeneratedPassword(final Profile profile, final String generatedPassword) {
		LOGGER.info("New profile has been created from facebook: {}", profile.getUid());
		notificationManagerService.sendPasswordGenerated(profile, generatedPassword);
	}
	
	static class MultipartFromUrl implements MultipartFile {
		private final String link;
		
		public MultipartFromUrl(String link) {
			this.link = link;
		}

		@Override
		public String getName() {
			return link;
		}

		@Override
		public String getOriginalFilename() {
			return link;
		}

		@Override
		public String getContentType() {
			return "image/jpeg";
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public long getSize() {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte[] getBytes() throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public InputStream getInputStream() throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void transferTo(File dest) throws IOException {
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) new URL(link).openConnection();
				try (InputStream in = conn.getInputStream()) {
					Files.copy(in, Paths.get(dest.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
				}
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
		}
	}

}

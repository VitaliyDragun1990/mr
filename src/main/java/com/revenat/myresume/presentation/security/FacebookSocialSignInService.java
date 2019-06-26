package com.revenat.myresume.presentation.security;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revenat.myresume.domain.entity.Profile;
import com.revenat.myresume.domain.exception.ApplicationException;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkAccount;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkGateway;
import com.revenat.myresume.infrastructure.repository.media.ImagePair;
import com.revenat.myresume.infrastructure.repository.media.ImageRepository;

@Service
class FacebookSocialSignInService implements SocialSignInService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookSocialSignInService.class);
	private static final String DEFAULT_PASSWORD = "password";
	
	private final SocialNetworkGateway socialGateway;
	private final SecurityService securityService;
	private final SignUpService signUpService;
	private final ImageRepository imageRepository;

	@Autowired
	public FacebookSocialSignInService(SocialNetworkGateway socialGateway, SecurityService securityService,
			SignUpService signUpService, ImageRepository imageRepository) {
		this.socialGateway = socialGateway;
		this.securityService = securityService;
		this.signUpService = signUpService;
		this.imageRepository = imageRepository;
	}

	@Override
	public String getAuthorizeUrl() {
		return socialGateway.getAuthorizeUrl();
	}

	@Override
	@Transactional
	public AuthenticatedUser signIn(String verificationCode) {
		SocialNetworkAccount socialAccount = socialGateway.getSocialAccount(verificationCode);
		Optional<AuthenticatedUser> userOptional = securityService.findByEmail(socialAccount.getEmail());
		if (userOptional.isPresent()) { // user data already present in the application
			AuthenticatedUser user = userOptional.get();
			SecurityUtil.authenticate(user);
			LOGGER.debug("User {} signed in via Facebook", user.getUsername());
			return user;
		} else { // user data is absent in the application
			return registerNewUser(socialAccount);
		}
	}

	private AuthenticatedUser registerNewUser(SocialNetworkAccount account) {
		Profile newProfile = new Profile();
		try {
			newProfile.setFirstName(account.getFirstName());
			newProfile.setLastName(account.getLastName());
			newProfile.setPassword(DEFAULT_PASSWORD);
			newProfile.setEmail(account.getEmail());
			setPhoto(newProfile, account.getAvatarUrl());
			
			return signUpService.signUp(newProfile);
		} catch (ApplicationException e) {
			imageRepository.deleteIfExists(newProfile.getLargePhoto());
			imageRepository.deleteIfExists(newProfile.getSmallPhoto());
			throw e;
		}
	}

	private void setPhoto(Profile profile, String avatarUrl) {
		if (avatarUrl != null) {
			try (InputStream in = new URL(avatarUrl).openStream()) {
				ImagePair photos = imageRepository.savePhoto(in);
				profile.setLargePhoto(photos.getLargeImagePath());
				profile.setSmallPhoto(photos.getSmallImagePath());
			} catch (IOException e) {
				throw new ApplicationException("Error while downloading photo from facebook account", e);
			}
		}
		
	}

}

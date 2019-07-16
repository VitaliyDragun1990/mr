package com.revenat.myresume.infrastructure.gateway.social.impl;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;
import com.revenat.myresume.infrastructure.exception.SocialNetworkGatewayException;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkAccount;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkGateway;
import com.revenat.myresume.infrastructure.media.converter.TranslitConverter;
import com.revenat.myresume.infrastructure.util.Checks;

public class FacebookGateway implements SocialNetworkGateway {
	private final String appId;
	private final String appSecret;
	private final String host;
	private final String redirectUrl;
	private final TranslitConverter translitConverter;

	public FacebookGateway(String appId, String appSecret, String host, String redirectUrl,
			TranslitConverter translitConverter) {
		this.appId = appId;
		this.appSecret = appSecret;
		this.host = host;
		this.redirectUrl = redirectUrl;
		this.translitConverter = translitConverter;
	}

	@Override
	public String getAuthorizeUrl() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(FacebookPermissions.EMAIL);
		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		return client.getLoginDialogUrl(appId, getRedirectUrl(), scopeBuilder);
	}

	@Override
	public SocialNetworkAccount getSocialAccount(String verificationCode) {
		Checks.checkParam(verificationCode != null, "verificationCode to get social account with can not be null");
		
		try {
			User user = getFacebookUser(verificationCode);
			String avatarUrl = getFacebookUserAvatarUrl(user.getId());
			return buildSocialNetworkAccount(user, avatarUrl);
		} catch (FacebookException e) {
			throw new SocialNetworkGatewayException("Error while retrieving data from user's facebook account", e);
		}
	}
	
	private User getFacebookUser(String verificationCode) {
		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		AccessToken accessToken = client.obtainUserAccessToken(appId, appSecret, getRedirectUrl(), verificationCode);
		client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
		return client.fetchObject("me", User.class, Parameter.with("fields", "email,first_name,last_name"));
	}
	
	private String getFacebookUserAvatarUrl(String facebookUserId) {
		return String.format("https://graph.facebook.com/v3.3/%s/picture?type=large", facebookUserId);
	}

	private SocialNetworkAccount buildSocialNetworkAccount(User user, String avatarUrl) {
		return new SocialNetworkAccount(
				translitConverter.translit(user.getFirstName()),
				translitConverter.translit(user.getLastName()),
				avatarUrl,
				user.getEmail());
	}
	
	private String getRedirectUrl() {
		return host + redirectUrl;
	}

}

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
import com.revenat.myresume.infrastructure.exception.SocialNetworkAuthenticationException;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkAccount;
import com.revenat.myresume.infrastructure.gateway.social.SocialNetworkGateway;

public class FacebookGateway implements SocialNetworkGateway {
	private final String appId;
	private final String appSecret;
	private final String host;
	private final String redirectUrl;

	public FacebookGateway(String appId, String appSecret, String host, String redirectUrl) {
		this.appId = appId;
		this.appSecret = appSecret;
		this.host = host;
		this.redirectUrl = redirectUrl;
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
		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		try {
			AccessToken accessToken = client.obtainUserAccessToken(appId, appSecret, getRedirectUrl(), verificationCode);
			client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
			User user = client.fetchObject("me", User.class, Parameter.with("fields", "email,first_name,last_name"));
			String avatarUrl = String.format("https://graph.facebook.com/v3.3/%s/picture?type=large", user.getId());
			return new SocialNetworkAccount(user.getFirstName(), user.getLastName(), avatarUrl, user.getEmail());
		} catch (FacebookException e) {
			throw new SocialNetworkAuthenticationException("Error while retrieving data from user's facebook account", e);
		}
	}
	
	private String getRedirectUrl() {
		return host + redirectUrl;
	}

}

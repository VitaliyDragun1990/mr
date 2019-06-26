package com.revenat.myresume.infrastructure.gateway.social;

import com.revenat.myresume.infrastructure.exception.SocialNetworkAuthenticationException;

/**
 * This interface represents component responsible for getting access to
 * client's account from some sort of social network.
 * 
 * @author Vitaly Dragun
 *
 */
public interface SocialNetworkGateway {
	
	/**
	 * Returns authorization url to which clients should be redirected in order to
	 * authenticate themselves as users of some social network.
	 * 
	 */
	String getAuthorizeUrl();

	/**
	 * Gets {@link SocialAccount} of authenticated social network user using
	 * provided {@code authToken} parameter.
	 * 
	 * @param verificationCode authentication token that will be used to get user's account
	 *                  in social network.
	 * @return {@link SocialAccount} instance that represents user's social network
	 *         account
	 * @throws SocialNetworkAuthenticationException if error occurs while getting information
	 *                                 about client's social network account
	 */
	SocialNetworkAccount getSocialAccount(String verificationCode);
}

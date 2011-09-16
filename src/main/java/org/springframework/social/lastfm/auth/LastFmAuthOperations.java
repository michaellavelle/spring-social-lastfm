package org.springframework.social.lastfm.auth;

import org.springframework.util.MultiValueMap;

/**
 * A service interface for the LastFm auth flow. This interface allows you to
 * conduct the "Pseudo-OAuth dance" with LastFm on behalf of a user.
 * 
 * @author Michael Lavelle
 */
public interface LastFmAuthOperations {

	/**
	 * Construct the URL to redirect the user to for authorization.
	 * 
	 * @param parameters
	 *            authorization parameters needed to build the URL
	 * @return the absolute authorize URL to redirect the user to for
	 *         authorization
	 */
	String buildAuthorizeUrl(LastFmAuthParameters parameters);

	/**
	 * Exchange the authorization code for an access grant.
	 * 
	 * @param token
	 *            the authorization code returned by the lastfm upon user
	 *            authorization
	 * @param additionalParameters
	 *            any additional parameters to be sent when exchanging the
	 *            authorization code for an access grant. Should not be encoded.
	 * @return the access grant.
	 */
	AccessGrant exchangeForAccess(String token,
			MultiValueMap<String, String> additionalParameters);

}

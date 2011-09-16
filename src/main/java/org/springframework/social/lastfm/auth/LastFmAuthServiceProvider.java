package org.springframework.social.lastfm.auth;

import org.springframework.social.ServiceProvider;
import org.springframework.social.lastfm.api.LastFm;

/**
 * @author Michael Lavelle
 */
public interface LastFmAuthServiceProvider extends ServiceProvider<LastFm> {

	/**
	 * Get the service interface for carrying out the "Pseudo-OAuth dance" with LastFm
	 * The result of the LastFm-Auth dance is an access token that can be
	 * used to obtain a {@link #getApi(String,String) API binding}.
	 */
	LastFmAuthOperations getLastFmAuthOperations();

	/**
	 * Returns an API interface allowing the client application to access
	 * protected resources on behalf of a user.
	 * 
	 * @param token
	 *            the LastFm-API access token
	 * @param sessionKey
	 *            the LastFm-API sessionKey        
	 * @return a binding to the LastFm's API
	 */
	LastFm getApi(String token, String sessionKey);

}
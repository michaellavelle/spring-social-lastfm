package org.springframework.social.lastfm.connect;

import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.impl.LastFmTemplate;
import org.springframework.social.lastfm.auth.LastFmAuthOperations;
import org.springframework.social.lastfm.auth.LastFmAuthServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author Michael Lavelle
 */
public class LastFmServiceProvider implements LastFmAuthServiceProvider {

	private String clientId;
	private String secret;
	private String userAgent;

	private final LastFmAuthOperations lastFmAuthOperations;

	/**
	 * Create a new {@link OAuth2ServiceProvider}.
	 * 
	 * @param oauth2Operations
	 *            the OAuth2Operations template for conducting the OAuth 2 flow
	 *            with the provider.
	 */
	public LastFmServiceProvider(LastFmAuthOperations lastFmAuthOperations) {
		this.lastFmAuthOperations = lastFmAuthOperations;
	}

	public LastFmServiceProvider(String clientId, String clientSecret,String userAgent) {
		lastFmAuthOperations = new LastFmAuthTemplate(clientId, clientSecret,userAgent);
		this.clientId = clientId;
		this.secret = clientSecret;
		this.userAgent = userAgent;
	}

	// implementing OAuth2ServiceProvider

	public final LastFmAuthOperations getLastFmAuthOperations() {
		return lastFmAuthOperations;
	}

	public LastFm getApi(String token, String sessionKey) {
		return new LastFmTemplate(userAgent,token, sessionKey, clientId, secret);

	}

}
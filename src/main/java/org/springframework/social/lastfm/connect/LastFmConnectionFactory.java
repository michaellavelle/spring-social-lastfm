package org.springframework.social.lastfm.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.auth.AccessGrant;
import org.springframework.social.lastfm.auth.LastFmAuthOperations;
import org.springframework.social.lastfm.auth.LastFmAuthServiceProvider;
import org.springframework.social.lastfm.connect.support.LastFmAuthConnection;

/**
 * Factory for creating LastFMAuth-based {@link Connection}s
 * 
 * @author Michael Lavelle
 */
public class LastFmConnectionFactory extends ConnectionFactory<LastFm> {

	/**
	 * Create a {@link LastFmConnectionFactory}.
	 * 

	 * @param clientId
	 *            the LastFm APi clientId
	 * @param clientSecret the LastFm Api clientSecret
	 */
	public LastFmConnectionFactory(String clientId, String clientSecret,String userAgent) {
		super("lastfm", new LastFmServiceProvider(clientId, clientSecret,userAgent),
				new LastFmAdapter());
	}

	/**
	 * Get LastFm's {@link LastFmAuthOperations} that allows the client
	 * application to conduct the Pseudo-OAuth flow with LastFm
	 */
	public LastFmAuthOperations getLastFmAuthOperations() {
		return getLastFmAuthServiceProvider().getLastFmAuthOperations();
	}

	/**
	 * Create a LastFm-Auth based {@link Connection} from the {@link AccessGrant}
	 * returned after {@link #getLastFmAuthOperations() completing the Auth flow}.
	 * 
	 * @param accessGrant
	 *            the LastFm access grant
	 * @return the new LastFm connection
	 * @see LastFmAuthOperations#exchangeForAccess(String, String,
	 *      org.springframework.util.MultiValueMap)
	 */
	public Connection<LastFm> createConnection(AccessGrant accessGrant) {
		return new LastFmAuthConnection(getProviderId(),
				extractProviderUserId(accessGrant), accessGrant.getToken(),
				accessGrant.getSessionKey(), getLastFmAuthServiceProvider(),
				getApiAdapter());
	}

	/**
	 * Create a LastFm-Auth-based {@link Connection} from the connection data.
	 */
	public Connection<LastFm> createConnection(ConnectionData data) {
		return new LastFmAuthConnection(new LastFmConnectionData(data),
				getLastFmAuthServiceProvider(), getApiAdapter());
	}

	// subclassing hooks

	/**
	 * Hook for extracting the providerUserId from the returned
	 * {@link AccessGrant}, if it is available. Default implementation returns
	 * null, indicating it is not exposed and another remote API call will be
	 * required to obtain it. Subclasses may override.
	 */
	protected String extractProviderUserId(AccessGrant accessGrant) {
		return null;
	}

	// internal helpers

	private LastFmAuthServiceProvider getLastFmAuthServiceProvider() {
		return (LastFmAuthServiceProvider) getServiceProvider();
	}

}
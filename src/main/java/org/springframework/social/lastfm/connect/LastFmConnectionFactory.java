/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.lastfm.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
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
	 * 
	 * @param clientId
	 *            the LastFm APi clientId
	 * @param clientSecret
	 *            the LastFm Api clientSecret
	 */
	public LastFmConnectionFactory(String clientId, String clientSecret) {
		super("lastfm", new LastFmServiceProvider(clientId, clientSecret,
				"spring-social-lastfm/1.0.0-SNAPSHOT"), new LastFmAdapter());
	}

	/**
	 * Get LastFm's {@link LastFmAuthOperations} that allows the client
	 * application to conduct the Pseudo-OAuth flow with LastFm
	 */
	public LastFmAuthOperations getLastFmAuthOperations() {
		return getLastFmAuthServiceProvider().getLastFmAuthOperations();
	}

	/**
	 * Create a LastFm-Auth based {@link Connection} from the
	 * {@link LastFmAccessGrant} returned after
	 * {@link #getLastFmAuthOperations() completing the Auth flow}.
	 * 
	 * @param accessGrant
	 *            the LastFm access grant
	 * @return the new LastFm connection
	 * @see LastFmAuthOperations#exchangeForAccess(String, String,
	 *      org.springframework.util.MultiValueMap)
	 */
	public Connection<LastFm> createConnection(LastFmAccessGrant accessGrant) {
		return new LastFmAuthConnection(getProviderId(),
				extractProviderUserId(accessGrant), accessGrant,
				getLastFmAuthServiceProvider(), getApiAdapter());
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
	 * {@link LastFmAccessGrant}, if it is available. Default implementation
	 * returns null, indicating it is not exposed and another remote API call
	 * will be required to obtain it. Subclasses may override.
	 */
	protected String extractProviderUserId(LastFmAccessGrant accessGrant) {
		return null;
	}

	// internal helpers

	private LastFmAuthServiceProvider getLastFmAuthServiceProvider() {
		return (LastFmAuthServiceProvider) getServiceProvider();
	}

}
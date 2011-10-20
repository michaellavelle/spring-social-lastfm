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
package org.springframework.social.lastfm.pseudooauth2.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.social.lastfm.connect.LastFmAdapter;
import org.springframework.social.lastfm.connect.LastFmConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

/**
 * Adapts a non-oauth LastFmConnectionFactory to a OAuth2ConnectionFactory
 * interface, to enable use with ProviderSignInController and ConnectController
 * 
 * @author Michael Lavelle
 */
public class LastFmPseudoOAuth2ConnectionFactory extends
		OAuth2ConnectionFactory<LastFm> {

	private LastFmConnectionFactory lastFmConnectionFactory;

	public LastFmPseudoOAuth2ConnectionFactory(String clientId,
			String clientSecret) {
		super("lastfm", new LastFmPseudoOAuth2ServiceProvider(clientId,
				clientSecret, "spring-social-lastfm/1.0.0-SNAPSHOT"),
				new LastFmAdapter());
		this.lastFmConnectionFactory = new LastFmConnectionFactory(clientId,
				clientSecret);
	}

	@Override
	public Connection<LastFm> createConnection(AccessGrant accessGrant) {

		LastFmAccessGrant lastFmAccessGrant = LastFmPseudoOAuth2AccessGrant
				.fromAccessToken(accessGrant.getAccessToken());
		return lastFmConnectionFactory.createConnection(lastFmAccessGrant);

	}

	@Override
	public Connection<LastFm> createConnection(ConnectionData data) {
		return lastFmConnectionFactory.createConnection(data);
	}

}

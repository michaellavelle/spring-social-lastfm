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

import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.impl.LastFmTemplate;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
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

	public LastFmServiceProvider(String clientId, String clientSecret,
			String userAgent) {
		lastFmAuthOperations = new LastFmAuthTemplate(clientId, clientSecret,
				userAgent);
		this.clientId = clientId;
		this.secret = clientSecret;
		this.userAgent = userAgent;
	}

	// implementing OAuth2ServiceProvider

	public final LastFmAuthOperations getLastFmAuthOperations() {
		return lastFmAuthOperations;
	}

	public LastFm getApi(LastFmAccessGrant lastFmAccessGrant) {
		return new LastFmTemplate(userAgent, lastFmAccessGrant, clientId,
				secret);

	}

}
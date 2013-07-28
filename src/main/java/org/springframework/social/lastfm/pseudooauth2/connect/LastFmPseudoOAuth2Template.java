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

import org.springframework.social.lastfm.auth.LastFmAuthParameters;
import org.springframework.social.lastfm.connect.LastFmAuthTemplate;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Michael Lavelle
 */
public class LastFmPseudoOAuth2Template implements OAuth2Operations {

	private LastFmAuthTemplate lastFmAuthTemplate;
	private String apiKey;

	public LastFmPseudoOAuth2Template(String apiKey,
			LastFmAuthTemplate lastFmAuthTemplate) {
		this.lastFmAuthTemplate = lastFmAuthTemplate;
		this.apiKey = apiKey;
	}

	private LastFmAuthParameters createLastFmAuthParameters(
			OAuth2Parameters parameters) {
		LastFmAuthParameters lastFmAuthParameters = new LastFmAuthParameters(
				parameters);
		lastFmAuthParameters.add("cb", parameters.getFirst("redirect_uri"));
		lastFmAuthParameters.add("api_key", apiKey);
		return lastFmAuthParameters;
	}

	@Override
	public String buildAuthenticateUrl(GrantType grantType,
			OAuth2Parameters parameters) {
		return lastFmAuthTemplate
				.buildAuthorizeUrl(createLastFmAuthParameters(parameters));
	}

	@Override
	public String buildAuthorizeUrl(GrantType grantType,
			OAuth2Parameters parameters) {
		return lastFmAuthTemplate
				.buildAuthorizeUrl(createLastFmAuthParameters(parameters));
	}

	@Override
	public AccessGrant exchangeForAccess(String authorizationCode,
			String redirectUrl, MultiValueMap<String, String> parameters) {
		if (parameters == null) {
			parameters = new LinkedMultiValueMap<String, String>();
		}
		parameters.add("api_key", apiKey);

		org.springframework.social.lastfm.auth.LastFmAccessGrant lastFmAccessGrant = lastFmAuthTemplate
				.exchangeForAccess(authorizationCode, parameters);
		LastFmPseudoOAuth2AccessGrant lastFmAccessToken = new LastFmPseudoOAuth2AccessGrant(
				lastFmAccessGrant.getToken(), lastFmAccessGrant.getSessionKey());
		AccessGrant accessGrant = new AccessGrant(
				lastFmAccessToken.toAccessToken(), null, null, null);
		return accessGrant;
	}

	@Override
	public AccessGrant refreshAccess(String refreshToken, String scope,
			MultiValueMap<String, String> parameters) {
		throw new UnsupportedOperationException("Last.Fm does not support refresh tokens");
	}

	@Override
	public AccessGrant exchangeCredentialsForAccess(String username,
			String password, MultiValueMap<String, String> additionalParameters) {
		throw new UnsupportedOperationException("Last.Fm does credentials API access");

	}

	@Override
	public AccessGrant refreshAccess(String refreshToken,
			MultiValueMap<String, String> additionalParameters) {
		throw new UnsupportedOperationException("Last.Fm does not support refresh tokens");

	}

	@Override
	public AccessGrant authenticateClient() {
		throw new UnsupportedOperationException("Last.Fm's client authentication is unavailable globally but is per method");
	}

	@Override
	public AccessGrant authenticateClient(String scope) {
		throw new UnsupportedOperationException("Last.Fm's client authentication is unavailable globally but is per method");
	}

}

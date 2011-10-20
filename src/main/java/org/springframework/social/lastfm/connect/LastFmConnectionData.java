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

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;

/**
 * @author Michael Lavelle
 */
public class LastFmConnectionData extends ConnectionData {

	private LastFmAccessGrant lastFmAccessGrant;

	public LastFmAccessGrant getLastFmAccessGrant() {
		return lastFmAccessGrant;
	}

	private static final long serialVersionUID = 1L;

	public LastFmConnectionData(String providerId, String providerUserId,
			String displayName, String profileUrl, String imageUrl,
			LastFmAccessGrant lastFmAccessGrant) {
		super(providerId, providerUserId, displayName, profileUrl, imageUrl,
				lastFmAccessGrant.getToken(), null, lastFmAccessGrant
						.getSessionKey(), null);
		this.lastFmAccessGrant = lastFmAccessGrant;
	}

	public LastFmConnectionData(ConnectionData connectionData) {

		this(connectionData.getProviderId(),
				connectionData.getProviderUserId(), connectionData
						.getDisplayName(), connectionData.getProfileUrl(),
				connectionData.getImageUrl(), new LastFmAccessGrant(
						connectionData.getAccessToken(),
						connectionData.getRefreshToken()));

	}

}

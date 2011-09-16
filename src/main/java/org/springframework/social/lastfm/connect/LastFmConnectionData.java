package org.springframework.social.lastfm.connect;

import org.springframework.social.connect.ConnectionData;

public class LastFmConnectionData extends ConnectionData {

	public String getToken() {
		return getAccessToken();
	}

	public String getSessionKey() {
		return getRefreshToken();
	}

	private static final long serialVersionUID = 1L;

	public LastFmConnectionData(String providerId, String providerUserId,
			String displayName, String profileUrl, String imageUrl,
			String token, String sessionKey) {
		super(providerId, providerUserId, displayName, profileUrl, imageUrl,
				token, null, sessionKey, null);
	}

	public LastFmConnectionData(ConnectionData connectionData) {

		this(connectionData.getProviderId(),
				connectionData.getProviderUserId(), connectionData
						.getDisplayName(), connectionData.getProfileUrl(),
				connectionData.getImageUrl(), connectionData.getAccessToken(),
				connectionData.getRefreshToken());

	}

}

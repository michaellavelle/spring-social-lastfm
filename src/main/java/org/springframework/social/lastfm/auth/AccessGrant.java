package org.springframework.social.lastfm.auth;

import java.io.Serializable;

/**
 * LastFm auth access token.
 * 
 * @author Michael Lavelle
 */
@SuppressWarnings("serial")
public class AccessGrant implements Serializable {

	private final String token;

	private final String sessionKey;

	public AccessGrant(String token, String sessionKey) {
		this.token = token;
		this.sessionKey = sessionKey;
	}

	public String getToken() {
		return token;
	}

	public String getSessionKey() {
		return sessionKey;
	}

}

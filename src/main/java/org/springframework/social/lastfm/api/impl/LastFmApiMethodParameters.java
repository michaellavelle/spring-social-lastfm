package org.springframework.social.lastfm.api.impl;

import org.springframework.social.lastfm.connect.LastFmSignature;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author Michael Lavelle
 */
public class LastFmApiMethodParameters extends
		LinkedMultiValueMap<String, String> {

	
	private static final long serialVersionUID = 1L;

	public LastFmApiMethodParameters(String methodName, String apiKey,
			String token, String secret) {
		LastFmSignature lastFmSignature = new LastFmSignature(apiKey,
				methodName, token, secret);
		String apiSignature = lastFmSignature.toString();
		add("api_sig", apiSignature);
		add("api_key", apiKey);
		add("method", methodName);
		add("token", token);
		add("format", "json");
	}

}

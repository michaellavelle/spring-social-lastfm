package org.springframework.social.lastfm.api.impl;

import java.util.List;

import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.UserOperations;
import org.springframework.social.lastfm.api.impl.json.LastFmLovedTracksResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmProfileResponse;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class UserTemplate extends AbstractLastFmOperations implements
		UserOperations {

	private String apiKey;
	private String token;
	private String secret;
	private String sessionKey;

	public UserTemplate(RestTemplate restTemplate, String token,
			String sessionKey, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		super(restTemplate, isAuthorizedForUser);
		this.token = token;
		this.apiKey = apiKey;
		this.secret = secret;
		this.sessionKey = sessionKey;
	}
	
	protected String buildLastFmApiUrl(LastFmApiMethodParameters methodParameters)
	{
		return buildLastFmApiUrl(methodParameters,null);
	}
	
	protected String buildLastFmApiUrl(LastFmApiMethodParameters methodParameters,String userName)
	{
		String apiKey = methodParameters.getFirst("api_key");
		String method = methodParameters.getFirst("method");
		String apiSignature = methodParameters.getFirst("api_sig");
		methodParameters.add("sk", sessionKey);

		return "http://ws.audioscrobbler.com/2.0/?method=" + method + (userName != null ? ("&user="
				+ userName) : "" ) + "&api_key=" + apiKey + "&api_sig="
				+ apiSignature + "&sk=" + sessionKey + "&format=json";
	}

	@Override
	public LastFmProfile getUserProfile() {
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getInfo", apiKey, token, secret);

	

		return restTemplate.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmProfileResponse.class).getLastFmProfile();

	}

	@Override
	public LastFmProfile getUserProfile(String userName) {
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getInfo", apiKey, token, secret);
		methodParameters.add("sk", sessionKey);

		return restTemplate.getForObject(buildLastFmApiUrl(methodParameters,userName),
				LastFmProfileResponse.class).getLastFmProfile();

	}

	@Override
	public List<Track> getLovedTracks(String userName) {
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getlovedtracks", apiKey, token, secret);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters,userName),
						LastFmLovedTracksResponse.class).getTracksResponse()
				.getTracks();

	}

}

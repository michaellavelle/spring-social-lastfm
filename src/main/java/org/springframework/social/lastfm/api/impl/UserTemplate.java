package org.springframework.social.lastfm.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackDescriptor;
import org.springframework.social.lastfm.api.UserOperations;
import org.springframework.social.lastfm.api.impl.json.LastFmLovedTracksResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmProfileResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmRecentTracksResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTopTracksResponse;
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
	
	private String baseApiUrl = "http://ws.audioscrobbler.com/2.0/";

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
		String delim = "?";
		String url = baseApiUrl;
		for (Map.Entry<String, List<String>> entry : methodParameters.entrySet())
		{
			for (String value : entry.getValue())
			{
				url = url + delim + entry.getKey() + "=" + value;
				delim = "&";
			}
		}
		return url;
	}

	@Override
	public LastFmProfile getUserProfile() {
		
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getInfo", apiKey, token, secret,sessionKey);

		return restTemplate.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmProfileResponse.class).getLastFmProfile();

	}

	@Override
	public LastFmProfile getUserProfile(String userName) {
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("user", userName);
		
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getInfo", apiKey, token, secret,additionalParams);

		return restTemplate.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmProfileResponse.class).getLastFmProfile();

	}

	@Override
	public List<SimpleTrack> getRecentTracks(String userName) {
		
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("user", userName);
		
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getrecenttracks", apiKey, token, secret,additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmRecentTracksResponse.class).getTracksResponse()
				.getTracks();

	}
	
	@Override
	public List<Track> getLovedTracks(String userName) {
		
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("user", userName);

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getlovedtracks", apiKey, token, secret,additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmLovedTracksResponse.class).getTracksResponse()
				.getTracks();

	}
	

	@Override
	public List<Track> getTopTracks(String userName) {
		
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("user", userName);

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.gettoptracks", apiKey, token, secret,additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmTopTracksResponse.class).getTracksResponse()
				.getTracks();

	}
	

	

	@Override
	public void scrobble(TrackDescriptor trackDescriptor,Date timestamp) {
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("track", trackDescriptor.getName());
		additionalParams.put("artist",trackDescriptor.getArtistName());
		Integer timestampInSeconds =  new Integer((int)(timestamp.getTime()/1000));
		additionalParams.put("timestamp",timestampInSeconds.toString());

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.scrobble", apiKey, token, secret,sessionKey,additionalParams);

		restTemplate.postForObject(baseApiUrl,
				methodParameters,
				String.class);
	}

	@Override
	public void updateNowPlaying(TrackDescriptor trackDescriptor) {
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("track", trackDescriptor.getName());
		additionalParams.put("artist",trackDescriptor.getArtistName());
	
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.updateNowPlaying", apiKey, token, secret,sessionKey,additionalParams);

		restTemplate.postForObject(baseApiUrl,
				methodParameters,
				String.class);
		
	}

}

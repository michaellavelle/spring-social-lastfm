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

	
	
	

	public UserTemplate(RestTemplate restTemplate, String token,
			String sessionKey, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		super(restTemplate, token, sessionKey, apiKey, secret, isAuthorizedForUser);
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
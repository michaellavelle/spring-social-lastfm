package org.springframework.social.lastfm.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.SimpleTrackDescriptor;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackOperations;
import org.springframework.social.lastfm.api.TrackSearchResult;
import org.springframework.social.lastfm.api.impl.json.LastFmTopTracksResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTrackMatchesResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTrackSearchResponse;
import org.springframework.web.client.RestTemplate;

public class TrackTemplate extends AbstractLastFmOperations implements
		TrackOperations {

	

	public TrackTemplate(RestTemplate restTemplate, String token,
			String sessionKey, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		super(restTemplate, token, sessionKey, apiKey, secret, isAuthorizedForUser);
	}

	@Override
	public List<TrackSearchResult> searchByTrackName(String trackName) {
		return searchByArtistAndTrackName(null,trackName);
	}

	@Override
	public List<TrackSearchResult> searchByArtistAndTrackName(String artistName,
			String trackName) {
		Map<String,String> additionalParams = new HashMap<String,String>();
		additionalParams.put("track", trackName);
		if (artistName != null)
		{
			additionalParams.put("artist", artistName);
		}

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.search", apiKey, secret,additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmTrackSearchResponse.class).getTrackMatchesResponse().getTracksResponse()
				.getTracks();
	}

}

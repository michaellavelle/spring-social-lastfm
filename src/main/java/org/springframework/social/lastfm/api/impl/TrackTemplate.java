package org.springframework.social.lastfm.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.SimpleTrackDescriptor;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackDescriptor;
import org.springframework.social.lastfm.api.TrackOperations;
import org.springframework.social.lastfm.api.TrackSearchResult;
import org.springframework.social.lastfm.api.impl.json.LastFmSimilarTracksResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmSimpleTrackDescriptorsResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTrackSearchResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTracksResponse;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.web.client.RestTemplate;

public class TrackTemplate extends AbstractLastFmOperations implements
		TrackOperations {

	public TrackTemplate(RestTemplate restTemplate,
			LastFmAccessGrant lastFmAccessGrant, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		super(restTemplate, lastFmAccessGrant, apiKey, secret,
				isAuthorizedForUser);
	}

	@Override
	public List<TrackSearchResult> searchByTrackName(String trackName) {
		return searchByArtistAndTrackName(null, trackName);
	}

	@Override
	public List<TrackSearchResult> searchByArtistAndTrackName(
			String artistName, String trackName) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackName);
		if (artistName != null) {
			additionalParams.put("artist", artistName);
		}

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.search", apiKey, secret, additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmTrackSearchResponse.class)
				.getNestedResponse().getTracksResponse().getTracks();
	}

	@Override
	public List<Track> getSimilarTracks(TrackDescriptor trackDescriptor) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		if (trackDescriptor.getName() != null)
		{
			additionalParams.put("track", trackDescriptor.getName());
		}
		if (trackDescriptor.getArtistName() != null)
		{
			additionalParams.put("artist", trackDescriptor.getArtistName());
		}

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.similar", apiKey, secret, additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmSimilarTracksResponse.class)
				.getNestedResponse().getNestedResponse().getTracks();
	}

}

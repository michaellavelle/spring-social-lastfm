package org.springframework.social.lastfm.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackDescriptor;
import org.springframework.social.lastfm.api.TrackOperations;
import org.springframework.social.lastfm.api.TrackSearchResult;
import org.springframework.social.lastfm.api.impl.json.LastFmTrackMatchesResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTrackSearchResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTracksResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmTrackListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmTrackSearchResultListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.PageInfo;
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
	public Page<TrackSearchResult> searchByTrackName(String trackName) {
		return searchByArtistAndTrackName(null, trackName,null);
	}
	
	@Override
	public Page<TrackSearchResult> searchByTrackName(String trackName,Pageable pageable) {
		return searchByArtistAndTrackName(null, trackName,pageable);
	}
	
	@Override
	public Page<TrackSearchResult> searchByArtistAndTrackName(
			String artistName, String trackName) {
		return searchByArtistAndTrackName(artistName,trackName,null);
	}

	@Override
	public Page<TrackSearchResult> searchByArtistAndTrackName(
			String artistName, String trackName,Pageable pageable) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackName);
		if (artistName != null) {
			additionalParams.put("artist", artistName);
		}
		
		setPageableParamsIfSpecified(additionalParams,pageable);

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.search", apiKey, secret, additionalParams);

		LastFmTrackMatchesResponse trackMatchesResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmTrackSearchResponse.class)
		.getNestedResponse();
		
		LastFmTrackSearchResultListResponse trackSearchListResponse = trackMatchesResponse.getTracksResponse();
		
		PageInfo pageInfo = trackMatchesResponse.getPageInfo();
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<TrackSearchResult>(new ArrayList<TrackSearchResult>(),pageable,pageInfo.getTotal());
		}

		return new PageImpl<TrackSearchResult>(trackSearchListResponse.getTracks(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),pageInfo.getTotal());

		
		
	}

	@Override
	public Page<Track> getSimilarTracks(TrackDescriptor trackDescriptor) {
		return getSimilarTracksWithLimit(trackDescriptor,null);
	}
	
	@Override
	public Page<Track> getSimilarTracks(TrackDescriptor trackDescriptor,int limit) {
		return getSimilarTracksWithLimit(trackDescriptor,limit);
	}
	
	private Page<Track> getSimilarTracksWithLimit(TrackDescriptor trackDescriptor,Integer limit) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		if (trackDescriptor.getName() != null)
		{
			additionalParams.put("track", trackDescriptor.getName());
		}
		if (trackDescriptor.getArtistName() != null)
		{
			additionalParams.put("artist", trackDescriptor.getArtistName());
		}
		if (limit != null)
		{
			additionalParams.put("limit", limit.toString());
		}

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.similar", apiKey, secret, additionalParams);

		LastFmTrackListResponse trackListResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmTracksResponse.class)
		.getNestedResponse();
				

		return new PageImpl<Track>(trackListResponse.getTracks(),new PageRequest(0,limit == null ? trackListResponse.getTracks().size() : limit),trackListResponse.getTracks().size());
	}

}

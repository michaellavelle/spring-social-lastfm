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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.lastfm.api.Artist;
import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.Playlist;
import org.springframework.social.lastfm.api.Shout;
import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackDescriptor;
import org.springframework.social.lastfm.api.UserOperations;
import org.springframework.social.lastfm.api.impl.json.LastFmArtistsResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmPlaylistsResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmUsersResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmProfileResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmSimpleTracksResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmShoutsResponse;
import org.springframework.social.lastfm.api.impl.json.LastFmTracksResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmArtistListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmShoutListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmSimpleTrackListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmTrackListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmUserListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.PageInfo;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class UserTemplate extends AbstractLastFmOperations implements
		UserOperations {

	public UserTemplate(RestTemplate restTemplate,
			LastFmAccessGrant lastFmAccessGrant, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		super(restTemplate, lastFmAccessGrant, apiKey, secret,
				isAuthorizedForUser);
	}

	@Override
	public LastFmProfile getUserProfile() {

		requireAuthorization();
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getInfo", apiKey, lastFmAccessGrant.getToken(), secret,
				lastFmAccessGrant.getSessionKey());

		return restTemplate.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmProfileResponse.class).getNestedResponse();

	}

	@Override
	public LastFmProfile getUserProfile(String userName) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getInfo", apiKey, null, null, additionalParams);

		return restTemplate.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmProfileResponse.class).getNestedResponse();

	}
	
	@Override
	public Page<SimpleTrack> getRecentTracks(String userName) 
	{
		return getRecentTracks(userName,null);	
	}

	@Override
	public Page<SimpleTrack> getRecentTracks(String userName,Pageable pageable) {

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);


		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getrecenttracks", apiKey, null, null, additionalParams);
		
		LastFmSimpleTrackListResponse simpleTrackListResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmSimpleTracksResponse.class).getNestedResponse();
		
		PageInfo pageInfo = simpleTrackListResponse.getPageInfo();
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<SimpleTrack>(new ArrayList<SimpleTrack>(),pageable,pageInfo.getTotal());
		}

		return new PageImpl<SimpleTrack>(simpleTrackListResponse.getTracks(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),simpleTrackListResponse.getPageInfo().getTotal());


	}

	@Override
	public Page<Track> getLovedTracks(String userName) {

		return getLovedTracks(userName,null);
	}
	
	
	
	@Override
	public Page<Track> getLovedTracks(String userName,Pageable pageable) {

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getlovedtracks", apiKey, null, null, additionalParams);

		LastFmTrackListResponse trackListResponse = restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmTracksResponse.class).getNestedResponse();
		
		PageInfo pageInfo = trackListResponse.getPageInfo();
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<Track>(new ArrayList<Track>(),pageable,pageInfo.getTotal());
		}

		return new PageImpl<Track>(trackListResponse.getTracks(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),trackListResponse.getPageInfo().getTotal());
		
	}
	
	@Override
	public Page<Track> getTopTracks(String userName) {
		return getTopTracks(userName,null);
	}

	@Override
	public Page<Track> getTopTracks(String userName,Pageable pageable) {

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);


		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.gettoptracks", apiKey, null, null, additionalParams);

		LastFmTrackListResponse trackListResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmTracksResponse.class).getNestedResponse();
		
		PageInfo pageInfo = trackListResponse.getPageInfo();
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<Track>(new ArrayList<Track>(),pageable,pageInfo.getTotal());
		}
		
		return new PageImpl<Track>(trackListResponse.getTracks(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),trackListResponse.getPageInfo().getTotal());

	}
	
	@Override
	public Page<Artist> getTopArtists(String userName) {
		return getTopArtists(userName,null);
	}
	
	@Override
	public Page<Artist> getTopArtists(String userName,Pageable pageable) {

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);


		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.gettopartists", apiKey, null, null, additionalParams);

		LastFmArtistListResponse artistListResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmArtistsResponse.class).getNestedResponse();
		
		PageInfo pageInfo = artistListResponse.getPageInfo();

		
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<Artist>(new ArrayList<Artist>(),pageable,pageInfo.getTotal());
		}
		
		return new PageImpl<Artist>(artistListResponse.getArtists(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),pageInfo.getTotal());

	}

	@Override
	public void scrobble(TrackDescriptor trackDescriptor, Date timestamp) {
		requireAuthorization();

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackDescriptor.getName());
		additionalParams.put("artist", trackDescriptor.getArtistName());
		Integer timestampInSeconds = new Integer(
				(int) (timestamp.getTime() / 1000));
		additionalParams.put("timestamp", timestampInSeconds.toString());

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.scrobble", apiKey, lastFmAccessGrant.getToken(), secret,
				lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);
	}

	@Override
	public void updateNowPlaying(TrackDescriptor trackDescriptor) {
		requireAuthorization();

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackDescriptor.getName());
		additionalParams.put("artist", trackDescriptor.getArtistName());

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.updateNowPlaying", apiKey, lastFmAccessGrant.getToken(),
				secret, lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);

	}

	@Override
	public void shout(String userName, String message) {
		requireAuthorization();
		
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		additionalParams.put("message", message);
		
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.shout", apiKey, lastFmAccessGrant.getToken(),
				secret, lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);


	}


	@Override
	public Page<Shout> getShouts(String userName) {
		return getShouts(userName,null);
	}
	@Override
	public Page<Shout> getShouts(String userName,Pageable pageable) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);


		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getshouts", apiKey, null, null, additionalParams);

		LastFmShoutListResponse shoutListResponse = restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmShoutsResponse.class).getNestedResponse();
		

		PageInfo pageInfo = shoutListResponse.getPageInfo();

		
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<Shout>(new ArrayList<Shout>(),pageable,pageInfo.getTotal());
		}
		
		return new PageImpl<Shout>(shoutListResponse.getShouts(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),pageInfo.getTotal());
	}

	@Override
	public Page<Artist> getRecommendedArtists() {
		return getRecommendedArtists(null);
	}
	
	@Override
	public Page<Artist> getRecommendedArtists(Pageable pageable) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		setPageableParamsIfSpecified(additionalParams,pageable);
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getrecommendedartists", apiKey,  lastFmAccessGrant.getToken(), secret, lastFmAccessGrant.getSessionKey(),additionalParams);

		
		LastFmArtistListResponse artistListResponse = restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmArtistsResponse.class).getNestedResponse();
		

		PageInfo pageInfo = artistListResponse.getPageInfo();

		
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<Artist>(new ArrayList<Artist>(),pageable,pageInfo.getTotal());
		}
		
		return new PageImpl<Artist>(artistListResponse.getArtists(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),pageInfo.getTotal());
	}
	

	@Override
	public Page<LastFmProfile> getFriends(String userName) {
		return getFriends(userName,null);
	}
	
	
	@Override
	public Page<LastFmProfile> getFriends(String userName,Pageable pageable) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);


		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getfriends", apiKey, null, null, additionalParams);

		LastFmUserListResponse userListResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmUsersResponse.class).getNestedResponse();
		
		
		PageInfo pageInfo = userListResponse.getPageInfo();

		
		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<LastFmProfile>(new ArrayList<LastFmProfile>(),pageable,pageInfo.getTotal());
		}
		
		return new PageImpl<LastFmProfile>(userListResponse.getUsers(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),pageInfo.getTotal());

	}

	@Override
	public List<LastFmProfile> getNeighbours(String userName) {
		return getNeighboursWithLimit(userName,null);
	}
	
	@Override
	public List<LastFmProfile> getNeighbours(String userName,int limit) {
		return getNeighboursWithLimit(userName,limit);
	}
	
	
	public List<Playlist> getPlaylists(String userName) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getplaylists", apiKey, null, null, additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmPlaylistsResponse.class).getNestedResponse().getPlaylists();
	}
	
	private List<LastFmProfile> getNeighboursWithLimit(String userName,Integer limit) {
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		if (limit != null)
		{
			additionalParams.put("limit", limit.toString());
		}

		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"user.getneighbours", apiKey, null, null, additionalParams);

		return restTemplate
				.getForObject(buildLastFmApiUrl(methodParameters),
						LastFmUsersResponse.class).getNestedResponse().getUsers();
	}

	@Override
	public void love(String artistName, String trackName) {

		requireAuthorization();

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackName);
		additionalParams.put("artist", artistName);
	
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.love", apiKey, lastFmAccessGrant.getToken(), secret,
				lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);
		
	}
	
	
	@Override
	public void unlove(String artistName, String trackName) {

		requireAuthorization();

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackName);
		additionalParams.put("artist", artistName);
	
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"track.unlove", apiKey, lastFmAccessGrant.getToken(), secret,
				lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);
		
	}

}

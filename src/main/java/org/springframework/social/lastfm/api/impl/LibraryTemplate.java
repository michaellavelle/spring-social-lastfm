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
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.lastfm.api.AlbumTrack;
import org.springframework.social.lastfm.api.LibraryOperations;
import org.springframework.social.lastfm.api.impl.json.LastFmAlbumTracksResponse;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmAlbumTrackListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.PageInfo;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class LibraryTemplate extends AbstractLastFmOperations implements
		LibraryOperations {

	public LibraryTemplate(RestTemplate restTemplate,
			LastFmAccessGrant lastFmAccessGrant, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		super(restTemplate, lastFmAccessGrant, apiKey, secret,
				isAuthorizedForUser);
	}

	
	@Override
	public Page<AlbumTrack> getTracks(String userName) {
		return getTracks(userName,null);
	}
	
	@Override
	public Page<AlbumTrack> getTracks(String userName,Pageable pageable) {

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("user", userName);
		setPageableParamsIfSpecified(additionalParams,pageable);


		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"library.gettracks", apiKey, null, null, additionalParams);

		LastFmAlbumTrackListResponse trackListResponse = restTemplate
		.getForObject(buildLastFmApiUrl(methodParameters),
				LastFmAlbumTracksResponse.class).getNestedResponse();
		
		PageInfo pageInfo = trackListResponse.getPageInfo();

		
		// Last.Fm will return the last page available if a page number is requested greater than or equal to the total pages
		// Ensure that we override this behaviour and return an empty page for this case
		if (pageable != null && pageable.getPageNumber() >= pageInfo.getTotalPages())
		{
				return new PageImpl<AlbumTrack>(new ArrayList<AlbumTrack>(),pageable,pageInfo.getTotal());
		}
		
		return new PageImpl<AlbumTrack>(trackListResponse.getTracks(),new PageRequest(pageInfo.getZeroIndexedPage(),pageInfo.getPerPage()),trackListResponse.getPageInfo().getTotal());

	}
	
	
	
	@Override
	public void addTrack(String artistName, String trackName) {

		requireAuthorization();

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackName);
		additionalParams.put("artist", artistName);
	
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"library.addtrack", apiKey, lastFmAccessGrant.getToken(), secret,
				lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);
		
	}
	
	@Override
	public void removeTrack(String artistName, String trackName) {

		requireAuthorization();

		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("track", trackName);
		additionalParams.put("artist", artistName);
	
		LastFmApiMethodParameters methodParameters = new LastFmApiMethodParameters(
				"library.removetrack", apiKey, lastFmAccessGrant.getToken(), secret,
				lastFmAccessGrant.getSessionKey(), additionalParams);

		restTemplate.postForObject(baseApiUrl, methodParameters, String.class);
		
	}
	

}

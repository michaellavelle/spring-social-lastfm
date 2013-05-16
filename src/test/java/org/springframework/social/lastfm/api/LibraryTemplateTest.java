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

package org.springframework.social.lastfm.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.social.test.client.RequestMatchers.body;
import static org.springframework.social.test.client.RequestMatchers.header;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.lastfm.api.impl.UserAgentHelper;

public class LibraryTemplateTest extends AbstractLastFmApiTest {

	@Test
	public void getLibraryTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=library.gettracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/library-tracks"),
								responseHeaders));

		Page<AlbumTrack> tracksPage = lastFm.libraryOperations().getTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(325,tracksPage.getTotalElements());
		List<AlbumTrack> tracks = tracksPage.getContent();
		assertAlbumTrackData(tracks.get(0));

	}
	
	@Test
	public void getLibraryTracks_SingleTrackResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=library.gettracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/library-single-track-response"),
								responseHeaders));

		Page<AlbumTrack> tracksPage = lastFm.libraryOperations().getTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(1,tracksPage.getTotalElements());
		List<AlbumTrack> tracks = tracksPage.getContent();
		assertAlbumTrackData(tracks.get(0));

	}



	/**
	 * Tests for the case where the library tracks response contains no
	 * tracks. 
	 */
	@Test
	public void getLibraryTracksEmptyResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=library.gettracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(
								jsonResource("testdata/empty-library-response"),
								responseHeaders));

		Page<AlbumTrack> tracksPage = lastFm.libraryOperations().getTracks("mattslip");
		List<AlbumTrack> tracks = tracksPage.getContent();
		assertEquals(0,tracksPage.getNumber());
		assertEquals(0,tracksPage.getTotalElements());
		assertNotNull(tracks);
		assertEquals(0,tracks.size());

	}
	

	
	@Test
	public void addTrackToLibrary() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=9d70a80fc0ffaea3a74d33a793d0bf91&api_key=someApiKey&sk=someSessionKey&method=library.addtrack&token=someToken&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.libraryOperations().addTrack("My artist name", "My track name");

	}
	
	@Test
	public void removeTrackFromLibrary() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=3d2860a9ba668aa4344bf3ddb34fffb4&api_key=someApiKey&sk=someSessionKey&method=library.removetrack&token=someToken&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.libraryOperations().removeTrack("My artist name", "My track name");

	}
	
	@Test(expected = NotAuthorizedException.class)
	public void addTrackToLibrary_unauthorized() {


		unauthorizedLastFm.libraryOperations().addTrack("My artist name", "My track name");

	}
	
	

}

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

public class PlaylistTemplateTest extends AbstractLastFmApiTest {

	
	@Test
	public void createPlaylist() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=0b7d0d15b155d611514518879c368f2f&api_key=someApiKey&sk=someSessionKey&method=playlist.create&token=someToken&title=mytitle&description=mydescription"))
				.andRespond(
						withResponse(jsonResource("testdata/create-playlist-response"),
								responseHeaders));

		Playlist playlist = lastFm.playlistOperations().createPlaylist("mytitle", "mydescription");
		assertEquals("11201452",playlist.getId());
		assertEquals("mytitle",playlist.getTitle());
		assertEquals("mydescription",playlist.getDescription());


	}
	
	@Test
	public void addTrackToPlaylist() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=601f0863041828bffa2cd50f3c44d1ce&api_key=someApiKey&sk=someSessionKey&method=playlist.addTrack&token=someToken&track=Some+Track+Name&artist=Some+Artist&playlistID=1234"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.playlistOperations().addTrackToPlaylist("1234", "Some Artist", "Some Track Name");

	}

	

}

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

public class TrackTemplateTest extends AbstractLastFmApiTest {

	
	@Test
	public void getSimilarTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=track.similar&track=Music&artist=Madonna"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/similar-tracks"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.trackOperations().getSimilarTracks(new SimpleTrackDescriptor("Madonna","Music"));
		List<Track> tracks = tracksPage.getContent();
		assertEquals(0,tracksPage.getNumber());
		assertEquals(250,tracksPage.getTotalElements());
		assertEquals("Don't Tell Me",tracks.get(0).getName());
		assertEquals("Madonna",tracks.get(0).getArtistName());

	}
	
	
}

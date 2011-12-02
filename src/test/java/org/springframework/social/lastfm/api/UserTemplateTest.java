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
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.ResourceNotFoundException;

public class UserTemplateTest extends AbstractLastFmApiTest {

	protected final static Date someDate = new Date(123456789);

	@Test
	public void getUserProfile_currentUser() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_sig=8bbd32f49982b528b16cb704b671d242&api_key=someApiKey&sk=someSessionKey&method=user.getInfo&token=someToken"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/full-profile"),
								responseHeaders));

		LastFmProfile profile = lastFm.userOperations().getUserProfile();
		assertBasicProfileData(profile);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getUserProfile_currentUser_unauthorized() {
		unauthorizedLastFm.userOperations().getUserProfile();
	}

	@Test
	public void getUserProfile_specificUserByUserId() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getInfo&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/full-profile"),
								responseHeaders));

		LastFmProfile profile = lastFm.userOperations().getUserProfile(
				"mattslip");
		assertBasicProfileData(profile);
	}

	@Test
	public void getRecentTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getrecenttracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/recent-tracks"),
								responseHeaders));

		List<SimpleTrack> tracks = lastFm.userOperations().getRecentTracks(
				"mattslip");
		assertSimpleTrackData(tracks.get(0));
	}

	@Test
	public void getTopTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.gettoptracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/top-tracks"),
								responseHeaders));

		List<Track> tracks = lastFm.userOperations().getTopTracks("mattslip");
		assertTrackData(tracks.get(0));

	}

	@Test
	public void getLovedTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/loved-tracks"),
								responseHeaders));

		List<Track> tracks = lastFm.userOperations().getLovedTracks("mattslip");
		assertTrackData(tracks.get(0));

	}
	
	@Test
	public void getShouts() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getshouts&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/shouts"),
								responseHeaders));

		List<Shout> shouts = lastFm.userOperations().getShouts("mattslip");
		assertShoutData(shouts.get(2));



	}
	
	@Test
	public void getShoutsSingleShoutResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getshouts&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/single-shout-response"),
								responseHeaders));

		List<Shout> shouts = lastFm.userOperations().getShouts("mattslip");
		assertShoutData(shouts.get(0));
		
	}

	/**
	 * Tests for the case where the loved tracks response contains only a single
	 * track. In this case the Json format of the response is different to the
	 * response for multiple tracks
	 */
	@Test
	public void getLovedTracksSingleTrackResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(
								jsonResource("testdata/loved-tracks-single-track-response"),
								responseHeaders));

		List<Track> tracks = lastFm.userOperations().getLovedTracks("mattslip");
		assertTrackData(tracks.get(0));

	}

	@Test
	public void getLovedTracks_withoutAuthorization() {

		mockUnauthorizedServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/loved-tracks"),
								responseHeaders));

		List<Track> tracks = unauthorizedLastFm.userOperations()
				.getLovedTracks("mattslip");
		assertTrackData(tracks.get(0));

	}

	@Test(expected = ResourceNotFoundException.class)
	public void getLovedTracks_invalidUser() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=someOtherUser"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andRespond(
						withResponse(jsonResource("testdata/invalid-user"),
								responseHeaders));

		lastFm.userOperations().getLovedTracks(
				"someOtherUser");

	}
	
	
	@Test
	public void shout() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andExpect(
						body("format=json&api_sig=0086bb835c43ac345624691862cc9fd4&api_key=someApiKey&sk=someSessionKey&method=user.shout&token=someToken&message=someMessage&user=someUserName"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.userOperations().shout("someUserName", "someMessage");

	}
	

	@Test(expected = NotAuthorizedException.class)
	public void shout_unauthorized() {

		unauthorizedLastFm.userOperations().shout("someUserName", "someMessage");

	}

	@Test
	public void scrobble() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andExpect(
						body("format=json&api_sig=b4c8c73655abc90599cdfc0ed9c3b3e8&api_key=someApiKey&sk=someSessionKey&method=track.scrobble&token=someToken&timestamp=123456&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.userOperations().scrobble(
				new SimpleTrackDescriptor("My artist name", "My track name"),
				someDate);

	}

	@Test(expected = NotAuthorizedException.class)
	public void scrobble_unauthorized() {

		unauthorizedLastFm.userOperations().scrobble(
				new SimpleTrackDescriptor("My artist name", "My track name"),
				someDate);

	}

	@Test
	public void updateNowPlaying() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", "someUserAgent"))
				.andExpect(
						body("format=json&api_sig=6511f45e73a7fd12edab35d18a6655ce&api_key=someApiKey&sk=someSessionKey&method=track.updateNowPlaying&token=someToken&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/recent-tracks"),
								responseHeaders));

		lastFm.userOperations().updateNowPlaying(
				new SimpleTrackDescriptor("My artist name", "My track name"));

	}

	private void assertBasicProfileData(LastFmProfile profile) {
		assertEquals("123456789", profile.getId());
		assertEquals("mattslip", profile.getName());
		assertEquals("http://www.last.fm/user/mattslip", profile.getUrl());
	}

	private void assertBasicTrackData(TrackDescriptor trackDescriptor) {
		assertEquals("Moon Theory", trackDescriptor.getName());
		assertEquals("Miami Horror", trackDescriptor.getArtistName());

	}

	private void assertSimpleTrackData(SimpleTrack simpleTrack) {
		assertBasicTrackData(simpleTrack);
		assertEquals("http://www.last.fm/music/Miami+Horror/_/Moon+Theory",
				simpleTrack.getUrl());
	}

	private void assertTrackData(Track track) {
		assertBasicTrackData(track);
		assertEquals("http://www.last.fm/music/Miami+Horror/_/Moon+Theory",
				track.getUrl());
		assertEquals("http://www.last.fm/music/Miami+Horror", track.getArtist()
				.getUrl());
	}
	
	private void assertShoutData(Shout shout) {
		assertEquals(shout.getAuthor(),"LAST.HQ");
		assertEquals(shout.getMessage(),"Welcome aboard, mattslip! Happy listening.");
		assertNotNull(shout.getDate());

		
	}

}

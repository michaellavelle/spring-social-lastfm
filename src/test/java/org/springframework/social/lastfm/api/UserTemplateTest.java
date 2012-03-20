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

public class UserTemplateTest extends AbstractLastFmApiTest {

	protected final static Date someDate = new Date(123456789);

	@Test
	public void getUserProfile_currentUser() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_sig=8bbd32f49982b528b16cb704b671d242&api_key=someApiKey&sk=someSessionKey&method=user.getInfo&token=someToken"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
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
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
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
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/recent-tracks"),
								responseHeaders));

		Page<SimpleTrack> simpleTracks = lastFm.userOperations().getRecentTracks(
		"mattslip");
		assertEquals(0,simpleTracks.getNumber());
		assertEquals(1,simpleTracks.getTotalElements());
		List<SimpleTrack> tracks = simpleTracks.getContent();
		assertSimpleTrackData(tracks.get(0));
	}
	
	@Test
	public void getRecentTracksSingleTrackResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getrecenttracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/recent-tracks-single-track-response"),
								responseHeaders));

		Page<SimpleTrack> simpleTracks = lastFm.userOperations().getRecentTracks("mattslip");
		assertEquals(0,simpleTracks.getNumber());
		assertEquals(1,simpleTracks.getTotalElements());
		List<SimpleTrack> tracks = simpleTracks.getContent();
		assertSimpleTrackData(tracks.get(0));
	}
	
	/**
	 * Tests for the case where the top tracks response contains no 
	 * tracks. In this case the Json format of the response is different to the
	 * response for multiple tracks
	 */
	@Test
	public void getRecentTracksEmptyResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getrecenttracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(
								jsonResource("testdata/recent-tracks-empty"),
								responseHeaders));

		Page<SimpleTrack> simpleTracks = lastFm.userOperations().getRecentTracks("mattslip");
		List<SimpleTrack> tracks = simpleTracks.getContent();
		assertEquals(0,simpleTracks.getNumber());
		assertEquals(0,simpleTracks.getTotalElements());
		assertNotNull(tracks);
		assertEquals(0,tracks.size());

	}


	@Test
	public void getTopTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.gettoptracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/top-tracks"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.userOperations().getTopTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(1,tracksPage.getTotalElements());
		List<Track> tracks = tracksPage.getContent();
		assertTrackData(tracks.get(0));
	}
	
	@Test
	public void getTopArtists() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.gettopartists&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/top-artists"),
								responseHeaders));

		Page<Artist> artistsPage = lastFm.userOperations().getTopArtists("mattslip");
		assertEquals(0,artistsPage.getNumber());
		assertEquals(133,artistsPage.getTotalElements());
		List<Artist> artists = artistsPage.getContent();
		assertArtistData(artists.get(0));
	}
	
	@Test
	public void getTopArtistsEmptyResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.gettopartists&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/top-artists-empty"),
								responseHeaders));

		Page<Artist> artistsPage = lastFm.userOperations().getTopArtists("mattslip");
		assertEquals(0,artistsPage.getNumber());
		assertEquals(0,artistsPage.getTotalElements());
		List<Artist> artists = artistsPage.getContent();
		assertNotNull(artists);
		assertEquals(0,artists.size());
	}
	
	@Test
	public void getRecommendedArtists() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_sig=616df1497ae31b96429906947d82bad2&api_key=someApiKey&sk=someSessionKey&method=user.getrecommendedartists&token=someToken"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/recommended-artists"),
								responseHeaders));

		Page<Artist> artistsPage = lastFm.userOperations().getRecommendedArtists();
		List<Artist> artists = artistsPage.getContent();
		assertEquals(0,artistsPage.getNumber());
		assertEquals(250,artistsPage.getTotalElements());
		assertArtistData(artists.get(0));


	}
	
	
	@Test
	public void getTopTracksSingleTrackResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.gettoptracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/top-tracks-single-track-response"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.userOperations().getTopTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(1,tracksPage.getTotalElements());
		List<Track> tracks = tracksPage.getContent();
		
		assertTrackData(tracks.get(0));
	}
	
	/**
	 * Tests for the case where the top tracks response contains no 
	 * tracks. In this case the Json format of the response is different to the
	 * response for multiple tracks
	 */
	@Test
	public void getTopTracksEmptyResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.gettoptracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(
								jsonResource("testdata/top-tracks-empty"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.userOperations().getTopTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(0,tracksPage.getTotalElements());
		List<Track> tracks = tracksPage.getContent();
		assertNotNull(tracks);
		assertEquals(0,tracks.size());

	}




	@Test
	public void getLovedTracks() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/loved-tracks"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.userOperations().getLovedTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(1,tracksPage.getTotalElements());
		List<Track> tracks = tracksPage.getContent();
		assertTrackData(tracks.get(0));

	}
	
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
	
	@Test
	public void getShouts() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getshouts&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/shouts"),
								responseHeaders));

		Page<Shout> shoutsPage = lastFm.userOperations().getShouts("mattslip");
		assertEquals(0,shoutsPage.getNumber());
		assertEquals(3,shoutsPage.getTotalElements());
		List<Shout> shouts = shoutsPage.getContent();
		assertShoutData(shouts.get(2));



	}
	
	@Test
	public void getShoutsSingleShoutResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getshouts&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/single-shout-response"),
								responseHeaders));

		Page<Shout> shoutsPage = lastFm.userOperations().getShouts("mattslip");
		assertEquals(0,shoutsPage.getNumber());
		assertEquals(1,shoutsPage.getTotalElements());
		List<Shout> shouts = shoutsPage.getContent();
		assertShoutData(shouts.get(0));
		
	}
	
	@Test
	public void getShoutsEmptyShoutsResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getshouts&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/empty-shouts-response"),
								responseHeaders));

		Page<Shout> shoutsPage = lastFm.userOperations().getShouts("mattslip");
		List<Shout> shouts = shoutsPage.getContent();
		assertEquals(0,shoutsPage.getNumber());
		assertEquals(0,shoutsPage.getTotalElements());
		assertNotNull(shouts);
		assertEquals(shouts.size(),0);
	}
	
	@Test
	public void getFriendsEmptyFriendsResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getfriends&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/empty-friends-response"),
								responseHeaders));

		Page<LastFmProfile> friendsPage = lastFm.userOperations().getFriends("mattslip");
		List<LastFmProfile> friends = friendsPage.getContent();
		assertEquals(0,friendsPage.getNumber());
		assertEquals(0,friendsPage.getTotalElements());
		assertNotNull(friends);
		assertEquals(0,friends.size());
	}
	
	@Test
	public void getFriends() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getfriends&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/friends"),
								responseHeaders));

		Page<LastFmProfile> friendsPage = lastFm.userOperations().getFriends("mattslip");
		List<LastFmProfile> friends = friendsPage.getContent();
		assertEquals(0,friendsPage.getNumber());
		assertEquals(3,friendsPage.getTotalElements());
		assertNotNull(friends);
		assertEquals(3,friends.size());
	}
	
	@Test
	public void getNeighbours() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getneighbours&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/neighbours"),
								responseHeaders));

		List<LastFmProfile> neighbours = lastFm.userOperations().getNeighbours("mattslip");
		assertNotNull(neighbours);
		assertEquals(50,neighbours.size());
	}
	
	@Test
	public void getFriendsSingleFriendResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getfriends&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/single-friend-response"),
								responseHeaders));

		Page<LastFmProfile> friendsPage = lastFm.userOperations().getFriends("mattslip");
		List<LastFmProfile> friends = friendsPage.getContent();
		assertNotNull(friends);
		assertEquals(1,friends.size());
		assertEquals(0,friendsPage.getNumber());
		assertEquals(1,friendsPage.getTotalElements());
		assertEquals("michaellavelle",friends.get(0).getName());
		assertEquals("Michael Lavelle",friends.get(0).getRealName());
		assertEquals("http://www.last.fm/user/michaellavelle",friends.get(0).getUrl());


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
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(
								jsonResource("testdata/loved-tracks-single-track-response"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.userOperations().getLovedTracks("mattslip");
		assertEquals(0,tracksPage.getNumber());
		assertEquals(1,tracksPage.getTotalElements());
		List<Track> tracks = tracksPage.getContent();
		assertTrackData(tracks.get(0));

	}
	
	/**
	 * Tests for the case where the loved tracks response contains only a single
	 * track. In this case the Json format of the response is different to the
	 * response for multiple tracks
	 */
	@Test
	public void getLovedTracksEmptyResponse() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(
								jsonResource("testdata/loved-tracks-empty"),
								responseHeaders));

		Page<Track> tracksPage = lastFm.userOperations().getLovedTracks("mattslip");
		List<Track> tracks = tracksPage.getContent();
		assertEquals(0,tracksPage.getNumber());
		assertEquals(0,tracksPage.getTotalElements());
		assertNotNull(tracks);
		assertEquals(0,tracks.size());

	}
	

	@Test
	public void getLovedTracks_withoutAuthorization() {

		mockUnauthorizedServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=mattslip"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andRespond(
						withResponse(jsonResource("testdata/loved-tracks"),
								responseHeaders));

		List<Track> tracks = unauthorizedLastFm.userOperations()
				.getLovedTracks("mattslip").getContent();
		assertTrackData(tracks.get(0));

	}

	@Test(expected = ResourceNotFoundException.class)
	public void getLovedTracks_invalidUser() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/?format=json&api_key=someApiKey&method=user.getlovedtracks&user=someOtherUser"))
				.andExpect(method(GET))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
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
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
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
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=b4c8c73655abc90599cdfc0ed9c3b3e8&api_key=someApiKey&sk=someSessionKey&method=track.scrobble&token=someToken&timestamp=123456&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.userOperations().scrobble(
				new SimpleTrackDescriptor("My artist name", "My track name"),
				someDate);

	}
	
	@Test
	public void love() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=b50a3b8e66b396f78133d34dc887bda3&api_key=someApiKey&sk=someSessionKey&method=track.love&token=someToken&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.userOperations().love("My artist name", "My track name");

	}
	
	
	
	
	@Test(expected = NotAuthorizedException.class)
	public void love_unauthorized() {

		unauthorizedLastFm.userOperations().love("My artist name", "My track name");

	}
	
	@Test(expected = NotAuthorizedException.class)
	public void unlove_unauthorized() {

		unauthorizedLastFm.userOperations().unlove("My artist name", "My track name");

	}
	
	
	@Test
	public void unlove() {

		mockServer
				.expect(requestTo("http://ws.audioscrobbler.com/2.0/"))
				.andExpect(method(POST))
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
				.andExpect(
						body("format=json&api_sig=929dd59b92e361a3e3ffb7b687856a2b&api_key=someApiKey&sk=someSessionKey&method=track.unlove&token=someToken&track=My+track+name&artist=My+artist+name"))
				.andRespond(
						withResponse(jsonResource("testdata/status-ok"),
								responseHeaders));

		lastFm.userOperations().unlove("My artist name", "My track name");

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
				.andExpect(header("User-Agent", UserAgentHelper.getUserAgent()))
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
	
	
	private void assertArtistData(Artist artist) {
		
		assertEquals("http://www.last.fm/music/Jessica+6",artist.getUrl());
		assertEquals("Jessica 6",artist.getName());

	}
	
	private void assertShoutData(Shout shout) {
		assertEquals(shout.getAuthor(),"LAST.HQ");
		assertEquals(shout.getMessage(),"Welcome aboard, mattslip! Happy listening.");
		assertNotNull(shout.getDate());

		
	}

}

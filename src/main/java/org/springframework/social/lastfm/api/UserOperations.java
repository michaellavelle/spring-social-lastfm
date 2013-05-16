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

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Michael Lavelle
 */
public interface UserOperations {

	public LastFmProfile getUserProfile();

	public LastFmProfile getUserProfile(String userName);
	
	public void shout(String userName,String message);

	public Page<Track> getLovedTracks(String userName,Pageable pageable);
	
	public Page<Track> getLovedTracks(String userName);

	public Page<Shout> getShouts(String userName);
	
	public Page<Shout> getShouts(String userName,Pageable pageable);
	
	public Page<LastFmProfile> getFriends(String userName);
	
	public Page<LastFmProfile> getFriends(String userName,Pageable pageable);
	
	public List<LastFmProfile> getNeighbours(String userName);
	
	public List<Playlist> getPlaylists(String userName);

	public List<LastFmProfile> getNeighbours(String userName,int limit);

	public Page<Track> getTopTracks(String userName);
	
	public Page<Track> getTopTracks(String userName,Pageable pageable);
	
	public Page<Artist> getTopArtists(String userName);
	
	public Page<Artist> getTopArtists(String userName,Pageable pageable);

	public Page<SimpleTrack> getRecentTracks(String userName);
	
	public Page<SimpleTrack> getRecentTracks(String userName,Pageable pageable);

	public void scrobble(TrackDescriptor trackDescriptor, Date timestamp);
	
	public void love(String artistName,String trackName);
	
	public void unlove(String artistName,String trackName);

	public void updateNowPlaying(TrackDescriptor trackDescriptor);

	public Page<Artist> getRecommendedArtists();
	public Page<Artist> getRecommendedArtists(Pageable pageable);


}

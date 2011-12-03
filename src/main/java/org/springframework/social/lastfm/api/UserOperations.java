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

/**
 * @author Michael Lavelle
 */
public interface UserOperations {

	public LastFmProfile getUserProfile();

	public LastFmProfile getUserProfile(String userName);
	
	public void shout(String userName,String message);

	public List<Track> getLovedTracks(String userName);
	
	public List<Shout> getShouts(String userName);
	
	public List<LastFmProfile> getFriends(String userName);
	
	public List<LastFmProfile> getNeighbours(String userName);



	public List<Track> getTopTracks(String userName);

	public List<SimpleTrack> getRecentTracks(String userName);

	public void scrobble(TrackDescriptor trackDescriptor, Date timestamp);
	
	public void love(String artistName,String trackName);
	public void unlove(String artistName,String trackName);



	public void updateNowPlaying(TrackDescriptor trackDescriptor);

}

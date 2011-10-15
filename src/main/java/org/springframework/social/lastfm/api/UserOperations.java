package org.springframework.social.lastfm.api;

import java.util.Date;
import java.util.List;

/**
 * @author Michael Lavelle
 */
public interface UserOperations {

	public LastFmProfile getUserProfile();

	public LastFmProfile getUserProfile(String userName);

	public List<Track> getLovedTracks(String userName);
	public List<Track> getTopTracks(String userName);

	public List<SimpleTrack> getRecentTracks(String userName);

	
	public void scrobble(TrackDescriptor trackDescriptor,Date timestamp);
	public void updateNowPlaying(TrackDescriptor trackDescriptor);

	
	
	
}

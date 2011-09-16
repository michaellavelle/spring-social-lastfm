package org.springframework.social.lastfm.api;

import java.util.List;

/**
 * @author Michael Lavelle
 */
public interface UserOperations {

	public LastFmProfile getUserProfile();

	public LastFmProfile getUserProfile(String userName);

	public List<Track> getLovedTracks(String userName);

}

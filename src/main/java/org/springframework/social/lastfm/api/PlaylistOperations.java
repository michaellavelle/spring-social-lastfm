package org.springframework.social.lastfm.api;

public interface PlaylistOperations {

	public Playlist createPlaylist(String title,String description);
	public void addTrackToPlaylist(String id, String artistName,String trackName);
}

package org.springframework.social.lastfm.api;

public class AlbumTrack extends Track {

	private Album album;
	
	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public AlbumTrack(String url, String name, String musicBrainsId,
			Artist artist,Album album) {
		super(url, name, musicBrainsId, artist);
		this.album = album;
	}

}

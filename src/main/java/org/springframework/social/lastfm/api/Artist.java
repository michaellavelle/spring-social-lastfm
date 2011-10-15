package org.springframework.social.lastfm.api;

/**
 * @author Michael Lavelle
 */
public class Artist {

	public String getMusicBrainsId() {
		return musicBrainsId;
	}

	public void setMusicBrainsId(String musicBrainsId) {
		this.musicBrainsId = musicBrainsId;
	}

	private String url;
	private String name;
	private String musicBrainsId;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Artist(String url, String name,String musicBrainsId) {
		this.url = url;
		this.name = name;
		this.musicBrainsId = musicBrainsId;
	}

}

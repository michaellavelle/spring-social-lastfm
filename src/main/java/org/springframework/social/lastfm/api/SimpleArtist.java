package org.springframework.social.lastfm.api;

public class SimpleArtist implements ArtistDescriptor {

	private String name;
	private String musicBrainsId;
	
	public SimpleArtist(String name,String musicBrainsId)
	{
		this.name = name;
		this.musicBrainsId = musicBrainsId;
	}
	
	public SimpleArtist(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getMusicBrainsId() {
		return musicBrainsId;
	}
	
}

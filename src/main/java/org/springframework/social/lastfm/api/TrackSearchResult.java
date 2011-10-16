package org.springframework.social.lastfm.api;

public class TrackSearchResult implements TrackDescriptor {

	private String name;
	private String artistName;
	private String url;
	
	
	public TrackSearchResult(String name,String artistName,String url)
	{
		this.name = name;
		this.artistName = artistName;
		this.url = url;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getArtistName() {
		return artistName;
	}

	@Override
	public String getMusicBrainsId() {
		return null;
	}
	
	public String getUrl()
	{
		return url;
	}

}

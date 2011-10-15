package org.springframework.social.lastfm.api;

/**
 * @author Michael Lavelle
 */
public class SimpleTrackDescriptor implements TrackDescriptor {

	private String name;
	private ArtistDescriptor artistDescriptor;
	private String musicBrainsId;
	
	public SimpleTrackDescriptor(String artistName,String name,String musicBrainsId)
	{
		this.name = name;
		this.artistDescriptor = new SimpleArtist(artistName);
		this.musicBrainsId = musicBrainsId;
	}
	
	public SimpleTrackDescriptor(ArtistDescriptor artistDescriptor,String name,String musicBrainsId)
	{
		this.name = name;
		this.artistDescriptor = artistDescriptor;
		this.musicBrainsId = musicBrainsId;
	}
	
	
	
	
	public SimpleTrackDescriptor(String artistName,String name)
	{
		this.name = name;
		this.artistDescriptor = new SimpleArtist(artistName);
	}
	
	public SimpleTrackDescriptor(String musicBrainsId)
	{
		this.musicBrainsId = musicBrainsId;

	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getArtistName() {
		return artistDescriptor == null ? null : artistDescriptor.getName();
	}

	@Override
	public String getMusicBrainsId() {
		return musicBrainsId;
	}

}

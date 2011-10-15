package org.springframework.social.lastfm.api;

/**
 * @author Michael Lavelle
 */
public class SimpleTrack extends SimpleTrackDescriptor {

	private String url;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public SimpleTrack(String url, String name,String musicBrainsId,String artistName) {
		super(artistName,name,musicBrainsId);
		this.url = url;
		
	}
	
	public SimpleTrack(String url, String name,String musicBrainsId,SimpleArtist simpleArtist) {
		super(simpleArtist,name,musicBrainsId);
		this.url = url;
		
	}

}

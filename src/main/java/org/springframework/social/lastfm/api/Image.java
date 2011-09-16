package org.springframework.social.lastfm.api;

/**
 * @author Michael Lavelle
 */
public class Image {

	private String url;
	private String size;

	public String getSize() {
		return size;
	}

	public String getUrl() {
		return url;
	}

	public Image(String url, String size) {
		this.url = url;
		this.size = size;
	}

}

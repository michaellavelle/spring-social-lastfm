package org.springframework.social.lastfm.api;

import java.io.Serializable;
import java.util.List;

/**
 * Model class containing a LastFM user's profile information.
 * 
 * @author Michael Lavelle
 */
public class LastFmProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String realName;
	private String url;
	private List<Image> images;

	public LastFmProfile() {

	}

	public LastFmProfile(String id, String name, String realName, String url,
			List<Image> images) {
		this.id = id;
		this.name = name;
		this.realName = realName;
		this.url = url;
		this.images = images;
	}

	public List<Image> getImages() {
		return images;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package org.springframework.social.lastfm.api;

public class Album {

	private String name;

	public Album(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

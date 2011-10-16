/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.lastfm.api;

/**
 * @author Michael Lavelle
 */
public class Track implements TrackDescriptor {

	private String url;
	private String name;
	private String musicBrainsId;
	private Artist artist;

	public String getMusicBrainsId() {
		return musicBrainsId;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public void setMusicBrainsId(String musicBrainsId) {
		this.musicBrainsId = musicBrainsId;
	}

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

	public Track(String url, String name,String musicBrainsId,Artist artist) {
		this.url = url;
		this.name = name;
		this.musicBrainsId = musicBrainsId;
		this.artist = artist;
	}

	@Override
	public String getArtistName() {
		return artist.getName();
	}

}

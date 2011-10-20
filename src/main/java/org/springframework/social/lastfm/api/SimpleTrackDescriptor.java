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
public class SimpleTrackDescriptor implements TrackDescriptor {

	private String name;
	private ArtistDescriptor artistDescriptor;
	private String musicBrainsId;

	public SimpleTrackDescriptor(String artistName, String name,
			String musicBrainsId) {
		this.name = name;
		this.artistDescriptor = new SimpleArtist(artistName);
		this.musicBrainsId = musicBrainsId;
	}

	public SimpleTrackDescriptor(ArtistDescriptor artistDescriptor,
			String name, String musicBrainsId) {
		this.name = name;
		this.artistDescriptor = artistDescriptor;
		this.musicBrainsId = musicBrainsId;
	}

	public SimpleTrackDescriptor(String artistName, String name) {
		this.name = name;
		this.artistDescriptor = new SimpleArtist(artistName);
	}

	public SimpleTrackDescriptor(String musicBrainsId) {
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

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
package org.springframework.social.lastfm.api.impl.json.lists;

import java.util.List;

import org.springframework.social.lastfm.api.ArtistDescriptor;
import org.springframework.social.lastfm.api.SimpleTrackDescriptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Container for a list of Last.fm json object - allows for automatic JSON binding from *either* a list of objects
 * or a Map representation of a single object, as LastFm responds with different Json structures
 * depending on whether a single object is returned where a list can be.
 * 
 * @author Michael Lavelle
 */
public class SimpleTrackDescriptorListContainer extends AbstractLastFmListContainer<SimpleTrackDescriptor> {


	@JsonCreator
	public SimpleTrackDescriptorListContainer(List<SimpleTrackDescriptor> simpleTracks) {
		super(simpleTracks);
	}
	
	public SimpleTrackDescriptorListContainer(String artistName, String name,
			String musicBrainsId) {
		super(new SimpleTrackDescriptor(artistName,name,musicBrainsId));
	}

	public SimpleTrackDescriptorListContainer(ArtistDescriptor artistDescriptor,
			String name, String musicBrainsId) {
		super(new SimpleTrackDescriptor(artistDescriptor,name,musicBrainsId));
	}

	public SimpleTrackDescriptorListContainer(String artistName, String name) {
		super(new SimpleTrackDescriptor(artistName,name));
	}

	public SimpleTrackDescriptorListContainer(String musicBrainsId) {

		super(new SimpleTrackDescriptor(musicBrainsId));
	}

	public List<SimpleTrackDescriptor> getTracks()
	{
		return elements;
	}

}

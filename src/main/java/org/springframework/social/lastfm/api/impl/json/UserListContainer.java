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
package org.springframework.social.lastfm.api.impl.json;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.Artist;
import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.Shout;
import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.Track;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Container for a shout list - allows for automatic JSON binding from *either* a list of Shouts
 * or a Map representation of a single shout, as LastFm responds with different Json structures
 * depending on whether a single shout is returned
 * 
 * @author Michael Lavelle
 */
public class UserListContainer {

	private List<LastFmProfile> users;

	@JsonCreator
	public UserListContainer(List<LastFmProfile> users) {
		this.users = users;
	}
	
	public UserListContainer(String id, String name, String realName, String url,
			List<Image> images) {
		this.users = Arrays.asList(new LastFmProfile(id,name,realName,url,images));
		
	}

	public List<LastFmProfile> getUsers() {
		return users;
	}

}

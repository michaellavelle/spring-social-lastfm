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

import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFmProfile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Container for a list of Last.fm users- allows for automatic JSON binding from *either* a list of objects
 * or a Map representation of a single user, as LastFm responds with different Json structures
 * depending on whether a single user is returned or a number of users
 * 
 * @author Michael Lavelle
 */
public class UserListContainer extends AbstractLastFmListContainer<LastFmProfile> {


	@JsonCreator
	public UserListContainer(List<LastFmProfile> users) {
		super(users);
	}
	
	public UserListContainer(String id, String name, String realName, String url,
			List<Image> images) {
		super(new LastFmProfile(id,name,realName,url,images));
		
	}

	public List<LastFmProfile> getUsers() {
		return elements;
	}

}

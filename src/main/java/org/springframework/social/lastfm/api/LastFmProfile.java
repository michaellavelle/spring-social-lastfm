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

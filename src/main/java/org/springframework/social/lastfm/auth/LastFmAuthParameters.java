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
package org.springframework.social.lastfm.auth;

import java.util.List;
import java.util.Map;

import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.support.ParameterMap;
/**
* Parameters for building an LastFm authorize URL.
* @author Michael Lavelle
* @see LastFmAuthOperations#buildAuthorizeUrl(OAuth2Parameters)
*/
public final class LastFmAuthParameters extends ParameterMap {

	private static final String REDIRECT_URI = "cb";

	/**
	 * Creates a new LastFmAuthParameters map that is initially empty. Use the
	 * setter methods to add parameters after construction.
	 * 
	 * @see #setRedirectUri(String)
	 * @see #setScope(String)
	 * @see #setState(String)
	 * @see #set(String, String)
	 */
	public LastFmAuthParameters() {
		super(null);
	}

	/**
	 * Creates a new LastFmAuthParameters populated from the initial parameters
	 * provided.
	 * 
	 * @param parameters
	 *            the initial parameters
	 * @see #setRedirectUri(String)
	 * @see #setScope(String)
	 * @see #setState(String)
	 */
	public LastFmAuthParameters(Map<String, List<String>> parameters) {
		super(parameters);
	}

	/**
	 * The authorization callback url. This value must match the redirectUri
	 * registered with the LastFm. 
	 */
	public String getRedirectUri() {
		return getFirst(REDIRECT_URI);
	}

	/**
	 * Sets the authorization callback url. This value must match the
	 * redirectUri registered with LastFm. 
	 */
	public void setRedirectUri(String redirectUri) {
		set(REDIRECT_URI, redirectUri);
	}

}

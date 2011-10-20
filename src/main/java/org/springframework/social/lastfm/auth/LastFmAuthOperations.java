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

import org.springframework.util.MultiValueMap;

/**
 * A service interface for the LastFm auth flow. This interface allows you to
 * conduct the "Pseudo-OAuth dance" with LastFm on behalf of a user.
 * 
 * @author Michael Lavelle
 */
public interface LastFmAuthOperations {

	/**
	 * Construct the URL to redirect the user to for authorization.
	 * 
	 * @param parameters
	 *            authorization parameters needed to build the URL
	 * @return the absolute authorize URL to redirect the user to for
	 *         authorization
	 */
	String buildAuthorizeUrl(LastFmAuthParameters parameters);

	/**
	 * Exchange the authorization code for an access grant.
	 * 
	 * @param token
	 *            the authorization code returned by the lastfm upon user
	 *            authorization
	 * @param additionalParameters
	 *            any additional parameters to be sent when exchanging the
	 *            authorization code for an access grant. Should not be encoded.
	 * @return the access grant.
	 */
	LastFmAccessGrant exchangeForAccess(String token,
			MultiValueMap<String, String> additionalParameters);

}

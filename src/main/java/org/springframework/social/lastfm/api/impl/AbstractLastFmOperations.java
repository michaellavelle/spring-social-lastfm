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
package org.springframework.social.lastfm.api.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public abstract class AbstractLastFmOperations {

	protected final RestTemplate restTemplate;
	protected final boolean isAuthorizedForUser;
	protected String baseApiUrl = "http://ws.audioscrobbler.com/2.0/";

	protected String apiKey;
	protected String secret;
	protected LastFmAccessGrant lastFmAccessGrant;

	public AbstractLastFmOperations(RestTemplate restTemplate,
			LastFmAccessGrant lastFmAccessGrant, String apiKey, String secret,
			boolean isAuthorizedForUser) {
		this.restTemplate = restTemplate;
		this.isAuthorizedForUser = isAuthorizedForUser;
		this.apiKey = apiKey;
		this.secret = secret;
		this.lastFmAccessGrant = lastFmAccessGrant;
	}

	protected void requireAuthorization() {
		if (!isAuthorizedForUser) {
			throw new MissingAuthorizationException();
		}
	}

	protected void setPageableParamsIfSpecified(Map<String,String> params,Pageable pageable)
	{
		if (pageable != null)
		{
			params.put("page",new Integer(pageable.getPageNumber() + 1).toString());
			params.put("limit", new Integer(pageable.getPageSize()).toString());
		}
	}
	
	
	
	protected String buildLastFmApiUrl(
			LastFmApiMethodParameters methodParameters) {
		String delim = "?";
		String url = baseApiUrl;
		for (Map.Entry<String, List<String>> entry : methodParameters
				.entrySet()) {
			for (String value : entry.getValue()) {
				url = url + delim + entry.getKey() + "=" + value;
				delim = "&";
			}
		}
		return url;
	}

}

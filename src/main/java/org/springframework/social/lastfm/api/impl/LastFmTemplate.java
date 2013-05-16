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

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.LibraryOperations;
import org.springframework.social.lastfm.api.PlaylistOperations;
import org.springframework.social.lastfm.api.TrackOperations;
import org.springframework.social.lastfm.api.UserOperations;
import org.springframework.social.lastfm.api.impl.json.LastFmModule;
import org.springframework.social.lastfm.auth.AbstractLastFmAuthApiBinding;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class LastFmTemplate extends AbstractLastFmAuthApiBinding implements
		LastFm {

	private UserOperations userOperations;
	private TrackOperations trackOperations;
	private LibraryOperations libraryOperations;
	private PlaylistOperations playlistOperations;

	private ObjectMapper objectMapper;

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters(boolean json) {
		List<HttpMessageConverter<?>> messageConverters = super
				.getMessageConverters(json);
		messageConverters.add(new ByteArrayHttpMessageConverter());
		return messageConverters;
	}

	private void initSubApis(LastFmAccessGrant lastFmAccessGrant,
			String apiKey, String secret) {

		userOperations = new UserTemplate(getRestTemplate(), lastFmAccessGrant,
				apiKey, secret, isAuthorized());

		trackOperations = new TrackTemplate(getRestTemplate(),
				lastFmAccessGrant, apiKey, secret, isAuthorized());
		
		libraryOperations = new LibraryTemplate(getRestTemplate(),
				lastFmAccessGrant, apiKey, secret, isAuthorized());
		

		playlistOperations = new PlaylistTemplate(getRestTemplate(),
				lastFmAccessGrant, apiKey, secret, isAuthorized());



	}

	/**
	 * Create a new instance of LastFmTemplate. This constructor creates a new
	 * LastFmTemplate able to perform unauthenticated operations against
	 * LastFm's API. Some operations do not require LastFmAuth authentication.
	 * For example, retrieving a specified user's profile or feed does not
	 * require authentication . A LastFmTemplate created with this constructor
	 * will support those operations. Those operations requiring authentication
	 * will throw {@link NotAuthorizedException}.
	 */
	public LastFmTemplate(String apiKey) {
		super();
		initialize(null, apiKey, null);
	}

	/**
	 * Create a new instance of LastFmTemplate. This constructor creates the
	 * LastFmTemplate using a given access token.
	 * 
	 * @param accessToken
	 *            An access token given by LastFm after a successful
	 *            authentication
	 */
	public LastFmTemplate(
			LastFmAccessGrant lastFmAccessGrant, String apiKey, String secret) {
		super(lastFmAccessGrant);
		initialize(lastFmAccessGrant, apiKey, secret);

	}

	// private helpers
	private void initialize(LastFmAccessGrant lastFmAccessGrant, String apiKey,
			String secret) {
		registerLastFmJsonModule(getRestTemplate());
		getRestTemplate().setErrorHandler(new LastFmErrorHandler());
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(lastFmAccessGrant, apiKey, secret);
	}

	private void registerLastFmJsonModule(RestTemplate restTemplate2) {
		objectMapper = new ObjectMapper();
		LastFmModule lastFmModule = new LastFmModule();
		objectMapper.setDateFormat(lastFmModule.getDateFormat());
		objectMapper.registerModule(lastFmModule);
		List<HttpMessageConverter<?>> converters = getRestTemplate()
				.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}

	@Override
	public UserOperations userOperations() {
		return userOperations;
	}

	@Override
	public TrackOperations trackOperations() {
		return trackOperations;
	}
	
	@Override
	public LibraryOperations libraryOperations() {
		return libraryOperations;
	}
	

	@Override
	public PlaylistOperations playlistOperations() {
		return playlistOperations;
	}

}

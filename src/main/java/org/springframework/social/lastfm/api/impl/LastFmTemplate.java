package org.springframework.social.lastfm.api.impl;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.UserOperations;
import org.springframework.social.lastfm.api.impl.json.LastFmModule;
import org.springframework.social.lastfm.auth.AbstractLastFmAuthApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class LastFmTemplate extends AbstractLastFmAuthApiBinding implements
		LastFm {

	private UserOperations userOperations;

	private ObjectMapper objectMapper;

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters(boolean json) {
		List<HttpMessageConverter<?>> messageConverters = super
				.getMessageConverters(json);
		messageConverters.add(new ByteArrayHttpMessageConverter());
		return messageConverters;
	}

	private void initSubApis(String token, String sessionKey, String apiKey,
			String secret) {

		userOperations = new UserTemplate(getRestTemplate(), token, sessionKey,
				apiKey, secret, isAuthorized());

	}

	/**
	 * Create a new instance of LastFmTemplate. This constructor creates a
	 * new LastFmTemplate able to perform unauthenticated operations against
	 * LastFm's API. Some operations do not require LastFmAuth authentication.
	 * For example, retrieving a specified user's profile or feed does not
	 * require authentication . A LastFmTemplate created with this
	 * constructor will support those operations. Those operations requiring
	 * authentication will throw {@link NotAuthorizedException}.
	 */
	public LastFmTemplate(String userAgent) {
		super(userAgent);
		initialize(null, null, null, null);
	}

	/**
	 * Create a new instance of LastFmTemplate. This constructor creates the
	 * FacebookTemplate using a given access token.
	 * 
	 * @param accessToken
	 *            An access token given by LastFm after a successful
	 *            authentication
	 */
	public LastFmTemplate(String userAgent,String token, String sessionKey, String apiKey,
			String secret) {
		super(userAgent,token, sessionKey);
		initialize(token, sessionKey, apiKey, secret);

	}

	// private helpers
	private void initialize(String token, String sessionKey, String apiKey,
			String secret) {
		registerSoundCloudJsonModule(getRestTemplate());
		getRestTemplate().setErrorHandler(new LastFmErrorHandler());
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(token, sessionKey, apiKey, secret);
	}

	private void registerSoundCloudJsonModule(RestTemplate restTemplate2) {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new LastFmModule());
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

}

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

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.lastfm.api.impl.LastFmTemplate;
import org.springframework.social.lastfm.auth.LastFmAccessGrant;
import org.springframework.social.test.client.MockRestServiceServer;

public abstract class AbstractLastFmApiTest {
	protected static final String API_KEY= "someApiKey";
	protected static final String SECRET= "secret";

	protected static final String USER_AGENT = "someUserAgent";

	protected static final LastFmAccessGrant ACCESS_GRANT = new LastFmAccessGrant("someToken","someSessionKey");
	
	
	protected LastFmTemplate lastFm;
	protected LastFmTemplate unauthorizedLastFm;
	protected MockRestServiceServer mockServer;
	protected MockRestServiceServer mockUnauthorizedServer;

	protected HttpHeaders responseHeaders;

	@Before
	public void setup() {
		lastFm = new LastFmTemplate(USER_AGENT,ACCESS_GRANT,API_KEY,SECRET);
		mockServer = MockRestServiceServer.createServer(lastFm.getRestTemplate());

		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		unauthorizedLastFm = new LastFmTemplate(USER_AGENT,API_KEY);
		mockUnauthorizedServer = MockRestServiceServer.createServer(unauthorizedLastFm.getRestTemplate());
	}

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}
	



}

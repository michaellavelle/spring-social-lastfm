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
package org.springframework.social.lastfm.connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.UserOperations;

public class LastFmAdapterTest {

	private LastFmAdapter apiAdapter = new LastFmAdapter();

	@SuppressWarnings("unchecked")
	private LastFm lastFm = Mockito.mock(LastFm.class);

	@Test
	public void fetchProfile() {
		UserOperations userOperations = Mockito.mock(UserOperations.class);
		Mockito.when(lastFm.userOperations()).thenReturn(userOperations);
		Mockito.when(userOperations.getUserProfile())
				.thenReturn(
						new LastFmProfile(
								"123456789",
								"mattslip",
								"Matt Slip",
								"http://www.last.fm/user/mattslip",
								Arrays.asList(new Image(
										"http://userserve-ak.last.fm/serve/64/46182239.jpg",
										"medium"))));

		UserProfile profile = apiAdapter.fetchUserProfile(lastFm);
		assertEquals("Matt Slip", profile.getName());
		assertEquals("Matt", profile.getFirstName());
		assertEquals("Slip", profile.getLastName());
		assertNull(profile.getEmail());
		assertEquals("mattslip", profile.getUsername());
	}

	@Test
	public void setConnectionValues() {
		UserOperations userOperations = Mockito.mock(UserOperations.class);
		Mockito.when(lastFm.userOperations()).thenReturn(userOperations);
		Mockito.when(userOperations.getUserProfile())
				.thenReturn(
						new LastFmProfile(
								"123456789",
								"mattslip",
								"Matt Slip",
								"http://www.last.fm/user/mattslip",
								Arrays.asList(new Image(
										"http://userserve-ak.last.fm/serve/64/46182239.jpg",
										"medium"))));

		TestConnectionValues connectionValues = new TestConnectionValues();
		apiAdapter.setConnectionValues(lastFm, connectionValues);
		assertEquals("Matt Slip", connectionValues.getDisplayName());
		assertEquals("http://userserve-ak.last.fm/serve/64/46182239.jpg",
				connectionValues.getImageUrl());
		assertEquals("http://www.last.fm/user/mattslip",
				connectionValues.getProfileUrl());
		assertEquals("123456789", connectionValues.getProviderUserId());
	}

	private static class TestConnectionValues implements ConnectionValues {

		private String displayName;
		private String imageUrl;
		private String profileUrl;
		private String providerUserId;

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getProfileUrl() {
			return profileUrl;
		}

		public void setProfileUrl(String profileUrl) {
			this.profileUrl = profileUrl;
		}

		public String getProviderUserId() {
			return providerUserId;
		}

		public void setProviderUserId(String providerUserId) {
			this.providerUserId = providerUserId;
		}

	}
}

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

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFm;
import org.springframework.social.lastfm.api.LastFmProfile;

/**
 * LastFm ApiAdapter implementation.
 * 
 * @author Michael Lavelle
 */
public class LastFmAdapter implements ApiAdapter<LastFm> {

	@Override
	public UserProfile fetchUserProfile(LastFm lastfm) {
		LastFmProfile profile = lastfm.userOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getRealName())
				.setUsername(profile.getName()).build();
	}

	@Override
	public void setConnectionValues(LastFm lastfm, ConnectionValues values) {
		LastFmProfile profile = lastfm.userOperations().getUserProfile();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getRealName());
		values.setProfileUrl(profile.getUrl());
		Image profileImage = null;
		if (profile.getImages() != null) {
			for (Image image : profile.getImages()) {
				if (image.getSize().equals("medium")) {
					profileImage = image;
				}
			}
		}

		if (profileImage != null) {
			values.setImageUrl(profileImage.getUrl());
		}

	}

	@Override
	public boolean test(LastFm lastFm) {
		try {
			lastFm.userOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void updateStatus(LastFm soundCloud, String arg1) {

	}

}

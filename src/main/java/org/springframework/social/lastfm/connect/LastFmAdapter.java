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

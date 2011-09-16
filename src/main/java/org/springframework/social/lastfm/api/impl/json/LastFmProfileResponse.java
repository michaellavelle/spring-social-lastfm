package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.LastFmProfile;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmProfileResponse {

	private LastFmProfile lastFmProfile;

	@JsonCreator
	public LastFmProfileResponse(
			@JsonProperty("user") LastFmProfile lastFmProfile) {
		this.lastFmProfile = lastFmProfile;

	}

	public LastFmProfile getLastFmProfile() {
		return lastFmProfile;
	}

}

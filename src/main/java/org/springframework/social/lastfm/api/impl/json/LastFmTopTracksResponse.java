package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmTopTracksResponse {

	private LastFmTracksResponse tracksResponse;

	@JsonCreator
	public LastFmTopTracksResponse(
			@JsonProperty("toptracks") LastFmTracksResponse tracksResponse) {
		this.tracksResponse = tracksResponse;
	}

	public LastFmTracksResponse getTracksResponse() {
		return tracksResponse;
	}
}

package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmLovedTracksResponse {

	private LastFmTracksResponse tracksResponse;

	@JsonCreator
	public LastFmLovedTracksResponse(
			@JsonProperty("lovedtracks") LastFmTracksResponse tracksResponse) {
		this.tracksResponse = tracksResponse;
	}

	public LastFmTracksResponse getTracksResponse() {
		return tracksResponse;
	}
}

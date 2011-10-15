package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmRecentTracksResponse {

	private LastFmSimpleTracksResponse tracksResponse;

	@JsonCreator
	public LastFmRecentTracksResponse(
			@JsonProperty("recenttracks") LastFmSimpleTracksResponse tracksResponse) {
		this.tracksResponse = tracksResponse;
	}

	public LastFmSimpleTracksResponse getTracksResponse() {
		return tracksResponse;
	}
}

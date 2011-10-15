package org.springframework.social.lastfm.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.SimpleTrack;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmSimpleTracksResponse {

	private List<SimpleTrack> tracks;

	@JsonCreator
	public LastFmSimpleTracksResponse(@JsonProperty("track") List<SimpleTrack> tracks) {
		this.tracks = tracks;
	}

	public List<SimpleTrack> getTracks() {
		return tracks;
	}

}

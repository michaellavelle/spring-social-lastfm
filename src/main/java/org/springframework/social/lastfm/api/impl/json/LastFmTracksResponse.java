package org.springframework.social.lastfm.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.Track;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmTracksResponse {

	private List<Track> tracks;

	@JsonCreator
	public LastFmTracksResponse(@JsonProperty("track") List<Track> tracks) {
		this.tracks = tracks;
	}

	public List<Track> getTracks() {
		return tracks;
	}

}

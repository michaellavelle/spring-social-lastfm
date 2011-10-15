package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.SimpleArtist;

/**
 * Annotated mixin to add Jackson annotations to SimpleTrack.
 * 
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class SimpleTrackMixin {

	@JsonCreator
	SimpleTrackMixin(@JsonProperty("url") String url,
			@JsonProperty("name") String name,@JsonProperty("mbid") String musicBrainsId,@JsonProperty("artist") SimpleArtist artist) {
	}

}

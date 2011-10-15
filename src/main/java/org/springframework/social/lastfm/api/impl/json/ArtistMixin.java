package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Annotated mixin to add Jackson annotations to Track.
 * 
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class ArtistMixin {

	@JsonCreator
	ArtistMixin(@JsonProperty("url") String url,
			@JsonProperty("name") String name,@JsonProperty("mbid") String musicBrainsId) {
	}

}

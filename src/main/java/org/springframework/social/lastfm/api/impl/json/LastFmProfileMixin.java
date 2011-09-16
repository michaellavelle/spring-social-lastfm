package org.springframework.social.lastfm.api.impl.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.Image;

/**
 * Annotated mixin to add Jackson annotations to LastFmProfile.
 * 
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class LastFmProfileMixin {

	@JsonCreator
	LastFmProfileMixin(@JsonProperty("id") String id,
			@JsonProperty("name") String name,
			@JsonProperty("realname") String realName,
			@JsonProperty("url") String url,
			@JsonProperty("image") List<Image> images) {
	}

}

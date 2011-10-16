/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.lastfm.api.Artist;
import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.SimpleArtist;
import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.SimpleTrackDescriptor;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackSearchResult;

/**
 * Jackson module for setting up mixin annotations on LastFm model types.
 * This enables the use of Jackson annotations without directly annotating the
 * model classes themselves.
 * 
 * @author Michael Lavelle
 */
public class LastFmModule extends SimpleModule {

	public LastFmModule() {
		super("LastFmModule", new Version(1, 0, 0, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(LastFmProfile.class,
				LastFmProfileMixin.class);
		context.setMixInAnnotations(Track.class, TrackMixin.class);
		context.setMixInAnnotations(SimpleTrack.class, SimpleTrackMixin.class);
		context.setMixInAnnotations(TrackSearchResult.class, TrackSearchResultMixin.class);

		context.setMixInAnnotations(SimpleArtist.class, SimpleArtistMixin.class);

		context.setMixInAnnotations(Artist.class, ArtistMixin.class);

		context.setMixInAnnotations(Image.class, ImageMixin.class);

	}
}

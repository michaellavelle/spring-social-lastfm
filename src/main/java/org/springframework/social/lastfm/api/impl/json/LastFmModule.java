package org.springframework.social.lastfm.api.impl.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.lastfm.api.Artist;
import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.SimpleArtist;
import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.Track;

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
		context.setMixInAnnotations(SimpleArtist.class, SimpleArtistMixin.class);

		context.setMixInAnnotations(Artist.class, ArtistMixin.class);

		context.setMixInAnnotations(Image.class, ImageMixin.class);

	}
}

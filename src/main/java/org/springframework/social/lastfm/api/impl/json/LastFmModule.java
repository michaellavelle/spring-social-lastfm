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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.social.lastfm.api.Album;
import org.springframework.social.lastfm.api.AlbumTrack;
import org.springframework.social.lastfm.api.Artist;
import org.springframework.social.lastfm.api.Image;
import org.springframework.social.lastfm.api.LastFmProfile;
import org.springframework.social.lastfm.api.Playlist;
import org.springframework.social.lastfm.api.Shout;
import org.springframework.social.lastfm.api.SimpleArtist;
import org.springframework.social.lastfm.api.SimpleTrack;
import org.springframework.social.lastfm.api.Track;
import org.springframework.social.lastfm.api.TrackSearchResult;
import org.springframework.social.lastfm.api.impl.json.lists.AlbumTrackListContainer;
import org.springframework.social.lastfm.api.impl.json.lists.PlaylistListContainer;
import org.springframework.social.lastfm.api.impl.json.lists.ShoutListContainer;
import org.springframework.social.lastfm.api.impl.json.lists.SimpleTrackListContainer;
import org.springframework.social.lastfm.api.impl.json.lists.TrackListContainer;
import org.springframework.social.lastfm.api.impl.json.lists.TrackSearchResultListContainer;
import org.springframework.social.lastfm.api.impl.json.lists.UserListContainer;

/**
 * Jackson module for setting up mixin annotations on LastFm model types. This
 * enables the use of Jackson annotations without directly annotating the model
 * classes themselves.
 * 
 * @author Michael Lavelle
 */
public class LastFmModule extends SimpleModule {

	public LastFmModule() {
		super("LastFmModule", new Version(1, 0, 0, null));
	}
	
	public DateFormat getDateFormat()
	{
		return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
	}

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(LastFmProfile.class,
				LastFmProfileMixin.class);
		context.setMixInAnnotations(AlbumTrackListContainer.class, AlbumTrackMixin.class);
		context.setMixInAnnotations(TrackListContainer.class, TrackMixin.class);
		context.setMixInAnnotations(ShoutListContainer.class, ShoutMixin.class);
		context.setMixInAnnotations(UserListContainer.class, LastFmProfileMixin.class);
		context.setMixInAnnotations(PlaylistListContainer.class, PlaylistMixin.class);

		context.setMixInAnnotations(TrackSearchResultListContainer.class, TrackSearchResultMixin.class);

		
		
		context.setMixInAnnotations(SimpleTrackListContainer.class, SimpleTrackMixin.class);

		context.setMixInAnnotations(Track.class, TrackMixin.class);
		context.setMixInAnnotations(AlbumTrack.class, AlbumTrackMixin.class);

		context.setMixInAnnotations(Shout.class, ShoutMixin.class);
		context.setMixInAnnotations(Playlist.class, PlaylistMixin.class);


		context.setMixInAnnotations(SimpleTrack.class, SimpleTrackMixin.class);
		context.setMixInAnnotations(TrackSearchResult.class,
				TrackSearchResultMixin.class);

		context.setMixInAnnotations(SimpleArtist.class, SimpleArtistMixin.class);

		context.setMixInAnnotations(Artist.class, ArtistMixin.class);
		context.setMixInAnnotations(Album.class, AlbumMixin.class);


		context.setMixInAnnotations(Image.class, ImageMixin.class);
	
	
	}
}

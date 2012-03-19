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
package org.springframework.social.lastfm.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Michael Lavelle
 */
public interface TrackOperations {

	public Page<TrackSearchResult> searchByTrackName(String trackName);
	
	public Page<TrackSearchResult> searchByTrackName(String trackName,Pageable pageable);
	
	public Page<TrackSearchResult> searchByArtistAndTrackName(
			String artistName, String trackName);
	
	public Page<TrackSearchResult> searchByArtistAndTrackName(
			String artistName, String trackName,Pageable pageable);
	
	public Page<Track> getSimilarTracks(TrackDescriptor trackDescriptor);
	
	public Page<Track> getSimilarTracks(TrackDescriptor trackDescriptor,int limit);


}

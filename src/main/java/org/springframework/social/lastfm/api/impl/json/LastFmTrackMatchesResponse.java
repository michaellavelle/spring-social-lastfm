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

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.lastfm.api.impl.json.lists.LastFmTrackSearchResultListResponse;
import org.springframework.social.lastfm.api.impl.json.lists.PageInfo;

/**
 * @author Michael Lavelle
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastFmTrackMatchesResponse {

	private LastFmTrackSearchResultListResponse tracksResponse;

	private long totalResults;
	private int itemsPerPage;
	private long startIndex;
	
	
	
	
	public PageInfo getPageInfo() {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPerPage(itemsPerPage);
		pageInfo.setTotal(totalResults);
		pageInfo.setTotalPages(((int)((totalResults - 1)/itemsPerPage)) + 1);
		pageInfo.setPage(((int)(startIndex/itemsPerPage)));

		return pageInfo;
	}
	
	@JsonProperty("opensearch:itemsPerPage")
	public void setPerPage( int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}
	
	@JsonProperty("opensearch:totalResults")
	public void setTotalResults(long totalResults)
	{
		this.totalResults = totalResults;
	}
	
	@JsonProperty("opensearch:startIndex")
	public void setStartIndex(long startIndex)
	{
		this.startIndex = startIndex;
	}
	
	

	@JsonCreator
	public LastFmTrackMatchesResponse(
			@JsonProperty("trackmatches") LastFmTrackSearchResultListResponse tracksResponse) {
		this.tracksResponse = tracksResponse;
	}
	
	

	public LastFmTrackSearchResultListResponse getTracksResponse() {
		return tracksResponse;
	}
}

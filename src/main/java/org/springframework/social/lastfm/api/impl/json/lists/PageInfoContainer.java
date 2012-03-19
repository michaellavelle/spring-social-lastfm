package org.springframework.social.lastfm.api.impl.json.lists;

import org.codehaus.jackson.annotate.JsonProperty;

public class PageInfoContainer {

	private PageInfo pageInfo = new PageInfo();

	@JsonProperty("page")
	public void setPage(int page) {
		pageInfo.setPage(page);
	}
	
	@JsonProperty("total")
	public void setTotal(int total) {
		pageInfo.setTotal(total);
	}
	
	@JsonProperty("totalPages")
	public void setTotalPages(int totalPages) {
		pageInfo.setTotalPages(totalPages);
	}
	
	@JsonProperty("perPage")
	public void setPerPage(int perPage) {
		pageInfo.setPerPage(perPage);
	}
	
	@JsonProperty("@attr")
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	

	public PageInfo getPageInfo()
	{
		return pageInfo;
	}


	
}

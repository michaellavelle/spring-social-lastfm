package org.springframework.social.lastfm.api.impl.json.lists;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageInfo {

	private long total;
	private int page;
	private int perPage;
	private int totalPages;
	
	@JsonCreator
	public PageInfo(@JsonProperty("page") int page,@JsonProperty("perPage") int perPage,@JsonProperty("totalPages") int totalPages,@JsonProperty("total") long total)
	{
		this.total=total;
		this.page = page;
		this.perPage = perPage;
		this.totalPages = totalPages;
	}
	
	public PageInfo()
	{
		
	}
	
	public void setTotal(long total) {
		this.total = total;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPage() {
		return page;
	}

	public int getPerPage() {
		return perPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public long getTotal()
	{
		return total;
	}
	
}

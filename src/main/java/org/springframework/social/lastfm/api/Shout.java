package org.springframework.social.lastfm.api;

import java.util.Date;

/**
 * @author Michael Lavelle
 */
public class Shout {
	
	private String message;
	
	private Date date;
	
	public String getAuthor() {
		return author;
	}
	private String author;
	
	public Shout(String message,Date date,String author)
	{
		this.message = message;
		this.date = date;
		this.author = author;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}

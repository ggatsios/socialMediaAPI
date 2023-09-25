package org.ggatsios.dwproject.socialMediaAPI;

import java.sql.Date;

public class Posts {
	private long id;
	private long userId;
	private String text;
	private Date createdDate;
	
	
	 public Posts() {
	  // No-argument constructor
	}

	public Posts(long userId, String text){
		this.userId = userId;
		this.text = text;
		this.createdDate = new Date(System.currentTimeMillis()); // Set the current date
	}
	
	public long getId() {
	    return id;
	}

	public void setId(long id) {
	    this.id = id;
	}
	
	 public long getUserId() {
	    return userId;
	}

	 public void setUserId(long userId) {
	    this.userId = userId;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}

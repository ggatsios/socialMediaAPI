package org.ggatsios.dwproject.socialMediaAPI;

import java.sql.Date;

public class Comments {
	private long id;
	private long postId;
	private long userId;
	private String text;
	private Date createdDate;
	
	public Comments(long postId, long userId, String text) {
		this.postId = postId;
		this.userId = userId;
		this.createdDate = new Date(System.currentTimeMillis());
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
	 
    public long getPostId() {
    	return postId;
    }
    
    public void setPostId(long postId) {
    	this.postId = postId;
    }
    
	public String getText() {
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}

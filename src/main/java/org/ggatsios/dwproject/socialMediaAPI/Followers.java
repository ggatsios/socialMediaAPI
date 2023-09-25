package org.ggatsios.dwproject.socialMediaAPI;

public class Followers {
	private long id;
	private long followerUserId;
	private long followingUserId;
	
	public Followers(long followerUserId, long followingUserId) {
		this.followerUserId = followerUserId;
		this.followingUserId = followingUserId;
	}
	
	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
	
	public long getFollowerUserId() {
		return followerUserId;
	}
	
	public void setFollowerUserId(long followerUserId){
		this.followerUserId = followerUserId;
	}
	
	public long getFollowingUserId(){
		return followingUserId;
	}
	
	public void setFollowingUserId(long followingUserId){
		this.followingUserId = followingUserId;
	}
}

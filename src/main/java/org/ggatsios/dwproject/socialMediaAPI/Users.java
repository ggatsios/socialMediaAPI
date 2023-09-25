package org.ggatsios.dwproject.socialMediaAPI;

import org.mindrot.jbcrypt.BCrypt; //For password hashing


public class Users {
	private long id;
	private String email;
	private String password; 
	private String planType;
	
	
	public Users() {
		// No-argument constructor
	}
	
	public Users(String email, String password, String planType) {
		this.email = email;
		this.password = password;
		this.planType = planType;
	}
	
	public long getId() {
	    return id;
	}

	public void setId(long id) {
	    this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
	    return password;
	}
	
	
	public String getPlan() {
		return planType;
	}
	
	public void setPlan(String planType) {
		this.planType = planType;
	}
	
	public void hashAndSetPassword(String plainPassword) {
	    String salt = BCrypt.gensalt(); // Generate a salt
	    String hashedPassword = BCrypt.hashpw(plainPassword, salt); // Hash the password
	    this.password = hashedPassword;
	}
	
	public boolean checkPassword(String plainPassword) {
	    // Check if the provided plain password matches the stored hashed password
	    return BCrypt.checkpw(plainPassword, this.password);
	}
}


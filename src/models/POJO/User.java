package models.POJO;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userID;
	private String firstName;
	private String lastName;
	private String email;
	private String passWord;
	private String website;
	private String yahoo;
	private String facebook;
	private String bio;
	private String avatar;
	private byte userLevel;
	private String active;
	private Date registrationDate;

	public User() {
		super();
	}
	
	
	public User(String firstName, String lastName, String email,
			byte userLevel) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userLevel = userLevel;
	}


	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getYahoo() {
		return yahoo;
	}
	public void setYahoo(String yahoo) {
		this.yahoo = yahoo;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public byte getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(byte userLevel) {
		this.userLevel = userLevel;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	
	
}

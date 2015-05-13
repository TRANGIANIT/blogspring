package models.POJO;

import java.io.Serializable;
import java.util.Date;

public class Library implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int imageID;
	private String imageName;
	private int userID;
	private String guid;
	private String mimeType;
	private Date uploadDate;
	
	
	public Library() {
		super();
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
}

package models.POJO;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int postID;
	private int userID;
	private String userName;
	private int catID;
	private String catName;
	private String postName;
	private String content;
	private Date postOn;
	private String postSlug;
	private String illustration;
	//thuoc tinh luu duong dan toi post
	private String catePostStr;
	private String postDesc;
	private int numComments;

	public Post() {
		super();
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getCatID() {
		return catID;
	}

	public void setCatID(int catID) {
		this.catID = catID;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostOn() {
		return postOn;
	}

	public void setPostOn(Date postOn) {
		this.postOn = postOn;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPostSlug() {
		return postSlug;
	}

	public void setPostSlug(String postSlug) {
		this.postSlug = postSlug;
	}

	public String getCatePostStr() {
		return catePostStr;
	}

	public void setCatePostStr(String catePostStr) {
		this.catePostStr = catePostStr;
	}

	public String getIllustration() {
		return illustration;
	}

	public void setIllustration(String illustration) {
		this.illustration = illustration;
	}

	public String getPostDesc() {
		return postDesc;
	}

	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}
}

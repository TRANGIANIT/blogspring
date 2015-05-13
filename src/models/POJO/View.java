package models.POJO;

import java.io.Serializable;

public class View implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int postID;
	private int numViews;
	public View() {
		super();
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public int getNumViews() {
		return numViews;
	}
	public void setNumViews(int numViews) {
		this.numViews = numViews;
	}
	
}

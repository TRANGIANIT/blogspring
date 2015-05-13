package models.POJO;

import java.io.Serializable;

public class Counter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String scope;
	private int num_visits;
	public Counter() {
		super();
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public int getNum_visits() {
		return num_visits;
	}
	public void setNum_visits(int num_visits) {
		this.num_visits = num_visits;
	}
	
}

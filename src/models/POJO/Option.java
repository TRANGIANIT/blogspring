package models.POJO;

import java.io.Serializable;

public class Option implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int optionID;
	private String optionName;
	private String optionValue;
	
	
	public Option() {
		super();
	}
	public int getOptionID() {
		return optionID;
	}
	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
}

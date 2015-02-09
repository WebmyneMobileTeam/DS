package com.wm.beans;

import com.google.gson.annotations.SerializedName;

public class BeanProductDetails {

	@SerializedName("DescriptionID")
	private String mDescriptionID;
	
	@SerializedName("DescriptionLine")
	private String mDescriptionLine;
	
	@SerializedName("ProductID")
	private String mProductID;

	public BeanProductDetails(String mDescriptionID, String mDescriptionLine,
			String mProductID) {
		super();
		this.mDescriptionID = mDescriptionID;
		this.mDescriptionLine = mDescriptionLine;
		this.mProductID = mProductID;
	}

	public String getmDescriptionID() {
		return mDescriptionID;
	}

	public String getmDescriptionLine() {
		return mDescriptionLine;
	}

	public String getmProductID() {
		return mProductID;
	}
	
	
	
}

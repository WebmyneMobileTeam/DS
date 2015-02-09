package com.wm.beans;

import com.google.gson.annotations.SerializedName;

public class BeanProductImages {

	@SerializedName("ImagePath")
	private String mImagePath;
	
	@SerializedName("ImagesTrsID")
	private String mImagesTrsID;
	
	@SerializedName("ProductID")
	private String mProductID;

	public BeanProductImages(String mImagePath, String mImagesTrsID,
			String mProductID) {
		super();
		this.mImagePath = mImagePath;
		this.mImagesTrsID = mImagesTrsID;
		this.mProductID = mProductID;
	}

	public String getmImagePath() {
		return mImagePath;
	}

	public String getmImagesTrsID() {
		return mImagesTrsID;
	}

	public String getmProductID() {
		return mProductID;
	}
	
	
	
}

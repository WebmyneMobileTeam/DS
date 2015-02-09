package com.wm.beans;

import com.google.gson.annotations.SerializedName;


public class BeanCustomerAddress {

	@SerializedName("Address1")
	private String mAddress1;
	
	@SerializedName("Address2")
	private String mAddress2;
	
	@SerializedName("AddressID")
	private String mAddressID;
	
	@SerializedName("City")
	private String mCity;
	
	@SerializedName("Country")
	private String mCountry;
	
	@SerializedName("CustomerID")
	private String mCustomerID;
	
	@SerializedName("NearestLandMark")
	private String mNearestLandMark;
	
	@SerializedName("PinCode")
	private String mPinCode;
	
	@SerializedName("State")
	private String mState;
	
	public BeanCustomerAddress(String mAddress1, String mAddress2,
			String mAddressID, String mCity, String mCountry,
			String mCustomerID, String mNearestLandMark, String mPinCode,
			String mState) {
	    
		super();
		this.mAddress1 = mAddress1;
		this.mAddress2 = mAddress2;
		this.mAddressID = mAddressID;
		this.mCity = mCity;
		this.mCountry = mCountry;
		this.mCustomerID = mCustomerID;
		this.mNearestLandMark = mNearestLandMark;
		this.mPinCode = mPinCode;
		this.mState = mState;
	}

	
	public String getmAddress1() {
		return mAddress1;
	}

	public String getmAddress2() {
		return mAddress2;
	}

	public String getmAddressID() {
		return mAddressID;
	}

	public String getmCity() {
		return mCity;
	}

	public String getmCountry() {
		return mCountry;
	}

	public String getmCustomerID() {
		return mCustomerID;
	}

	public String getmNearestLandMark() {
		return mNearestLandMark;
	}

	public String getmPinCode() {
		return mPinCode;
	}

	public String getmState() {
		return mState;
	}


}

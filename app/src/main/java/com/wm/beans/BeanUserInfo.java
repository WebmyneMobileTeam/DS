package com.wm.beans;

import com.google.gson.annotations.SerializedName;

public class BeanUserInfo {
    

	@SerializedName("City")
	private String mCity;
	
	@SerializedName("CustomerID")
	private String mCustomerID;
	
	@SerializedName("EmailID")
	private String mEmailID;
	
	@SerializedName("FirstName")
	private String mFirstName;
	
	@SerializedName("LastName")
	private String mLastName;
	
	@SerializedName("PhoneNumber")
	private String mPhoneNumber;
	
	@SerializedName("State")
	private String mState;

	public BeanUserInfo(String mCity, String mCustomerID, String mEmailID,
			String mFirstName, String mLastName, String mPhoneNumber,
			String mState) {
	    
		super();
		this.mCity = mCity;
		this.mCustomerID = mCustomerID;
		this.mEmailID = mEmailID;
		this.mFirstName = mFirstName;
		this.mLastName = mLastName;
		this.mPhoneNumber = mPhoneNumber;
		this.mState = mState;
		
		
	}


	
	public String getmCity() {
		return mCity;
	}

	public String getmCustomerID() {
		return mCustomerID;
	}

	public String getmEmailID() {
		return mEmailID;
	}

	public String getmFirstName() {
		return mFirstName;
	}

	public String getmLastName() {
		return mLastName;
	}

	public String getmPhoneNumber() {
		return mPhoneNumber;
	}

	public String getmState() {
		return mState;
	}


	
	

	
	
}

package com.wm.beans;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BeanCustomerInfo {

	@SerializedName("CustAddress")
	private List<BeanCustomerAddress> mCustomerAddresses; 
	
	@SerializedName("UserInfo")
	private BeanUserInfo mUserInfo;

	public List<BeanCustomerAddress> getmCustomerAddresses() {
		return mCustomerAddresses;
	}

	public BeanUserInfo getmUserInfo() {
		return mUserInfo;
	}

	
}

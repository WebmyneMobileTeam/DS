package com.wm.beans;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BeanSubmitOrder {

	@SerializedName("OrderID")
	private String mOrderID;
	
	@SerializedName("OrderDate")
	private String mOrderDate;
	
	@SerializedName("CustAddress")
	private BeanCustomerAddress mCustomerAddress;
	
	@SerializedName("CustomerInfo")
	private BeanUserInfo mCustomerInfo;
	
	@SerializedName("OrderedProducts")
	private List<BeanOrderedProducts>  mOrderedProductsList;

	public BeanSubmitOrder(String mOrderID, String mOrderDate,
			BeanCustomerAddress mCustomerAddress, BeanUserInfo mCustomerInfo,
			List<BeanOrderedProducts> mOrderedProductsList) {
		super();
		this.mOrderID = mOrderID;
		this.mOrderDate = mOrderDate;
		this.mCustomerAddress = mCustomerAddress;
		this.mCustomerInfo = mCustomerInfo;
		this.mOrderedProductsList = mOrderedProductsList;
	}

	public String getmOrderID() {
		return mOrderID;
	}

	public String getmOrderDate() {
		return mOrderDate;
	}

	public BeanCustomerAddress getmCustomerAddress() {
		return mCustomerAddress;
	}

	public BeanUserInfo getmCustomerInfo() {
		return mCustomerInfo;
	}

	public List<BeanOrderedProducts> getmOrderedProductsList() {
		return mOrderedProductsList;
	}
	
	
}

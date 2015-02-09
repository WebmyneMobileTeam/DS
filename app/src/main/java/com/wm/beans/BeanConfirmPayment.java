package com.wm.beans;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BeanConfirmPayment {

	@SerializedName("AddressID")
	private String mAddressID;

	@SerializedName("IsPaymentComplete")
	private String mIsPaymentComplete;

	@SerializedName("OrderDate")
	private String mOrderDate;

	@SerializedName("OrderID")
	private String mOrderID;

	@SerializedName("PaymentTransactionID")
	private String mPaymentTransactionID;

	@SerializedName("CustAddress")
	private BeanCustomerAddress mCustomerAddress;
	
	@SerializedName("CustomerInfo")
	private BeanUserInfo mCustomerInfo;
	
	@SerializedName("OrderedProducts")
	private List<BeanOrderedProducts>  mOrderedProductsList;
	
	
	public BeanConfirmPayment(String mAddressID, String mIsPaymentComplete,
			String mOrderDate, String mOrderID, String mPaymentTransactionID,
			BeanCustomerAddress mCustomerAddress, BeanUserInfo mCustomerInfo,
			List<BeanOrderedProducts> mOrderedProductsList) {
	    
		super();
		
		this.mAddressID = mAddressID;
		this.mIsPaymentComplete = mIsPaymentComplete;
		this.mOrderDate = mOrderDate;
		this.mOrderID = mOrderID;
		this.mPaymentTransactionID = mPaymentTransactionID;
		this.mCustomerAddress = mCustomerAddress;
		this.mCustomerInfo = mCustomerInfo;
		this.mOrderedProductsList = mOrderedProductsList;
	}
	public String getmAddressID() {
		return mAddressID;
	}

	public String getmIsPaymentComplete() {
		return mIsPaymentComplete;
	}


	public String getmOrderDate() {
		return mOrderDate;
	}


	public String getmOrderID() {
		return mOrderID;
	}


	public String getmPaymentTransactionID() {
		return mPaymentTransactionID;
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

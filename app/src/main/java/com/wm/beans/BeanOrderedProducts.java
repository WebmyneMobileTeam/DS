package com.wm.beans;

import com.google.gson.annotations.SerializedName;

public class BeanOrderedProducts {

	@SerializedName("OrderID")
	private String mOrderID;
	
	@SerializedName("OrderproductID")
	private String mOrderproductID;
	
	@SerializedName("ProductID")
	private String mProductID;
	
	@SerializedName("Quantity")
	private String mQuantity;
	
	@SerializedName("TotalPrice")
	private String mTotalPrice;
	
	@SerializedName("UnitPrice")
	private String mUnitPrice;

	public BeanOrderedProducts(String mOrderID, String mOrderproductID,
			String mProductID, String mQuantity, String mTotalPrice,
			String mUnitPrice) {
		super();
		this.mOrderID = mOrderID;
		this.mOrderproductID = mOrderproductID;
		this.mProductID = mProductID;
		this.mQuantity = mQuantity;
		this.mTotalPrice = mTotalPrice;
		this.mUnitPrice = mUnitPrice;
	}

	public String getmOrderID() {
		return mOrderID;
	}

	public String getmOrderproductID() {
		return mOrderproductID;
	}

	public String getmProductID() {
		return mProductID;
	}

	public String getmQuantity() {
		return mQuantity;
	}

	public String getmTotalPrice() {
		return mTotalPrice;
	}

	public String getmUnitPrice() {
		return mUnitPrice;
	}
	

}

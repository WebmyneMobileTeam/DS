package com.wm.beans;

import com.google.gson.annotations.SerializedName;

public class BeanProduct {

    @SerializedName("Category")
    private String mCategory;

    @SerializedName("CompanyName")
    private String mCompanyName;

    @SerializedName("CreationDate")
    private String mCreationDate;

    @SerializedName("ExpiryDate")
    private String mExpiryDate;

    @SerializedName("IsPublished")
    private String mIsPublished;

    @SerializedName("MfgDate")
    private String mMfgDate;

    @SerializedName("Name")
    private String mName;

    @SerializedName("ProductID")
    private String mProductID;

    @SerializedName("Thumbnail")
    private String mThumbnail;

    @SerializedName("UnitPrice")
    private String mUnitPrice;

//    public BeanProduct(String mCategory, String mCompanyName,
//	    String mCreationDate, String mExpiryDate, String mIsPublished,
//	    String mMfgDate, String mName, String mProductID,
//	    String mThumbnail, String mUnitPrice) {
//	super();
//	this.mCategory = mCategory;
//	this.mCompanyName = mCompanyName;
//	this.mCreationDate = mCreationDate;
//	this.mExpiryDate = mExpiryDate;
//	this.mIsPublished = mIsPublished;
//	this.mMfgDate = mMfgDate;
//	this.mName = mName;
//	this.mProductID = mProductID;
//	this.mThumbnail = mThumbnail;
//	this.mUnitPrice = mUnitPrice;
//    }
    
    public BeanProduct(String mCategory, String mIsPublished,String mName, String mProductID,
    	    String mThumbnail, String mUnitPrice) {
    	super();
    	this.mCategory = mCategory;
  
    	this.mIsPublished = mIsPublished;

    	this.mName = mName;
    	this.mProductID = mProductID;
    	this.mThumbnail = mThumbnail;
    	this.mUnitPrice = mUnitPrice;
        }

    public String getmCategory() {
	return mCategory;
    }

    public String getmCompanyName() {
	return mCompanyName;
    }

    public String getmCreationDate() {
	return mCreationDate;
    }

    public String getmExpiryDate() {
	return mExpiryDate;
    }

    public String getmIsPublished() {
	return mIsPublished;
    }

    public String getmMfgDate() {
	return mMfgDate;
    }

    public String getmName() {
	return mName;
    }

    public String getmProductID() {
	return mProductID;
    }

    public String getmThumbnail() {
	return mThumbnail;
    }

    public String getmUnitPrice() {
	return mUnitPrice;
    }

}

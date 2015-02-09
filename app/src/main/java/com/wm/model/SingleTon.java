package com.wm.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.support.v7.app.ActionBar;

import com.wm.beans.BeanCustomerAddress;
import com.wm.beans.BeanOrderedProducts;
import com.wm.beans.BeanProduct;
import com.wm.beans.BeanProductDetails;
import com.wm.beans.BeanProductImages;
import com.wm.beans.BeanUserInfo;

public class SingleTon {

    private static SingleTon s = new SingleTon();

    
    public String my = new String();

    public ActionBar.LayoutParams acBarParams = new ActionBar.LayoutParams(
	    ActionBar.LayoutParams.WRAP_CONTENT,
	    ActionBar.LayoutParams.WRAP_CONTENT);

    public ArrayList<String> categories = new ArrayList<String>();

    public ArrayList<String> sliderOptions = new ArrayList<String>();

    public ArrayList<BeanProduct> productData = new ArrayList<BeanProduct>();

    public ArrayList<BeanProductDetails> productDetails = new ArrayList<BeanProductDetails>();

    public ArrayList<BeanProductImages> productImages = new ArrayList<BeanProductImages>();

    public BeanProduct selected_product_bean = null;

    // TODO changes
    public Set<String> productDataArray = new HashSet<String>();
    public Set<String> productDetailsArray = new HashSet<String>();
    public Set<String> productImagesArray = new HashSet<String>();

    public ArrayList<BeanProduct> favProductData = new ArrayList<BeanProduct>();

    // For Customer Information
    public List<BeanCustomerAddress> beanCustomerAddressListForCustomerInfo = new ArrayList<BeanCustomerAddress>();
    public BeanUserInfo beanUSerInfoForCustomerInfo;
    //

    // for Submit Order
    public List<BeanOrderedProducts> beanOrderedProdutsListForSubmitOrder = new ArrayList<BeanOrderedProducts>();
    public BeanOrderedProducts beanOrderedProductForSubmitOrder;
    public BeanCustomerAddress beanCustomerAddressForSubmitOrder;
    public BeanUserInfo beanUSerInfoForSubmitOrder;

    //
    
    //for confirm payment
	public List<BeanOrderedProducts> beanOrderedProdutsListForConfirmPayment=new ArrayList<BeanOrderedProducts>();
	public BeanCustomerAddress beanCustomerAddressForConfirmPayment;
	public BeanUserInfo beanUSerInfoForConfirmPayment;
	
	//
    private SingleTon() {
    }

    public static SingleTon getInstance() {

	return s;

    }

}

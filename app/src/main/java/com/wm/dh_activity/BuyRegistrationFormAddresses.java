package com.wm.dh_activity;

import java.io.Reader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanCustomerAddress;
import com.wm.beans.BeanOrderedProducts;
import com.wm.customviews.CustomButton;
import com.wm.customviews.CustomEdittext;
import com.wm.customviews.CustomTextview;
import com.wm.model.API;
import com.wm.model.AppColors;
import com.wm.model.AppConstants;
import com.wm.model.SingleTon;

public class BuyRegistrationFormAddresses extends ActionBarActivity implements OnClickListener{

    private static String CHARSET = "UTF-8";
	private Reader reader = null;
	private Dialog dialog,addressDialog;
	private CustomButton btnBuyRegistrationUserInfoNext;
	private CustomTextview tvAddNewAddress,tvChageAddress, tvTotalPrice;
	private CustomEdittext etAddress1,etCity,etState,etCountry,etPinCode,etQuantity; //etAddress2,etLandMark,
	private double productPrice;
	public static int quantityOfProduct;
	public static String orderId=new String();
    public static String PRICEUNIT = new String();
    DecimalFormat df = new DecimalFormat("#.00"); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_registration_detail_form_addresses);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setupActionBar();
		AppConstants.ADDRESS_TYPE = false; // Set All EditText Non Editable 
		if(AppConstants.USER_TYPE==true) {  // Show Address List Dialog if user is registered
			disdisplayAddressDialog();
		}
		btnBuyRegistrationUserInfoNext=(CustomButton) findViewById(R.id.btn_buy_registration_form_addresses_submit);
		tvAddNewAddress=(CustomTextview) findViewById(R.id.add_new_address);
		tvChageAddress=(CustomTextview) findViewById(R.id.change_address);
		tvTotalPrice=(CustomTextview) findViewById(R.id.total_price);
		etAddress1=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_address_one);
//		etAddress2=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_address_two);
//		etLandMark=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_landmark);
		etCity=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_city);
		etState=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_state);
		etCountry=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_country);
		etPinCode=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_addresses_pincode);
		etQuantity=(CustomEdittext) findViewById(R.id.et_quantity);
		productPrice= Double.parseDouble(SingleTon.getInstance().selected_product_bean.getmUnitPrice());
		
		quantityOfProduct = Integer.parseInt(etQuantity.getText().toString().trim());
		Log.e("product quantity:",Integer.toString(quantityOfProduct));
		double finalPrice = productPrice * quantityOfProduct;
		PRICEUNIT = ""+finalPrice;
		Log.e("final price:",finalPrice+"");
		
		String priceToPrint = df.format(finalPrice).toString();	
		Log.e("final price:",finalPrice+"");
		PRICEUNIT = ""+finalPrice;
		tvTotalPrice.setText(" "+priceToPrint.replace(".",","));
		
	
	
		etQuantity.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			    
				if(etQuantity.getText().toString().trim().equalsIgnoreCase("")) {
					tvTotalPrice.setText("");
				}
				    else {
			
					Log.e("product price:",productPrice+"");
					
					quantityOfProduct = Integer.parseInt(etQuantity.getText().toString().trim());
					Log.e("product quantity:",Integer.toString(quantityOfProduct));
					double finalPrice = productPrice * quantityOfProduct;
					
	
					String priceToPrint = df.format(finalPrice).toString();	
					Log.e("final price:",finalPrice+"");
					PRICEUNIT = ""+finalPrice;
					tvTotalPrice.setText(" "+priceToPrint.replace(".",","));
				}
		

				
			}
		});
		btnBuyRegistrationUserInfoNext.setOnClickListener(this);
		tvAddNewAddress.setOnClickListener(this);
		tvChageAddress.setOnClickListener(this);

		if(AppConstants.USER_TYPE==true) {  // Visible "Add Address" and "Change Address" if User is registered
			tvAddNewAddress.setVisibility(View.VISIBLE);
			tvChageAddress.setVisibility(View.VISIBLE);

			etAddress1.setEnabled(false);
//			etAddress2.setEnabled(false);
//			etLandMark.setEnabled(false);
			etCity.setEnabled(false);
			etState.setEnabled(false);
			etCountry.setEnabled(false);
			etPinCode.setEnabled(false);
		} else {
			tvAddNewAddress.setVisibility(View.GONE);
			tvChageAddress.setVisibility(View.GONE);
		}
	}

	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(AppColors.APP_THEME_COLOR));
		actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));
		SingleTon.getInstance().acBarParams.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
		actionBar.setDisplayShowCustomEnabled(true);
		CustomTextview tv = new CustomTextview(BuyRegistrationFormAddresses.this, null);
		tv.setText("SHIPPING INFORMATION");
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		actionBar.setCustomView(tv, SingleTon.getInstance().acBarParams);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.btn_buy_registration_form_addresses_submit:
			if(checkMandatoryFields()==true) {
				if(AppConstants.USER_TYPE==false  ) { // For New User
					SingleTon.getInstance().beanCustomerAddressForSubmitOrder=new BeanCustomerAddress(etAddress1.getText().toString().trim(), "", "0", etCity.getText().toString().trim(), etCountry.getText().toString().trim(), "0", "", etPinCode.getText().toString().trim(), etState.getText().toString().trim());
				} else if(AppConstants.ADDRESS_TYPE==true) { // For Registered User -- add new address
					SingleTon.getInstance().beanCustomerAddressForSubmitOrder=new BeanCustomerAddress(etAddress1.getText().toString().trim(), "", "0", etCity.getText().toString().trim(), etCountry.getText().toString().trim(), SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCustomerID(), "", etPinCode.getText().toString().trim(), etState.getText().toString().trim());
				}
				else {		// For Registered User -- with old address
					SingleTon.getInstance().beanCustomerAddressForSubmitOrder=new BeanCustomerAddress(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmAddress1(),SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmAddress2(),SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmAddressID(), SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCity(), SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCountry(), SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCustomerID(),SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmNearestLandMark(), SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmPinCode(),SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmState());
				}
				new BackgroundTaskForSubmitOrder().execute();
			}
			break;

		case R.id.add_new_address:
			// Clear all EditText
			etAddress1.setText("");
//			etAddress2.setText("");
//			etLandMark.setText("");
			etCity.setText("");
			etState.setText("");
			etCountry.setText("");
			etPinCode.setText("");

			// Enable All EditText
			etAddress1.setEnabled(true);
//			etAddress2.setEnabled(true);
//			etLandMark.setEnabled(true);
			etCity.setEnabled(true);
			etState.setEnabled(true);
			etCountry.setEnabled(true);
			etPinCode.setEnabled(true);
			AppConstants.ADDRESS_TYPE=true; // New Address 
			break;

		case R.id.change_address:

			AppConstants.ADDRESS_TYPE=false; // Old Address
			// Disable All EditText
			etAddress1.setEnabled(false);
//			etAddress2.setEnabled(false);
//			etLandMark.setEnabled(false);
			etCity.setEnabled(false);
			etState.setEnabled(false);
			etCountry.setEnabled(false);
			etPinCode.setEnabled(false);
			disdisplayAddressDialog();
			break;

		}
	}

	// For Empty Field Validation
	private boolean checkMandatoryFields() {
		boolean check = false;
		ArrayList<EditText> EditTextList = new ArrayList<EditText>();
		try {
			EditTextList.clear();
		} catch (Exception e) {
		}
		EditTextList.add(etAddress1);
//		EditTextList.add(etLandMark);
		EditTextList.add(etCity);
		EditTextList.add(etState);
		EditTextList.add(etCountry);
		EditTextList.add(etPinCode);
		EditTextList.add(etQuantity);
		
		for (EditText editText : EditTextList) {
		    
			if (editText.getText().toString().equalsIgnoreCase("")) {
				check = false;
				editText.requestFocus();
				editText.setError("Please Enter " + editText.getHint());
				break;
			}else if(etQuantity.getText().toString().equalsIgnoreCase("0")) {
			    
			    check = false;
				editText.requestFocus();
				editText.setError("Quantity should not be zero");

				break;
			    
			}
			
			else {
				check = true;
			}
		}
		return check;
	}

	// Display Address List Dialog 
	private void disdisplayAddressDialog() {
		
		AlertDialog.Builder alert = new Builder(BuyRegistrationFormAddresses.this);
		alert.setTitle("Select Address");
		String msg = "";
		alert.setMessage(msg);
		alert.setCancelable(false);
		final ListView lvAllAddresses = new ListView(BuyRegistrationFormAddresses.this);
		lvAllAddresses.setAdapter(new AddressAdapter(BuyRegistrationFormAddresses.this, SingleTon.getInstance().beanCustomerAddressListForCustomerInfo));
		//TODO
		lvAllAddresses.setDivider(new ColorDrawable(Color.GRAY));
		lvAllAddresses.setDividerHeight(1);
		alert.setView(lvAllAddresses);
		addressDialog = alert.create();
		addressDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
		addressDialog.show();
	}

	// Custom Adapter for Address List Dialog
	public class AddressAdapter extends BaseAdapter
	{
		Context context;
		List<BeanCustomerAddress> beanCustomerAddressListForCustomerInfo;
		LayoutInflater inflater;

		public AddressAdapter(BuyRegistrationFormAddresses buyRegistrationFormAddresses, List<BeanCustomerAddress> beanCustomerAddressListForCustomerInfo) {
			this.context = buyRegistrationFormAddresses;
			this.beanCustomerAddressListForCustomerInfo = beanCustomerAddressListForCustomerInfo;
		}

		public class ViewHolder {
			CustomTextview txtAddress1,txtAddress2,txtLandMark,txtCity,txtState,txtCountry,txtPincode;
		}

		public int getCount() {
			return beanCustomerAddressListForCustomerInfo.size();
		}

		public Object getItem(int position) {
			return beanCustomerAddressListForCustomerInfo.get(position);
		}

		public long getItemId(int position) {
			return beanCustomerAddressListForCustomerInfo.indexOf(getItem(position));
		}

		public View getView(final int position, View convertView, ViewGroup parent)
		{
			final ViewHolder holder;
			LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_address, null);
				holder = new ViewHolder();
				holder.txtAddress1 = (CustomTextview) convertView.findViewById(R.id.txt_address1);
				holder.txtAddress2 = (CustomTextview) convertView.findViewById(R.id.txt_address2);
				holder.txtLandMark = (CustomTextview) convertView.findViewById(R.id.txt_landmark);
				holder.txtCity = (CustomTextview) convertView.findViewById(R.id.txt_city);
				holder.txtState = (CustomTextview) convertView.findViewById(R.id.txt_state);
				holder.txtCountry = (CustomTextview) convertView.findViewById(R.id.txt_country);
				holder.txtPincode = (CustomTextview) convertView.findViewById(R.id.txt_pincode);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
            try{


			//TODO

			holder.txtAddress1.setText(URLDecoder.decode(beanCustomerAddressListForCustomerInfo.get(position).getmAddress1().toString().trim(), "UTF-8")); //+","+beanCustomerAddressListForCustomerInfo.get(position).getmAddress2().toString().trim()
//			if(beanCustomerAddressListForCustomerInfo.get(position).getmAddress2().equalsIgnoreCase("")){
//				holder.txtAddress2.setVisibility(View.GONE);
//			} else {
//				holder.txtAddress2.setVisibility(View.VISIBLE);
//				holder.txtAddress2.setText(beanCustomerAddressListForCustomerInfo.get(position).getmAddress2());
//			}
			holder.txtLandMark.setText(URLDecoder.decode(beanCustomerAddressListForCustomerInfo.get(position).getmNearestLandMark().toString().trim(), "UTF-8"));
			holder.txtCity.setText(URLDecoder.decode(beanCustomerAddressListForCustomerInfo.get(position).getmCity().toString().trim()+",",CHARSET));
			holder.txtState.setText(URLDecoder.decode(beanCustomerAddressListForCustomerInfo.get(position).getmState().toString().trim(), CHARSET));
			holder.txtCountry.setText(URLDecoder.decode(beanCustomerAddressListForCustomerInfo.get(position).getmCountry().toString().trim()+",",CHARSET));
			holder.txtPincode.setText(URLDecoder.decode(beanCustomerAddressListForCustomerInfo.get(position).getmPinCode().toString().trim(), CHARSET));

            }catch(Exception e){}

            convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					        SingleTon.getInstance().beanCustomerAddressForSubmitOrder = new BeanCustomerAddress(
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmAddress1(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmAddress2(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmAddressID(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmCity(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmCountry(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmCustomerID(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmNearestLandMark(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmPinCode(),
							SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmState());

                    try {

                        etAddress1.setText(URLDecoder.decode(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmAddress1().toString().trim() + "", CHARSET));
//					etAddress2.setText(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmAddress2().toString().trim()+"");
//					etLandMark.setText(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmNearestLandMark().toString().trim()+"");
                        etCity.setText(URLDecoder.decode(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmCity().toString().trim() + "",CHARSET));
                        etState.setText(URLDecoder.decode(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmState().toString().trim() + "",CHARSET));
                        etCountry.setText(URLDecoder.decode(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmCountry().toString().trim() + "",CHARSET));
                        etPinCode.setText(URLDecoder.decode(SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(position).getmPinCode().toString().trim() + "",CHARSET));
                        addressDialog.dismiss();

                    }catch(Exception e){}

				}
			});
			return convertView;
		}
	}

	// AsyncTask For Submit Order
	private class BackgroundTaskForSubmitOrder extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new Dialog(BuyRegistrationFormAddresses.this,android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setContentView(R.layout.loading_bottom);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
                String str = new String();
				// shipping address

                String ss = SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmAddress1().toString();

				JsonObject customerAddressObject=new JsonObject();
				customerAddressObject.addProperty("Address1",URLEncoder.encode(ss,"UTF-8"));
				customerAddressObject.addProperty("Address2",URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmAddress2().toString(),CHARSET));
				customerAddressObject.addProperty("AddressID",URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmAddressID().toString(),CHARSET));
				customerAddressObject.addProperty("City", URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCity().toString(),CHARSET));
				customerAddressObject.addProperty("Country", URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCountry().toString(),CHARSET));
				customerAddressObject.addProperty("CustomerID", URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmCustomerID().toString(),CHARSET));
				customerAddressObject.addProperty("NearestLandMark", URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmNearestLandMark().toString(),CHARSET));
				customerAddressObject.addProperty("PinCode", URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmPinCode().toString(),CHARSET));
				customerAddressObject.addProperty("State", URLEncoder.encode(SingleTon.getInstance().beanCustomerAddressForSubmitOrder.getmState().toString(),CHARSET));
				Log.e("customerAddressObject",customerAddressObject+"");


				// user information
				JsonObject customerInfoObject=new JsonObject();
				customerInfoObject.addProperty("City",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmCity().toString(),CHARSET));
				customerInfoObject.addProperty("EmailID",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmEmailID().toString(),CHARSET));
				customerInfoObject.addProperty("CustomerID",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmCustomerID().toString(),CHARSET));
				customerInfoObject.addProperty("FirstName",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmFirstName().toString(),CHARSET));
				customerInfoObject.addProperty("LastName",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmLastName().toString(),CHARSET));
				customerInfoObject.addProperty("PhoneNumber",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmPhoneNumber().toString(),CHARSET));
				customerInfoObject.addProperty("State",  URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForSubmitOrder.getmState().toString(),CHARSET));
				Log.e("customerInfoObject",customerInfoObject+"");

				// ordered products List
				SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.clear();
				SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.add(new BeanOrderedProducts("0", "0", SingleTon.getInstance().selected_product_bean.getmProductID(),etQuantity.getText().toString().trim(),tvTotalPrice.getText().toString(), SingleTon.getInstance().selected_product_bean.getmUnitPrice()));

				JsonArray jsonOrderedProductList=new JsonArray();
				for(int i=0;i<SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.size();i++) {
					JsonObject orderedProduct=new JsonObject();
					orderedProduct.addProperty("OrderID",SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.get(i).getmOrderID().toString());
					orderedProduct.addProperty("OrderproductID", SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.get(i).getmOrderproductID().toString());
					orderedProduct.addProperty("ProductID",SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.get(i).getmProductID().toString());
					orderedProduct.addProperty("Quantity", SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.get(i).getmQuantity().toString());
					orderedProduct.addProperty("TotalPrice", SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.get(i).getmTotalPrice().replace(",",".").toString());
					orderedProduct.addProperty("UnitPrice", SingleTon.getInstance().beanOrderedProdutsListForSubmitOrder.get(i).getmUnitPrice().toString());
					jsonOrderedProductList.add(orderedProduct);
				}
				Log.e(".........",jsonOrderedProductList+"");

				// Get Current Date
				Date cDate = new Date();
				String fDate = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
				Log.e("current date",fDate+"");

				// Create Json object for post
				JsonObject jsonOrderSubmit=new JsonObject();
				jsonOrderSubmit.add("CustAddress", customerAddressObject);
				jsonOrderSubmit.add("CustomerInfo", customerInfoObject);
				jsonOrderSubmit.add("OrderedProducts", jsonOrderedProductList);
				jsonOrderSubmit.addProperty("OrderID", "0");
				jsonOrderSubmit.addProperty("OrderDate", fDate+"");
				Log.e("gh",jsonOrderSubmit+"");

				reader= API.callWebservicePost(AppConstants.URL_SUBMITORDER,jsonOrderSubmit.toString());
				Log.e("reader",reader+"");

				// Get Response in String
				StringBuffer response=new StringBuffer();
				int i = 0;               
				do {
					i = reader.read();
					char character = (char) i;
					response.append(character);

				} while (i != -1); 
				reader.close();
				Log.e("response",response+" ");
				
				orderId=response.substring(1, response.length()-2);
				Log.e("order id",orderId+"");
				handlePostsList(reader);
				
			} catch (JsonSyntaxException e) {
				Log.e("JsonSyntaxException","");
				e.printStackTrace();
			} catch (JsonIOException e) {
				Log.e("JsonIOException",e+"");
				e.printStackTrace();
			} catch (Exception e) {
				Log.e("Exception",e+"");
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
	} // End of AsyncTask

	private void handlePostsList(final Reader reader) {
		runOnUiThread(new Runnable() {
			Intent intent=null;
			public void run() {
				try {
					intent= new Intent(BuyRegistrationFormAddresses.this, Payment.class);
					startActivity(intent);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JsonIOException e) {
					e.printStackTrace();
				}
			}
		});
	}




}

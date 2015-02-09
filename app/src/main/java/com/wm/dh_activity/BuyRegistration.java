package com.wm.dh_activity;

import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanCustomerInfo;
import com.wm.beans.BeanUserInfo;
import com.wm.customviews.CustomButton;
import com.wm.customviews.CustomEdittext;
import com.wm.customviews.CustomTextview;
import com.wm.model.AppColors;
import com.wm.model.AppConstants;
import com.wm.model.MyApplication;
import com.wm.model.SingleTon;

public class BuyRegistration extends ActionBarActivity implements OnClickListener{

	private Reader reader = null;
	private Dialog dialog;
	private CustomEdittext etFirstName,etLastName,etEmail;
	private CustomButton btnSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_registration);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setupActionBar();

		etFirstName=(CustomEdittext) findViewById(R.id.buy_registration_first_name);
		etLastName=(CustomEdittext) findViewById(R.id.buy_registration_last_name);
		etEmail=(CustomEdittext) findViewById(R.id.buy_registration_email);
		btnSubmit=(CustomButton) findViewById(R.id.buy_registration_submit);
		btnSubmit.setOnClickListener(this);
		
	
	}

	private void setupActionBar() {
	    
		  
				ActionBar actionBar = getSupportActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				actionBar.setDisplayShowTitleEnabled(false);
				actionBar.setBackgroundDrawable(new ColorDrawable(AppColors.APP_THEME_COLOR));
				actionBar.setLogo(new ColorDrawable(Color.TRANSPARENT));
				SingleTon.getInstance().acBarParams.gravity = Gravity.CENTER | Gravity.CENTER_VERTICAL;
				actionBar.setDisplayShowCustomEnabled(true);
				CustomTextview tv = new CustomTextview(BuyRegistration.this, null);
				tv.setText("USER INFORMATION");
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

		if(v.getId()==R.id.buy_registration_submit){
			if(checkMandatoryFields()==true) {
				callTaskForCustomerInfo();
			}
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
		
		EditTextList.add(etFirstName);
		EditTextList.add(etEmail);
		
		for (EditText editText : EditTextList) {
			if (editText.getText().toString().equalsIgnoreCase("")) {
				check = false;
				editText.requestFocus();
				editText.setError("Please Enter " + editText.getHint());
				break;
			} else if(editText.getId()==R.id.buy_registration_email) {
				boolean isValid=validateEmail(editText.getText().toString().trim());
				if(isValid==false){
					check = false;
					editText.requestFocus();
					editText.setError("Please Enter Valid Email");
					break;
				}
			}
			else {
				check = true;
			}
		}
		return check;
	}

	// For Email Validation
	private boolean validateEmail(String email) {
		
	
		Pattern pattern;
		Matcher matcher;
		String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	
	private void callTaskForCustomerInfo(){
		
		try {
			
			final Dialog dialog;
			dialog = new Dialog(BuyRegistration.this,android.R.style.Theme_Translucent_NoTitleBar);
			dialog.setContentView(R.layout.loading_bottom);
			dialog.show();
			
			JSONObject jsonObjectForCustomerInfo=new JSONObject();
			jsonObjectForCustomerInfo.put("FirstName", etFirstName.getText().toString().trim());
			jsonObjectForCustomerInfo.put("LastName", etLastName.getText().toString().trim());
			jsonObjectForCustomerInfo.put("EmailID", etEmail.getText().toString().trim());
			
			JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,AppConstants.URL_GETCUSTOMERINFO,jsonObjectForCustomerInfo, new Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject jobj) {
					// TODO Auto-generated method stub
					
					dialog.dismiss();
					String res = jobj.toString();
					handlePostsList(res);
					
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			
			MyApplication.getInstance().addToRequestQueue(req);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void handlePostsList(final String reader) {
	
		runOnUiThread(new Runnable() {
			public void run() {
				
				try {
					
					Intent intent=null;
					BeanCustomerInfo beanCustomerInfo =new GsonBuilder().create().fromJson(reader, BeanCustomerInfo.class);	
					SingleTon.getInstance().beanCustomerAddressListForCustomerInfo=beanCustomerInfo.getmCustomerAddresses();
					SingleTon.getInstance().beanUSerInfoForCustomerInfo=beanCustomerInfo.getmUserInfo();
					if(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmCustomerID().equalsIgnoreCase("0")) {
						Log.e("New Registration","................");
						AppConstants.USER_TYPE=false;
						SingleTon.getInstance().beanUSerInfoForCustomerInfo=new BeanUserInfo("", "",etEmail.getText().toString().trim(), etFirstName.getText().toString().trim(), etLastName.getText().toString().trim(), "", "");
					} else {
						Log.e("Already Registered","................");
						AppConstants.USER_TYPE=true;
						Log.e("address list size:",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.size()+"");
						
						for(int i=0;i<SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.size();i++) {
							
							Log.e("Address1: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmAddress1()+" ");
							Log.e("Address2: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmAddress2()+" ");
							Log.e("address id: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmAddressID()+" ");
							Log.e("City: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmCity()+" ");
							Log.e("Country: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmCountry()+" ");
							Log.e("CustomerID: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmCustomerID()+" ");
							Log.e("NearestLandMark: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmNearestLandMark()+" ");
							Log.e("PinCode: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmPinCode()+" ");
							Log.e("State: ",SingleTon.getInstance().beanCustomerAddressListForCustomerInfo.get(i).getmState()+" ");
						}
						
						Log.e("Customer Id",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmCustomerID()+" ");
						Log.e("City",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmCity()+" ");
						Log.e("EmailID",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmEmailID()+" ");
						Log.e("FirstName",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmFirstName()+" ");
						Log.e("LastName",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmLastName()+" ");
						Log.e("PhoneNumber",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmPhoneNumber()+" ");
						Log.e("State",SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmState()+" ");
						
					}
					
				
					intent= new Intent(BuyRegistration.this, BuyRegistrationFormUserInfo.class);
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

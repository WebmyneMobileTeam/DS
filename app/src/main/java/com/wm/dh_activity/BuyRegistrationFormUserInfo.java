package com.wm.dh_activity;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;

import com.webmyne.daryabherbarium.R;
import com.wm.beans.BeanUserInfo;
import com.wm.customviews.CustomButton;
import com.wm.customviews.CustomEdittext;
import com.wm.customviews.CustomTextview;
import com.wm.model.AppColors;
import com.wm.model.AppConstants;
import com.wm.model.SingleTon;

public class BuyRegistrationFormUserInfo extends ActionBarActivity implements OnClickListener{
    private static String CHARSET = "UTF-8";
	private CustomButton btnBuyRegistrationUserInfoNext;
	private CustomEdittext etFirstName,etLastName,etEmail,etMobile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_registration_detail_form_user_info);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setupActionBar();

		btnBuyRegistrationUserInfoNext=(CustomButton) findViewById(R.id.btn_buy_registration_form_user_info_submit);
		etFirstName=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_user_info_first_name);
		etLastName=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_user_info_last_name);
		etEmail=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_user_info_email);
		etMobile=(CustomEdittext) findViewById(R.id.txt_buy_registration_form_user_info_mobile);
		btnBuyRegistrationUserInfoNext.setOnClickListener(this);

        try {
            etFirstName.setText(URLDecoder.decode(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmFirstName(), CHARSET));
            etLastName.setText(URLDecoder.decode(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmLastName(), CHARSET));
            etEmail.setText(URLDecoder.decode(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmEmailID(), CHARSET));
            etMobile.setText(URLDecoder.decode(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmPhoneNumber(), CHARSET));
        }catch(Exception e){}

		if(AppConstants.USER_TYPE==true) {
			etFirstName.setEnabled(false);
			etLastName.setEnabled(false);
			etEmail.setEnabled(false);
			etMobile.setEnabled(false);
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
		CustomTextview tv = new CustomTextview(BuyRegistrationFormUserInfo.this, null);
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
		try {
            if (v.getId() == R.id.btn_buy_registration_form_user_info_submit) {
                if (checkMandatoryFields() == true) { // check validation
                    if (AppConstants.USER_TYPE == false) { // for new user
                        SingleTon.getInstance().beanUSerInfoForSubmitOrder = new BeanUserInfo("city", "0", etEmail.getText().toString().trim(), URLEncoder.encode(etFirstName.getText().toString().trim(), CHARSET), URLEncoder.encode(etLastName.getText().toString().trim(),CHARSET), etMobile.getText().toString().trim(), "state");
                    } else { // for registered user
                        SingleTon.getInstance().beanUSerInfoForSubmitOrder = new BeanUserInfo("city", SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmCustomerID(), SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmEmailID(), URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmFirstName(),CHARSET),URLEncoder.encode(SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmLastName(),CHARSET), SingleTon.getInstance().beanUSerInfoForCustomerInfo.getmPhoneNumber(), "state");
                    }

                    Intent intent = new Intent(BuyRegistrationFormUserInfo.this, BuyRegistrationFormAddresses.class);
                    startActivity(intent);
                }
            }
        }catch(Exception e){}

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
		EditTextList.add(etMobile);
		for (EditText editText : EditTextList) {
			if (editText.getText().toString().equalsIgnoreCase("")) {
				check = false;
				editText.requestFocus();
				editText.setError("Please Enter " + editText.getHint());
				break;
			} else if(editText.getId()==R.id.txt_buy_registration_form_user_info_email) {
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
}

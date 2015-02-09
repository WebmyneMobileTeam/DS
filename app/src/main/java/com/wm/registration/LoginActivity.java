package com.wm.registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.webmyne.daryabherbarium.R;
import com.wm.customviews.CustomButton;
import com.wm.customviews.CustomTextview;
import com.wm.dh_activity.ProductMain;
import com.wm.model.ConfigurationActionBar;

public class LoginActivity extends ActionBarActivity{
    
    CustomTextview btnCreateNewAccount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
  
        ConfigurationActionBar.SetupActionBar(getSupportActionBar(), "LOGIN",false,false,LoginActivity.this);
        
        new Thread(new Runnable() {
	    
	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		   setupHeightStrip();
	    }
	}).start();
        
        fetchIds();
        
   ((CustomButton)findViewById(R.id.btnLogin)).setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		Intent i = new Intent(LoginActivity.this,ProductMain.class);
		startActivity(i);
	
	    }
	});
      
    }

    private void fetchIds() {
	// TODO Auto-generated method stub
	
	btnCreateNewAccount = (CustomTextview)findViewById(R.id.btnCreateAccount);
	btnCreateNewAccount.setOnClickListener(myClick);
	
	
    }
    
    View.OnClickListener myClick = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
    	
            Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
	
		startActivity(i);
        
        }
    };

    private void setupHeightStrip() {
	// TODO Auto-generated method stub
	
	final ImageView ivLogo = (ImageView)findViewById(R.id.imgLogo);
	final View v = (View)findViewById(R.id.login_strip);
	ViewTreeObserver vto = ivLogo.getViewTreeObserver();
	vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
	    
	    @Override
	    public void onGlobalLayout() {
		// TODO Auto-generated method stub
		RelativeLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.height = (int) (ivLogo.getMeasuredHeight() - (ivLogo.getMeasuredHeight()/1.5));
		v.setLayoutParams(params);
		
	    }
	});
	
	
    }

}

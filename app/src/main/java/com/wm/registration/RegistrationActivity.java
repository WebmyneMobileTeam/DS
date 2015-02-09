package com.wm.registration;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.webmyne.daryabherbarium.R;
import com.wm.customviews.CustomButton;
import com.wm.dh_activity.ProductMain;
import com.wm.model.ConfigurationActionBar;

public class RegistrationActivity extends ActionBarActivity{
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration); 
        
        setupActionBar();
        
     
        ((CustomButton)findViewById(R.id.btnSignUp)).setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		
		Intent i = new Intent(RegistrationActivity.this,ProductMain.class);
		startActivity(i);
	
	    }
	});
     
     
    }
    
  
    private void setupActionBar() {
	// TODO Auto-generated method stub
	
	ConfigurationActionBar.SetupActionBar(getSupportActionBar(), "Sign up",false,false,RegistrationActivity.this);
	
    }
   
    

}

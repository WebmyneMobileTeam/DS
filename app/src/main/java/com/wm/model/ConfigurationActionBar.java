package com.wm.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Gravity;

import com.wm.customviews.CustomTextview;

public class ConfigurationActionBar {

    public static void SetupActionBar(ActionBar ab, String title,
	    boolean displaHomeAsUp, boolean isLogoEnabled, Context ctx) {

	SingleTon.getInstance().acBarParams.gravity = Gravity.CENTER
		| Gravity.CENTER_VERTICAL;
	
	ab.setBackgroundDrawable(new ColorDrawable(AppColors.APP_THEME_COLOR));
	ab.setDisplayShowCustomEnabled(true);
	
	if (displaHomeAsUp == true) {

	    ab.setDisplayHomeAsUpEnabled(true);
	}

	CustomTextview tv = new CustomTextview(ctx, null);

	tv.setText(title);
	tv.setTextColor(Color.WHITE);
	tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
	ab.setCustomView(tv, SingleTon.getInstance().acBarParams);
	
	if (isLogoEnabled == true) {
	    
	

	} else {
	 
	   ab.setLogo(new ColorDrawable(Color.TRANSPARENT));
	}

	ab.setTitle("");
	

    }

}

package com.wm.dh_activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.webmyne.daryabherbarium.R;
import com.wm.model.AppConstants;

public class Launcher extends Activity {
	
	ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_launcher);

      AppConstants.init();
	
	imgSplash = (ImageView)findViewById(R.id.imgSplash);
	
	if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
		processNextScreen();
	}else{
		goNextWithAnimation();
	}
	
    }

    @SuppressLint("NewApi") private void goNextWithAnimation() {

    	
    	new CountDownTimer(AppConstants.SPLASH_DURATION,AppConstants.SPLASH_INTERVAL) {

    	    @Override
    	    public void onTick(long millisUntilFinished) {


    	    }

    	    @Override
    	    public void onFinish() {

    	    	ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imgSplash, "scaleX",0f);
    			ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imgSplash, "scaleY",0f);
    			scaleDownX.setDuration(300);
    			scaleDownY.setDuration(300);
    			AnimatorSet scaleDown = new AnimatorSet();	
    			
    			scaleDown.playTogether(scaleDownX,scaleDownY);
    			scaleDown.start();
    			
    			scaleDown.addListener(new AnimatorListener() {

					@Override
					public void onAnimationRepeat(Animator animation) {

					}
					
					@Override
					public void onAnimationEnd(Animator animation) {

						Intent i = new Intent(Launcher.this, ProductMain.class);
						// i.putExtra("","");
						startActivity(i);
						finish();
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {

					}

					@Override
					public void onAnimationStart(Animator animation) {


					}
				});

    	    }
    	}.start();

	}
	private void processNextScreen() {


	new CountDownTimer(AppConstants.SPLASH_DURATION,AppConstants.SPLASH_INTERVAL) {

	    @Override
	    public void onTick(long millisUntilFinished) {
	    }

	    @Override
	    public void onFinish() {

		Intent i = new Intent(Launcher.this, ProductMain.class);
		// i.putExtra("","");
		startActivity(i);
		finish();

	    }
	}.start();

    }

}

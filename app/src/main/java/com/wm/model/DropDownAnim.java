package com.wm.model;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;

public class DropDownAnim extends Animation implements AnimationListener{
	
private final int targetHeight;
    private final View view;
    private final boolean down;
    private int mSize = 0;
    

    public DropDownAnim(View view, int targetHeight, boolean down ,int s) {
    	
        this.view = view;
        this.targetHeight = targetHeight;
        this.down = down;
        this.mSize = s;
        this.setAnimationListener(this);
        this.view.setMinimumHeight(mSize);

    } 
    
  
  
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        
        if (down) {
            newHeight = (int) (targetHeight * interpolatedTime);
         
        } else {
           // newHeight = (int) (targetHeight * (1 - interpolatedTime) + mSize);
            
            newHeight = (int) (targetHeight * (1 - interpolatedTime));
       
        }
        
     
        
        view.getLayoutParams().height = newHeight +mSize;
     
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }



    @Override
    public void onAnimationStart(Animation animation) {
	// TODO Auto-generated method stub
	
    }



    @Override
    public void onAnimationEnd(Animation animation) {
	// TODO Auto-generated method stub
	
    }



    @Override
    public void onAnimationRepeat(Animation animation) {
	// TODO Auto-generated method stub
	
    }
    
    

}

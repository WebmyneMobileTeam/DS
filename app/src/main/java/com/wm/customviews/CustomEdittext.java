package com.wm.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.webmyne.daryabherbarium.R;
import com.wm.model.AppConstants;

public class CustomEdittext extends EditText {

    public CustomEdittext(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {

	// TODO Auto-generated method stub

	TypedArray a = context.obtainStyledAttributes(attrs,
		R.styleable.customfont);
	String fontFamily = AppConstants.CUSTOM_FONT;

	/*
	 * final int n = a.getIndexCount(); for (int i = 0; i < n; ++i) { int
	 * attr = a.getIndex(i); if (attr ==
	 * R.styleable.customfont_android_fontFamily) { fontFamily =
	 * a.getString(attr); } a.recycle(); }
	 */

	if (!isInEditMode()) {
	    try {
		Typeface tf = Typeface.createFromAsset(
			getContext().getAssets(), fontFamily);
		setTypeface(tf,Typeface.BOLD);
	    } catch (Exception e) {
	    }
	}

    }

}

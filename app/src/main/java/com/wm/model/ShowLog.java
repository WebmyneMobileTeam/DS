package com.wm.model;

import android.util.Log;

public class ShowLog {

    public static void displayLog(String tag, String msg) {

	if (AppConstants.SHOW_LOGS == true) {

	    Log.e(tag, msg);

	}

    }

}

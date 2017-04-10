package com.sinaleju.lifecircle.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class ADSoftInputUtils {
	public static void hide(Context context){
		IBinder binder = ((Activity)context).getWindow().peekDecorView().getWindowToken();
		InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        m .hideSoftInputFromWindow(binder, 0);

	}
	
	public static void show(Context context){
		InputMethodManager m = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
}

package com.sinaleju.lifecircle.app.utils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewUtil {
	/**
	 * 
	 * @param v
	 * @param text
	 */
	public static final void textSetter(View v, String text) {
		if(v==null)
			return;
		if (v instanceof TextView)
			((TextView) v).setText(text);
		else if (v instanceof EditText)
			((EditText) v).setText(text);
		else if (v instanceof Button)
			((Button) v).setText(text);
	}
}

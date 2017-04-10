package com.sinaleju.lifecircle.app.utils;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class ADInteractUtils {
	public static boolean isActionDownOnView(MotionEvent event,View... views) {
		float x = event.getRawX();
		float y = event.getRawY();
		int[] l = new int[2];
		for (View v : views) {
			int w = v.getMeasuredWidth();
			int h = v.getMeasuredHeight();
			v.getLocationOnScreen(l);
			Rect r = new Rect(l[0], l[1], l[0] + w, l[1] + h);
			if(x > r.left && x < r.right && y < r.bottom && y > r.top){
				return true;
			}
		}
		return false;
	}
}

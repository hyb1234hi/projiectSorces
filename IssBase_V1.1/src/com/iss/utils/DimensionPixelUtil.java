package com.iss.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 代码中的单位转换
 * px
 * dip
 * sp
 * @author perry
 *
 */
public class DimensionPixelUtil {
	public final static int PX = TypedValue.COMPLEX_UNIT_PX;
	public final static int DIP = TypedValue.COMPLEX_UNIT_DIP;
	public final static int SP = TypedValue.COMPLEX_UNIT_SP;

	/**
	 * 
	 * @param unit    单位 </br>0 px</br>1 dip</br>2 sp
	 * @param value  size 大小
	 * @param context
	 * @return    
	 */
	public static float getDimensionPixelSize(int unit, float value, Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		switch (unit) {
		case PX:
			return value;
		case DIP:
		case SP:
			return TypedValue.applyDimension(unit, value, metrics);
		default:
			throw new IllegalArgumentException("unknow unix");
		}
	}
	
	/**
	* 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	*/
	public static int dip2px(Context context, float dpValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (dpValue * scale + 0.5f);
	}

	/**
	* 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	*/
	public static int px2dip(Context context, float pxValue) {
	final float scale = context.getResources().getDisplayMetrics().density;
	return (int) (pxValue / scale + 0.5f);
	}

	
}

package com.sinaleju.lifecircle.app.customviews.picker.pickerimpl;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.sinaleju.lifecircle.app.customviews.picker.pickers.D_CommonImpl;
import com.sinaleju.lifecircle.app.customviews.picker.pickers.D_CommonImpl.D_Children;
import com.sinaleju.lifecircle.app.customviews.picker.pickers.D_CommonImpl.D_Parent;
import com.sinaleju.lifecircle.app.customviews.picker.pickers.D_DateStringArray;
import com.sinaleju.lifecircle.app.customviews.picker.pickers.S_StringArray;

public class WheelFactory {

	/**
	 * 
	 * @param context
	 * @param stringArray
	 * @param targetView
	 * @return
	 */
	public static <P extends D_Parent<C>, C extends D_Children<P>> WheelDialog getCommonDoubleWheel(
			final Context context, List<? extends D_Parent<C>> p,
			final View targetView) {

		// init SingleWheel
		DoubleWheel dw = new DoubleWheel(context);
		// make a setter
		WheelViewSetter setter = new D_CommonImpl<P, C>(context, dw,
				targetView, p);
		// init
		dw.init(setter);

		return dw;
	}

	/**
	 * 
	 * @param context
	 * @param stringArray
	 * @param targetView
	 * @return
	 */
	public static WheelDialog getSingleWheelWithStringArray(
			final Context context, final String[] stringArray,
			final View targetView, int index) {

		// init SingleWheel
		SingleWheel sw = new SingleWheel(context);
		// make a setter
		WheelViewSetter setter = new S_StringArray(context, sw, stringArray,
				targetView, index);
		// init
		sw.init(setter);

		return sw;
	}

	public static WheelDialog getDoubleWheelWithStringArray(
			final Context context, final String[] leftArray,
			final String[] rightArray, final View targetView) {

		// init SingleWheel
		DoubleWheel dw = new DoubleWheel(context);
		// make a setter
		WheelViewSetter setter = new D_DateStringArray(context, dw, targetView,
				leftArray, rightArray);
		// init
		dw.init(setter);

		return dw;
	}

}

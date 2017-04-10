package com.sinaleju.lifecircle.app.customviews.picker.pickerimpl;

import android.view.View;
import android.widget.Button;

import com.sinaleju.lifecircle.app.customviews.picker.WheelView;

/**
 * @author sunny.dai
 */
public interface WheelViewSetter {
	/**
	 */
	public void setAdapters();

	/**
	 */
	public void addOnChangeListeners();

	/**
	 * @param rootView
	 *            the whole dialog view ,Default without any processing for
	 *            this.we already set a listener for it which will dismiss this
	 *            dialog when rootView is clicked.you can also set another if it
	 *            is necessary
	 * @param left
	 *            left|top button above wheelview,as a negativeButton normally
	 * @param right
	 *            right|top button above wheelview,as a postiveButton normally
	 */
	public void setOnClickListeners(View rootView, Button left, Button right);
	
	
	/**
	 * init WheelView fields  
	 */
	public void initWheels(WheelView... wheels);
}

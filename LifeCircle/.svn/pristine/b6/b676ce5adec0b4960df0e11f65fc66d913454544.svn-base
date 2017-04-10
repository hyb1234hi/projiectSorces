package com.sinaleju.lifecircle.app.customviews.picker.pickers;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sinaleju.lifecircle.app.customviews.picker.WheelAdapter;
import com.sinaleju.lifecircle.app.customviews.picker.WheelView;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.SingleWheel;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.TextSetter;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog.WheelActor;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelItem;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelViewSetter;

public class S_StringArray implements WheelViewSetter {
	private WheelView wheel;
	private SingleWheel sw;
	private View targetView;
	private String[] stringArray;
	private Context context;
	private int index;

	public S_StringArray(Context context, SingleWheel sw, String[] stringArray,
			View targetView, int index) {
		this.context = context;
		this.sw = sw;
		this.stringArray = stringArray;
		this.targetView = targetView;
		this.index = index;
	}

	@Override
	public void setAdapters() {
		// make data
		List<WheelItem> items = WheelItem.makeItemsFromObjects(null,
				stringArray);

		// make adapter
		WheelAdapter wheelAdapter = new WheelAdapter(context, items);

		// setAdapter
		wheel.setViewAdapter(wheelAdapter);
		
		wheel.setCurrentItem(index);
	}

	@Override
	public void addOnChangeListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOnClickListeners(View rootView, Button left, Button right) {
		// TODO Auto-generated method stub
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				sw.dismiss();
				
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sw.dismiss();
				String text = null;
				text = ((WheelAdapter) wheel.getViewAdapter()).getItemText(
						wheel.getCurrentItem()).toString();
				TextSetter.textSetter(targetView, text);
				
				//doAction if this WheelDialog has a actor
				WheelActor actor = sw.getWheelActor();
				//
				if(actor!=null)
					actor.doActionWhenConfirm(wheel.getCurrentItem());
				
			}
		});
	}

	@Override
	public void initWheels(WheelView... wheels) {
		this.wheel = wheels[0];
	}

}

package com.sinaleju.lifecircle.app.customviews.picker.pickerimpl;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.customviews.picker.WheelView;

public class SingleWheel extends WheelDialog {

	private ViewGroup rootView;
	private WheelView wheel;
	private Button rightButton;
	private Button leftButton;
	
	/**
	 * constructor
	 * 
	 * @param context
	 */
	public SingleWheel(Context context) {
		super(context, R.style.style_picker);
		setContentView(R.layout.view_picker_single_wheel);

		setProperty();
		initView();
		setListener();
	}

	/**
	 * 
	 */
	private void initView() {
		rootView = (ViewGroup) findViewById(R.id.layout_root);
		wheel = (WheelView) findViewById(R.id.wheelView_view1);
		leftButton = (Button) findViewById(R.id.wheel_button_cancel);
		rightButton = (Button) findViewById(R.id.wheel_button_ok);
	}

	/**
	 * 
	 */
	private void setListener() {

		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	/**
	 * 
	 */
	private void setProperty() {
		Window window = getWindow();
		WindowManager.LayoutParams p = window.getAttributes();
		Display d = getWindow().getWindowManager().getDefaultDisplay();

		p.height = (int) (d.getHeight() * 1);
		p.width = (int) (d.getWidth() * 1);
		window.setAttributes(p);
	}

	@Override
	protected void prepareToShow(){
		if (this.wheelSetter != null) {
			wheelSetter.initWheels(wheel);
			wheelSetter.setAdapters();
			wheelSetter.addOnChangeListeners();
			wheelSetter.setOnClickListeners(rootView, leftButton, rightButton);
		}
	}
	
	


}

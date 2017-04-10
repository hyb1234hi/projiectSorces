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

/**
 * 
 * @author sunny.dai
 * 
 */
public class DoubleWheel extends WheelDialog {

	private ViewGroup rootView;
	private WheelView leftWheel;
	private WheelView rightWheel;
	private Button rightButton;
	private Button leftButton;

	// private DataSetter dataSetter;

	public DoubleWheel(Context context) {
		super(context, R.style.style_picker);
		setContentView(R.layout.view_picker_double_wheel);

		setProperty();
		initView();
		setListener();
	}

	private void initView() {
		rootView = (ViewGroup) findViewById(R.id.layout_root);
		leftWheel = (WheelView) findViewById(R.id.wheelView_view1);
		rightWheel = (WheelView) findViewById(R.id.wheelView_view2);
		leftButton = (Button) findViewById(R.id.wheel_button_cancel);
		rightButton = (Button) findViewById(R.id.wheel_button_ok);
	}
	
	public void setLeftButtonText(){
		leftButton.setText("取消");
	}

	private void setListener() {

		rootView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

//	/**
//	 * 
//	 * @param setter
//	 */
//	public void setDoubleWheelDataSetter(DoubleWheelDataSetter setter) {
//
//		// this.dataSetter = setter;
//
//		if (setter != null) {
//			setter.setAdapters(leftWheel, rightWheel);
//			setter.setOnClickListeners(rootView, leftButton, rightButton,leftWheel,rightWheel);
//			setter.addOnChangeListeners(leftWheel, rightWheel);
//		}
//
//	}

//	/**
//	 * 
//	 * @author sunny.dai
//	 * 
//	 */
//	public interface DoubleWheelDataSetter {
//		public void setAdapters(WheelView left, WheelView right);
//
//		public void addOnChangeListeners(WheelView left, WheelView right);
//
//		public void setOnClickListeners(View rootView, Button leftButton, Button rightButton,WheelView leftWheel,WheelView rightWheel);
//	}

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

//	/**
//	 * 
//	 * @param setter
//	 */
//	public void showWithDoubleWheelDataSetter(DoubleWheelDataSetter setter) {
//		setDoubleWheelDataSetter(setter);
//		show();
//	}

	@Override
	protected void prepareToShow() {
		if (this.wheelSetter != null) {
			wheelSetter.initWheels(leftWheel,rightWheel);
			wheelSetter.setAdapters();
			wheelSetter.addOnChangeListeners();
			wheelSetter.setOnClickListeners(rootView, leftButton, rightButton);
		}
	}

}

package com.sinaleju.lifecircle.app.customviews.picker.pickers;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sinaleju.lifecircle.app.customviews.picker.OnWheelChangedListener;
import com.sinaleju.lifecircle.app.customviews.picker.WheelAdapter;
import com.sinaleju.lifecircle.app.customviews.picker.WheelView;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.DoubleWheel;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelItem;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelViewSetter;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.ViewUtil;

public class D_DateStringArray implements WheelViewSetter {
	private static final String TAG = "D_StringArray";
	private Context mContext;
	private DoubleWheel mDoubleWheel;
	private View mTargetView;
	private WheelView mLeftWheel;
	private WheelView mRightWheel;
	private WheelAdapter mLeftAdapter;
	private String[] mLeft;
	private String[] mRight;

	public D_DateStringArray(Context context, DoubleWheel dw, View targetView,
			String[] left, String[] right) {
		this.mContext = context;
		this.mDoubleWheel = dw;
		this.mTargetView = targetView;
		this.mLeft = left;
		this.mRight = right;
	}

	@Override
	public void setAdapters() {
		// make data
		List<WheelItem> leftItems = WheelItem.makeItemsFromObjects(null, mLeft);
		// make adapter
		WheelAdapter leftWheelAdapter = new WheelAdapter(mContext, leftItems);
		// setAdapter
		mLeftWheel.setViewAdapter(leftWheelAdapter);

		// make data
		List<WheelItem> rightItems = WheelItem.makeItemsFromObjects(null,
				mRight);
		// make adapter
		WheelAdapter rightWheelAdapter = new WheelAdapter(mContext, rightItems);
		// setAdapter
		mRightWheel.setViewAdapter(rightWheelAdapter);
	}

	@Override
	public void addOnChangeListeners() {

		mLeftWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (mLeftWheel.getCurrentItem() >= mRightWheel.getCurrentItem() + 1) {
					mRightWheel.setCurrentItem(mRightWheel.getCurrentItem() + 1);
				}
			}
		});
		
		mRightWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (mLeftWheel.getCurrentItem() >= mRightWheel.getCurrentItem() + 1) {
					mLeftWheel.setCurrentItem(mLeftWheel.getCurrentItem() - 1);
				}
			}
		});

	}

	@Override
	public void setOnClickListeners(View rootView, Button left, Button right) {

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mDoubleWheel.dismiss();
			}
		});

		right.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// dismiss
				mDoubleWheel.dismiss();
				
				// adapter
				WheelAdapter leftAdapter = (WheelAdapter) mLeftWheel
						.getViewAdapter();
				WheelAdapter rightAdapter = (WheelAdapter) mRightWheel
						.getViewAdapter();
				// texts
				String leftText = leftAdapter.getItemText(
						mLeftWheel.getCurrentItem()).toString();
				String rightText = rightAdapter.getItemText(
						mRightWheel.getCurrentItem()).toString();

				// setTag
				WheelItem item = rightAdapter.getItem(mRightWheel
						.getCurrentItem());
				mTargetView.setTag(item.getTag());

				// update text
				ViewUtil.textSetter(mTargetView, leftText + "-" + rightText);

				// doAction if this WheelDialog has a actor
				// WheelActor actor = mDoubleWheel.getWheelActor();
				// if (actor != null) {
				// actor.doActionWhenConfirm();
				// }
			}
		});

	}

	@Override
	public void initWheels(WheelView... wheels) {
		mLeftWheel = wheels[0];
		mRightWheel = wheels[1];
		LogUtils.e(TAG, "mLeftWheel  " + mLeftWheel + " mRightWheel  "
				+ mRightWheel);

	}

}

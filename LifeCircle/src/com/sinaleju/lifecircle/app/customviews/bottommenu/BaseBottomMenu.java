package com.sinaleju.lifecircle.app.customviews.bottommenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.iss.utils.DimensionPixelUtil;
import com.sinaleju.lifecircle.R;

public class BaseBottomMenu extends LinearLayout implements AnimationListener {

	private Animation animation_translate_bottom_visible,
			animation_translate_bottom_disvisible;
	private boolean is_show = false;

	private LayoutInflater inflater = null;
	private Context mContext = null;
	private String cancelButtonName = "取消";
	public static final String TYPE_BG_RED = "TYPE_CANCEL";

	public BaseBottomMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BaseBottomMenu(Context context) {
		super(context);
		init(context);
	}

	private void init(Context mContext) {
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);

		// animation
		animation_translate_bottom_disvisible = AnimationUtils.loadAnimation(
				mContext, R.anim.anim_bottom_menu_like_drop_down);
		animation_translate_bottom_visible = AnimationUtils.loadAnimation(
				mContext, R.anim.anim_bottom_menu_like_raise_up);
		animation_translate_bottom_visible.setAnimationListener(this);
		animation_translate_bottom_disvisible.setAnimationListener(this);
	}

	public void addButton(Button[] b) {
		for (int i = 0; i < b.length; i++) {
			addView(b[i]);
		}

		addView(createACancelMenuButton(cancelButtonName));
	}

	public void addButton(String[] names) {
		if (names != null) {
			for (int i = 0; i < names.length; i++) {
				if (names[i].startsWith(TYPE_BG_RED))
					addView(createADeleteMenuButton(names[i].substring(TYPE_BG_RED.length())));
				else
					addView(createAStandardMenuButton(names[i]));
			}
		}

		addView(createACancelMenuButton(cancelButtonName));
	}


	private ViewGroup createAStandardMenuButton(String name) {
		return createAButton(name,
				R.drawable.selector_bottom_menu_function_button, Color.BLACK);
	}

	private ViewGroup createACancelMenuButton(String name) {
		ViewGroup b = createAButton(name,
				R.drawable.selector_bottom_menu_cancel_button, Color.WHITE);
		b.getChildAt(0).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		return b;
	}

	private ViewGroup createADeleteMenuButton(String name) {
		return createAButton(name,
				R.drawable.selector_bottom_menu_delete_button, Color.WHITE);
	}

	private ViewGroup createAButton(String name, int resid, int textColor) {

		ViewGroup vg = (ViewGroup) inflater.inflate(
				R.layout.view_base_bottom_menu_button, null);
		LayoutParams params = new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.topMargin = (int) DimensionPixelUtil.getDimensionPixelSize(
				DimensionPixelUtil.DIP, 10.0f, mContext);
		vg.setLayoutParams(params);
		Button b = (Button) vg.findViewById(R.id.bottomButton);
		b.setText(name);
		b.setTextColor(textColor);
		b.setBackgroundResource(resid);
		return vg;
	}

	// public String getCancelButtonName() {
	// return cancelButtonName;
	// }
	//
	// public void setCancelButtonName(String cancelButtonName) {
	// this.cancelButtonName = cancelButtonName;
	// }

	public void show() {
		if (!is_show) {
			setVisibility(View.VISIBLE);
			startAnimation(animation_translate_bottom_visible);
			is_show = true;
		} else {
			dismiss();
		}
	}

	public void dismiss() {
		startAnimation(animation_translate_bottom_disvisible);
		setVisibility(View.GONE);
		is_show = false;
	}

	public boolean isShowing(){
		return is_show;
	}
	
	public void setListener(int childIndex, View.OnClickListener listener) {
		ViewGroup viewGroup = (ViewGroup) getChildAt(childIndex);
		Button b = (Button) viewGroup.getChildAt(0);
		b.setOnClickListener(listener);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		this.clearAnimation();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	public Button getButtonAt(int index) {
		Button b = null;
		ViewGroup vg = (ViewGroup) getChildAt(index);
		if (vg == null)
			throw new IndexOutOfBoundsException();
		b = (Button) vg.getChildAt(0);
		return b;
	}
}

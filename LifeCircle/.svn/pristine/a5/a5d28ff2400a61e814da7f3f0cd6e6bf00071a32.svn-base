package com.sinaleju.lifecircle.app.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sinaleju.lifecircle.R;

public class CustomVisitingCardView extends RelativeLayout {

	public CustomVisitingCardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomVisitingCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomVisitingCardView(Context context) {
		super(context);
	}

	private ImageView img;

	private void init() {
		img = new ImageView(getContext());
		LayoutParams params = new LayoutParams(500, 500);
		params.addRule(CENTER_IN_PARENT);
		img.setBackgroundColor(0xffff0000);
		img.setVisibility(View.INVISIBLE);
		img.setLayoutParams(params);
		addView(img);
	}

	private boolean isShown;

	public void show() {
		if (isShown()) {
			return;
		}
		setShown(true);
		Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.anim_visiting_card_drop_in);
		a.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				img.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				img.clearAnimation();
			}
		});
		img.startAnimation(a);
	}

	public void dismiss() {
		if (!isShown()) {
			return;
		}
		setShown(false);
		Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.anim_visiting_card_drop_out);
		a.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				img.clearAnimation();
				img.setVisibility(View.INVISIBLE);
				setVisibility(View.INVISIBLE);
			}
		});
		img.startAnimation(a);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (isShown())
				dismiss();
			else
				show();
		}
		return true;
	}

	public boolean isShown() {
		return isShown;
	}

	public void setShown(boolean isShown) {
		this.isShown = isShown;
	}

}

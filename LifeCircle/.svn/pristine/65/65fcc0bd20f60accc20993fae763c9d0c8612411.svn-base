package com.sinaleju.lifecircle.app.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;

public class SwitchButton extends RelativeLayout implements OnTouchListener,
		OnClickListener {
	private TextView textLeft;
	private TextView textRight;
	private TextView textSlipLeft;
	private TextView textSlipRight;
	private View slipButton;
	private View ll;
	private Context context;
	private TranslateAnimation fromLeftToRight;
	private TranslateAnimation fromRightToLeft;
	private int width;

	// 是否正在滑动
	private boolean isSlipping = false;
	// 当前开关状态，true为左边开启，false为右边开启。
	private boolean isSwitchLeft = true;

	// 手指按下时的水平坐标X，当前的水平坐标X
	private float previousX, currentX;
	private float slipButtonWidth;

	// 开关监听器
	private OnSwitchListener onSwitchListener;
	// 是否设置了开关监听器
	private boolean isSwitchListenerOn = false;
	private boolean hasMeasured;
	private String slipButtonText;

	public SwitchButton(Context context) {
		super(context);
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_swichbutton, this);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		textLeft = (TextView) findViewById(R.id.text_left);
		textRight = (TextView) findViewById(R.id.text_right);
		textSlipLeft = (TextView) findViewById(R.id.textslipleft);
		textSlipRight = (TextView) findViewById(R.id.textslipright);
		slipButton = findViewById(R.id.slipbutton);
		ll = findViewById(R.id.backgroundll);

	}

	private void initData() {

		// 获取滑动按钮父布局的宽度，以便设置滑动按钮的移动位置。
		ll.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				if (!hasMeasured) {
					width = ll.getMeasuredWidth() / 2;
					slipButton.setLayoutParams(new LayoutParams(width,
							LayoutParams.MATCH_PARENT));

					slipButtonWidth = slipButton.getMeasuredWidth();
					// 获取到宽度和高度后，可用于计算
					fromLeftToRight = new TranslateAnimation(0f, width-2, 0f,
							0f);
					fromLeftToRight.setFillAfter(true);
					fromLeftToRight.setDuration(300);
					fromLeftToRight.setInterpolator(new DecelerateInterpolator(1.1f));

					fromLeftToRight
							.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub
									textSlipLeft.setVisibility(View.GONE);
									textSlipRight.setVisibility(VISIBLE);
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									onSwitchListener.onSwitched(false);// 右边开启
									textLeft.setClickable(true);
									textRight.setClickable(false);
									//invalidate();

								}
							});

					fromRightToLeft = new TranslateAnimation(width-2 ,0, 0f,
							0f);
					fromRightToLeft.setFillAfter(true);
					fromRightToLeft.setDuration(300);
					fromRightToLeft.setInterpolator(new DecelerateInterpolator(1.1f));

					fromRightToLeft
							.setAnimationListener(new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {
									// TODO Auto-generated method stub
									textSlipRight.setVisibility(View.GONE);
									textSlipLeft.setVisibility(View.VISIBLE);
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									// TODO Auto-generated method stub
									onSwitchListener.onSwitched(true);// 左边开启；
									textLeft.setClickable(false);
									textRight.setClickable(true);
									//invalidate();								

								}
							});

					hasMeasured = true;
				}

				return true;
			}
		});
	}

	private void setListener() {
		textLeft.setOnClickListener(this);
		textRight.setOnClickListener(this);
		ll.setOnClickListener(this);
		setOnTouchListener(this);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_left:
			slipButton.startAnimation(fromRightToLeft);
//			onSwitchListener.onSwitched(true);// 左边开启；
//			textLeft.setClickable(false);
//			textRight.setClickable(true);
//			invalidate();
			break;
		case R.id.text_right:
			slipButton.startAnimation(fromLeftToRight);
//			onSwitchListener.onSwitched(false);// 右边开启
//			textLeft.setClickable(true);
//			textRight.setClickable(false);
//			invalidate();
			break;

		default:
			break;
		}
	}

	public void setOnSwitchListener(OnSwitchListener listener) {
		onSwitchListener = listener;
		// isSwitchListenerOn = true;
	}

	public interface OnSwitchListener {
		abstract void onSwitched(boolean isSwitchOn);
	}

	public void setText(String leftText, String rightText) {
		textLeft.setText(leftText);
		textSlipLeft.setText(leftText);
		textRight.setText(rightText);
		textSlipRight.setText(rightText);
	}

}
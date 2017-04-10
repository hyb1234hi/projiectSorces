package com.sinaleju.lifecircle.app.customviews.mainscrolllayout;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.sinaleju.lifecircle.app.utils.LogUtils;

public class ScrollLayout extends ViewGroup {

	private static final String TAG = "ScrollLayout";

	private VelocityTracker mVelocityTracker; // 用于判断甩动手势

	private static final int SNAP_VELOCITY = 600;

	private int myCurScreen = 0;
	private int i = 0;

	private OnViewChangeListener mOnViewChangeListener;
	private Timer mTimer;
	private AlphaAnimation alphaAnimation1;
	private AlphaAnimation alphaAnimation2;

	// private boolean isAnimationEnd;

	public ScrollLayout(Context context) {
		super(context);
		init();
	}

	public ScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	private void init() {
		mTimer = new Timer();
		// 显示动画
		alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation1.setDuration(800);
		// 隐藏动画
		alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
		alphaAnimation2.setDuration(800);

		alphaAnimation2.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) { // TODO

			}

			@Override
			public void onAnimationRepeat(Animation animation) { //

			}

			@Override
			public void onAnimationEnd(Animation animation) { // TODO
				// isAnimationEnd = true;

			}
		});

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			int childLeft = 0;
			final int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());

			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			/*
			 * if(!isAnimationEnd){ alphaAnimation2.setDuration(100);
			 * isAnimationEnd=false; }
			 */
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);

			}
			stopAutoScroll();

			break;

		case MotionEvent.ACTION_MOVE:

			if (mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				// isAnimationEnd = false;
			}

			break;

		case MotionEvent.ACTION_UP:
			int velocityX = 0;
			if (mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);
				velocityX = (int) mVelocityTracker.getXVelocity();
				LogUtils.i("velocityX", "velocityX:    " + velocityX
						+ "   myCurScreen:   " + myCurScreen);

			}

			if (velocityX > SNAP_VELOCITY && myCurScreen > 0) {
				// 向右滑动 Fling enough to move rigtht
				LogUtils.e(TAG, "snap rigth");

				/*
				 * View currentView = getChildAt(myCurScreen); View nextView =
				 * getChildAt(myCurScreen - 1);
				 * 
				 * nextView.startAnimation(alphaAnimation1);
				 * nextView.setVisibility(View.VISIBLE);
				 * 
				 * currentView.startAnimation(alphaAnimation1);
				 * currentView.setVisibility(View.GONE);
				 */

				// TODO Auto-generated method stub
				getChildAt(myCurScreen).startAnimation(alphaAnimation2);
				getChildAt(myCurScreen).setVisibility(View.GONE);
				myCurScreen--;

				getChildAt(myCurScreen).setVisibility(View.VISIBLE);
				getChildAt(myCurScreen).startAnimation(alphaAnimation1);
				invalidate();

			} else if (velocityX < -SNAP_VELOCITY
					&& myCurScreen < getChildCount() - 1) {
				// 向左滑动Fling enough to move left
				LogUtils.e(TAG, "snap left");
				/*
				 * View currentView = getChildAt(myCurScreen); View nextView =
				 * getChildAt(myCurScreen + 1);
				 * 
				 * nextView.startAnimation(alphaAnimation1);
				 * nextView.setVisibility(View.VISIBLE);
				 * 
				 * currentView.startAnimation(alphaAnimation1);
				 * currentView.setVisibility(View.GONE);
				 */

				getChildAt(myCurScreen).startAnimation(alphaAnimation2);
				getChildAt(myCurScreen).setVisibility(View.GONE);
				myCurScreen++;
				getChildAt(myCurScreen).setVisibility(View.VISIBLE);
				getChildAt(myCurScreen).startAnimation(alphaAnimation1);
				invalidate();

			}
			// 通知改变圆点位置
			if (mOnViewChangeListener != null) {
				LogUtils.i("onviewchange", "onviewchange  mycurscreen" + myCurScreen);
				mOnViewChangeListener.OnViewChange(myCurScreen);
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			break;
		}

		return true;
	}

	// 设置图片的显示隐藏状态
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				LogUtils.i("ScrollLayout", "getChildCount   " + getChildCount());
				if (i == getChildCount()) {
					i = 0;
				}
				getChildAt(i).startAnimation(alphaAnimation2);
				getChildAt(i).setVisibility(View.GONE);
				i++;
				if (i == getChildCount()) {
					i = 0;
				}
				// i = (i == getChildCount() ? 0 : i++);
				// LogUtils.i("ScrollLayout", "handleMessage1   " + i);
				getChildAt(i).setVisibility(View.VISIBLE);
				getChildAt(i).startAnimation(alphaAnimation1);
				postInvalidate();
				if (mOnViewChangeListener != null) {
					mOnViewChangeListener.OnViewChange(i);
				}
			}
		};
	};

	public void autoScroll() {
		// 图片自动滚动
		LogUtils.i("ScrollLayout", "autoScroll");
		mTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}, 3000, 2000);

	}

	public void stopAutoScroll() {
		if (mTimer != null) {
			mTimer.cancel();
		}
	}

	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

}

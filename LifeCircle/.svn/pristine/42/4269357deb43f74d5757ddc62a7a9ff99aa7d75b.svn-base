package com.sinaleju.lifecircle.app.customviews.pathlikelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.sinaleju.lifecircle.R;

/**
 * Class: ListViewPro.java<br>
 * Date: 2013/04/04<br>
 * Author: TiejianSha <br>
 * Email: tntshaka@gmail.com<br>
 */

public class PathLikeListView extends ListView implements OnScrollListener {

	private Context mContext;
	private Scroller mScroller;
	TouchTool tool;
	int left, top;
	float startX, startY, currentX, currentY;
	int bgViewH, iv1W;
	int rootW, rootH;
	View mHeadView;
	View mFootView;
	ImageView mHeadImage;
	ImageView mFootImage;

	private boolean isNeedHeader = true;
	private boolean isNeedFooter = true;

	boolean scrollerType;
	static final int len = 0xc8;

	public PathLikeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//
//		super.setOnScrollListener(this);
//
//		final TypedArray a = context.obtainStyledAttributes(attrs,
//				R.styleable.PathLikeListView);
//		final int scrollBarPanelLayoutId = a.getResourceId(
//				R.styleable.PathLikeListView_scrollBarPanel, -1);
//		final int scrollBarPanelInAnimation = a.getResourceId(
//				R.styleable.PathLikeListView_scrollBarPanelInAnimation,
//				R.anim.anim_pathlist_in_animation);
//		final int scrollBarPanelOutAnimation = a.getResourceId(
//				R.styleable.PathLikeListView_scrollBarPanelOutAnimation,
//				R.anim.anim_pathlist_out_animation);
//		a.recycle();
//
//		if (scrollBarPanelLayoutId != -1) {
//			setScrollBarPanel(scrollBarPanelLayoutId);
//		}
//
//		final int scrollBarPanelFadeDuration = ViewConfiguration
//				.getScrollBarFadeDuration();
//
//		if (scrollBarPanelInAnimation > 0) {
//			mInAnimation = AnimationUtils.loadAnimation(getContext(),
//					scrollBarPanelInAnimation);
//		}
//
//		if (scrollBarPanelOutAnimation > 0) {
//			mOutAnimation = AnimationUtils.loadAnimation(getContext(),
//					scrollBarPanelOutAnimation);
//			mOutAnimation.setDuration(scrollBarPanelFadeDuration);
//
//			mOutAnimation.setAnimationListener(new AnimationListener() {
//
//				@Override
//				public void onAnimationStart(Animation animation) {
//				}
//
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//
//				}
//
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					if (mScrollBarPanel != null) {
//						mScrollBarPanel.setVisibility(View.GONE);
//					}
//				}
//			});
//		}
	}

	public PathLikeListView(Context context, AttributeSet attrs) {
		// super(context, attrs);
		this(context, attrs, android.R.attr.listViewStyle);
		this.mContext = context;
		mScroller = new Scroller(mContext);
	}

	public PathLikeListView(Context context) {
		super(context);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (!mScroller.isFinished()) {
			return super.onTouchEvent(event);
		}

		if (isNeedHeader()) {
			currentX = event.getX();
			currentY = event.getY();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				// Log.i("ListView2", "ACTION_DOWN!!!");
				left = mHeadImage.getLeft();
				top = mHeadImage.getBottom();
				rootW = getWidth();
				rootH = getHeight();
				bgViewH = mHeadImage.getHeight();
				startX = currentX;
				startY = currentY;
				tool = new TouchTool(mHeadImage.getLeft(),
						mHeadImage.getBottom(), mHeadImage.getLeft(),
						mHeadImage.getBottom() + len);
				break;
			case MotionEvent.ACTION_MOVE:
				// Log.i("ListView2", "ACTION_MOVE!!!");
				// Log.i("ListView2", "currentX" + currentX);
				// Log.i("ListView2", "currentY" + currentY);
				// Log.i("ListView2", "headView.getTop()=" +
				// mHeadView.getTop());
				// Log.i("ListView2", "headView.isShown()=" +
				// mHeadView.isShown());

				if (mHeadView.isShown() && mHeadView.getTop() >= 0) {
					if (tool != null) {
						int t = tool.getScrollY(currentY - startY);
						if (t >= top && t <= mHeadView.getBottom() + len) {
							mHeadImage
									.setLayoutParams(new RelativeLayout.LayoutParams(
											mHeadImage.getWidth(), t));
						}
					}
					scrollerType = false;
				}
				break;
			case MotionEvent.ACTION_UP:
				// Log.i("ListView2", "ACTION_UP!!!");
				scrollerType = true;
				mScroller.startScroll(mHeadImage.getLeft(),
						mHeadImage.getBottom(), 0 - mHeadImage.getLeft(),
						bgViewH - mHeadImage.getBottom(), 200);
				invalidate();
				break;
			}
		}
		
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			int x = mScroller.getCurrX();
			int y = mScroller.getCurrY();
			// System.out.println("x=" + x);
			// System.out.println("y=" + y);
			mHeadImage.layout(0, 0, x + mHeadImage.getWidth(), y);
			invalidate();
			if (!mScroller.isFinished() && scrollerType && y > 200) {//
				mHeadImage.setLayoutParams(new RelativeLayout.LayoutParams(
						mHeadImage.getWidth(), y));
			}
		}
	}

	public static interface OnPositionChangedListener {
		public void onPositionChanged(PathLikeListView listView, int position,
				View scrollBarPanel);

		public void onScollPositionChanged(View scrollBarPanel, int top);
	}

	private OnScrollListener mOnScrollListener = null;

	private View mScrollBarPanel = null;

	private int mScrollBarPanelPosition = 0;

	private OnPositionChangedListener mPositionChangedListener;

	private int mLastPosition = -1;

	private Animation mInAnimation = null;

	private Animation mOutAnimation = null;

	private final Handler mHandler = new Handler();

	private final Runnable mScrollBarPanelFadeRunnable = new Runnable() {

		@Override
		public void run() {
			if (mOutAnimation != null) {
				// mScrollBarPanel.startAnimation(mOutAnimation);
			}
		}
	};

	/*
	 * keep track of Measure Spec
	 */
	private int mWidthMeasureSpec;

	private int mHeightMeasureSpec;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (null != mPositionChangedListener && null != mScrollBarPanel) {

			// Don't do anything if there is no itemviews
			if (totalItemCount > 0) {
				/*
				 * from android source code (ScrollBarDrawable.java)
				 */
				final int thickness = getVerticalScrollbarWidth();
				int height = Math.round((float) getMeasuredHeight()
						* computeVerticalScrollExtent()
						/ computeVerticalScrollRange());
				int thumbOffset = Math
						.round((float) (getMeasuredHeight() - height)
								* computeVerticalScrollOffset()
								/ (computeVerticalScrollRange() - computeVerticalScrollExtent()));
				final int minLength = thickness * 2;
				if (height < minLength) {
					height = minLength;
				}
				thumbOffset += height / 2;

				/*
				 * find out which itemviews the center of thumb is on
				 */
				final int count = getChildCount();
				for (int i = 0; i < count; ++i) {
					final View childView = getChildAt(i);
					if (childView != null) {
						if (thumbOffset > childView.getTop()
								&& thumbOffset < childView.getBottom()) {
							/*
							 * we have our candidate
							 */
							if (mLastPosition != firstVisibleItem + i) {
								mLastPosition = firstVisibleItem + i;

								/*
								 * inform the position of the panel has changed
								 */
								mPositionChangedListener.onPositionChanged(
										this, mLastPosition, mScrollBarPanel);

								/*
								 * measure panel right now since it has just
								 * changed INFO: quick hack to handle TextView
								 * has ScrollBarPanel (to wrap text in case
								 * TextView's content has changed)
								 */
								measureChild(mScrollBarPanel,
										mWidthMeasureSpec, mHeightMeasureSpec);
							}
							break;
						}
					}
				}

				/*
				 * update panel position
				 */
				mScrollBarPanelPosition = thumbOffset
						- mScrollBarPanel.getMeasuredHeight() / 2;
				final int x = getMeasuredWidth()
						- mScrollBarPanel.getMeasuredWidth()
						- getVerticalScrollbarWidth();
				// System.out.println("left=="
				// + x
				// + " top=="
				// + mScrollBarPanelPosition
				// + " bottom=="
				// + (x + mScrollBarPanel.getMeasuredWidth())
				// + " right=="
				// + (mScrollBarPanelPosition + mScrollBarPanel
				// .getMeasuredHeight()));
				mScrollBarPanel.layout(
						x,
						mScrollBarPanelPosition,
						x + mScrollBarPanel.getMeasuredWidth(),
						mScrollBarPanelPosition
								+ mScrollBarPanel.getMeasuredHeight());
				mPositionChangedListener.onScollPositionChanged(this,
						mScrollBarPanelPosition);
			}
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}
	}

	public void setOnPositionChangedListener(
			OnPositionChangedListener onPositionChangedListener) {
		mPositionChangedListener = onPositionChangedListener;
	}

	@Override
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		mOnScrollListener = onScrollListener;
	}

	public void setScrollBarPanel(View scrollBarPanel) {
		mScrollBarPanel = scrollBarPanel;
		mScrollBarPanel.setVisibility(View.GONE);
		requestLayout();
	}

	public void setScrollBarPanel(int resId) {
		setScrollBarPanel(LayoutInflater.from(getContext()).inflate(resId,
				this, false));
	}

	public View getScrollBarPanel() {
		return mScrollBarPanel;
	}

	@Override
	protected boolean awakenScrollBars(int startDelay, boolean invalidate) {
		final boolean isAnimationPlayed = super.awakenScrollBars(startDelay,
				invalidate);

		if (isAnimationPlayed == true && mScrollBarPanel != null) {
			if (mScrollBarPanel.getVisibility() == View.GONE) {
				// mScrollBarPanel.setVisibility(View.VISIBLE);
				if (mInAnimation != null) {
					// mScrollBarPanel.startAnimation(mInAnimation);

				}
			}

			mHandler.removeCallbacks(mScrollBarPanelFadeRunnable);
			mHandler.postAtTime(mScrollBarPanelFadeRunnable,
					AnimationUtils.currentAnimationTimeMillis() + startDelay);
		}

		return isAnimationPlayed;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// System.out.println("onMeasure.......................");
		if (mScrollBarPanel != null && getAdapter() != null) {
			mWidthMeasureSpec = widthMeasureSpec;
			mHeightMeasureSpec = heightMeasureSpec;

			measureChild(mScrollBarPanel, widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// System.out.println("onLayout.......................");
		if (mScrollBarPanel != null) {
			final int x = getMeasuredWidth()
					- mScrollBarPanel.getMeasuredWidth()
					- getVerticalScrollbarWidth();
			mScrollBarPanel.layout(
					x,
					mScrollBarPanelPosition,
					x + mScrollBarPanel.getMeasuredWidth(),
					mScrollBarPanelPosition
							+ mScrollBarPanel.getMeasuredHeight());
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (isNeedHeader())
			initHeader();
		if (isNeedFooter())
			initFooter();
		super.setAdapter(adapter);
	}

	private boolean isNeedFooter() {
		return isNeedFooter;
	}

	private boolean isNeedHeader() {
		return isNeedHeader;
	}

	public void setHeaderImage(BitmapDrawable drawable) {
		if (mHeadImage != null) {
			mHeadImage.setImageDrawable(drawable);
		}
	}

	public void hideHeaderImage() {
		if (mHeadImage != null) {
			mHeadImage.setVisibility(View.GONE);
		}
	}

	public void setNeedHeader(boolean need) {
		isNeedHeader = need;
	}

	public void setNeedFooter(boolean need) {
		isNeedFooter = need;
	}

	public void setFootImage(BitmapDrawable drawable) {
		if (mFootImage != null) {
			mFootImage.setImageDrawable(drawable);
		}
	}

	private void initHeader() {
		if (mHeadView == null) {
			mHeadView = LayoutInflater.from(getContext()).inflate(

			R.layout.view_custom_pathlist_header, null);
			mHeadImage = (ImageView) mHeadView
					.findViewById(R.id.path_headimage);

			// add
			addHeaderView(mHeadView);
		}
	}

	private void initFooter() {
		if (mFootView == null) {
			mFootView = LayoutInflater.from(getContext()).inflate(
					R.layout.view_custom_pathlist_footer, null);
			mFootImage = (ImageView) mFootView
					.findViewById(R.id.path_footimage);

			// add
			addFooterView(mFootView);
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		// System.out.println("dispatchDraw.......................");
		if (mScrollBarPanel != null
				&& mScrollBarPanel.getVisibility() == View.VISIBLE) {

			drawChild(canvas, mScrollBarPanel, getDrawingTime());
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// System.out.println("onDetachedFromWindow.......................");
		mHandler.removeCallbacks(mScrollBarPanelFadeRunnable);
	}

	private static class TouchTool {

		private int startX, startY;
		private int endX, endY;

		public TouchTool(int startX, int startY, int endX, int endY) {
			super();
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}

		public int getScrollX(float dx) {
			int xx = (int) (startX + dx / 2.5F);
			return xx;
		}

		public int getScrollY(float dy) {
			int yy = (int) (startY + dy / 2.5F);
			return yy;
		}
	}

}

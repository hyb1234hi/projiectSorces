package com.sinaleju.lifecircle.app.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sinaleju.lifecircle.R;
/**
 * @author dan_alan
 */
public class SlideIndicator extends View {

	private int mStepLength;
	private int mOrientation;
	private int mTagColor;
	private int mTagWidth;
	private int mTagHeight;
	private Bitmap mTagBitmap;
	private Paint mPaint;
	private static final int VERTICAL = 1;
	private static final int HORIZONTAL = 0;

	private int mLeft;
	private int mTop;

	public int getCurrentTop() {
		return mTop;
	}

	public SlideIndicator(Context context) {
		super(context);
	}

	public SlideIndicator(Context context, int stepLength) {
		super(context);
		this.mStepLength = stepLength;
	}

	public SlideIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean attachToView(View view) {
		View tag = (View) this.getTag();
		if (tag != null && tag == view) {
			return false;
		}
		int b = 0;
		int a = 0;
		if (isVertical()) {
			b = mTop;
			a = view.getTop();
		} else {
			b = mLeft;
			a = view.getLeft();
		}

		if (this.move(a - b)) {
			this.setTag(view);
			return true;
		}
		
		return false;
	}

	private int mCount = 0;
	private int mSurplus;

	private boolean move(int stepLength) {
		if (stepLength == 0)
			return false;
		if (mCount != 0) {
			return false;
		}

		this.mStepLength = stepLength;

		new Thread(new Runnable() {

			@Override
			public void run() {
				int abs = Math.abs(mStepLength);
				mSurplus = abs % 100;
				while (mCount < (abs > 100 ? 100 : abs)) {

					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					int a = 0;
					if (mSurplus <= 0) {
						mSurplus = 0;
					} else {
						--mSurplus;
						a = mStepLength / abs;
					}
					if (isVertical()) {
						mTop += (mStepLength / 100 + a);
					} else {
						mLeft += (mStepLength / 100 + a);
					}

					postInvalidate();
					
					mCount++;
				}
				mCount = 0;
			}

		}).start();

		return true;

	}

	public SlideIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.SlideIndicator);

		mTagColor = a.getColor(R.styleable.SlideIndicator_tagColor, Color.GRAY);
		mTagWidth = (int) a.getDimension(R.styleable.SlideIndicator_tagWidth, -1);
		mTagHeight = (int) a.getDimension(R.styleable.SlideIndicator_tagHeight, -1);

		int index = a.getInt(R.styleable.SlideIndicator_orientation, -1);
		if (index > 0) {
			setOrientation(index);
		}

		a.recycle();

		mPaint = new Paint();
		mPaint.setColor(mTagColor);
		mPaint.setAntiAlias(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.drawRect(mLeft, mTop, mLeft + mTagWidth, mTop + mTagHeight,
				mPaint);
		canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mTagWidth == -1) {
			mTagWidth = MeasureSpec.getSize(widthMeasureSpec);
		}

		if (mTagHeight == -1) {
			mTagHeight = MeasureSpec.getSize(heightMeasureSpec);
		}

	}

	public int getStepLength() {
		return mStepLength;
	}

	public void setStepLength(int stepLength) {
		this.mStepLength = stepLength;
	}

	public int getTagWidth() {
		return mTagWidth;
	}

	public void setTagWidth(int tagWidth) {
		this.mTagWidth = tagWidth;
	}

	public int getTagColor() {
		return mTagColor;
	}

	public void setTagColor(int tagColor) {
		this.mTagColor = tagColor;
	}

	public int getOrientation() {
		return mOrientation;
	}

	public void setOrientation(int orientation) {
		this.mOrientation = orientation;
	}

	public int getTagHeight() {
		return mTagHeight;
	}

	public void setTagHeight(int tagHeight) {
		this.mTagHeight = tagHeight;
	}

	private boolean isVertical() {
		return mOrientation == VERTICAL;
	}

	public Bitmap getmTagBitmap() {
		return mTagBitmap;
	}

	public void setmTagBitmap(Bitmap mTagBitmap) {
		this.mTagBitmap = mTagBitmap;
	}

}

package com.sinaleju.lifecircle.app.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sinaleju.lifecircle.R;

public class RoundCornerImageView extends ImageView {

	private Paint paint;
	private int roundWidth = 8;
	private int roundHeight = 8;
	private Paint paint2;

	public RoundCornerImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public RoundCornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public RoundCornerImageView(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {

		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.RoundAngleImageView);
			setRoundWidth(a.getDimensionPixelSize(
					R.styleable.RoundAngleImageView_roundWidth, getRoundWidth()));
			setRoundHeight(a.getDimensionPixelSize(
					R.styleable.RoundAngleImageView_roundHeight, getRoundHeight()));
		} else {
			float density = context.getResources().getDisplayMetrics().density;
			setRoundWidth((int) (getRoundWidth() * density));
			setRoundHeight((int) (getRoundHeight() * density));
		}

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

		paint2 = new Paint();
		paint2.setXfermode(null);
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap);
		super.draw(canvas2);
		drawLiftUp(canvas2);
		drawRightUp(canvas2);
		drawLiftDown(canvas2);
		drawRightDown(canvas2);
		canvas.drawBitmap(bitmap, 0, 0, paint2);
		bitmap.recycle();
	}

	private void drawLiftUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getRoundHeight());
		path.lineTo(0, 0);
		path.lineTo(getRoundWidth(), 0);
		path.arcTo(new RectF(0, 0, getRoundWidth() * 2, getRoundHeight() * 2), -90, -90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawLiftDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getHeight() - getRoundHeight());
		path.lineTo(0, getHeight());
		path.lineTo(getRoundWidth(), getHeight());
		path.arcTo(new RectF(0, getHeight() - getRoundHeight() * 2,
				0 + getRoundWidth() * 2, getHeight()), 90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth() - getRoundWidth(), getHeight());
		path.lineTo(getWidth(), getHeight());
		path.lineTo(getWidth(), getHeight() - getRoundHeight());
		path.arcTo(new RectF(getWidth() - getRoundWidth() * 2, getHeight()
				- getRoundHeight() * 2, getWidth(), getHeight()), 0, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth(), getRoundHeight());
		path.lineTo(getWidth(), 0);
		path.lineTo(getWidth() - getRoundWidth(), 0);
		path.arcTo(new RectF(getWidth() - getRoundWidth() * 2, 0, getWidth(),
				0 + getRoundHeight() * 2), -90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	public int getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(int roundWidth) {
		this.roundWidth = roundWidth;
	}

	public int getRoundHeight() {
		return roundHeight;
	}

	public void setRoundHeight(int roundHeight) {
		this.roundHeight = roundHeight;
	}

}

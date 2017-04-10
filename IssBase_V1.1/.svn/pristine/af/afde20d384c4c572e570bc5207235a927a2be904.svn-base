package com.iss.view.calendar;

import java.util.Calendar;

import com.iss.loghandler.Log;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;

public class DateWidgetDayCell extends View {
	// types
	public interface OnItemClick {
		public void OnClick(DateWidgetDayCell item);
	}

	public static int ANIM_ALPHA_DURATION = 100;
	// fields
	private final static float fTextSize = 22;
	private final static int iMargin = 1;
	private final static int iAlphaInactiveMonth = 0x55;//0x88
	private static final String TAG = "DateWidgetDayCell";

	// fields
	private int iDateYear = 0;
	private int iDateMonth = 0;
	private int iDateDay = 0;

	// fields
	private OnItemClick itemClick = null;
	private Paint pt = new Paint();
	private RectF rect = new RectF();
	private String sDate = "";

	// fields
	private boolean bSelected = false;
	private boolean bIsActiveMonth = false;
	private boolean bToday = false;
	private boolean bHoliday = false;
	private boolean bTouchedDown = false;

	// methods
	public DateWidgetDayCell(Context context, int iWidth, int iHeight) {
		super(context);
		setFocusable(true);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
	}

	public boolean getSelected() {
		return this.bSelected;
	}

	@Override
	public void setSelected(boolean bEnable) {
		if (this.bSelected != bEnable) {
			this.bSelected = bEnable;
		}
		this.invalidate();//不刷新问题 2012-11-16 从if中放出来
	}
	
	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
//		return 
				super.isSelected();
		return bSelected;
	}
	
	public void setData(int iYear, int iMonth, int iDay, boolean bToday,
			boolean bHoliday, int iActiveMonth) {
		iDateYear = iYear;
		iDateMonth = iMonth;
		iDateDay = iDay;

		this.sDate = Integer.toString(iDateDay);
		this.bIsActiveMonth = (iDateMonth == iActiveMonth);
		this.bToday = bToday;
		this.bHoliday = bHoliday;
	}

	protected void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;
	}

	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
			doItemClick();
		}
		return bResult;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyUp(keyCode, event);
		return bResult;
	}

	protected void doItemClick() {
		if (itemClick != null)
			itemClick.OnClick(this);
	}

	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,Rect previouslyFocusedRect) {
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		invalidate();
	}

	public Calendar getDate() {
		Calendar calDate = Calendar.getInstance();
		calDate.clear();
		calDate.set(Calendar.YEAR, iDateYear);
		calDate.set(Calendar.MONTH, iDateMonth);
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);
		return calDate;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// init rectangles
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(3, 3);

		// drawing
		final boolean bFocused = IsViewFocused();

		drawDayView(canvas, bFocused);
		drawDayNumber(canvas, bFocused);
	}

	private void drawDayView(Canvas canvas, boolean bFocused) {
		if (bSelected ) {//|| bFocused
			LinearGradient lGradBkg = null;

			if (bFocused) {
//				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
//						DayStyle.iColorBkgFocusDark,DayStyle.iColorBkgFocusLight, Shader.TileMode.CLAMP);
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						DayStyle.iColorSelectedBg,DayStyle.iColorSelectedBg, Shader.TileMode.CLAMP);
				//当前月不包括的日期背景颜色
//				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
//						DayStyle.tranpond,DayStyle.tranpond, Shader.TileMode.CLAMP);
			}

			if (bSelected) {
//				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,DayStyle.iColorBkgSelectedDark,DayStyle.iColorBkgSelectedLight, Shader.TileMode.CLAMP);
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						DayStyle.iColorSelectedBg,DayStyle.iColorSelectedBg, Shader.TileMode.CLAMP);
			}

			if (lGradBkg != null) {
				pt.setColor(DayStyle.iColorSelectedBg);
				pt.setStyle(Style.FILL_AND_STROKE); 
				pt.setShader(lGradBkg);
				canvas.drawRect(rect, pt);
				pt.setStyle(Style.STROKE);
			}
			

			pt.setShader(null);

		} else {
			//当月所包含日期 背景颜色
//			pt.setColor(DayStyle.getColorBkg(bHoliday, bToday));
//			pt.setColor(DayStyle.colorWhite);
//			pt.setColor(Color.WHITE);
			pt.setColor(Color.BLACK);
//			pt.setColor(Color.TRANSPARENT);
			pt.setStyle(Style.STROKE);    
			//当前月不包括的日期背景颜色
//			lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
//					DayStyle.tranpond,DayStyle.tranpond, Shader.TileMode.CLAMP);
		
			
			if(bHoliday){//节假日
				pt.setAlpha(iAlphaInactiveMonth);
			
			}
			if(getDate().getTimeInMillis()+(1000*60*60*24)<System.currentTimeMillis()){
				//如果小于当前日期的话也需要置为灰色
				pt.setAlpha(iAlphaInactiveMonth);
			}
			if (!bIsActiveMonth){//不是本月的
				pt.setAlpha(iAlphaInactiveMonth);
				pt.setColor(Color.TRANSPARENT);//透明的没有边框
				}
			canvas.drawRect(rect, pt);
		}
	}

	protected void drawDayNumber(Canvas canvas, boolean bFocused) {
		// draw day number
		pt.setTypeface(null);
		pt.setAntiAlias(true);
		pt.setShader(null);
//		pt.setFakeBoldText(true);//粗体
		pt.setTextSize(fTextSize);

		pt.setUnderlineText(false);
		if (bToday&&isbIsActiveMonth()){
			pt.setUnderlineText(true);
		}

		int iTextPosX = (int) rect.right - (int) pt.measureText(sDate);
		int iTextPosY = (int) rect.bottom + (int) (-pt.ascent())
				- getTextHeight();

		iTextPosX -= ((int) rect.width() >> 1)
				- ((int) pt.measureText(sDate) >> 1);
		iTextPosY -= ((int) rect.height() >> 1) - (getTextHeight() >> 1);

		// draw text
		if (bSelected ) {//|| bFocused
			if (bSelected)
//				pt.setColor(DayStyle.iColorTextSelected);
				pt.setColor(Color.WHITE);
			if (bFocused)
				pt.setColor(Color.WHITE);
//				pt.setColor(DayStyle.iColorTextFocused);
		} else {
//			pt.setColor(DayStyle.getColorText(bHoliday, bToday));
			pt.setColor(Color.BLACK);
		}
//		pt.setColor(Color.rgb(190, 66, 00));
		if (!bIsActiveMonth){//不是本月
			pt.setAlpha(iAlphaInactiveMonth);
			}
		if(bHoliday){//节假日
			pt.setAlpha(iAlphaInactiveMonth);
		}
		if(getDate().getTimeInMillis()+(1000*60*60*24)<System.currentTimeMillis()){
			//如果小于当前日期的话也需要置位灰色
			pt.setAlpha(iAlphaInactiveMonth);
		}
		canvas.drawText(sDate, iTextPosX, iTextPosY + iMargin, pt);

		pt.setUnderlineText(false);
	}

	public boolean IsViewFocused() {
		return (this.isFocused() || bTouchedDown);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(DateWidgetDayCell.this);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
			doItemClick();
		}
		return bHandled;
	}

	public static void startAlphaAnimIn(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}

	protected int getiDateMonth() {
		return iDateMonth;
	}

	protected boolean isbIsActiveMonth() {
		return bIsActiveMonth;
	}
	
	
}

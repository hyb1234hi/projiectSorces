package com.iss.view.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.R;

public class DateWidget extends Activity {
	
	private Window window;
	public static final String DATEWIDGET_SELECT_DATE = "DATEWIDGET_SELECT_DATE";
	private ArrayList<DateWidgetDayCell> days = new ArrayList<DateWidgetDayCell>();
	// private SimpleDateFormat dateMonth = new SimpleDateFormat("MMMM yyyy");
	private Calendar calStartDate = Calendar.getInstance();
	private Calendar calToday = Calendar.getInstance();
	private Calendar calCalendar = Calendar.getInstance();
	private Calendar calSelected = Calendar.getInstance();
	LinearLayout layContent = null;
//	Button btnPrev = null;
//	Button btnToday = null;
//	Button btnNext = null;
	
	TextView btnToday = null;
	RelativeLayout btnPrev = null;
	RelativeLayout btnNext = null;
	
	private int iFirstDayOfWeek = Calendar.MONDAY;
//	private int iFirstDayOfWeek = Calendar.TUESDAY;
	private int iMonthViewCurrentMonth = 0;
	private int iMonthViewCurrentYear = 0;
	public static final int SELECT_DATE_REQUEST = 111;
//	private static final int iDayCellSize = 38;
	private static final int iDayCellSize = 64;
	private static final int iDayCellHeight = 84;
//	private static final int iDayHeaderHeight = 24;
	private static final int iDayHeaderHeight = 40;
	private static final int iTotalWidth = (iDayCellSize * 7);
	private TextView tv;
	private int mYear;
	private int mMonth;
	private int mDay;
	private LinearLayout mLinearLayout;
	
	private int width = 0;
	private int height = 0;
	private Button back;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		iFirstDayOfWeek = Calendar.MONDAY;//历法不同，周日为第一天
//		iFirstDayOfWeek = Calendar.SUNDAY;
		mYear = calSelected.get(Calendar.YEAR);
		mMonth = calSelected.get(Calendar.MONTH);
		mDay = calSelected.get(Calendar.DAY_OF_MONTH);
//		setContentView(generateContentView());
//		setTheme(R.style.CustomNoTitleTheme);
		window = getWindow();
		window.setFeatureInt(Window.FEATURE_NO_TITLE, 0);
//		setContentView(R.layout.layout_course_main);
//		mLinearLayout = (LinearLayout) findViewById(R.id.course_main);
		
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();//屏幕宽度
		height = wm.getDefaultDisplay().getHeight();//屏幕高度
		
		mLinearLayout.addView(generateContentView());
//		back = (Button) findViewById(R.id.course_detail_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateWidget.this.finish();
			}
		});
		
		calStartDate = getCalendarStartDate();
		DateWidgetDayCell daySelected = updateCalendar();
		updateControlsState();
		if (daySelected != null)
			daySelected.requestFocus();		
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	private LinearLayout createLayout(int iOrientation) {
		LinearLayout lay = new LinearLayout(this);
		lay.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		lay.setOrientation(iOrientation);
		return lay;
	}

	private Button createButton(String sText, int iWidth, int iHeight) {
		Button btn = new Button(this);
		btn.setText(sText);
		btn.setLayoutParams(new LayoutParams(iWidth, iHeight));
		return btn;
	}
	
	/**
	 * 日期栏顶部设置
	 */
	private void generateTopButtons(LinearLayout layTopControls) {
//		final int iHorPadding = 24;
//		final int iSmallButtonWidth = 40;
		final ImageView left;
		final ImageView right;
//		//当前日期设置
//		btnToday = createButton("", iTotalWidth - iSmallButtonWidth - iSmallButtonWidth,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
//		btnToday.setGravity(Gravity.CENTER);
//		btnToday.setTextColor(Color.rgb(66, 66, 66));
//		btnToday.setTextSize(18);
//		btnToday.setPadding(iHorPadding, btnToday.getPaddingTop(), iHorPadding,btnToday.getPaddingBottom());
//		btnToday.setBackgroundResource(Color.TRANSPARENT);
//
//		//前一天
//		btnPrev = createButton("" , 18 , 21);
//		btnPrev.setBackgroundResource(R.drawable.calender_left_selected);
//		btnPrev.setGravity(Gravity.CENTER);
//		
//		//后一天
//		btnNext = createButton("" , 18 , 21);
//		btnNext.setBackgroundResource(R.drawable.calender_right_selected);
//		btnNext.setGravity(Gravity.CENTER);
		
		
//		layTopControls = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.calendar_top_linear_title, null);
		btnToday = (TextView) layTopControls.findViewById(R.id.calendar_top_center);
		btnPrev = (RelativeLayout) layTopControls.findViewById(R.id.calendar_top_left);
		btnNext = (RelativeLayout) layTopControls.findViewById(R.id.calendar_top_right);
		
		left = (ImageView)layTopControls.findViewById(R.id.calendar_top_img_left);
		right = (ImageView)layTopControls.findViewById(R.id.calendar_top_img_right);
		// set events
		btnPrev.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				setPrevViewItem();
				left.setOnClickListener(this);
			}
		});
		btnToday.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {

				setTodayViewItem();
//				String s = calToday.get(Calendar.YEAR) + "/"+ (calToday.get(Calendar.MONTH) + 1);
				String s1 = (calToday.get(Calendar.MONTH) + 1) + "月" + calToday.get(Calendar.YEAR);
				btnToday.setText(s1);
			}
		});
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				setNextViewItem();
				right.setOnClickListener(this);
			}
		});

//		layTopControls.setGravity(Gravity.CENTER);
//		layTopControls.addView(btnPrev);
//		layTopControls.addView(btnToday);
//		layTopControls.addView(btnNext);
	}

	private View generateContentView() {
		LinearLayout layMain = createLayout(LinearLayout.VERTICAL);
		layMain.setPadding(0, 0, 0, 0);
//		LinearLayout layTopControls = createLayout(LinearLayout.HORIZONTAL);
		LinearLayout layTopControls = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.calendar_top_linear_title, null);
		//日历顶部选择条 带前进后退键
//		layTopControls.setBackgroundResource(R.drawable.new_course_title);
//		layTopControls.inflate(getApplicationContext(), R.layout.layout_course_top, null);
		layContent = createLayout(LinearLayout.VERTICAL);
		layContent.setPadding(0, 0, 0, 0);
		generateTopButtons(layTopControls);
		generateCalendar(layContent);
		layMain.addView(layTopControls);
		layMain.addView(layContent);

		tv = new TextView(this);
		layMain.addView(tv);
		return layMain;
	}

	private View generateCalendarRow() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		
		for (int iDay = 0; iDay < 7; iDay++) {
			if(width == 480){
				DateWidgetDayCell dayCell = new DateWidgetDayCell(DateWidget.this,iDayCellSize, iDayCellHeight);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}else if(width == 540){
				DateWidgetDayCell dayCell = new DateWidgetDayCell(DateWidget.this,73, 115);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}else if(width == 320){
				DateWidgetDayCell dayCell = new DateWidgetDayCell(DateWidget.this,43, 50);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}
		}
		return layRow;
	}
	
	/**
	 * 日期详情周一至周日
	 * @return
	 */
	private View generateCalendarHeader() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		for (int iDay = 0; iDay < 7; iDay++) {
			if(width == 480){
				DateWidgetDayHeader day = new DateWidgetDayHeader(this,iDayCellSize, iDayHeaderHeight);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				layRow.addView(day);
			}else if(width == 540){
				DateWidgetDayHeader day = new DateWidgetDayHeader(this,73, 108);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				layRow.addView(day);
			}else if(width == 320){
				DateWidgetDayHeader day = new DateWidgetDayHeader(this,43, 50);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				layRow.addView(day);
			}	
		}
		return layRow;
	}

	private void generateCalendar(LinearLayout layContent) {
		layContent.addView(generateCalendarHeader());
		days.clear();
		for (int iRow = 0; iRow < 6; iRow++) {
			layContent.addView(generateCalendarRow());
		}
	}

	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		UpdateStartDateForMonth();

		return calStartDate;
	}
	//刷新日历界面
	private DateWidgetDayCell updateCalendar() {
		DateWidgetDayCell daySelected = null;
		boolean bSelected = false;
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		for (int i = 0; i < days.size(); i++) {
			final int iYear = calCalendar.get(Calendar.YEAR);
			final int iMonth = calCalendar.get(Calendar.MONTH);
			final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
			final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
			DateWidgetDayCell dayCell = days.get(i);
			// check today
			boolean bToday = false;
			if (calToday.get(Calendar.YEAR) == iYear)
				if (calToday.get(Calendar.MONTH) == iMonth)
					if (calToday.get(Calendar.DAY_OF_MONTH) == iDay)
						bToday = true;
			// check holiday
			boolean bHoliday = false;
			if ((iDayOfWeek == Calendar.SATURDAY)
					|| (iDayOfWeek == Calendar.SUNDAY))
				bHoliday = true;
			if ((iMonth == Calendar.JANUARY) && (iDay == 1))
				bHoliday = true;

			dayCell.setData(iYear, iMonth, iDay, bToday, bHoliday,
					iMonthViewCurrentMonth);
			bSelected = false;
			if (bIsSelection)
				if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth)
						&& (iSelectedYear == iYear)) {
					bSelected = true;
				}
			dayCell.setSelected(bSelected);
			if (bSelected)//如果被选中
				daySelected = dayCell;
			calCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		layContent.invalidate();
		return daySelected;
	}

	private void UpdateStartDateForMonth() {
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		UpdateCurrentMonthDisplay();
		// update days for week
		int iDay = 0;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
	}

	private void UpdateCurrentMonthDisplay() {
//		String s = calCalendar.get(Calendar.YEAR) + "/"+ (calCalendar.get(Calendar.MONTH) + 1);// dateMonth.format(calCalendar.getTime());
//		String s1 = (calCalendar.get(Calendar.MONTH) + 1) + "月" + calCalendar.get(Calendar.YEAR);
		String realS = (iMonthViewCurrentMonth + 1) + "月" + iMonthViewCurrentYear;
		btnToday.setText(realS);
		mYear = calCalendar.get(Calendar.YEAR);
	}

	private void setPrevViewItem() {
		iMonthViewCurrentMonth--;
		if (iMonthViewCurrentMonth == -1) {
			iMonthViewCurrentMonth = 11;
			iMonthViewCurrentYear--;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		UpdateStartDateForMonth();
		updateCalendar();

	}

	private void setTodayViewItem() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);
		calStartDate.setTimeInMillis(calToday.getTimeInMillis());
		calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		UpdateStartDateForMonth();
		updateCalendar();
	}

	private void setNextViewItem() {
		iMonthViewCurrentMonth++;
		if (iMonthViewCurrentMonth == 12) {
			iMonthViewCurrentMonth = 0;
			iMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		UpdateStartDateForMonth();
		updateCalendar();

	}

	private DateWidgetDayCell.OnItemClick mOnDayCellClick = new DateWidgetDayCell.OnItemClick() {
		public void OnClick(DateWidgetDayCell item) {
			calSelected.setTimeInMillis(item.getDate().getTimeInMillis());
			item.setSelected(true);
			updateCalendar();
			updateControlsState();//改变选中的状态
//			Intent intent=new Intent(DateWidget.this,CourseActivity.class);
//			int year =calSelected.get(Calendar.YEAR);
//			int month=calSelected.get(Calendar.MONTH)+1;
//			int day=calSelected.get(Calendar.DAY_OF_MONTH);
//			String date = year+"-"+month+"-"+day ;
//			intent.putExtra("selectdate" , date);		
//			setResult(RESULT_OK, intent);
			DateWidget.this.finish();
		}
	};
	
	private void updateControlsState() {
		SimpleDateFormat dateFull = new SimpleDateFormat("d MMMM yyyy");
		mYear = calSelected.get(Calendar.YEAR);
		mMonth = calSelected.get(Calendar.MONTH);
		mDay = calSelected.get(Calendar.DAY_OF_MONTH);
		tv.setText(new StringBuilder().append(mYear).append("/").append(
				format(mMonth + 1)).append("/").append(format(mDay)).append(
				"-----").append(dateFull.format(calSelected.getTime())));	
	}

	private String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}

}

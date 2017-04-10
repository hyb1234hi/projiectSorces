package com.iss.view.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.R;
import com.iss.utils.DimensionPixelUtil;

//public class DateWidget extends LinearLayout
public class DateWidgetView extends LinearLayout{
	private ArrayList<DateWidgetDayCell> days = new ArrayList<DateWidgetDayCell>();
//	public ArrayList<DateWidgetDayCell> daysSelect = new ArrayList<DateWidgetDayCell>();
	/**
	 * 选中的日期列表
	 */
	public ArrayList<Calendar> daysCalSelect = new ArrayList<Calendar>();
	// private SimpleDateFormat dateMonth = new SimpleDateFormat("MMMM yyyy");
	private Calendar calStartDate = Calendar.getInstance();
	private Calendar calToday = Calendar.getInstance();
	private Calendar calCalendar = Calendar.getInstance();
	private Calendar calSelected = Calendar.getInstance();
	LinearLayout layContent = null;
//	Button btnPrev = null;
//	Button btnToday = null;
//	Button btnNext = null;
//	private int iFirstDayOfWeek = Calendar.MONDAY;
	private int iFirstDayOfWeek = Calendar.SUNDAY;
	private int iMonthViewCurrentMonth = 0;
	private int iMonthViewCurrentYear = 0;
	public static final int SELECT_DATE_REQUEST = 111;
	private static final int iDayCellSize = 57;
//	private static final int iDayCellSize = 62;
	private static final int iDayCellHeight = 50;
//	private static final int iDayHeaderHeight = 24;
	private static final int iDayHeaderHeight = 30;//星期的高度
	private static final int iTotalWidth = (iDayCellSize * 7);
	
	private static final int sizeWidthCell = 44;//item的宽度
	private static final int sizeHeightCell = 33;//日期的高度
	private static final int sizeHeightHeader = 15;//星期的高度
	private static final String TAG = "DateWidgetView";
	
	private static  int sizeWidthCellDIP = 0;//item的宽度
	private static  int sizeHeightCellDIP = 0;//日期的高度
	private static  int sizeHeightHeaderDIP = 0;//星期的高度
	
	private TextView tv;
	private int mYear;
	private int mMonth;
	private int mDay;
	private Context mContext;
	private LinearLayout mLinearLayout;
	int screenW;
	TextView btnToday = null;
	RelativeLayout btnPrev = null;
	RelativeLayout btnNext = null;
	/**
	 * 点击某个日期的监听
	 */
	public IDateCellClick iDateCellClick ;
	
	public DateWidgetView(Activity context,LinearLayout linearCalendar){
		super(context);
		this.mContext = context;
		mLinearLayout = linearCalendar;
		screenW = context.getWindowManager().getDefaultDisplay().getWidth();  
		initView(context);
	}
//	@Override
//	public void onCreate(Bundle icicle) {
//		super.onCreate(icicle);
//		iFirstDayOfWeek = Calendar.MONDAY;
//		mYear = calSelected.get(Calendar.YEAR);
//		mMonth = calSelected.get(Calendar.MONTH);
//		mDay = calSelected.get(Calendar.DAY_OF_MONTH);
//		setContentView(generateContentView());
//		calStartDate = getCalendarStartDate();
//		DateWidgetDayCell daySelected = updateCalendar();
//		updateControlsState();
//		if (daySelected != null)
//			daySelected.requestFocus();
//	}
	
	public void initView(Context context){
//		View view = LayoutInflater.from(context).inflate(R.layout.layout_course_main, null);
//		mLinearLayout = (LinearLayout) view.findViewById(R.id.course_main);
//		iFirstDayOfWeek = Calendar.MONDAY;
		iFirstDayOfWeek = Calendar.SUNDAY;
		mYear = calSelected.get(Calendar.YEAR);
		mMonth = calSelected.get(Calendar.MONTH);
		mDay = calSelected.get(Calendar.DAY_OF_MONTH);
		sizeHeightCellDIP = (int)getDimensionPixelSize(sizeHeightCell, context);
		sizeWidthCellDIP = (int)getDimensionPixelSize(sizeWidthCell, context);
		sizeHeightHeaderDIP = (int)getDimensionPixelSize(sizeHeightHeader, context);
		Log.e("date widget view："," sizeHeightCellDIP:"+sizeHeightCellDIP+",sizeWidthCellDIP:"+sizeWidthCellDIP+",sizeHeightHeaderDIP:"+sizeHeightHeaderDIP);
		
		
//		setContentView(generateContentView());
		mLinearLayout.addView(generateContentView());
		calStartDate = getCalendarStartDate();
		DateWidgetDayCell daySelected = updateCalendar();
		tv.setVisibility(View.GONE);//隐藏底部的测试数据
		updateControlsState();
		if (daySelected != null){
			daySelected.requestFocus();
		}
		
		
//		addView(view);
	}
	
	/**
	 * 清空所选择的日期数据
	 */
	public void clear() {
		daysCalSelect.clear();
		updateCalendar();
	}
	/**
	 * 设置点击某个日期的监听
	 */
	public void setDateCellClick(IDateCellClick iDateCellClick){
		this.iDateCellClick = iDateCellClick;
	}
	
	private LinearLayout createLayout(int iOrientation) {
		LinearLayout lay = new LinearLayout(mContext);
		lay.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		lay.setOrientation(iOrientation);
		return lay;
	}

	private Button createButton(String sText, int iWidth, int iHeight) {
		Button btn = new Button(mContext);
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
				String s1 = calToday.get(Calendar.YEAR) + " 年  "+ (calToday.get(Calendar.MONTH) + 1+" 月");
//				String s1 = (calToday.get(Calendar.MONTH) + 1) + "月" + calToday.get(Calendar.YEAR);
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
	
//	private void generateTopButtons(LinearLayout layTopControls) {
//		final int iHorPadding = 24;
//		final int iSmallButtonWidth = 60;
//		btnToday = createButton("", iTotalWidth - iSmallButtonWidth- iSmallButtonWidth,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
//		btnToday.setPadding(iHorPadding, btnToday.getPaddingTop(), iHorPadding,btnToday.getPaddingBottom());
//		btnToday.setBackgroundResource(android.R.drawable.btn_default_small);
//
//		SymbolButton btnPrev = new SymbolButton(mContext,SymbolButton.symbol.arrowLeft);
//		btnPrev.setLayoutParams(new LayoutParams(iSmallButtonWidth,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
//		btnPrev.setBackgroundResource(android.R.drawable.btn_default_small);
//
//		SymbolButton btnNext = new SymbolButton(mContext,SymbolButton.symbol.arrowRight);
//		btnNext.setLayoutParams(new LayoutParams(iSmallButtonWidth,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
//		btnNext.setBackgroundResource(android.R.drawable.btn_default_small);
//
//		// set events
//		btnPrev.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View arg0) {
//				setPrevViewItem();
//			}
//		});
//		btnToday.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View arg0) {
//
//				setTodayViewItem();
//				String s = calToday.get(Calendar.YEAR) + "/"+ (calToday.get(Calendar.MONTH) + 1);
//				btnToday.setText(s);
//			}
//		});
//		btnNext.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View arg0) {
//				setNextViewItem();
//			}
//		});
//
//		layTopControls.setGravity(Gravity.CENTER_HORIZONTAL);
//		layTopControls.addView(btnPrev);
//		layTopControls.addView(btnToday);
//		layTopControls.addView(btnNext);
//	}

	private View generateContentView() {
		LinearLayout layMain = createLayout(LinearLayout.VERTICAL);
//		layMain.setPadding(8, 8, 8, 8);
		layMain.setPadding(0, 0, 0, 0);
//		LinearLayout layTopControls = createLayout(LinearLayout.HORIZONTAL);
		LinearLayout layTopControls = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.calendar_top_linear_title, null);
		layContent = createLayout(LinearLayout.VERTICAL);
//		layContent.setPadding(20, 0, 20, 0);
		layContent.setPadding(10, 0, 10, 0);
		generateTopButtons(layTopControls);
		generateCalendar(layContent);
		int hei = (int) DimensionPixelUtil.getDimensionPixelSize(TypedValue.COMPLEX_UNIT_DIP, 25, mContext);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, hei);
//		params.height = 20;
		layTopControls.setLayoutParams(params);
		
		layMain.addView(layTopControls);
		layMain.addView(layContent);

		tv = new TextView(mContext);
		layMain.addView(tv);
		return layMain;
	}

	private View generateCalendarRow() {
		LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
		Log.w("MyLog", "generateCalendarRow.width : " + getWidth()+"---"+screenW);
		for (int iDay = 0; iDay < 7; iDay++) {
			if(screenW == 480){
				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,iDayCellSize, iDayCellHeight);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}else if(screenW == 540){
				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,66, 60);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}else if(screenW == 320){
				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,37, 32);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}
//			else if(screenW == 720){
//				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,88, 60);
//				dayCell.setItemClick(mOnDayCellClick);
//				days.add(dayCell);
//				layRow.addView(dayCell);
//			}
			else if(screenW == 800){//
				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,100, 70);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}else{//480
				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,sizeWidthCellDIP, sizeHeightCellDIP);
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
			if(screenW == 480){
				DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,iDayCellSize, iDayHeaderHeight);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				day.setBackgroundColor(DayStyle.iColorText);
				layRow.addView(day);
			}else if(screenW == 540){
//				DateWidgetDayCell dayCell = new DateWidgetDayCell(mContext,70, 108);
//				dayCell.setItemClick(mOnDayCellClick);
//				days.add(dayCell);
//				layRow.addView(dayCell);	
				DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,66, 60);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				layRow.addView(day);
			}else if(screenW == 320){
				DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,37, 50);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				layRow.addView(day);
			}
//			else if(screenW == 720){
//				//480
//				DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,88, 30);
//				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
//				day.setData(iWeekDay);
//				day.setBackgroundColor(DayStyle.iColorText);
//				layRow.addView(day);
//			}
			else if(screenW == 800){
				//480
				DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,100, 70);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				day.setBackgroundColor(DayStyle.iColorText);
				layRow.addView(day);
			}else{
				//480
				DateWidgetDayHeader day = new DateWidgetDayHeader(mContext,sizeWidthCellDIP, sizeHeightHeaderDIP);
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				day.setBackgroundColor(DayStyle.iColorText);
				layRow.addView(day);
			}
		}
		return layRow;
	}

	private void generateCalendar(LinearLayout layContent) {
		layContent.addView(generateCalendarHeader());
		days.clear();
		for (int iRow = 0; iRow < 6; iRow++) {
			LinearLayout view = (LinearLayout)generateCalendarRow();
			layContent.addView(view);
//			if(iRow == 5){//最后一行，是否需要显示
//				DateWidgetDayCell cell = (DateWidgetDayCell) view.getChildAt(0);
//				if(cell.getiDateMonth()==iMonthViewCurrentMonth){
//					view.setVisibility(View.VISIBLE);
//					
//					}else{
//						view.setVisibility(View.INVISIBLE);
//						
//					}
//			}
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

	private DateWidgetDayCell updateCalendar() {
		DateWidgetDayCell daySelected = null;
		boolean bSelected = false;
		final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
//		final int iSelectedYear = calSelected.get(Calendar.YEAR);
//		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
//		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
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
			//开始对比是否在选中的list日期中
//			int size = daysSelect.size();
			int size = daysCalSelect.size();
			
			if(size <=0||bHoliday||!dayCell.isbIsActiveMonth()){
				//没有选中的数据
				dayCell.setSelected(false);
			}else
				if(dayCell.getDate().getTimeInMillis()+(1000*60*60*24)<System.currentTimeMillis()){
					//如果小于当前日期的话也不可以选中
					dayCell.setSelected(false);
				}
			else{
			bSelected = false;
//			
//			for(DateWidgetDayCell dayCellTemp : daysSelect){
//				if(dayCellTemp.equals(dayCell)){
			for(Calendar dayCellTemp : daysCalSelect){
				if(dayCellTemp.equals(dayCell.getDate())&&dayCell.isbIsActiveMonth()){
					//存在选中的日期list中
					bSelected = true;
					break;//跳出
				}
			}
//			*/
			dayCell.setSelected(bSelected);
			
		}//暂定结束选中的 list 日期对比
			
			if (bSelected)
				daySelected = dayCell;
			calCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		layContent.invalidate();
//		if(daySelected!=null){
//		if(daySelected.isbIsActiveMonth()){
//			daySelected.setVisibility(View.VISIBLE);
//		}else{
//			daySelected.setVisibility(View.INVISIBLE);
//		}
//		}
		return daySelected;
	}

	private void UpdateStartDateForMonth() {
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());//解决显示日历左右时第一次错误的问题
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
		String s = calCalendar.get(Calendar.YEAR) + " 年  "+ (calCalendar.get(Calendar.MONTH) + 1+" 月");
		Log.e("MyLog", "DateWidget ...... String s" + s);
		btnToday.setText(s);
		mYear = calCalendar.get(Calendar.YEAR);
		iMonthViewCurrentMonth=calCalendar.get(Calendar.MONTH);
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
//		daysSelect.clear();
//		daysCalSelect.clear();
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
//		daysSelect.clear();
//		daysCalSelect.clear();
		updateCalendar();

	}

	/**
	 * 点击某个item时间的监听
	 */
	private DateWidgetDayCell.OnItemClick mOnDayCellClick = new DateWidgetDayCell.OnItemClick() {
		public void OnClick(DateWidgetDayCell item) {
			if(item.isSelected()){
				//取消选择的话
				//默认到1970年1月1日了 
				calSelected.setTimeInMillis(0);
				item.setSelected(false);
				//取消选择的话从list中移除这个对象
//				if(daysSelect.contains(item)){
//					daysSelect.remove(item);
//				}
				if(daysCalSelect.contains(item.getDate())){
					daysCalSelect.remove(item.getDate());
				}
			}else{
				calSelected.setTimeInMillis(item.getDate().getTimeInMillis());
				item.setSelected(true);
				//选择的话添加到选择的list中
//				daysSelect.add(item);
				if(item.isbIsActiveMonth()){
				daysCalSelect.add(item.getDate());
				}
			}
			//设置开放给开发者的点击日期的监听事件
			if(iDateCellClick != null){
				iDateCellClick.onDateCellClick(item);
			}
			updateCalendar();
			updateControlsState();
		}
	};

	/**
	 * 
	 */
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

	/**
	 * 
	 * @param unit    单位dip
	 * @param value  size 大小
	 * @param context
	 * @return    
	 */
	public static float getDimensionPixelSize(float value, Context context) {
		
		return getDimensionPixelSize(TypedValue.COMPLEX_UNIT_DIP,value,context);
	}
	
	/**
	 * 
	 * @param unit    单位
	 * @param value  size 大小
	 * @param context
	 * @return    
	 */
	public static float getDimensionPixelSize(int unit, float value, Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		switch (unit) {
		case TypedValue.COMPLEX_UNIT_PX:
			return value;
		case TypedValue.COMPLEX_UNIT_DIP:
		case TypedValue.COMPLEX_UNIT_SP:
			return TypedValue.applyDimension(unit, value, metrics);
		default:
			throw new IllegalArgumentException("unknow unix");
		}
	}
}

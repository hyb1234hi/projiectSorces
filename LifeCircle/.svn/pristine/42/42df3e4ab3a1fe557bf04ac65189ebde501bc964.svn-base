package com.sinaleju.lifecircle.app.customviews.itemview;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.CommWebViewAct;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.customviews.AlwaysMarqueeTextView;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.dialog.SearchTopBarDialog;
import com.sinaleju.lifecircle.app.model.Model_HomePageTop;
import com.sinaleju.lifecircle.app.model.Model_HomePageTop.Advertising;
import com.sinaleju.lifecircle.app.model.Model_HomePageTop.Weather;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAddFollower;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_HomePageTop extends AbsItemView {
	private static final String TAG = "Item_HomePageTop";
	private RadioGroup mRadioGroup;
	private RadioButton mRadioCurrent;
	private RadioButton mRadioHot;
	private TextView mTxtTopicCount;
	private TextView mTxtPropertyName;
	// private ImageView mImgGuanzhu;
	private ImageView mImgHeader;// 显示头像
	private ImageView mImgBg;// 头部背景
	private View mLoadingFooter;
	// private View mLyotGuanzhu;

	private TextView mTemperatureValue;
	private ImageView mWeatherIcon;
	private AlwaysMarqueeTextView mWeatherDesc;
	private TextView mTemperatureRange;

	private ImageView iv_quanup = null;
	private LinearLayout ll_adv = null;
	private View iv_point = null;

	private TextView mTextData;
	private TextView mTextCalendar;

	private ImageView mFlagDot;
	private View mSearchView;

	private LinearLayout ll_calendar = null;
	private FrameLayout fl_temperature = null;

	private LinearLayout ll_item_home_page = null;

	private boolean isPerHome = true;
	private boolean isProperty = false; // 是否是物业
	private UserBean mUserBean = null;

	public Item_HomePageTop(Context context) {
		super(context);
	}

	@Override
	public void init() {
		initViews();
		setListener();
	}

	/**
	 * 
	 */
	private void initViews() {
		LogUtils.d(TAG, "initViews");
		LayoutInflater.from(getContext()).inflate(R.layout.item_home_page_top, this);
		mTxtPropertyName = (TextView) findViewById(R.id.txtWuye);
		// mImgGuanzhu = (ImageView) findViewById(R.id.imgGuanzhu);

		mLoadingFooter = findViewById(R.id.foot);
		// mLyotGuanzhu = findViewById(R.id.lyotGuanzhu);
		mImgHeader = (ImageView) findViewById(R.id.imgHeader);
		mImgBg = (ImageView) findViewById(R.id.iv_bg_community);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		mRadioCurrent = (RadioButton) findViewById(R.id.radioCurrent);
		mTxtTopicCount = (TextView) findViewById(R.id.txtTopicNum);
		mRadioHot = (RadioButton) findViewById(R.id.radioHot);

		// temperature
		mWeatherIcon = (ImageView) findViewById(R.id.imgWeahterIcon);
		mWeatherDesc = (AlwaysMarqueeTextView) findViewById(R.id.txtWeatherDesc);
		mTemperatureValue = (TextView) findViewById(R.id.txtTemperatureValue);
		mTemperatureRange = (TextView) findViewById(R.id.txtTemperatureRange);

		iv_quanup = (ImageView) findViewById(R.id.iv_quanup);
		ll_adv = (LinearLayout) findViewById(R.id.ll_adv);
		iv_point = (View) findViewById(R.id.iv_point);

		mTextData = (TextView) findViewById(R.id.tv_home_data);
		mTextData.setText(dateToWeek());
		mTextCalendar = (TextView) findViewById(R.id.tv_Calendar);
		mTextCalendar.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));// 加粗
		mTextCalendar.getPaint().setFakeBoldText(true);// 加粗
		mTextCalendar.setText(getDate());

		ll_calendar = (LinearLayout) findViewById(R.id.ll_calendar);
		fl_temperature = (FrameLayout) findViewById(R.id.fl_temperature);
		ll_item_home_page = (LinearLayout) findViewById(R.id.ll_item_home_page);

		setInVisible();
		// search
		mSearchView = findViewById(R.id.searchView);

		// dot
		mFlagDot = (ImageView) findViewById(R.id.imgFlagDot);

		// set
		mRadioCurrent.setChecked(true);
		LogUtils.d(TAG, "initViews  222   ");
		mUserBean = AppContext.curUser();
		if (mUserBean != null) {
			if (mUserBean.getType() == 0) {
				isPerHome = true;
			} else if (mUserBean.getType() == 1) {
				isPerHome = false;
				isProperty = false;
			} else if (mUserBean.getType() == 2) {
				isPerHome = false;
				isProperty = true;
			}
			setUserHead(mUserBean);

			if (mUserBean.getCurCommunity().getType() == 2) {
				ll_adv.setVisibility(View.VISIBLE);
				iv_point.setVisibility(View.VISIBLE);
			} else {
				ll_adv.setVisibility(View.GONE);
				iv_point.setVisibility(View.GONE);
			}
		}

	}

	public LinearLayout getLl_adv() {
		return ll_adv;
	}

	public void setLl_adv(LinearLayout ll_adv) {
		this.ll_adv = ll_adv;
	}

	public static final int WEEKDAYS = 7;

	public static String[] WEEK = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

	/**
	 * 日期变量转成对应的星期字符串
	 * 
	 * @param date
	 * @return
	 */
	public String dateToWeek() {
		Calendar calendar = Calendar.getInstance();
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex < 1 || dayIndex > WEEKDAYS) {
			return null;
		}
		LogUtils.e(TAG, "dateToWeek ----- " + WEEK[dayIndex - 1]);
		return WEEK[dayIndex - 1];
	}

	public String getDate() {
		Calendar calendar = Calendar.getInstance();
		int dayIndex = calendar.get(Calendar.DATE);
		return dayIndex + "";
	}

	// 刷新完 显示
	public void setVisible() {
		ll_calendar = (LinearLayout) findViewById(R.id.ll_calendar);
		fl_temperature = (FrameLayout) findViewById(R.id.fl_temperature);
		ll_item_home_page = (LinearLayout) findViewById(R.id.ll_item_home_page);
		if (ll_calendar != null)
			ll_calendar.setVisibility(View.VISIBLE);
		if (fl_temperature != null)
			fl_temperature.setVisibility(View.VISIBLE);
		if (ll_item_home_page != null)
			ll_item_home_page.setVisibility(View.VISIBLE);
	}

	// 刷新的时候 隐藏
	public void setInVisible() {
		ll_calendar = (LinearLayout) findViewById(R.id.ll_calendar);
		fl_temperature = (FrameLayout) findViewById(R.id.fl_temperature);
		ll_item_home_page = (LinearLayout) findViewById(R.id.ll_item_home_page);
		if (ll_calendar != null)
			ll_calendar.setVisibility(View.INVISIBLE);
		if (fl_temperature != null)
			fl_temperature.setVisibility(View.INVISIBLE);
		if (ll_item_home_page != null)
			ll_item_home_page.setVisibility(View.INVISIBLE);
	}

	private void setListener() {

		LogUtils.d(TAG, "setListener  111   ");
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == mRadioCurrent.getId()) {
					mFlagDot.setBackgroundResource(R.drawable.ic_blue_dot);
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_HOME_FRAGMENT_SWITCH_HOT_AND_CURRENT_MSG, true);
				} else {
					mFlagDot.setBackgroundResource(R.drawable.ic_orange_dot);
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_HOME_FRAGMENT_SWITCH_HOT_AND_CURRENT_MSG, false);
				}
			}
		});

		mTxtPropertyName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!AppContext.isVisitor()) {
					AppContext.gotoIndexActivity(getContext(), 2/* 物业type */, AppContext.curUser()
							.getCurCommunity().getProperty_id());
				}
			}
		});

		mSearchView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SearchTopBarDialog.pop(getContext());
				// 监控软键盘状态，弹起时隐藏 底部两个按钮，隐藏时显示底部按钮
			}
		});

		mImgHeader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (AppContext.isValid(getContext())) {
					mUserBean = AppContext.curUser();
					if (mUserBean != null) {
						if (mUserBean.getType() == 0) {
							isPerHome = true;
						} else if (mUserBean.getType() == 1) {
							isPerHome = false;
							isProperty = false;
						} else if (mUserBean.getType() == 2) {
							isPerHome = false;
							isProperty = true;
						}
						// setUserHead(mUserBean);
					}
					if (isPerHome) {
						((HomeActivity) getContext()).gotoPersonalPageFromHome(mUserBean.getUid(),
								true);
					} else {
						((HomeActivity) getContext()).gotoBusinessPageFromHome(mUserBean.getUid(),
								isProperty, true);
					}
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_CLICK_HOMEPAGE_TOP);
				}
			}
		});
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

	@Override
	protected void toSet(int type, BaseModel model) {
		final Model_HomePageTop m = (Model_HomePageTop) model;
		//
		setTopicCount(m);
		//
		setPropertyName(m);
		//
		// setGuanzhuButton(m);

		// weather
		setWeahter(m);
		setAdvert(m);

		if (ll_adv == null) {
			ll_adv = (LinearLayout) findViewById(R.id.ll_adv);
		}
//		LogUtils.e(TAG, "m ：： " + m + "  m.getmAdvertising()  ：： " + m.getmAdvertising());
//		if(m == null || m.getmAdvertising() == null ){
////			ll_adv.setVisibility(View.GONE);
//			if(iv_quanup == null)
//				iv_quanup = (ImageView) findViewById(R.id.iv_quanup);
//			iv_quanup.setVisibility(View.GONE);
//		}
		ll_adv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(m != null || m.getmAdvertising() != null && m.getmAdvertising().getHerf() != null && !m.getmAdvertising().getHerf().equals("")){
					Intent intent = new Intent(getContext(), CommWebViewAct.class);
					intent.putExtra("ticket_url", m.getmAdvertising().getHerf());
					getContext().startActivity(intent);
				}
			}
		});
		// notify homefragment and send this pointer to homefragment
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_HOME_PAGE_TOP_VIEW_DIDLOAD, this);
	}

	private void setWeahter(Model_HomePageTop m) {

		Weather w = m.getWeather();

		if (w == null)
			return;

		//
		setTemperatureRange(w);

		// icon
		setWeatherIcon(w);

		// desc
		setWeatherDesc(w);

		// temp
		setWeatherTemp(w);
		setVisible();
	}

	private void setAdvert(Model_HomePageTop m) {
		Advertising a = m.getmAdvertising();
		if (a == null)
			return;
		setAdvertImage(a);
	}

	private void setAdvertImage(Advertising a) {
		if (a == null)
			return;
		Log.v(TAG, "a.getImg() :: " + a.getImg());
		if (!TextUtils.isEmpty(a.getImg()) && !a.getImg().equals("null")) {
			// ImageLoader.getInstance((Activity) getContext()).displayImage(
			// a.getImg(),
			// iv_quanup);
			if (iv_quanup == null) {
				iv_quanup = (ImageView) findViewById(R.id.iv_quanup);
			}
			ImageLoader.getInstance((Activity) getContext()).displayImage(a.getImg(), iv_quanup,
					SimpleImageLoaderOptions.getOptions(0, false));
		}else {
			if (iv_quanup == null) {
				iv_quanup = (ImageView) findViewById(R.id.iv_quanup);
			}
			iv_quanup.setVisibility(View.GONE);
//			iv_quanup
//			 .setImageResource(R.drawable.iv_adv);
		}
		// iv_quanup.setImageBitmap(bm);

	}

	private void setWeatherTemp(Weather w) {
		LogUtils.e(TAG, "setWeatherTemp  setWeahter  :: " + w.getTemperature() + "");
		mTemperatureValue.setText(w.getTemperature() + "");
	}

	private void setWeatherDesc(Weather w) {
		mWeatherDesc.setText(w.getDesc());
	}

	private void setWeatherIcon(Weather w) {
		int i = w.getIcon();
		int resId = getResources().getIdentifier("weather_" + i, "drawable",
				"com.sinaleju.lifecircle");
		mWeatherIcon.setImageResource(resId);
	}

	// 设置头像
	private void setUserHead(UserBean userBean) {
		if (!TextUtils.isEmpty(userBean.getHeaderUrl()) && !userBean.getHeaderUrl().equals("null")) {
			ImageLoader.getInstance((Activity) getContext()).displayImage(
					userBean.getHeaderUrl(),
					mImgHeader,
					SimpleImageLoaderOptions.getRoundImageOptions(
							PublicUtils.getUserIndexDefaultHeadImage(userBean.getType()), 100),
					new FadeInImageLoadingListener(mImgHeader));
		} else {
			mImgHeader
					.setImageResource(PublicUtils.getUserIndexDefaultHeadImage(userBean.getType()));
		}
	}

	/**
	 * @param w
	 * 
	 */
	private void setTemperatureRange(Weather w) {

		// test
		String lowTemp = w.getTemp_low() + "";
		String highTemp = w.getTemp_high() + "";
		String spanString = lowTemp + "    " + highTemp + "  ";
		SpannableString span = new SpannableString(spanString);
		span.setSpan(new ForegroundColorSpan(0xff37E1D3), 0, lowTemp.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		span.setSpan(new ForegroundColorSpan(0xffFF7D1D), lowTemp.length() + 4, lowTemp.length()
				+ 4 + highTemp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		span.setSpan(new ImageSpan(getContext(), R.drawable.ic_weather_temp_low,
				ImageSpan.ALIGN_BASELINE), lowTemp.length() + 1, lowTemp.length() + 2,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		span.setSpan(new ImageSpan(getContext(), R.drawable.ic_weather_temp_high,
				ImageSpan.ALIGN_BASELINE), spanString.length() - 1, spanString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		mTemperatureRange.setText(span);
	}

	/**
	 * @param m
	 */
	private void setTopicCount(final Model_HomePageTop m) {
		String count = m.getTopicCount() + "";
		String spanString = "共 " + count + " 条信息";
		SpannableString span = new SpannableString(spanString);
		span.setSpan(new ForegroundColorSpan(0xffffffff), 2, 2 + count.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mTxtTopicCount.setText(span);
	}

	/**
	 * @param m
	 */
	public void setTopicCount(final String count) {
		String spanString = "共 " + count + " 条信息";
		SpannableString span = new SpannableString(spanString);
		span.setSpan(new ForegroundColorSpan(0xffffffff), 2, 2 + count.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		LogUtils.d(TAG, "" + spanString);
		mTxtTopicCount.setText(span);
	}

	/**
	 * @param m
	 */
	private void setPropertyName(final Model_HomePageTop m) {
		mTxtPropertyName.setText(m.getProperty_name() == null
				|| m.getProperty_name().equals("null") ? "" : m.getProperty_name());
	}

	private RSAddFollower rs = null;

	/**
	 * @param m
	 */
	// private void setGuanzhuButton(final Model_HomePageTop m) {
	// mImgGuanzhu.setBackgroundResource(m.getFollowType() == 0 ?
	// R.drawable.selector_home_page_jiaguanzhu : m.getFollowType() == 1 ?
	// R.drawable.selector_home_page_yiguanzhu
	// : R.drawable.selector_home_page_huxiangguanzhu);
	// if (m.getProperty_id() == AppConst.NULL_INT || m.getProperty_id() == 0 ||
	// (!AppContext.isVisitor() && AppContext.curUser()// 当前user就是本小区的物业
	// .getUid() == m.getProperty_id())) {
	// mImgGuanzhu.setVisibility(View.GONE);
	// return;
	// }
	// mImgGuanzhu.setVisibility(View.GONE);
	// mImgGuanzhu.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// if (rs != null) {
	// return;
	// }
	// if (AppContext.isValid((Activity) getContext())) {
	// int t = m.getFollowType();
	// final int type = t == 0 ? 1 : 0;
	// rs = new RSAddFollower();
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("user_id", AppContext.curUser().getUid() + "");
	// map.put("visitor_id", m.getProperty_id() + "");
	// map.put("type", type + "");
	// rs.setParams(map);
	// rs.setOnSuccessHandler(new OnSuccessHandler() {
	//
	// @Override
	// public void onSuccess(Object result) {
	// rs = null;
	// JSONObject obj = null;
	// try {
	// obj = new JSONObject(result.toString());
	// int status = obj.optInt("status");
	// m.setFollowType(status);
	// mImgGuanzhu.setBackgroundResource(m.getFollowType() == 0 ?
	// R.drawable.selector_home_page_jiaguanzhu
	// : m.getFollowType() == 1 ? R.drawable.selector_home_page_yiguanzhu :
	// R.drawable.selector_home_page_huxiangguanzhu);
	//
	// } catch (JSONException e) {
	// LogUtils.e(TAG, "", e);
	// }
	//
	// }
	// });
	// rs.setOnFaultHandler(new OnFaultHandler() {
	//
	// @Override
	// public void onFault(Exception ex) {
	// rs = null;
	// LogUtils.e(TAG, "", ex);
	//
	// }
	// });
	// rs.asyncExecute(getContext());
	// }
	// }
	// });
	// }

	public void showLoading() {
		mLoadingFooter.setVisibility(View.VISIBLE);
	}

	public void dismissLoading() {
		mLoadingFooter.setVisibility(View.GONE);
	}

	public ImageView getmImgHeader() {
		return mImgHeader;
	}

}

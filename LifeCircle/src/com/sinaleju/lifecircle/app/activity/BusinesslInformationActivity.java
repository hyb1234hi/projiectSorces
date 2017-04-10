package com.sinaleju.lifecircle.app.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.loghandler.Log;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpUtils;
import com.loopj.android.http.RequestParams;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.camera.ADCamera;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog.WheelActor;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelFactory;
import com.sinaleju.lifecircle.app.database.entity.MerchantCategoryBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantSubcategoryBean;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.model.BusImageModel;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageImage;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageSpecials;
import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSBusinessInfoEdit;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSPropertyInfoEdit;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class BusinesslInformationActivity extends BaseActivity {

	private EditText mEditName, mEditPhone, mEditBus, mEditTraffic, mEditPerCapita, mEditIntro,
			mEditTitle, mEditListPrice, mEditSpePrice, mEditSpeIntro;
	private TextView mLoginNum, mLoginType, mIdentity, mIndustry, mStyle, mMap, mTopMor, mTopAfter,
			mBotMor, mBotAfter;
	private Button mBtnName, mBtnIdentity, mBtnPhone, mBtnAddress, mBtnMap, mBtnTraffic,
			mBtnPerCapita, mBtnIntro, mBtnTitle, mBtnListPrice, mBtnSpePrice, mBtnSpeIntro;
	private RelativeLayout mIndustryLayout, mStyleLayout, mMapAddressLayout, mTopMorLayout,
			mTopAfterLayout, mBotMorLayout, mBotAfterLayout;
	private ImageView mBusTopOne, mBusTopTwo, mBusTopThree, mBusBotOne, mBusBotTwo, mBusBotThree,
			mSpeTop, mSpeBotLeft, mSpeBotMid, mSpeBotRight;
	private String[] mImagePaths = new String[10];
	private String[] mImageUrls = new String[10];
	private int mCurrentIndex = 0;
	private ImageView mCurrentImage = null;
	private ADCamera mCamera = ADCamera.builder(this);

	private String[] mStyleArray = new String[] { "自营", "连锁", "公共服务机构", "其他" };

	private String[] mDateLeftArray = new String[] { "00:00", "01:00", "02:00", "03:00", "04:00",
			"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
			"14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
			"23:00" };
	private String[] mDateRightArray = new String[] { "01:00", "02:00", "03:00", "04:00", "05:00",
			"06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
			"15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00",
			"24:00" };

	private UserBean mUserBean = null;
	private boolean isProperty = false; // 是否是物业
	private TextView mTypeText, mAddressName, mBusIntroduce, mBusImage, mHoursTime, mSpeTitle;
	private RelativeLayout mTraLayout, mPerCapitaLayout;
	private LinearLayout mSpeLayout;
	private int normalAmS = -1;
	private int normalAmE = -1;
	private int normalPmS = -1;
	private int normalPmE = -1;
	private int haltAmS = -1;
	private int haltAmE = -1;
	private int haltPmS = -1;
	private int haltPmE = -1;
	private double mLon = -1;
	private double mLat = -1;

	private String addressName = "";
	private ImageView dividerline1, dividerline2, dividerline3, dividerline4;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_business_information;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mEditName = (EditText) findViewById(R.id.bus_name_edit);
		mEditPhone = (EditText) findViewById(R.id.servic_phone_edit);
		mEditBus = (EditText) findViewById(R.id.bus_address_edit);
		mEditTraffic = (EditText) findViewById(R.id.traffic_address_edit);
		mEditPerCapita = (EditText) findViewById(R.id.per_capita_edit);
		mEditIntro = (EditText) findViewById(R.id.bus_introduce_edit);
		mEditTitle = (EditText) findViewById(R.id.title_edit);
		mEditListPrice = (EditText) findViewById(R.id.list_price_edit);
		mEditSpePrice = (EditText) findViewById(R.id.pricing_edit);
		mEditSpeIntro = (EditText) findViewById(R.id.specials_edit);

		mLoginNum = (TextView) findViewById(R.id.bus_login_number);
		mLoginType = (TextView) findViewById(R.id.bus_login_type);
		mIdentity = (TextView) findViewById(R.id.identity_type);
		mIndustry = (TextView) findViewById(R.id.industry_type);
		mStyle = (TextView) findViewById(R.id.style_type);
		mMap = (TextView) findViewById(R.id.map_address_edit);
		mTopMor = (TextView) findViewById(R.id.top_morning_edit);
		mTopAfter = (TextView) findViewById(R.id.top_afternoon_edit);
		mBotMor = (TextView) findViewById(R.id.bottom_morning_edit);
		mBotAfter = (TextView) findViewById(R.id.bottom_afternoon_edit);

		mBtnName = (Button) findViewById(R.id.bus_name_edit_icon);
		mBtnIdentity = (Button) findViewById(R.id.identity_type_btn);
		mBtnPhone = (Button) findViewById(R.id.servic_phone_icon);
		mBtnAddress = (Button) findViewById(R.id.bus_address_icon);
		mBtnMap = (Button) findViewById(R.id.map_address_icon);
		mBtnTraffic = (Button) findViewById(R.id.traffic_address_icon);
		mBtnPerCapita = (Button) findViewById(R.id.per_capita_icon);
		mBtnIntro = (Button) findViewById(R.id.bus_introduce_icon);
		mBtnTitle = (Button) findViewById(R.id.title_edit_icon);
		mBtnListPrice = (Button) findViewById(R.id.list_price_icon);
		mBtnSpePrice = (Button) findViewById(R.id.pricing_icon);
		mBtnSpeIntro = (Button) findViewById(R.id.specials_icon);

		mIndustryLayout = (RelativeLayout) findViewById(R.id.industry_type_layout);
		mStyleLayout = (RelativeLayout) findViewById(R.id.style_type_layout);
		mMapAddressLayout = (RelativeLayout) findViewById(R.id.map_address_layout);
		mTopMorLayout = (RelativeLayout) findViewById(R.id.top_morning_layout);
		mTopAfterLayout = (RelativeLayout) findViewById(R.id.top_afternoon_layout);
		mBotMorLayout = (RelativeLayout) findViewById(R.id.bottom_morning_layout);
		mBotAfterLayout = (RelativeLayout) findViewById(R.id.bottom_afternoon_layout);

		mBusTopOne = (ImageView) findViewById(R.id.top_one_image);
		mBusTopTwo = (ImageView) findViewById(R.id.top_two_image);
		mBusTopThree = (ImageView) findViewById(R.id.top_three_image);
		mBusBotOne = (ImageView) findViewById(R.id.bottom_one_image);
		mBusBotTwo = (ImageView) findViewById(R.id.bottom_two_image);
		mBusBotThree = (ImageView) findViewById(R.id.bottom_three_image);
		mSpeTop = (ImageView) findViewById(R.id.specials_image);
		mSpeBotLeft = (ImageView) findViewById(R.id.specials_left_image);
		mSpeBotMid = (ImageView) findViewById(R.id.specials_mid_image);
		mSpeBotRight = (ImageView) findViewById(R.id.specials_right_image);

		mTypeText = (TextView) findViewById(R.id.bus_type_text);
		mBusIntroduce = (TextView) findViewById(R.id.bus_introduce);
		mBusImage = (TextView) findViewById(R.id.bus_image);
		mAddressName = (TextView) findViewById(R.id.bus_address_name);
		mHoursTime = (TextView) findViewById(R.id.hours_time);
		mSpeTitle = (TextView) findViewById(R.id.spe_title);
		mTraLayout = (RelativeLayout) findViewById(R.id.traffic_address_layout);
		mPerCapitaLayout = (RelativeLayout) findViewById(R.id.per_capita_layout);
		mSpeLayout = (LinearLayout) findViewById(R.id.spe_layout);

		dividerline1 = (ImageView) findViewById(R.id.dividerline_one);
		dividerline2 = (ImageView) findViewById(R.id.dividerline_two);
		dividerline3 = (ImageView) findViewById(R.id.dividerline_three);
		dividerline4 = (ImageView) findViewById(R.id.dividerline_four);

		initImageWidAndHei();
		setUserData();
		setPropertyView();
	}

	private void initImageWidAndHei() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int big = PublicUtils.dip2px(mActivity, 150);
		setImageWidthAndHeight(dm, mBusTopOne, big);
		setImageWidthAndHeight(dm, mBusTopTwo, big);
		setImageWidthAndHeight(dm, mBusTopThree, big);
		setImageWidthAndHeight(dm, mBusBotOne, big);
		setImageWidthAndHeight(dm, mBusBotTwo, big);
		setImageWidthAndHeight(dm, mBusBotThree, big);
		int small = PublicUtils.dip2px(mActivity, 100);
		setImageWidthAndHeight(dm, mSpeBotLeft, small);
		setImageWidthAndHeight(dm, mSpeBotMid, small);
		setImageWidthAndHeight(dm, mSpeBotRight, small);
	}

	private void setPropertyView() {
		if (isProperty) {
			mIndustryLayout.setVisibility(View.GONE);
			mStyleLayout.setVisibility(View.GONE);
			mBtnIdentity.setVisibility(View.GONE);
			mIdentity.setText("认证成功");
			mTypeText.setText("物业公司主页信息");
			mAddressName.setText("办公地址");
			mHoursTime.setText("办公\n时间");
			mBusIntroduce.setText("公司介绍");
			mBusImage.setText("公司图片");
			mTraLayout.setVisibility(View.GONE);
			mPerCapitaLayout.setVisibility(View.GONE);
			mSpeTitle.setVisibility(View.GONE);
			mSpeLayout.setVisibility(View.GONE);
			dividerline1.setVisibility(View.GONE);
			dividerline2.setVisibility(View.GONE);
			dividerline3.setVisibility(View.GONE);
			dividerline4.setVisibility(View.GONE);
		}
	}

	private void setUserData() {

		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		mTitleBar.initRightButtonTextOrResId(0, R.drawable.selector_topbar_save_button);
		mTitleBar.setTitleName("商家资料");

		mUserBean = AppContext.curUser();
		if (mUserBean.getType() == 1) {
			mTitleBar.setTitleName("商家资料");
			mLoginType.setText("商家");
			isProperty = false;
		} else if (mUserBean.getType() == 2) {
			mTitleBar.setTitleName("物业资料");
			mLoginType.setText("物业公司");
			isProperty = true;
		}

		if (mUserBean != null) {
			if (!TextUtils.isEmpty(mUserBean.getName())) {
				mEditName.setText(mUserBean.getName());
			} else {
				mEditName.setText("");
			}
			if (!TextUtils.isEmpty(mUserBean.getLogin_name())) {
				mLoginNum.setText(mUserBean.getLogin_name());
			} else {
				mLoginNum.setText("");
			}
			if (!isProperty) {
				if (mUserBean.getIs_auth() == 0) {
					mIdentity.setText("未认证");
				} else if (mUserBean.getIs_auth() == 1) {
					mIdentity.setText("待认证");
					mBtnIdentity.setVisibility(View.GONE);
				} else if (mUserBean.getIs_auth() == 2) {
					mIdentity.setText("已认证");
					mBtnIdentity.setVisibility(View.GONE);
				}
				if (!TextUtils.isEmpty(mUserBean.getCategory())
						&& !TextUtils.isEmpty(mUserBean.getSubcategory())) {
					mIndustryLeft = mUserBean.getCategory();
					mIndustryRight = mUserBean.getSubcategory();
					mIndustry.setText(mUserBean.getCategory() + "-" + mUserBean.getSubcategory());
				} else {
					mIndustry.setText("");
				}
				if (!TextUtils.isEmpty(mUserBean.getBiz_method())) {
					int type = Integer.parseInt(mUserBean.getBiz_method()) - 1;
					if (type >= 0 && type < mStyleArray.length) {
						mStyle.setText(mStyleArray[type]);
					}
				} else {
					mStyle.setText("");
				}
				if (!TextUtils.isEmpty(mUserBean.getTraffic_routes())) {
					mEditTraffic.setText(mUserBean.getTraffic_routes());
				} else {
					mEditTraffic.setText("");
				}
				if (!TextUtils.isEmpty(mUserBean.getPer_capita())) {
					mEditPerCapita.setText(mUserBean.getPer_capita());
				} else {
					mEditPerCapita.setText("");
				}
				setBusSpecials();
			}

			if (!TextUtils.isEmpty(mUserBean.getPhone())) {
				mEditPhone.setText(mUserBean.getPhone());
			} else {
				mEditPhone.setText("");
			}
			if (!TextUtils.isEmpty(mUserBean.getAddress())) {
				mEditBus.setText(mUserBean.getAddress());
			} else {
				mEditBus.setText("");
			}
			if (!TextUtils.isEmpty(mUserBean.getBusLocationText())) {
				mMap.setText(mUserBean.getBusLocationText());
				addressName = mUserBean.getBusLocationText();
			} else {
				mMap.setText("");
			}
			if (!TextUtils.isEmpty(mUserBean.getLongitude())) {
				mLon = handStringToDouble(mUserBean.getLongitude());
			}
			if (!TextUtils.isEmpty(mUserBean.getLatitude())) {
				mLat = handStringToDouble(mUserBean.getLatitude());
			}
			setBusTime();
			setBusImages();
		}

	}

	private double handStringToDouble(String text) {
		double temp = -1;
		try {
			temp = Double.parseDouble(text);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.d("zm", "String To Double Error");
			return temp;
		}
		return temp;
	}

	@Override
	protected void initData() {

	}

	private void setBusSpecials() {
		Model_BusinessPageSpecials busSpecials = mUserBean.getBusSpecials();
		if (busSpecials != null) {
			if (!TextUtils.isEmpty(busSpecials.getTitle())) {
				mEditTitle.setText(busSpecials.getTitle());
			} else {
				mEditTitle.setText("");
			}
			if (!TextUtils.isEmpty(busSpecials.getHeight_price())) {
				mEditListPrice.setText(busSpecials.getHeight_price());
			} else {
				mEditListPrice.setText("");
			}
			if (!TextUtils.isEmpty(busSpecials.getLow_price())) {
				mEditSpePrice.setText(busSpecials.getLow_price());
			} else {
				mEditSpePrice.setText("");
			}
			if (!TextUtils.isEmpty(busSpecials.getContent())) {
				mEditSpeIntro.setText(busSpecials.getContent());
			} else {
				mEditSpeIntro.setText("");
			}
			setBusSpeImages(busSpecials);
		}
	}

	private void setBusSpeImages(Model_BusinessPageSpecials busSpecials) {
		List<BusImageModel> images = busSpecials.getImages();
		for (int i = 0; i < images.size(); i++) {
			BusImageModel model = images.get(i);
			setSpeImageToImageView(model);
		}
	}

	private void setSpeImageToImageView(BusImageModel model) {
		ImageView imageView = null;
		if (TextUtils.isEmpty(model.getPosition())) {
			return;
		}
		int pos = Integer.parseInt(model.getPosition());
		mImageUrls[pos + 5] = model.getImageUrl();
		switch (pos) {
		case 1:
			imageView = mSpeTop;
			break;
		case 2:
			imageView = mSpeBotLeft;
			break;
		case 3:
			imageView = mSpeBotMid;
			break;
		case 4:
			imageView = mSpeBotRight;
			break;
		default:
			break;
		}
		if (imageView != null && !TextUtils.isEmpty(model.getImageUrl())) {
			PublicUtils.loadHeadImage(imageView, model.getImageUrl(), 0);
		}
	}

	private void setBusImages() {
		Model_BusinessPageImage busImages = mUserBean.getBusImages();
		if (busImages != null) {
			List<BusImageModel> images = busImages.getImages();
			for (int i = 0; i < images.size(); i++) {
				BusImageModel model = images.get(i);
				setBusImageToImageView(model);
			}
		}
	}

	private void setBusImageToImageView(BusImageModel model) {
		ImageView imageView = null;
		if (TextUtils.isEmpty(model.getPosition())) {
			return;
		}
		int pos = Integer.parseInt(model.getPosition());
		mImageUrls[pos - 1] = model.getImageUrl();
		switch (pos) {
		case 1:
			imageView = mBusTopOne;
			break;
		case 2:
			imageView = mBusTopTwo;
			break;
		case 3:
			imageView = mBusTopThree;
			break;
		case 4:
			imageView = mBusBotOne;
			break;
		case 5:
			imageView = mBusBotTwo;
			break;
		case 6:
			imageView = mBusBotThree;
			break;
		default:
			break;
		}
		if (imageView != null && !TextUtils.isEmpty(model.getImageUrl())) {
			PublicUtils.loadHeadImage(imageView, model.getImageUrl(), 0);
		}
	}

	private void setBusTime() {
		if (!TextUtils.isEmpty(mUserBean.getNormal_am_start())
				&& !TextUtils.isEmpty(mUserBean.getNormal_am_end())) {
			mTopMor.setText(setIntroduceTime(mUserBean.getNormal_am_start()) + "-"
					+ setIntroduceTime(mUserBean.getNormal_am_end()));
		} else {
			mTopMor.setText("");
		}

		if (!TextUtils.isEmpty(mUserBean.getNormal_pm_start())
				&& !TextUtils.isEmpty(mUserBean.getNormal_pm_end())) {
			mTopAfter.setText(setIntroduceTime(mUserBean.getNormal_pm_start()) + "-"
					+ setIntroduceTime(mUserBean.getNormal_pm_end()));
		} else {
			mTopAfter.setText("");
		}

		if (!TextUtils.isEmpty(mUserBean.getHalt_am_start())
				&& !TextUtils.isEmpty(mUserBean.getHalt_am_end())) {
			mBotMor.setText(setIntroduceTime(mUserBean.getHalt_am_start()) + "-"
					+ setIntroduceTime(mUserBean.getHalt_am_end()));
		} else {
			mBotMor.setText("");
		}

		if (!TextUtils.isEmpty(mUserBean.getHalt_pm_start())
				&& !TextUtils.isEmpty(mUserBean.getHalt_pm_end())) {
			mBotAfter.setText(setIntroduceTime(mUserBean.getHalt_pm_start()) + "-"
					+ setIntroduceTime(mUserBean.getHalt_pm_end()));
		} else {
			mBotAfter.setText("");
		}

		if (!TextUtils.isEmpty(mUserBean.getIntroduction())) {
			mEditIntro.setText(mUserBean.getIntroduction());
		} else {
			mEditIntro.setText("");
		}
	}

	private String setIntroduceTime(String timeString) {
		int time = 0;
		try {
			time = Integer.parseInt(timeString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		StringBuffer sbBuffer = new StringBuffer();
		if (time < 10) {
			sbBuffer.append("0" + time + ":00");
		} else {
			sbBuffer.append(time + ":00");
		}
		return sbBuffer.toString();
	}

	private boolean scale = true, returndata = false, noFaceDetection = false;

	private void gotoGallery(int aspectX, int aspectY, int outputX, int outputY) {
		mCamera.setDefultSmallImageUri(String.valueOf(System.currentTimeMillis()));
		mCamera.startGalleryForPhoto(aspectX, aspectY, outputX, outputY, scale, returndata,
				noFaceDetection);
	}

	private void checkData(String content, String listPrice, String spePrice) {
		if (TextUtils.isEmpty(content.trim())) {
			showToast("昵称不能为空");
			return;
		}
		if (!TextUtils.isEmpty(listPrice) && !TextUtils.isEmpty(spePrice)) {
			try {
				if (Float.parseFloat(spePrice) > Float.parseFloat(listPrice)) {
					showToast("优惠价格不能大于原价");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				showToast("您输入的内容无法转换成数字");
			}
		}
	}

	private void editBusinessInfo() {

		String content = mEditName.getText().toString();
		String listPrice = mEditListPrice.getText().toString();
		String spePrice = mEditSpePrice.getText().toString();
		checkData(content, listPrice, spePrice);

		setUploadTimeData();
		String style = mStyle.getText().toString();
		String phone = mEditPhone.getText().toString();
		String address = mEditBus.getText().toString();
		String traffic = mEditTraffic.getText().toString();
		String perCapita = mEditPerCapita.getText().toString();
		String intro = mEditIntro.getText().toString();
		String title = mEditTitle.getText().toString();
		String speIntro = mEditSpeIntro.getText().toString();
		String mapAddress = mMap.getText().toString();

		showProgressDialog("正在保存资料...", true);
		String url = RemoteConst.URL_BASIC + RemoteConst.URL_BUSINESS_EDIT_INFO;
		RSBusinessInfoEdit busRs = new RSBusinessInfoEdit(mUserBean.getUid(), content,
				mIndustryLeft, mIndustryRight, getStyleText(style), phone, address, mLon, mLat,
				traffic, perCapita, intro, normalAmS, normalAmE, normalPmS, normalPmE, haltAmS,
				haltAmE, haltPmS, haltPmE, title, listPrice, spePrice, speIntro, mapAddress);

		RequestParams params = new RequestParams();
		Map<String, String> param = busRs.getUsingParams();
		for (String name : param.keySet()) {
			String value = param.get(name);
			params.put(name, value);
		}
		addFileUrlData(params);
		addFileData(params);
		Log.d("zhaoming", "\n params====== " + url + "?" + params.toString() + "\n");
		HttpUtils.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			protected void handleSuccessMessage(int statusCode, String responseBody) {
				Log.d("zhaoming", "onSuccess: \n" + responseBody);
				hideProgressDialog();
				if (!TextUtils.isEmpty(responseBody)) {
					try {
						JSONObject object = new JSONObject(responseBody);
						int result = object.optInt("result", -1);
						if (result == 1) {
							showToast("保存资料成功");
							setBusUserBeanData();
							setResult(601);
							PublicUtils.hideSoftInputMethod(BusinesslInformationActivity.this);
							finish();
						} else {
							showToast("保存资料失败");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						showToast("保存资料失败");
					}
				}
			}

			@Override
			protected void handleFailureMessage(Throwable e, String responseBody) {
				hideProgressDialog();
				showToast("保存资料失败");
			}
		});
	}

	private String getStyleText(String style) {
		String temp = "1";
		for (int i = 0; i < mStyleArray.length; i++) {
			if (style.equals(mStyleArray[i])) {
				temp = i + 1 + "";
			}
		}
		return temp;
	}

	private void addFileData(RequestParams params) {
		String prefix = "";
		for (int i = 0; i < mImagePaths.length; i++) {
			if (!TextUtils.isEmpty(mImagePaths[i])) {
				if (i < 6) {
					prefix = "img_";
					params.put(prefix + (i + 1), getInputStream(mImagePaths[i]), mImagePaths[i],
							"image/jpg");
				} else {
					prefix = "spe_";
					params.put(prefix + (i - 5), getInputStream(mImagePaths[i]), mImagePaths[i],
							"image/jpg");
				}
			}
		}
	}

	private void addFileUrlData(RequestParams params) {
		String urlPrefix = "";
		for (int i = 0; i < mImageUrls.length; i++) {
			if (!TextUtils.isEmpty(mImageUrls[i])) {
				if (i < 6) {
					urlPrefix = "img_url_";
					params.put(urlPrefix + (i + 1), mImageUrls[i]);
				} else {
					urlPrefix = "spe_url_";
					params.put(urlPrefix + (i - 5), mImageUrls[i]);
				}
			}
		}
	}

	private InputStream getInputStream(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fis;
	}

	private void setUploadTimeData() {
		String text = mTopMor.getText().toString();
		if (!TextUtils.isEmpty(text) && text.length() > 10) {
			normalAmS = Integer.parseInt(text.substring(0, 2));
			normalAmE = Integer.parseInt(text.substring(6, 8));
		}
		String text1 = mTopAfter.getText().toString();
		if (!TextUtils.isEmpty(text1) && text1.length() > 10) {
			normalPmS = Integer.parseInt(text1.substring(0, 2));
			normalPmE = Integer.parseInt(text1.substring(6, 8));
		}
		String text2 = mBotMor.getText().toString();
		if (!TextUtils.isEmpty(text2) && text2.length() > 10) {
			haltAmS = Integer.parseInt(text2.substring(0, 2));
			haltAmE = Integer.parseInt(text2.substring(6, 8));
		}
		String text3 = mBotAfter.getText().toString();
		if (!TextUtils.isEmpty(text3) && text3.length() > 10) {
			haltPmS = Integer.parseInt(text3.substring(0, 2));
			haltPmE = Integer.parseInt(text3.substring(6, 8));
		}
	}

	private void editPropertyInfo() {
		String content = mEditName.getText().toString();
		if (TextUtils.isEmpty(content.trim())) {
			showToast("昵称不能为空");
			return;
		}
		setUploadTimeData();
		String phone = mEditPhone.getText().toString();
		String address = mEditBus.getText().toString();
		String intro = mEditIntro.getText().toString();
		String mapAddress = mMap.getText().toString();
		showProgressDialog("正在保存资料...", true);
		String url = RemoteConst.URL_BASIC + RemoteConst.URL_PROPERTY_EDIT_INFO;
		RSPropertyInfoEdit proRs = new RSPropertyInfoEdit(mUserBean.getUid(), content, phone,
				address, mLon, mLat, intro, normalAmS, normalAmE, normalPmS, normalPmE, haltAmS,
				haltAmE, haltPmS, haltPmE, mapAddress);

		RequestParams params = new RequestParams();
		Map<String, String> param = proRs.getUsingParams();
		for (String name : param.keySet()) {
			String value = param.get(name);
			params.put(name, value);
		}
		addFileUrlData(params);
		addFileData(params);
		Log.d("zhaoming", "\n params====== " + url + "?" + params.toString() + "\n");
		HttpUtils.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			protected void handleSuccessMessage(int statusCode, String responseBody) {
				Log.d("zhaoming", "onSuccess: \n" + responseBody);
				hideProgressDialog();
				if (!TextUtils.isEmpty(responseBody)) {
					try {
						JSONObject object = new JSONObject(responseBody);
						int result = object.optInt("result", -1);
						if (result == 1) {
							showToast("保存资料成功");
							setProUserBeanData();
							setResult(601);
							PublicUtils.hideSoftInputMethod(BusinesslInformationActivity.this);
							finish();
						} else {
							showToast("保存资料失败");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						showToast("保存资料失败");
					}
				}
			}

			@Override
			protected void handleFailureMessage(Throwable e, String responseBody) {
				hideProgressDialog();
				showToast("保存资料失败");
			}
		});

	}

	private void setBusUserBeanData() {
		mUserBean.setName(mEditName.getText().toString());
		mUserBean.setPhone(mEditPhone.getText().toString());
		mUserBean.setAddress(mEditBus.getText().toString());
		mUserBean.setTraffic_routes(mEditTraffic.getText().toString());
		mUserBean.setPer_capita(mEditPerCapita.getText().toString());
		mUserBean.setIntroduction(mEditIntro.getText().toString());
		mUserBean.setBiz_method(getStyleText(mStyle.getText().toString()));
		if (mIndustryLeftId > 0 && mIndustryRightId > 0) {
			mUserBean.setCategory_id(mIndustryLeftId);
			mUserBean.setSubcategory_id(mIndustryRightId);
		}
		mUserBean.setCategory(mIndustryLeft);
		mUserBean.setSubcategory(mIndustryRight);
		mUserBean.setLongitude(String.valueOf(mLon));
		mUserBean.setLatitude(String.valueOf(mLat));
		mUserBean.setNormal_am_start(String.valueOf(normalAmS));
		mUserBean.setNormal_am_end(String.valueOf(normalAmE));
		mUserBean.setNormal_pm_start(String.valueOf(normalPmS));
		mUserBean.setNormal_pm_end(String.valueOf(normalPmE));
		mUserBean.setHalt_am_start(String.valueOf(haltAmS));
		mUserBean.setHalt_am_end(String.valueOf(haltAmE));
		mUserBean.setHalt_pm_start(String.valueOf(haltPmS));
		mUserBean.setHalt_pm_end(String.valueOf(haltPmE));
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH);
	}

	private void setProUserBeanData() {
		mUserBean.setName(mEditName.getText().toString());
		mUserBean.setPhone(mEditPhone.getText().toString());
		mUserBean.setAddress(mEditBus.getText().toString());
		mUserBean.setIntroduction(mEditIntro.getText().toString());
		mUserBean.setNormal_am_start(String.valueOf(normalAmS));
		mUserBean.setNormal_am_end(String.valueOf(normalAmE));
		mUserBean.setNormal_pm_start(String.valueOf(normalPmS));
		mUserBean.setNormal_pm_end(String.valueOf(normalPmE));
		mUserBean.setHalt_am_start(String.valueOf(haltAmS));
		mUserBean.setHalt_am_end(String.valueOf(haltAmE));
		mUserBean.setHalt_pm_start(String.valueOf(haltPmS));
		mUserBean.setHalt_pm_end(String.valueOf(haltPmE));
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH);
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicUtils.hideSoftInputMethod(BusinesslInformationActivity.this);
				finish();
			}
		});

		mTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isProperty) {
					editPropertyInfo();
				} else {
					editBusinessInfo();
				}
			}
		});

		mBtnIdentity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BusinesslInformationActivity.this, BusinessApplyAuthAct.class);
				intent.putExtra(BusinessApplyAuthAct.INDUSTRY_TYPE_KEY, mIndustry.getText()
						.toString());
				intent.putExtra(BusinessApplyAuthAct.MANAGER_STYLE_KEY, mStyle.getText().toString());
				BusinesslInformationActivity.this.startActivityForResult(intent, 600);
			}
		});

		mIndustryLayout.setOnClickListener(clickListener);
		mStyleLayout.setOnClickListener(clickListener);
		mMapAddressLayout.setOnClickListener(clickListener);
		mTopMorLayout.setOnClickListener(clickListener);
		mTopAfterLayout.setOnClickListener(clickListener);
		mBotMorLayout.setOnClickListener(clickListener);
		mBotAfterLayout.setOnClickListener(clickListener);

		mEditName.setOnFocusChangeListener(focusChangeListener);
		mEditPhone.setOnFocusChangeListener(focusChangeListener);
		mBotMorLayout.setOnFocusChangeListener(focusChangeListener);
		mEditBus.setOnFocusChangeListener(focusChangeListener);
		mEditTraffic.setOnFocusChangeListener(focusChangeListener);
		mEditPerCapita.setOnFocusChangeListener(focusChangeListener);
		mEditTitle.setOnFocusChangeListener(focusChangeListener);
		mEditListPrice.setOnFocusChangeListener(focusChangeListener);
		mEditSpePrice.setOnFocusChangeListener(focusChangeListener);

		mBusTopOne.setOnClickListener(imageClickListener);
		mBusTopTwo.setOnClickListener(imageClickListener);
		mBusTopThree.setOnClickListener(imageClickListener);
		mBusBotOne.setOnClickListener(imageClickListener);
		mBusBotTwo.setOnClickListener(imageClickListener);
		mBusBotThree.setOnClickListener(imageClickListener);
		mSpeTop.setOnClickListener(imageClickListener);
		mSpeBotLeft.setOnClickListener(imageClickListener);
		mSpeBotMid.setOnClickListener(imageClickListener);
		mSpeBotRight.setOnClickListener(imageClickListener);
	}

	OnClickListener imageClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			ArrayList<String> pathList = new ArrayList<String>();
			switch (view.getId()) {
			case R.id.top_one_image:
				mCurrentIndex = 0;
				mCurrentImage = mBusTopOne;
				break;
			case R.id.top_two_image:
				mCurrentIndex = 1;
				mCurrentImage = mBusTopTwo;
				break;
			case R.id.top_three_image:
				mCurrentIndex = 2;
				mCurrentImage = mBusTopThree;
				break;
			case R.id.bottom_one_image:
				mCurrentIndex = 3;
				mCurrentImage = mBusBotOne;
				break;
			case R.id.bottom_two_image:
				mCurrentIndex = 4;
				mCurrentImage = mBusBotTwo;
				break;
			case R.id.bottom_three_image:
				mCurrentIndex = 5;
				mCurrentImage = mBusBotThree;
				break;
			case R.id.specials_image:
				mCurrentIndex = 6;
				mCurrentImage = mSpeTop;
				break;
			case R.id.specials_left_image:
				mCurrentIndex = 7;
				mCurrentImage = mSpeBotLeft;
				break;
			case R.id.specials_mid_image:
				mCurrentIndex = 8;
				mCurrentImage = mSpeBotMid;
				break;
			case R.id.specials_right_image:
				mCurrentIndex = 9;
				mCurrentImage = mSpeBotRight;
				break;
			default:
				break;
			}

			if (mImagePaths[mCurrentIndex] != null) {
				pathList.add(mImagePaths[mCurrentIndex]);
				Intent intent = new Intent();
				intent.setClass(BusinesslInformationActivity.this, ShowBigPhotoActivity.class);
				intent.putStringArrayListExtra(AppConst.INTENT_IMAGE_PATH_ARRAY, pathList);
				intent.putExtra("is_url_image", false);
				BusinesslInformationActivity.this.startActivityForResult(intent, 210);
				return;
			}
			if (mImageUrls[mCurrentIndex] != null) {
				pathList.add(mImageUrls[mCurrentIndex]);
				Intent intent = new Intent();
				intent.setClass(BusinesslInformationActivity.this, ShowBigPhotoActivity.class);
				intent.putStringArrayListExtra(AppConst.INTENT_IMAGE_PATH_ARRAY, pathList);
				intent.putExtra("is_url_image", true);
				BusinesslInformationActivity.this.startActivityForResult(intent, 210);
				return;
			}
			if (mCurrentIndex < 6) {
				gotoGallery(4, 3, 400, 300);
			} else if (mCurrentIndex == 6) {
				gotoGallery(2, 1, 600, 300);
			} else {
				gotoGallery(4, 3, 400, 300);
			}

		}
	};

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.industry_type_layout:
				showMerchantWheel();
				break;
			case R.id.style_type_layout:
				WheelFactory.getSingleWheelWithStringArray(BusinesslInformationActivity.this,
						mStyleArray, mStyle, 0).show();
				break;
			case R.id.map_address_layout:
				Intent intent = new Intent(BusinesslInformationActivity.this,
						BusLocationMapActivity.class);
				if (mLat != -1 && mLon != -1) {
					intent.putExtra("lon", mLon);
					intent.putExtra("lat", mLat);
					intent.putExtra("addressName", addressName);
				}
				startActivityForResult(intent, 500);
				break;
			case R.id.top_morning_layout:
				WheelFactory.getDoubleWheelWithStringArray(BusinesslInformationActivity.this,
						mDateLeftArray, mDateRightArray, mTopMor).show();
				break;
			case R.id.top_afternoon_layout:
				WheelFactory.getDoubleWheelWithStringArray(BusinesslInformationActivity.this,
						mDateLeftArray, mDateRightArray, mTopAfter).show();
				break;
			case R.id.bottom_morning_layout:
				WheelFactory.getDoubleWheelWithStringArray(BusinesslInformationActivity.this,
						mDateLeftArray, mDateRightArray, mBotMor).show();
				break;
			case R.id.bottom_afternoon_layout:
				WheelFactory.getDoubleWheelWithStringArray(BusinesslInformationActivity.this,
						mDateLeftArray, mDateRightArray, mBotAfter).show();
				break;
			default:
				break;
			}
		}
	};

	private String mIndustryLeft = null;
	private String mIndustryRight = null;
	private int mIndustryLeftId = 0;
	private int mIndustryRightId = 0;

	private void showMerchantWheel() {
		List<MerchantCategoryBean> list = null;
		try {
			list = MerchantCategoryBean.queryForAll(getHelper().getMerchantCategoryBeanDao(),
					getHelper());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WheelDialog wheel = WheelFactory.getCommonDoubleWheel(this, list, mIndustry);
		wheel.setWheelActor(new WheelActor() {

			@Override
			public void doActionWhenConfirm(Object... o) {
				MerchantCategoryBean leftBean = (MerchantCategoryBean) o[0];
				MerchantSubcategoryBean rightBean = (MerchantSubcategoryBean) o[1];
				mIndustryLeftId = leftBean.getId();
				mIndustryLeft = leftBean.getName();
				mIndustryRightId = rightBean.getId();
				mIndustryRight = rightBean.getName();
				// TODO: 传送id
			}
		});
		wheel.show();
	}

	private void setImageWidthAndHeight(DisplayMetrics dm, ImageView imageView, int space) {
		int screenWidth = (dm.widthPixels - space) / 3;
		LayoutParams param = imageView.getLayoutParams();
		param.width = screenWidth;
		param.height = screenWidth - 10;
		imageView.setLayoutParams(param);
		imageView.setPadding(2, 2, 2, 2);
		imageView.setScaleType(ScaleType.FIT_XY);
	}

	private ArrayList<Integer> delete = new ArrayList<Integer>();

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 210 && resultCode == 200) {
			delete = data.getIntegerArrayListExtra(AppConst.INTENT_IMAGE_PATH_INDEX_ARRAY);
			if (delete != null && delete.size() != 0) {
				if (mCurrentIndex == 6) {
					mCurrentImage.setImageResource(R.drawable.big_add_image_btn);
				} else {
					mCurrentImage.setImageResource(R.drawable.bus_infor_add_image);
				}

				mImagePaths[mCurrentIndex] = null;
				mImageUrls[mCurrentIndex] = null;
			}
		}

		if (requestCode == 600 && resultCode == 601) {
			mIdentity.setText("待认证");
			mBtnIdentity.setVisibility(View.GONE);
		}

		if (requestCode == 500 && resultCode == 501) {
			double lat = data.getDoubleExtra("lat", -1);
			double lon = data.getDoubleExtra("lon", -1);
			if (lat != -1 && lon != -1) {
				mLat = lat;
				mLon = lon;
			}
			String mapAddress = data.getStringExtra("addressName");
			if (!TextUtils.isEmpty(mapAddress)) {
				mMap.setText(mapAddress);
			}
		}

		if (mCamera.onActivityResult(mCurrentImage, requestCode, resultCode, data)) {
			mImagePaths[mCurrentIndex] = mCamera.getPath();
		}
	}

	OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View view, boolean hasFocus) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.bus_name_edit:
				if (hasFocus) {
					mBtnName.setVisibility(View.GONE);
				} else {
					mBtnName.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.servic_phone_edit:
				if (hasFocus) {
					mBtnPhone.setVisibility(View.GONE);
				} else {
					mBtnPhone.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.bus_address_edit:
				if (hasFocus) {
					mBtnAddress.setVisibility(View.GONE);
				} else {
					mBtnAddress.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.traffic_address_edit:
				if (hasFocus) {
					mBtnTraffic.setVisibility(View.GONE);
				} else {
					mBtnTraffic.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.per_capita_edit:
				if (hasFocus) {
					mBtnPerCapita.setVisibility(View.GONE);
				} else {
					mBtnPerCapita.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.title_edit:
				if (hasFocus) {
					mBtnTitle.setVisibility(View.GONE);
				} else {
					mBtnTitle.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.list_price_edit:
				if (hasFocus) {
					mBtnListPrice.setVisibility(View.GONE);
				} else {
					mBtnListPrice.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.pricing_edit:
				if (hasFocus) {
					mBtnSpePrice.setVisibility(View.GONE);
				} else {
					mBtnSpePrice.setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		hideProgressDialog();
		PublicUtils.hideSoftInputMethod(this);
		// RecycleImageBitmap();
		super.onDestroy();
	}

	private void RecycleImageBitmap() {
		ImageView[] imageViews = { mBusTopOne, mBusTopTwo, mBusTopThree, mBusBotOne, mBusBotTwo,
				mBusBotThree, mSpeTop, mSpeBotLeft, mSpeBotMid, mSpeBotRight };
		for (int i = 0; i < imageViews.length; i++) {
			if (imageViews[i] != null) {
				BitmapDrawable bdOne = (BitmapDrawable) imageViews[i].getDrawable();
				// 别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
				imageViews[i].setBackgroundResource(0);
				if (bdOne != null) {
					bdOne.setCallback(null);
					bdOne.getBitmap().recycle();
				}
			}
		}

	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
}

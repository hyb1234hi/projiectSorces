package com.sinaleju.lifecircle.app.activity;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.cooperationaccount.sina.AccessTokenKeeper;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog.WheelActor;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelFactory;
import com.sinaleju.lifecircle.app.database.entity.MerchantCategoryBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantSubcategoryBean;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSCompleteInfo;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class AddInformationActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {
	private static final String TAG = "AddInformationActivity";

	private SharedPreferences mSp1;
	private SharedPreferences mSp2;
	private View mSwitchButton;
	private View mRl_phoneNumber;
	private View mIv_phoneNumberLine;
	private View mRl_checkNumber;
	private View mIv_checkNumber;
	private View mRl_password;
	private View mIv_password;
	private View mRl_repeatPassword;
	private View mIv_repeatPassword;
	private View mRl_nickName;

	private TextView mEt_liveArea;
	private View mLl_selectLiveArea;
	// private Button mIv_selectLiveArea;

	private RadioGroup mRg_whoAreYou;
	private View mLl_personType;
	private TextView mTv_personType;
	private View mLl_merchantType;
	private TextView mTv_merchantType;

	private View mRl_servicePhoneNumber;
	private View mIv_servicePhonenumberLine;
	private EditText mEt_servicePhoneNumber;

	private EditText mEt_name;
	//private EditText mEt_password;
	//private EditText mEt_repeatPassword;
	private Button mBt_submit;
	
	private View mLl_person;
	private View mLl_merchant;
	private TextView mTv_person;
	private TextView mTv_merchant;
	

	private String[] mPersonTypeArray;
	private String mLiveAreaId;
	private String mCategory = "1";// 最终提交的当前用户类型，默认为“业主”
	private String mMerchantCategory = "家政服务-家政";// 记录当前商家类型，默认为“生活服务-家政”
	private String mServicePhoneNumber;
	private String mNickName;
	private String mType = "0";
	private int mCount;
	private int mCount2 = 2;
	private boolean isFirstAddLiveArea = true;
	// private String mToken;
	// private String mExpiresTime;
	private String mHeadImageUrl;
	private String fid;
	private String origin;
	private int userId;
	private String params;
	
	private int black;
	private int white;
	private int bgGrey;
	private int textGrey;
	private int textDarkGrey;
	private int blue;
	private  int curUserType=0;

	private SharedPreferences sp;
	@Override
	protected int getLayoutId() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("完善信息");
		return R.layout.ac_regist;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		mSwitchButton = findViewById(R.id.regist_ll_switchbutton);
		mSwitchButton.setVisibility(View.GONE);
		mRl_phoneNumber = findViewById(R.id.regist_rl_phonenumber);
		mRl_phoneNumber.setVisibility(View.GONE);
		mIv_phoneNumberLine = findViewById(R.id.regist_iv_phonenumberline);
		mIv_phoneNumberLine.setVisibility(View.GONE);
		mRl_checkNumber = findViewById(R.id.regist_rl_checknumber);
		mRl_checkNumber.setVisibility(View.GONE);
		mIv_checkNumber = findViewById(R.id.regist_iv_checknumber);
		mIv_checkNumber.setVisibility(View.GONE);
		mRl_password = findViewById(R.id.regist_rl_password);
		mRl_password.setVisibility(View.GONE);
		mIv_password = findViewById(R.id.regist_iv_password);
		mIv_password.setVisibility(View.GONE);
		mRl_repeatPassword = findViewById(R.id.regist_rl_repeatpassword);
		mRl_repeatPassword.setVisibility(View.GONE);
		mIv_repeatPassword = findViewById(R.id.regist_iv_repeatpassword);
		mIv_repeatPassword.setVisibility(View.GONE);
		mRl_nickName = findViewById(R.id.regist_rl_nickname);

		// mIv_selectLiveArea = (Button)
		// findViewById(R.id.regist_selectlivearea);

		mEt_liveArea = (TextView) findViewById(R.id.regist_livearea);
		mLl_selectLiveArea = findViewById(R.id.regist_ll_selectlivearea);

		mRg_whoAreYou = (RadioGroup) findViewById(R.id.regist_whoareyou);
		mLl_personType = findViewById(R.id.regist_ll_persontype);
		mTv_personType = (TextView) findViewById(R.id.regist_whoareyou_persontype);
		mLl_merchantType = findViewById(R.id.regist_ll_merchanttype);
		mTv_merchantType = (TextView) findViewById(R.id.regist_whoareyou_merchanttype);

		mRl_servicePhoneNumber = findViewById(R.id.regist_rl_servicephone);
		mIv_servicePhonenumberLine = findViewById(R.id.regist_iv_servicephoneline);
		mEt_servicePhoneNumber = (EditText) findViewById(R.id.regist_servicephone);
		mEt_name = (EditText) findViewById(R.id.regist_username);
		//mEt_password = (EditText) findViewById(R.id.regist_password);
		//mEt_repeatPassword = (EditText) findViewById(R.id.regist_confirmpassword);
		mBt_submit = (Button) findViewById(R.id.regist_submit);
		mBt_submit.setClickable(false);
		
		mLl_person = findViewById(R.id.regist_ll_person);
		mLl_merchant = findViewById(R.id.regist_ll_merchant);
		mTv_person = (TextView) findViewById(R.id.regist_tv_person);
		mTv_merchant = (TextView) findViewById(R.id.regist_tv_merchant);
		
		blue = getResources().getColor(R.color.ac_regist_text_blue);
		bgGrey = R.drawable.ac_regist_persontype_bg_grey;
		textGrey=getResources().getColor(R.color.ac_regist_text_grey);
		textDarkGrey=getResources().getColor(R.color.ac_regist_text_grey_dark);
		black = getResources().getColor(R.color.ac_regist_text_black);
		white =R.drawable.ac_regist_persontype_bg_white;
		
		
		
	}

	@Override
	protected void initData() {
		sp = getSharedPreferences("userinfo", MODE_PRIVATE);	
		checkIsFull();
		//mLl_selectLiveArea.setBackgroundResource(R.drawable.selector_login_layout_press_top);
		//mRl_nickName.setBackgroundResource(R.drawable.selector_login_layout_press_bottom);

		mPersonTypeArray = new String[] { "业主", "租户", "周边个人" };
		mSp1 = getSharedPreferences("logininfo", MODE_PRIVATE);
		mSp2 = getSharedPreferences("userinfo", MODE_PRIVATE);

		mNickName = mSp2.getString("nickname", "");
		// mToken = mSp2.getString("token", "");
		// mExpiresTime = mSp2.getString("expirestime", "");
		mHeadImageUrl = mSp2.getString("headimage", "");
		params = mSp2.getString("param", "");
		mEt_name.setText(mNickName);
	}

	@Override
	protected void initCallbacks() {
		// onClick
		mTitleBar.setLeftButtonListener(this);
		mLl_selectLiveArea.setOnClickListener(this);
		mBt_submit.setOnClickListener(this);
		mLl_merchantType.setOnClickListener(this);
		mLl_personType.setOnClickListener(this);

		// OnCheckedChangeListener
		mRg_whoAreYou.setOnCheckedChangeListener(this);

		// OnFocusChangeListener
		// mEt_liveArea.addTextChangedListener(textWatcher);
		mEt_name.addTextChangedListener(textWatcher);
		mEt_servicePhoneNumber.addTextChangedListener(textWatcher);
		//mEt_password.addTextChangedListener(textWatcher);
		//mEt_repeatPassword.addTextChangedListener(textWatcher);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 返回
		case R.id.imavBack:
			AccessTokenKeeper.clear(this);
			this.finish();

			break;

		case R.id.regist_ll_selectlivearea:
			// 选择小区，进入选择城市页面。
			Intent selectCityIntent = new Intent(mContext,
					SelectCityActivity.class);
			startActivityForResult(selectCityIntent, 0);
			break;

		// 选择个人类型
		case R.id.regist_ll_persontype:
			WheelFactory.getSingleWheelWithStringArray(mContext,
					mPersonTypeArray, mTv_personType, 0).show();
			mRg_whoAreYou.check(R.id.regist_whoareyou_person);
			hideServicePhoneView();
			break;

		// 选择商家类型
		case R.id.regist_ll_merchanttype:
			mRg_whoAreYou.check(R.id.regist_whoareyou_merchant);
			/* start a wheel */
			showMerchantWheel();
			showSerivcePhoneView();

			break;
		// 提交
		case R.id.regist_submit:
			// 当提交信息成功时设置
			if (mType.equals("0")) {
				getPersonTypeId();
			}

			mNickName = mEt_name.getText().toString().trim();
			mServicePhoneNumber = mEt_servicePhoneNumber.getText().toString()
					.trim();
			fid = mSp2.getString("uid", "");
			origin = mSp2.getString("origin", "");

			LogUtils.i(TAG, "userId:  " + fid + "  orgin  " + origin);

			RSCompleteInfo rsCompleteInfo = new RSCompleteInfo(fid, mCategory,
					mLiveAreaId, mType, mNickName, origin, mServicePhoneNumber,
					mHeadImageUrl, params);
			rsCompleteInfo.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					hideProgressDialog();
					String obj = result.toString();

					try {
						JSONObject jsonObjet = new JSONObject(obj);
						JSONObject data = jsonObjet.getJSONObject("data");
						JSONObject userInfo = data.getJSONObject("userinfo");
						userId = userInfo.getInt("id");
						// 创建新用户
						createUser(userId);
						AppContext.curUser().setSina_accesstoken(
								sp.getString("token", ""));
						// 更新用户信息
						AppContext.curUser().update(getHelper());
						gotoHomeActivity();
					} catch (JSONException e) {
						LogUtils.e(TAG, "parse json error ", e);
					} catch (SQLException e) {
						LogUtils.e(TAG, "sqlite create user error ", e);
					}
					LogUtils.e(TAG, "result....userid " + userId);

					// 提交成功，进入主页。
//					Intent homeIntent = new Intent(mContext, HomeActivity.class);
//					homeIntent.putExtra("userid", userId);
//					startActivity(homeIntent);
//					finish();
				}
			});
		
			rsCompleteInfo.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					if (ex instanceof ADRemoteException) {
						showToast(((ADRemoteException) ex).msg());
					} else if (ex instanceof ConnectTimeoutException) {
						showToast("请求超时，请重试");
					}
					LogUtils.e(TAG, ex.toString());
				}
			});
			if (PublicUtils.isNetworkAvailable(mContext)) {
				rsCompleteInfo.asyncExecute(mContext);
				showProgressDialog("信息提交中...", false);
			} else {
				showToast("网络连接失败，请检查网络后重试");
			}

			break;

		default:
			break;
		}

	}
	
	/**
	 * 获取个人类型Id
	 */
	private void getPersonTypeId() {
		String personType = mTv_personType.getText().toString().trim();
		for (int i = 0; i < mPersonTypeArray.length; i++) {
			if (personType.equals(mPersonTypeArray[i])) {
				mCategory = String.valueOf(i + 1);
				break;
			}
		}
	}

	/**
	 * 显示商家分类列表
	 */
	private void showMerchantWheel() {
		List<MerchantCategoryBean> list = null;
		try {
			list = MerchantCategoryBean.queryForAll(getHelper()
					.getMerchantCategoryBeanDao(), getHelper());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WheelDialog wheel = WheelFactory.getCommonDoubleWheel(this, list,
				mTv_merchantType);
		wheel.setWheelActor(new WheelActor() {

			@Override
			public void doActionWhenConfirm(Object... o) {
				MerchantCategoryBean leftBean = (MerchantCategoryBean) o[0];
				MerchantSubcategoryBean rightBean = (MerchantSubcategoryBean) o[1];
//				int cateId = leftBean.getId();
//				int subcateId = rightBean.getId();
//				// TODO: 传送id
//				mCategory = mMerchantCategory = cateId + "-" + subcateId;
				String cateName=leftBean.getName();
				String subcateName=rightBean.getName();
				// TODO: 传送id
				mCategory = mMerchantCategory = cateName + "-" + subcateName;
			}
		});
		wheel.show();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.regist_whoareyou_person:
			// Toast.makeText(mContext, "您是个人", Toast.LENGTH_SHORT).show();
			hideServicePhoneView();
			mTv_personType.setTextColor(getResources().getColor(
					R.color.ac_regist_text_black));
			mTv_merchantType.setTextColor(getResources().getColor(
					R.color.ac_regist_text_grey));
			mType = "0";
			changeBgColor();
			mCount2 = 2;
			checkIsFull();
			break;
		case R.id.regist_whoareyou_merchant:
			mCategory=mMerchantCategory;
			// Toast.makeText(mContext, "您是商家", Toast.LENGTH_SHORT).show();
			// 如果是商家，则显示服务电话项。
			showSerivcePhoneView();
			mTv_merchantType.setTextColor(getResources().getColor(
					R.color.ac_regist_text_black));
			mTv_personType.setTextColor(getResources().getColor(
					R.color.ac_regist_text_grey));
			mType = "1";
			changeBgColor();
			mCount2 = 3;
			checkIsFull();
			break;
		/*
		 * case R.id.regist_whoareyou_tenement: // Toast.makeText(mContext,
		 * "您是物业", Toast.LENGTH_SHORT).show(); showSerivcePhoneView();
		 * mTv_merchantType.setTextColor(getResources().getColor(
		 * R.color.ac_regist_text_grey));
		 * mTv_personType.setTextColor(getResources().getColor(
		 * R.color.ac_regist_text_grey)); break;
		 */

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			LogUtils.e(TAG, TAG + "----onActivityResult");
			if (data != null) {
				mEt_liveArea.setText(data.getStringExtra("community_name"));
				mEt_liveArea.setTextColor(black);
				mLiveAreaId = String.valueOf(data.getIntExtra("community_id",
						-1));
				if (isFirstAddLiveArea) {
					mCount++;
					checkIsFull();
					isFirstAddLiveArea = false;
				}
			}
		}

	}

	/*
	 * @Override public void onFocusChange(View view, boolean hasFocus) { if
	 * (!hasFocus) { if (view.getId() == R.id.regist_phonenumber) {
	 * Toast.makeText(mContext, "电话号码", 0).show(); } if (view.getId() ==
	 * R.id.regist_checknumber) { Toast.makeText(mContext, "验证码", 0).show(); } }
	 * 
	 * }
	 */

	/**
	 * 隐藏服务电话项
	 */
	private void hideServicePhoneView() {
		if (mRl_servicePhoneNumber.getVisibility() == View.VISIBLE) {
			String text=mEt_servicePhoneNumber.getText().toString().trim();
			if(!"".equals(text)){
				mEt_servicePhoneNumber.setText("");
			}
			mRl_servicePhoneNumber.setVisibility(View.GONE);
			mIv_servicePhonenumberLine.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示服务电话项
	 */
	private void showSerivcePhoneView() {
		if (mRl_servicePhoneNumber.getVisibility() == View.GONE) {
			mRl_servicePhoneNumber.setVisibility(View.VISIBLE);
			mIv_servicePhonenumberLine.setVisibility(View.VISIBLE);
		}
	}

	// 监测所有信息是否输入完整
	TextWatcher textWatcher = new TextWatcher() {
		// boolean isEmpty=false;
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			String text = s.toString().trim();
			if ("".equals(text)) {
				mCount--;
			}
			LogUtils.e(TAG, "chengedmcount    " + mCount);

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			String text = s.toString().trim();
			if (text.equals("")) {
				mCount++;
			}
			LogUtils.e(TAG, "beforemcount    " + mCount);
		}

		@Override
		public void afterTextChanged(Editable s) {
			checkIsFull();
		}
	};

	private void checkIsFull() {
		LogUtils.e(TAG,"count:  "+mCount+"...count2..."+mCount2);
		if (mCount == mCount2) {
			mBt_submit.setBackgroundResource(R.drawable.regist_submit_press);
			mBt_submit.setClickable(true);
		} else {
			mBt_submit.setBackgroundResource(R.drawable.login_normal);
			mBt_submit.setClickable(false);
		}
	}
	private void changeBgColor() {
		if (mType.equals("0")&& curUserType!=0) {
			//个人、商家 文字颜色
			mTv_person.setTextColor(black);
			mTv_merchant.setTextColor(textDarkGrey);
			//个人、商家类型 文字颜色
			mTv_personType.setTextColor(blue);
			mTv_merchantType.setTextColor(textGrey);
			//个人、商家类型 背景颜色
			mLl_person.setBackgroundResource(white);
			mLl_merchant.setBackgroundResource(bgGrey);
			curUserType=0;
			
		}else if(mType.equals("1")&& curUserType!=1){
			mTv_person.setTextColor(textDarkGrey);
			mTv_merchant.setTextColor(black);
			mTv_personType.setTextColor(textGrey);
			mTv_merchantType.setTextColor(blue);
			mLl_person.setBackgroundResource(bgGrey);
			mLl_merchant.setBackgroundResource(white);
			curUserType=1;
		}

	}
	
	@Override
	public void onBackPressed() {
		LogUtils.i(TAG, "返回");
		AccessTokenKeeper.clear(this);
		super.onBackPressed();
	}
	//进入主页
	private void gotoHomeActivity() {
		// 隐藏键盘
		ADSoftInputUtils.hide(mContext);
		// 登录成功跳转到主页
		Intent homeIntent = new Intent(mContext, HomeActivity.class);
		// homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		HomeActivity h = ((AppContext) getApplicationContext())
				.getHomeActivity();
		if (h != null) {
			h.finish();
		}
		startActivity(homeIntent);
		finish();
	}
	
}

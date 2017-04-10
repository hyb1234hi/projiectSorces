package com.sinaleju.lifecircle.app.activity;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.SwitchButton;
import com.sinaleju.lifecircle.app.customviews.SwitchButton.OnSwitchListener;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelDialog.WheelActor;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelFactory;
import com.sinaleju.lifecircle.app.database.entity.MerchantCategoryBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantSubcategoryBean;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetAuthCode;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSRegist;
import com.sinaleju.lifecircle.app.utils.ADPatternUtils;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class RegistActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener/* , OnFocusChangeListener */{
	private static final String TAG = "RegistActivity";

	private static final int REQUEST_CODE_SELECT_COMMUNITY = 10;

	private SwitchButton mSb_registType;
	private boolean mRegistType = true; // 默认电话注册。

	private EditText mEt_phoneNumber;
	private View mRl_phoneNumber;
	private View mIv_phoneNumberLine;
	private Button mBt_getCheckNumber;

	private TextView mTv_checkOrEmail;
	private EditText mEt_checkOrEmail;

	private TextView mTv_liveArea;
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
	private EditText mEt_password;
	private EditText mEt_repeatPassword;
	private Button mBt_submit;
	private String[] mPersonTypeArray;
	private TimeCount mTimeCount;

	private View mLl_person;
	private View mLl_merchant;
	private TextView mTv_person;
	private TextView mTv_merchant;

	private String mCategory = "1";// 最终提交的当前用户类型，默认为“业主”
	private String mMerchantCategory = "家政服务-家政";// 记录当前商家类型，默认为“生活服务-家政”
	private String mAccountName;
	private String mCheckNumber;
	private int mLiveAreaId;
	private String mServicePhone;
	private String mNickName;
	private String mPassword;
	private String mRepeatPassword;
	private String mUserType = "0";
	private int mCount1;
	private int mCount2 = 6;
	private boolean isFirstAddLiveArea = true;

	private int black;
	private int white;
	private int bgGrey;
	private int textGrey;
	private int textDarkGrey;
	private int blue;
	private  int curUserType=0;

	@Override
	protected int getLayoutId() {

		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null, R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("注册新用户");
		return R.layout.ac_regist;
	}

	@Override
	protected void initView() {

		LogUtils.i(TAG, "------------initViews");

		mSb_registType = (SwitchButton) findViewById(R.id.regist_registtype);

		mRl_phoneNumber = findViewById(R.id.regist_rl_phonenumber);
		mIv_phoneNumberLine = findViewById(R.id.regist_iv_phonenumberline);
		mEt_phoneNumber = (EditText) findViewById(R.id.regist_phonenumber);
		mEt_phoneNumber.requestFocus();
		mBt_getCheckNumber = (Button) findViewById(R.id.regist_getcheckednumber);
		
		// mIv_selectLiveArea = (Button)
		// findViewById(R.id.regist_selectlivearea);

		mTv_checkOrEmail = (TextView) findViewById(R.id.checkednumber);
		mEt_checkOrEmail = (EditText) findViewById(R.id.regist_checknumber);
		mTv_liveArea = (TextView) findViewById(R.id.regist_livearea);
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
		mEt_password = (EditText) findViewById(R.id.regist_password);
		mEt_repeatPassword = (EditText) findViewById(R.id.regist_confirmpassword);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE_SELECT_COMMUNITY
				&& resultCode == Activity.RESULT_OK) {
			LogUtils.e(TAG, TAG + "----onActivityResult");
			if (data != null) {
				mTv_liveArea.setText(data.getStringExtra("community_name"));
				mTv_liveArea.setTextColor(black);
				mLiveAreaId = data.getIntExtra("community_id", -1);
				if (isFirstAddLiveArea) {
					mCount1++;
					checkIsFull();
					isFirstAddLiveArea = false;
				}
			}

		}
	}

	@Override
	protected void initData() {
		checkIsFull();
		mPersonTypeArray = new String[] { "业主", "租户", "周边个人" };
		// mMerchantTypeArray = new String[][] { { "美食", "地产", "足球" },{ "足球",
		// "体育", "娱乐" } };
		mTimeCount = new TimeCount(30000, 1000);
	}

	@Override
	protected void initCallbacks() {
		// onClick
		mTitleBar.setLeftButtonListener(this);
		mBt_getCheckNumber.setOnClickListener(this);
		// mIv_selectLiveArea.setOnClickListener(this);
		mLl_selectLiveArea.setOnClickListener(this);
		mBt_submit.setOnClickListener(this);
		mLl_merchantType.setOnClickListener(this);
		mLl_personType.setOnClickListener(this);

		// OnCheckedChangeListener
		mRg_whoAreYou.setOnCheckedChangeListener(this);

		// OnFocusChangeListener

		// mEt_phoneNumber.setOnFocusChangeListener(this);
		// mEt_checkOrEmail.setOnFocusChangeListener(this);

		mSb_registType.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if (isSwitchOn) { // 手机注册
					// 清除原有数据
					clearText();
					// 显示手机号码项，并修改“邮箱”为“验证码”
					showPhoneNumberView();
					mRegistType = true;
					// 当前用户类型是个人。
					if (mUserType.equals("0")) {
						mCount2 = 6;
					} else {
						mCount2 = 7;
					}
					checkIsFull();
				} else { // 邮箱注册
					// 清除原有数据
					clearText();

					// 隐藏手机号码项，并修改“验证码”为“邮箱”
					hidePhoneNumberView();
					mRegistType = false;
					if (mUserType.equals("0")) {
						mCount2 = 5;
					} else {
						mCount2 = 6;
					}
					checkIsFull();
				}
			}
		});
		// 添加内容改变监听
		mEt_checkOrEmail.addTextChangedListener(textWatcher);
		mEt_name.addTextChangedListener(textWatcher);
		mEt_password.addTextChangedListener(textWatcher);
		mEt_phoneNumber.addTextChangedListener(textWatcher);
		mEt_repeatPassword.addTextChangedListener(textWatcher);
		mEt_servicePhoneNumber.addTextChangedListener(textWatcher);
		// mTv_liveArea.addTextChangedListener(textWatcher);

	}

	// 监测所有信息是否输入完整
	TextWatcher textWatcher = new TextWatcher() {
		// boolean isEmpty=false;
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			String text = s.toString().trim();
			/*
			 * // boolean isFrist = true; LogUtils.e(TAG,
			 * "changed...."+s+"     start..." + start + "...before..." + before
			 * + "...count..." + count); if (isEmpty && start == 0&&before==0) {
			 * mCount1++; LogUtils.e(TAG, " mCount1-------" + mCount1);
			 * LogUtils.e(TAG, "start1..." + start + "...before1..." + before +
			 * "...count1..." + count);
			 * 
			 * }
			 */
			if ("".equals(text)) {
				mCount1--;
				// isFrist = true;
				// LogUtils.e(TAG, "-------" + mCount1);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			String text = s.toString().trim();
			if (text.trim().equals("")) {
				mCount1++;
				// LogUtils.e(TAG, "mCount1...."+mCount1);
			}
			// LogUtils.e(TAG, "beforeText...." + s + "    startbefore..." +
			// start
			// + "...countbefore..." + count + "...afterbefore..." + after);

		}

		@Override
		public void afterTextChanged(Editable s) {

			// TODO Auto-generated method stub

			// LogUtils.e(TAG, "mCount2........" + mCount2);
			checkIsFull();
		}
	};

	private void checkIsFull() {
		if (mCount1 == mCount2) {
			mBt_submit.setBackgroundResource(R.drawable.regist_submit_press);
			mBt_submit.setClickable(true);
		} else {
			mBt_submit.setBackgroundResource(R.drawable.login_normal);
			mBt_submit.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.imavBack:
			this.finish();
			break;

		// 获取验证码
		case R.id.regist_getcheckednumber:
			mAccountName = mEt_phoneNumber.getText().toString().trim();
			if ("".equals(mAccountName)
					|| !ADPatternUtils.isMobileNO(mAccountName)) {
				showToast("请输入正确的手机号码");
			} else {
				// 向服务器请求验证码
				RSGetAuthCode getAuthCode = new RSGetAuthCode(mAccountName);
				getAuthCode.setOnSuccessHandler(new OnSuccessHandler() {

					@Override
					public void onSuccess(Object result) {
						// TODO Auto-generated method stub
						// 开始倒计时
						hideProgressDialog();
						mTimeCount.start();
						showToast("验证码已经发送到你的的机，请注意查收");
						LogUtils.e(TAG, result.toString());
					}
				});
				getAuthCode.setOnFaultHandler(new OnFaultHandler() {

					@Override
					public void onFault(Exception ex) {
						// TODO Auto-generated method stub
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
					getAuthCode.asyncExecute(mContext);
					showProgressDialog("请求发送中", false);
				} else {
					showToast("网络连接失败，请检查网络后重试");
				}
			}

			break;

		case R.id.regist_ll_selectlivearea:
			// 选择小区，进入选择城市页面。
			Intent selectCityIntent = new Intent(mContext,
					SelectCityActivity.class);
			startActivityForResult(selectCityIntent,
					REQUEST_CODE_SELECT_COMMUNITY);
			break;

		// 选择个人类型
		case R.id.regist_ll_persontype:
			// mPersonCategory=mCategory="1"; //默认的个人类型。
			WheelFactory.getSingleWheelWithStringArray(mContext,
					mPersonTypeArray, mTv_personType, 0).show();
			mRg_whoAreYou.check(R.id.regist_whoareyou_person);
			hideServicePhoneView();
			break;

		// 选择商家类型
		case R.id.regist_ll_merchanttype:
			// mMerchantCategory=mCategory="9-111"; //默认的商家类型
			/* start a wheel */
			showMerchantWheel();
			mRg_whoAreYou.check(R.id.regist_whoareyou_merchant);
			showSerivcePhoneView();
			break;
		// 提交
		case R.id.regist_submit:
			/*
			 * if (!mRegistType) { mCheckNumber = ""; } if
			 * (mUserType.equals("0")) { mServicePhone = ""; }
			 */

			// RSRegist rs = new RSRegist(met, mCheckNumber, "1",
			// "1", "hhhh", "123456", "");
			// mCategory = "2";

			mAccountName = mEt_phoneNumber.getText().toString().trim();
			mNickName = mEt_name.getText().toString().trim();
			mPassword = mEt_password.getText().toString().trim();
			mRepeatPassword = mEt_repeatPassword.getText().toString().trim();
			mServicePhone = mEt_servicePhoneNumber.getText().toString().trim();
			mCheckNumber = mEt_checkOrEmail.getText().toString().trim();
			// 获取当前个人类型的id.
			if (mUserType.equals("0")) {
				getPersonTypeId();
			}
			// 对输入的电话号码、邮箱格式，及密码进行判断。
			if (!mRegistType) { // 如果是邮箱注册
				if (!ADPatternUtils.isEmail(mCheckNumber)) {
					showToast("请输入正确的邮箱");
					break;
				}
				mAccountName = mCheckNumber;// 如果是邮箱注册，则验证码输入框变为用户邮箱输入框。
				mCheckNumber = "";
			}
			if (mPassword.length() < 6 || mPassword.length() > 12) {
				showToast("密码长度在6-12位之间");
				break;
			}
			if (!mPassword.equals(mRepeatPassword)) {
				showToast("两次输入密码不一致");
				break;
			}
			RSRegist rs = new RSRegist(mCategory, mAccountName, mCheckNumber,
					mLiveAreaId, mUserType, mNickName, mPassword, mServicePhone);
			rs.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					// 注册成功跳转到登录
					hideProgressDialog();
					// showToast("注册成功");
					Intent loginIntent = new Intent(mContext,
							LoginActivity.class);
					loginIntent.putExtra("loginname", mAccountName);
					loginIntent.putExtra("password", mPassword);
					startActivity(loginIntent);
					ADSoftInputUtils.hide(mContext);
					finish();
				}
			});
			rs.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					if (ex instanceof ADRemoteException)
						showToast(((ADRemoteException) ex).msg());
					else if (ex instanceof ConnectTimeoutException) {
						showToast("请求超时，请重试");
					}
					LogUtils.e(TAG, ex.toString());
				}
			});
			if (PublicUtils.isNetworkAvailable(mContext)) {
				showProgressDialog("请求发送中...", true);
				rs.asyncExecute(this);
			} else {
				showToast("网络连接失败，请检查网络后重试");
			}
			break;

		default:
			break;
		}

	}

	private void changeBgColor() {
		if (mUserType.equals("0")&& curUserType!=0) {
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
			
		}else if(mUserType.equals("1")&& curUserType!=1){
			mTv_person.setTextColor(textDarkGrey);
			mTv_merchant.setTextColor(black);
			mTv_personType.setTextColor(textGrey);
			mTv_merchantType.setTextColor(blue);
			mLl_person.setBackgroundResource(bgGrey);
			mLl_merchant.setBackgroundResource(white);
			curUserType=1;
		}

	}

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
			// 获取当前个人类型的id.
			// getPersonTypeId();
			LogUtils.i(TAG, "category:  " + mCategory);
			// Toast.makeText(mContext, "您是个人", Toast.LENGTH_SHORT).show();
			hideServicePhoneView();
			mUserType = "0";
			changeBgColor();
			// 手机注册且是个人
			if (mRegistType) {
				mCount2 = 6;
			} else {
				// 邮箱注册且是个人
				mCount2 = 5;
			}
			checkIsFull();
			break;
		case R.id.regist_whoareyou_merchant:
			mCategory = mMerchantCategory;
			LogUtils.i(TAG, "category:  " + mCategory);
			// Toast.makeText(mContext, "您是商家", Toast.LENGTH_SHORT).show();
			// 如果是商家，则显示服务电话项。
			showSerivcePhoneView();
//			mTv_merchantType.setTextColor(getResources().getColor(
//					R.color.ac_regist_text_black));
//			mTv_personType.setTextColor(getResources().getColor(
//					R.color.ac_regist_text_grey));
			mUserType = "1";
			changeBgColor();
			// 手机注册且是商家
			if (mRegistType) {
				mCount2 = 7;
			} else {
				// 邮箱注册且是商家
				mCount2 = 6;
			}
			checkIsFull();
			break;
		/*
		 * case R.id.regist_whoareyou_tenement: // Toast.makeText(mContext,
		 * "您是物业", Toast.LENGTH_SHORT).show(); showSerivcePhoneView();
		 * mTv_merchantType.setTextColor(getResources().getColor(
		 * R.color.ac_regist_text_grey));
		 * mTv_personType.setTextColor(getResources().getColor(
		 * R.color.ac_regist_text_grey)); mUserType = "2"; // 手机注册且是物业 if
		 * (mRegistType) { mCount2 = 7; } else { // 邮箱注册且是物业 mCount2 = 6; }
		 * checkIsFull(); break;
		 */

		default:
			break;
		}

	}

	/**
	 * 
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

	/*
	 * @Override public void onFocusChange(View view, boolean hasFocus) { if
	 * (!hasFocus) { if (view.getId() == R.id.regist_phonenumber) {
	 * Toast.makeText(mContext, "电话号码", 0).show(); } if (view.getId() ==
	 * R.id.regist_checknumber) { Toast.makeText(mContext, "验证码", 0).show(); } }
	 * 
	 * }
	 */

	/**
	 * 隐藏手机号码项
	 */
	private void hidePhoneNumberView() {
		if (mRl_phoneNumber.getVisibility() == View.VISIBLE) {
			mRl_phoneNumber.setVisibility(View.GONE);
			mIv_phoneNumberLine.setVisibility(View.GONE);
			mTv_checkOrEmail.setText(R.string.tv_regist_email);
			mEt_checkOrEmail.setHint(R.string.et_regist_email_hint);
			if (!mEt_checkOrEmail.getText().toString().trim().equals("")) {
				mEt_checkOrEmail.setText("");
			}
			if (!mEt_phoneNumber.getText().toString().trim().equals("")) {
				mEt_phoneNumber.setText("");
			}
		}
	}

	/**
	 * 显示手机号码项
	 */
	private void showPhoneNumberView() {
		if (mRl_phoneNumber.getVisibility() == View.GONE) {
			mRl_phoneNumber.setVisibility(View.VISIBLE);
			mIv_phoneNumberLine.setVisibility(View.VISIBLE);
			mTv_checkOrEmail.setText(R.string.text_checknumber);
			mEt_checkOrEmail.setHint(R.string.edittext_checknumber_hint);

		}
	}

	/**
	 * 隐藏服务电话项
	 */
	private void hideServicePhoneView() {
		if (mRl_servicePhoneNumber.getVisibility() == View.VISIBLE) {
			mRl_servicePhoneNumber.setVisibility(View.GONE);
			mIv_servicePhonenumberLine.setVisibility(View.GONE);
			if (!mEt_servicePhoneNumber.getText().toString().trim().equals("")) {
				mEt_servicePhoneNumber.setText("");
			}
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

	private void clearText() {
		mEt_phoneNumber.setText("");
		mEt_checkOrEmail.setText("");
		mTv_liveArea.setText("选择所在小区");
		mEt_servicePhoneNumber.setText("");
		mEt_name.setText("");
		mEt_password.setText("");
		mEt_repeatPassword.setText("");
	}

	// 获取验证码倒计时
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			mBt_getCheckNumber.setText("获取验证码");
			mBt_getCheckNumber.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			mBt_getCheckNumber.setClickable(false);
			//设置"30秒后"这三个字的字体大小和颜色。
			SpannableStringBuilder firstLine = new SpannableStringBuilder(millisUntilFinished / 1000 + "秒后\r\n");			
			firstLine.setSpan(new ForegroundColorSpan(Color.GRAY), 0, firstLine.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			firstLine.setSpan(new AbsoluteSizeSpan(16),0,firstLine.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			//设置"重新获取"这四个字的字体大小和颜色。
	        SpannableString secondLine = new SpannableString("重新获取");			
	        secondLine.setSpan(new ForegroundColorSpan(Color.GRAY), 0, secondLine.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        secondLine.setSpan(new AbsoluteSizeSpan(20),0,secondLine.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);	        
	       
	        SpannableStringBuilder text=firstLine.append(secondLine);
			mBt_getCheckNumber.setText(text);
			//mBt_getCheckNumber.setText(millisUntilFinished / 1000 + "秒后\r\n重新获取");
		}
	}

}

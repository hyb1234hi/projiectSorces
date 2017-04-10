package com.sinaleju.lifecircle.app.activity;

import org.apache.http.conn.ConnectTimeoutException;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.SwitchButton;
import com.sinaleju.lifecircle.app.customviews.SwitchButton.OnSwitchListener;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSChangePassword;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSCreateUser;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetAuthCode;
import com.sinaleju.lifecircle.app.utils.ADPatternUtils;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class ChangePosswordActivity extends BaseActivity implements
		OnClickListener {
	private static final String TAG = "ChangePosswordActivity";
	private SwitchButton mSwitchButton;
	private View mV_switchButton;
	private EditText mEt_originPassword;
	private EditText mEt_phoneNumber;
	private LinearLayout mV_originPassword;
	private Button mBt_getCheckNumber;
	private EditText mEt_checkNumber;
	private EditText mEt_newPassowrd;
	private EditText mEt_againPassword;
	private String userType;
	private String loginName;
	private boolean isPhone = true;// 记录是否是手机方式修改密码
	private boolean isOtherAccount;//判断当前账户是否是第三方账户且是否没有修改过密码
	private String userId;
	private String userName;
	private String originPasswordOrEmail;
	private String newPassword;
	private String againPassword;
	private String phoneNumber;
	private String checkNumber = "";
	private TimeCount mTimeCount;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_changepassword;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		mV_originPassword = (LinearLayout) findViewById(R.id.changepassword_ll_originpassword);
		mSwitchButton = (SwitchButton) findViewById(R.id.changepassword_type);
		mEt_originPassword = (EditText) findViewById(R.id.changepassword_origingpassword);
		mEt_phoneNumber = (EditText) findViewById(R.id.changepassword_phonenumber);
		mBt_getCheckNumber = (Button) findViewById(R.id.changepassword_getcheckednumber);
		mEt_checkNumber = (EditText) findViewById(R.id.changepassword_checknumber);
		mEt_newPassowrd = (EditText) findViewById(R.id.changepassword_newPassword);
		mEt_againPassword = (EditText) findViewById(R.id.changepassword_againnewpassword);
		userType = AppContext.curUser().getOrigin();
		loginName=AppContext.curUser().getLogin_name();
		userId = String.valueOf(AppContext.curUser().getUid());
		LogUtils.e(TAG, "urer origin " + userType+" loginName: "+loginName);
		if(userType.equals("sina")&&loginName.startsWith("sina_")){
			isOtherAccount=true;
		}else{
			isOtherAccount=false;
		}
		
		LogUtils.e(TAG, "urer origin " + userType+" loginName: "+loginName+" isOtherAccount: "+isOtherAccount);
		if (isOtherAccount) {
			// 第三方注册用户
			mV_switchButton = findViewById(R.id.changepassword_ll_swithbutton);
			mV_switchButton.setVisibility(View.VISIBLE);
			mV_originPassword.setVisibility(View.VISIBLE);
			mEt_originPassword.setVisibility(View.GONE);
			mSwitchButton.setText("手机", "邮箱");
			mSwitchButton.setOnSwitchListener(new OnSwitchListener() {

				@Override
				public void onSwitched(boolean isSwitchOn) {
					if (isSwitchOn) {
						// 手机
						isPhone = true;
						mV_originPassword.setVisibility(View.VISIBLE);
						mEt_originPassword.setVisibility(View.GONE);
						// 清空已填写信息。
						clearText();
					} else {
						// 邮箱
						clearText();
						checkNumber = "";
						isPhone = false;
						mV_originPassword.setVisibility(View.GONE);
						mEt_originPassword.setVisibility(View.VISIBLE);
						mEt_originPassword
								.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
						mEt_originPassword.setHint("请输入邮箱地址");
					}

				}

				/**
				 * 
				 */
				private void clearText() {
					mEt_originPassword.setText("");
					mEt_newPassowrd.setText("");
					mEt_againPassword.setText("");
					mEt_checkNumber.setText("");
					mEt_phoneNumber.setText("");
				}
			});

		} else {
			// 系统注册用户修改密码

		}
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.initRightButtonTextOrResId(null,
				R.drawable.selector_topbar_submit_button);
		mTitleBar.setTitleName("修改密码");
		mTimeCount = new TimeCount(30000, 1000);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mBt_getCheckNumber.setOnClickListener(this);
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imavBack:
			// 返回
			ADSoftInputUtils.hide(mContext);
			finish();
			break;
		case R.id.changepassword_getcheckednumber:
			phoneNumber = mEt_phoneNumber.getText().toString().trim();
			if ("".equals(phoneNumber)
					|| !ADPatternUtils.isMobileNO(phoneNumber)) {
				showToast("请输入正确的手机号码");
			} else {
				// 向服务器请求验证码
				RSGetAuthCode getAuthCode = new RSGetAuthCode(phoneNumber);
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

		case R.id.tvRightFunc1:
			// 提交
			boolean newPasswordIsNull = false;
			newPassword = mEt_newPassowrd.getText().toString().trim();
			againPassword = mEt_againPassword.getText().toString().trim();
			if ("".equals(newPassword) || "".equals(againPassword)) {
				newPasswordIsNull = true;
			}

			if (isOtherAccount) {
				// 第三方注册用户
				if (isPhone) {
					// 手机修改 接收手机号码和验证码
					checkNumber = mEt_checkNumber.getText().toString().trim();
					userName = mEt_phoneNumber.getText().toString().trim();
					if (newPasswordIsNull && "".equals(checkNumber)
							|| "".equals(userName)) {
						showToast("请输入完整信息");
						break;
					}

				} else {
					// 邮箱修改 接收邮箱地址
					userName = originPasswordOrEmail = mEt_originPassword
							.getText().toString().trim();
					if (newPasswordIsNull && "".equals(originPasswordOrEmail)) {
						showToast("请输入完整信息");
						break;
					}

					if (!ADPatternUtils.isEmail(originPasswordOrEmail)) {
						showToast("请输入正确的邮箱");
						break;
					}

				}

			} else {
				// 系统注册用户 接收原始密码
				originPasswordOrEmail = mEt_originPassword.getText().toString()
						.trim();
				if (newPasswordIsNull && "".equals(originPasswordOrEmail)) {
					showToast("请输入完整信息");
					break;
				}
			}
			int passwordLength = newPassword.length();
			if (passwordLength < 6 || passwordLength > 12) {
				showToast("密码长度应在6-12位之间");
				break;
			}
			if (!newPassword.equals(againPassword)) {
				showToast("两次输入的新密码不一致");
				break;
			}
			// 连接服务器
			if (!isOtherAccount) { //系统账户修改密码
				RSChangePassword rsChange = new RSChangePassword(userId,
						originPasswordOrEmail, newPassword);
				rsChange.setOnSuccessHandler(new OnSuccessHandler() {

					@Override
					public void onSuccess(Object result) {
						hideProgressDialog();
						showToast("密码修改成功");
						finish();
					}
				});
				rsChange.setOnFaultHandler(new OnFaultHandler() {

					@Override
					public void onFault(Exception ex) {
						// TODO Auto-generated method stub
						hideProgressDialog();
						if (ex instanceof ADRemoteException) {
							showToast(((ADRemoteException) ex).msg());
						} else if (ex instanceof ConnectTimeoutException) {
							showToast("请求超时，请重试");
						}

					}
				});
				if (PublicUtils.isNetworkAvailable(mContext)) {
					showProgressDialog("操作中...", true);
					rsChange.asyncExecute(mContext);
				} else {
					showToast("网络异常，请检查网络后重试");
				}

			} else {
				// 授权账户修改密码
				RSCreateUser rsCreate = new RSCreateUser(userId, userName,
						checkNumber, newPassword);
				rsCreate.setOnSuccessHandler(new OnSuccessHandler() {

					@Override
					public void onSuccess(Object result) {
						hideProgressDialog();
						AppContext.curUser().setLogin_name(userName);
						showToast("密码修改成功");
						finish();
					}
				});
				rsCreate.setOnFaultHandler(new OnFaultHandler() {

					@Override
					public void onFault(Exception ex) {
						// TODO Auto-generated method stub
						hideProgressDialog();
						if (ex instanceof ADRemoteException) {
							showToast(((ADRemoteException) ex).msg());
						} else if (ex instanceof ConnectTimeoutException) {
							showToast("请求超时，请重试");
						}

					}
				});
				if (PublicUtils.isNetworkAvailable(mContext)) {
					showProgressDialog("操作中...", true);
					rsCreate.asyncExecute(mContext);
				} else {
					showToast("网络异常，请检查网络后重试");
				}

			}

			break;

		default:
			break;
		}

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
		}
	}

}

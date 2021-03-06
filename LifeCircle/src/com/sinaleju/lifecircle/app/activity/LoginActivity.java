package com.sinaleju.lifecircle.app.activity;

import java.sql.SQLException;

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
import android.widget.ImageView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.cooperationaccount.sina.AccessTokenKeeper;
import com.sinaleju.lifecircle.app.cooperationaccount.sina.OAuthActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSConfirmComplete;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSLogin;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private SharedPreferences sp;
	protected static final String TAG = "LoginActivity";
	private static final int INTENT_GOTOOAUTHACTIVITY = 100;
	private View mV_titleView;
	//private Button mBt_title_close;
//	private Button mBt_title_regsit;

	private EditText mEt_userName;
	private EditText mEt_password;

	private String mUserName;
	private String mPassword;

	private int mCount;
	private ImageView mIv_retrivePassword;

	private Button mBt_login;
	private View mV_sina;
	// private View mV_qq;
	private String uid;
	private String origin;
	private boolean isResult = false;

	@Override
	protected void onStart() {
		checkInfoIsComplete();
		super.onStart();
	}

	@Override
	protected int getLayoutId() {
		//super.mTitleBar.setVisibility(View.GONE);
		return R.layout.ac_login;
	}

	@Override
	protected void initView() {
		// 测试
		/*
		 * if(SinaBindingUtils.isBinding(mContext)){ showToast("绑定成功"); }else{
		 * showToast("绑定失败"); }
		 */

		//mV_titleView = findViewById(R.id.login_title);
		//mBt_title_close = (Button) mV_titleView.findViewById(R.id.title_left);
		//mBt_title_regsit = (Button) mV_titleView.findViewById(R.id.title_right);
		LogUtils.v(TAG, "-------------onCreate--------------");
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.setTitleName("登录");
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_login_close);
		mTitleBar.initRightButtonTextOrResId("", R.drawable.selector_login_regist);
		mEt_userName = (EditText) findViewById(R.id.login_username);
		mEt_userName.requestFocus();
		mEt_password = (EditText) findViewById(R.id.login_password);
		mIv_retrivePassword = (ImageView) findViewById(R.id.login_retrivepassword);

		mBt_login = (Button) findViewById(R.id.login_btlogin);
		mBt_login.setClickable(false);

		mV_sina = findViewById(R.id.login_sina);
		// mV_qq = findViewById(R.id.login_qq);
	}

	protected void initData() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("userinfo", MODE_PRIVATE);		
		//uid=AppContext.curUser().getPlatform_id();
		//origin=AppContext.curUser().getPlatform_name();	
		
		// 如果是从注册页面中跳转过来，则直接登录。
		Intent data = getIntent();
		if (data != null) {
			LogUtils.i(TAG, "userName   " + mUserName);
			mUserName = getIntent().getStringExtra("loginname");
			mPassword = getIntent().getStringExtra("password");
			if (mUserName != null && mPassword != null) {
				login();
			}

			boolean isSinaLogin = data.getBooleanExtra("sinalogin", false);
			if (isSinaLogin && !isResult) {
				LogUtils.i(TAG, "微博登录。。。");
				sinaLogin();
			}
			data = null;
		}

	}

	@Override
	protected void initCallbacks() {
		//mBt_title_close.setOnClickListener(this);
		//mBt_title_regsit.setOnClickListener(this);
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);
		mIv_retrivePassword.setOnClickListener(this);

		mBt_login.setOnClickListener(this);
		mV_sina.setOnClickListener(this);
		// mV_qq.setOnClickListener(this);
		mEt_userName.addTextChangedListener(textWatcher);
		mEt_password.addTextChangedListener(textWatcher);

	}

	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			String text = s.toString().trim();
			if ("".equals(text)) {
				mCount--;
				// LogUtils.e(TAG, "-------" + mCount);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			String text = s.toString().trim();
			if ("".equals(text)) {
				mCount++;
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			checkInfoIsComplete();
		}

		/**
		 * 
		 */

	};

	private void checkInfoIsComplete() {
		if (mCount == 2) {
			mBt_login.setBackgroundResource(R.drawable.login_press);
			mBt_login.setClickable(true);
		} else {
			mBt_login.setBackgroundResource(R.drawable.login_normal);
			mBt_login.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 关闭
		case R.id.imavBack:
			this.finish();
			break;
		// 注册
		case R.id.tvRightFunc1:
			Intent registIntent = new Intent(mContext, RegistActivity.class);
			startActivity(registIntent);

			// finish();

			break;

		// 找回密码
		case R.id.login_retrivepassword:
			Intent retriveIntent = new Intent(mContext,
					RetrivePasswordActivity.class);
			startActivity(retriveIntent);

			break;
		// 登录
		case R.id.login_btlogin:
			// 判断登录信息是否填写完整
			mUserName = mEt_userName.getText().toString().trim();
			mPassword = mEt_password.getText().toString().trim();
			// 向服务器提交用户信息，并获取验证结果。
			login();
			break;
		// 新浪微博登录
		case R.id.login_sina:
			// 判断合作账号的登录状态
			// LogUtils.e(TAG,"token    "+
			// AccessTokenKeeper.readAccessToken(mContext).toString());
			sinaLogin();
			// Intent OAthIntent = new Intent(mContext, OAuthActivity.class);
			// startActivity(OAthIntent);
			// finish();
			break;
		// case R.id.login_qq:
		// showToast("查找好友");
		// Intent findFriendsIntent = new
		// Intent(mContext,SettingActivity.class);
		// startActivity(findFriendsIntent);
		// finish();
		// break;
		default:
			break;
		}

	}

	/**
	 * 
	 */
	private void sinaLogin() {
		LogUtils.i(TAG,
				"isValid  "
						+ AccessTokenKeeper.readAccessToken(mContext)
								.isSessionValid());

		if (AccessTokenKeeper.readAccessToken(mContext).isSessionValid()) {
			// 判断当前微博账户是否完善过个人信息。
			isCompleteInfo();
		} else {
			Intent OAthIntent = new Intent(mContext, OAuthActivity.class);
			startActivityForResult(OAthIntent, INTENT_GOTOOAUTHACTIVITY);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		isResult = true;
		if (requestCode == INTENT_GOTOOAUTHACTIVITY) {
			if (resultCode == Activity.RESULT_OK) {
				LogUtils.e(TAG, "onActivityResult  " + resultCode);
				isCompleteInfo();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				finish();
			}
		}
	}

	/**
	 * 
	 */
	private void isCompleteInfo() {
		uid = sp.getString("uid", "");
		origin = sp.getString("origin", "");
		LogUtils.i(TAG, "uid  " + uid + "  origin  " + origin);
		// 判断是否填写过完善信息。
		RSConfirmComplete rSConfirmComplete = new RSConfirmComplete(uid,
				origin, "", "", "1");
		rSConfirmComplete.setOnSuccessHandler(new OnSuccessHandler() {
			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				LogUtils.i(TAG, "result   " + result.toString());
				// 已填写完善信息
				String obj = result.toString();
				JSONObject jsonObjet = null;
				try {
					jsonObjet = new JSONObject(obj);
					String sResult = jsonObjet.optString("result");
					LogUtils.e(TAG, "result:   " + sResult);
					if (sResult.equals("1")) {
						JSONObject data = jsonObjet.optJSONObject("data");
						if (data == null) {
							LogUtils.e(TAG, "微博登录返回的信息是空");
							return;
						}
						JSONObject userInfo = data.getJSONObject("userinfo");
						int userId = userInfo.getInt("id");

						// 创建新用户
						createUser(userId);
						AppContext.curUser().setSina_accesstoken(
								sp.getString("token", ""));
						// 更新用户信息
						AppContext.curUser().update(getHelper());

						/*Intent homeIntent = new Intent(mContext,
								HomeActivity.class);
						startActivity(homeIntent);
						finish();*/
						//进入主页
						gotoHomeActivity();
					} else {
						Intent addInfoIntent = new Intent(mContext,
								AddInformationActivity.class);
						startActivity(addInfoIntent);
						finish();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					LogUtils.e(TAG, e.toString());
					showToast("创建用户失败");

				}

			}
		});

		rSConfirmComplete.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}else if (ex instanceof ADRemoteException) {	
					String msg=((ADRemoteException) ex).msg();
					if("".equals(msg)){
						//Intent addInfoIntent = new Intent(mContext,
						//		AddInformationActivity.class);
						//startActivity(addInfoIntent);
						//finish();
						
					}else{
						showToast(msg);
					}
					AccessTokenKeeper.clear(mContext);
				}
				
				LogUtils.i(TAG, ex.toString());
			}
		});

		if (PublicUtils.isNetworkAvailable(mContext)) {
			showProgressDialog("正在检查账号信息......", false);
			rSConfirmComplete.asyncExecute(mContext);

		} else {
			showToast("网络连接失败，请检查网络后重试");
		}
	}

	/**
	 * 
	 */
	private void login() {
		RSLogin rsLogin = new RSLogin(mUserName, mPassword);
		rsLogin.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				LogUtils.e(TAG, "result   " + result.toString());
				try {
					JSONObject json = new JSONObject(result.toString());
					JSONObject userinfo = json.getJSONObject("userinfo");
					int userId = userinfo.getInt("id");
					LogUtils.e(TAG, "userId   " + userId);
					if (userId == 0) {
						// TODO:
						return;
					}

					createUser(userId);

					LogUtils.i(TAG, "useId:   " + userId);
				} catch (JSONException e) {
					LogUtils.e(TAG, "parse json error ", e);
				} catch (SQLException e) {
					LogUtils.e(TAG, "sqlite create user error ", e);
				}
				//进入主页
				gotoHomeActivity();

			}

		
		});

		rsLogin.setOnFaultHandler(new OnFaultHandler() {
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
			showProgressDialog("登录中......", true);
			rsLogin.asyncExecute(mContext);
		} else {
			showToast("网络连接失败，请检查网络后重试");
		}

	}
	
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

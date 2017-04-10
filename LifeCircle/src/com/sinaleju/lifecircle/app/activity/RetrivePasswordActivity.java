package com.sinaleju.lifecircle.app.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.SwitchButton;
import com.sinaleju.lifecircle.app.customviews.SwitchButton.OnSwitchListener;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSFindPassword;
import com.sinaleju.lifecircle.app.utils.ADPatternUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class RetrivePasswordActivity extends BaseActivity implements
		OnClickListener {
	protected static final String TAG = "RetrivePasswordActivity";
	private SwitchButton m_switchButton;
	private EditText mEt_phoneOrEmail;
	private Button mBt_submit;
	private String mUserName;
	private boolean mAccountType = true;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		getWindow().setBackgroundDrawableResource(R.color.application_background);
		return R.layout.ac_retrivepassword;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		m_switchButton = (SwitchButton) findViewById(R.id.retrive_retrivetype);
		m_switchButton.setText("手机找回", "邮箱找回");
		mEt_phoneOrEmail = (EditText) findViewById(R.id.retrive_edittext);
		mBt_submit = (Button) findViewById(R.id.retrive_submit);
		
	}

	@Override
	protected void initData() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("找回密码");
		isComplete();
	}

	@Override
	protected void initCallbacks() {
		mTitleBar.setLeftButtonListener(this);
		mBt_submit.setOnClickListener(this);
		m_switchButton.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if (isSwitchOn) {
					mEt_phoneOrEmail.setText("");
					mEt_phoneOrEmail.setHint(R.string.retrivepasswordphonehint);
					mAccountType = true;
				} else {
					mEt_phoneOrEmail.setText("");
					mEt_phoneOrEmail.setHint(R.string.retrivepasswordemailhint);
					mAccountType = false;
				}
			}
		});

		mEt_phoneOrEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence text, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// LogUtils.e(TAG, "onTextChanged   " + text);
				isComplete();
			}

			/**
			 * 
			 */
			

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				// LogUtils.e(TAG, "beforeTextChanged");
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// LogUtils.e(TAG, "afterTextChanged");
			}
		});

	}
	private void isComplete() {
		mUserName = mEt_phoneOrEmail.getText().toString().trim();
		if (!"".equals(mUserName)) {
			mBt_submit
					.setBackgroundResource(R.drawable.regist_submit_press);
			mBt_submit.setClickable(true);
		} else {
			mBt_submit
					.setBackgroundResource(R.drawable.login_normal);
			mBt_submit.setClickable(false);
		}
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imavBack:
			this.finish();
			break;

		case R.id.retrive_submit:
			if (mAccountType) {
				if (!ADPatternUtils.isMobileNO(mUserName)) {
					showToast("请输入正确的手机号码");
					break;
				}
			} else {
				if (!ADPatternUtils.isEmail(mUserName)) {
					showToast("请输入正确的邮箱");
					break;
				}
			}
			RSFindPassword rsFindPassword = new RSFindPassword(mUserName);
			rsFindPassword.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					// TODO Auto-generated method stub
					hideProgressDialog();
					String message;
					if (mAccountType) {
						message = "新密码已经发送到您的手机，请注意查收";
					} else {
						message = "新密码已经发送到您的邮箱，请注意查收";
					}
					showToast(message);
					finish();
				}
			});
			rsFindPassword.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					// TODO Auto-generated method stub
					hideProgressDialog();
					if (ex instanceof ADRemoteException) {
						showToast(((ADRemoteException) ex).msg());
					}
					LogUtils.e(TAG, ex.toString());
				}
			});
			if (PublicUtils.isNetworkAvailable(mContext)) {
				rsFindPassword.asyncExecute(mContext);
				showProgressDialog("发送请求中", false);
			} else {
				showToast("网络连接失败，请检查网络后重试");
			}
			break;

		default:
			break;
		}

	}

}

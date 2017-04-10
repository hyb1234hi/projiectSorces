package com.sinaleju.lifecircle.app.base_activity;

import java.sql.SQLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.customviews.bottommenu.BaseBottomMenu;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends
		OrmLiteBaseActivity<DatabaseOpenHelper> {

	protected LinearLayout mContentLayout = null;
	protected BaseBottomMenu mBottomMenu = null;
	protected Activity mActivity = null;
	protected Context mContext = null;
	protected TextView contentNull = null;
	protected LayoutInflater mInflater = null;
	protected ProgressBar mProgressbar = null;
	protected ProgressDialog mProgressDialog = null;
	protected FrameLayout mBaseFrameLayout;
	protected TitleBar mTitleBar;
	protected AppContext mAppContext;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getBooleanExtra(AppConst.INTENT_ACTIVITY_CLEAR_TASK,
				false)) {
			((AppContext) getApplicationContext()).clearTask();
		}
		((AppContext) getApplicationContext()).addActs(this);
		setContentView(R.layout.ac_base);
		thisInit();
		//
		initView();
		initCallbacks();

	}

	private void thisInit() {
		mAppContext = (AppContext) getApplicationContext();
		mContext = mActivity = this;
		mInflater = LayoutInflater.from(this);
		mContentLayout = (LinearLayout) findViewById(R.id.lyotContent);
		mBottomMenu = (BaseBottomMenu) findViewById(R.id.mBottomMenu);
		mProgressbar = (ProgressBar) findViewById(R.id.baseProgressBar);
		mTitleBar = (TitleBar) findViewById(R.id.mTitleBar);
		mBaseFrameLayout = (FrameLayout) findViewById(R.id.ac_base_frame_layout);

		mContentLayout.addView(inflateView(getLayoutId()), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}

	public void showProgressDialog(int msgId, boolean isCancel){
		showProgressDialog(getString(msgId), isCancel);
	}
	
	public void showProgressDialog(String msg, boolean isCancel) {
		if (null == mProgressDialog)
			mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(msg != null ? msg : "");
		mProgressDialog.setCancelable(isCancel);
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				cancelTask();
			}
		});
		mProgressDialog.show();
	}

	public void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	protected void showContentNull() {
		contentNull.setVisibility(View.VISIBLE);
	}

	protected void hideContentNull() {
		mContentLayout.setVisibility(View.VISIBLE);
		contentNull.setVisibility(View.GONE);
	}

	protected void showProgress() {
		mContentLayout.setVisibility(View.GONE);
		mProgressbar.setVisibility(View.VISIBLE);
	}

	protected void hideProgress() {
		mContentLayout.setVisibility(View.VISIBLE);
		mProgressbar.setVisibility(View.GONE);
	}

	protected View inflateView(int resid) {
		return mInflater.inflate(resid, null);
	}

	protected void initBottomMenu(String... names) {
		mBottomMenu.addButton(names);
	}

	protected void initBottomMenu(Button... b) {
		mBottomMenu.addButton(b);
	}

	protected void showBottomMenu() {
		mBottomMenu.show();
	}

	protected void dismissBottomMenu() {
		mBottomMenu.dismiss();
	}

	protected void setBottomMenuButtonListener(int index,
			View.OnClickListener listener) {
		mBottomMenu.setListener(index, listener);
	}
	
	protected void cancelTask(){
		
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private Toast toast = null;

	public void showToast(int strId){
		showToast(getString(strId));
	}
	public void showToast(String text) {
		if (toast == null) {
			toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setText(text);
		}
		toast.show();
	}

	protected abstract int getLayoutId();

	protected abstract void initView();

	protected abstract void initData();

	protected abstract void initCallbacks();

	// from AppContext
	protected void createUser(int uid) throws SQLException {
		mAppContext.createUser(getHelper(), uid);
	}

	@Override
	public void onBackPressed() {
		if(mBottomMenu.isShowing()){
			mBottomMenu.dismiss();
		}else{
			super.onBackPressed();
		}
	}
}

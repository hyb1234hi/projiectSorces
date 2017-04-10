package com.sinaleju.lifecircle.app.base_activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.umeng.analytics.MobclickAgent;


public abstract class BaseFragmentActivity extends OrmLiteFragmentActivity<DatabaseOpenHelper>{

	private ProgressDialog uncancelProgressDialog;
	protected Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = this;
		initView();
	}
	protected void showUncancelProgressDialog(String msg){
		uncancelProgressDialog = new ProgressDialog(this);
		uncancelProgressDialog.setMessage(msg!=null?msg:"");
		uncancelProgressDialog.setCancelable(false);
		uncancelProgressDialog.show();
	}
	protected void hideUncancelProgressDialog(){
		if(uncancelProgressDialog!=null)
			uncancelProgressDialog.dismiss();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		initData();
		setListener();
	}
	@Override
	public void onResume(){
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		MobclickAgent.onPause(this);
	}
	

	

	protected abstract void initView();
	protected abstract void initData();
	protected abstract void setListener();
	public abstract void onClick(View v);

}

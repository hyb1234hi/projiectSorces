package com.iss.app;

import android.app.Activity;
import android.os.Bundle;

public abstract class IssActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		initView();
		initData();
		setListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	protected abstract void initView();
	protected abstract void initData();
	protected abstract void setListener();

}

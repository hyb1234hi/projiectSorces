package com.sinaleju.lifecircle.app.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;

public class AboutAct extends BaseActivity implements OnClickListener{

	private View mRoot;
	
	private TextView mail, weibo, version;

	@Override
	protected int getLayoutId() {
		return R.layout.about;
	}

	@Override
	protected void initView() {
		mTitleBar.setTitleName(R.string.about_us);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);
		mTitleBar.setLeftButtonListener(this);
	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.imavBack:
		finish();
		break;
		
		}
	}
	
}

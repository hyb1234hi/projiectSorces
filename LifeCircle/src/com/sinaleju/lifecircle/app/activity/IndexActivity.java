package com.sinaleju.lifecircle.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.OrmLiteFragmentActivity;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.fragment.BusinessHomeFragment;
import com.sinaleju.lifecircle.app.fragment.PersonalHomeFragment;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class IndexActivity extends OrmLiteFragmentActivity<DatabaseOpenHelper> {

	private boolean isPersonalIndex = false;
	private boolean isProperty = false;
	private int mVisitorId = 1;
	private PersonalHomeFragment mPerHomeFragment;
	private BusinessHomeFragment mBusHomeFragment;

	private int followStatus = -1;// 关注关系，寻找好友或新浪微博好友中到此界面，加关注或解除关注后告诉前界面。
	public static final int SUCESS_RESULT_CODE = 601;
	private static final String TAG = "IndexActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LogUtils.v(TAG, "-------------onCreate--------------");
		setContentView(R.layout.fr_home_frame);

		isPersonalIndex = getIntent()
				.getBooleanExtra("is_personal_index", true);
		isProperty = getIntent().getBooleanExtra("is_property", false);
		mVisitorId = getIntent().getIntExtra("mVisitor_Id", 1);
		FragmentTransaction t = this.getSupportFragmentManager()
				.beginTransaction();
		if (isPersonalIndex) {
			mPerHomeFragment = new PersonalHomeFragment();
			mPerHomeFragment.setFromIndexLeft(false);
			mPerHomeFragment.setmVisitorId(mVisitorId);
			t.add(R.id.fr_home_frame, mPerHomeFragment);
		} else {
			mBusHomeFragment = new BusinessHomeFragment();
			mBusHomeFragment.setmVisitorId(mVisitorId);
			mBusHomeFragment.setFromIndexLeft(false);
			mBusHomeFragment.setProperty(isProperty);
			t.add(R.id.fr_home_frame, mBusHomeFragment);
		}
		t.commit();
	}

	public void setFollowStatus(int followStatus) {
		this.followStatus = followStatus;
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		getIntent().putExtra("follow", followStatus);
		setResult(SUCESS_RESULT_CODE, getIntent());
		finish();
	}

}

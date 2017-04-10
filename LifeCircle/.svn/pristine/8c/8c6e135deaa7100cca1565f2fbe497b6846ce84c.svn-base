package com.sinaleju.lifecircle.app.base_activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.fragment.LeftHomeFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

public class BaseSlideActivity extends OrmLiteBaseSlideFragmentActivity<DatabaseOpenHelper> {

	protected LeftHomeFragment mLeftHomeFrag;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// set the Behind View
		setBehindContentView(R.layout.fr_slide_menu_left_frame);
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mLeftHomeFrag = new LeftHomeFragment();
		t.replace(R.id.menu_frame, mLeftHomeFrag);
		t.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.dimen_home_slide_menu_shadow_width);
		sm.setShadowDrawable(R.drawable.shape_home_slide_menu_shadow);
		sm.setBehindOffsetRes(R.dimen.dimen_slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}

	protected LeftHomeFragment getLeftHomeFragment() {
		if (mLeftHomeFrag == null) {
			mLeftHomeFrag = new LeftHomeFragment();
		}
		return mLeftHomeFrag;
	}
	
	@Override
	public void toggle() {
		super.toggle();
		
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
	
	
	
}

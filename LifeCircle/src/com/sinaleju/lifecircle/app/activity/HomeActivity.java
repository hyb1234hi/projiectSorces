package com.sinaleju.lifecircle.app.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseSlideActivity;
import com.sinaleju.lifecircle.app.fragment.AttentionListFragment;
import com.sinaleju.lifecircle.app.fragment.BusinessHomeFragment;
import com.sinaleju.lifecircle.app.fragment.FindFriendsFragment;
import com.sinaleju.lifecircle.app.fragment.HomeFragment;
import com.sinaleju.lifecircle.app.fragment.LeftEventsSquareFragment;
import com.sinaleju.lifecircle.app.fragment.LeftHomeFragment;
import com.sinaleju.lifecircle.app.fragment.MsgAndNoticeFragment;
import com.sinaleju.lifecircle.app.fragment.MyCommunitiesFragment;
import com.sinaleju.lifecircle.app.fragment.PersonalHomeFragment;
import com.sinaleju.lifecircle.app.fragment.RightHomeFragment;
import com.sinaleju.lifecircle.app.fragment.SettingFragment;
import com.sinaleju.lifecircle.app.service.LifeCircleService;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

public class HomeActivity extends BaseSlideActivity {
	private HomeFragment mHomeFragment;
	private PersonalHomeFragment mPersonalHome;
	private BusinessHomeFragment mBusinessHome;
	private ProgressDialog mProgressDialog;
	private boolean kill = true;
	private static final String TAG = "HomeActivity";
	private static final long MSG_SEND_DELAY = 350;

	private RightHomeFragment rightView;
	private FindFriendsFragment findFriendsFragment;
	private MsgAndNoticeFragment msgAndNoticeFragment;

	private List<MotionEventInterceptor> mMotionEventInterceptors = new LinkedList<HomeActivity.MotionEventInterceptor>();
	private List<OnBackPressCallBack> mOnBackPressCallBacks = new LinkedList<HomeActivity.OnBackPressCallBack>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.v(TAG, "-------------onCreate--------------");
		((AppContext) getApplicationContext()).clearTask();
		((AppContext) getApplicationContext()).addActs(this);
		((AppContext) getApplicationContext()).setHomeActivity(this);
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		rightView = new RightHomeFragment();
		// rightView.registMediator();
		LogUtils.e(TAG, "registMediator");
		getSlidingMenu().setSecondaryMenu(R.layout.fr_slide_menu_right_frame);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shape_home_slide_menu_shadowright);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fr_slide_menu_right_frame, rightView).commit();

		AppContext.width = getWindowManager().getDefaultDisplay().getWidth();
		AppContext.height = getWindowManager().getDefaultDisplay().getHeight();

		setContentView(R.layout.fr_home_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fr_home_frame, getHomeFragment()).commit();

	}

	// 进入加入小区
	public void gotoAddCellsPage() {
		toggleWithTouchDisable();

		mHandler.sendEmptyMessageDelayed(MSG_GOTO_ADD_CELL_PAGE, MSG_SEND_DELAY);
	}

	// 进入设置页
	public void gotoSettingPage() {
		toggleWithTouchDisable();
		mHandler.sendEmptyMessageDelayed(MSG_GOTO_SETTING_PAGE, MSG_SEND_DELAY);
	}

	// 进入活动广场
	public void gotoEventsSquarePage() {
		toggleWithTouchDisable();
		mHandler.sendEmptyMessageDelayed(MSG_GOTO_EVENTS_SQUARE_PAGE, MSG_SEND_DELAY);
	}

	public void gotoPersonalPage(int visitorId, boolean isFromIndexLeft) {
		toggleWithTouchDisable();

		Message msg = mHandler.obtainMessage();
		msg.what = MSG_GOTO_PERSONAL_PAGE;
		Bundle b = new Bundle();
		b.putInt("vid", visitorId);
		b.putBoolean("isf", isFromIndexLeft);
		msg.setData(b);
		mHandler.sendMessageDelayed(msg, MSG_SEND_DELAY);
	}

	public void gotoPersonalPageFromHome(int visitorId, boolean isFromIndexLeft) {
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_GOTO_PERSONAL_PAGE_FROM_HOME;
		Bundle b = new Bundle();
		b.putInt("vid", visitorId);
		b.putBoolean("isf", isFromIndexLeft);
		msg.setData(b);
		mHandler.sendMessageDelayed(msg, MSG_SEND_DELAY);
	}

	public void gotoFindFriendsPage(int uid) {
		toggleWithTouchDisable();

		Message msg = mHandler.obtainMessage();
		msg.what = MSG_GOTO_FIND_FRIEND_PAGE;
		msg.arg1 = uid;
		mHandler.sendMessageDelayed(msg, MSG_SEND_DELAY);
	}

	public void gotoMsgAndNoticePage(int uid) {
		toggleWithTouchDisable();

		Message msg = mHandler.obtainMessage();
		msg.what = MSG_GOTO_MSG_AND_NOTICE_PAGE;
		msg.arg1 = uid;
		mHandler.sendMessageDelayed(msg, MSG_SEND_DELAY);
	}

	public void gotoBusinessPage(int visitorId, boolean isProperty, boolean isFromIndexLeft) {
		toggleWithTouchDisable();

		Message msg = mHandler.obtainMessage();
		msg.what = MSG_GOTO_BUSSINESS_PAGE;
		Bundle b = new Bundle();
		b.putInt("vid", visitorId);
		b.putBoolean("isp", isProperty);
		b.putBoolean("isf", isFromIndexLeft);
		msg.setData(b);
		mHandler.sendMessageDelayed(msg, MSG_SEND_DELAY);

	}

	public void gotoBusinessPageFromHome(int visitorId, boolean isProperty, boolean isFromIndexLeft) {
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_GOTO_BUSSINESS_PAGE;
		Bundle b = new Bundle();
		b.putInt("vid", visitorId);
		b.putBoolean("isp", isProperty);
		b.putBoolean("isf", isFromIndexLeft);
		msg.setData(b);
		mHandler.sendMessageDelayed(msg, MSG_SEND_DELAY);

	}

	public void backToMainPage() {
		if (getSlidingMenu().isMenuShowing()) {
			toggleWithTouchDisable();
		}

		mHandler.sendEmptyMessageDelayed(MSG_BACK_TO_MAIN_PAGE, MSG_SEND_DELAY);

	}

	public void gotoAttentionListPage() {
		toggleWithTouchDisable();
		mHandler.sendEmptyMessageDelayed(MSG_GOTO_ATTENTION_LIST, MSG_SEND_DELAY);
	}

	private HomeFragment getHomeFragment() {
		if (mHomeFragment == null) {
			mHomeFragment = new HomeFragment();
		}
		return mHomeFragment;
	}

	private BusinessHomeFragment getBusinessHomeFragment(int visitorId, boolean isProperty,
			boolean isFromIndexLeft) {
		if (mBusinessHome == null) {
			mBusinessHome = new BusinessHomeFragment();
			mBusinessHome.setmVisitorId(visitorId);
			mBusinessHome.setFromIndexLeft(isFromIndexLeft);
			mBusinessHome.setProperty(isProperty);
		}
		return mBusinessHome;
	}

	private PersonalHomeFragment getPersonalHomeFragment(int visitorId, boolean isFromIndexLeft) {
		if (mPersonalHome == null) {
			mPersonalHome = new PersonalHomeFragment();
			mPersonalHome.setFromIndexLeft(isFromIndexLeft);
			mPersonalHome.setmVisitorId(visitorId);
		}
		return mPersonalHome;
	}

	private FindFriendsFragment getFindFriendsFragment(int userId) {
		findFriendsFragment = new FindFriendsFragment(userId);
		return findFriendsFragment;
	}

	private MsgAndNoticeFragment getMsgAndNoticeFragment(int userId) {
		msgAndNoticeFragment = new MsgAndNoticeFragment(userId);
		return msgAndNoticeFragment;
	}

	public RightHomeFragment getRightHomeFragment() {
		return rightView;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		// 这里需要销毁掉home的mediator 不然在退出重新登录后还会存在原来的
		if (getHomeFragment() != null) {
			getHomeFragment().removeMediator();
		}
		removeCallBacks();
		super.onDestroy();
	}

	private void removeCallBacks() {
		removeMotionEventInterceptors();
		removeOnBackPressCallBacks();
	}

	@Override
	public void onBackPressed() {
		LogUtils.v(TAG, "-------------onBackPressed--------------");
		if (getLeftHomeFragment().getCurIndex() == LeftHomeFragment.FIND_FRIENDS_INDEX
				&& null != findFriendsFragment) {
			if (findFriendsFragment.onBackPressed())
				return;
		}

		if (mOnBackPressCallBacks.size() > 0) {
			for (OnBackPressCallBack c : mOnBackPressCallBacks) {
				c.callback();
			}
			return;
		}

		new AlertDialog.Builder(this).setTitle(null).setMessage("是否确认退出？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// HomeActivity.super.onBackPressed();
						stopService(new Intent(HomeActivity.this, LifeCircleService.class));
						MobclickAgent.onKillProcess(HomeActivity.this);
						finish();
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}

	public void onResume() {
		super.onResume();
		Runtime.getRuntime().gc();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	protected void showProgressDialog(String msg, boolean isCancel) {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(msg != null ? msg : "");
		mProgressDialog.setCancelable(isCancel);
		mProgressDialog.show();
	}

	protected void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	@Override
	public void toggle() {
		if (rightView == null) {
			System.out.println("righView is null");
		}
		super.toggle();
	}

	public void toggleWithTouchDisable() {
		setTouchEnable(false);
		toggle();
	}

	public void disableSeconderyMenu() {
		getSlidingMenu().setSeconderyMenuShowEnable(false);
	}

	public void enableSeconderyMenu() {
		getSlidingMenu().setSeconderyMenuShowEnable(true);
	}

	public void disableSlide() {
		setSlideEnable(false);
	}

	public void enableSlide() {
		setSlideEnable(true);
	}

	private void setSlideEnable(boolean enable) {
		getSlidingMenu().setSeconderyMenuShowEnable(enable);
		getSlidingMenu().setLeftMenuShowEnable(enable);
	}

	private static final int MSG_BACK_TO_MAIN_PAGE = 1;
	private static final int MSG_GOTO_ATTENTION_LIST = 2;
	private static final int MSG_GOTO_PERSONAL_PAGE = 3;
	private static final int MSG_GOTO_BUSSINESS_PAGE = 4;
	private static final int MSG_GOTO_FIND_FRIEND_PAGE = 5;
	private static final int MSG_GOTO_ADD_CELL_PAGE = 6;
	private static final int MSG_GOTO_SETTING_PAGE = 7;
	private static final int MSG_GOTO_MSG_AND_NOTICE_PAGE = 8;// 消息中心
	private static final int MSG_GOTO_EVENTS_SQUARE_PAGE = 9;// 活动广场
	private static final int MSG_GOTO_PERSONAL_PAGE_FROM_HOME = 10;// 从首页进个人/商家
																	// 主页

	private ADHandler<HomeActivity> mHandler = new ADHandler<HomeActivity>(this,
			new MessageHandler<HomeActivity>() {

				@Override
				public void handleMessage(HomeActivity o, Message msg) {
					// set enable
					setTouchEnable(true);

					switch (msg.what) {
					case MSG_BACK_TO_MAIN_PAGE:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, o.getHomeFragment()).commit();
						o.enableSeconderyMenu();
						break;
					case MSG_GOTO_FIND_FRIEND_PAGE:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, o.getFindFriendsFragment(msg.arg1))
								.commit();
						break;
					case MSG_GOTO_MSG_AND_NOTICE_PAGE:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, o.getMsgAndNoticeFragment(msg.arg1))
								.commit();
						break;
					case MSG_GOTO_SETTING_PAGE:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, new SettingFragment()).commit();
						break;
					case MSG_GOTO_ATTENTION_LIST:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, new AttentionListFragment()).commit();
						break;
					case MSG_GOTO_PERSONAL_PAGE:
					case MSG_GOTO_PERSONAL_PAGE_FROM_HOME:
						Bundle b = msg.getData();
						o.getSupportFragmentManager()
								.beginTransaction()
								.replace(
										R.id.fr_home_frame,
										o.getPersonalHomeFragment(b.getInt("vid"),
												b.getBoolean("isf"))).commit();
						break;
					case MSG_GOTO_ADD_CELL_PAGE:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, new MyCommunitiesFragment()).commit();
						break;
					case MSG_GOTO_BUSSINESS_PAGE:
						Bundle b2 = msg.getData();
						o.getSupportFragmentManager()
								.beginTransaction()
								.replace(
										R.id.fr_home_frame,
										o.getBusinessHomeFragment(b2.getInt("vid"),
												b2.getBoolean("isp"), b2.getBoolean("isf")))
								.commit();
						break;
					case MSG_GOTO_EVENTS_SQUARE_PAGE:
						o.getSupportFragmentManager().beginTransaction()
								.replace(R.id.fr_home_frame, new LeftEventsSquareFragment())
								.commit();
						break;
					}

				}

			});

	private boolean mTouchEnable = true;

	private void setTouchEnable(boolean enabled) {
		mTouchEnable = enabled;
	}

	private boolean isTouchEnable() {
		return mTouchEnable;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (!isTouchEnable()) {
			return true;
		}

		if (mMotionEventInterceptors.size() > 0) {
			for (MotionEventInterceptor m : mMotionEventInterceptors) {
				if (m.onIntercept(ev)) {
					int action = ev.getAction();
					if (action == MotionEvent.ACTION_DOWN) {
						LogUtils.i(TAG, "dispatchTouchEvent     down");
					}
					return super.dispatchTouchEvent(ev);
				}
			}
			return true;
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (!isTouchEnable()) {
			return true;
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 
	 * @author dan_alan
	 * 
	 */
	public interface MotionEventInterceptor {
		public boolean onIntercept(MotionEvent event);
	}

	public interface OnBackPressCallBack extends H_CallBack {

	}

	public interface H_CallBack {
		public void callback();
	}

	public void removeMotionEventInterceptor(MotionEventInterceptor m) {
		mMotionEventInterceptors.remove(m);
	}

	public void registMotionEventInterceptor(MotionEventInterceptor m) {
		if (!mMotionEventInterceptors.contains(m)) {
			mMotionEventInterceptors.add(m);
		}
	}

	public void removeOnBackPressCallBack(OnBackPressCallBack m) {
		mOnBackPressCallBacks.remove(m);
	}

	public void registOnBackPressCallBack(OnBackPressCallBack m) {
		if (!mOnBackPressCallBacks.contains(m)) {
			mOnBackPressCallBacks.add(m);
		}
	}

	private void removeOnBackPressCallBacks() {
		mOnBackPressCallBacks.clear();
		mOnBackPressCallBacks = null;
	}

	private void removeMotionEventInterceptors() {
		mMotionEventInterceptors.clear();
		mMotionEventInterceptors = null;
	}

}

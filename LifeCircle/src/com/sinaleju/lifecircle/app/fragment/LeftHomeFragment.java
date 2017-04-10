package com.sinaleju.lifecircle.app.fragment;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.service.LifeCircleService;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class LeftHomeFragment extends BaseFragment implements OnClickListener {

	protected static final String TAG = "LeftHomeFragment";

	private View mContentView = null;

	private RelativeLayout mHeadLayout = null;
	private ImageView mHeadImage = null;
	private TextView mHeadName = null;
	private TextView mHeadSign = null;
	private ImageView mIndexJiantou = null;

	private RelativeLayout mIndexLayout = null;
	private RelativeLayout mAddedLayout = null;
	private RelativeLayout mEventsSquareLayout = null;
	private RelativeLayout mAttenLayout = null;
	private RelativeLayout mMsgLayout = null;
	private RelativeLayout mFriendLayout = null;
	private RelativeLayout mSettingLayout = null;

	private ImageView mMsgIcon = null;
	private boolean isPerHome = true;
	private boolean isProperty = false; // 是否是物业
	private UserBean mUserBean = null;

	private int index;
	private final int PERSONAL_PAGE_INDEX = 1;
	private final int HOME_INDEX = 2;
	private final int ADDED_UNIT_INDEX = 3;
	private final int EVENTS_SQUARE_INDEX = 4;
	private final int ATTENTION_INDEX = 5;
	private final int MSG_INDEX = 6;
	public static final int FIND_FRIENDS_INDEX = 7;
	private final int SETTING_INDEX = 8;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.v(TAG, "-------------onCreateView--------------");
		if (mContentView == null) {
			mContentView = inflater.inflate(R.layout.fr_left_view, container, false);
			initView();
			initData();
			setListener();
		} else {
			((ViewGroup) mContentView.getParent()).removeAllViews();
		}
		return mContentView;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		ApplicationFacade.getInstance().registerMediator(
				new LeftHomeMediator(LeftHomeMediator.NAME, null));

		mHeadLayout = (RelativeLayout) mContentView.findViewById(R.id.left_head_layout);
		mHeadImage = (ImageView) mContentView.findViewById(R.id.left_head_image);
		mHeadName = (TextView) mContentView.findViewById(R.id.left_head_name);
		mHeadSign = (TextView) mContentView.findViewById(R.id.left_head_sign);
		mIndexLayout = (RelativeLayout) mContentView.findViewById(R.id.left_index_layout);
		mAddedLayout = (RelativeLayout) mContentView.findViewById(R.id.left_added_layout);
		mEventsSquareLayout = (RelativeLayout) mContentView
				.findViewById(R.id.left_events_square_layout);
		mAttenLayout = (RelativeLayout) mContentView.findViewById(R.id.left_attention_layout);
		mMsgLayout = (RelativeLayout) mContentView.findViewById(R.id.left_msg_layout);
		mFriendLayout = (RelativeLayout) mContentView.findViewById(R.id.left_friend_layout);
		mSettingLayout = (RelativeLayout) mContentView.findViewById(R.id.left_setting_layout);
		mMsgIcon = (ImageView) mContentView.findViewById(R.id.left_msg_num_icon);
		mIndexJiantou = (ImageView) mContentView.findViewById(R.id.left_index_jiantou);
		mIndexLayout.setSelected(true);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		mHeadLayout.setOnClickListener(this);
		mIndexLayout.setOnClickListener(this);
		mAddedLayout.setOnClickListener(this);
		mEventsSquareLayout.setOnClickListener(this);
		mAttenLayout.setOnClickListener(this);
		mMsgLayout.setOnClickListener(this);
		mFriendLayout.setOnClickListener(this);
		mSettingLayout.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mUserBean = AppContext.curUser();
		if (mUserBean != null) {
			if (!TextUtils.isEmpty(mUserBean.getHeaderUrl())) {
				ImageLoader.getInstance(getActivity())
						.displayImage(
								mUserBean.getHeaderUrl(),
								mHeadImage,
								SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils
										.getUserIndexDefaultHeadImage(mUserBean.getType()), 100),
								new FadeInImageLoadingListener(mHeadImage));
			} else {
				mHeadImage.setImageResource(PublicUtils.getUserIndexDefaultHeadImage(mUserBean
						.getType()));
			}
			if (!TextUtils.isEmpty(mUserBean.getName())) {
				mHeadName.setText(mUserBean.getName());
			} else {
				mHeadName.setText("游客");
			}
			if (!TextUtils.isEmpty(mUserBean.getSignature())) {
				mHeadSign.setText(mUserBean.getSignature());
			} else {
				mHeadSign.setText("您还没有填写签名");
			}

			if (mUserBean.getType() == 0) {
				isPerHome = true;
			} else if (mUserBean.getType() == 1) {
				isPerHome = false;
				isProperty = false;
			} else if (mUserBean.getType() == 2) {
				isPerHome = false;
				isProperty = true;
			}
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.left_head_layout:

			LogUtils.v(TAG, "-------------onClick-------left_head_layout-------");

			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_blue);
				setLayoutSelected(null);
				if (isPerHome) {
					((HomeActivity) getActivity()).gotoPersonalPage(mUserBean.getUid(), true);
				} else {
					((HomeActivity) getActivity()).gotoBusinessPage(mUserBean.getUid(), isProperty,
							true);
				}
			}
			index = PERSONAL_PAGE_INDEX;
			break;
		case R.id.left_index_layout:
			LogUtils.v(TAG, "-------------onClick-------left_index_layout-------");
			mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
			setLayoutSelected(mIndexLayout);
			mIndexLayout.setSelected(true);
			((HomeActivity) getActivity()).backToMainPage();
			index = HOME_INDEX;
			break;
		case R.id.left_added_layout:
			LogUtils.v(TAG, "-------------onClick-------left_added_layout-------");
			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mAddedLayout);
				((HomeActivity) getActivity()).gotoAddCellsPage();
			}
			index = ADDED_UNIT_INDEX;
			break;
		case R.id.left_events_square_layout:
			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mEventsSquareLayout);
				((HomeActivity) getActivity()).gotoEventsSquarePage();
			}
			index = EVENTS_SQUARE_INDEX;
			break;
		case R.id.left_attention_layout:
			LogUtils.v(TAG, "-------------onClick-------left_attention_layout-------");
			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mAttenLayout);
				((HomeActivity) getActivity()).gotoAttentionListPage();
			}
			index = ATTENTION_INDEX;
			break;
		case R.id.left_msg_layout:
			LogUtils.v(TAG, "-------------onClick-------left_msg_layout-------");
			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mMsgLayout);
				((HomeActivity) getActivity()).gotoMsgAndNoticePage(mUserBean.getUid());
			}
			index = MSG_INDEX;
			break;
		case R.id.left_friend_layout:
			LogUtils.v(TAG, "-------------onClick-------left_friend_layout-------");
			if (index == FIND_FRIENDS_INDEX) {
				((AppContext) getActivity().getApplication()).getHomeActivity().toggle();
				return;
			}
			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mFriendLayout);
				((HomeActivity) getActivity()).gotoFindFriendsPage(mUserBean.getUid());
			}
			index = FIND_FRIENDS_INDEX;
			break;
		case R.id.left_setting_layout:
			LogUtils.v(TAG, "-------------onClick-------left_setting_layout-------");
			if (AppContext.isValid(getActivity())) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mSettingLayout);
				((HomeActivity) getActivity()).gotoSettingPage();
			}
			index = SETTING_INDEX;
			break;
		default:
			break;
		}

		LogUtils.d(TAG, "index === " + index);
	}

	private void setLayoutSelected(RelativeLayout markLayout) {
		RelativeLayout[] all = { mIndexLayout, mAddedLayout, mEventsSquareLayout, mAttenLayout,
				mMsgLayout, mFriendLayout, mSettingLayout };
		if (markLayout != null) {
			markLayout.setSelected(true);
		}
		for (int i = 0; i < all.length; i++) {
			if (all[i] != markLayout && all[i].isSelected()) {
				all[i].setSelected(false);
			}
		}

	}

	public void performMainButtonClick() {
		mIndexLayout.performClick();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ApplicationFacade.getInstance().removeMediator(LeftHomeMediator.NAME);
		super.onDestroy();
	}

	private class LeftHomeMediator extends Mediator {
		public static final String NAME = "LeftHomeFragment";

		public LeftHomeMediator(String mediatorName, Object viewComponent) {
			super(mediatorName, viewComponent);
		}

		@Override
		public void handleNotification(INotification notification) {
			String name = notification.getName();
			if (name.equals(AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH)) {
				initData();
			} else if (name.equals(AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE)) {
				if (LifeCircleService.getIsNewMessage()) {
					mMsgIcon.setVisibility(View.VISIBLE);
				} else {
					mMsgIcon.setVisibility(View.GONE);
				}
			} else if (name.equals(AppConst.APP_FACADE_CLICK_HOMEPAGE_TOP)) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_blue);
				setLayoutSelected(null);
				index = PERSONAL_PAGE_INDEX;
			} else if (name.equals(AppConst.APP_FACADE_ENTER_HOMEPAGE_TOP)) {
				mIndexJiantou.setImageResource(R.drawable.ac_left_jiantou_grey);
				setLayoutSelected(mIndexLayout);
				mIndexLayout.setSelected(true);
				index = HOME_INDEX;
			}
			super.handleNotification(notification);
		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH,
					AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE,
					AppConst.APP_FACADE_CLICK_HOMEPAGE_TOP, AppConst.APP_FACADE_ENTER_HOMEPAGE_TOP };
		}

	}

	public int getCurIndex() {
		return index;
	}
}

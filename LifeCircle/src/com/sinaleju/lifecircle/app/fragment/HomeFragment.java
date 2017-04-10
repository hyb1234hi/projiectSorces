package com.sinaleju.lifecircle.app.fragment;

import java.sql.SQLException;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView.OnPositionChangedListener;
import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.assist.PauseOnScrollListener;
import com.iss.view.common.ToastAlone;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.activity.HomeActivity.MotionEventInterceptor;
import com.sinaleju.lifecircle.app.activity.HomeActivity.OnBackPressCallBack;
import com.sinaleju.lifecircle.app.activity.SendMsgMainActivity;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.customviews.HomePageSelectorView;
import com.sinaleju.lifecircle.app.customviews.HomePageSelectorView.ICallRefreshableView;
import com.sinaleju.lifecircle.app.customviews.LCScrollBarPanel;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_HomePageTop;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.exception.ADNetworkNotAvailableException;
import com.sinaleju.lifecircle.app.model.Model_HomePageTop;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Visitor;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet.NodeList;
import com.sinaleju.lifecircle.app.model.json.JSONParser_HomePageTopDefault;
import com.sinaleju.lifecircle.app.model.json.JSONParser_Trends;
import com.sinaleju.lifecircle.app.service.LifeCircleService;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSHomePageTop;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSTrends;
import com.sinaleju.lifecircle.app.utils.ADAnimationUtils;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.ADInteractUtils;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LCCaution;
import com.sinaleju.lifecircle.app.utils.LCPageCounter2;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;
import com.umeng.analytics.MobclickAgent;

public class HomeFragment extends BaseFragment implements ICallRefreshableView {

	// TAG
	protected static final String TAG = "HomeFragment";
	private static final int SET_TYPE_COUNT = 15;
	private static final int MSG_SET_REFRESH_COMPLETE = 1;
	// views
	private TitleBar mTitleBar;
	private PullToRefreshListView mListView;
	private ImageView mSelectorButton;
	private ImageView mEditorButton;
	// -views
	private HomePageSelectorView mSelectorView;
	private ListView mSpinnerView;
	private LCScrollBarPanel mScrollBar;
	// --views
//	private ImageView mBaseImageView;
	private RelativeLayout rl_home_frame = null;
	private ImageView mEmptyView;
	private View mContent;
	private View mRootView;
	// ---views
	private Item_HomePageTop mHomeTopView;

	// Adapter
	private SpinnerAdapter mSpinnerAdapter = new SpinnerAdapter();
	private MultiTypesAdapter mTimeLineAdapter;

	// data
	private MultiModelsSet mSet;
	private UserBean mUser;
	private Visitor mVisitor;
	// -data
	private NodeList<MultiModelBase> mRealNodeList = null;
	private NodeList2<MultiModelBase> mHotNodeList = null;

	// others
	private LCCaution mCaution;

	private boolean mEntireRefresh;
	// 响应进入发信息界面
	public static ProgressDialog dialog = null;

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("HomeFragment");
		// set slide left enable
		((HomeActivity) getActivity()).enableSeconderyMenu();

		// check if refresh entire page
		if (entireRefreshEnabled()) {

			// disable entire refresh
			disableEntireRefresh();

			// set refesh
			startPullRefreshAnimation();
			performCommunitySet(getUid(), getCommunities());

		}

		// regist a mediator
		registMediator();

	}

	/**
	 * 
	 */
	public void onPause() {
		super.onPause();
		((HomeActivity) getActivity()).disableSeconderyMenu();
		MobclickAgent.onPageEnd("HomeFragment"); // 保证 onPageEnd 在onPause
													// 之前调用,因为 onPause 中会保存信息
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.v(TAG, "-------------initView-------");
		if (mRootView == null) {

			// initViews
			initViews(inflater, container);

			// init list view
			initListView();

			// init title layout
			initTitleLayout();

			// initCuation
			initCuation();

			// initUser
			initUser();

			// setListeners
			setListeners();

			// init
			performInit();

		} else {
			((ViewGroup) mRootView.getParent()).removeAllViews();
		}

		return mRootView;
	}

	private void initListView() {
		mScrollBar = new LCScrollBarPanel(getActivity());
		mScrollBar.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		((ExtendedListView) mListView.getRefreshableView()).setScrollBarPanel(mScrollBar);
		((ExtendedListView) mListView.getRefreshableView())
				.setOnPositionChangedListener(new OnPositionChangedListener() {

					@Override
					public void onPositionChanged(ExtendedListView listView, int position,
							View scrollBarPanel) {
						if (mTimeLineAdapter != null) {
							if (position > 0) {
								Object o = mTimeLineAdapter.getItem(position - 1);
								if (o != null && o instanceof Model_TrendsBase) {
									Model_TrendsBase m = (Model_TrendsBase) o;
									long time = m.getAddTime();
									mScrollBar.updateTime(time);
								} else {
									mScrollBar.updateTime(System.currentTimeMillis() / 1000);
								}
							}
						}
					}
				});
	}

	/**
	 * Init all views
	 * 
	 * @param inflater
	 *            : LayoutInflater
	 * @param container
	 *            : The container of the fragment
	 */
	private void initViews(LayoutInflater inflater, ViewGroup container) {

		// root
		mRootView = inflater.inflate(R.layout.fr_home, container, false);

		// views
		mSelectorButton = (ImageView) mRootView.findViewById(R.id.bottomButtonLeft);
		mEditorButton = (ImageView) mRootView.findViewById(R.id.bottomButtonRight);
		mListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
		mTitleBar = (TitleBar) mRootView.findViewById(R.id.mTitleBar);

		// -views
		mSelectorView = (HomePageSelectorView) mRootView.findViewById(R.id.selector);
		mSelectorView.setAllSelect();
		mSelectorView.setBack(this);
		mSpinnerView = (ListView) mRootView.findViewById(R.id.spinner);

		// --views
		mEmptyView = (ImageView) mRootView.findViewById(R.id.emptyView);
		mContent = mRootView.findViewById(R.id.lyotContent);
		rl_home_frame = (RelativeLayout)mRootView.findViewById(R.id.rl_hoome_frame);
//		mBaseImageView = (ImageView) mRootView.findViewById(R.id.base_fudong_image);

		if (((AppContext) getActivity().getApplication()).ismNeedShowHome()) {
			((AppContext) getActivity().getApplication()).setmNeedShowHome(false);
			((AppContext) getActivity().getApplication()).updateNeedShowHome();
			showFuDongView();
		}

	}

	/**
	 * initTitleLayout
	 */
	protected void initTitleLayout() {
		mTitleBar.setTitleName("小区首页");
		mTitleBar.setBackgroundResource(R.drawable.topbar_bg_transparent);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_home_page_top_bar_left_button);
		mTitleBar.initRightButtonTextOrResId(0, R.drawable.selector_home_page_top_bar_right_button);

		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((AppContext) getActivity().getApplication()).getHomeActivity().toggle();
			}
		});
		mTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((AppContext) getActivity().getApplication()).getHomeActivity().showSecondaryMenu();
			}
		});
	}

	/**
	 * Init caution
	 */
	private void initCuation() {
		mCaution = new LCCaution(mEmptyView, mContent);
		mCaution.setOnFailClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mCaution.recall();
				updateUserInfo();
			}
		});
	}

	/**
	 * Init user from AppContext
	 */
	private void initUser() {
		mUser = AppContext.curUser();
		mVisitor = AppContext.curVisitor();
	}

	/**
	 * To init with a user or a visitor
	 */
	private void performInit() {

		if (mUser != null)
			initWithUser();
		else
			initWithVisitor();
	}

	/**
	 * Perform init with a user
	 */
	private void initWithUser() {
		updateUserInfo();
	}

	/**
	 * Perform init with a visitor
	 */
	private void initWithVisitor() {

		CommunityBean bean = mVisitor.getCommunity();
		if (bean == null) {
			/** Caution */
			cautionWithNoEntryErro();
		}
		// set community with a visitor
		performCommunitySet(AppConst.NULL_INT, mVisitor.getCommunity());
	}

	/**
	 * Set with data from the user or the visitor
	 */
	private void performCommunitySet(int uid, CommunityBean... beans) {

		// set community spinner
		setSpinner(beans);

		// update data for current community
		updateCurrentCommunity(beans[0], uid);
	}

	/**
	 * Set the spinner view with communities
	 * 
	 * @param beans
	 */
	private void setSpinner(CommunityBean[] beans) {

		// update data
		mSpinnerAdapter.setData(beans);

		// set views
		if (mSpinnerView.getAdapter() == null)
			mSpinnerView.setAdapter(mSpinnerAdapter);
		else
			mSpinnerAdapter.notifyDataSetChanged();

		/** set mTitlebar text */
		// set Title image
		if (beans.length > 1) {
			if (!mTitleBar.isShowSpinner()) {
				mTitleBar.showSpinnerImage();
				mTitleBar.setTitleListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						toggleSpinner();
					}
				});
			}

			// re-set spinner height
			DisplayMetrics dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			int h = (int) (166 * dm.density);
			if (beans.length < 4) {
				h = h * beans.length / 4;
			}

			// height
			if (mSpinnerView.getLayoutParams().height != h)
				mSpinnerView.getLayoutParams().height = h;
		} else {
			mTitleBar.hideSpinnerImage();
		}

		// set title Name;
		String name = beans[0].getName();
		mTitleBar.setTitleName(name);

		// Send notify to right fragment for refrsh UI
		sendNotify(AppConst.APP_FACADE_RIGHT_HOME_FRAGMENT_USER_UI_REFRESH, name);

	}

	/**
	 * toggle spinner
	 */
	protected void toggleSpinner() {

		// change spinner image
		if (mSpinnerView.getVisibility() == View.VISIBLE) {
			((HomeActivity) getActivity())
					.removeMotionEventInterceptor(mSpinnerMotionEventInterceptor);
			((HomeActivity) getActivity()).removeOnBackPressCallBack(mSpinnerOnBackPressCallBack);
			mSpinnerView.setVisibility(View.INVISIBLE);
			ADAnimationUtils.raiseOut(mSpinnerView);
			mTitleBar.spinnerOn();
		} else {
			((HomeActivity) getActivity())
					.registMotionEventInterceptor(mSpinnerMotionEventInterceptor);
			((HomeActivity) getActivity()).registOnBackPressCallBack(mSpinnerOnBackPressCallBack);
			mSpinnerView.setVisibility(View.VISIBLE);
			ADAnimationUtils.dropIn(mSpinnerView);
			mTitleBar.spinnerOff();
		}
	}

	/**
	 * toggle selector
	 */
	private void toggleSelector() {
		toggleSelector(true);
	}

	/**
	 * toggle selector
	 */
	private void toggleSelector(boolean anim) {
		LogUtils.e(TAG,
				"toggleSelector   mSelectorView.isShowing()  :: " + mSelectorView.isShowing());
		if (mSelectorView.isShowing()) {
			((HomeActivity) getActivity())
					.removeMotionEventInterceptor(mSelectorMotionEventInterceptor);
			((HomeActivity) getActivity()).removeOnBackPressCallBack(mSelectorOnBackPressCallBack);
			mSelectorButton.setImageResource(R.drawable.ic_home_page_to_select);
			mSelectorView.hide(anim);
		} else {
			((HomeActivity) getActivity())
					.registMotionEventInterceptor(mSelectorMotionEventInterceptor);
			((HomeActivity) getActivity()).registOnBackPressCallBack(mSelectorOnBackPressCallBack);
			mSelectorView.show(anim);
			mSelectorButton.setImageResource(R.drawable.ic_home_page_left_down_button_checked);
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isShowSpinner() {
		return mSpinnerView.getVisibility() == View.VISIBLE;
	}

	/**
	 * Update current community include(property's info,community's time line)
	 */
	private void updateCurrentCommunity(CommunityBean bean, int uid) {
		LogUtils.v(TAG, "-----updateCurrentCommunity---");
		// set basic data , adapter , listView
		baseBuild();

		// request property data from remote-service
		requestPropertyData(bean, uid);
		// request community time-line from remote-service
		requestTimeLine(bean.getCid(), uid, false);

	}

	/**
	 * Set the set
	 */
	private void baseBuild() {
		LogUtils.v(TAG, "-----baseBuild---");
		// re-generate node lists
		mRealNodeList = new NodeList<MultiModelBase>();
		mHotNodeList = new NodeList2<MultiModelBase>();

		// re-generate request-type
		setRequestType(RSTrends.TYPE_TIME);

		/* re-generate mSet */
		mSet = new MultiModelsSet(SET_TYPE_COUNT, new JSONParser_HomePageTopDefault());
		// set mSet with default data
		mSet.add(JSONParser_HomePageTopDefault.NULL);
		// set lock-node
		mSet.lockNode();
		// set nodeList
		mSet.changeNodeList(mRealNodeList);
		// change to trends parser
		mSet.setJSONParser(new JSONParser_Trends());

		/* generate timeline adapter */
		mTimeLineAdapter = new MultiTypesAdapter(mSet, getActivity());

		/* set ListView */
		mListView.setAdapter(mTimeLineAdapter);

		// set func button visible
		// before 2.3 need set visible before anim
		mSelectorButton.setVisibility(View.VISIBLE);
		mEditorButton.setVisibility(View.VISIBLE);

		//
		ADAnimationUtils.fadeIn(mSelectorButton);
		ADAnimationUtils.fadeIn(mEditorButton);
	}

	/**
	 * Property remote-service
	 */
	private RSHomePageTop mPropertyRS;

	/**
	 * request current community's property info
	 * 
	 * @param bean
	 */
	private void requestPropertyData(CommunityBean bean, int uid) {
		LogUtils.v(TAG, "-----baseBuild---");
		if (mPropertyRS != null) {
			return;
		}

		mPropertyRS = new RSHomePageTop(bean.getCid(), uid);
		mPropertyRS.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				LogUtils.v(TAG, "-mPropertyRS ---onSuccess---");
				if (result == null) {
					return;
				}

				String json = result.toString();

				if (json.equals(""))
					return;

				try {
					// parse json
					JSONObject obj = new JSONObject(json);
					JSONObject community_info = obj.optJSONObject("community_info");
					JSONObject property_info = obj.optJSONObject("property_info");
					JSONObject weatherObj = obj.optJSONObject("weather");
					JSONObject advertising = obj.optJSONObject("advertising_info");
					// get exist model
					Model_HomePageTop m = (Model_HomePageTop) mSet.get(0);

					// reset model
					m.setBackground(community_info.optString("background"));
					m.setFollowType(obj.optInt("follow_type"));
					CommunityBean curCommunity = null;
					if (mUser != null) {
						curCommunity = mUser.getCurCommunity();
					} else {
						curCommunity = mVisitor.getCommunity();
					}

					if (property_info != null) {
						m.setProperty_id(property_info.optInt("id"));
						curCommunity.setProperty_id(property_info.optInt("id"));
						m.setProperty_name(property_info.optString("name"));
						curCommunity.setProperty_name(property_info.optString("name"));
					}
					m.setWeather(weatherObj);
					m.setAdvertising(advertising);

					// notify data changed
					mTimeLineAdapter.notifyDataSetChanged();

					// Send notify to right fragment for refrsh UI
					sendNotify(AppConst.APP_FACADE_RIGHT_HOME_FRAGMENT_USER_UI_REFRESH,
							curCommunity.getName());

					// loading finish
					mPropertyRS = null;

				} catch (JSONException e) {
					LogUtils.e(TAG, "", e);
				}
			}
		});
		mPropertyRS.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				// loading finish
				mPropertyRS = null;
				showToast("获取数据失败，请稍候重试");
				LogUtils.e(TAG, "", ex);
			}
		});

		mPropertyRS.asyncExecute(getActivity());
	}

	/**
	 * TimeLine request remote-service
	 */
	private RSTrends mRealTimeTrendsRS;
	private RSTrends mHotTrendsRS;

	/**
	 * request current community's time line data
	 */
	private void requestTimeLine(int cid, int uid, boolean anim) {
		LogUtils.e(TAG, "-requestTimeLine--------cid---：：-" + cid);
		if (isRequestingRealTime())
			requestRealTime(cid, uid, anim);
		else
			requestHotTime(cid, uid, anim);

	}

	/**
	 * 
	 * @param cid
	 * @param uid
	 * @param anim
	 */
	private void requestRealTime(int cid, int uid, final boolean anim) {

		if (mRealTimeTrendsRS != null) {
			return;
		}

		if (mSet.isMax()) {
			stopPullRefreshAnimation();
			ToastAlone.makeText(getActivity(), mSet.size() <= 1 ? "暂无数据" : "数据已全加载完毕",
					Toast.LENGTH_LONG).show();
			return;
		}

		mRealTimeTrendsRS = new RSTrends(cid, mSet.getNextPageStart(), mSet.getPageSize(),
				getRequestType(), uid, mSelectorView.getUserArg(), mSelectorView.getVIPArg(),
				mSelectorView.getFollowArg(), mSelectorView.getTag());

		mRealTimeTrendsRS.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				LogUtils.v(TAG, "--onSuccess---");
				if (mListView.isRefreshing()) {
					stopPullRefreshAnimation();
				}

				if (anim) {
					mHomeTopView.dismissLoading();
				}

				//
				mRealTimeTrendsRS = null;

				if (result == null) {
					return;
				}
				String json = result.toString();
				LogUtils.v(TAG, "--onSuccess---json  :: " + json);

				// top model
				Model_HomePageTop m = (Model_HomePageTop) mSet.get(0);
				int t_count = 0;

				//
				if (mSet.add(json)) {
					t_count = mSet.getPageCounterTotalValue();

					// re-set topic count
					m.setTopicCount(t_count);

					// add hot nodelist
					if (mRealNodeList.size() == 0) {
						((Model_TrendsBase) (mSet.getTempList().get(0))).setHideDividLine(true);
					}
					mRealNodeList.addAll(mSet.getTempList());

					// refresh UI
					mTimeLineAdapter.notifyDataSetChanged();
				} else {
					// json没有数据的时候
					// refresh UI
					mRealNodeList.clear();
					m.setTopicCount(0);
					mTimeLineAdapter.notifyDataSetChanged();
					// 如果没有相关数据的话 给个提示语、
					ToastAlone.makeText(getActivity(), "暂无数据", Toast.LENGTH_LONG).show();
				}
				mSelectorButton.setClickable(true);

			}
		});

		mRealTimeTrendsRS.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				LogUtils.e(TAG, "", ex);

				if (mListView.isRefreshing()) {
					stopPullRefreshAnimation();
				}

				if (anim) {
					mHomeTopView.dismissLoading();
				}
				showToast("获取数据失败，请稍候重试");
				mSelectorButton.setClickable(true);
				mRealTimeTrendsRS = null;
			}
		});

		mRealTimeTrendsRS.asyncExecute(getActivity());

		if (anim) {
			mHomeTopView.showLoading();
		}
	}

	/**
	 * 
	 * @param cid
	 * @param uid
	 * @param anim
	 */
	private void requestHotTime(int cid, int uid, final boolean anim) {

		if (mHotTrendsRS != null)
			return;

		if (mHotNodeList.isMax()) {
			stopPullRefreshAnimation();
			ToastAlone.makeText(getActivity(), mSet.size() <= 1 ? "暂无数据" : "数据已全加载完毕",
					Toast.LENGTH_LONG).show();
			return;
		}

		mHotTrendsRS = new RSTrends(cid, mHotNodeList.getSize(), getRequestType(), uid,
				mSelectorView.getUserArg(), mSelectorView.getVIPArg(),
				mSelectorView.getFollowArg(), mSelectorView.getMerchantCate(),
				mHotNodeList.getNextStartPage());

		mHotTrendsRS.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {

				if (mListView.isRefreshing()) {
					stopPullRefreshAnimation();
				}

				if (anim) {
					mHomeTopView.dismissLoading();
				}

				//
				mHotTrendsRS = null;

				if (result == null)
					return;

				String json = result.toString();

				try {
					JSONObject obj = new JSONObject(json);
					int curPage = obj.optInt("curPage");
					int totalPage = obj.optInt("totalPage");
					mHotNodeList.setTotalPage(totalPage);
					mHotNodeList.setCurPage(curPage);
				} catch (JSONException e) {
					LogUtils.e(TAG, "", e);
					// 通知刷新ui
					showToast("获取数据失败，请稍候重试");
					return;
				}

				// top model
				Model_HomePageTop m = (Model_HomePageTop) mSet.get(0);
				int t_count = 0;

				// handle event
				if (mSet.add(json)) {

					t_count = mSet.getPageCounterTotalValue();

					// re-set topic count
					m.setTopicCount(t_count);

					// add hot nodelist
					if (mHotNodeList.size() == 0) {
						((Model_TrendsBase) (mSet.getTempList().get(0))).setHideDividLine(true);
					}

					mHotNodeList.addAll(mSet.getTempList());

					// refresh UI
					mTimeLineAdapter.notifyDataSetChanged();

				} else {
					mHotNodeList.clear();
					// refresh UI
					m.setTopicCount(0);// 设置信息数为0
					mTimeLineAdapter.notifyDataSetChanged();
					ToastAlone.makeText(getActivity(), "暂无数据", Toast.LENGTH_LONG).show();
				}
				mSelectorButton.setClickable(true);
			}
		});

		mHotTrendsRS.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {

				if (mListView.isRefreshing()) {
					stopPullRefreshAnimation();
				}

				if (anim) {
					mHomeTopView.dismissLoading();
				}

				mHotTrendsRS = null;
				mSelectorButton.setClickable(true);
				showToast("获取数据失败，请稍候重试");
				LogUtils.e(TAG, "", ex);
			}
		});

		mHotTrendsRS.asyncExecute(getActivity());

		if (anim) {
			mHomeTopView.showLoading();
		}

	}

	/**
	 * To update current user's info from remote-service
	 */
	private void updateUserInfo() {

		// show loading dialog
		showProgressDialog("正在加载用户信息", false);

		// get act
		final HomeActivity act = (HomeActivity) getActivity();

		// update...
		((AppContext) (act.getApplicationContext())).updateUserInfo(act.getHelper(),
				new OnSuccessHandler() {

					@Override
					public void onSuccess(Object result) {

						try {
							// dismiss loading dialog
							hideProgressDialog();

							/*
							 * set SlideMenu slide enabled when current userinfo
							 * successfully updated.
							 */
							((HomeActivity) getActivity()).enableSlide();

							// notify leftFragment refresh user UI
							sendNotify(AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH, null);
							// AppContext.msgHandleLoop(getActivity());
							getActivity().startService(
									new Intent(getActivity(), LifeCircleService.class));

							/** SET */
							// set current community with user
							CommunityBean[] beans = mUser.getSortCommunityArray();

							/** SET_NORMAL */
							if (beans != null && beans.length > 0) {
								// start normal set
								performCommunitySet(mUser.getUid(), beans);
							} else {
								/** SET_ERROR */
								cautionWithNoEntryErro();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}, new OnFaultHandler() {

					@Override
					public void onFault(Exception ex) {

						// log
						LogUtils.e(TAG, "", ex);

						// dismiss loading dialog
						hideProgressDialog();

						/*
						 * set SlideMenu slide disabled when current userinfo
						 * update failed.
						 */
						((HomeActivity) getActivity()).disableSlide();

						// hanlde error with Exception
						if (ex instanceof ADNetworkNotAvailableException)
							mCaution.error();
						else
							mCaution.fail();

					}

				});
	}

	/**
	 * show the Error Caution with No Community Bug
	 */
	private void cautionWithNoEntryErro() {
		// show caution
		mCaution.error();

		// set logoff , need to relogin
		mCaution.setOnErrorClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					AppContext.logOff((HomeActivity) getActivity());
				} catch (SQLException e) {
					LogUtils.e(TAG, "", e);
				}
			}
		});

		// show toast with error msg
		showToast("错误：尚未加入任何小区，请点击空白处，重新登录！");
	}

	/**
	 * Send notifications
	 * 
	 * @param notify
	 */
	protected void sendNotify(String name, Object body) {
		// send notify
		if (name != null)
			ApplicationFacade.getInstance().sendNotification(name, body);
		// LogUtils.e(TAG, "sendNotify.....name: "+name);
	}

	/**
	 * Adapter for Spinner View
	 * 
	 * @author dan_alan
	 * 
	 */
	private class SpinnerAdapter extends BaseAdapter {

		private CommunityBean[] beans;

		public void setData(CommunityBean[] beans) {
			this.beans = beans;
		}

		@Override
		public int getCount() {
			return beans.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int p, View v, ViewGroup arg2) {

			SpinnerViewHolder holder = null;

			if (v == null) {

				// view
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_home_page_spinner_text, null);

				// holder
				holder = new SpinnerViewHolder();
				holder.txt = (TextView) v.findViewById(R.id.text);
				holder.view = v.findViewById(R.id.view);
				//
				v.setTag(holder);

			} else {
				holder = (SpinnerViewHolder) v.getTag();
			}

			holder.txt.setText(beans[p].getName());
			holder.view.setVisibility(p == 0 ? View.VISIBLE : View.GONE);
			v.setBackgroundColor(p == 0 ? 0xffE7E5E2 : 0xfffaf8f6);
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// reset current community
					mUser.setCurrentCommunity(beans[p]);
					// toggle
					toggleSpinner();

					// re-set Spinner
					setSpinner(mUser.getSortCommunityArray());

					// update community with canceling requesting services
					if (p > 0) {

						// refresh data
						if (mListView.isRefreshing()) {
							if (mPropertyRS != null) {
								mPropertyRS.cancel();
								mPropertyRS = null;
							}
							if (mRealTimeTrendsRS != null) {
								mRealTimeTrendsRS.cancel();
								mRealTimeTrendsRS = null;
							}
							if (mHotTrendsRS != null) {
								mHotTrendsRS.cancel();
								mHotTrendsRS = null;
							}

							updateCurrentCommunity(getCurrentCommunityBean(), getUid());
						} else {
							mListView.setRefreshing();
						}
					}

				}
			});

			return v;
		}

	}

	/**
	 * ViewHolder for SpinnerAdapter
	 * 
	 * @author dan_alan
	 * 
	 */
	private static class SpinnerViewHolder {
		View view;
		TextView txt;
	}

	/** ------------------ BOOLEAN ------------------ */

	/**
	 * mEntireRefresh
	 * 
	 * @return
	 */
	private boolean entireRefreshEnabled() {
		return mEntireRefresh;
	}

	/**
	 * 
	 */
	private void disableEntireRefresh() {
		mEntireRefresh = false;
	}

	/**
	 * 
	 */
	private void enableEntireRefresh() {
		mEntireRefresh = true;
	}

	/**
	 * 
	 */
	private MotionEventInterceptor mSpinnerMotionEventInterceptor = new MotionEventInterceptor() {

		@Override
		public boolean onIntercept(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				LogUtils.v(TAG, "mSpinnerMotionEventInterceptor");
				if (!ADInteractUtils.isActionDownOnView(event, mSpinnerView)) {
					toggleSpinner();
					return false;
				}
			}
			return true;
		}
	};

	private OnBackPressCallBack mSpinnerOnBackPressCallBack = new OnBackPressCallBack() {

		@Override
		public void callback() {
			toggleSpinner();
		}
	};

	/**
	 * 
	 */
	private MotionEventInterceptor mSelectorMotionEventInterceptor = new MotionEventInterceptor() {

		@Override
		public boolean onIntercept(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				LogUtils.v(TAG, "mSelectorMotionEventInterceptor");
				if (!ADInteractUtils.isActionDownOnView(event, mSelectorButton, mSelectorView)) {
					((HomeActivity) getActivity())
							.removeMotionEventInterceptor(mSelectorMotionEventInterceptor);
					((HomeActivity) getActivity())
							.removeOnBackPressCallBack(mSelectorOnBackPressCallBack);
					mSelectorButton.setImageResource(R.drawable.ic_home_page_to_select);
					mSelectorView.hide(true);
					return true;
				}
			}/*
			 * else if (event.getAction() == MotionEvent.ACTION_UP) {
			 * LogUtils.i(TAG, "mSelectorMotionEventInterceptor     up");
			 * hideselect = true; hideSelectorView(); return true; }
			 */
			return true;
		}
	};
	// private boolean hideselect = false;
	/**
	 * selector onbackpress callback
	 */
	private OnBackPressCallBack mSelectorOnBackPressCallBack = new OnBackPressCallBack() {

		@Override
		public void callback() {
			toggleSelector();
		}
	};

	public void hideSelectorView() {

		toggleSelector(false);
		refreshTimeLine();
		// if (mSelectorView.isShowing()) {
		// toggleSelector(false);
		// refreshTimeLine();
		// // 刷新
		// // mSet.refreshImmediatelyWithAdapterNotify(mTimeLineAdapter);
		// mListView.getRefreshableView().setSelection(0);
		// } else
		// toggleSelector();

	}

	/**
	 * setListeners
	 */
	private void setListeners() {
		/**
		 * To filter timeline
		 */
		mSelectorButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LogUtils.i(TAG, "mSelectorButton --- " + mSelectorView.isShowing());

				if (mSelectorView.isShowing()) {
					mSelectorButton.setImageResource(R.drawable.ic_home_page_to_select);
					mSelectorView.hide(true);
				} else {
					MobclickAgent.onEvent(getActivity(), PublicUtils.MOBCLICKAGENT_FILTER);
					toggleSelector();
				}
			}
		});
		/**
		 * To edit and send message
		 */
		mEditorButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!AppContext.isValid(getActivity()))
					return;

				dialog = new ProgressDialog(getActivity());
				dialog.show();
				Intent intent = new Intent(getActivity(), SendMsgMainActivity.class);
				getActivity().startActivity(intent);

			}
		});

		/**
		 * 
		 */
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				LogUtils.v(TAG, "--mListView---onPullDownToRefresh---");
				// if just anim , return
				if (mPullRefreshAnimOnly) {
					return;
				}

				// if requesting ,prevent updating
				if (mHotTrendsRS != null || mRealTimeTrendsRS != null || mPropertyRS != null) {
					stopPullRefreshAnimation();
					return;
				}

				// update
				updateCurrentCommunity(getCurrentCommunityBean(), getUid());
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				LogUtils.v(TAG, "--mListView---onPullUpToRefresh---");
				requestTimeLine(getCid(), getUid(), false);
			}
		});

		/**
		 * set onScoll Listener
		 */

		mListView.getRefreshableView()
				.setOnScrollListener(
						new PauseOnScrollListener(ImageLoader.getInstance(getActivity()), false,
								true, null));
	}

	/** ---------------- Mediator ---------------- */
	private HomeFragmentMediator mMediator = null;

	public void removeMediator() {
		ApplicationFacade.getInstance().removeMediator(HomeFragmentMediator.NAME);
	}

	private void registMediator() {
		if (mMediator != null)
			return;
		mMediator = new HomeFragmentMediator();
		ApplicationFacade.getInstance().registerMediator(mMediator);
	}

	private class HomeFragmentMediator extends Mediator {
		public static final String NAME = "HomeFragmentMediator";

		public HomeFragmentMediator() {
			this(NAME, null);
		}

		public HomeFragmentMediator(String mediatorName, Object viewComponent) {
			super(NAME, viewComponent);
		}

		@Override
		public void handleNotification(INotification n) {
			String name = n.getName();
			Object o = n.getBody();
			if (name.equals(AppConst.APP_FACADE_HOME_FRAGMENT_SWITCH_HOT_AND_CURRENT_MSG)) {
				Boolean currentClick = (Boolean) o;
				if (currentClick == true) {
					switchToRealTime();
				} else {
					switchToHotTime();
				}
			}

			if (name.equals(AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH)) {
				enableEntireRefresh();
			}

			if (name.equals(AppConst.APP_FACADE_HOME_PAGE_TOP_VIEW_DIDLOAD)) {
				mHomeTopView = (Item_HomePageTop) o;
			}

			if (name.equals(AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE)) {
				if (LifeCircleService.getIsNewMessage()) {
					mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_top_bar_left_button);
				} else {
					mTitleBar.initLeftButtonTextOrResId(0,
							R.drawable.selector_home_page_top_bar_left_button);
				}
			}

			if (name.equals(AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH)) {
				if (mHomeTopView != null && mHomeTopView.getmImgHeader() != null) {
					setUserHead(AppContext.curUser(), mHomeTopView.getmImgHeader());
				}
			}

		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { AppConst.APP_FACADE_HOME_FRAGMENT_SWITCH_HOT_AND_CURRENT_MSG,
					AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH,
					AppConst.APP_FACADE_HOME_PAGE_TOP_VIEW_DIDLOAD,
					AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE,
					AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH };
		}

	}

	// 设置头像
	private void setUserHead(UserBean userBean, ImageView mImgHeader) {
		if (!TextUtils.isEmpty(userBean.getHeaderUrl()) && !userBean.getHeaderUrl().equals("null")) {
			ImageLoader.getInstance(getActivity()).displayImage(
					userBean.getHeaderUrl(),
					mImgHeader,
					SimpleImageLoaderOptions.getRoundImageOptions(
							PublicUtils.getUserIndexDefaultHeadImage(userBean.getType()), 100),
					new FadeInImageLoadingListener(mImgHeader));
		} else {
			mImgHeader
					.setImageResource(PublicUtils.getUserIndexDefaultHeadImage(userBean.getType()));
		}
	}

	/**
	 * switch to current time-line
	 */
	private void switchToRealTime() {

		// check if hot is requesting
		if (mHotTrendsRS != null) {
			mHotTrendsRS.cancel();
			mHotTrendsRS = null;
			dimissTopViewLoading();
			if (mListView.isRefreshing()) {
				stopPullRefreshAnimation();
			}
		}

		// do change
		setRequestType(RSTrends.TYPE_TIME);
		mSet.changeNodeList(mRealNodeList);
		mTimeLineAdapter.notifyDataSetChanged();
		if (mRealNodeList.size() == 0) {
			requestRealTime(getCid(), getUid(), true);
		}
	}

	/**
	 * switch to hot time-line
	 */
	private void switchToHotTime() {

		// check if realTime is requesting
		if (mRealTimeTrendsRS != null) {
			mRealTimeTrendsRS.cancel();
			mRealTimeTrendsRS = null;
			dimissTopViewLoading();
			if (mListView.isRefreshing()) {
				stopPullRefreshAnimation();
			}
		}

		// do change
		setRequestType(RSTrends.TYPE_HOT);
		mSet.changeNodeList(mHotNodeList);
		mTimeLineAdapter.notifyDataSetChanged();
		if (mHotNodeList.size() == 0) {
			requestHotTime(getCid(), getUid(), true);
		}
	}

	/**
	 * 
	 */
	private void dimissTopViewLoading() {
		if (mHomeTopView != null)
			mHomeTopView.dismissLoading();
	}

	/**
	 * 
	 * @return
	 */
	private int getUid() {
		if (mUser != null)
			return mUser.getUid();
		return AppConst.NULL_INT;
	}

	/**
	 * 
	 * @return
	 */
	private CommunityBean getCurrentCommunityBean() {
		if (mUser != null)
			return mUser.getCurCommunity();
		else
			return mVisitor.getCommunity();
	}

	/**
	 * 
	 * @return
	 */
	private CommunityBean[] getCommunities() {
		if (mUser != null)
			return mUser.getSortCommunityArray();
		else
			return new CommunityBean[] { mVisitor.getCommunity() };
	}

	/**
	 * 
	 * @return
	 */
	private int getCid() {
		if (mUser != null)
			return mUser.getCurrentCommunityId();
		else
			return mVisitor.getCommunity_id();
	}

	/**
	 * requst type
	 */
	private int requestType = RSTrends.TYPE_TIME;

	/**
	 * 
	 * @return
	 */
	private boolean isRequestingRealTime() {
		return this.requestType == RSTrends.TYPE_TIME;
	}

	/**
	 * 
	 * @return
	 */
	private int getRequestType() {
		return this.requestType;
	}

	/**
	 * 
	 * @param type
	 */
	private void setRequestType(int type) {
		this.requestType = type;
	}

	/**
	 * ADHandler
	 */
	private ADHandler<HomeFragment> mHandler = new ADHandler<HomeFragment>(this,
			new MessageHandler<HomeFragment>() {

				@Override
				public void handleMessage(HomeFragment f, Message msg) {
					if (msg.what == MSG_SET_REFRESH_COMPLETE) {
						f.forceRefreshListViewComplete();
					}
				}
			});

	/**
	 * 
	 */
	public void forceRefreshListViewComplete() {
		mListView.forceRefreshComplete();
	}

	/**
	 * 
	 */
	private void stopPullRefreshAnimation() {
		mHandler.sendEmptyMessageDelayed(MSG_SET_REFRESH_COMPLETE, 100);
	}

	private boolean mPullRefreshAnimOnly;

	/**
	 * 
	 */
	private void startPullRefreshAnimation() {
		mPullRefreshAnimOnly = true;
		mListView.setRefreshing();
		mPullRefreshAnimOnly = false;
	}

	/**
	 * 
	 */
	private void prepareToRefreshTimeLine() {
		mSet.setRefresh();
		mHotNodeList.clear();
		mRealNodeList.clear();
	}

	/**
	 * 
	 */
	private void refreshTimeLine() {
		prepareToRefreshTimeLine();
		requestTimeLine(getCid(), getUid(), true);
	}

	/**
	 * 
	 * @author dan_alan
	 * 
	 * @param <E>
	 */
	public static class NodeList2<E> extends LinkedList<E> {

		private static final long serialVersionUID = 3926982381817522124L;

		private LCPageCounter2 mPageCounter;

		public NodeList2() {
			mPageCounter = new LCPageCounter2(10);
		}

		public int getSize() {
			return mPageCounter.getSize();
		}

		public int getNextStartPage() {
			return mPageCounter.getNextStartPage();
		}

		public boolean isMax() {
			return mPageCounter.isMax();
		}

		public void setTotalPage(int t_page) {
			mPageCounter.setTotalPage(t_page);
		}

		public void setCurPage(int c_page) {
			mPageCounter.setCurPage(c_page);
		}

		public void resetPageCounter() {
			mPageCounter.reset();
		}

		@Override
		public void clear() {
			super.clear();
			mPageCounter.reset();
		}
	}

	@Override
	public void onBack() {
		// TODO Auto-generated method stub
		mSelectorButton.setClickable(false);
		hideSelectorView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		BitmapDrawable bdOne = (BitmapDrawable) mBaseImageView.getBackground();
//		// 别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
//		mBaseImageView.setBackgroundResource(0);
//		if (bdOne != null) {
//			bdOne.setCallback(null);
//			bdOne.getBitmap().recycle();
//		}
		super.onDestroy();
	}

	private void showFuDongView() {
		rl_home_frame.setVisibility(View.VISIBLE);
		rl_home_frame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_home_frame.setVisibility(View.GONE);
			}
		});
//		mBaseImageView.setVisibility(View.VISIBLE);
//		Bitmap bm = BitmapFactory.decodeResource(this.getResources(), imageId);
//		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
//		mBaseImageView.setBackgroundDrawable(bd);
//		mBaseImageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mBaseImageView.setVisibility(View.GONE);
//			}
//		});
	}
}

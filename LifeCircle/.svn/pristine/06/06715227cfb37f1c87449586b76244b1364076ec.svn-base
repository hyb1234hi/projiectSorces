package com.sinaleju.lifecircle.app.activity;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView.OnPositionChangedListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.LCScrollBarPanel;
import com.sinaleju.lifecircle.app.customviews.bottommenu.BaseBottomMenu;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_Trends;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSOfficHome;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSPersonalIndexHeader;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class OfficHomeActivity extends BaseActivity {

	private static final String TAG = "OfficHomeActivity";

	// private TitleBar my_TitleBar = null;

	private PullToRefreshListView mPerHomeList = null;

	private RSPersonalIndexHeader mHeaderRs = null;
	private MultiModelsSet mSet = null;
	private LCScrollBarPanel mScrollBar;

	// private ExecutorService executor = Executors.newSingleThreadExecutor();

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_offic_home;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mPerHomeList = (PullToRefreshListView) findViewById(R.id.personal_home_list);
		mBottomMenu = (BaseBottomMenu) findViewById(R.id.mBottomMenu);
		initListView();
		setListener();
		requestPersonalHeader();
	}

	private void initListView() {
		mScrollBar = new LCScrollBarPanel(mContext);
		mScrollBar.setLayoutParams(new AbsListView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		((ExtendedListView) mPerHomeList.getRefreshableView())
				.setScrollBarPanel(mScrollBar);
		((ExtendedListView) mPerHomeList.getRefreshableView())
				.setOnPositionChangedListener(new OnPositionChangedListener() {

					@Override
					public void onPositionChanged(ExtendedListView listView,
							int position, View scrollBarPanel) {
						if (mAdapter != null) {
							Object o = mAdapter.getItem(position);
							if (o != null && o instanceof Model_TrendsBase) {
								Model_TrendsBase m = (Model_TrendsBase) o;
								long time = m.getAddTime();
								mScrollBar.updateTime(time);
							}
						}
					}
				});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("官方主页");
		mTitleBar.setOnClickListener(null);

	}

	private void requestPersonalHeader() {
		requestUserTimeLine();
	}

	private RSOfficHome mUserRs = null;
	private MultiTypesAdapter mAdapter = null;
	private int curPageIndex = 1;
	private int maxPage = 0;
	private static final int ON_REFRESHING = 1;
	private static final int ON_MORE = 2;
	private static final int ON_DIDLOAD = 0;
	private int pullState = ON_DIDLOAD;

	private void requestUserTimeLine() {

		if (maxPage > 0 && curPageIndex > maxPage) {
			setRefreshComplete();
			showToast("数据已经全部加载完毕");
			return;
		}

		if (mUserRs == null) {
			mUserRs = new RSOfficHome(-1, curPageIndex, 10);
			mUserRs.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					hideProgressDialog();
					if (result == null) {
						mUserRs = null;
						mHeaderRs = null;
						showToast("网络不可用");
						return;
					}
					JSONObject obj = null;
					try {
						obj = new JSONObject(result.toString());
						maxPage = obj.getInt("totalPage");
					} catch (JSONException e) {
						mUserRs = null;
						mHeaderRs = null;
						e.printStackTrace();
					}

					switch (pullState) {
					case ON_DIDLOAD:
						mSet = new MultiModelsSet(new JSONParser_Trends(false,
								0));
						mAdapter = new MultiTypesAdapter(mSet, mContext);
						mPerHomeList.setAdapter(mAdapter);
						// mSet.setJSONParser(new JSONParser_Trends(false, 0));
						if (mSet.add(result.toString())) {
							if (mAdapter != null) {
								mAdapter.notifyDataSetChanged();
							}
							curPageIndex++;
						}
						break;
					case ON_REFRESHING:
						// mSet.setJSONParser(new JSONParser_Trends(false, 0));
						mSet = new MultiModelsSet(new JSONParser_Trends(false,
								0));
						if (mSet.add(result.toString())) {
							if (mAdapter != null) {
								mAdapter.notifyDataSetChanged();
							}
							curPageIndex++;
						}
						break;
					case ON_MORE:
						if (mSet.add(result.toString())) {
							if (mAdapter != null) {
								mAdapter.notifyDataSetChanged();
							}
							curPageIndex++;
						}
						break;
					}

					setRefreshComplete();
					mUserRs = null;
					mHeaderRs = null;
				}

			});
			mUserRs.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					setRefreshComplete();
					if (ex instanceof ADRemoteException) {
						showToast(((ADRemoteException) ex).msg());
					} else if (ex instanceof ConnectTimeoutException) {
						showToast("请求超时，请重试");
					}
					mUserRs = null;
					mHeaderRs = null;
				}
			});
			if (!PublicUtils.isNetworkAvailable(mContext)) {
				showToast("网络异常，请重试");
			} else {
				showProgressDialog("数据获取中...", true);
				mUserRs.asyncExecute(mContext, this);
			}
		}
	}

	protected void setListener() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mPerHomeList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				refreshUserData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (mHeaderRs == null && mUserRs == null) {
					pullState = ON_MORE;
				}
				requestUserTimeLine();
			}
		});
	}

	private static final int SET_REFRESH_COMPLETE = 101;
	private static final int MSG_SHOW_TOAST_SUCCESS = 110;
	private static final int MSG_SHOW_TOAST_FAILED = 120;
	private ADHandler<OfficHomeActivity> mHandler = new ADHandler<OfficHomeActivity>(
			this, new MessageHandler<OfficHomeActivity>() {

				@Override
				public void handleMessage(OfficHomeActivity f, Message msg) {
					if (msg.what == SET_REFRESH_COMPLETE) {
						f.forceRefreshListViewComplete();
					} else if (msg.what == MSG_SHOW_TOAST_SUCCESS) {
						f.showToast(msg.obj.toString());
						f.refreshUserData();
					} else if (msg.what == MSG_SHOW_TOAST_FAILED) {
						f.showToast(msg.obj.toString());
						// setUpdateHeadImageFail();
					}
				}
			});

	private void setRefreshComplete() {
		mHandler.sendEmptyMessageDelayed(SET_REFRESH_COMPLETE, 100);
	}

	public void forceRefreshListViewComplete() {
		mPerHomeList.forceRefreshComplete();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void refreshUserData() {
		curPageIndex = 1;
		maxPage = 0;
		pullState = ON_REFRESHING;
		requestPersonalHeader();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void initCallbacks() {
		mPerHomeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				return;

			}
		});

	}
}

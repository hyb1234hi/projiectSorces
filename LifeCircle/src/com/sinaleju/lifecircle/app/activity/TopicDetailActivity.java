package com.sinaleju.lifecircle.app.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView.OnPositionChangedListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.LCScrollBarPanel;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.customviews.bottommenu.BaseBottomMenu;
import com.sinaleju.lifecircle.app.model.Model_TopicDetail;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_TopicDetailTop;
import com.sinaleju.lifecircle.app.model.json.JSONParser_Trends;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSTopicDetail;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class TopicDetailActivity extends BaseActivity {

	private static final int MSG_REFRESH_COMPLETE = 10;
	private static final int MSG_REFRESH_DELAY = 20;
	protected static final String TAG = "TopicDetailActivity";
	private PullToRefreshListView mListView;
	private TextView mEmptyView;
	private int mTopicId;
	private TitleBar mSelfTitleBar;
	private LCScrollBarPanel mScrollBar;

	@Override
	protected int getLayoutId() {
		return R.layout.ac_topic_detail;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		initFromIntent();
		initTitleBar();
		initActionSheet();
		initListView();
	}

	@Override
	protected void initData() {
		refreshListViewDelay();
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub

	}

	private void initFromIntent() {
		mTopicId = getIntent().getIntExtra(AppConst.INTENT_TOPIC_DETAIL_ID, -1);
	}

	private void initTitleBar() {
		mTitleBar.setVisibility(View.GONE);
		mSelfTitleBar = (TitleBar) findViewById(R.id.self_title_bar);
		mSelfTitleBar.setTitleName("话 题");
		mSelfTitleBar.setBackgroundResource(R.drawable.topbar_bg_transparent);
		mSelfTitleBar.initRightButtonTextOrResId(0, R.drawable.selector_top_bar_more);
		mSelfTitleBar.showBackButton();
		mSelfTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mSelfTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showBottomMenu();
			}
		});
	}

	private void initActionSheet() {
		initBottomMenu(BaseBottomMenu.TYPE_BG_RED + "回到首页");
		setBottomMenuButtonListener(0, new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissBottomMenu();
				Intent intent = new Intent(mContext, HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void initListView() {
		mListView = (PullToRefreshListView) findViewById(R.id.list);
		mEmptyView = (TextView) findViewById(R.id.emptyView);
		mEmptyView.setText("");
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				refreshData();
				mEmptyView.setText("");
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				request();
				mEmptyView.setText("");
			}
		});

		mScrollBar = new LCScrollBarPanel(this);
		mScrollBar.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		((ExtendedListView) mListView.getRefreshableView()).setScrollBarPanel(mScrollBar);
		((ExtendedListView) mListView.getRefreshableView())
				.setOnPositionChangedListener(new OnPositionChangedListener() {

					@Override
					public void onPositionChanged(ExtendedListView listView, int position,
							View scrollBarPanel) {
						if (mAdapter != null) {
							if (position > 0) {
								Object o = mAdapter.getItem(position - 1);
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

	private void refreshListViewDelay() {
		mHandler.sendEmptyMessageDelayed(MSG_REFRESH_DELAY, 200);
	}

	private void setListViewRefresh() {
		mListView.setRefreshing();
	}

	private void setListViewRefreshComplete() {
		mHandler.sendEmptyMessageDelayed(MSG_REFRESH_COMPLETE, 200);
	}

	public void forceCompleteRefresh() {
		mListView.forceRefreshComplete();
	}

	private ADHandler<TopicDetailActivity> mHandler = new ADHandler<TopicDetailActivity>(this,
			new MessageHandler<TopicDetailActivity>() {

				@Override
				public void handleMessage(TopicDetailActivity o, Message msg) {
					if (msg.what == MSG_REFRESH_COMPLETE) {
						o.forceCompleteRefresh();
					} else if (msg.what == MSG_REFRESH_DELAY) {
						o.setListViewRefresh();
					}
				}
			});

	private RSTopicDetail rs = null;
	private MultiModelsSet set = null;
	private MultiTypesAdapter mAdapter;

	private void request() {
		if (mTopicId == -1) {
			return;
		}
		if (rs != null) {
			return;
		}

		if (set != null && set.isMax()) {
			setListViewRefreshComplete();
			showToast("数据已经全部加载完毕");
			return;
		}

		rs = new RSTopicDetail(mTopicId, set == null ? StringRS.NULL_INT : set.getPageSize(),
				set == null ? StringRS.NULL_INT : set.getNextPageStart(),
				AppContext.curUser() != null ? AppContext.curUser().getUid() : StringRS.NULL_INT);
		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				setListViewRefreshComplete();
				// 第一次加载
				if (set == null) {
					set = new MultiModelsSet(3, new JSONParser_TopicDetailTop());
					set.add(MultiModelsSet.NULL_JSON);
					set.lockNode();
					set.setJSONParser(new JSONParser_Trends());
					mAdapter = new MultiTypesAdapter(set, mContext);
					mListView.setAdapter(mAdapter);
				}

				// 之后的刷新
				String json = result.toString();
				Model_TopicDetail m = (Model_TopicDetail) set.get(0);
				if (m != null) {
					JSONObject o = null;
					try {
						o = new JSONObject(json);
						JSONObject topic = o.getJSONObject("topic");
						m.setAmount(topic.optInt("count"));
						String name = topic.optString("name");
						if (name != null && !name.equals("")) {
							m.setName("#" + name + "#");
						}
						m.setTopic_id(topic.optInt("id"));
					} catch (JSONException e) {
						LogUtils.e(TAG, "", e);
					}

				}
				set.add(json);
				mAdapter.notifyDataSetChanged();

				rs = null;
				mEmptyView.setText("");
			}

		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				LogUtils.e(TAG, "", ex);
				rs = null;
				setListViewRefreshComplete();
				mListView.setEmptyView(mEmptyView);
				mEmptyView.setText("网络连接异常");
			}
		});
		rs.asyncExecute(this);
	}

	private void refreshData() {
		set = null;
		request();
	}
}

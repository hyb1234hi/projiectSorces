package com.sinaleju.lifecircle.app.activity;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppCache;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.bottommenu.BaseBottomMenu;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsDetailName;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailComment;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailDeliver;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailName;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsgDeliver;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet.NodeList;
import com.sinaleju.lifecircle.app.model.json.JSONParser_TrendsDetailComment;
import com.sinaleju.lifecircle.app.model.json.JSONParser_TrendsDetailContent;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSDeleteMsg;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSTrendsDetailComment;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSTrendsDetailContent;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSTrendsDetailDeliver;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class TrendsDetailActivity extends BaseActivity {
	protected static final String TAG = "TrendsDetailActivity";
	public static final int SET_REFRESH_COMPLETE = 10;
	/** 评论 */
	public static final int INTENT_REQUEST_CODE_COMMENT = 1;
	/** 转发 */
	public static final int INTENT_REQUEST_CODE_DELIVER = 2;
	public static final int MSG_ANIM_DELIVERED_INDICATOR = 3;
	private PullToRefreshListView mListView = null;
	private View mRefreshButton = null;
	private View mCommentButton = null;
	private View mDeliverButton = null;
	private View mMoreButton = null;
	private Item_TrendsMsg mContentItem;
	private Item_TrendsDetailName mNameItem;
	private NodeList<MultiModelBase> mCommentList = new NodeList<MultiModelBase>();
	private NodeList<MultiModelBase> mDeliverList = new NodeList<MultiModelBase>();
	private Model_TrendsMsg mModel;

	@Override
	protected int getLayoutId() {
		return R.layout.ac_trends_detail;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		if (initFromIntent()) {

			mListView = (PullToRefreshListView) findViewById(R.id.list);

			// bottom bar
			mRefreshButton = findViewById(R.id.imgbutton_trends_detail_bottom_bar_refresh);
			mCommentButton = findViewById(R.id.imgbutton_trends_detail_bottom_bar_comment);
			mDeliverButton = findViewById(R.id.imgbutton_trends_detail_bottom_bar_devliver);
			mMoreButton = findViewById(R.id.imgbutton_trends_detail_bottom_bar_more);

			setTitleLayout();
			setBottomBar();

			set();
		}

	}

	private void set() {
		mSet = new MultiModelsSet(4, new JSONParser_TrendsDetailContent(mModel));
		mSet.add(JSONParser_TrendsDetailContent.NULL);
		mSet.lockNode();

		mAdapter = new MultiTypesAdapter(mSet, mContext);
		mListView.setAdapter(mAdapter);

		mSet.setJSONParser(new JSONParser_TrendsDetailComment());

		requestList();
	}

	/**
	 * 
	 */
	private boolean initFromIntent() {

		// boolean
		setSwitcher(!getIntent().getBooleanExtra("delivered", false));
		if (!isSwitcherComment())
			startIndicatorAnimToDeliver();
		// data
		Object o = getIntent().getSerializableExtra("msg");
		if (o != null) {
			Model_TrendsMsg m = (Model_TrendsMsg) o;
			if (m instanceof Model_TrendsDetailMsg) {
				mModel = m;
			} else if (m instanceof Model_TrendsMsgDeliver) {
				mModel = new Model_TrendsDetailDeliver((Model_TrendsMsgDeliver) m);
			} else {
				mModel = new Model_TrendsDetailMsg(m);
			}
			return true;
		} else {
			showError();
			return false;
		}
	}

	/**
	 * 
	 */
	protected void startIndicatorAnimToDeliver() {
		mHandler.sendEmptyMessageDelayed(MSG_ANIM_DELIVERED_INDICATOR, 200);
	}

	private void showError() {

	}

	private void clear() {
		mCommentList.clear();
		mDeliverList.clear();
		// mSet.refreshImmediatelyWithAdapterNotify(mAdapter);
	}

	private void setBottomBar() {
		mRefreshButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (mContentItem == null)
				// return;
				clear();
				// mContentItem.performCommentClick();
				requestTrendsContent();
			}

		});
		mCommentButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!AppContext.isValid(mActivity))
					return;

				int cid = mModel.getCommunity_id();
				if (!AppContext.curUser().isJoinInCommunity(cid)) {
					showToast("抱歉，您尚未加入 " + mModel.getCommunity_name() + " 小区，不能进行该小区的相关评论操作");
					return;
				}

				Intent intent = new Intent(mContext, SendCommentActivity.class);
				fitIntent(intent);
				intent.putExtra("cid", cid);
				startActivityForResult(intent, INTENT_REQUEST_CODE_COMMENT);
			}
		});
		mDeliverButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!AppContext.isValid(mActivity))
					return;

				int cid = mModel.getCommunity_id();
				if (!AppContext.curUser().isJoinInCommunity(cid)) {
					showToast("抱歉，您尚未加入 " + mModel.getCommunity_name() + " 小区，不能进行该小区的相关转发操作");
					return;
				}

				if (mSet == null || mSet.size() == 0) {
					return;
				}
				Intent intent = new Intent(mContext, SendForwardActivity.class);
				fitIntent(intent);
				intent.putExtra("cid", cid);
				startActivityForResult(intent, INTENT_REQUEST_CODE_DELIVER);
			}

		});
		mMoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (AppContext.isValid(mActivity)) {
					showOrCreateBottomMenuIfNeedy();
				}
			}

		});

		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				LogUtils.e(TAG, "onPullDownToRefresh");
				addMore();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				LogUtils.e(TAG, "onPullUpToRefresh");
				addMore();
			}
		});
	}

	protected void addMore() {
		if (isSwitcherComment()) {
			// clear();
			// mContentItem.performCommentClick();
			// requestTrendsContent();
			requestCommentList();
		} else {
			// clear();
			// mContentItem.performCommentClick();
			// requestTrendsContent();
			requestDeliverList();
		}
	}

	private void fitIntent(Intent intent) {
		LogUtils.e(TAG, "fitIntent");
		MultiModelBase model = mSet.get(1);
		if (model != null) {
			Model_TrendsMsg m = null;
			if (model instanceof Model_TrendsDetailDeliver) {
				LogUtils.e(TAG, "model instanceof Model_TrendsDetailDeliver");
				m = ((Model_TrendsDetailDeliver) model).getDeliveredMsg();
				intent.putExtra("span_texts", ((Model_TrendsDetailDeliver) model).getMSpanTexts());
			} else {
				LogUtils.e(TAG, "model ！！！！！！！！！！instanceof Model_TrendsDetailDeliver");
				m = (Model_TrendsMsg) model;
			}
			intent.putExtra("forward_id", getMsgId());
			intent.putExtra("msg_id", m.getMsg_id());
			intent.putExtra("header", m.getHeadPortraitUrl());
			intent.putExtra("name", m.getName());
			intent.putExtra("text", m.getText());
			intent.putExtra("tag", m.getmTag());
		}

		MultiModelBase modelName = mSet.get(0);
		if (modelName != null && modelName instanceof Model_TrendsDetailName) {
			Model_TrendsDetailName m = (Model_TrendsDetailName) modelName;
			intent.putExtra("uid", m.getUid());
			intent.putExtra("type", m.getType());
			intent.putExtra("msg_user_name", m.getName());
		}
	}

	private void setTitleLayout() {
		mTitleBar.setTitleName("信息正文");
		mTitleBar.showBackButton();
		mTitleBar.setLeftButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int d_count = mModel.getDeliveredCount();
		int c_count = mModel.getCommentCount();

		if (requestCode == INTENT_REQUEST_CODE_COMMENT && resultCode == Activity.RESULT_OK) {

			c_count++;

			if (data.getBooleanExtra("extra", false)) {
				d_count++;
			}

			// 需要刷新-----------
			clear();
			// mContentItem.performCommentClick();
			requestTrendsContent();
		} else if (requestCode == INTENT_REQUEST_CODE_DELIVER && resultCode == Activity.RESULT_OK) {

			d_count++;

			if (data.getBooleanExtra("extra", false)) {
				c_count++;
			}
			// 需要刷新-----------
			clear();
			// mContentItem.performCommentClick();
			requestTrendsContent();
		}

		mModel.setDeliveredCount(d_count);
		mModel.setCommentCount(c_count);

		mContentItem.setDeliveredCount(d_count);
		mContentItem.setCommentCount(c_count);

		// get original detai model
		// Warning: this object could be a null object,so do check before using
		// it
		Model_TrendsMsg m = (Model_TrendsMsg) AppCache.getInstance().get("originalDetailModel");
		if (m != null) {
			m.setDeliveredCount(d_count);
			m.setCommentCount(c_count);
		}
	}

	@Override
	protected void initData() {

	}

	private void showOrCreateBottomMenuIfNeedy() {

		//
		if (mSet == null || mSet.size() == 0) {
			return;
		}

		// show
		if (mBottomMenu.getChildCount() > 0) {
			showBottomMenu();
			return;
		}

		// create
		MultiModelBase nameModel = mSet.get(0);
		if (nameModel != null && nameModel instanceof Model_TrendsDetailName) {
			Model_TrendsDetailName m = (Model_TrendsDetailName) nameModel;

			// clip text
			String clipText = null;
			MultiModelBase mm = mSet.get(1);
			if (mm != null) {
				if (mm instanceof Model_TrendsDetailMsg) {
					clipText = ((Model_TrendsDetailMsg) mm).getMsgInfo();
				} else {
					clipText = ((Model_TrendsDetailDeliver) mm).getDeliveredMsg().getMsgInfo();
				}
			}

			clipText = clipText == null ? "" : clipText;

			// init
			int uid = m.getUid();
			if (uid == AppContext.curUser().getUid()) {
				initBottomMenu(BaseBottomMenu.TYPE_BG_RED + "删除", "复制", "转发短信");
				setBottomMenuButtonListener(0, new DeleteListener());
				setBottomMenuButtonListener(1, new ClipListener(clipText));
				setBottomMenuButtonListener(2, new SendMsgListener(clipText));
			} else {
				initBottomMenu("复制", "转发短信");
				setBottomMenuButtonListener(0, new ClipListener(clipText));
				setBottomMenuButtonListener(1, new SendMsgListener(clipText));
			}
		}

		showBottomMenu();
	}

	private class DeleteListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			dismissBottomMenu();
			RSDeleteMsg rs = new RSDeleteMsg(AppContext.curUser().getUid(), getMsgId());
			rs.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH);
					finish();
					showToast("删除消息成功");
					hideProgressDialog();
				}
			});
			rs.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					showToast("删除消息失败");
					hideProgressDialog();
					LogUtils.e(TAG, "", ex);
				}
			});
			showProgressDialog("", false);
			rs.asyncExecute(mContext);
		}

	}

	private class SendMsgListener implements View.OnClickListener {

		private String msg;

		public SendMsgListener(String msg) {
			this.msg = msg;
		}

		@Override
		public void onClick(View v) {
			dismissBottomMenu();
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
			intent.putExtra("sms_body", msg);
			startActivity(intent);
		}

	}

	private class ClipListener implements View.OnClickListener {
		private String mClipText;

		public ClipListener(String clipText) {
			this.mClipText = clipText;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			if (cb == null) {
				return;
			}
			cb.setText(mClipText);
			dismissBottomMenu();
			showToast("已复制到剪切板");
		}

	}

	@Override
	protected void initCallbacks() {

	}

	private RSTrendsDetailContent mContentRs = null;
	private MultiModelsSet mSet;
	private MultiTypesAdapter mAdapter;

	private void requestTrendsContent() {

		if (mContentRs != null) {
			return;
		}

		int uid = StringRS.NULL_INT;
		if (!AppContext.isVisitor())
			uid = AppContext.curUser().getUid();
		mContentRs = new RSTrendsDetailContent(getMsgId(), uid);
		mContentRs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {

				LogUtils.e(TAG, result.toString());

				mSet = new MultiModelsSet(4, new JSONParser_TrendsDetailContent());
				mSet.add(result.toString());
				mSet.lockNode();

				mAdapter = new MultiTypesAdapter(mSet, mContext);
				mListView.setAdapter(mAdapter);

				mSet.setJSONParser(new JSONParser_TrendsDetailComment());

				showLoading();
				requestList();

				hideProgressDialog();

				mContentRs = null;
			}
		});
		mContentRs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				mContentRs = null;
				mAdapter.notifyDataSetChanged();
				LogUtils.e(TAG, "request trends detail content error ", ex);
				hideProgressDialog();
				showToast("网络错误，请稍候重试");
			}
		});

		showProgressDialog(null, false);
		mContentRs.asyncExecute(this);
	}

	private RSTrendsDetailComment commentRs = null;

	/**
	 * 刷新评论列表
	 */
	private void requestCommentList() {
		if (commentRs != null) {
			return;
		}

		if (mSet.isMax()) {
			setRefreshComplete();
			if (mCommentList.size() == 0) {
				dismissLoadingWithError();
			} else {
				showToast("数据已经全部加载完毕");
			}
			return;
		}
		commentRs = new RSTrendsDetailComment(getMsgId(), getCommentLastId(),
				getCommentRequestSize());
		commentRs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				commentRs = null;
				if (mSet.add(result.toString())) {
					dismissLoading();
					mCommentList.addAll(mSet.getTempList());

					// hide first top line
					if (mCommentList.size() > 0
							&& ((Model_TrendsDetailComment) mCommentList.get(0)).isNeedTopLine()) {
						((Model_TrendsDetailComment) mCommentList.get(0)).setNeedTopLine(false);
					}

					mAdapter.notifyDataSetChanged();
				} else {
					dismissLoadingWithError();
				}

				// if refreshing
				setRefreshComplete();
			}
		});
		commentRs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				dismissLoadingWithError();
				setRefreshComplete();
				LogUtils.e(TAG, "error", ex);
				commentRs = null;
				showToast("网络错误，请稍候重试");
			}
		});
		commentRs.asyncExecute(this);
	}

	private int getCommentRequestSize() {
		return mSet.getPageSize();
	}

	private int getCommentLastId() {
		return mSet.getNextPageStart();
	}

	protected int getMsgId() {
		return mModel != null ? mModel.getMsg_id() : -1;
	}

	@Override
	public void onResume() {
		super.onResume();
		ApplicationFacade.getInstance().registerMediator(
				new TrendsDetailMediator(TrendsDetailMediator.NAME, null));
	}

	public void onPause() {
		super.onPause();
		ApplicationFacade.getInstance().removeMediator(TrendsDetailMediator.NAME);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private boolean mSwitcherComment;

	private void setSwitcher(boolean comment) {
		this.mSwitcherComment = comment;
	}

	private boolean isSwitcherComment() {
		return this.mSwitcherComment;
	}

	// VisitingCardDialog
	private void requestList() {
		if (isSwitcherComment())
			switchToComment();
		else
			switchToDeliver();
	}

	private RSTrendsDetailDeliver deliverRs = null;

	/**
	 * 刷新转发列表
	 */
	private void requestDeliverList() {

		if (deliverRs != null) {
			return;
		}

		if (mSet.isMax()) {
			setRefreshComplete();
			if (mDeliverList.size() == 0) {
				dismissLoadingWithError();
			} else {
				showToast("数据已经全部加载完毕");
			}
			return;
		}

		deliverRs = new RSTrendsDetailDeliver(getMsgId(), getCommentLastId(),
				getCommentRequestSize());
		deliverRs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				deliverRs = null;
				if (mSet.add(result.toString())) {
					dismissLoading();
					mDeliverList.addAll(mSet.getTempList());

					// hide first top line
					if (mDeliverList.size() > 0
							&& ((Model_TrendsDetailComment) mDeliverList.get(0)).isNeedTopLine()) {
						((Model_TrendsDetailComment) mDeliverList.get(0)).setNeedTopLine(false);
					}

					mAdapter.notifyDataSetChanged();
				} else {
					dismissLoadingWithError();
				}

				// if refreshing
				setRefreshComplete();
			}
		});
		deliverRs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				dismissLoadingWithError();
				setRefreshComplete();
				LogUtils.e(TAG, "error", ex);
				deliverRs = null;
				showToast("网络错误，请稍候重试");
			}
		});
		deliverRs.asyncExecute(this);

	}

	private void switchToComment() {
		setSwitcher(true);
		dismissLoading();
		mSet.changeNodeList(mCommentList);
		mAdapter.notifyDataSetChanged();
		if (mCommentList.size() == 0) {
			showLoading();
		}
		requestCommentList();
	}

	private void switchToDeliver() {
		setSwitcher(false);
		dismissLoading();
		mSet.changeNodeList(mDeliverList);
		mAdapter.notifyDataSetChanged();
		if (mDeliverList.size() == 0) {
			showLoading();
		}
		requestDeliverList();
	}

	private void showLoading() {
		if (mContentItem != null && !mContentItem.isLoading()) {
			mContentItem.showLoading();
		}
	}

	private void dismissLoading() {
		if (mContentItem != null && mContentItem.isLoading()) {
			mContentItem.dismissLoading();
		}
	}

	protected void dismissLoadingWithError() {
		if (mContentItem != null && mContentItem.isLoading()) {
			mContentItem.showError();
		}
	}

	private void dimissErro() {
		mContentItem.dismissLoading();
	}

	private class TrendsDetailMediator extends Mediator {
		public static final String NAME = "TrendsDetailMediator";

		public TrendsDetailMediator(String mediatorName, Object viewComponent) {
			super(mediatorName, viewComponent);
		}

		@Override
		public void handleNotification(INotification notification) {
			Object obj = notification.getBody();
			String name = notification.getName();
			if (name.equals(AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_COMMENT)) {
				switchToComment();
			} else if (name.equals(AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_DELIVER)) {
				switchToDeliver();
			} else if (name.equals(AppConst.APP_FACADE_TRENDSDETAIL_MSG_ITEM)) {
				mContentItem = (Item_TrendsMsg) obj;
				if (!isSwitcherComment()) {
					mContentItem.setIndicatorDeliver();
				}
				// set indicator
				if (mContentItem != null) {
					mContentItem.disableRefresh();
				}
			} else if (name.equals(AppConst.APP_FACADE_TRENDSDETAIL_MSG_NAME)) {
				mNameItem = (Item_TrendsDetailName) obj;
				// set indicator
				if (mContentItem != null) {
					mNameItem.disableRefresh();
				}
			}
			super.handleNotification(notification);
		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_COMMENT,
					AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_DELIVER,
					AppConst.APP_FACADE_TRENDSDETAIL_MSG_ITEM,
					AppConst.APP_FACADE_TRENDSDETAIL_MSG_NAME };
		}

	}

	private ADHandler<TrendsDetailActivity> mHandler = new ADHandler<TrendsDetailActivity>(this,
			new MessageHandler<TrendsDetailActivity>() {

				@Override
				public void handleMessage(TrendsDetailActivity a, Message msg) {
					if (msg.what == TrendsDetailActivity.SET_REFRESH_COMPLETE) {
						a.forceRefreshListViewComplete();
					} else if (msg.what == TrendsDetailActivity.MSG_ANIM_DELIVERED_INDICATOR) {
						a.indicatorAnimToDelivered();
					}
				}
			});

	protected void forceRefreshListViewComplete() {
		mListView.onRefreshComplete();
	}

	protected void indicatorAnimToDelivered() {
		if (mContentItem != null) {
			mContentItem.startIndicateToDeliverAnim();
		}
	}

	private void setRefreshComplete() {
		mHandler.sendEmptyMessageDelayed(SET_REFRESH_COMPLETE, 100);
	}

}

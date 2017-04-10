package com.sinaleju.lifecircle.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView;
import com.handmark.pulltorefresh.library.extras.ExtendedListView.OnPositionChangedListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.ChatDetailAct;
import com.sinaleju.lifecircle.app.activity.FollowListAct;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.adapter.PersonalChatAdapter;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.chatandnotice.TabView;
import com.sinaleju.lifecircle.app.chatandnotice.TabView.ClickCallback;
import com.sinaleju.lifecircle.app.customviews.LCScrollBarPanel;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.PersonalChatBean;
import com.sinaleju.lifecircle.app.model.PersonalChatBean.UserInfo;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_PersonalChat;
import com.sinaleju.lifecircle.app.model.json.JSONParser_PersonalNotice;
import com.sinaleju.lifecircle.app.service.LifeCircleService;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSPersonalChat;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSPersonalNotice;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.LogUtils;

/**
 * 
 * @ClassName: MsgAndNoticeFragment
 * @Description: TODO 消息的fragment
 * @author zhenwei
 * @date 2013-8-23 下午12:08:30
 * 
 */
public class MsgAndNoticeFragment extends BaseFragment implements OnClickListener {

	private static final String TAG = "MsgAndNoticeFragment";
	private View mRoot;
	private Button backBtn, rightBtn;
	private TabView tabView;
	private int userId;

	private PullToRefreshListView listView;
	private RSPersonalChat rsChat;
	private RSPersonalNotice rsNotice;
	private int chat_last_id = 0; // 上一页最后一条数据id
	private final int page_size = 15;
	private boolean isChatList = true;

	private int smallest_id;
	private int total;
	private int surplus;
	private UserInfo userInfo;
	private ArrayList<PersonalChatBean> chatListData;
	private PersonalChatAdapter mChatAdapter;

	private MultiModelsSet mSet = null;
	private MultiTypesAdapter mNoticeAdapter = null;
	private LCScrollBarPanel mScrollBar;
	private NotificationManager mNotiManager;

	public MsgAndNoticeFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MsgAndNoticeFragment(int userId) {
		super();
		this.userId = userId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.v(TAG, "-------------initView-------");
		mRoot = inflater.inflate(R.layout.msg_and_notice, null);
		initView();
		initListView();
		initData();
		setListener();
		return mRoot;
	}

	@Override
	protected void initView() {
		ApplicationFacade.getInstance().registerMediator(
				new MsgAndNoticeMediator(MsgAndNoticeMediator.NAME, null));
		backBtn = (Button) mRoot.findViewById(R.id.msg_and_notice_left_btn);
		rightBtn = (Button) mRoot.findViewById(R.id.msg_and_notice_right_btn);
		tabView = (TabView) mRoot.findViewById(R.id.msg_and_notice_tabview);
		listView = (PullToRefreshListView) mRoot.findViewById(R.id.notice_listview);

		tabView.setChatIconShowOrHide(LifeCircleService.isNewLetter);
		tabView.setNoticeIconShowOrHide(LifeCircleService.isNewNotice);
		mNotiManager = (NotificationManager) getActivity().getSystemService(
				Context.NOTIFICATION_SERVICE);
	}

	private void initListView() {
		mScrollBar = new LCScrollBarPanel(getActivity());
		mScrollBar.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		((ExtendedListView) listView.getRefreshableView()).setScrollBarPanel(mScrollBar);
		((ExtendedListView) listView.getRefreshableView()).setShowScrollBarPanel(false);
		((ExtendedListView) listView.getRefreshableView())
				.setOnPositionChangedListener(new OnPositionChangedListener() {

					@Override
					public void onPositionChanged(ExtendedListView listView, int position,
							View scrollBarPanel) {
						if (!isChatList) {
							if (mNoticeAdapter != null) {
								if (position > 0) {
									Object o = mNoticeAdapter.getItem(position - 1);
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
					}
				});
	}

	@Override
	protected void initData() {
		showProgressDialog(getActivity().getString(R.string.loading_data), true);
		loadingData(true);
	}

	@Override
	protected void setListener() {
		backBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		tabView.setClickCallback(new ClickCallback() {

			@Override
			public void click(int index) {
				switch (index) {
				case ClickCallback.FIRST_INDEX:
					isChatList = true;
					rightBtn.setVisibility(View.VISIBLE);
					mScrollBar.setVisibility(View.GONE);
					((ExtendedListView) listView.getRefreshableView()).setShowScrollBarPanel(false);
					if (mChatAdapter != null) {
						listView.setAdapter(mChatAdapter);
					}
					refreshChatData();
					break;
				case ClickCallback.SECOND_INDEX:
					isChatList = false;
					rightBtn.setVisibility(View.INVISIBLE);
					mScrollBar.setVisibility(View.VISIBLE);
					((ExtendedListView) listView.getRefreshableView()).setShowScrollBarPanel(true);
					if (mNoticeAdapter == null) {
						mSet = new MultiModelsSet(5, new JSONParser_PersonalNotice());
						mNoticeAdapter = new MultiTypesAdapter(mSet, getActivity());
						listView.setAdapter(mNoticeAdapter);
						requestNoticeData();
					} else {
						listView.setAdapter(mNoticeAdapter);
					}
					break;
				}
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pullDownLoadingData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				pullUpLoadingData();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (isChatList) {
					UserBean userBean = AppContext.curUser();
					PersonalChatBean bean = chatListData.get(position - 1);
					Intent intent = new Intent(getActivity(), ChatDetailAct.class);
					intent.putExtra(ChatDetailAct.NAME_KEY, bean.getUser_info().getDisplay_name());
					intent.putExtra(ChatDetailAct.USER_ID_KEY, userBean.getUid());
					intent.putExtra(ChatDetailAct.TO_USER_ID_KEY, bean.getUser_info().getId());
					intent.putExtra(ChatDetailAct.TYPE_KEY, bean.getUser_info().getType());
					getActivity().startActivity(intent);
				}
			}
		});
	}

	private void pullUpLoadingData() {// 上拉刷新加载数据
		if (isChatList) {
			if (surplus == 0) {
				showToast(R.string.no_more);
				comlpeteRefresh();
				return;
			}
			loadingData(false);
		} else {
			requestNoticeData();
		}
	}

	private void pullDownLoadingData() {// 下拉刷新加载数据
		if (isChatList) {
			chat_last_id = 0;
			loadingData(true);
		} else {
			mSet = new MultiModelsSet(5, new JSONParser_PersonalNotice());
			mNoticeAdapter = new MultiTypesAdapter(mSet, getActivity());
			listView.setAdapter(mNoticeAdapter);
			requestNoticeData();
		}
	}

	private void requestNoticeData() {
		if (mSet.isMax()) {
			showToast(R.string.no_more);
			comlpeteRefresh();
			return;
		}

		rsNotice = new RSPersonalNotice(mSet.getNextPageStart(), mSet.getPageSize(), userId);
		rsNotice.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				comlpeteRefresh();
				if (mSet.add(result.toString())) {
					mNoticeAdapter.notifyDataSetChanged();
				}
				comlpeteRefresh();
				if (LifeCircleService.isNewNotice) {
					if (getActivity() != null) {
						LifeCircleService.manuallyNewMessageState(getActivity());
					}
				}
				rsNotice = null;
			}
		});
		rsNotice.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				comlpeteRefresh();
				showToast(R.string.net_error);
				rsNotice = null;
			}
		});
		showProgressDialog(getActivity().getString(R.string.loading_data), true);
		rsNotice.asyncExecute(getActivity());
	}

	private void refreshChatData() {
		listView.setRefreshing();
		loadingData(true);
	}

	private void loadingData(final boolean isReloading) {
		if (rsChat != null) {
			comlpeteRefresh();
			return;
		}

		rsChat = new RSPersonalChat();
		HashMap<String, String> params = new HashMap<String, String>();
		if (chat_last_id > 0) {
			params.put("last_id", String.valueOf(chat_last_id));
		}
		params.put("page_size", String.valueOf(page_size));
		params.put("user_id", String.valueOf(userId));

		rsChat.setParams(params);
		rsChat.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				comlpeteRefresh();
				rsChat = null;

				HashMap<String, Object> resultMap = JSONParser_PersonalChat.parser(result
						.toString());
				fillChatData(resultMap, isReloading);
				if (LifeCircleService.isNewLetter) {
					if (getActivity() != null) {
						LifeCircleService.manuallyNewMessageState(getActivity());
					}
				}
			}
		});
		rsChat.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				comlpeteRefresh();
				listView.setAdapter(null);
				rsChat = null;
				showToast(R.string.net_error);
			}
		});

		rsChat.asyncExecute(getActivity());
	}

	private void fillChatData(HashMap<String, Object> resultMap, boolean isReloading) {
		if (null != resultMap) {
			smallest_id = (Integer) resultMap.get("smallest_id");
			total = (Integer) resultMap.get("total");
			surplus = (Integer) resultMap.get("surplus");

			userInfo = (UserInfo) resultMap.get("user_info");
			ArrayList<PersonalChatBean> list = (ArrayList<PersonalChatBean>) resultMap.get("list");
			if ((null == list || list.size() == 0)) {
				if (isReloading) {
					showToast("您现在还没有跟任何人发过私信！");
					chatListData = list;
					mChatAdapter = null;
					listView.setAdapter(null);
				}
				return;
			}
			if (null == chatListData || isReloading)
				chatListData = list;
			else
				chatListData.addAll(list);

			if (null == mChatAdapter || isReloading) {
				mChatAdapter = new PersonalChatAdapter(getActivity(), chatListData);
				if (isChatList)
					listView.setAdapter(mChatAdapter);
			} else {
				mChatAdapter.notifyDataSetChanged();
			}
		}
	}

	private void comlpeteRefresh() {
		mHandler.sendEmptyMessageDelayed(SET_REFRESH_COMPLETE, 100);
	}

	private static final int SET_REFRESH_COMPLETE = 101;
	private ADHandler<MsgAndNoticeFragment> mHandler = new ADHandler<MsgAndNoticeFragment>(this,
			new MessageHandler<MsgAndNoticeFragment>() {

				@Override
				public void handleMessage(MsgAndNoticeFragment f, Message msg) {
					if (msg.what == SET_REFRESH_COMPLETE) {
						listView.forceRefreshComplete();
					}
				}

			});

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.msg_and_notice_left_btn:
			((HomeActivity) getActivity()).toggle();
			break;
		case R.id.msg_and_notice_right_btn:
			Intent intent = new Intent(getActivity(), FollowListAct.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isChatList) {
			refreshChatData();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ApplicationFacade.getInstance().removeMediator(MsgAndNoticeMediator.NAME);
		super.onDestroy();
	}

	private class MsgAndNoticeMediator extends Mediator {
		public static final String NAME = "MsgAndNoticeMediator";

		public MsgAndNoticeMediator(String mediatorName, Object viewComponent) {
			super(mediatorName, viewComponent);
		}

		@Override
		public void handleNotification(INotification notification) {
			String name = notification.getName();
			if (name.equals(AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE)) {
				tabView.setChatIconShowOrHide(LifeCircleService.isNewLetter);
				tabView.setNoticeIconShowOrHide(LifeCircleService.isNewNotice);
				if (!LifeCircleService.isNewLetter) {
					mNotiManager.cancel(100001);
				}
				if (!LifeCircleService.isNewNotice) {
					mNotiManager.cancel(100002);
				}
			}
			super.handleNotification(notification);
		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE };
		}

	}

}

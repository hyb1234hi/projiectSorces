package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.adapter.ChatDetailAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.bottommenu.BaseBottomMenu;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.ChatDetailBean;
import com.sinaleju.lifecircle.app.model.PersonalChatBean.UserInfo;
import com.sinaleju.lifecircle.app.model.json.JSONParser_ChatDetail;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSCommenHttp;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;

/**
 * 
 * @ClassName: ChatDetailAct
 * @Description: TODO 互发私信界面
 * @author zhenwei
 * @date 2013-8-23 下午12:01:16
 * 
 */
public class ChatDetailAct extends BaseActivity implements OnClickListener {

	PullToRefreshListView listView;
	Button sendBtn;
	EditText bodyET;

	private String titleName;
	private int userId, toUserId;
	private int type;

	private int surplus = -1;
	private int last_id;
	private final int page_size = 10;

	public static final String NAME_KEY = "name";
	public static final String USER_ID_KEY = "user_id";
	public static final String TO_USER_ID_KEY = "to_user_id";
	public static final String TYPE_KEY = "type";
	private static final String TAG = "ChatDetailAct";

	RSCommenHttp rs;

	private final int FIRST_DOWN_LOAD = 0;// 第一次进该界面
	private final int PULL_DOWN_LOAD = 1;// 下拉刷新
	private final int REGULAR_REFRESH_LOAD = 2;// 上拉刷新

	// 定时设为30秒
	private final int REFRESH_DELAY = 15 * 1000;

	private UserInfo userInfo, toUserInfo;
	private ArrayList<ChatDetailBean> listData;

	private ChatDetailAdapter adapter;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.chat_detail;
	}

	@Override
	protected void initView() {
		listView = (PullToRefreshListView) findViewById(R.id.chat_detail_listview);
		sendBtn = (Button) findViewById(R.id.chat_detail_send_btn);
		bodyET = (EditText) findViewById(R.id.chat_detail_body_et);

		init();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	private void init() {
		Intent intent = getIntent();
		titleName = intent.getStringExtra(NAME_KEY);
		userId = intent.getIntExtra(USER_ID_KEY, -1);
		toUserId = intent.getIntExtra(TO_USER_ID_KEY, -1);
		type = intent.getIntExtra(TYPE_KEY, 0);

		LogUtils.i(TAG, "name: " + titleName + "  userId: " + userId + " toUserId: " + toUserId);
		mTitleBar.setTitleName(titleName);
		mTitleBar.initRightButtonTextOrResId("", R.drawable.selector_top_bar_more);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);

		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);

		bodyET.setHint("发消息给" + titleName + "...");

		initBottomMenu(BaseBottomMenu.TYPE_BG_RED + "访问主页", "删除此对话框");

		showProgressDialog(R.string.loading_data, false);
		loadData(FIRST_DOWN_LOAD, createPullDownRefreshParams());
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		sendBtn.setOnClickListener(this);

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				pullDownLoadingData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			}
		});

		setBottomMenuButtonListener(0, new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppContext.gotoIndexActivity(mContext, type, toUserId);
				dismissBottomMenu();
			}
		});

		setBottomMenuButtonListener(1, new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteChat();
				dismissBottomMenu();
			}
		});
		setBottomMenuButtonListener(2, new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissBottomMenu();
			}
		});
	}

	protected void pullDownLoadingData() {
		if (surplus == 0) {
			showToast("没有更早的数据了！");
			comlpeteRefresh();
			return;
		}
		pullDownRefresh();
	}

	// listview更新完成
	private void comlpeteRefresh() {
		mHandler.sendEmptyMessageDelayed(0, 100);
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			listView.forceRefreshComplete();
		}

	};

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.chat_detail_send_btn:
			sendChat();
			break;
		case R.id.imavBack:
			hideSoftInput();
			finish();
			break;
		case R.id.tvRightFunc1:
			hideSoftInput();
			showBottomMenu();
			break;
		}
	}

	/**
	 * 定时更新
	 */
	private void regularRefresh() {
		loadData(REGULAR_REFRESH_LOAD, createRegularRefreshParams());
	}

	private HashMap<String, String> createRegularRefreshParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(USER_ID_KEY, String.valueOf(userId));
		params.put(TO_USER_ID_KEY, String.valueOf(toUserId));
		int maxId = (listData == null || listData.size() == 0) ? 0 : ((ChatDetailBean) listData
				.get(listData.size() - 1)).getId();
		params.put("max_id", String.valueOf(maxId));

		return params;
	}

	/**
	 * 下拉刷新
	 */
	private void pullDownRefresh() {
		loadData(PULL_DOWN_LOAD, createPullDownRefreshParams());
	}

	private HashMap<String, String> createPullDownRefreshParams() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(USER_ID_KEY, String.valueOf(userId));
		params.put(TO_USER_ID_KEY, String.valueOf(toUserId));

		int lastId = (listData == null || listData.size() == 0) ? 0 : ((ChatDetailBean) listData
				.get(0)).getId();
		params.put("last_id", String.valueOf(lastId));
		params.put("page_size", String.valueOf(page_size));

		return params;
	}

	/**
	 * 取聊天列表数据
	 * 
	 * @param index
	 * @param lastId
	 */
	private void loadData(final int index, HashMap<String, String> params) {

		rs = new RSCommenHttp(RemoteConst.URL_PERSONAL_CHAT_DETAIL, StringRS.METHOD_GET);
		rs.setParams(params);
		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				HashMap<String, Object> resultMap = JSONParser_ChatDetail.parser(result.toString());
				if (null == resultMap) {
					return;
				}
				if (index == FIRST_DOWN_LOAD) {
					fillResetData(resultMap);
				} else if (index == REGULAR_REFRESH_LOAD) {// 数据添加到listview的下面
					fillDataAddToBottom(resultMap);
				} else {// 数据添加到listview的上面
					fillDataAddToTop(resultMap);
				}

				refreshDelay();
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				refreshDelay();
			}
		});
		rs.asyncExecute(this);
	}

	/**
	 * 下拉刷新的数据 添加到listview中
	 * 
	 * @param resultMap
	 */
	private void fillDataAddToTop(HashMap<String, Object> resultMap) {
		baseData(resultMap);
		comlpeteRefresh();
		ArrayList<ChatDetailBean> list = (ArrayList<ChatDetailBean>) resultMap.get("list");

		if (null == listData || listData.size() == 0 || null == adapter) {// 此判断可能没用
			fillResetData(resultMap);
		} else if (null != list && list.size() > 0) {
			listData.addAll(0, list);
			addToTop(list.size());
		}
	}

	/**
	 * 加载的数据添加到listview的下面
	 * 
	 * @param resultMap
	 */
	private void fillDataAddToBottom(HashMap<String, Object> resultMap) {
		ArrayList<ChatDetailBean> list = (ArrayList<ChatDetailBean>) resultMap.get("list");
		if (null == listData || listData.size() == 0 || null == adapter) {
			fillResetData(resultMap);
		} else if (null != list) {
			listData.addAll(list);
			adapter.notifyDataSetChanged();
			if (listView.getLastVisiblePosition() == listData.size() - 1) {
				listView.setSelection(listData.size());
			}
		}
	}

	// 重新刷新数据
	private void fillResetData(HashMap<String, Object> resultMap) {
		baseData(resultMap);
		ArrayList<ChatDetailBean> list = (ArrayList<ChatDetailBean>) resultMap.get("list");
		if (null != list && list.size() > 0) {
			listData = list;
			adapter = new ChatDetailAdapter(mContext, list, userId, userInfo, toUserInfo);
			listView.setAdapter(adapter);
			if (listData != null && listData.size() > 0) {
				adapter.notifyDataSetChanged();
				listView.setSelection(listData.size() - 1);
			}

		}
	}

	// 计算 在adapter刷数据后 还在原来的位置
	private void addToTop(int addSize) {
		int firstPosition = listView.getFirstVisiblePosition();
		View firstView = listView.getChildAt(1);
		int[] subviewLocations = new int[2];
		if (null != firstView)
			firstView.getLocationOnScreen(subviewLocations);
		adapter.notifyDataSetChanged();

		int[] listviewLocation = new int[2];
		listView.getLocationInWindow(listviewLocation);

		listView.setSelectionFromTop(addSize + firstPosition, subviewLocations[1]
				- listviewLocation[1]);
	}

	private void baseData(HashMap<String, Object> resultMap) {
		surplus = (Integer) resultMap.get("surplus");
		last_id = (Integer) resultMap.get("smallest_id");
		if (null == userInfo)
			userInfo = (UserInfo) resultMap.get("user_info");
		if (null == toUserInfo)
			toUserInfo = (UserInfo) resultMap.get("to_user_info");
	}

	// 发送私信
	private void sendChat() {
		if (TextUtils.isEmpty(bodyET.getText().toString())) {
			return;
		}

		hideSoftInput();

		showProgressDialog("正在发送，请稍后...", false);

		rs = new RSCommenHttp(RemoteConst.URL_SEND_CHAT, StringRS.METHOD_POST);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(USER_ID_KEY, String.valueOf(userId));
		params.put(TO_USER_ID_KEY, String.valueOf(toUserId));
		params.put("content", bodyET.getText().toString());

		rs.setParams(params);

		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				regularRefresh();
				bodyET.setText("");
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ConnectTimeoutException) {
					showToast("发送超时，请重试");
				} else if (ex instanceof ADRemoteException) {
					String msg = ((ADRemoteException) ex).msg();
					if (!TextUtils.isEmpty(msg)) {
						showToast(msg);
					} else {
						showToast("发送失败！");
					}
				}
				LogUtils.i(TAG, ex.toString());
			}
		});
		rs.asyncExecute(this);
	}

	// 定时刷新 暂定为30秒刷新一次
	private void refreshDelay() {
		removeRefresh();
		refreshLooper.postDelayed(refreshRunnable, REFRESH_DELAY);
	}

	private void removeRefresh() {
		refreshLooper.removeCallbacks(refreshRunnable);
	}

	Handler refreshLooper = new Handler();

	/**
	 */
	private Runnable refreshRunnable = new Runnable() {

		@Override
		public void run() {
			regularRefresh();
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void finish() {
		removeRefresh();
		super.finish();
	}

	/**
	 * 删除此聊天记录
	 */
	private void deleteChat() {
		showProgressDialog("正在删除此对话，请稍后...", false);
		RSCommenHttp deketeRs = new RSCommenHttp(RemoteConst.URL_DELETE_CHAT, StringRS.METHOD_GET);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(USER_ID_KEY, String.valueOf(userId));
		params.put(TO_USER_ID_KEY, String.valueOf(toUserId));
		deketeRs.setParams(params);

		deketeRs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				showToast("删除成功！");
				hideProgressDialog();
				finish();
			}
		});
		deketeRs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else {
					showToast(R.string.net_error);
				}
			}
		});
		deketeRs.asyncExecute(this);
	}

	private void hideSoftInput() {
		InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (input.isActive()) {
			try {
				ADSoftInputUtils.hide(mContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

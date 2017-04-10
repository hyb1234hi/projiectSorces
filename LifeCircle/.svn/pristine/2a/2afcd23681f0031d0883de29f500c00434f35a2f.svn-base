package com.sinaleju.lifecircle.app.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.adapter.TicketAdapter;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.TicketModel;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAddNewCommunity;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSEventsSquare;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class LeftEventsSquareFragment extends BaseFragment {

	protected static final String TAG = "LeftEventsSquareFragment";

	private View mContentView = null;

	private TitleBar mTitleBar = null;
	private ListView mTicketList = null;
	private RSEventsSquare mActivityList = null;
	private TicketAdapter mTicketAdapter = null;
	private List<TicketModel> mTicketModels = new ArrayList<TicketModel>();
	private RSAddNewCommunity rsAddCommunity = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mContentView == null) {
			mContentView = inflater.inflate(R.layout.fr_events_square, container, false);
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
		mTitleBar = (TitleBar) mContentView.findViewById(R.id.mSquareTitleBar);
		mTicketList = (ListView) mContentView.findViewById(R.id.ticket_list);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((AppContext) getActivity().getApplication()).getHomeActivity().toggle();
			}
		});
	}

	@Override
	protected void initData() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_home_page_top_bar_left_button);
		mTitleBar.setTitleName("活动广场");

		requestActivityList();

	}

	private void joinEventsSquare(final TicketModel ticket) {
		if (rsAddCommunity != null) {
			return;
		}
		int add_time = (int) (System.currentTimeMillis() / 1000);
		rsAddCommunity = new RSAddNewCommunity(AppContext.curUser().getUid() + "",
				ticket.getCommunity_id() + "", add_time + "");
		rsAddCommunity.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) { // wu
				hideProgressDialog();
				CommunityBean bean = new CommunityBean();
				bean.setCid(ticket.getCommunity_id());
				bean.setName(ticket.getName());
				bean.setTopicCount(ticket.getTopic_num());
				bean.setType(2);// 设置是虚拟小区
				AppContext.curUser().addNewCommunity(bean);
				// 设置刷新主页
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH);
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_ENTER_HOMEPAGE_TOP);

				// 进入当前小区
				((HomeActivity) getActivity()).backToMainPage();

				rsAddCommunity = null;
				LogUtils.e(TAG, result.toString());
			}
		});
		rsAddCommunity.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				rsAddCommunity = null;
				LogUtils.e(TAG, ex.toString());
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}
			}
		});
		rsAddCommunity.asyncExecute(getActivity(), this);
	}

	private void enterEventsSquare(final TicketModel ticket) {
		CommunityBean bean = new CommunityBean();
		bean.setCid(ticket.getCommunity_id());
		bean.setName(ticket.getName());
		bean.setTopicCount(ticket.getTopic_num());
		bean.setType(2);// 设置是虚拟小区
		AppContext.curUser().setCurrentCommunity(bean);
		// 设置刷新主页
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH);
		ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_ENTER_HOMEPAGE_TOP);
		// 进入当前小区
		((HomeActivity) getActivity()).backToMainPage();
	}

	private void requestActivityList() {
		if (mActivityList == null) {
			mActivityList = new RSEventsSquare(AppContext.curUser().getUid());
			mActivityList.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					hideProgressDialog();
					if (!TextUtils.isEmpty(result.toString())) {
						parseJSON(result.toString());
						if (mTicketModels.size() > 0) {
							mTicketAdapter = new TicketAdapter(getActivity(), mTicketModels) {

								@Override
								public void onJoinTicketClick(TicketModel ticket) {
									// TODO Auto-generated method stub
									if (ticket.getIsJoin() == 2) {
										joinEventsSquare(ticket);
									} else if (ticket.getIsJoin() == 1) {
										enterEventsSquare(ticket);
									}
								}

							};
							mTicketList.setAdapter(mTicketAdapter);
						}
					} else {
						showToast("服务器返回错误！");
					}
					mActivityList = null;
				}

			});
			mActivityList.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					mActivityList = null;
					showToast("网络错误！");
				}
			});

			showProgressDialog("正在加载中...", true);
			mActivityList.asyncExecute(getActivity(), this);
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void parseJSON(String json) {
		try {
			JSONArray array = new JSONArray(json);
			if (array != null && array.length() != 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					TicketModel model = new TicketModel();
					model.setCommunity_id(obj.optInt("community_id"));
					model.setName(obj.optString("name"));
					model.setWeb_url(obj.optString("web_url"));
					model.setBackground(obj.optString("background"));
					model.setContent(obj.optString("content"));
					model.setTopic_num(obj.optInt("topic_num"));
					model.setIsJoin(obj.optInt("type"));
					mTicketModels.add(model);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

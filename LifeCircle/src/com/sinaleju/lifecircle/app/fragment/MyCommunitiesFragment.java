package com.sinaleju.lifecircle.app.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.SelectCityActivity;
import com.sinaleju.lifecircle.app.adapter.AddedCellAdapter;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAddNewCommunity;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSDeleteCommunity;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetAddedCommunityList;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class MyCommunitiesFragment extends BaseFragment implements OnTouchListener{

	private static final String TAG = "MyCommunitiesFragment";
	protected static final int GOTOSELECTCITYACTIVITY = 1000;
	private RSGetAddedCommunityList rsCommunity;
	private ListView mAddedCellList = null;
	private AddedCellAdapter mCellAdapter = null;
	public static List<CommunityBean> mAddedCellModels = new ArrayList<CommunityBean>();
	private String user_id;
	private View mContentView = null;
	private TitleBar mTitleBar;
	private View mTitleBar_leftButton;
	private LinearLayout clickNew = null;
	private int currentLength=0;//记录当前小区列表的长度。
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.v(TAG, "-------------initView-------");
		if (mContentView == null) {
			mContentView = inflater.inflate(R.layout.ac_added_cell, container,
					false);
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
		mTitleBar = (TitleBar) mContentView.findViewById(R.id.mTitleBar);
		mTitleBar_leftButton=mTitleBar.getLeftButtonView();
		initTitleLayout();
		mAddedCellList = (ListView) mContentView
				.findViewById(R.id.added_cell_list);
		// 从网络获取已加入区列表
		user_id = String.valueOf(AppContext.curUser().getUid());
		getCommunityList();
		setData();
		/*
		 * for (int i = 0; i < 5; i++) { CommunityBean addedCellModel = new
		 * CommunityBean(); addedCellModel.setId(1);
		 * addedCellModel.setName("京城雅居"); mAddedCellModels.add(addedCellModel);
		 * }
		 */

	}

	protected void initTitleLayout() {
		mTitleBar.setTitleName("已加入小区");
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0,
				R.drawable.selector_home_page_top_bar_left_button);
		// mTitleBar.initRightButtonTextOrResId(0,R.drawable.ic_home_page_top_bar_right_button);

		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((AppContext) getActivity().getApplication()).getHomeActivity()
						.toggle();
			}
		});
		/*
		 * mTitleBar.setRightButton1Listener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { ((AppContext)
		 * getActivity().getApplication()).getHomeActivity()
		 * .showSecondaryMenu(); } });
		 */
	}

	/**
	 * 获取已加入小区列表
	 * 
	 * @param user_id
	 */
	private void getCommunityList() {
		if (rsCommunity == null) {
			rsCommunity = new RSGetAddedCommunityList(user_id);
		}

		rsCommunity.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				mAddedCellModels.clear();
				// 返回成功，解析数据
				try {
					JSONArray resultArray = new JSONArray(result.toString());
					for (int i = 0; i < resultArray.length(); i++) {
						JSONObject communityObj = resultArray.optJSONObject(i);
						CommunityBean addedCellModel = new CommunityBean();
						addedCellModel.setCid(communityObj.optInt("id"));
						addedCellModel.setName(communityObj.optString("name"));
						addedCellModel.setTopicCount(communityObj.optInt("topic_num"));
						addedCellModel.setType(communityObj.optInt("type"));
						if(addedCellModel.getType() == 1){
							mAddedCellModels.add(addedCellModel);
						}
					}
					mAddedCellList.setAdapter(mCellAdapter);
					int newLength=mAddedCellModels.size();
					if(newLength>currentLength){
						if(currentLength!=0){
							//添加新小到本地小区列表							
							CommunityBean bean = mAddedCellModels.get(0);							
							AppContext.curUser().addNewCommunity(bean);
							// 设置刷新主页
							ApplicationFacade.getInstance().sendNotification(
									AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH);
						}							
					}
					currentLength=newLength;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LogUtils.e(TAG, result.toString());

			}
		});
		
		rsCommunity.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else if (ex instanceof ConnectTimeoutException) {
					showToast( "请求超时，请重试");
				}

				LogUtils.e(TAG, ex.toString());

			}
		});
		if (PublicUtils.isNetworkAvailable(getActivity())) {
			showProgressDialog("数据加载中...", true);
			rsCommunity.asyncExecute(getActivity(),this);
		}else{
			showToast("网络请求失败，请检查网络");
		}

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		// mTitleBar.setTitleName("已加入小区");
		// mTitleBar.setLeftButtonShow(true);
		// mTitleBar.initLeftButtonTextOrResId(0, R.drawable.common_back);
		// mTitleBar.initRightButtonTextOrResId("编辑", 0);

	}

	private void setData() {
		

		mCellAdapter = new AddedCellAdapter(getActivity(), this,
				mAddedCellModels);
		 View footView= LayoutInflater.from(getActivity()).inflate(
				R.layout.cell_foot_view, null);
		clickNew = (LinearLayout) footView.findViewById(R.id.click_new_layout);
		clickNew.setOnClickListener(new OnClickListener() {
			// 添加新小区
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), SelectCityActivity.class);
				MyCommunitiesFragment.this.startActivityForResult(intent,
						GOTOSELECTCITYACTIVITY);
			}
		});
		if(AppContext.curUser().getType()!=2){			
			mAddedCellList.addFooterView(footView);
		}

		// mAddedCellList.setAdapter(mCellAdapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			int user_id = AppContext.curUser().getUid();
			int community_id = data.getIntExtra("community_id", -1);
			int add_time = (int) (System.currentTimeMillis() / 1000);			

			// 添加新小区

			RSAddNewCommunity rsAddCommunity = new RSAddNewCommunity(user_id
					+ "", community_id + "", add_time + "");
			rsAddCommunity.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {// wu
					mAddedCellModels.clear();
					hideProgressDialog();


					getCommunityList();
					LogUtils.e(TAG, result.toString());
				}
			});
			rsAddCommunity.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					LogUtils.e(TAG, ex.toString());
					if (ex instanceof ADRemoteException) {
						showToast(((ADRemoteException) ex).msg());
					} else if (ex instanceof ConnectTimeoutException) {
						showToast("请求超时，请重试");
					}
					// Toast.makeText(getActivity(), "添加小区失败",
					// Toast.LENGTH_SHORT).show();
				}
			});
			if (PublicUtils.isNetworkAvailable(getActivity())) {
				showProgressDialog("请求发送中...", true);
				rsAddCommunity.asyncExecute(getActivity(),this);

			} else {
				showToast("网络连接异常，请检查网络");
			}

		}
	}

	// 删除小区
	public void deleteCommnity(final int community_id) {
		RSDeleteCommunity rsDeleteCommunity = new RSDeleteCommunity(user_id,
				community_id + "");
		rsDeleteCommunity.setOnSuccessHandler(new OnSuccessHandler() {
			@Override
			public void onSuccess(Object result) {
				mAddedCellModels.clear();
				//hideProgressDialog();
				AppContext.curUser().removeCommunity(community_id);
				getCommunityList();
				LogUtils.e(TAG, result.toString());

			}
		});
		rsDeleteCommunity.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				//hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}
				LogUtils.e(TAG, ex.toString());
			}
		});
		if (PublicUtils.isNetworkAvailable(getActivity())) {
			//showProgressDialog("请求发送中...", true);
			rsDeleteCommunity.asyncExecute(getActivity(),this);

		} else {
			showToast("网络连接异常，请检查网络后重试");
		}

	}

	@Override
	protected void setListener() {
		mTitleBar_leftButton.setOnTouchListener(this);
		clickNew.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.imavBack:
		case R.id.click_new_layout:
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				mCellAdapter.hideEditLayout();
			}
			break;

		default:
			break;
		}
		return false;
	}

}

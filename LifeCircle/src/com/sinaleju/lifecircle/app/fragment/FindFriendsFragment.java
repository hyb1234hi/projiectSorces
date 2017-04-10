package com.sinaleju.lifecircle.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.activity.IndexActivity;
import com.sinaleju.lifecircle.app.activity.SinaFriensAct;
import com.sinaleju.lifecircle.app.adapter.FindFriendRecognizedAdapter;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.cooperationaccount.sina.OAuthActivity;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.RecognizedFriendsBean;
import com.sinaleju.lifecircle.app.model.json.JSONParser_RecognizedFriends;
import com.sinaleju.lifecircle.app.model.json.JSONParser_RecognizedFriends2;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSConfirmComplete;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetRecognizedFriends;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSSearchFriends;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSUserInfo;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LCPageCounter2;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.slidingmenu.lib.SlidingMenu.OnOpenListener;

/**
 * 
 * @ClassName: FindFriendsFragment 
* @Description: TODO 寻找好友的fragment
* @author zhenwei 
* @date 2013-8-23 下午12:07:39 
*
 */
public class FindFriendsFragment extends BaseFragment implements
		OnClickListener {

	private View mContentView = null;

	private View mV_contactView;
	private View mV_nearView;
	private View mV_sinaView;
	private View tipsTv;
	private LinearLayout searchLinear;
	private View mIv_search;
	private View mIv_delete;
	private String mSearchContent;

	private View headerView;
	private TitleBar mTitleBar;
	private ListView listView;

	private LinearLayout searchBody;
	private EditText searchET;
	private Button delete, searchBtn;
	private PullToRefreshListView searchListView;

	public static final int AUTH_REQUEST_CODE = 1;
	public static final int INDEX_REQUEST_CODE = 2;

	protected static final String TAG = "FindFriendsFragment";
	private int userId;

	private RSGetRecognizedFriends rs;
	private ArrayList<RecognizedFriendsBean> listData;// 可能认识的人list列表
	private FindFriendRecognizedAdapter recognizedAdapter;

	private RSSearchFriends keySearchRs;
	private final int PAGE_SIZE = 10;//搜索结果每页的数量
	private int curPage;//搜索结果当前的页数
	private int totalPage;//搜索结果一共的页数
	private LCPageCounter2 counter = new LCPageCounter2(PAGE_SIZE);;
	
	private SharedPreferences sp;

	private ArrayList<RecognizedFriendsBean> searchResultListData;// 搜索结果的list列表
	private FindFriendRecognizedAdapter searchAdapter;
	
	public FindFriendsFragment() {
		super();
		
	}

	public FindFriendsFragment(int userId) {
		super();
		this.userId = userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.v(TAG, "-------------initView-------");
		if (mContentView == null) {
			mContentView = inflater.inflate(R.layout.ac_findfriends, container,
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

		headerView = View.inflate(getActivity(), R.layout.find_friends_header,
				null);

		tipsTv = headerView.findViewById(R.id.find_friends_tips_tv);
		mV_contactView = headerView.findViewById(R.id.findfriends_contact);
		mV_nearView = headerView.findViewById(R.id.findfriends_near);
		mV_sinaView = headerView.findViewById(R.id.findfriends_sina);
		searchLinear = (LinearLayout) headerView
				.findViewById(R.id.find_friends_search_linear);
		mIv_search = headerView.findViewById(R.id.findfriends_search);
		mIv_delete = headerView.findViewById(R.id.findfriends_delete);

		searchBody = (LinearLayout) mContentView
				.findViewById(R.id.find_friends_search_container);
		searchET = (EditText) mContentView
				.findViewById(R.id.search_et);
		delete = (Button) mContentView
				.findViewById(R.id.delete_btn);
		searchBtn = (Button) mContentView
				.findViewById(R.id.search_btn);
		searchListView = (PullToRefreshListView) mContentView
				.findViewById(R.id.find_friends_search_listview);

		mTitleBar = (TitleBar) mContentView
				.findViewById(R.id.find_friends_mTitleBar);
		listView = (ListView) mContentView
				.findViewById(R.id.find_friends_listview);
		listView.addHeaderView(headerView);

		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_home_page_top_bar_left_button);
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setTitleName("寻找好友");

		loadRecognizedPersons();

	}

	@Override
	protected void initData() {			
		
		sp = getActivity().getSharedPreferences("userinfo", 0);
		searchET.setHint("输入昵称查找");
	}

	private void getBindingState() {
		RSUserInfo rsUserInfo = new RSUserInfo(userId);
		rsUserInfo.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				UserBean curUser = AppContext.curUser();
				JSONObject dataObj;
				try {
					dataObj = new JSONObject(result.toString());
					JSONArray platformArray = dataObj
							.optJSONArray("platforminfo");

					int platformLength = platformArray.length();
					if (platformArray != null && platformLength > 0) {
						for (int i = 0; i < platformLength; i++) {
							JSONObject platformObj = platformArray
									.getJSONObject(i);
							int isDel = platformObj.optInt("is_del");
							if (isDel == 0) {
								String param = platformObj.optString("param");
								JSONObject paramObj = new JSONObject(param);
								curUser.setToken(paramObj
										.getString("access_token"));
								curUser.setExpiresTime(paramObj
										.getString("expires_in"));
								curUser.setPlatform_id(platformObj.optString("platform_id"));
								curUser.setPlatform_name(platformObj.optString("platform_name"));
								curUser.setIsBindingSina(true);
							}else{
								curUser.setIsBindingSina(false);
							}
						}
					}else{
						curUser.setIsBindingSina(false);
					}
					getSinaFriends();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		rsUserInfo.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
					
				} else if (ex instanceof ConnectTimeoutException) {
					showToast( "请求超时，请重试");
					
				}

			}
		});
		if (PublicUtils.isNetworkAvailable(getActivity())) {
			showProgressDialog("数据获取中...", false);
			rsUserInfo.asyncExecute(getActivity(),this);
		} else {
			showToast( "网络异常，请检查网络");
		}
	}

	@Override
	protected void setListener() {
		mTitleBar.setLeftButtonListener(this);
		mV_contactView.setOnClickListener(this);
		mV_nearView.setOnClickListener(this);
		mV_sinaView.setOnClickListener(this);
		mIv_delete.setOnClickListener(this);
		mIv_search.setOnClickListener(this);
		searchLinear.setOnClickListener(this);
		delete.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		searchBody.setOnClickListener(this);
		

		searchET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!TextUtils.isEmpty(s.toString())){
					counter.setCurPage(0);
					searchFriendsByKey(s.toString(), false);
				}
			}
		});
		((HomeActivity)getActivity()).getSlidingMenu().setOnOpenListener(new OnOpenListener() {
			
			@Override
			public void onOpen() {
				if(null != getActivity())ADSoftInputUtils.hide(getActivity());
			}
		});
		
		searchListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				
				if(counter.isMax()){//最后一页了
					showToast("已经是最后一页了！");
					comlpeteRefresh();
				}else{
					searchFriendsByKey(searchET.getText().toString().replace(" ", ""), false);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imavBack:
			((AppContext) getActivity().getApplication()).getHomeActivity()
					.toggle();
			break;
		case R.id.search_btn:
			String searchKey = searchET.getText().toString().replace(" ", "");

			if (!TextUtils.isEmpty(searchKey)
					&& (null == searchListView.getTag() || (null != searchListView
							.getTag() && !searchKey.equals(String
							.valueOf(searchListView.getTag()))))) {
				counter.setCurPage(0);
				
				searchFriendsByKey(searchET.getText().toString(), true);
			} else if (!TextUtils.isEmpty(searchKey)
					&& (null != searchListView.getTag() && searchKey
							.equals(String.valueOf(searchListView.getTag())))) {
				if (null == searchResultListData
						|| searchResultListData.size() == 0)
					showToast("没有搜到结果！");
			} else if (TextUtils.isEmpty(searchKey)) {
				searchResultListData = null;
				searchListView.setAdapter(null);
			}
			break;
		case R.id.find_friends_search_linear:
			if (searchBody.getVisibility() == View.GONE) {
				searchET.setText("");
				searchET.requestFocus();
				searchResultListData = null;
				searchListView.setAdapter(null);
				searchBody.setVisibility(View.VISIBLE);
				showInput(searchET);
			}
			break;

		case R.id.findfriends_contact:
			goToFriendsList(SinaFriensAct.FROM_CONTACT_INDEX);
			break;
		case R.id.findfriends_near:

			break;
		case R.id.findfriends_sina:
			//获取绑定状态
			getBindingState();	
			goToFriendsList(SinaFriensAct.FROM_SINA_INDEX);
			break;
		case R.id.delete_btn:
			searchET.setText("");
			break;
		case R.id.find_friends_search_container:
			/*
			 * searchBody.setVisibility(View.GONE); hideInput(searchBody);
			 */
			break;
		default:
			break;
		}
	}
	
	//获取联系人好友信息。
	private void goToFriendsList(int index) {
		Intent intent = null;
		if (index == SinaFriensAct.FROM_CONTACT_INDEX) {
			intent = new Intent(getActivity(), SinaFriensAct.class);
			intent.putExtra(RightHomeFragment.USER_ID_KEY, userId);
			intent.putExtra("index", index);
			startActivity(intent);
		} /*else {
			// Oauth2AccessToken accessToken =
			// AccessTokenKeeper.readAccessToken(getActivity());
			// String token = accessToken.getToken();
			// 判断当前账户是否绑定微博账户
			getSinaFriends(index);
		}*/

	}
	
	//获取新浪微博好友信息
	private void getSinaFriends() {
		Intent intent;
		boolean isBinding = AppContext.curUser().getIsBindingSina();
		if (!isBinding) {
			intent = new Intent(getActivity(), OAuthActivity.class);
			startActivityForResult(intent, AUTH_REQUEST_CODE);
		} else {
			intent = new Intent(getActivity(), SinaFriensAct.class);
			intent.putExtra(RightHomeFragment.USER_ID_KEY, userId);
			intent.putExtra("index", SinaFriensAct.FROM_SINA_INDEX);
			startActivity(intent);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == AUTH_REQUEST_CODE
				&& resultCode == Activity.RESULT_OK) {
			onActivityResultFromAuth();
		} else if (requestCode == INDEX_REQUEST_CODE
				&& resultCode == IndexActivity.SUCESS_RESULT_CODE
				&& null != data) {
			onActivityResultFromIndexAct(data);
		}
	}

	private void onActivityResultFromAuth() {
		String user_id = String.valueOf(AppContext.curUser().getUid());
		final String platform_name = sp.getString("origin", "");
		final String platform_id = sp.getString("uid", "");
		String param = sp.getString("param", "");
		// 绑定微博账号
		/*
		 * if(SinaBindingUtils.bindingSina(getActivity())){ Intent intent = new
		 * Intent(getActivity(), SinaFriensAct.class);
		 * intent.putExtra(RightHomeFragment.USER_ID_KEY, userId);
		 * startActivity(intent); }else{ Toast.makeText(getActivity(),
		 * "绑定新浪微博失败", Toast.LENGTH_SHORT) .show(); }
		 */
		RSConfirmComplete rsBinding = new RSConfirmComplete(platform_id,
				platform_name, param, String.valueOf(user_id), "2");
		rsBinding.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				UserBean curUser = AppContext.curUser();
				String obj = result.toString();
				JSONObject jsonObjet = null;
				try {
					jsonObjet = new JSONObject(obj);
					String sResult = jsonObjet.optString("result");
					if (sResult.equals("1")) {
						// 当前微博账号已绑定过其它账号：即绑定失败。
					//	showToast( jsonObjet.optString("message"));						
						Object dataObj=jsonObjet.opt("data");
						if(dataObj instanceof String){
							//可以绑定
							curUser.setToken(sp.getString("token", ""));
							curUser.setExpiresTime(sp.getString("expirestime", ""));
							curUser.setPlatform_id(platform_id);
							curUser.setPlatform_name(platform_name);
							curUser.setIsBindingSina(true);
							LogUtils.e(TAG, "plantform:  "+AppContext.curUser().getPlatform_id());
							Intent intent = new Intent(getActivity(),
									SinaFriensAct.class);
							intent.putExtra(RightHomeFragment.USER_ID_KEY, userId);
							startActivity(intent);
						}

					} else {
						showToast( jsonObjet.optString("message"));
						
						/*// 绑定成功 // 更新用户信息
						curUser.setToken(sp.getString("token", ""));
						curUser.setExpiresTime(sp.getString("expirestime", ""));
						curUser.setPlatform_id(sp.getString("platform_id", ""));
						curUser.setPlatform_name(sp.getString("platform_name",
								""));
						curUser.setIsBindingSina(true);
						Intent intent = new Intent(getActivity(),
								SinaFriensAct.class);
						intent.putExtra(RightHomeFragment.USER_ID_KEY, userId);
						startActivity(intent);*/
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		rsBinding.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
					
				} else if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}

				LogUtils.e(TAG, ex.toString());
			}
		});
		if (PublicUtils.isNetworkAvailable(getActivity())) {
			showProgressDialog("连接中...", true);
			rsBinding.asyncExecute(getActivity(),this);
		}else{
			showToast("网络连接失败，请检查网络");
		}
	}

	private void onActivityResultFromIndexAct(Intent data) {
		int status = data.getIntExtra("follow", -1);
		int mVisitor_Id = data.getIntExtra("mVisitor_Id", -1);
		// 重置一下status indexAct中的status中0代表取消关注 1代表已关注 2代表互相关注， 而本act中0未关注 1 已关注
		if (status == 2)
			status = 1;

		if (status != -1 && mVisitor_Id != -1) {
			int oldStatus;
			if (null != searchResultListData && searchResultListData.size() > 0
					&& null != searchAdapter) {
				for (int i = 0; i < searchResultListData.size(); i++) {
					oldStatus = Integer.valueOf(searchResultListData.get(i)
							.getStatus());
					if (searchResultListData.get(i).getId()
							.equals(String.valueOf(mVisitor_Id))
							&& oldStatus != status) {
						searchResultListData.get(i).setStatus(
								String.valueOf(status));
						searchAdapter.notifyDataSetChanged();
						break;
					}
				}
			}

			RecognizedFriendsBean bean = new RecognizedFriendsBean();
			bean.setId(String.valueOf(mVisitor_Id));
			bean.setStatus(String.valueOf(status));

			updateFollowStatus(bean);
		}
	}

	//加载可能认识的人
	private void loadRecognizedPersons() {
		if (null != rs)
			return;
		showProgressDialog(getString(R.string.loading_data), false);

		rs = new RSGetRecognizedFriends();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", String.valueOf(userId));
		rs.setParams(params);

		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				if (null != result) {
					listData = JSONParser_RecognizedFriends.parser(result
							.toString());
					fillData(listData);
				}
			}
		});

		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				showToast(getString(R.string.net_error));
				hideProgressDialog();
				listView.setAdapter(null);
			}
		});

		rs.asyncExecute(getActivity(),this);
	}

	@Override
	protected void cancelTask() {
		if (null != rs)
			rs.cancel();
		listView.setAdapter(null);
	}

	private void fillData(ArrayList<RecognizedFriendsBean> listData) {
		if (listData == null || listData.size() == 0) {
			showToast("没有有可能认识的人！");
			listView.setAdapter(null);
		} else {
			recognizedAdapter = new FindFriendRecognizedAdapter(
					FindFriendRecognizedAdapter.RECONIZED_ADAPTER, this,
					getActivity(), listData, String.valueOf(userId));
			listView.setAdapter(recognizedAdapter);
		}

	}

	//搜索人
	private void searchFriendsByKey(final String key, final boolean isShowDialog) {
		if (isShowDialog)
			showProgressDialog(getString(R.string.searching_data), false);

		if (null != keySearchRs && !keySearchRs.isCanceled())
			keySearchRs.cancel();

		keySearchRs = new RSSearchFriends();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("keyword", key.replace(" ", ""));
		params.put("user_id", String.valueOf(userId));
		params.put("page_size", String.valueOf(counter.getSize()));
		params.put("page", String.valueOf(counter.getNextStartPage()));

		keySearchRs.setParams(params);
		keySearchRs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				// TODO Auto-generated method stub
				hideProgressDialog();
				if (null != result) {
					ArrayList<RecognizedFriendsBean> listData = JSONParser_RecognizedFriends2
							.parse(result.toString(), counter);
					
					searchListView.setTag(key);
					// fillData(listData);
					if (counter.getCurPage() == 1) {//是第一页数据
						searchResultListData = listData;
						if(null == listData || listData.size() ==0){
							if (isShowDialog)	showToast("没有搜到结果！");
							searchListView.setAdapter(null);
						}else{
							searchAdapter = new FindFriendRecognizedAdapter(
									FindFriendRecognizedAdapter.SEARCH_RECONIZED_ADAPTER,
									FindFriendsFragment.this, getActivity(),
									searchResultListData, String.valueOf(userId));
							searchListView.setAdapter(searchAdapter);
						}
						
					} else {//不是第一页
						comlpeteRefresh();
						if(null != listData) searchResultListData.addAll(listData);
						searchAdapter.notifyDataSetChanged();
					}
				}
			}
		});

		keySearchRs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				comlpeteRefresh();
				
				if (ex instanceof ADRemoteException) {
					if (isShowDialog)
						showToast(((ADRemoteException) ex).msg());
					searchListView.setTag(key);
				} else {
					showToast(R.string.net_error);
				}
				searchResultListData = null;
				searchListView.setAdapter(null);
				hideProgressDialog();
			}
		});

		keySearchRs.asyncExecute(getActivity(),this);
	}

	private void comlpeteRefresh(){
		mHandler.sendEmptyMessageDelayed(0, 100);
	}
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			searchListView.onRefreshComplete();
		}
		
	};
	
	private void showInput(View view) {
		try {
			InputMethodManager inputMethodManager = ((InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE));
			if (inputMethodManager.isActive()) {// 此判断没什么用，有好的方法再替换吧
				inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
//				inputMethodManager.showSoftInput(view,
//						InputMethodManager.RESULT_SHOWN);
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 当通过搜索关键字搜索到的结果加关注后，在可能认识的人中的列表中也更新一下关注状态
	 */
	public void updateFollowStatus(RecognizedFriendsBean bean) {
		if (null != listData && listData.size() > 0) {
			for (int i = 0; i < listData.size(); i++) {
				if (listData.get(i).getId().equals(bean.getId())
						&& !listData.get(i).getStatus()
								.equals(bean.getStatus())) {
					listData.get(i).setStatus(bean.getStatus());
					if (null != recognizedAdapter)
						recognizedAdapter.notifyDataSetChanged();
					break;
				}
			}
		}
	}

	public boolean onBackPressed() {
		if (searchBody.getVisibility() == View.VISIBLE) {
			searchBody.setVisibility(View.GONE);
			return true;
		}

		return false;
	}

}

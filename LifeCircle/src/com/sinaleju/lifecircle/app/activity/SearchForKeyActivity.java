package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.CustomSearchBarView;
import com.sinaleju.lifecircle.app.customviews.CustomSearchBarView.SearchMode;
import com.sinaleju.lifecircle.app.dialog.VisitingCardDialog;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.RecognizedFriendsBean;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_RecognizedFriends2;
import com.sinaleju.lifecircle.app.model.json.JSONParser_Trends;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAddFollower;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSSearchFriends;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSSearchMessage;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.LCPageCounter2;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;
import com.umeng.analytics.MobclickAgent;

public class SearchForKeyActivity extends BaseActivity {

	protected static final String TAG = "SearchForKeyActivity";

	// views
	private PullToRefreshListView mListView;
	private EditText mSearchEdit;
	private CustomSearchBarView mSearchBar;
	
//	private RadioGroup rgSearch = null;
	private Button rb_all = null;
	private Button rb_follow = null;
	private Button rb_auth = null;

	// private boolean mIsSearchingForPeople;

	private RSSearchMessage mRSSearchMessage;
	private LCPageCounter2 mMessagePageCounter = new LCPageCounter2(10);
	private LCPageCounter2 mPeoplePageCounter = new LCPageCounter2(10);
	private MultiModelsSet mSet = new MultiModelsSet(20, new JSONParser_Trends());
	private MultiTypesAdapter mMessageAdapter = new MultiTypesAdapter(mSet, this);
	private View mEmptyView;
	
	private int user_t = 0;//是否关注  0 全部  1  未关注 2 已关注
	private int audit_type = 0;//是否认证 0 未认证 1 已认证
	
	private RSSearchFriends mSearchPeopleRS;
	private ArrayList<RecognizedFriendsBean> mSearchPeopleDataList = new ArrayList<RecognizedFriendsBean>();
	private PeopleAdapter mPeopleAdapter;
	private boolean isFinish = false;//判断数据是否加载完成
	private int current_index = R.id.rb_all;//记录当前点击的Button

	
	public int getUser_t() {
		return user_t;
	}

	public void setUser_t(int user_t) {
		this.user_t = user_t;
	}

	public int getAudit_type() {
		return audit_type;
	}

	public void setAudit_type(int audit_type) {
		this.audit_type = audit_type;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.ac_search_for_key;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		//
		initSearchBar();
		// initTitlelayout
		initTitlelayout();
		// initListView
		initListView();
		// init others
		initOtherViews();

		firstRequest();
	}

	private void initFromIntent() {
		Intent intent = getIntent();
		mSearchEdit.setText(intent.getStringExtra("text"));
		mSearchBar.setSearchMode((SearchMode) intent.getSerializableExtra("searchMode"));
	}
	
	/**
	 * 
	 */
	private void initSearchBar() {
		mSearchBar = (CustomSearchBarView) findViewById(R.id.searchBar);
		mSearchEdit = mSearchBar.getmEditText();

		mSearchBar.setType(1);
	}

	/**
	 * 
	 */
	private void initListView() {
		mListView = (PullToRefreshListView) findViewById(R.id.list);
		mListView.setAdapter(mMessageAdapter);
	}

	private void firstRequest() {
		// setIsSearchingForPeople(false);
		LogUtils.i(TAG, "firstRequest");
		doRequestOrRefresh();
	}

	/**
	 * 
	 */
	private void doRequestOrRefresh() {
		LogUtils.i(TAG, "doRequestOrRefresh");
		mListView.setMode(Mode.PULL_FROM_START);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// mSet.setRefresh();
				mListView.setRefreshing();
			}
		}, 300);
	}

	@Override
	protected void initData() {
		initFromIntent();
	}
	
	private void setDefaultBg(){
		rb_all.setBackgroundResource(R.drawable.selector_bg);
		rb_auth.setBackgroundResource(R.drawable.selector_bg);
		rb_follow.setBackgroundResource(R.drawable.selector_bg);
	}
	private void setAllSelectBg(){
		rb_all.setBackgroundResource(R.drawable.selector_bg_1);
	}
	private void setAuthSelectBg(){
		rb_auth.setBackgroundResource(R.drawable.selector_bg_1);
	}
	private void setFollowSelectBg(){
		rb_follow.setBackgroundResource(R.drawable.selector_bg_1);
	}

	@Override
	protected void initCallbacks() {
		LogUtils.i(TAG, "initCallbacks");
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				LogUtils.i(TAG, "onPullDownToRefresh");
				isFinish = false;
				if (isSearchingForPeople()) {
					requestForPeople();
				} else {
					requestForMessages();
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				LogUtils.i(TAG, "onPullUpToRefresh");
				isFinish = false;
				if (isSearchingForPeople()) {
					requestForPeople();
				} else {
					requestForMessages();
				}
			}
		});

		mSearchBar.setSearchOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LogUtils.i(TAG, "mSearchBar    setSearchOnClickListener");
				if(isNull()){
					isFinish = true;
					mSearchEdit.setText("");
					showToast("请输入搜索内容！");
					return;
				}else {
					setRefresh();
					doRefresh();
				}
			}
		});
		
		rb_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogUtils.e(TAG, "rb_all   isFinish : " + isFinish);
				if(!isFinish){
					return;
				}else{
					if(current_index == R.id.rb_all){
						return;
					}
					current_index = R.id.rb_all;
					setDefaultBg();
					setAllSelectBg();
					mMessageAdapter = null;
					mSet = null;
					mSet = new MultiModelsSet(20, new JSONParser_Trends());
					mSet.totallyClear();
					mMessageAdapter = new MultiTypesAdapter(mSet, SearchForKeyActivity.this);
					//全部
					setAudit_type(0);
					setUser_t(0);
					setRefresh();
					doRefresh();
				}
			}
		});
		
		rb_follow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogUtils.e(TAG, "rb_all   rb_follow : " + isFinish);
				//我关注的
				if(!isFinish){
					return;
				}else{
					if(current_index == R.id.rb_follow){
						return;
					}
					current_index = R.id.rb_follow;
					mMessageAdapter = null;
					mSet = null;
					mSet = new MultiModelsSet(20, new JSONParser_Trends());
					mSet.totallyClear();
					mMessageAdapter = new MultiTypesAdapter(mSet, SearchForKeyActivity.this);
					setDefaultBg();
					setFollowSelectBg();
					if(isSearchingForPeople()){
						setAudit_type(0);
						setUser_t(1);
						setRefresh();
						doRefresh();
					} else {
						setAudit_type(0);
						setUser_t(2);
						setRefresh();
						doRefresh();
					}
				}
			}
		});
		
		rb_auth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogUtils.e(TAG, "rb_all   rb_auth : " + isFinish);
				//认证用户
				if(!isFinish){
					return;
				}else{
					if(current_index == R.id.rb_auth){
						return;
					}
					current_index = R.id.rb_auth;
					
					setDefaultBg();
					setAuthSelectBg();
					mSet = null;
					mSet = new MultiModelsSet(20, new JSONParser_Trends());
					mSet.totallyClear();
					mMessageAdapter = new MultiTypesAdapter(mSet, SearchForKeyActivity.this);
					if(isSearchingForPeople()){
						setAudit_type(2);
						setUser_t(0);
						setRefresh();
						doRefresh();
					} else {
						setAudit_type(2);
						setUser_t(0);
						setRefresh();
						doRefresh();
					}
				}
			}
		});
	}

	private void initOtherViews() {
		mEmptyView = findViewById(R.id.emptyView);
//		rgSearch = (RadioGroup) findViewById(R.id.rg_search);
		
		rb_all = (Button)findViewById(R.id.rb_all);
		setAllSelectBg();//默认设置点击All
		rb_follow = (Button)findViewById(R.id.rb_follow);
		rb_auth = (Button)findViewById(R.id.rb_auth);
	}

	public void initTitlelayout() {
		mTitleBar.setTitleName("搜索");
		mTitleBar.showBackButton();
		mTitleBar.setTitleNameSize(24.f);
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PublicUtils.hideSoftInputMethod(mActivity);
				finish();
			}
		});
	}
	/**
	 * requestForMessages 请求搜索消息
	 */
	private void requestForMessages() {
		if (mRSSearchMessage != null) {
			LogUtils.e(TAG, "mRSSearchMessage != null");
			isFinish = true;
			stopPullRefreshAnimation();
			return;
		}

		if (mMessagePageCounter.isMax()) {
			isFinish = true;
			showToast("数据已全部加载完毕");
			stopPullRefreshAnimation();
			return;
		}

		decaution();
		mRSSearchMessage = new RSSearchMessage(AppContext.isVisitor() ? AppContext.curVisitor().getCommunity_id() : AppContext.curUser().getCurrentCommunityId(), getKeyword(), AppContext.curUser().getUid(), mMessagePageCounter.getNextStartPage(),
				mMessagePageCounter.getSize(), getUser_t(), getAudit_type());
		mRSSearchMessage.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				isFinish = true;
				LogUtils.e(TAG, "mRSSearchMessage   onSuccess : " + isFinish);
				
				stopPullRefreshAnimation();
				mRSSearchMessage = null;

				if (result == null) {
					return;
				}

				String json = result.toString();
				try {
					JSONObject obj = new JSONObject(json);
					int c_page = obj.optInt("curPage");
					int t_page = obj.optInt("totalPage");

					mMessagePageCounter.setCurPage(c_page);
					mMessagePageCounter.setTotalPage(t_page);
				} catch (JSONException e) {
					LogUtils.e(TAG, "", e);
				}

				if (mSet.add(json)) {
					if (mListView.getRefreshableView().getAdapter() == mMessageAdapter) {
						LogUtils.v(TAG, "mMessageAdapter  11111");
						mMessageAdapter.notifyDataSetChanged();
					} else {
						LogUtils.v(TAG, "mMessageAdapter  22222");
//						mMessageAdapter = new MultiTypesAdapter(mSet, SearchForKeyActivity.this);
						mListView.setAdapter(mMessageAdapter);
					}
				} else {
					caution();
				}

			}
		});

		mRSSearchMessage.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				LogUtils.e(TAG, "mRSSearchMessage   setOnFaultHandler : " + isFinish);
				isFinish = true;
				stopPullRefreshAnimation();
				mRSSearchMessage = null;
				caution();
			}
		});

		mRSSearchMessage.asyncExecute(this);
	}

	/**
	 * requestForPeople 请求搜索用户
	 */
	private void requestForPeople() {
		if (mSearchPeopleRS != null) {
			LogUtils.e(TAG, "mSearchPeopleRS != null");
			isFinish = true;
			stopPullRefreshAnimation();
			return;
		}

		decaution();

		if (mPeoplePageCounter.isMax()) {
			isFinish = true;
			showToast("数据已全部加载完毕");
			stopPullRefreshAnimation();
			return;
		}
		mSearchPeopleRS = new RSSearchFriends(AppContext.isVisitor() ? -1 : AppContext.curUser().getUid(), getKeyword(),getUser_t(),getAudit_type());

		mSearchPeopleRS.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				isFinish = true;
				LogUtils.e(TAG, "mSearchPeopleRS   onSuccess : " + isFinish);
				stopPullRefreshAnimation();

				mSearchPeopleRS = null;

				if (result == null) {
					return;
				}
				ArrayList<RecognizedFriendsBean> temp = JSONParser_RecognizedFriends2.parse(result.toString(), mPeoplePageCounter);

				if (temp != null && temp.size() > 0) {
					mSearchPeopleDataList.addAll(temp);
					if (mPeopleAdapter == null) {
						mPeopleAdapter = new PeopleAdapter(mSearchPeopleDataList);
						mListView.setAdapter(mPeopleAdapter);
					} else {
						if (mListView.getRefreshableView().getAdapter() != mPeopleAdapter) {
							mListView.setAdapter(mPeopleAdapter);
						} else {
							mPeopleAdapter.notifyDataSetChanged();
						}
					}
				} else {
					caution();
				}
			}
		});

		mSearchPeopleRS.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				isFinish = true;
				LogUtils.e(TAG, "mSearchPeopleRS   onSuccess : " + isFinish);
				stopPullRefreshAnimation();
				mSearchPeopleRS = null;
				caution();
			}
		});

		mSearchPeopleRS.asyncExecute(this);
	}
	private String getKeyword() {
		return mSearchEdit.getText().toString();
	}
	
	private boolean isNull(){
		if(getKeyword() == null || getKeyword().equals("")){
			return true;
		}
		return false;
	}

	private static final int MSG_SET_REFRESH_COMPLETE = 1;
	protected static final int MSG_SWITCH_MODE_FROM_END = 11;

	/**
	 * 
	 */
	private void stopPullRefreshAnimation() {
		mHandler.sendEmptyMessageDelayed(MSG_SET_REFRESH_COMPLETE, 100);
	}

	/**
	 * 
	 */
	public void forceRefreshListViewComplete() {
		mListView.onRefreshComplete();
		setPullModeEnd();
	}

	/**
	 * 
	 */
	private void setPullModeEnd() {
		mHandler.sendEmptyMessageDelayed(MSG_SWITCH_MODE_FROM_END, 200);
	}

	private void caution() {
		mListView.setVisibility(View.INVISIBLE);
		mEmptyView.setVisibility(View.VISIBLE);
	}

	private void decaution() {
		mListView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.INVISIBLE);
	}

	/**
	 * @return
	 */
	private boolean isSearchingForPeople() {
		return mSearchBar.getSearchMode() == SearchMode.PEOPLE;
	}

	/**
	 * 
	 */
	private void setRefresh() {
		if (isSearchingForPeople()) {
			mSearchPeopleDataList.clear();
			mPeoplePageCounter.reset();
		} else {
			mSet.refresh();
			mSet.setRefresh();
			mMessagePageCounter.reset();
		}
	}

	/**
	 * 
	 */
	private void doRefresh() {
		mListView.setMode(Mode.PULL_FROM_START);
		mListView.setRefreshing();//追溯到刷新接口
	}

	/**
	 * ADHandler
	 */
	private ADHandler<SearchForKeyActivity> mHandler = new ADHandler<SearchForKeyActivity>(this, new MessageHandler<SearchForKeyActivity>() {

		@Override
		public void handleMessage(SearchForKeyActivity a, Message msg) {
			if (msg.what == MSG_SET_REFRESH_COMPLETE) {
				a.forceRefreshListViewComplete();
			} else if (msg.what == MSG_SWITCH_MODE_FROM_END) {
				mListView.setMode(Mode.PULL_FROM_END);
			}
		}
	});

	/**
	 * 
	 * @author dan_alan
	 * 
	 */
	private class PeopleAdapter extends BaseAdapter {

		private ArrayList<RecognizedFriendsBean> mData;

		public PeopleAdapter(ArrayList<RecognizedFriendsBean> data) {
			this.mData = data;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public RecognizedFriendsBean getItem(int i) {
			return mData.get(i);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int pos, View convertView, ViewGroup arg2) {
			LogUtils.e(TAG, "getView  :: " + mData.size());
			ViewHolder holder = null;
			if (convertView == null) {

				// view
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_people, null);

				// holder
				holder = new ViewHolder();
				holder.add = (ImageView) convertView.findViewById(R.id.imgAdd);
				holder.added = (TextView) convertView.findViewById(R.id.txtAdded);
				holder.desc = (TextView) convertView.findViewById(R.id.desc);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.head = (ImageView) convertView.findViewById(R.id.imgHeader);

				// set tag
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			RecognizedFriendsBean bean = getItem(pos);

			// header
			setHeader(holder, bean);

			// name
			String name = bean.getDisplay_name();
			holder.name.setText(name);

			// desc
			String desc = "";
			if(bean.getCommon() == null || bean.getCommon().equals("") || bean.getCommon().equals("0")){
				desc = "";
			}else {
				desc = bean.getCommon() + "个共同好友";
			}
			holder.desc.setText(desc);

			// added
			setAdder(holder, bean);

			return convertView;
		}

		/**
		 * @param holder
		 * @param bean
		 */
		private void setAdder(final ViewHolder holder, final RecognizedFriendsBean bean) {
			String status = bean.getStatus();
			LogUtils.e(TAG, "status :: " + status);
			if (status != null && !status.equals("")) {
				int st = Integer.valueOf(status);
				LogUtils.e(TAG, "st :: " + st);
				if (st == 1) {
					holder.add.setVisibility(View.GONE);
					holder.added.setVisibility(View.VISIBLE);
					holder.add.setOnClickListener(null);
				} else {
					holder.add.setVisibility(View.VISIBLE);
					holder.added.setVisibility(View.GONE);
					holder.add.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							holder.added.setEnabled(false);

							RSAddFollower rs = new RSAddFollower(AppContext.curUser().getUid(), bean.getId(), "1");

							rs.setOnSuccessHandler(new OnSuccessHandler() {

								@Override
								public void onSuccess(Object result) {
									// hideProgressDialog();
									Toast.makeText(mContext, "加关注成功！", Toast.LENGTH_LONG).show();
									bean.setStatus("2");// 置成已加入关注的状态
									holder.added.setEnabled(true);
									holder.add.setVisibility(View.GONE);
									holder.added.setVisibility(View.VISIBLE);
								}
							});
							rs.setOnFaultHandler(new OnFaultHandler() {

								@Override
								public void onFault(Exception ex) {
									holder.added.setEnabled(true);

									// hideProgressDialog();
									if (ex instanceof ADRemoteException) {
										Toast.makeText(mContext, ((ADRemoteException) ex).msg(), Toast.LENGTH_LONG).show();
									} else {
										Toast.makeText(mContext, "加关注失败！", Toast.LENGTH_LONG).show();
									}
								}
							});

							rs.asyncExecute(mContext);
						}
					});
				}
			}
		}

		/**
		 * @param holder
		 * @param bean
		 */
		private void setHeader(ViewHolder holder, final RecognizedFriendsBean bean) {
			String url = bean.getHeader();
			if(bean.getType() == null || bean.getType().equals("")){
				bean.setType("0");
			}
			int res = PublicUtils.getUserDefaultHeadImage(Integer.valueOf(bean.getType()));
			holder.head.setImageResource(res);
			if (url != null && !url.equals("")) {
				ImageLoader.getInstance(SearchForKeyActivity.this).displayImage(url, holder.head, SimpleImageLoaderOptions.getRoundImageOptions(0, 100));
			}

			holder.head.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					VisitingCardDialog.pop(mContext, Integer.valueOf(bean.getId()), Integer.valueOf(bean.getType()));
				}
			});
		}

	}

	private static class ViewHolder {
		ImageView head;
		TextView name;
		TextView desc;
		TextView added;
		ImageView add;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SearchForKeyActivity");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SearchForKeyActivity");
	}

}

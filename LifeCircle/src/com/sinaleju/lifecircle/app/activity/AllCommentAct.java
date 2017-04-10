package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.adapter.ServiceDetailCommentAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.fragment.RightHomeFragment;
import com.sinaleju.lifecircle.app.model.EviewModel;
import com.sinaleju.lifecircle.app.model.json.JSONParser_AllComment;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAllComment;

/**
 * 
 * @author leju
 *所有评论列表页
 */
public class AllCommentAct extends BaseActivity implements OnClickListener{

	//table_choose
	public static final int TABLE_CHOOSE_USER = 1;
	public static final int TABLE_CHOOSE_SERVICE = 2;
	
	//row_id
	public static final int ROW_ID_ONE = 1;
	public static final int ROW_ID_TWO = 2;
	
	private PullToRefreshListView listView;
	
	private RSAllComment rs;
	private ArrayList<EviewModel> listData;
	
	private int table_choose;//评价对象： 1-用户（商家、物业） 2-公共服务
	private int row_id;//若table_choose为1，则表示用户ID；若table_choose为2, 则表示公共服务ID
	private int last_index_id;//last_index_id	int	否	上次查询评价记录的最小索引ID，默认为0，从第一条开始查询
	private final int SIZE = 10;//查询条数，默认为5
	private int surplus = 0;//剩余条数
	
	private int user_id;
	
	private ServiceDetailCommentAdapter adapter;
	
	
	private boolean isFirstLoaded = false;
	
	public final int REQUEST_CODE = 1;
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.all_comment;
	}

	@Override
	protected void initView() {
		
		listView = (PullToRefreshListView)findViewById(R.id.all_comment_listview);
		
		user_id = getIntent().getIntExtra(RightHomeFragment.USER_ID_KEY, RightHomeFragment.INVALIDATE_USER_ID);
		
		table_choose = getIntent().getIntExtra("table_choose", TABLE_CHOOSE_USER);
		row_id = getIntent().getIntExtra("row_id", ROW_ID_ONE);
		
		mTitleBar.setTitleName(R.string.comment_title);
		mTitleBar.initRightButtonTextOrResId("", R.drawable.commit_comment_btn);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);
		
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);
		
		firstLoadingData(getString(R.string.loading_data));
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initCallbacks() {
		/*listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lastItemCount = firstVisibleItem + visibleItemCount;
				if(null != listData && surplus != 0 && lastItemCount == listData.size()){
					loadData(null);
				}
			}
		});*/
		
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
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.imavBack:
			finish();
			break;
			
		case R.id.tvRightFunc1:
			if(RightHomeFragment.INVALIDATE_USER_ID == user_id) showToast("不能进行评论，请先进行登录！！");
			else{
				Intent intent = new Intent(AllCommentAct.this, BusinessEviewActivity.class);
				intent.putExtra("table_choose", table_choose);
				intent.putExtra("row_id", row_id);
				intent.putExtra(RightHomeFragment.USER_ID_KEY, user_id);
				startActivityForResult(intent, REQUEST_CODE);
			}
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 601){
			last_index_id = 0;
			listData = null;
			adapter = null;
			//listView.setAdapter(adapter);
			rs = null;
			loadData(true);
		}
	}

	private void firstLoadingData(String loadingData){//第一次进来加载数据
		showProgressDialog(loadingData, true);
		last_index_id = 0;
		loadData(true);
	}
	
	private void pullUpLoadingData(){//上拉刷新加载数据
		if(surplus == 0){
			showToast(R.string.no_more);
			comlpeteRefresh();
			return;
		}
		loadData(false);
	}
	
	private void pullDownLoadingData(){//下拉刷新加载数据
		last_index_id = 0;
		loadData(true);
	}
	
	private void loadData(final boolean isReloading){
		if(rs != null){
			comlpeteRefresh();
			return;
		}
		
//		if(null == listData) {
//			showProgressDialog(loading, true);
//		}
		
		rs = new RSAllComment();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("table_choose", String.valueOf(table_choose));
		params.put("row_id", String.valueOf(row_id));
		//params.put("last_index_id", listData == null?"0":String.valueOf((listData.size()-1)));
		params.put("last_index_id", String.valueOf(last_index_id));
		params.put("size", String.valueOf(SIZE));
		rs.setParams(params);
		
		rs.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				rs = null;
				comlpeteRefresh();
				hideProgressDialog();
				
				if(null != result){
					
					HashMap<String, Object> map = JSONParser_AllComment.paser(result.toString());
					fillData(map, isReloading);
				}
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				rs = null;
				showToast(getString(R.string.net_error));
				hideProgressDialog();
			}
		});
		rs.asyncExecute(this);
	}
	
	private void comlpeteRefresh(){
		mHandler.sendEmptyMessageDelayed(0, 100);
	}
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			listView.forceRefreshComplete();
		}
		
	};
	
	@Override
	protected void cancelTask() {
		if(null != rs) rs.cancel();
	}

	private void fillData(HashMap<String, Object> map, boolean isReloading){
		if(null != map){
			last_index_id = Integer.valueOf((String)(map.get("smallest_id")));
			surplus = Integer.valueOf((String)(map.get("surplus")));
			
			if(null == listData || isReloading) listData = (ArrayList<EviewModel>)map.get("list");
			else listData.addAll((ArrayList<EviewModel>)map.get("list"));
			
			if(null == adapter || isReloading){
				adapter = new ServiceDetailCommentAdapter(ServiceDetailCommentAdapter.FROM_ALL_COMMENT, 
						this, 
						listData);
				listView.setAdapter(adapter);
			}else{
				adapter.notifyDataSetChanged();
			}
		}
	}

}

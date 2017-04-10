package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.adapter.HotTopicAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.model.HotTopicBean;
import com.sinaleju.lifecircle.app.model.json.JSONParser_HotTopic;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSHotTopic;

/**
 * 
 * @ClassName: HotTopicAct 
* @Description: TODO 热门话题列表界面
* @author zhenwei 
* @date 2013-8-23 下午12:03:01 
*
 */
public class HotTopicAct extends BaseActivity implements OnClickListener{

	private PullToRefreshListView listView;
	
	private RSHotTopic rs;
	
	private int community_id;//小区id
	
	private int curPage = 1;
	private final int PAGE_SIZE = 15;
	private int totalPage;
	
	private ArrayList<HotTopicBean> listData;
	
	private HotTopicAdapter adapter;
	
	@Override
	protected int getLayoutId() {
		return R.layout.hot_topic;
	}

	@Override
	protected void initView() {
		listView = (PullToRefreshListView)findViewById(R.id.hot_topic_listview);
		init();
	}

	private void init(){
		mTitleBar.setTitleName(R.string.hot_topic);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.setLeftButtonListener(this);
		
		community_id = getIntent().getIntExtra("community_id", -1);
		
		showProgressDialog(getString(R.string.loading_data), true);
		loadData(curPage);
	}
	
	@Override
	protected void initData() {
	}

	@Override
	protected void initCallbacks() {
		
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(HotTopicAct.this, TopicDetailActivity.class);
				intent.putExtra(AppConst.INTENT_TOPIC_DETAIL_ID, Integer.valueOf(listData.get(arg2 - 1).getId()));
				startActivity(intent);
			}
			
		});
	}

	private void pullDownLoadingData(){//下拉刷新加载数据
		loadData(1);//加载第一页的数据
	}
	
	private void pullUpLoadingData(){//上拉刷新加载数据
		if(totalPage == curPage){
			showToast(R.string.no_more);
			comlpeteRefresh();
			return;
		}
		loadData(++curPage);
	}
	
	private void loadData(final int page){
		if(rs != null) return;
		
		rs = new RSHotTopic();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("community_id", String.valueOf(community_id));
		params.put("type", "hot");
		params.put("page", String.valueOf(page));
		params.put("page_size", String.valueOf(PAGE_SIZE));
		rs.setParams(params);
		rs.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				rs = null;
				hideProgressDialog();
				comlpeteRefresh();
				
				HashMap<String, Object> resultMap = JSONParser_HotTopic.parse(result.toString());
				if(null != resultMap){
					curPage = (Integer)resultMap.get("curPage");
					totalPage = (Integer)resultMap.get("totalPage");
					
					ArrayList<HotTopicBean> list = (ArrayList<HotTopicBean>) resultMap.get("list");
					if(page == 1){//第一次重新加载
						fillDataReset(list);
					}else{//下一页数据
						fillDataAddBottom(list);
					}
				}
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				Toast.makeText(HotTopicAct.this, R.string.net_error, Toast.LENGTH_LONG).show();
				rs = null;
				hideProgressDialog();
				comlpeteRefresh();
			}
		});
		
		rs.asyncExecute(this);
	}
	
	private void fillDataReset(ArrayList<HotTopicBean> list){
		if(null == list || list.size() == 0){
			Toast.makeText(this, "没有热门话题！", Toast.LENGTH_LONG).show();
			adapter = null;
			listView.setAdapter(null);
		}else{
			listData = list;
			adapter = new HotTopicAdapter(this, listData);
			listView.setAdapter(adapter);
		}
	}
	
	private void fillDataAddBottom(ArrayList<HotTopicBean> list){
		if(null != list){
			if(null == adapter)adapter = new HotTopicAdapter(this, list);
			listData.addAll(list);
			if(null == adapter){
				adapter = new HotTopicAdapter(this, listData);
				listView.setAdapter(adapter);
			}else{
				adapter.notifyDataSetChanged();
			}
			
		}
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
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.imavBack:
			finish();
			break;
		}
	}
	
}

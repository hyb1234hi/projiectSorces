package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.adapter.TopicResultAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.Model_TopicsList;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_TopicsList;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetAllToplicList;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class TopicsActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = "TopicsActivity";
	private EditText mEt_searchContent;
	private View mV_delete;
	private ListView mList;
	//private View mCoverView;
	private String mSearchContent;
	private Button mCancel;
	private MultiTypesAdapter mAdapter;
	private TopicResultAdapter resultAdapter;
	private JSONParser_TopicsList jsonParserTopic;
	private List<MultiModelBase> dataList;
	private List<MultiModelBase> resultList;
	private ListView reslutListView;
	private int community_id;
	private String page;
	private String size;
	private String type;
	private View resultListView_bottom;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_topics;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		mEt_searchContent = (EditText) findViewById(R.id.topics_searchcontent);
		mV_delete = findViewById(R.id.topics_delete);
		mList = (ListView) findViewById(R.id.topics_list);
		reslutListView = (ListView) findViewById(R.id.topics_reslutlist);
		
//		resultListView_bottom=getLayoutInflater().inflate(R.layout.cell_foot_view, null);
//		resultListView_bottom.findViewById(R.id.image).setBackgroundResource(R.drawable.addedcommunity_addicon);
//		((TextView)resultListView_bottom.findViewById(R.id.text)).setText("更多热门话题");
//		((LinearLayout)resultListView_bottom.findViewById(R.id.click_new_layout)).setOnClickListener(this);
//		reslutListView.addFooterView(resultListView_bottom);
		
		//mCoverView = findViewById(R.id.topics_coverlayout);
		mCancel = (Button) findViewById(R.id.topics_cancel);
		
		resultList = new ArrayList<MultiModelBase>();
	}

	@Override
	protected void initData() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("插入话题");
		resultAdapter = new TopicResultAdapter(mContext);
		// 获取请求参数信息
		community_id = getIntent().getIntExtra(
				AppConst.INTENT_MSG_COMMUNITY_ID, 0);
		LogUtils.e(TAG, "community_id:   " + community_id);
		page = "";// 页数 默认1
		size = "";// 分页大小
		type = "";// 分类 1 按照最新排序，2 话题数量排序

		// 从网络获取小区关注列表
		jsonParserTopic = new JSONParser_TopicsList();		

		RSGetAllToplicList rsToplicList = new RSGetAllToplicList(
				String.valueOf(community_id), page, size, type);
		rsToplicList.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				LogUtils.e(TAG, result.toString());
				hideProgressDialog();
				MultiModelsSet set = new MultiModelsSet(jsonParserTopic);
				set.add(result.toString());
				mAdapter = new MultiTypesAdapter(set, mContext);
				mList.setAdapter(mAdapter);

				dataList = jsonParserTopic.getDateList();

			}
		});
		rsToplicList.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if(ex instanceof ADRemoteException){
					showToast(((ADRemoteException) ex).msg());
				} else if(ex instanceof ConnectTimeoutException){
					showToast("请求超时，请重试");
					
				}
				LogUtils.e(TAG, ex.toString());

			}
		});
		// 是否有网络连接
		if (PublicUtils.isNetworkAvailable(mContext)) {
			rsToplicList.asyncExecute(mContext);
			showProgressDialog("数据加载中...", true);
		} else {
			showToast("网络连接失败，请检查网络后重试");
		}

		// 设置Adapter;
		// MultiModelsSet set = new MultiModelsSet(new JSONParser_TopicsList());
		// set.add("1");
		// mAdapter = new MultiTypesAdapter(set, mContext);
		// mList.setAdapter(mAdapter);

		// dataList = jsonParserTopic.getDateList();

		// LogUtils.e(TAG, "datalist: size  " + dataList.size());
	}

	@Override
	protected void initCallbacks() {
		mV_delete.setOnClickListener(this);
		mTitleBar.setLeftButtonListener(this);
		mCancel.setOnClickListener(this);
		mEt_searchContent.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mTitleBar.setVisibility(View.GONE);
					mCancel.setVisibility(View.VISIBLE);
					//mCoverView.setVisibility(View.VISIBLE);
				}

			}
		});
		mEt_searchContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				LogUtils.e(TAG, s.toString());
				String text = s.toString().trim().toUpperCase();
				Model_TopicsList topic = null;
				if ("".equals(text)) {
					mList.setVisibility(View.VISIBLE);
					reslutListView.setVisibility(View.GONE);
				} else {
					mTitleBar.setVisibility(View.GONE);
					//mCoverView.setVisibility(View.VISIBLE);
					mCancel.setVisibility(View.VISIBLE);
					// 隐藏覆盖层，并重新设置list 的adapter;
					//mCoverView.setVisibility(View.GONE);
					// 清空结果list
					resultList.clear();
					// 判断已有话题库中是否包含当前搜索话题。
					resultList.add(new Model_TopicsList(-1,-1,-1,"#"+s.toString()+"#"));					
					if (dataList != null && dataList.size() > 0) {
						for (int i = 0; i < dataList.size(); i++) {
							MultiModelBase base = dataList.get(i);
							if (base instanceof Model_TopicsList) {
								topic = (Model_TopicsList) base;
								if (topic.getName().toUpperCase().startsWith("#" + text)&& !resultList.contains(topic)) {
									resultList.add(topic);
								}
							}
						}
						if (resultList != null && resultList.size() > 0) {
							mList.setVisibility(View.GONE);
							reslutListView.setVisibility(View.VISIBLE);
							resultAdapter.setList(resultList);
							reslutListView.setAdapter(resultAdapter);
							// showToast("搜索结果条数：  " + resultList.size());
						} else {
						//	showToast("你搜索的话题不存在");
						}
					}

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				/*
				 * LogUtils.e(TAG, s.toString()); if (s.equals("")) {
				 * mCancel.setVisibility(View.VISIBLE);
				 * mTitleBar.setVisibility(View.GONE);
				 * mCoverView.setVisibility(View.VISIBLE); }
				 */

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		reslutListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Model_TopicsList topicsModel = (Model_TopicsList) resultList.get((int) arg3);
				Intent data = new Intent();
				data.putExtra(AppConst.INTENT_MSG_TOPIC_ID, topicsModel.getId());
				data.putExtra(AppConst.INTENT_MSG_TOPIC_TEXT,
						topicsModel.getName());
				mActivity.setResult(300, data);
				mActivity.finish();
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topics_delete:
			mSearchContent = mEt_searchContent.getText().toString().trim();
			if (!"".equals(mSearchContent)) {
				mEt_searchContent.setText("");
			}
			break;
		case R.id.imavBack:
			//Intent intent = new Intent(mContext, AttentionUserActivity.class);
			//startActivity(intent);
			finish();
			break;
		case R.id.topics_cancel:
			// 隐藏覆盖屋，重设adapter;
			mTitleBar.setVisibility(View.VISIBLE);
			//mCoverView.setVisibility(View.GONE);
			mCancel.setVisibility(View.GONE);
			reslutListView.setVisibility(View.GONE);
			mList.setVisibility(View.VISIBLE);
			mEt_searchContent.setText("");
			break;
//		case R.id.click_new_layout:
//			startActivity(new Intent(mContext,HotTopicAct.class));
//			break;
		default:
			break;
		}

	}

}

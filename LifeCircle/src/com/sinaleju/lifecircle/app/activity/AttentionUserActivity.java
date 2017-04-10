package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.adapter.ReslutAttentionUserAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.Model_AttentionUserInfo;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_AttentionUser;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetAttentionList;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class AttentionUserActivity extends BaseActivity implements
		OnClickListener {
	private static final String TAG = "AttentionUserActivity";
	private EditText mEt_searchContent;
	private View mV_delete;
	private ListView mListView;
	private ListView mResultListView;
	private String mSearchContent;
	private MultiTypesAdapter mAdapter;
	private ReslutAttentionUserAdapter resultAdapter;
	private List<Model_AttentionUserInfo> resultList;
	private int community_id;
	private int userId;
	private String follow_Type;
	private String recent;
	private int attentionCount;
	private List<MultiModelBase> dataList;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_attentionuser;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		mEt_searchContent = (EditText) findViewById(R.id.attentionuser_searchcontent);
		mV_delete = findViewById(R.id.attentionuser_delete);
		mListView = (ListView) findViewById(R.id.attentionuser_list);
		mResultListView = (ListView) findViewById(R.id.attentionuser_resultlist);
		resultList = new ArrayList<Model_AttentionUserInfo>();
		resultAdapter = new ReslutAttentionUserAdapter(mContext, resultList,
				false);
		mResultListView.setAdapter(resultAdapter);
	}

	@Override
	protected void initData() {
		
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initRightButtonTextOrResId(null,
				R.drawable.selector_topbar_refresh_button);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("小区关注用户");
		// 获取请求参数信息
		community_id = getIntent().getIntExtra(
				AppConst.INTENT_MSG_COMMUNITY_ID, 0);
		userId = getIntent().getIntExtra(AppConst.INTENT_MSG_USER_ID, 0);
		LogUtils.e(TAG, "community_id:  " + community_id + "  userId:  "
				+ userId);
		follow_Type = "2"; // 关注类型：0、个人，1、 商家 物业 2、全部
		recent = "1";// 请求返回数据时是否包含最新联系人。
		// 从网络获取小区关注列表
		getUserList();
		
	}

	/**
	 * 
	 */
	private void getUserList() {
		RSGetAttentionList rsAttentionList = new RSGetAttentionList(
				String.valueOf(community_id), String.valueOf(userId), follow_Type, recent);
		rsAttentionList.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				attentionCount=0;
				MultiModelsSet set = new MultiModelsSet(
						new JSONParser_AttentionUser());
				set.add(result.toString());

				// set.add("1");
				mAdapter = new MultiTypesAdapter(set, mContext);
				mListView.setAdapter(mAdapter);
				dataList = JSONParser_AttentionUser.resultData;
				//计算关注用户数量。
				for (int i = 0; i < dataList.size(); i++) {
					if (dataList.get(i) instanceof Model_AttentionUserInfo ) {
						attentionCount++;
					}
				}
				mEt_searchContent.setHint("本小区共有"+attentionCount+"位关注用户");

			}
		});
		rsAttentionList.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				}else if(ex instanceof ConnectTimeoutException){
					showToast("请求超时，请重试");
				}
				LogUtils.e(TAG, ex.toString());

			}
		});
		// 是否有网络连接
		if (PublicUtils.isNetworkAvailable(mContext)) {
			rsAttentionList.asyncExecute(mContext);
			showProgressDialog("数据加载中...", true);
		} else {
			showToast("网络连接失败，请检查网络后重试");
		}
	}

	@Override
	protected void initCallbacks() {
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);
		mV_delete.setOnClickListener(this);
		// 搜索框输入内容监听
		mEt_searchContent.addTextChangedListener(new TextWatcher() {
			int originLength=0;
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String result = s.toString().trim().toUpperCase();
				Model_AttentionUserInfo user = null;
				resultList.clear();
				if ("".equals(result)) {
					mResultListView.setVisibility(View.GONE);
					mListView.setVisibility(View.VISIBLE);
					originLength=0;
				} else {
					mResultListView.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.GONE);
					
					for (int i = 0; i < dataList.size(); i++) {
						MultiModelBase base = dataList.get(i);
						if (base instanceof Model_AttentionUserInfo) {
							user = (Model_AttentionUserInfo) base;
							if (user.getNickName().toUpperCase().startsWith(result)) {
								resultList.add(user);
							}
						}
					}
					LogUtils.i(TAG, "size: "+resultList.size());
					if(resultList.size()==0){
						resultList.add(new Model_AttentionUserInfo(-1, 0, "", "@"+s, "0", 0));
						resultAdapter.notifyDataSetChanged();
					}
					//当本次搜索结果和上次不一样时，重新设置搜索结果adapter;
					if(resultList.size()!=originLength){
						originLength=resultList.size();
						resultAdapter.setResultList(resultList);
					}

				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		// 搜索listView onItemClick监听
		mResultListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Model_AttentionUserInfo userModel = resultList.get(arg2);
				String userName=null;
				if(userModel.getId()==-1){
					userName=userModel.getNickName();
				}else{
					userName="@"+userModel.getNickName();
				}
				Intent data = new Intent();
				data.putExtra(AppConst.INTENT_MSG_AT_ID, userModel.getId());
				data.putExtra(AppConst.INTENT_MSG_AT_TEXT,
						userName);
				data.putExtra(AppConst.INTENT_MSG_AT_TYPE, userModel.getType());
				setResult(400, data);
				finish();
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attentionuser_delete:
			mSearchContent = mEt_searchContent.getText().toString().trim();
			if (!"".equals(mSearchContent)) {
				mEt_searchContent.setText("");
			}
			break;
		// 返回
		case R.id.imavBack:
			finish();
			break;
		// 刷新
		case R.id.tvRightFunc1:
			getUserList();
			break;

		default:
			break;
		}

	}

}

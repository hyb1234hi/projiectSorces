package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.adapter.TopicResultAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.map.SelectLiveAreaMapActivity;
import com.sinaleju.lifecircle.app.model.Model_Community;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.model.json.JSONParser_CommunityChoose;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSFindCommunity;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class CommunityChooseActivity extends BaseActivity implements
		OnClickListener {
	protected static final String TAG = "SelectLiveAreaActivity";
	private static final int GOTOSELECTMAPCOMMUNITY =2000;
	private EditText mEt_searchContent;
	private ImageView mIv_delect;
	private ListView mLv_areaList;
	private ListView resultListView;
	// private View mLl_mapSelect;
	private MultiTypesAdapter mAreaAdapter;
	private List<Model_Community> mAreaList;
	private List<MultiModelBase> dataList;
	private List<MultiModelBase> resultList;
	private TopicResultAdapter resultAdapter;
	private View bottomView;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_selectlivearea;
	}

	@Override
	protected void initView() {
		mEt_searchContent = (EditText) findViewById(R.id.selectarea_searchcontent);
		// mLl_mapSelect=findViewById(R.id.selectlivearea_map);
		mIv_delect = (ImageView) findViewById(R.id.selectarea_delete);
		mLv_areaList = (ListView) findViewById(R.id.selectarea_arealist);
		resultListView = (ListView) findViewById(R.id.selectarea_resultlist);
		// 地图选择小区
		bottomView= getLayoutInflater().inflate(
				R.layout.view_selectlivearea_bottom, null);
		mLv_areaList.addFooterView(bottomView);
		
		// 获取小区数据
		getCommunity();
		
	}

	@Override
	protected void initData() {
		resultList = new ArrayList<MultiModelBase>();
		resultAdapter = new TopicResultAdapter(mContext);

		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("选择小区");
		

	

		// 设置小区adapter
		/*
		 * MultiModelsSet set=new MultiModelsSet(new
		 * JSONParser_CommunityChoose()); set.add("1"); mAreaAdapter = new
		 * MultiTypesAdapter(set, mContext);
		 * mLv_areaList.setAdapter(mAreaAdapter);
		 */
	}

	private void getCommunity() {
		String cityId = getIntent().getStringExtra("cityid");
		RSFindCommunity fCommunity = new RSFindCommunity(cityId);
		fCommunity.setOnSuccessHandler(new OnSuccessHandler() {
			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				LogUtils.e(TAG, result.toString());
				// 设置小区adapter
				MultiModelsSet set = new MultiModelsSet(
						new JSONParser_CommunityChoose());
				set.add(result.toString());
				mAreaAdapter = new MultiTypesAdapter(set, mContext);
				mLv_areaList.setAdapter(mAreaAdapter);
				dataList = JSONParser_CommunityChoose.data;
			}
		});
		fCommunity.setOnFaultHandler(new OnFaultHandler() {

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
		if (PublicUtils.isNetworkAvailable(mContext)) {
			fCommunity.asyncExecute(mContext);
			showProgressDialog("数据加载中......", false);
		} else {
			showToast("网络连接失败，请检查网络后重试");
		}
	}

	@Override
	protected void initCallbacks() {
		mIv_delect.setOnClickListener(this);
		mTitleBar.setLeftButtonListener(this);
		
		bottomView.setOnClickListener(this);

		mEt_searchContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				LogUtils.e(TAG, s.toString());
				String text = s.toString().trim().toLowerCase();
				Model_Community community = null;
				if ("".equals(text)) {
					mLv_areaList.setVisibility(View.VISIBLE);
					resultListView.setVisibility(View.GONE);
				} else {
					// 清空结果list
					resultList.clear();
					// 判断小区库中是否包含当前搜索小区。
					if (dataList != null && dataList.size() > 0) {
						a: for (int i = 0; i < dataList.size(); i++) {
							MultiModelBase base = dataList.get(i);

							if (base instanceof Model_Community) {
								community = (Model_Community) base;
								if (community.getmCommunityName().startsWith(
										text)||community.getAlphabet().toLowerCase().startsWith(text)) {

									for (int j = 0; j < resultList.size(); j++) {
										if (((Model_Community) resultList
												.get(j)).getId() == community
												.getId())
											break a;
									}

									resultList.add(community);
								}
							}
						}
					//	if (resultList != null && resultList.size() > 0) {
							mLv_areaList.setVisibility(View.GONE);
							resultListView.setVisibility(View.VISIBLE);
							resultAdapter.setList(resultList);
							resultListView.setAdapter(resultAdapter);
							if(resultList.size()==0){
								showToast("您搜索的小区不存在");								
							}
							// showToast("搜索结果条数：  " + resultList.size());
					//	} else {
					//	}
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
		resultListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				done((Model_Community) resultList.get(arg2));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imavBack:
			this.finish();
			break;
		case R.id.selectarea_delete:
			if (!mEt_searchContent.getText().toString().equals("")) {
				mEt_searchContent.setText("");
			}
			break;
		case R.id.selectlivearea_map:
			// 地图选择小区
			Intent mapIntent = new Intent(mContext,
					SelectLiveAreaMapActivity.class);
			startActivityForResult(mapIntent, GOTOSELECTMAPCOMMUNITY);

			break;

		default:
			break;

		}
	}

	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent
	 data) {
	 // TODO Auto-generated method stub
	 if (resultCode ==SelectLiveAreaMapActivity.FORMMAPSELECTCOMMUNITY) {
		 if(data!=null){
			 Model_Community community=(Model_Community) data.getSerializableExtra("community");
			 done(community);
		 }
	 
	 }
	 }

	private void done(Model_Community community) {
		Intent data = new Intent();
		data.putExtra("community_id", community.getId());
		data.putExtra("community_name", community.getmCommunityName());
		data.putExtra("community_topic", community.getTopicCount());
		LogUtils.e(TAG, "topiCount: "+community.getTopicCount());
		// 游客选择城市后进入小区首页
		setResult(Activity.RESULT_OK, data);
		ADSoftInputUtils.hide(mContext);
		finish();
	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED);
		finish();
	}

	@Override
	public void onResume() {
		super.onResume();
		registNotify();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregistNotify();
	}

	/**
	 * 注册通知
	 */
	private void registNotify() {
		ApplicationFacade.getInstance()
				.registerMediator(
						new CommunityChooseMediator(
								CommunityChooseMediator.NAME, null));
	}

	/**
	 * 注销通知
	 */
	private void unregistNotify() {
		ApplicationFacade.getInstance().removeMediator(
				CommunityChooseMediator.NAME);
	}

	private class CommunityChooseMediator extends Mediator {
		public static final String NAME = "CommunityChooseMediator";

		public CommunityChooseMediator(String mediatorName, Object viewComponent) {
			super(mediatorName, viewComponent);
		}

		@Override
		public void handleNotification(INotification n) {
			String name = n.getName();
			Model_Community m = (Model_Community) n.getBody();
			if (name.equals(AppConst.APP_FACADE_COMMUNITY_SELECT_DONE)
					&& m != null) {
				done(m);
			}

		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { AppConst.APP_FACADE_COMMUNITY_SELECT_DONE };
		}

	}

}

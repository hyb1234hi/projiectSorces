package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.adapter.MerchantListAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.Model_MerchantInfo;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetMerchantList;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class MerchantListActivity extends BaseActivity implements
		OnClickListener {
	private PullToRefreshListView mListView;
	private MerchantListAdapter mAdapter;
	private List<Model_MerchantInfo> mPageList, mList;
	private String type; // 商家类型
	private int curPage = -1; // 当页商家的最小id.
	private int size = 10; // 每页显示条数
	private int totalPage = -1;
	protected boolean isMore = false;
	protected String TAG = "MerchantListActivity";

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_merchantlist;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		mListView = (PullToRefreshListView) findViewById(R.id.merchantlist_list);
		mListView.setMode(Mode.PULL_UP_TO_REFRESH);
		mTitleBar.setTitleName("商家列表");
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("",
				R.drawable.selector_topbar_back_button);
		mTitleBar.setLeftButtonListener(this);

		mList = new ArrayList<Model_MerchantInfo>();
		mPageList = new ArrayList<Model_MerchantInfo>();
		type =getIntent().getStringExtra("merchant_type");
		// 从服务器获取商家数据
		getMerchantList(type, curPage, size);
	}

	@Override
	protected void initData() {

	}

	private void getMerchantList(String type, int lastIndexId, int size) {
		RSGetMerchantList rsGetMerchant = new RSGetMerchantList(type,
				lastIndexId, size);
		rsGetMerchant.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				parserJson(result.toString());
				mList.addAll(mPageList);
				if (isMore) {
					mListView.onRefreshComplete();
					mAdapter.setList(mList);
					isMore = false;
				} else {
					mAdapter = new MerchantListAdapter(mContext, mList);
					mListView.setAdapter(mAdapter);
				}
				LogUtils.i(TAG, "mList.size: " + mList.size()
						+ "　mPageList.size:" + mPageList.size());

			}

		});
		rsGetMerchant.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}

			}
		});
		if (PublicUtils.isNetworkAvailable(mContext)) {
			showProgressDialog("数据加载中...", true);
			rsGetMerchant.asyncExecute(mContext);
		} else {
			showToast("网络连接失败，请重试");
		}

	}

	private void parserJson(String json) {
		try {
			JSONObject dataObj = new JSONObject(json);
			curPage = dataObj.optInt("curPage");
			totalPage = dataObj.optInt("totalPage");
			JSONArray merchantArray = dataObj.optJSONArray("list");
			if (merchantArray != null) {
				for (int i = 0; i < merchantArray.length(); i++) {
					JSONObject infoObj = merchantArray.optJSONObject(i);
					Model_MerchantInfo model = new Model_MerchantInfo(
							infoObj.optInt("id"), infoObj.optString("img"),
							infoObj.optString("name"),
							infoObj.optString("content"),
							infoObj.optString("web_url"));
					mPageList.add(model);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void initCallbacks() {
		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				return;
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// 加载更多
				if (curPage++ < totalPage) {
					isMore = true;
					getMerchantList(type, curPage, size);
				} else {
					showToast("所有数据已加载完毕");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mListView.onRefreshComplete();
						}
					}, 100);
				}
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 单击跳转到商家主页。
				LogUtils.e(TAG, "arg2: "+arg2+" arg3: "+arg3);
				int merchantId = mList.get((int) arg3).getId();
				LogUtils.e(TAG, "id: "+merchantId);
				// gotoBusinessPage( merchantId, false, true);
				AppContext.gotoIndexActivity(mContext, 1, merchantId);
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imavBack:
			finish();
			break;
		default:
			break;
		}
	}

}

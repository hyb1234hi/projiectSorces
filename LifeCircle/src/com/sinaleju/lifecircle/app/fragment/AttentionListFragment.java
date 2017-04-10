package com.sinaleju.lifecircle.app.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.ChatDetailAct;
import com.sinaleju.lifecircle.app.adapter.MultiTypesAdapter;
import com.sinaleju.lifecircle.app.adapter.ReslutAttentionUserAdapter;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.customviews.SwitchButton;
import com.sinaleju.lifecircle.app.customviews.SwitchButton.OnSwitchListener;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
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

public class AttentionListFragment extends BaseFragment implements
		OnClickListener {

	private static final String TAG = "AttentionListFragment";
	// private Context mContext = getActivity();
	private RSGetAttentionList rsGetAttention;
	private View mContentView = null;
	private SwitchButton switchButton;
	private ListView listView;
	private ListView resultListView;
	private TitleBar mTitleBar;
	private ImageView mIv_delete;
	private EditText mEt_searchContent;
	private String followType = "0";// 关注类型
	private int userId; // 用户
	private MultiTypesAdapter mPersonAdapter = null, mMerchantAdapter = null;
	private List<MultiModelBase> dataList = null, dataList_person = null,
			dataList_Merchant = null;
	private ReslutAttentionUserAdapter resultAdapter;
	private List<Model_AttentionUserInfo> resultList;
	private boolean isPrivatLetter = false;
	private InputMethodManager inputMethodManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mContentView == null) {
			mContentView = inflater.inflate(R.layout.fr_attentionlist,
					container, false);
			initData();
			initView();
			setListener();
		} else {
			((ViewGroup) mContentView.getParent()).removeAllViews();
		}
		return mContentView;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "-------------initView-------");
		switchButton = (SwitchButton) mContentView
				.findViewById(R.id.attentionlist_switchbutton);
		switchButton.setText("个人", "企业");
		listView = (ListView) mContentView
				.findViewById(R.id.attentionlist_list);
		resultListView = (ListView) mContentView
				.findViewById(R.id.attentionlist_resultlist);
		mTitleBar = (TitleBar) mContentView.findViewById(R.id.mTitleBar);
		mIv_delete = (ImageView) mContentView
				.findViewById(R.id.attentionlist_delete);
		mEt_searchContent = (EditText) mContentView
				.findViewById(R.id.attentionlist_searchcontent);

		initTitleLayout();
		userId = AppContext.curUser().getUid();

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		getAttentionList(); // 获取个人/企业关注列表；
		setData();
		super.onStart();
	}

	protected void initTitleLayout() {
		mTitleBar.setLeftButtonShow(true);
		if (isPrivatLetter) {
			mTitleBar.setTitleName("选择关注人");
			mTitleBar.initLeftButtonTextOrResId(0,
					R.drawable.selector_topbar_back_button);
		} else {
			mTitleBar.setTitleName("关注列表");
			mTitleBar.initLeftButtonTextOrResId(0,
					R.drawable.selector_home_page_top_bar_left_button);
		}
		mTitleBar.initRightButtonTextOrResId(0,
				R.drawable.selector_topbar_refresh_button);

		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isPrivatLetter) {
					getActivity().finish();
				} else {
					// LogUtils.i(TAG,
					// "isShow:  "+inputMethodManager.isActive(mEt_searchContent));
					if (inputMethodManager.isActive(mEt_searchContent)) { // 判断当前软键盘的显示状态
						inputMethodManager.hideSoftInputFromWindow(
								mEt_searchContent.getWindowToken(), 0);
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								((AppContext) getActivity().getApplication())
										.getHomeActivity().toggle();
							}
						}, 100);
					} else {
						((AppContext) getActivity().getApplication())
								.getHomeActivity().toggle();
					}
				}
			}
		});

		mTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 刷新
				getAttentionList();
			}
		});

	}

	/**
	 * 获取用户关注列表
	 * 
	 * @param user_id
	 */
	private void getAttentionList() {

		// if (rsGetAttention == null) {
		// }
		rsGetAttention = new RSGetAttentionList("", userId + "", followType, "");

		rsGetAttention.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				MultiModelsSet set = new MultiModelsSet(
						new JSONParser_AttentionUser());
				set.add(result.toString());
				if ("0".equals(followType)) {
					mPersonAdapter = new MultiTypesAdapter(set, getActivity());
					listView.setAdapter(mPersonAdapter);
					dataList = dataList_person = JSONParser_AttentionUser.resultData;
					if (dataList_person == null || dataList_person.size() == 0) {
						showToast("您还没有关注个人好友哟");
					}
				} else {
					mMerchantAdapter = new MultiTypesAdapter(set, getActivity());
					listView.setAdapter(mMerchantAdapter);
					dataList = dataList_Merchant = JSONParser_AttentionUser.resultData;
					if (dataList_Merchant == null
							|| dataList_Merchant.size() == 0) {
						showToast("您还没有关注企业好友哟");
						// dataList = JSONParser_AttentionUser.resultData;
					}
				}

			}
		});
		rsGetAttention.setOnFaultHandler(new OnFaultHandler() {

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
			showProgressDialog("数据获取中......", true);
			rsGetAttention.asyncExecute(getActivity(), this);

		} else {
			showToast("网络连接失败，请检查网络后重试");
		}

	}

	@Override
	protected void initData() {
		inputMethodManager = ((InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE));
		String sActivity = getActivity().getClass().getName();
		if (sActivity.contains("FollowListAct")) {
			isPrivatLetter = true;
		}
		LogUtils.i(TAG, sActivity);
	}

	private void setData() {
		resultList = new ArrayList<Model_AttentionUserInfo>();
		// Test
		// MultiModelsSet set = new MultiModelsSet(new
		// JSONParser_AttentionUser());
		// set.add("1");
		// mAdapter = new MultiTypesAdapter(set, getActivity());
		// listView.setAdapter(mAdapter);
		resultAdapter = new ReslutAttentionUserAdapter(getActivity(),
				resultList, true);
		resultListView.setAdapter(resultAdapter);
		// dataList = JSONParser_AttentionUser.data;
	}

	@Override
	protected void setListener() {
		mIv_delete.setOnClickListener(this);
		// 个人、企业切换
		switchButton.setOnSwitchListener(new OnSwitchListener() {

			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if (isSwitchOn) { // 个人好友
					followType = "0";
					clearSearchText();
					if (null == mPersonAdapter) {
						getAttentionList();
					} else {
						listView.setAdapter(mPersonAdapter);
						dataList = dataList_person;
						if (dataList_person == null
								|| dataList_person.size() == 0) {
							showToast("您还没有关注个人好友哟");
							// dataList = JSONParser_AttentionUser.resultData;
						}
						// dataList = JSONParser_AttentionUser.resultData;
					}
					
				} else { // 企业好友
					followType = "1";
					clearSearchText();
					if (null == mMerchantAdapter) {
						getAttentionList();
					} else {
						listView.setAdapter(mMerchantAdapter);
						dataList = dataList_Merchant;
						if (dataList_Merchant == null
								|| dataList_Merchant.size() == 0) {
							showToast("您还没有关注企业好友哟");
							// dataList = JSONParser_AttentionUser.resultData;
						}
					}
					
				}
			}

		});

		// 搜索框输入内容监听
		mEt_searchContent.addTextChangedListener(new TextWatcher() {
			int originLength = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String result = s.toString().trim().toUpperCase();
				Model_AttentionUserInfo user = null;
				resultList.clear();
				if ("".equals(result)) {
					resultListView.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					originLength = 0;
				} else {
					resultListView.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					for (int i = 0; i < dataList.size(); i++) {
						MultiModelBase base = dataList.get(i);
						if (base instanceof Model_AttentionUserInfo) {
							user = (Model_AttentionUserInfo) base;
							if (user.getNickName().toUpperCase()
									.startsWith(result)) {
								resultList.add(user);
							}
						}
					}
					// 当本次搜索结果和上次不一样时，重新设置搜索结果adapter;
					if (resultList.size() != originLength) {
						originLength = resultList.size();
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
		resultListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Model_AttentionUserInfo userModel = resultList.get(arg2);
				int uId = userModel.getId();
				String name = userModel.getNickName();

				if (isPrivatLetter) {
					Intent chatIntent = new Intent(getActivity(),
							ChatDetailAct.class);
					chatIntent.putExtra(ChatDetailAct.NAME_KEY, name);
					chatIntent.putExtra(ChatDetailAct.USER_ID_KEY, userId);
					chatIntent.putExtra(ChatDetailAct.TO_USER_ID_KEY, uId);
					chatIntent.putExtra(ChatDetailAct.TYPE_KEY, Integer.parseInt(userModel.getType()));
					startActivity(chatIntent);
					getActivity().finish();
				} else {
					int type = Integer.valueOf(userModel.getType());
					AppContext.gotoIndexActivity(getActivity(), type, uId);
				}

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attentionlist_delete:
			clearSearchText();
			break;

		default:
			break;
		}

	}

	private void clearSearchText() {
		if (!mEt_searchContent.getText().toString().trim().equals("")) {
			mEt_searchContent.setText("");
		}
	}

}

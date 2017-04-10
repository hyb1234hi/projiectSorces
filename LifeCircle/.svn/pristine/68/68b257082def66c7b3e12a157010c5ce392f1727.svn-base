package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.adapter.SelectCityAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.model.CityModel;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSFindCity;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.NetworkUtils;
import com.sinaleju.lifecircle.app.utils.PingyinUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class SelectCityActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SelectCityActivity";
	private AutoCompleteTextView mAt_searchContent;
	// private MultiAutoCompleteTextView mMt_autoComplete;
	private ImageView mIv_delect;
	private ImageView mIv_search;
	private TextView mTv_gpsCity;
	// private TextView mTv_gpsCityType;
	private ListView mLv_cityList;
	private SelectCityAdapter mCityAdapter;
	// private String[] cityArray;
	// private ArrayAdapter<String> mAutoAdapter;
	private List<CityModel> mCityList;
	private List<CityModel> mResultCityList;
	// private String mSearchContent;
	private LocationManagerProxy mAMapLocManager = null;
	private CityLocationListener locationListener = null;
	// private int mSearchLocation = -1;

	private View mLl_gps;
	private View mLl_gpsCity;
	private View mLl_hotCity;
	private String currentCity = ""; // 当前定位城市名称
	private String currentCityId = ""; // 当前定位城市Id
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				if (!currentCity.equals("") && mCityList != null
						&& mCityList.size() > 0) {
					for (int i = 0; i < mCityList.size(); i++) {
						if (mCityList.get(i).getName().equals(currentCity)) {
							currentCityId = mCityList.get(i).getCode();
							break;
						}
					}

					if (!currentCityId.equals("")) {
						LogUtils.i(TAG, "currentCityId  " + currentCityId);
						// 显示定位城市
						mTv_gpsCity.setText(currentCity);
						mLl_gpsCity.setClickable(true);
						// mLl_gps.setVisibility(View.VISIBLE);
						// mLl_gpsCity.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	};

	private static final int REQUEST_CODE_TO_COMMUNITY = 10;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_selectcity;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		mAt_searchContent = (AutoCompleteTextView) findViewById(R.id.selectcity_searchcontent);
		// mMt_autoComplete=(MultiAutoCompleteTextView)
		// findViewById(R.id.selectcity_autocomplete);
		mTv_gpsCity = (TextView) findViewById(R.id.selectcity_gpscity);
		// mTv_gpsCityType = (TextView)
		// findViewById(R.id.selectcity_gpscitytype);
		mIv_delect = (ImageView) findViewById(R.id.selectcity_delete);
		mIv_search = (ImageView) findViewById(R.id.selectcity_search);
		mLv_cityList = (ListView) findViewById(R.id.selectcity_citylist);
		mLl_gps = findViewById(R.id.selectcity__ll_gps);
		mLl_gpsCity = findViewById(R.id.selectcity_ll_gpscity);
		mLl_gpsCity.setClickable(false);
		mResultCityList = new ArrayList<CityModel>();
		mLl_hotCity=findViewById(R.id.selectcity_ll_hotcity);

	}

	@Override
	protected void initData() {
		// if(NetworkUtils.isOpenGPS(mContext)){
		// mLl_gps.setVisibility(View.VISIBLE);
		// mLl_gpsCity.setVisibility(View.VISIBLE);
		// }
		mAt_searchContent.setText("");
		mLl_gpsCity.setClickable(false);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(null,
				R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("选择城市");

		// 定位当前城市
		locationListener = new CityLocationListener();
		mAMapLocManager = LocationManagerProxy.getInstance(this);
		if (NetworkUtils.isNetworkAvailable(mContext)
				|| NetworkUtils.isOpenGPS(mContext)) {
			mAMapLocManager.requestLocationUpdates(
					LocationProviderProxy.AMapNetwork, 5000, 10,
					locationListener);
		} else {
			mLl_gpsCity.setClickable(false);
			mTv_gpsCity.setText("无法定位城市");
		}

		// 从网络获取城市信息。
		requestCityList();
		mCityList = new ArrayList<CityModel>();
		// mAt_searchContent.setThreshold(1);
		// mAt_searchContent.setDropDownBackgroundResource(R.drawable.autocomplete);
		// mAt_searchContent.setDropDownVerticalOffset(10);
		// mAt_searchContent.setDropDownWidth(LayoutParams.MATCH_PARENT);
	}

	/**
	 * 
	 */
	private void requestCityList() {
		RSFindCity fCity = new RSFindCity();
		fCity.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				try {
					JSONArray jsonArray = new JSONArray(result.toString());
					int length = jsonArray.length();
					// cityArray = new String[length];
					for (int i = 0; i < length; i++) {
						JSONObject json = jsonArray.optJSONObject(i);
						String name=json.getString("name");
						String firstAlphabet=PingyinUtils.cn2FirstSpell(name);
						LogUtils.i(TAG, "firstAlphabet: "+firstAlphabet);
						CityModel city = new CityModel(json.getString("id"),name,firstAlphabet);
						mCityList.add(city);
						// cityArray[i] = json.getString("name");
					}
					handler.sendEmptyMessage(0);
					mCityAdapter = new SelectCityAdapter(mContext, mCityList);
					mLv_cityList.setAdapter(mCityAdapter);
					// mAutoAdapter = new
					// ArrayAdapter<String>(mContext,android.R.layout.simple_dropdown_item_1line,
					// cityArray);
					// mAt_searchContent.setAdapter(mAutoAdapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// showToast(result.toString());
				LogUtils.e(TAG, result.toString());

			}
		});
		fCity.setOnFaultHandler(new OnFaultHandler() {
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
			showProgressDialog("数据获取中......", true);
			fCity.asyncExecute(mContext);

		} else {
			showToast("网络连接失败，请检查网络后重试");
		}
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(this);
		mIv_delect.setOnClickListener(this);
		mIv_search.setOnClickListener(this);
		mLl_gpsCity.setOnClickListener(this);
		mAt_searchContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = s.toString().trim().toLowerCase();
				mResultCityList.clear();
				LogUtils.i(TAG, "text: "+text+" length: "+mResultCityList.size());
				
				if ("".equals(text)) {
					mLl_gps.setVisibility(View.VISIBLE);
					mLl_gpsCity.setVisibility(View.VISIBLE);
					mLl_hotCity.setVisibility(View.VISIBLE);
					if (mCityList != null&&mCityAdapter!=null) {
						mCityAdapter.setList(mCityList);
					}
				} else {
					mLl_gps.setVisibility(View.GONE);
					mLl_gpsCity.setVisibility(View.GONE);
					mLl_hotCity.setVisibility(View.GONE);
					for (int i = 0; i < mCityList.size(); i++) {
						if (mCityList.get(i).getName().startsWith(text)||mCityList.get(i).getFisrtAlphabet().startsWith(text)) {
							mResultCityList.add(mCityList.get(i));
						}

					}
					if (mResultCityList.size() == 0) {
						showToast("您搜索的城市不存在");
					}
					mCityAdapter.setList(mResultCityList);
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
		/*
		 * mAt_searchContent.setOnFocusChangeListener(new
		 * OnFocusChangeListener() {
		 * 
		 * @Override public void onFocusChange(View v, boolean hasFocus) { if
		 * (hasFocus && !mAt_searchContent.getText().toString().trim()
		 * .equals("")) { AutoCompleteTextView view = (AutoCompleteTextView) v;
		 * view.showDropDown(); } } });
		 */

		mLv_cityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(mCityList != null && mCityList.size() > arg2){
					Intent selectLiveAreaIntent = new Intent(mContext,
							CommunityChooseActivity.class);
					selectLiveAreaIntent.putExtra("cityid", mCityList.get(arg2)
							.getCode());
					startActivityForResult(selectLiveAreaIntent,
							REQUEST_CODE_TO_COMMUNITY);
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (isBackFromCommunity(requestCode, resultCode)) {
			setResult(Activity.RESULT_OK, data);
			finish();
		}
	}

	private boolean isBackFromCommunity(int requestCode, int resultCode) {
		return requestCode == REQUEST_CODE_TO_COMMUNITY
				&& resultCode == Activity.RESULT_OK;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imavBack:
			this.finish();
			break;
		// 搜索
		case R.id.selectcity_search:
			// 获得搜索内容在当前列表中的位置。
			/*
			 * mSearchContent = mAt_searchContent.getText().toString().trim();
			 * if (!"".equals(mAt_searchContent)) { for (int i = 0; i <
			 * mCityList.size(); i++) { if
			 * (mSearchContent.equals(mCityList.get(i).getName())) {
			 * mSearchLocation = i; break; } } if (mSearchLocation == -1) {
			 * showToast("您搜索的城市未找到"); } else {
			 * mLv_cityList.setSelection(mSearchLocation);
			 * showToast(mSearchLocation + ""); } }
			 */

			break;
		case R.id.selectcity_delete:
			if (!mAt_searchContent.getText().toString().equals("")) {
				mAt_searchContent.setText("");
			}

			break;
		case R.id.selectcity_ll_gpscity:
			Intent selectLiveAreaIntent = new Intent(mContext,
					CommunityChooseActivity.class);
			selectLiveAreaIntent.putExtra("cityid", currentCityId);
			startActivityForResult(selectLiveAreaIntent,
					REQUEST_CODE_TO_COMMUNITY);

			break;

		default:
			break;
		}

	}

	// 定位监听
	class CityLocationListener implements AMapLocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(AMapLocation location) {
			// TODO Auto-generated method stub
			if (location != null) {
				currentCity = location.getCity();
				if (handler != null) {
					handler.sendEmptyMessage(0);
					mAMapLocManager.removeUpdates(this);
				}
			}
		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		mAMapLocManager.removeUpdates(locationListener);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (mAMapLocManager != null) {
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
		super.onDestroy();
	}
}

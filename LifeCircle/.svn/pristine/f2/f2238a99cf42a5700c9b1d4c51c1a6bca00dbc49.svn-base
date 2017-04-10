package com.sinaleju.lifecircle.app.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.fragment.MyCommunitiesFragment;
import com.sinaleju.lifecircle.app.model.Model_Community;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.json.JSONParser_CommunityChoose;
import com.sinaleju.lifecircle.app.utils.LogUtils;

/**
 * 地图选择小区
 */
public class SelectLiveAreaMapActivity extends FragmentActivity implements
		LocationSource, AMapLocationListener, OnMarkerClickListener,
		OnInfoWindowClickListener, OnMarkerDragListener, OnMapLoadedListener,
		InfoWindowAdapter {
	private static final String TAG = "SelectLiveAreaMapActivity";
	public static final int FORMMAPSELECTCOMMUNITY = 2001;
	private View mTitleView;
	private Button mBt_back;
	private TextView mTv_titleName;
	// private RadioGroup rOptions;
	private View mWindow;
	private View mContents;
	private AMap aMap;
	private List<MultiModelBase> dataList;
	private MarkerOptions[] markerArray;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private List<CommunityBean> addedCommunity = MyCommunitiesFragment.mAddedCellModels;
	private Map<Integer,Integer> topicMap=null;
	private int badge;
	private int curMarkerIndex;
	private Marker firstMarker;

	private Animation animation_show;
	private Animation animation_hide;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_selectlivearea_map);
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {		
		animation_show=AnimationUtils.loadAnimation(this, R.anim.anim_mapwindow_show);
		animation_hide=AnimationUtils.loadAnimation(this, R.anim.anim_mapwindow_hide);
		dataList = JSONParser_CommunityChoose.data;
		markerArray = new MarkerOptions[dataList.size()];
		BitmapDescriptor markerIcon = null;
		BitmapDescriptor otherIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.common_location_icon);
		BitmapDescriptor curIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.msg_main_location_press);
		int curCommunityId = -100;
		// 当前所在小区名字
		if (AppContext.curUser() != null) {
			curCommunityId = AppContext.curUser().getCurrentCommunityId();
		}
		// 取当前城市下的小区数据
		int j = 0;
		topicMap=new HashMap<Integer, Integer>();
		for (int i = 0; i < dataList.size(); i++) {
			MultiModelBase modelBase = dataList.get(i);
			if (modelBase instanceof Model_Community) {
				Model_Community communityModel = (Model_Community) modelBase;
				Double latitude = communityModel.getLatitude();
				Double longitude = communityModel.getLongitude();
				LatLng marker = new LatLng(latitude, longitude);
				String communityName = communityModel.getmCommunityName();
				int id = communityModel.getId();
				String communityId = String.valueOf(id);
				int topicCount=communityModel.getTopicCount();
				topicMap.put(id, topicCount);
				if (curCommunityId == id) {
					markerIcon = curIcon;
					curMarkerIndex = j;
					// LogUtils.i(TAG, "curName: "+communityName);
				} else {
					markerIcon = otherIcon;
					// LogUtils.i(TAG, "otherName: "+communityName);
				}
				// LogUtils.i(TAG, "icon: "+markerIcon.toString());
				MarkerOptions markerOptions = new MarkerOptions()
						.title(communityName).snippet(communityId)
						.position(marker).icon(markerIcon);
				markerArray[j] = markerOptions;
				j++;
				// markerMap.put(markerOptions, communityId);
			}
		}
		mTitleView = findViewById(R.id.selectarea_map_title);
		mBt_back = (Button) mTitleView.findViewById(R.id.title_left);
		mBt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		mTv_titleName = (TextView) mTitleView.findViewById(R.id.title_name);
		mTv_titleName.setText("地图选择");
		// mMarkerLitenerText = (TextView)
		// findViewById(R.id.mark_listenter_text);
		mWindow = getLayoutInflater().inflate(
				R.layout.view_map_custom_info_window, null);
		mContents = getLayoutInflater().inflate(
				R.layout.view_map_custom_info_contents, null);
		// rOptions = (RadioGroup)
		// findViewById(R.id.custom_info_window_options);
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		// aMap.addMarker(new MarkerOptions().position(Constants.BEIJING));
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));
		myLocationStyle.strokeColor(Color.BLACK);
		myLocationStyle.strokeWidth(1);

		aMap.setMyLocationStyle(myLocationStyle);
		mAMapLocationManager = LocationManagerProxy
				.getInstance(SelectLiveAreaMapActivity.this);
		aMap.setLocationSource(this);
		aMap.setMyLocationEnabled(true);// 设置为true表示系统定位按钮显示并响应点击，false表示隐藏，默认是false

		// aMap.getUiSettings().setZoomControlsEnabled(true);// 隐藏缩放按钮
		// aMap.getUiSettings().setMyLocationButtonEnabled(true);// 我的位置
		aMap.getUiSettings().setCompassEnabled(true);// 指南针
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		addMarkersToMap();// 往地图上添加marker
	}

	// 添加marker
	private void addMarkersToMap() {
		for (int i = 0; i < markerArray.length; i++) {
			if (markerArray[i] != null) {
				if (curMarkerIndex == i) {
					firstMarker = aMap.addMarker(markerArray[i]);
				} else {
					aMap.addMarker(markerArray[i]);
				}
				LogUtils.e(TAG, "marker number " + i);
			}
		}

		// drawMarkers();// 添加10个带有系统默认icon的marker点
	}

	/**
	 * 绘制系统默认的10种marker背景图片
	 */
	public void drawMarkers() {

	}

	/**
	 * 把一个xml布局文件转化成view
	 */
	public View getView(String title, String text) {
		View view = getLayoutInflater().inflate(R.layout.view_map_marker, null);
		TextView text_title = (TextView) view.findViewById(R.id.marker_title);
		TextView text_text = (TextView) view.findViewById(R.id.marker_text);
		text_title.setText(title);
		text_text.setText(text);
		return view;
	}

	/**
	 * 把一个view转化成bitmap对象
	 */
	public static Bitmap getViewBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	/**
	 * 清空地图上所有已经标注的marker
	 */
	public void onClearMap(View view) {
		if (AMapUtil.checkReady(this, aMap)) {
			aMap.clear();
		}
	}

	/**
	 * 重新标注所有的marker
	 */
	public void onResetMap(View view) {
		if (AMapUtil.checkReady(this, aMap)) {

			aMap.clear();
			addMarkersToMap();

			/*
			 * XIAN.remove(); addMarkersToMap();
			 */

		}
	}

	/**
	 * 对marker标注点点击响应事件
	 */
	@Override
	public boolean onMarkerClick(final Marker marker) {
		return false;

	}

	// 单击marker信息框。
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO click marker
		// String communityName = marker.getTitle();
		// int communityId = Integer.parseInt(marker.getSnippet());
		// LogUtils.i(TAG, "title: " + marker.getTitle() + "id: " +
		// communityId);
		// Model_Community community = new Model_Community(communityId,
		// communityName);
		// // LogUtils.i(
		// // TAG,
		// // "id  " + community.getId() + "  name  "
		// // + community.getmCommunityName());
		// // ToastUtil.show(this, liveAreaName);
		// Intent data = new Intent();
		// data.putExtra("community", community);
		// setResult(FORMMAPSELECTCOMMUNITY, data);
		// finish();

	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// String curDes = "Draged current position:(lat,lng)\n("
		// + arg0.getPosition().latitude + ","
		// + arg0.getPosition().longitude + ")";
		// mMarkerLitenerText.setText(arg0.getTitle() + curDes);
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// mMarkerLitenerText.setText(arg0.getTitle() + "DragEnd");

	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// mMarkerLitenerText.setText(arg0.getTitle() + "DragStart");
	}

	@Override
	public void onMapLoaded() {
		// 加载成功后设置所有maker显示在View中
		Builder builder = new LatLngBounds.Builder();
		for (int i = 0; i < markerArray.length; i++) {
			if (markerArray[i] != null) {
				LogUtils.i(TAG, "name: " + markerArray[i].getSnippet());
				builder.include(markerArray[i].getPosition());
			}
		}
		CameraPosition position = new CameraPosition.Builder()
				.target(firstMarker.getPosition()).zoom(12).bearing(0).tilt(0)
				.build();
		aMap.animateCamera(CameraUpdateFactory.newCameraPosition(position),
				1000, new CancelableCallback() {

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						firstMarker.showInfoWindow();
					}

				});

		// aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),
		// 20));
	}

	@Override
	public View getInfoContents(Marker marker) {
		// if (rOptions.getCheckedRadioButtonId() != R.id.custom_info_contents)
		// {
		// return null;
		// }
		render(marker, mContents);
		return mContents;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		// if (rOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
		// return null;
		// }
		render(marker, mWindow);
		mWindow.startAnimation(animation_show);
		return mWindow;
	}

	public void render(final Marker marker, View view) {
		badge = R.drawable.common_add;
		if (addedCommunity != null) {
			int curCommunityId = Integer.parseInt(marker.getSnippet());
			for (int i = 0; i < addedCommunity.size(); i++) {
				if (addedCommunity.get(i).getCid() == curCommunityId) {
					badge = R.drawable.common_added;
				}
			}
		}

		View textView = view.findViewById(R.id.mapwidndow_text);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mWindow.startAnimation(animation_hide);
				marker.hideInfoWindow();
			}
		});
		ImageView rightIcon = ((ImageView) view.findViewById(R.id.badge));
		rightIcon.setImageResource(badge);
		String communityName = marker.getTitle();
		Integer communityId = Integer.parseInt(marker.getSnippet());
		LogUtils.i(TAG, "title: " + marker.getTitle() + "id: " + communityId);
		int topicCount=topicMap.get(communityId);
		final Model_Community community = new Model_Community(communityId,
				communityName,topicCount);
		rightIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (badge == R.drawable.common_added) {
					Toast.makeText(SelectLiveAreaMapActivity.this,
							"您已经添加过该小区了哟", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent data = new Intent();
				data.putExtra("community", community);
				setResult(FORMMAPSELECTCOMMUNITY, data);
				finish();
			}
		});

		// ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
		String title = marker.getTitle();
		TextView titleUi = ((TextView) view.findViewById(R.id.title));

		if (title != null) {
			SpannableString titleText = new SpannableString(title);
			titleText.setSpan(new ForegroundColorSpan(Color.WHITE), 0,
					titleText.length(), 0);
			titleUi.setText(titleText);
		} else {
			titleUi.setText("");
		}
		String snippet = marker.getSnippet();
		TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
		snippetUi.setVisibility(View.GONE);
		if (snippet != null) {
			SpannableString snippetText = new SpannableString(snippet);
			// snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0,
			// 10,0);
			snippetText.setSpan(new ForegroundColorSpan(Color.WHITE), 0,
					snippetText.length(), 0);
			snippetUi.setText(snippetText);
		} else {
			snippetUi.setText("");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		deactivate();
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation aLocation) {
		if (mListener != null) {
			mListener.onLocationChanged(aLocation);
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
		 */
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);

	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destory();
		}
		mAMapLocationManager = null;
	}

}

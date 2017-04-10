package com.sinaleju.lifecircle.app.activity;

import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.search.core.AMapException;
import com.amap.api.search.geocoder.Geocoder;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.map.AMapUtil;
import com.sinaleju.lifecircle.app.map.Constants;

public class BusLocationMapActivity extends FragmentActivity implements
		OnMarkerClickListener, AMapLocationListener, OnInfoWindowClickListener,
		OnMarkerDragListener, OnMapLoadedListener, InfoWindowAdapter {

	private AMap aMap;
	private LatLng mLatLng = null;
	private Geocoder coder = null; // 声明一个逆地理编码对象
	private View mWindow;
	private View mContents;

	private double lat = -1;
	private double lon = -1;
	private String addressName = "";
	private Builder builder = null;
	private BitmapDescriptor curIcon = null;
	private BitmapDescriptor otherIcon = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_bus_location_map);
		init();
	}

	private void init() {
		builder = new LatLngBounds.Builder();
		curIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.common_location_icon);
		otherIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.msg_main_location_press);
		initData();
		coder = new Geocoder(this);
	}

	private void initData() {
		lat = getIntent().getDoubleExtra("lat", -1);
		lon = getIntent().getDoubleExtra("lon", -1);
		addressName = getIntent().getStringExtra("addressName");
		if (lat != -1 && lon != -1) {
			setMap(lat, lon);
		}
	}

	private void setMap(double d, double e) {
		mLatLng = new LatLng(d, e);
		mWindow = getLayoutInflater().inflate(
				R.layout.view_map_custom_info_window, null);
		mContents = getLayoutInflater().inflate(
				R.layout.view_map_custom_info_contents, null);
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.bus_location_map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private Marker curMarker = null;
	private static final int MARKER_SHOW_INFO_WINDOW = 1201;

	private Handler mGeocoderHandler = new Handler() {
		public void handleMessage(Message msg) {
			// 如果有地址信息的消息发送过来，将文本框中设置为该地址信息
			if (msg.what == Constants.ERROR) {
				Toast.makeText(BusLocationMapActivity.this, "获取地址失败，请重试",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == MARKER_SHOW_INFO_WINDOW) {
				if (curMarker != null) {
					curMarker.showInfoWindow();
				}
			}
		}
	};

	private void setUpMap() {
		aMap.getUiSettings().setZoomControlsEnabled(true);// 隐藏缩放按钮
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		aMap.setOnMapLongClickListener(onMapLongClick);
		addMarkersToMap();// 往地图上添加marker
	}

	/**
	 * 绘制系统默认的10种marker背景图片
	 */
	public void addMarkersToMap() {
		MarkerOptions markerOptions = new MarkerOptions().position(mLatLng)
				.title(addressName).snippet(addressName).icon(curIcon);
		Marker marker = aMap.addMarker(markerOptions); // add
		marker.showInfoWindow();
		builder.include(markerOptions.getPosition());
	}

	private OnMapLongClickListener onMapLongClick = new OnMapLongClickListener() {

		@Override
		public void onMapLongClick(final LatLng point) {
			// TODO Auto-generated method stub
			new Thread() {

				public void run() {
					try {
						lat = point.latitude;
						lon = point.longitude;
						doMapLongClick(point);
					} catch (AMapException e) {
						// TODO Auto-generated catch block
						mGeocoderHandler.sendMessage(Message.obtain(
								mGeocoderHandler, Constants.ERROR));
					}
				}
			}.start(); // 线程启动
		}
	};

	private void doMapLongClick(final LatLng point) throws AMapException {
		// 逆地理编码getFromLocation()函数获取该点对应的前3个地址信息
		List<Address> address = coder.getFromLocation(point.latitude,
				point.longitude, 3);
		if (address != null) {
			// 获取第一个地址信息
			Address addres = address.get(0);
			addressName = "";
			if (addres.getAdminArea() != null)
				addressName += addres.getAdminArea();

			if (addres.getSubLocality() != null)
				addressName += addres.getSubLocality();

			if (addres.getFeatureName() != null)
				addressName += addres.getFeatureName();

			addressName += "附近";
			MarkerOptions markerOptions = new MarkerOptions().position(point)
					.title(addressName).snippet(addressName).icon(otherIcon);
			curMarker = aMap.addMarker(markerOptions);
			mGeocoderHandler.sendMessage(Message.obtain(mGeocoderHandler,
					MARKER_SHOW_INFO_WINDOW));

			builder.include(markerOptions.getPosition());
//			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(
//					builder.build(), 20));
		}
	}

	@Override
	public View getInfoContents(Marker marker) {
		render(marker, mContents);
		return mContents;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		render(marker, mWindow);
		return mWindow;
	}

	public void render(final Marker marker, View view) {
		int badge = R.drawable.location_goto;
		View textView = view.findViewById(R.id.mapwidndow_text);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				marker.hideInfoWindow();
			}

		});
		ImageView rightIcon = ((ImageView) view.findViewById(R.id.badge));
		rightIcon.setImageResource(badge);
		String title = marker.getTitle();
		TextView titleUi = ((TextView) view.findViewById(R.id.title));
		if (title != null) {
			titleUi.setText(title);
		} else {
			titleUi.setText("");
		}
		TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
		snippetUi.setVisibility(View.GONE);
		rightIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("lat", marker.getPosition().latitude);
				intent.putExtra("lon", marker.getPosition().longitude);
				intent.putExtra("addressName", marker.getTitle());
				setResult(501, intent);
				finish();
			}
		});
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation location) {

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}

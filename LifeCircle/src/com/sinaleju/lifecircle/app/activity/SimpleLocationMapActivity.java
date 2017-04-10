package com.sinaleju.lifecircle.app.activity;

import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.Projection;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.map.AMapUtil;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;

public class SimpleLocationMapActivity extends FragmentActivity implements OnMarkerClickListener,
		AMapLocationListener, OnInfoWindowClickListener, OnMarkerDragListener, OnMapLoadedListener,
		InfoWindowAdapter {

	private static final int MSG_LOCATION_DONE = 20;
	private AMap aMap;
	private LatLng mLatLng = null;
	private Marker mMarker;
	private BitmapDescriptor curIcon = null;
	private String location = "";
	private Builder builder = null;
	private View mWindow;
	private View mContents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_bus_location_map);
		init();
	}

	private void initTitle() {
		// mTitleBar.setTitleName("我的位置");
		// mTitleBar.showBackButton();
		// mTitleBar.setLeftButtonListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// finish();
		// }
		// });
	}

	private void init() {
		builder = new LatLngBounds.Builder();
		curIcon = BitmapDescriptorFactory.fromResource(R.drawable.common_location_icon);
		mWindow = getLayoutInflater().inflate(R.layout.view_map_custom_info_window, null);
		mContents = getLayoutInflater().inflate(R.layout.view_map_custom_info_contents, null);
		initTitle();
		initData();
	}

	private void initData() {
		double lat = getIntent().getDoubleExtra("lat", -1);
		double lon = getIntent().getDoubleExtra("lon", -1);
		location = getIntent().getStringExtra("location");
		if (lat == -1 || lon == -1) {
			locationCreate();
			enableMyLocation();
		} else {
			setMap(lat, lon);
		}
	}

	private void setMap(double d, double e) {
		mLatLng = new LatLng(d, e);
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
					R.id.bus_location_map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		aMap.getUiSettings().setZoomControlsEnabled(true);// 隐藏缩放按钮
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
		addMarkersToMap();
	}

	private void addMarkersToMap() {
		drawMarkers();
	}

	public void drawMarkers() {
		MarkerOptions markerOptions = new MarkerOptions().position(mLatLng).title(location)
				.snippet(location).icon(curIcon);
		mMarker = aMap.addMarker(markerOptions); // add
		mMarker.showInfoWindow();
		builder.include(markerOptions.getPosition());
	}

	@Override
	public boolean onMarkerClick(Marker m) {
		if (m.equals(mMarker)) {
			if (AMapUtil.checkReady(this, aMap)) {
				jumpPoint(m);
			}
		}
		return false;
	}

	public void jumpPoint(final Marker marker) {

		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = aMap.getProjection();
		Point startPoint = proj.toScreenLocation(mLatLng);
		startPoint.offset(0, -100);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;

		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed / duration);
				double lng = t * mLatLng.longitude + (1 - t) * startLatLng.longitude;
				double lat = t * mLatLng.latitude + (1 - t) * startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));
				if (t < 1.0) {
					handler.postDelayed(this, 16);
				}
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

		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			// String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				// cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}

			String str = "";
			if (desc != null) {
				String[] l = desc.split(" ");
				int index = 3;
				if (location.getProvince() == null) {
					index--;
				}
				if (location.getCity() == null) {
					index--;
				}
				if (location.getDistrict() == null) {
					index--;
				}

				if (l.length > index) {
					str = l[index];
				}

			}

			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("location", str);
			b.putDouble("lon", geoLng);
			b.putDouble("lat", geoLat);
			msg.setData(b);
			msg.what = MSG_LOCATION_DONE;
			if (mHandler != null) {
				mHandler.sendMessage(msg);
			}
		}

	}

	private ADHandler<SimpleLocationMapActivity> mHandler = new ADHandler<SimpleLocationMapActivity>(
			this, new MessageHandler<SimpleLocationMapActivity>() {

				@Override
				public void handleMessage(SimpleLocationMapActivity o, Message msg) {
					if (msg.what == MSG_LOCATION_DONE) {
						Bundle b = msg.getData();
						setMap(b.getDouble("lat"), b.getDouble("lon"));
					}
				}
			});

	private LocationManagerProxy mAMapLocManager = null;

	private void locationCreate() {
		mAMapLocManager = LocationManagerProxy.getInstance(this);
	}

	public void enableMyLocation() {
		mAMapLocManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	public void disableMyLocation() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
		}
	}

	private void locationDestory() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		disableMyLocation();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		locationDestory();
		super.onDestroy();
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub

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
		rightIcon.setVisibility(View.GONE);
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

}

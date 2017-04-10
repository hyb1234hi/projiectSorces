package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.map.AMapUtil;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageAddress;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_BusinessPageAddress extends AbsItemView implements
		AMapLocationListener {

	private TextView mBusAddressText;
	private TextView mAddressTitle;
	private AMap aMap;
	private MapView mapView;
	private RelativeLayout mMapLayout;

	public Item_BusinessPageAddress(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_bus_address,
				this);
		initViews();
	}

	private void initViews() {
		mBusAddressText = (TextView) findViewById(R.id.bus_address_text);
		mAddressTitle = (TextView) findViewById(R.id.bus_address_title);
		mapView = (MapView) findViewById(R.id.bus_item_map);
		mMapLayout = (RelativeLayout) findViewById(R.id.map_layout);
	}

	private void setUpMapIfNeeded(Model_BusinessPageAddress address) {
		mapView.onCreate(null);
		mapView.onResume();
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.getUiSettings().setZoomControlsEnabled(false);// 隐藏缩放按钮
			if (AMapUtil.checkReady(getContext(), aMap)) {
				aMap.addMarker(new MarkerOptions().position(
						new LatLng(address.getmLat(), address.getmLon())).icon(
						BitmapDescriptorFactory
								.fromResource(R.drawable.common_location_icon)));
			}
		}
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		Model_BusinessPageAddress address = (Model_BusinessPageAddress) model;
		if (!TextUtils.isEmpty(address.getAddress())) {
			mBusAddressText.setText(address.getAddress());
		} else {
			mBusAddressText.setText("");
		}
		if (address.isProperty()) {
			mAddressTitle.setText("办公位置");
		} else {
			mAddressTitle.setText("商家地址");
		}
		if (address.isMySelf()) {
			// set un-controls
			AppContext appContext = (AppContext) getContext()
					.getApplicationContext();
			appContext.getHomeActivity().addUnControledViews(mapView);
		}
		if (address.getmLat() > 0 && address.getmLon() > 0) {
			ApplicationFacade.getInstance().sendNotification(
					AppConst.APP_FACADE_BUSINESS_MAP_CONTROL
							+ getContext().hashCode(), mapView);
			mMapLayout.setVisibility(View.VISIBLE);
			mapView.setVisibility(View.VISIBLE);
			setUpMapIfNeeded(address);
//			enableMyLocation();
		} else {
			mMapLayout.setVisibility(View.GONE);
			mapView.setVisibility(View.GONE);
		}
		disableRefresh();
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

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
			aMap.addMarker(new MarkerOptions().position(
					new LatLng(geoLat, geoLng)).icon(
					BitmapDescriptorFactory
							.fromResource(R.drawable.msg_main_location_press)));
			locationDestory();
		}
	}

	private LocationManagerProxy mAMapLocManager = null;

	public void enableMyLocation() {
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
		 */
		mAMapLocManager = LocationManagerProxy.getInstance(getContext());
		mAMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
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

}

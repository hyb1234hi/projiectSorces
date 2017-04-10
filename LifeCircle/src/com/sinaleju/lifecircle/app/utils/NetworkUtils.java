package com.sinaleju.lifecircle.app.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	/**
	 * 判断网络连接状态
	 * 
	 * */
	public static boolean isNetworkAvailable(Context context) {
		try {
			ConnectivityManager manger = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 判断GPS状态
	 * 
	 * */
	public static boolean isOpenGPS(Context context) {
		LocationManager alm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return alm
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);

	}
	
	
}

package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import com.sinaleju.lifecircle.app.model.Model_AroundInfo;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_AroundInfo implements
		MultiJSONParserBase<MultiModelBase> {

	private static final String TAG = "JSONParser_AroundInfo";
	private final boolean TEST = false;
	public static List<MultiModelBase> data;
	public static int pageCount;
	public Model_AroundInfo markerOne = null;
	// private Map<String, List<MultiModelBase>> mReferenceMap = new
	// HashMap<String, List<MultiModelBase>>();

	public JSONParser_AroundInfo() {
		data = new LinkedList<MultiModelBase>();		
	}

	public  void initFirstMarker(Intent dataIntent) {
		//Intent dataIntent = LocationMapActivity.callInent;
		markerOne = new Model_AroundInfo();
		markerOne.setLongitude(dataIntent.getDoubleExtra("lon", 0));
		markerOne.setLatitude(dataIntent.getDoubleExtra("lat", 0));
		markerOne.setName(dataIntent.getStringExtra("location"));
		markerOne.setType("community");
		markerOne.setFans(-1);
		markerOne.setAddress("当前区域");

		// initTempList();
	}

	// public List<MultiModelBase> getDateList() {
	// return data;
	// }

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException {
		LogUtils.i(TAG, json);
		JSONObject jsonObj = new JSONObject(json);
		pageCount = jsonObj.optInt("page_count");
		List<MultiModelBase> aroundInfoList = new LinkedList<MultiModelBase>();
		JSONArray listArray = jsonObj.optJSONArray("list");

		// "name": "北空温泉招待所",
		// "longitude": "116.449487",
		// "latitude": "39.891741",
		// "type": "merchant",
		// "address": "东城区板厂南里2号",
		// "category": "酒店",
		// "distance": 200,
		// "fans": "0"

		for (int i = 0; i < listArray.length(); i++) {
			JSONObject itemJson = listArray.optJSONObject(i);
			Model_AroundInfo aroundModel = new Model_AroundInfo();
			aroundModel.setName(itemJson.optString("name"));
			aroundModel.setLongitude(itemJson.optDouble("longitude"));
			aroundModel.setLatitude(itemJson.optDouble("latitude"));
			aroundModel.setType(itemJson.optString("type"));
			aroundModel.setAddress(itemJson.optString("address"));
			aroundModel.setCategory(itemJson.optString("category"));
			aroundModel.setDistance(itemJson.optInt("distance"));
			aroundModel.setFans(itemJson.optInt("fans"));
			aroundInfoList.add(aroundModel);
		}
		data.addAll(aroundInfoList);
		if (markerOne != null) {
			Model_AroundInfo firstModel=(Model_AroundInfo) data.get(0);
			double longitude=firstModel.getLongitude();
			double latitude=firstModel.getLatitude();
			String name=firstModel.getName();
			//如果当前位置和周边商家列表中的第一个商家的位置不同且名字不同时，将当前位置作为一个新的周边信息添加到周边商家列表中。
			LogUtils.e(TAG, "curLon: "+markerOne.getLongitude()+"firstLong: "+longitude+" curLat: "+markerOne.getLatitude()+" firstLat: "+latitude +"curName: "+markerOne.getName()+"firstName: "+name);
			if (markerOne.getLongitude()==longitude&&markerOne.getLatitude()==latitude ||markerOne.getName().equals(name)) {
			}else{
				data.add(0, markerOne);
				LogUtils.e(TAG, "addCurrentLocation");				
			}
			markerOne=null;
		}
		return data;
	}

}

package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.NearbyBusinessBean;

public class JSONParser_NearbyBusinessList {

	
	public static ArrayList<NearbyBusinessBean> parser(String json){
		
		ArrayList<NearbyBusinessBean> list = null;
		JSONArray array = null;
		try {
			array = new JSONArray(json);
			if(null != array){
				list = new ArrayList<NearbyBusinessBean>();
				
				NearbyBusinessBean bean = null;
				for(int i=0; i<array.length(); i++){
					JSONObject subO = array.optJSONObject(i);
					if(null != subO){
						bean = new NearbyBusinessBean();
						bean.setDisplay_name(subO.optString("display_name"));
						bean.setDistance(subO.optString("distance"));
						bean.setIs_open(subO.optString("is_open"));
						bean.setPhone(subO.optString("phone"));
						bean.setVisitor_id(subO.optString("visitor_id"));
						
						list.add(bean);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}

package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.EviewModel;

public class JSONParser_AllComment {

	
	public static HashMap<String, Object> paser(String json){
		JSONObject object;
		HashMap<String, Object> map = null;
		try {
			object = new JSONObject(json);
			if(null != object){
				map = new HashMap<String, Object>();
				map.put("smallest_id", object.optString("smallest_id", "0"));
				map.put("surplus", object.optString("surplus", "0"));
				
				JSONArray array = object.optJSONArray("list");
				ArrayList<EviewModel> listData = null;
				EviewModel model;
				if(null != array){
					listData = new ArrayList<EviewModel>();
					
					for(int i=0; i<array.length(); i++){
						JSONObject o = array.getJSONObject(i);
						model = new EviewModel();
						model.setId(o.optString("id"));
						model.setImageUrl(o.optString("from_user_header"));
						model.setName(o.optString("from_user_name"));
						model.setText(o.optString("content"));
						model.setAttitudeRB(Float.valueOf(o.optString("attitude", "0")));
						model.setQualityRB(Float.valueOf(o.optString("quality", "0")));
						model.setVelocityRB(Float.valueOf(o.optString("speed", "0")));
						model.setPriceRB(Float.valueOf(o.optString("price", "0")));
						model.setTime(o.optString("add_time"));
						listData.add(model);
					}
				}
				
				map.put("list", listData);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
		return map;
	}
}

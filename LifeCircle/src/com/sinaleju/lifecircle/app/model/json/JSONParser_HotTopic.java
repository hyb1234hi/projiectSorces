package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.HotTopicBean;

public class JSONParser_HotTopic {

	public static HashMap<String, Object> parse(String json){
		
		HashMap<String, Object> resultMap = null;
		
		JSONObject object = null;
		ArrayList<HotTopicBean> list = null;
		try {
			object = new JSONObject(json);
			if(null != object){
				resultMap = new HashMap<String, Object>();
				resultMap.put("curPage", object.optInt("curPage"));
				resultMap.put("totalPage", object.optInt("totalPage"));
				
				JSONArray array = object.optJSONArray("list");
				if(array != null){
					list = new ArrayList<HotTopicBean>();
					
					JSONObject subO = null;
					HotTopicBean bean = null;
					for(int i=0; i<array.length(); i++){
						subO = array.optJSONObject(i);
						bean = new HotTopicBean();
						
						bean.setId(subO.optString("id"));
						bean.setName(subO.optString("name"));
						bean.setCount(subO.optString("count"));
						bean.setAddtime(subO.optString("add_time"));
						
						list.add(bean);
					}
				}
				
				resultMap.put("list", list);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}
}

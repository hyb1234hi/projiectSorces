package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.SinaFriendsBean;

public class JSONParser_SinaFriends {

	public static HashMap<String, Object> parserFromSina(String json){
		JSONObject object = null;
		HashMap<String, Object> map = null;
		
		ArrayList<SinaFriendsBean> list = null;
		try {
			object = new JSONObject(json);
			
			if(null != object){
				map = new HashMap<String, Object>();
				
				int totalNum = object.optInt("total_number");
				map.put("totalNum", totalNum);
				
				JSONArray array = object.optJSONArray("users");
				if(null != array){
					list = new ArrayList<SinaFriendsBean>();
					SinaFriendsBean bean = null;
					for(int i=0; i<array.length(); i++){
						JSONObject subO = array.optJSONObject(i);
						bean = new SinaFriendsBean();
						
						bean.setDescription(subO.optString("description"));
						bean.setId(subO.optString("id"));
						bean.setIdstr(subO.optString("idstr"));
						bean.setName(subO.optString("name"));
						bean.setScreen_name(subO.optString("screen_name"));
						bean.setProfile_image_url(subO.optString("profile_image_url"));
						
						list.add(bean);
					}
				}
				
				map.put("list", list);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static ArrayList<SinaFriendsBean> parserFromLocalService(ArrayList<SinaFriendsBean> listData, 
			String json){
		
		JSONArray array = null;
		try {
			array = new JSONArray(json);
			if(null != array){
				for(int i=0; i<Math.min(array.length() , listData.size()); i++){
					JSONObject subO = array.optJSONObject(i);
					if(i < listData.size()){
						listData.get(i).setItem_name(subO.optString("item_name"));
						listData.get(i).setStatus(subO.optString("status"));
						listData.get(i).setUserId(subO.optString("user_id"));
						listData.get(i).setType(subO.optString("type", "0"));
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listData;
	}
}

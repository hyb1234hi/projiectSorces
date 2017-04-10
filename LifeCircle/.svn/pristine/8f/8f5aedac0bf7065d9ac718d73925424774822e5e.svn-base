package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.PersonalChatBean;

public class JSONParser_PersonalChat {

	public static HashMap<String, Object> parser(String json){
		
		JSONObject object;
		HashMap<String, Object> map = null;
		try {
			object = new JSONObject(json);
			if(null != object){
				map = new HashMap<String, Object>();
				map.put("smallest_id", object.optInt("smallest_id", 0));
				map.put("total", object.optInt("total", 0));
				map.put("surplus", object.optInt("surplus", 0));
				
				JSONObject userObject = object.optJSONObject("user_info");
				map.put("user_info", parserUserInfo(userObject));
				
				JSONArray array = object.optJSONArray("list");
				ArrayList<PersonalChatBean> listData = null;
				PersonalChatBean model;
				if(null != array){
					listData = new ArrayList<PersonalChatBean>();
					
					for(int i=0; i<array.length(); i++){
						JSONObject o = array.getJSONObject(i);
						model = new PersonalChatBean();
						model.setId(o.optInt("id"));
						model.setContent(o.optString("content"));
						model.setAddtime(o.optInt("add_time"));
						model.setHave_read(o.optInt("have_read"));
						model.setUnread_num(o.optInt("unread_num"));
						model.setUser_id(o.optInt("user_id"));
						model.setTo_user_id(o.optInt("to_user_id"));
						
						JSONObject subUserObject = o.optJSONObject("user_info");
						model.setUser_info(parserUserInfo(subUserObject));
						
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
	
	private static PersonalChatBean.UserInfo parserUserInfo(JSONObject userObject){
		PersonalChatBean.UserInfo userInfo = new PersonalChatBean.UserInfo();
		if(null != userObject){
			userInfo.setId(userObject.optInt("id", 0));
			userInfo.setType(userObject.optInt("type", 0));
			userInfo.setDisplay_name(userObject.optString("display_name"));
			userInfo.setHeader(userObject.optString("header"));
			userInfo.setHeader(userObject.optString("header"));
		}
		
		return userInfo;
	}
	
}

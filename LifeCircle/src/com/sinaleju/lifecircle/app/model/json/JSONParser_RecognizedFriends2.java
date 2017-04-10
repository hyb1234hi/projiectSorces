package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.RecognizedFriendsBean;
import com.sinaleju.lifecircle.app.utils.LCPageCounter2;

public class JSONParser_RecognizedFriends2 {

	public static ArrayList<RecognizedFriendsBean> parse(String json,LCPageCounter2 counter){

		
		ArrayList<RecognizedFriendsBean> list = null;
		
		
		try {
			
		JSONObject jsonObj = new JSONObject(json);
		
		if(counter!=null){
			counter.setCurPage(jsonObj.optInt("curPage"));
			counter.setTotalPage(jsonObj.optInt("totalPage"));
		}
		
		JSONArray array = null;
		
			array = jsonObj.getJSONArray("list");
			
			if(null != array){
				
				list = new ArrayList<RecognizedFriendsBean>();
				
				JSONObject subO = null;
				RecognizedFriendsBean bean = null;
				for(int i=0; i<array.length(); i++){
					subO = array.optJSONObject(i);
					bean = new RecognizedFriendsBean();
					
					bean.setDisplay_name(subO.optString("display_name"));
					bean.setDisplay_pinyin(subO.optString("display_pinyin"));
					bean.setFans_num(subO.optString("fans_num"));
					bean.setFollow_num(subO.optString("follow_num"));
					bean.setHeader(subO.optString("header"));
					bean.setId(subO.optString("id"));
					bean.setIs_auth(subO.optString("is_auth"));
					bean.setLogin_name(subO.optString("login_name"));
					bean.setOrigin(subO.optString("origin"));
					bean.setType(subO.optString("type"));
					bean.setCommon(subO.optString("common"));
					bean.setStatus(subO.optString("is_follow", "1"));//本协议0是未关注，1是已关注。
					
					list.add(bean);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	
	}
	public static ArrayList<RecognizedFriendsBean> parse(String json){
		return parse(json,null);
	}
}

package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSSearchFriends extends StringRS {

	Map<String, String> params;
	
	public RSSearchFriends(){ 
		//no-args constructor
	}
	
	public RSSearchFriends(int uid,String keyword,int user_t,int audit_type){
		params = new HashMap<String, String>();
		params.put("keyword", keyword);
		params.put("user_id", uid+"");
		params.put("user_t", user_t+"");
		params.put("is_auth", audit_type+"");
	}
	
	public void setParams(Map<String, String> params){
		this.params = params;
	}
	@Override
	public Map<String, String> getUsingParams() {
		return params;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_SEARCH_FRIENDS_BY_KEY;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

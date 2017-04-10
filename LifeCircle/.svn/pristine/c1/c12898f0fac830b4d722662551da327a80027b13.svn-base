package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSAddFollower extends StringRS {

	Map<String, String> params;
	
	public RSAddFollower(){
		//no-args constructor
	}
	
	public RSAddFollower(int uid,String vid,String type){
		params = new HashMap<String, String>();
		params.put("user_id", uid+"");
		params.put("visitor_id", vid+"");
		params.put("type", type+"");
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
		return RemoteConst.URL_FOLLOW;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

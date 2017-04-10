package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSGetAddedCommunityList extends StringRS {
	private String user_id;
	

	public RSGetAddedCommunityList(String user_id) {
		//setNeedCreateToken(false);
		//setNeedParams(false);
		this.user_id = user_id;
		
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_ADDEDCOMMUNITY;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
	
		return map;
	}

}

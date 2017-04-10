package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSDeleteCommunity extends StringRS {
	private String user_id;
	private String community_id;
	


	public RSDeleteCommunity(String user_id,String community_id) {
		
		this.user_id=user_id;
		this.community_id=community_id;
		
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_COMMUNITY_DELETECOMMNUITY;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("community_id", community_id);
		
		return map;
	}

}

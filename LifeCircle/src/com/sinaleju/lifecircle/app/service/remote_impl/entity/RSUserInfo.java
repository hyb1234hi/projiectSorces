package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSUserInfo extends StringRS{
	private int uid;
	
	public RSUserInfo(int uid){
		this.uid = uid;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("user_id",uid+"");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_INFO;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSUserSetup extends StringRS {
	private String user_id;
	private String send_stats;


	public RSUserSetup(String user_id,String send_stats) {
		//setNeedCreateToken(false);
		//setNeedParams(false);
		this.user_id=user_id;
		this.send_stats=send_stats;
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_SETUP;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("send_status", send_stats);
		
		return map;
	}

}

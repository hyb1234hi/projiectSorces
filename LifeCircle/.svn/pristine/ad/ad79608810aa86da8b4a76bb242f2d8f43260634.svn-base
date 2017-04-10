package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSDeleteMsg extends StringRS{
	private int user_id;
	private int message_id;
	public RSDeleteMsg(int user_id,int message_id){
		this.user_id=user_id;
		this.message_id=message_id;
	}
	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("user_id", user_id+"");
		map.put("message_id", message_id+"");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_MSG_DELETE;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

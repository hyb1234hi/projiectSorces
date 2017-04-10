package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSMsgLike extends StringRS{
	private int user_id;
	private int msg_id;
	private int type;
	public RSMsgLike(int user_id,int msg_id,int type){
		this.user_id=user_id;
		this.msg_id=msg_id;
		this.type=type;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("user_id", user_id+"");
		map.put("msg_id", msg_id+"");
		map.put("type", type+"");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_MESSAGE_LIKE;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

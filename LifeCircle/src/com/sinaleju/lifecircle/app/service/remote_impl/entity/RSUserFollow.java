package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSUserFollow extends StringRS {

	private int user_id;
	private int visitor_id;
	private int type;

	public RSUserFollow(int user_id, int visitor_id, int type) {
		super();
		this.user_id = user_id;
		this.visitor_id = visitor_id;
		this.type = type;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (visitor_id > 0)
			map.put("visitor_id", visitor_id + "");
		if (type > -1)
			map.put("type", type + "");
		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_USER_FOLLOW;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

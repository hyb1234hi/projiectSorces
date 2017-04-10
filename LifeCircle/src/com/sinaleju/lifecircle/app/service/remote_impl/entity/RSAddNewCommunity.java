package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSAddNewCommunity extends StringRS {

	private String user_id;
	private String community_id;
	private String add_time;

	public RSAddNewCommunity(String user_id, String community_id, String add_time) {
		// setNeedCreateToken(false);
		// setNeedParams(false);
		this.user_id = user_id;
		this.community_id = community_id;
		this.add_time = add_time;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_COMMUNITY_ADDNEWCOMMNUITY;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("community_id", community_id);
		map.put("add_time", add_time);
		return map;
	}

}

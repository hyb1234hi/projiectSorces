package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSHomePageTop extends StringRS {
	private int community_id;
	private int user_id;

	public RSHomePageTop(int community_id, int user_id) {
		this.community_id = community_id;
		this.user_id = user_id;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("community_id", community_id + "");
		if (!isNullInt(user_id))
			map.put("user_id", user_id + "");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_HOME_PAGE_TOP;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

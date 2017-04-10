package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSEventsSquare extends StringRS {

	private int user_id;

	public RSEventsSquare(int user_id) {
		super();
		this.user_id = user_id;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_ACTIVITY_LIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		return map;
	}

}

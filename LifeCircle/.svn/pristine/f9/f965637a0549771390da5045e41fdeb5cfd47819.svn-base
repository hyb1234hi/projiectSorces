package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSTrendsDetailContent extends StringRS {

	private int msg_id;
	private int uid;

	public RSTrendsDetailContent(int msg_id, int uid) {
		this.msg_id = msg_id;
		this.uid = uid;
		setNeedProcessedParams(false);
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg_id", msg_id + "");
		if (!isNullInt(uid))
			map.put("user_id", uid + "");

		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_TRENDS_DETAIL_CONTENT;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSTrendsDetailDeliver extends StringRS {
	private int msgid;
	private int lastCommentId;
	private int size;

	public RSTrendsDetailDeliver(int msgid, int lastCommentId, int pageSize) {
		this.msgid = msgid;
		this.lastCommentId = lastCommentId;
		this.size = pageSize;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();

		map.put("msg_id", msgid + "");
		if (!isNullInt(size))
			map.put("size", size + "");
		if (!isNullInt(lastCommentId))
			map.put("last_forward_id", lastCommentId + "");

		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_TRENDS_DETAIL_DELIVER_LIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

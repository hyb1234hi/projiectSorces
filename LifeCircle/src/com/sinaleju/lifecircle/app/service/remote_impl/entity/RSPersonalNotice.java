package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSPersonalNotice extends StringRS {

	private int last_id;
	private int page_size;
	private int user_id;

	public RSPersonalNotice(int last_id, int page_size, int user_id) {
		super();
		this.last_id = last_id;
		this.page_size = page_size;
		this.user_id = user_id;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (last_id > 0)
			map.put("last_id", last_id + "");
		if (page_size > 0)
			map.put("page_size", page_size + "");
		if (user_id > 0)
			map.put("user_id", user_id + "");

		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_PERSONAL_NOTICE_LIST;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_GET;
	}

}

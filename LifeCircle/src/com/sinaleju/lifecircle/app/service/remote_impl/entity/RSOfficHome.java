package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSOfficHome extends StringRS {

	private int user_id;
	private int page;
	private int page_size;

	public RSOfficHome(int user_id, int page, int page_size) {
		super();
		this.user_id = user_id;
		this.page = page;
		this.page_size = page_size;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (page > 0)
			map.put("page", page + "");
		if (page_size > 0)
			map.put("page_size", page_size + "");
		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_NOTICE_MYTIP;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_GET;
	}

}

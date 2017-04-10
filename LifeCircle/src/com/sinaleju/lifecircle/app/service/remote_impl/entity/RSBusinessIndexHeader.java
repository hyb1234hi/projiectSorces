package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSBusinessIndexHeader extends StringRS {

	private int user_id;
	private int visitor_id;
	private boolean isProperty = false; // 是否是物业

	public RSBusinessIndexHeader(int user_id, int visitor_id, boolean isProperty) {
		super();
		this.user_id = user_id;
		this.visitor_id = visitor_id;
		this.isProperty = isProperty;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (visitor_id > 0)
			map.put("visitor_id", visitor_id + "");

		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		if (isProperty) {
			return RemoteConst.URL_PROPERTY_INDEX;
		} else {
			return RemoteConst.URL_BUSINESS_INDEX;
		}
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_GET;
	}

}

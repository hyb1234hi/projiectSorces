package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSTelHits extends StringRS {

	private int from_id;
	private int to_id;

	public RSTelHits(int from_id, int to_id) {
		super();
		this.from_id = from_id;
		this.to_id = to_id;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (from_id > 0)
			map.put("from_id", from_id + "");
		if (to_id > 0)
			map.put("to_id", to_id + "");

		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_TEL_HITS;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_GET;
	}

}

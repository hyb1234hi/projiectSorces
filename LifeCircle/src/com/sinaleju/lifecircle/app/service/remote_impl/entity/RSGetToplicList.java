package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSGetToplicList extends StringRS {
	private String community_id;
	private String page;
	private String size;
	private String type;

	public RSGetToplicList(String community_id, String page, String size,
			String type) {
		//setNeedCreateToken(false);
		//setNeedParams(false);
		this.community_id = community_id;
		this.page = page;
		this.type = type;
		this.size = size;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_TOPIC_LIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("community_id", community_id);
		if (!page.equals("")) {
			map.put("page", page);
		}
		if (!type.equals("")) {
			map.put("type", type);
		}
		if (!size.equals("")) {
			map.put("size", size);
		}
		return map;
	}

}

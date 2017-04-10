package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSSearchMessage extends StringRS {

	private int community_id = 0;
	private int user_id = 0;
	private int page = 0;
	private int page_size = 0;
	private String keyword = null;
	private int user_t,audit_type;
	public RSSearchMessage(int coummunity_id, String keyword, int user_id, int page, int page_size,int user_t,int audit_type) {
		this.community_id = coummunity_id;
		this.keyword = keyword;
		this.user_id = user_id;
		this.page = page;
		this.page_size = page_size;
		this.user_t = user_t;
		this.audit_type = audit_type;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("community_id", community_id + "");
		map.put("keyword", keyword);
		map.put("user_id", user_id+"");
		map.put("page", page+"");
		map.put("page_size", page_size+"");
		map.put("user_t", user_t+"");
		map.put("audit_type", audit_type+"");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_SEARCH_MESSAGE;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

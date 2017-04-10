package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSGetAttentionList extends StringRS {
	private String community_id;
	private String useId;
	private String follow_Type;
	private String recent;

	public RSGetAttentionList(String community_id, String userId,
			String follow_Type, String recent) {
		// setNeedCreateToken(false);
		// setNeedParams(false);
		this.community_id = community_id;
		this.useId = userId;
		this.follow_Type = follow_Type;
		this.recent = recent;

	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_PERSON_FOLLOWLIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", useId);
		if (!"".equals(community_id)) {
			map.put("community_id", community_id);
		}
		if (!"".equals(follow_Type)) {
			map.put("follow_type", follow_Type);
		}
		if (!"".equals(recent)) {
			map.put("recent", recent);
		}

		return map;
	}

}

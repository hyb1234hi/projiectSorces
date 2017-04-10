package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSSendMainMsg extends StringRS {

	private int community_id;
	private double coordinatex;
	private double coordinatey;
	private String location;
	private int user_id;
	private String content;
	private String topic;
	private String at;
	private String user_type;
	private String tag;

	public RSSendMainMsg(int community_id, double coordinatex, double coordinatey, String location,
			int user_id, String content, String topic, String at, String user_type, String tag) {
		super();
		this.community_id = community_id;
		this.coordinatex = coordinatex;
		this.coordinatey = coordinatey;
		this.location = location;
		this.user_id = user_id;
		this.content = content;
		this.topic = topic;
		this.at = at;
		this.user_type = user_type;
		this.tag = tag;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		if (community_id > 0)
			map.put("community_id", community_id + "");
		if (coordinatex > 0)
			map.put("coordinatex", coordinatex + "");
		if (coordinatey > 0)
			map.put("coordinatey", coordinatey + "");
		if (location != null)
			map.put("location", location);
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (content != null)
			map.put("content", content);
		if (topic != null)
			map.put("topic", topic);
		if (at != null)
			map.put("at", at);
		if (user_type != null)
			map.put("user_type", user_type);
		if (tag != null)
			map.put("tag", tag);
		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_MESSAGE_PUBLISH;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

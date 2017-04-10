package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSMsgUpload extends StringRS {
	
	private int user_id;
	private int msg_id;
	private String topic;
	private String at;
	private String content;
	private int forward;
	
	public RSMsgUpload(int user_id, int msg_id, String topic, String at,
			String content, int forward) {
		super();
		this.user_id = user_id;
		this.msg_id = msg_id;
		this.topic = topic;
		this.at = at;
		this.content = content;
		this.forward = forward;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (msg_id > 0)
			map.put("msg_id", msg_id + "");
		if (topic != null)
			map.put("topic", topic);
		if (at != null)
			map.put("at", at);
		if (content != null)
			map.put("content", content);
		if (forward > 0)
			map.put("forward", forward + "");
		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_MESSAGE_UPLOAD;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSMsgComment extends StringRS {

	private int user_id;
	private int msg_id;
	private String topic;
	private String at;
	private String user_type;
	private String content;
	private int forward;
	private int cid;
	private int forward_id;
	private String forward_content;
	private String tag;

	public RSMsgComment(int user_id, int msg_id, String topic, String at, String user_type,
			String content, int forward, int cid, int forward_id, String forward_content, String tag) {
		super();
		this.user_id = user_id;
		this.msg_id = msg_id;
		this.topic = topic;
		this.at = at;
		this.user_type = user_type;
		this.content = content;
		this.forward = forward;
		this.cid = cid;
		this.forward_id = forward_id;
		this.forward_content = forward_content;
		this.tag = tag;
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
		if (user_type != null)
			map.put("user_type", user_type);
		if (content != null)
			map.put("content", content);
		if (forward > 0)
			map.put("forward", forward + "");
		if (forward_id > 0)
			map.put("forward_id", forward_id + "");
		if (forward_content != null)
			map.put("forward_content", forward_content);
		if (tag != null)
			map.put("tag", tag);

		map.put("community_id", cid + "");

		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_MESSAGE_COMMENT;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

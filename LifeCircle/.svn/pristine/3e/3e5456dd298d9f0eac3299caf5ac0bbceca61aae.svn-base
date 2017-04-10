package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSTopicDetail extends StringRS{

	private int size;
	private int last_index_id;
	private int topic_id;
	private int user_id;
	public RSTopicDetail(int topic_id,int size,int last_id,int user_id){
		this.topic_id = topic_id;
		this.last_index_id =last_id;
		this.size=size;
		this.user_id=user_id;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		
		Map<String, String> map = new HashMap<String,String>();
		
		map.put("topic_id", topic_id+"");
		if(!isNullInt(size))
			map.put("size", size+"");
		if(!isNullInt(last_index_id))
			map.put("last_index_id", last_index_id+"");
		if(!isNullInt(user_id))
			map.put("user_id", user_id+"");
		return map ;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_TOPIC_DETAIL;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

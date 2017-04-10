package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSVisitingCard extends StringRS{

	private int uid;
	private int vid;
	
	public RSVisitingCard(int uid,int vid){
		this.uid = uid;
		this.vid = vid;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("user_id", uid+"");
		map.put("visitor_id", vid+"");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_VISITING_CARD;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

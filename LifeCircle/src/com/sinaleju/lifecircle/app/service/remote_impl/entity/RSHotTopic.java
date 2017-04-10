package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSHotTopic extends StringRS{

	Map<String, String> params;
	
	public void setParams(Map<String, String> params){
		this.params = params;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		return params;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_TOPIC_LIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSGetSinaFriends extends StringRS {

	Map<String, String> params;
	public void setPramas(Map<String, String> params){
		this.params = params;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		return params;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_INVITE_LIST;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_GET;
	}

}

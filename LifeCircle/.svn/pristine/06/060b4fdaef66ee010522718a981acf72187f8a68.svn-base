package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSAllComment extends StringRS {

	Map<String, String> parmas;
	
	public void setParams(Map<String, String> parmas){
		this.parmas = parmas;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		return parmas;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_ALL_COMMENT;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

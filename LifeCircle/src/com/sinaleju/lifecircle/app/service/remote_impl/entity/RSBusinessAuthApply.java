package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSBusinessAuthApply extends StringRS {

	Map<String, String> params;
	
	public void setParams(Map<String, String> params){
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
		return RemoteConst.URL_BUSINESS_AUTH_APPLY;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

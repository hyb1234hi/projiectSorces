package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

//
public class RSCommenHttp extends StringRS {
	
	private Map<String, String> params;
	private String url;
	private int method;
	
	public RSCommenHttp(String url, int method) {
		super();
		// TODO Auto-generated constructor stub
		this.url = url;
		this.method = method;
	}

	public void setParams(HashMap<String, String>  params){
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
		return url;
	}

	@Override
	public int getMethod() {
		return method;
	}

}

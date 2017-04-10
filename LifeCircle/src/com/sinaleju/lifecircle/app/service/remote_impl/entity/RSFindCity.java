package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSFindCity extends StringRS {

	

	public RSFindCity() {
		setNeedCreateToken(false);
		//setNeedParams(false);
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_COMMUNITY_FINDCITY;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String , String >();
		return map;
	}

}

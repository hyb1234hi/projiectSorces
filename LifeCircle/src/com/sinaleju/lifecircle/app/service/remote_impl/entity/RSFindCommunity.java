package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSFindCommunity extends StringRS {
	private String cityId;
	

	public RSFindCommunity(String cityId) {
		//setNeedCreateToken(false);
		//setNeedParams(false);
		this.cityId=cityId;
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_COMMUNITY_FINDCOMMUNITY;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("city_id", cityId);
		return map;
	}

}

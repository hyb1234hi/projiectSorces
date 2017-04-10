package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSRemoveRelation extends StringRS {
	private String platform_name;
	private String platform_id;
	

	public RSRemoveRelation(String platform_id,String platform_name) {
		//setNeedCreateToken(false);
		//setNeedParams(false);
		this.platform_id=platform_id;
		this.platform_name=platform_name;
		
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_REMOVE_RELATION;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform_id", platform_id);
		map.put("platform_name",platform_name);
		return map;
	}

}

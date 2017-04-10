package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSCheckVersion extends StringRS {
	private String version_number;
	private String type="0";
	

	public RSCheckVersion(String version_number) {
		setNeedCreateToken(false);
		this.version_number=version_number;
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_VERSION_CHECK_VERSION;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String , String >();
		map.put("version_number", version_number);
		map.put("type", type);
		return map;
	}

}

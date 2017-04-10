package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSGetAuthCode extends StringRS {

	private String mPhoneNumber;
	

	public RSGetAuthCode(String mPhoneNumber) {
		setNeedProcessedParams(false);
		this.mPhoneNumber = mPhoneNumber;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("login_name", mPhoneNumber);
		return map;

	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_AUTOCODE;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

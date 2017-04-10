package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSLogin extends StringRS {

	private String mUserName;
	private String mPassword;

	public RSLogin(String mUserName, String mPassword) {
		setNeedCreateToken(false);
		setNeedProcessedParams(false);
		//setNeedReturnEntireResult(true);
		this.mUserName = mUserName;
		this.mPassword = mPassword;

	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("login_name", mUserName);
		map.put("password", mPassword);
		return map;

	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_LOGIN;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

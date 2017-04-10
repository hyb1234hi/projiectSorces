package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSFindPassword extends StringRS {

	private String mUserName;
	

	public RSFindPassword(String mUserName) {

		this.mUserName = mUserName;
		

	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("login_name", mUserName);
		return map;

	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_FINDPASSWORD;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSCreateUser extends StringRS {
	private String userId;
	private String userName;
	private String authCode;
	private String newPassword;

	public RSCreateUser(String user_id, String user_name, String oath_code,
			String new_passwd) {
		setNeedCreateToken(false);
		userId = user_id;
		userName = user_name;
		authCode = oath_code;
		newPassword = new_passwd;

		// setNeedParams(false);
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_CREAT_USER;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", userId);
		map.put("login_name", userName);
		if (!"".equals(authCode)) {
			map.put("auth_code", authCode);
		}
		map.put("new_passwd", newPassword);
		return map;
	}

}

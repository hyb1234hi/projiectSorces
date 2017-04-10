package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSChangePassword extends StringRS {
	private String userId;
	private String oldPassword;
	private String newPassword;
	

	public RSChangePassword(String user_id,String old_passwd,String new_passwd) {
		setNeedCreateToken(false);
		userId=user_id;
		oldPassword=old_passwd;
		newPassword=new_passwd;
		
		//setNeedParams(false);
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_CHANGE_PASSWORD;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String , String >();
		map.put("user_id", userId);
		map.put("old_passwd",oldPassword);
		map.put("new_passwd", newPassword);
		return map;
	}

}

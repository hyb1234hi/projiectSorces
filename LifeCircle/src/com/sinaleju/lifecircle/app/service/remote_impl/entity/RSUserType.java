package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSUserType extends StringRS {

	

	public RSUserType() {
	//	setNeedCreateToken(false);
		//setNeedParams(false);
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_USERTYPE;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}



	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		return null;
	}

}

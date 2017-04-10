package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSCheckUnreadNotice extends StringRS {
	private int userId;
	
	

	public RSCheckUnreadNotice(int user_id) {
		setNeedCreateToken(false);
		userId=user_id;
		
	}

	

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_NOTICE_CHAT_NOTICE_UNREAD;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}



	@Override
	public Map<String, String> getUsingParams() {
		Map<String,String> map=new HashMap<String , String >();
		map.put("user_id", userId+"");
		
		return map;
	}

}

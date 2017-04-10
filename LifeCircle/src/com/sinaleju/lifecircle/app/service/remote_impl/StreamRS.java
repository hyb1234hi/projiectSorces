package com.sinaleju.lifecircle.app.service.remote_impl;

import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.sinaleju.lifecircle.app.service.RemoteService;

/**
 * 获取网络数据
 * @author zxd
 * 
 */

public abstract class StreamRS extends RemoteService {

	protected static final int RESULT_IMG = 1;
	protected static final int METHOD_STRING = 0;
	
	@Override
	protected Object onExecute(Context context) throws Exception {
		if (!TextUtils.isEmpty(getCustomUrl())) {
			if(getResultType() == 1){
				Bitmap	bmp = HttpClientUtils.getBitmapByUrlConn(getUsingParams(), getCustomUrl());			
				return bmp;
			}
		}
		return null;
	}
	
	/***/
	public abstract Map<String, String> getUsingParams();

	/***/
	public abstract String getCustomUrl();
	
	/***/
	public abstract int getResultType();
}

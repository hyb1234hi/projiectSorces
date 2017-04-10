package com.sinaleju.lifecircle.app.service.remote_impl;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.exception.ADNetworkNotAvailableException;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.RemoteService;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.NetworkUtils;

/**
 * 
 * @author sunny.dai
 * 
 */
public abstract class StringRS extends RemoteService {
	public static final int METHOD_POST = 1;
	public static final int METHOD_GET = 0;
	private static final int TIME_OUT = 20000;
	private static final String KEY_RESULT = "result";
	private static final String KEY_DATA = "data";
	private static final String KEY_MESSAGE = "message";

	public static final int NULL_INT = AppConst.NULL_INT;

	private static final int RESULT_CODE_REQUEST_FAIL = 0;
	private static final int RESULT_CODE_REQUEST_SUCCESS = 1;
	private static final String BASIC_URL = RemoteConst.URL_BASIC;
	private static final String TAG = "StringRS";
	// private Map<String, String> mapParameters = null;
	private boolean needReturnEntireResult = false;
	private boolean needParams = true;
	private boolean isNeedCreateToken = true;
	private boolean isNeedBasicUrl = true;
	private Builder builder = null;
	private boolean needProcessMsg = true;

	@Override
	protected Object onExecute(Context context) throws Exception {

//		 if (AppContext.TEST) {
//		 setNeedProcessedParams(false);
//		 }

		// 监测网络状态
		if (context != null && !NetworkUtils.isNetworkAvailable(context))
			throw new ADNetworkNotAvailableException("net work not found ");
		String result = null;

		if (getMethod() == METHOD_POST)//post 	请求
			result = HttpClientUtils.getStringByHttpClientPost(getProcessedParams(), getProcessedUrl(), StringRS.TIME_OUT);
		else if (getMethod() == METHOD_GET)//get 请求
			result = HttpClientUtils.getStringByHttpClientGet(getProcessedParams(), getProcessedUrl(), StringRS.TIME_OUT);

		LogUtils.d(TAG, "remote request result is : " + result);

		if (!isNeedProcessMsg())
			return result;

		// 包装结果
		JSONObject json = new JSONObject(result);

		// 返回码处理
		int resultCode = json.getInt(StringRS.KEY_RESULT);
		LogUtils.e(TAG, "resultCode  :: " + resultCode);
		String dataResult = null;
		switch (resultCode) {
		case StringRS.RESULT_CODE_REQUEST_SUCCESS:
			dataResult = json.getString(KEY_DATA);
			break;
		case StringRS.RESULT_CODE_REQUEST_FAIL:
		default:
			String msg = json.getString(KEY_MESSAGE);
			throw new ADRemoteException(msg != null ? msg : "");
		}

		return isNeedReturnEntireResult() ? result : dataResult;
	}

	/***/
	public abstract Map<String, String> getUsingParams();

	/***/
	public abstract String getCustomUrl();

	/***/
	public abstract int getMethod();

	/**
	 * 加工处理：url
	 * 
	 * @return
	 */
	private String getProcessedUrl() {
		if (isNeedBasicUrl())
			return getBasicUrl() + getCustomUrl();
		else
			return getCustomUrl();
	}

	/**
	 * 加工处理：添加系统（app）参数
	 * 
	 * @return
	 */
	private Map<String, String> getProcessedParams() {

		if (!isNeedProcessedParams())
			return getUsingParams();

		// processing
		Map<String, String> processedParams = getUsingParams();
		if (isNeedCreateToken()) {

			// createToken
			processedParams = createTokenParams(processedParams);

			processedParams.put(RemoteConst.REQUEST_KEY_KEY, RemoteConst.REQUEST_KEY_VALUE);
			
			// test
			// processedParams.put("is_print","1");

		}
		
		// else {
		// processedParams.put(RemoteConst.REQUEST_VALUE_SECRET,
		// RemoteConst.REQUEST_APP_VALUE);
		// }

		return processedParams;
	}

	private Map<String, String> createTokenParams(Map<String, String> processedParams) {
		return SinaParameterEncryptor.mapWithEncryptedToken(processedParams);
	}

	/**
	 * get basic url
	 * 
	 * @return
	 */
	private String getBasicUrl() {
		return StringRS.BASIC_URL;
	}

	public class Builder {

		public Builder setNeedBasicUrl(boolean isNeedBasicUrl) {
			StringRS.this.setNeedBasicUrl(isNeedBasicUrl);
			return this;
		}

		public Builder setNeedReturnEntireResult(boolean entire) {
			StringRS.this.setNeedReturnEntireResult(entire);
			return this;
		}

		public Builder setNeedParams(boolean needParams) {
			StringRS.this.setNeedProcessedParams(needParams);
			return this;
		}

		public Builder setNeedCreateToken(boolean isNeedCreateToken) {
			StringRS.this.setNeedCreateToken(isNeedCreateToken);
			return this;
		}

		public Builder setNeedProcessMsg(boolean process) {
			StringRS.this.setNeedProcessMsg(process);
			return this;
		}
	}

	public Builder builder() {
		if (builder == null)
			builder = new Builder();
		return builder;
	}

	private boolean isNeedBasicUrl() {
		return isNeedBasicUrl;
	}

	private boolean isNeedCreateToken() {
		return isNeedCreateToken;
	}

	private boolean isNeedReturnEntireResult() {
		return needReturnEntireResult;
	}

	private boolean isNeedProcessedParams() {
		return needParams;
	}

	private boolean isNeedProcessMsg() {
		return needProcessMsg;
	}

	protected void setNeedProcessMsg(boolean process) {
		this.needProcessMsg = process;
	}

	protected void setNeedReturnEntireResult(boolean entire) {
		this.needReturnEntireResult = entire;
	}

	protected void setNeedBasicUrl(boolean isNeedBasicUrl) {
		this.isNeedBasicUrl = isNeedBasicUrl;
	}

	protected void setNeedProcessedParams(boolean needParams) {
		this.needParams = needParams;
	}

	protected void setNeedCreateToken(boolean isNeedCreateToken) {
		this.isNeedCreateToken = isNeedCreateToken;
	}

	protected boolean isNullInt(int nullInt) {
		return nullInt == NULL_INT;
	}

	protected void put(Map<String, String> map, String key, Object value) {
		if (value == null || !(value instanceof String))
			return;

		if (value instanceof Integer) {
			Integer v = (Integer) value;
			if (v == NULL_INT)
				return;
			map.put(key, v + "");
		} else {
			map.put(key, value.toString());
		}
	}
}

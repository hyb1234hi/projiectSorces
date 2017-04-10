package com.sinaleju.lifecircle.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistCheckUtils {
	private static final String TAG = "RegistCheckUtils";
	/**
	 * 验证手机号码格式是否正确
	 * @param mobileNumber
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		//Pattern p = Pattern	.compile("^((13[0-9])|(15[//d&&[^4]])|(18[0,5-9]))//d{8}$");
		Pattern p = Pattern	.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobileNumber);
		LogUtils.e(TAG, "isphoneNumber"+m.matches());
		//LogUtils.i("RegistCheckUtils", ""+p.matcher("18610434319").matches());
		return m.matches();
	}
	/**
	 * 验证邮箱格式是否正确
	 * @param strEmail
	 * @return true,false
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		
		return m.matches();
	}
}

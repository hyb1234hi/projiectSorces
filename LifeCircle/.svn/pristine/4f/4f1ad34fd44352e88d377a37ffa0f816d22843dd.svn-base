package com.sinaleju.lifecircle.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ADPatternUtils {
	private static final String TAG = "ADPatternUtils";

	public static boolean isEmail(String email) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.find();
	}

	public static boolean isMobileNO(String mobiles) {
		// Pattern p = Pattern
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		LogUtils.e(TAG, mobiles + "isMobileN---" + m.matches());
		return m.matches();
	}

	public static boolean isZipCode(String zipcode) {
		Pattern p = Pattern.compile("[1-9]\\d{5}(?!\\d)");
		Matcher m = p.matcher(zipcode);
		return m.matches();
	}
}

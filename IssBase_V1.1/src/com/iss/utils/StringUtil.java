package com.iss.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.text.TextUtils;

public class StringUtil {
	/**
	 * 将输入流转化成字符串
	 * 
	 * @param inputStream输入流
	 * @param encoding
	 *            字符编码类型,如果encoding传入null，则默认使用utf-8编码。
	 * @return 字符串
	 * @throws IOException
	 * @author lvmeng
	 */
	public static String inputToString(InputStream inputStream, String encoding) throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		inputStream.close();
		bos.close();
		if (TextUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		return new String(bos.toByteArray(), encoding);
	}
}

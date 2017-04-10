package com.sinaleju.lifecircle.app.service.remote_impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;

import com.sinaleju.lifecircle.app.utils.Md5Util;

@SuppressLint("DefaultLocale")
public class SinaParameterEncryptor {
	public static Map<String, String> mapWithEncryptedToken(Map<String, String> map) {

		// 排序
		Set<String> set = map.keySet();
		String[] keys = new String[set.size()];
		set.toArray(keys);
		Arrays.sort(keys);

		System.out.println("2" + Arrays.toString(keys));
		// 拼接
		StringBuffer paramString = new StringBuffer();
		for (int i = 0; i < keys.length; i++) {
			paramString.append(keys[i] + map.get(keys[i]));
		}

		// 合成token
		String token = RemoteConst.REQUEST_VALUE_SECRET + paramString.toString() + RemoteConst.REQUEST_VALUE_SECRET;
		System.out.println("token2=" + token);
		// md5
		token = Md5Util.getMD5Str(token);

		// 转换大写
		token = token.toUpperCase();

		System.out.println("token2MD5=" + token);

		// 添加map
		map.put(RemoteConst.REQUEST_KEY_TOKEN, token);

		return map;
	}

}

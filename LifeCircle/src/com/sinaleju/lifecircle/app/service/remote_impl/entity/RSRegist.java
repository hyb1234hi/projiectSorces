package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSRegist extends StringRS {

	private String mAccountName;
	private String mCategory;
	private int mCommunityId;
	private String mType;
	private String mNickName;
	private String mPassword;

	private String mServicePhone;
	private String mCheckNumber;

	public RSRegist(String mCategory,String mAccountName, String mCheckNumber,
			int mCommunityId, String mType, String mNickName,
			String mPassword, String mServicePhone) {
		this.mCategory=mCategory;
		this.mAccountName = mAccountName;
		this.mCommunityId = mCommunityId;
		this.mType = mType;
		this.mNickName = mNickName;
		this.mPassword = mPassword;

		this.mCheckNumber = mCheckNumber;
		this.mServicePhone = mServicePhone;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("category", mCategory);
		map.put("login_name", mAccountName);
		map.put("password", mPassword);
		map.put("type", mType);
		map.put("display_name", mNickName);
		map.put("community_id", mCommunityId+"");
		if (!"".equals(mCheckNumber)) {
			map.put("auth_code", mCheckNumber);
		}
		if (!"".equals(mServicePhone)) {
			map.put("service_phone", mServicePhone);
		}

		return map;

	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_REGIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

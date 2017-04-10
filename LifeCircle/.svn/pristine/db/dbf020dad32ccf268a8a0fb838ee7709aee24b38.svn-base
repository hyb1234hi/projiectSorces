package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSCompleteInfo extends StringRS {

	private String mCommunityId;
	private String mType;
	private String display_name;
	private String mCategory;
	private String plateform_header;
	private String mServicePhone;
	private String platform_name;
	private String platform_id;
	private String param;
	//private String timeOut;
	//private String token;
	

	public RSCompleteInfo(String mFid, String mCategory, String mCommunityId,
			String mType, String mNickName, String mOrigin, String mServicePhone, String mHeadImageUrl, String param /*String mToken, String mExpiresTime*/) {
		this.platform_id = mFid;
		this.mCommunityId = mCommunityId;
		this.mType = mType;
		this.display_name = mNickName;
		this.mCategory = mCategory;
		this.platform_name = mOrigin;
		this.mServicePhone = mServicePhone;
		this.plateform_header=mHeadImageUrl;
		this.param=param;
		//this.token=mToken;
		//this.timeOut=mExpiresTime;
		setNeedCreateToken(false);
		setNeedReturnEntireResult(true);
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform_id", platform_id);
		map.put("platform_name", platform_name);
		map.put("type", mType);
		map.put("display_name", display_name);
		map.put("community_id", mCommunityId);
		map.put("category", mCategory);
		//map.put("token", token);
		//map.put("timeout", timeOut);
		map.put("param", param);
		map.put("platform_header", plateform_header);
		
		if (!"".equals(mServicePhone)) {
			map.put("service_phone", mServicePhone);
		}

		return map;

	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_USER_COMPLETEINFO;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_POST;
	}

}

package com.sinaleju.lifecircle.app.model;

import org.json.JSONObject;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Model_UserInfo extends MultiModelBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1659974509358930981L;
	public static final String TEST_HEADER = "http://f.hiphotos.baidu.com/album/w%3D2048/sign=62c6670f06082838680ddb148ca1a801/08f790529822720e494a2eea7acb0a46f21fab1e.jpg";
	public static final String TEST_HEADER2 = "http://ww4.sinaimg.cn/bmiddle/7d43dc81jw1dsmj8uzpy0j.jpg";
	public static final String TEST_HEADER3 = "http://www.qqw21.com/article/uploadpic/2012-8/201281920215154238.jpg";
	public static final String TEST_HEADER4 = "http://tupian.hbrc.com/qqjia/0/52/5216_1370010895511111.jpg";
	public static final String TEST_HEADER5 = "http://www.qqw21.com/article/uploadpic/2012-9/201291952844572.jpg";
	private static final String TAG = "Model_UserInfo";

	private int uid;
	private int type;
	private String name;
	private String header;
	private boolean VIP;

	public void parse(JSONObject userinfo) {

		if (userinfo == null) {
			LogUtils.e(TAG, "userinfo is null");
			return;
		}
		setName(userinfo.optString("display_name"));
		setVIP(userinfo.optInt("is_auth"));
		setHeader(userinfo.optString("header"));
		setType(userinfo.optInt("type"));
		setUid(userinfo.optInt("id"));

	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isVIP() {
		return VIP;
	}

	public void setVIP(int vip) {
		if (vip > 0) {
			setVIP(true);
		}

	}

	public void setVIP(boolean VIP) {
		this.VIP = VIP;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return null;
	}

	
}

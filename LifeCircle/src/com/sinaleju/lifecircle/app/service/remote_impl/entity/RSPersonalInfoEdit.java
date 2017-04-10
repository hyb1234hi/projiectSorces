package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSPersonalInfoEdit extends StringRS {

	private int user_id;
	private String display_name;
	private int category;
	private int sex;
	private String birthday;
	private String astro;
	private String hometown;
	private String location;
	private String qq;
	private String mobile;
 
	public RSPersonalInfoEdit(int user_id, String display_name, int category,
			int sex, String birthday, String astro, String hometown,
			String location, String qq, String mobile) {
		super();
		this.user_id = user_id;
		this.display_name = display_name;
		this.category = category;
		this.sex = sex;
		this.birthday = birthday;
		this.astro = astro;
		this.hometown = hometown;
		this.location = location;
		this.qq = qq;
		this.mobile = mobile;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (display_name != null)
			map.put("display_name", display_name);
		if (category > 0)
			map.put("category", category + "");
		if (sex > -1)
			map.put("sex", sex + "");
		if (birthday != null)
			map.put("birthday", birthday);
		if (astro != null)
			map.put("astro", astro);
		if (hometown != null)
			map.put("hometown", hometown);
		if (location != null)
			map.put("location", location);
		if (qq != null)
			map.put("qq", qq);
		if (mobile != null)
			map.put("mobile", mobile);
		
		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_PERSONAL_EDIT_INFO;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

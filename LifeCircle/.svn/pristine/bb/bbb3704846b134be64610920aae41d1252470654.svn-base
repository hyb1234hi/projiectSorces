package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSPropertyInfoEdit extends StringRS {

	private int user_id;
	private String display_name;
	private String phone;
	private String address;
	private double longitude;
	private double latitude;
	private String introduction;
	private int normal_am_start;
	private int normal_am_end;
	private int normal_pm_start;
	private int normal_pm_end;
	private int halt_am_start;
	private int halt_am_end;
	private int halt_pm_start;
	private int halt_pm_end;
	private String map_address;

	public RSPropertyInfoEdit(int user_id, String display_name, String phone,
			String address, double longitude, double latitude,
			String introduction, int normal_am_start, int normal_am_end,
			int normal_pm_start, int normal_pm_end, int halt_am_start,
			int halt_am_end, int halt_pm_start, int halt_pm_end,
			String map_address) {
		super();
		this.user_id = user_id;
		this.display_name = display_name;
		this.phone = phone;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.introduction = introduction;
		this.normal_am_start = normal_am_start;
		this.normal_am_end = normal_am_end;
		this.normal_pm_start = normal_pm_start;
		this.normal_pm_end = normal_pm_end;
		this.halt_am_start = halt_am_start;
		this.halt_am_end = halt_am_end;
		this.halt_pm_start = halt_pm_start;
		this.halt_pm_end = halt_pm_end;
		this.map_address = map_address;
	}

	@Override
	public Map<String, String> getUsingParams() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		if (user_id > 0)
			map.put("user_id", user_id + "");
		if (display_name != null)
			map.put("display_name", display_name);
		if (phone != null)
			map.put("phone", phone);
		if (address != null)
			map.put("address", address);
		if (longitude > 0)
			map.put("longitude", longitude + "");
		if (latitude > 0)
			map.put("latitude", latitude + "");
		if (introduction != null)
			map.put("introduction", introduction);
		if (normal_am_start > -1)
			map.put("normal_am_start", normal_am_start + "");
		if (normal_am_end > -1)
			map.put("normal_am_end", normal_am_end + "");
		if (normal_pm_start > -1)
			map.put("normal_pm_start", normal_pm_start + "");
		if (normal_pm_end > -1)
			map.put("normal_pm_end", normal_pm_end + "");
		if (halt_am_start > -1)
			map.put("halt_am_start", halt_am_start + "");
		if (halt_am_end > -1)
			map.put("halt_am_end", halt_am_end + "");
		if (halt_pm_start > -1)
			map.put("halt_pm_start", halt_pm_start + "");
		if (halt_pm_end > -1)
			map.put("halt_pm_end", halt_pm_end + "");
		if (map_address != null)
			map.put("map_address", map_address);

		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_PROPERTY_EDIT_INFO;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

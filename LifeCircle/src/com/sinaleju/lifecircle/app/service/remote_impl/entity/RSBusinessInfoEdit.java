package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSBusinessInfoEdit extends StringRS {

	private int user_id;
	private String display_name;
	private String category;
	private String subcategory;
	private String biz_method;
	private String phone;
	private String address;
	private double longitude;
	private double latitude;
	private String traffic_routes;
	private String per_capita;
	private String introduction;
	private int normal_am_start;
	private int normal_am_end;
	private int normal_pm_start;
	private int normal_pm_end;
	private int halt_am_start;
	private int halt_am_end;
	private int halt_pm_start;
	private int halt_pm_end;
	private String specials_title;
	private String specials_height_price;
	private String specials_low_price;
	private String specials_content;
	private String map_address;

	public RSBusinessInfoEdit(int user_id, String display_name,
			String category, String subcategory, String biz_method,
			String phone, String address, double longitude, double latitude,
			String traffic_routes, String per_capita, String introduction,
			int normal_am_start, int normal_am_end, int normal_pm_start,
			int normal_pm_end, int halt_am_start, int halt_am_end,
			int halt_pm_start, int halt_pm_end, String specials_title,
			String specials_height_price, String specials_low_price,
			String specials_content, String map_address) {
		super();
		this.user_id = user_id;
		this.display_name = display_name;
		this.category = category;
		this.subcategory = subcategory;
		this.biz_method = biz_method;
		this.phone = phone;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.traffic_routes = traffic_routes;
		this.per_capita = per_capita;
		this.introduction = introduction;
		this.normal_am_start = normal_am_start;
		this.normal_am_end = normal_am_end;
		this.normal_pm_start = normal_pm_start;
		this.normal_pm_end = normal_pm_end;
		this.halt_am_start = halt_am_start;
		this.halt_am_end = halt_am_end;
		this.halt_pm_start = halt_pm_start;
		this.halt_pm_end = halt_pm_end;
		this.specials_title = specials_title;
		this.specials_height_price = specials_height_price;
		this.specials_low_price = specials_low_price;
		this.specials_content = specials_content;
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
		if (category != null)
			map.put("category", category + "");
		if (subcategory != null)
			map.put("subcategory", subcategory + "");
		if (biz_method != null)
			map.put("biz_method", biz_method);
		if (phone != null)
			map.put("phone", phone);
		if (address != null)
			map.put("address", address);
		if (longitude > 0)
			map.put("longitude", longitude + "");
		if (latitude > 0)
			map.put("latitude", latitude + "");
		if (traffic_routes != null)
			map.put("traffic_routes", traffic_routes);
		if (per_capita != null)
			map.put("per_capita", per_capita);
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
		if (specials_title != null)
			map.put("specials_title", specials_title);
		if (specials_height_price != null)
			map.put("specials_height_price", specials_height_price);
		if (specials_low_price != null)
			map.put("specials_low_price", specials_low_price);
		if (specials_content != null)
			map.put("specials_content", specials_content);
		if (map_address != null)
			map.put("map_address", map_address);
		
		return map;
	}

	@Override
	public String getCustomUrl() {
		// TODO Auto-generated method stub
		return RemoteConst.URL_BUSINESS_EDIT_INFO;
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		return StringRS.METHOD_POST;
	}

}

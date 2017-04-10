package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSSearchAroundMapData extends StringRS {
	private String longitude; // 经度
	private String latitude; // 纬度
	private String keyWord ;// 距离半径
	private String per_page; // 每页显示条数
	private String pageCount;// 分页数

	public RSSearchAroundMapData(String longitude, String latitude,String keyWord,String pageCount,String perPage) {
		// setNeedCreateToken(false);
		// setNeedParams(false);
		this.latitude = latitude;
		this.longitude = longitude;
		this.keyWord=keyWord;
		this.pageCount = pageCount;
		this.per_page=perPage;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_MESSAGE_SEARCH_MAPDATA;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		map.put("keyword", keyWord);
		map.put("page", pageCount);		
		if (!"".equals(per_page)) {
			map.put("per_page", per_page);
		}
		return map;
	}

}

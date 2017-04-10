package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_AroundInfo;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_AroundInfo extends MultiModelBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3303157422861284015L;

	public Model_AroundInfo() {
	}

	private int distance;
	private String name;
	private double longitude;
	private double latitude;
	private String  type; //商家/物业
	private String address; //商家、物业地址
	private String category; //商家类型
	private int fans; //关注数

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_AroundInfo.class;
	}

}

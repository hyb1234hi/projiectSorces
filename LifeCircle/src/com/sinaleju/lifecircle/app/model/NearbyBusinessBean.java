package com.sinaleju.lifecircle.app.model;

public class NearbyBusinessBean {

	private String visitor_id;
	private String display_name;
	private String distance;
	private String phone;
	private String is_open;
	
	public NearbyBusinessBean(){
		
	}
	public String getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIs_open() {
		return is_open;
	}
	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}

}

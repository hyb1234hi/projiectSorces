package com.sinaleju.lifecircle.app.model;

import java.util.ArrayList;
import java.util.List;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_BusinessPageHeader;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_BusinessPageHeader extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4216510531560048463L;
	private String display_name;
	private String name;
	private String signature;
	private String background;
	private String header;
	private String fans_num;
	private String follow_num;
	private String traffic_routes;
	private String latitude;
	private String longitude;
	private List<VisitorsModel> visitors = new ArrayList<VisitorsModel>();
	private String visitor_follow_status;
	private boolean isMySelf;
	private boolean isProperty = false; // 是否是物业
	private int type = 0;
	private int is_auth;// 认证状态 0 未认证 1 待认证 2 已认证
	private int send_status = 1;

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFans_num() {
		return fans_num;
	}

	public void setFans_num(String fans_num) {
		this.fans_num = fans_num;
	}

	public String getFollow_num() {
		return follow_num;
	}

	public void setFollow_num(String follow_num) {
		this.follow_num = follow_num;
	}

	public String getTraffic_routes() {
		return traffic_routes;
	}

	public void setTraffic_routes(String traffic_routes) {
		this.traffic_routes = traffic_routes;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public List<VisitorsModel> getVisitors() {
		return visitors;
	}

	public void setVisitors(List<VisitorsModel> visitors) {
		this.visitors = visitors;
	}

	public String getVisitor_follow_status() {
		return visitor_follow_status;
	}

	public void setVisitor_follow_status(String visitor_follow_status) {
		this.visitor_follow_status = visitor_follow_status;
	}

	public boolean isMySelf() {
		return isMySelf;
	}

	public void setMySelf(boolean isMySelf) {
		this.isMySelf = isMySelf;
	}

	public boolean isProperty() {
		return isProperty;
	}

	public void setProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIs_auth() {
		return is_auth;
	}

	public void setIs_auth(int is_auth) {
		this.is_auth = is_auth;
	}

	public int getSend_status() {
		return send_status;
	}

	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_BusinessPageHeader.class;
	}

}

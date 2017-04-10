package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.database.entity.CommunityBean;

public class Visitor {
	private int community_id;
	private String community_name;
	private String property_name;
	private int property_id;
	private int topicCount;

	public Visitor(int community_id, String community_name,int  topicCount) {
		this.community_id = community_id;
		this.community_name = community_name;
		this.topicCount=topicCount;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public int getCommunity_id() {
		return community_id;
	}

	public String getCommunity_name() {
		return community_name;
	}
	
	public CommunityBean getCommunity(){
		CommunityBean bean = new CommunityBean();
		bean.setCid(community_id);
		bean.setName(community_name);
		return bean;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public int getProperty_id() {
		return property_id;
	}

	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}

}

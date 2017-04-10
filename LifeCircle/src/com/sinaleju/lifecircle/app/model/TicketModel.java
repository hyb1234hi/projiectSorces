package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

public class TicketModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4472766836734497922L;
	private int community_id;
	private String name;
	private String web_url;
	private String background;
	private String content;
	private int isJoin; // 1已加入，2未加入
	private int topic_num;

	public int getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(int community_id) {
		this.community_id = community_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(int isJoin) {
		this.isJoin = isJoin;
	}

	public int getTopic_num() {
		return topic_num;
	}

	public void setTopic_num(int topic_num) {
		this.topic_num = topic_num;
	}

}

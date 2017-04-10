package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

public class TopicOrAtModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3047543908215498027L;

	private int id;
	private String text;
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

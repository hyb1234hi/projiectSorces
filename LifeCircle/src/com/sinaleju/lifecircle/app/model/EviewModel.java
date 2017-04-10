package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

public class EviewModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5181664731342074913L;

	private String id = null;
	private String imageUrl = null;
	private String name = null;
	private String time = null;
	private String text = null;

	private float qualityRB, priceRB, velocityRB, attitudeRB;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getQualityRB() {
		return qualityRB;
	}

	public void setQualityRB(float qualityRB) {
		this.qualityRB = qualityRB;
	}

	public float getPriceRB() {
		return priceRB;
	}

	public void setPriceRB(float priceRB) {
		this.priceRB = priceRB;
	}

	public float getVelocityRB() {
		return velocityRB;
	}

	public void setVelocityRB(float velocityRB) {
		this.velocityRB = velocityRB;
	}

	public float getAttitudeRB() {
		return attitudeRB;
	}

	public void setAttitudeRB(float attitudeRB) {
		this.attitudeRB = attitudeRB;
	}
}

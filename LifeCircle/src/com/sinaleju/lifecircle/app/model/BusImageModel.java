package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

public class BusImageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7509455077821823413L;

	private String position = null;
	private String imageUrl = null;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

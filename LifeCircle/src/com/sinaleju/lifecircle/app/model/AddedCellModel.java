package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

public class AddedCellModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -207261465601061943L;

	private String id = null;
	private String name = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

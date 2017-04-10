package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

import android.widget.Button;

public class AttenBtnModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3634887505247299385L;

	private String id = null;
	private Button attenBtn = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Button getAttenBtn() {
		return attenBtn;
	}

	public void setAttenBtn(Button attenBtn) {
		this.attenBtn = attenBtn;
	}

}

package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

import com.sinaleju.lifecircle.app.AppConst;

public class VisitorsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6907872954744952203L;
	
	private int visitor_id ;
	private int type = AppConst.NULL_INT;
	private String visitor_header = null;


	public String getVisitor_header() {
		return visitor_header;
	}

	public void setVisitor_header(String visitor_header) {
		this.visitor_header = visitor_header;
	}

	public int getVisitor_id() {
		return visitor_id;
	}

	public void setVisitor_id(int visitor_id) {
		this.visitor_id = visitor_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}

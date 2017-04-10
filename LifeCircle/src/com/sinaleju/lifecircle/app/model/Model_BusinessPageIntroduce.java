package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_BusinessPageIntroduce;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_BusinessPageIntroduce extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3913087950454128558L;
	private String normal_am_start;
	private String normal_am_end;
	private String normal_pm_start;
	private String normal_pm_end;
	private String halt_am_start;
	private String halt_am_end;
	private String halt_pm_start;
	private String halt_pm_end;
	private String introduction;
	private boolean isProperty = false; // 是否是物业

	public String getNormal_am_start() {
		return normal_am_start;
	}

	public void setNormal_am_start(String normal_am_start) {
		this.normal_am_start = normal_am_start;
	}

	public String getNormal_am_end() {
		return normal_am_end;
	}

	public void setNormal_am_end(String normal_am_end) {
		this.normal_am_end = normal_am_end;
	}

	public String getNormal_pm_start() {
		return normal_pm_start;
	}

	public void setNormal_pm_start(String normal_pm_start) {
		this.normal_pm_start = normal_pm_start;
	}

	public String getNormal_pm_end() {
		return normal_pm_end;
	}

	public void setNormal_pm_end(String normal_pm_end) {
		this.normal_pm_end = normal_pm_end;
	}

	public String getHalt_am_start() {
		return halt_am_start;
	}

	public void setHalt_am_start(String halt_am_start) {
		this.halt_am_start = halt_am_start;
	}

	public String getHalt_am_end() {
		return halt_am_end;
	}

	public void setHalt_am_end(String halt_am_end) {
		this.halt_am_end = halt_am_end;
	}

	public String getHalt_pm_start() {
		return halt_pm_start;
	}

	public void setHalt_pm_start(String halt_pm_start) {
		this.halt_pm_start = halt_pm_start;
	}

	public String getHalt_pm_end() {
		return halt_pm_end;
	}

	public void setHalt_pm_end(String halt_pm_end) {
		this.halt_pm_end = halt_pm_end;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public boolean isProperty() {
		return isProperty;
	}

	public void setProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_BusinessPageIntroduce.class;
	}

}

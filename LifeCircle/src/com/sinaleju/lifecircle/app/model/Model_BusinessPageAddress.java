package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_BusinessPageAddress;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_BusinessPageAddress extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7719090206764061915L;
	private String address;
	private String busLocationText;
	private double mLon = -1;
	private double mLat = -1;
	private boolean isMySelf = true;
	private boolean isProperty = false; // 是否是物业

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusLocationText() {
		return busLocationText;
	}

	public void setBusLocationText(String busLocationText) {
		this.busLocationText = busLocationText;
	}

	public double getmLon() {
		return mLon;
	}

	public void setmLon(double mLon) {
		this.mLon = mLon;
	}

	public double getmLat() {
		return mLat;
	}

	public void setmLat(double mLat) {
		this.mLat = mLat;
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

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_BusinessPageAddress.class;
	}

}

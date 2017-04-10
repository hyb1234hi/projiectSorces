package com.sinaleju.lifecircle.app.model;

import java.util.ArrayList;
import java.util.List;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_BusinessPageImage;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_BusinessPageImage extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2420361149169109309L;
	private boolean isMySelf;
	private List<BusImageModel> images = new ArrayList<BusImageModel>();
	private boolean isProperty = false; // 是否是物业

	public boolean isMySelf() {
		return isMySelf;
	}

	public void setMySelf(boolean isMySelf) {
		this.isMySelf = isMySelf;
	}

	public List<BusImageModel> getImages() {
		return images;
	}

	public void setImages(List<BusImageModel> images) {
		this.images = images;
	}

	public boolean isProperty() {
		return isProperty;
	}

	public void setProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_BusinessPageImage.class;
	}

}

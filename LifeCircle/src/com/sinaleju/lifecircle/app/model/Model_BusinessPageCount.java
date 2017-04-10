package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_BusinessPageCount;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_BusinessPageCount extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1157090444553000997L;
	private int count = 0;
	private boolean isProperty = false; // 是否是物业

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isProperty() {
		return isProperty;
	}

	public void setProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_BusinessPageCount.class;
	}
}

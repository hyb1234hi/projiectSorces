package com.sinaleju.lifecircle.app.model;

import java.util.ArrayList;
import java.util.List;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_BusinessPageEview;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_BusinessPageEview extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4548226749857578178L;
	private int smallest_id;
	private int surplus;
	private int count;
	private List<EviewModel> eviewModels = new ArrayList<EviewModel>();
	private boolean isProperty = false; // 是否是物业

	public int getSmallest_id() {
		return smallest_id;
	}

	public void setSmallest_id(int smallest_id) {
		this.smallest_id = smallest_id;
	}

	public int getSurplus() {
		return surplus;
	}

	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<EviewModel> getEviewModels() {
		return eviewModels;
	}

	public void setEviewModels(List<EviewModel> eviewModels) {
		this.eviewModels = eviewModels;
	}

	public boolean isProperty() {
		return isProperty;
	}

	public void setProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_BusinessPageEview.class;
	}

}

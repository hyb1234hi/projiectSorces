package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_CategoryLabel;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_CategoryLabel extends MultiModelBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8406175468677624711L;
	public Model_CategoryLabel(){
	}
	public Model_CategoryLabel(String label){
		setmLabelString(label);
	}
	private String mLabelString;
	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_CategoryLabel.class;
	}
	public String getmLabelString() {
		return mLabelString;
	}
	public void setmLabelString(String mLabelString) {
		this.mLabelString = mLabelString;
	}

}

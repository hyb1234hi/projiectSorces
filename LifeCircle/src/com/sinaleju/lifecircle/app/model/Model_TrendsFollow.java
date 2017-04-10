package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsFollow;

public class Model_TrendsFollow extends Model_TrendsSimple{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7798053965064609993L;
	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_TrendsFollow.class;
	}

}

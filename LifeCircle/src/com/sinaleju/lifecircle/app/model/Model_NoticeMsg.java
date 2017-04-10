package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_NoticeMsg;

public class Model_NoticeMsg extends Model_TrendsMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 384695867886341803L;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_NoticeMsg.class;
	}
}
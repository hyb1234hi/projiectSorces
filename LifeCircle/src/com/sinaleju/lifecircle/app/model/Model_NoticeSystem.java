package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_NoticeSystem;

public class Model_NoticeSystem extends Model_TrendsBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1777868164624425337L;
	private String mSystemMsg;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_NoticeSystem.class;
	}

	public String getmSystemMsg() {
		return mSystemMsg;
	}

	public void setmSystemMsg(String mSystemMsg) {
		this.mSystemMsg = mSystemMsg;
	}

}

package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_NoticeMsgDeliver;

public class Model_NoticeDelive extends Model_TrendsMsgDeliver {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086807642307007895L;
	private boolean isShowHeadImage = true;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_NoticeMsgDeliver.class;
	}

	public boolean isShowHeadImage() {
		return isShowHeadImage;
	}

	public void setShowHeadImage(boolean isShowHeadImage) {
		this.isShowHeadImage = isShowHeadImage;
	}

}

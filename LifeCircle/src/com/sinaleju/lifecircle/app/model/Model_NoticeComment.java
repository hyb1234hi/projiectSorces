package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_NoticeComment;

public class Model_NoticeComment extends Model_NoticeDelive {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5086807642307007895L;
	private Model_NoticeMsg mCommentMsg;
	private boolean isShowHeadImage = true;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_NoticeComment.class;
	}

	public boolean isShowHeadImage() {
		return isShowHeadImage;
	}

	public void setShowHeadImage(boolean isShowHeadImage) {
		this.isShowHeadImage = isShowHeadImage;
	}

	public Model_NoticeMsg getmCommentMsg() {
		return mCommentMsg;
	}

	public void setmCommentMsg(Model_NoticeMsg mCommentMsg) {
		this.mCommentMsg = mCommentMsg;
	}

}

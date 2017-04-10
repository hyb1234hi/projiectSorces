package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_TrendsDetailMsg extends Item_TrendsMsg {

	public Item_TrendsDetailMsg(Context context) {
		super(context);
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void initViews() {
		super.initViews();

		mBottomLine.setVisibility(View.VISIBLE);
		mImgIndicatorAnimator.setVisibility(View.VISIBLE);

		setCommentCountSelected();
	}

	@Override
	protected void setFlag(Model_TrendsBase m) {
		// do-nothing
	}

	protected void resetContentViewPadding() {
		// mContentView.setPadding(mContentView.getPaddingLeft(),
		// mContentView.getPaddingTop(), mContentView.getPaddingRight(), 0);
	}

	@Override
	protected void seconderySet(int type, BaseModel model) {// 信息正文页
		// float d = getResources().getDisplayMetrics().density;
		// RelativeLayout.LayoutParams p = new
		// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
		// (int) (200*d));
		// mImgMsg.setLayoutParams(p);
		// mImgMsg.setScaleType(ScaleType.CENTER_CROP);
		super.seconderySet(2, model);

		// send
		ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_TRENDSDETAIL_MSG_ITEM,
				this);
	}

	public void setDeliverClick() {
		// onDeliverClick();
	}

	@Override
	protected void onCommentClick() {
		indicateToComment();
	}

	@Override
	protected void onDeliverClick() {
		indicateToDeliver();
	}

	@Override
	protected void animCommentCallback() {
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_COMMENT);
	}

	@Override
	protected void animDeliverCallback() {
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_DELIVER);
	}

	@Override
	protected void setMsg(Model_TrendsMsg m) {
		SpanText[] spans = m.getMSpanTexts();
		if (spans != null) {
			for (SpanText span : spans) {
				if (span.getSpanType() == SpanType.TEXT) {
					span.setItem_id(AppConst.NULL_INT);
					span.setTag(null);
				}
			}
			mTxtMsg.setText(TrendsSpan.createTrendsSpan(getContext(), spans));
			mTxtMsg.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}
}

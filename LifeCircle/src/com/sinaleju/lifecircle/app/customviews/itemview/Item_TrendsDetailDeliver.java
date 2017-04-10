package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailDeliver;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Item_TrendsDetailDeliver extends Item_TrendsMsgDeliver {

	// private View mLyotBottom;
	private TextView mCommentCountView;
	private TextView mDeliverCountView;
	private TextView mAgreeCountView;
	private View mLayoutComment;
	private View mLayoutDeliver;
	private View mLayoutAgree;
	private ImageView mImgDeliverAgree;

	public Item_TrendsDetailDeliver(Context context) {
		super(context);
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void initViews() {
		super.initViews();//possss
		LogUtils.e("Item_TrendsDetailDeliver", "initViews");
		// include deliver
		includeDeliver_CommentDeliverFavor = findViewById(R.id.includeDeliver_CommentDeliverFavor);
		includeDeliver_CommentDeliverFavor.setVisibility(View.VISIBLE);

		mCommentCountView = (TextView) includeDeliver_CommentDeliverFavor.findViewById(R.id.txtCommentCount);
		mDeliverCountView = (TextView) includeDeliver_CommentDeliverFavor.findViewById(R.id.txtDeliveredCount);
		mAgreeCountView = (TextView) includeDeliver_CommentDeliverFavor.findViewById(R.id.txtAgreeCount);
		mImgDeliverAgree = (ImageView) includeDeliver_CommentDeliverFavor.findViewById(R.id.imgAgree);
		mLayoutFavor = includeDeliver_CommentDeliverFavor.findViewById(R.id.lyotFavor);
		mLayoutDeliver = includeDeliver_CommentDeliverFavor.findViewById(R.id.linearDeliveredCount);
		mLayoutComment = includeDeliver_CommentDeliverFavor.findViewById(R.id.linearCommentCount);

		showIndicatiorLayout();
		resetContentViewPadding();
		
		mBottomLine.setVisibility(View.VISIBLE);
		mImgIndicatorAnimator.setVisibility(View.VISIBLE);
		
		setCommentCountSelected();

	}

	protected void resetContentViewPadding() {
		// mContentView.setPadding(mContentView.getPaddingLeft(),
		// mContentView.getPaddingTop(), mContentView.getPaddingRight(), 0);
	}

	@Override
	protected void setFlag(Model_TrendsBase m) {
		// do-nothing
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {
		
		float d = getResources().getDisplayMetrics().density;
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (200*d));
		mImgDelivered.setLayoutParams(p);
		mImgDelivered.setScaleType(ScaleType.CENTER_CROP);
		
		super.seconderySet(1,model);
		//
		mLyotDeliver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Model_TrendsDetailDeliver m = (Model_TrendsDetailDeliver) mModel;
				Intent intent = generateDetailIntent(m.getDeliveredMsg());
				getContext().startActivity(intent);
			}
		});

		mTxtTitle.setVisibility(View.GONE);

		// set
		final Model_TrendsDetailDeliver m = (Model_TrendsDetailDeliver) model;

		String agreeCount = m.getDeliveredMsg().getAgreeCount() + "";
		int d_count =  m.getDeliveredMsg().getDeliveredCount(); 
		int c_count =  m.getDeliveredMsg().getCommentCount();
		String deliverCount = d_count==0?"转发":d_count+"";
		String commentCount = c_count==0?"评论":c_count+"";
		int islike = m.getDeliveredMsg().getLike();
		LogUtils.e(VIEW_LOG_TAG, "islike  ：： " + islike);
		mCommentCountView.setText(commentCount);
		mDeliverCountView.setText(deliverCount);
		mDeliverCountView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Model_TrendsDetailDeliver m = (Model_TrendsDetailDeliver) mModel;
				Intent intent = generateDetailIntent(m.getDeliveredMsg());
				intent.putExtra("delivered", true);
				getContext().startActivity(intent);
			}
		});

		mCommentCountView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Model_TrendsDetailDeliver m = (Model_TrendsDetailDeliver) mModel;
				Intent intent = generateDetailIntent(m.getDeliveredMsg());
				getContext().startActivity(intent);
			}
		});

		mAgreeCountView.setText(agreeCount);
		mImgDeliverAgree.setImageResource(islike == 1 ? R.drawable.img_agree_checked : R.drawable.img_agree_unchecked);
		LogUtils.e("Item_TrendsDetailDeliver", "islike ：： " + islike);
		mImgDeliverAgree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int msgid = m.getDeliveredMsg().getMsg_id();
				int like = m.getDeliveredMsg().getLike() == 0 ? 1 : 0;
				requestLike(mAgreeCountView, mImgDeliverAgree, msgid, like, m.getDeliveredMsg());
			}
		});

		// send
		ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_TRENDSDETAIL_MSG_ITEM, this);
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
		ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_COMMENT);
	}

	@Override
	protected void animDeliverCallback() {
		ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_TRENDSDETAIL_REQUEST_DELIVER);
	}

	@Override
	protected void setDeliveredMsg(Model_TrendsMsg m) {
		// add name：
		SpanText[] arg = m.getMSpanTexts();
		if (arg == null) {
			return;
		}
		SpanText[] spans = new SpanText[arg.length + 1];
		spans[0] = new SpanText(SpanType.NAME, m.getUid(), m.getName() + ":", m.getU_type());
		for (int i = 0; i < arg.length; i++) {
			spans[i + 1] = arg[i];
			if (arg[i].getSpanType() == SpanType.TEXT) {
				arg[i].setItem_id(AppConst.NULL_INT);
			}
		}

		mTxtDeliveredMsg.setText(TrendsSpan.createTrendsSpan(getContext(), spans));
		mTxtDeliveredMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}
}

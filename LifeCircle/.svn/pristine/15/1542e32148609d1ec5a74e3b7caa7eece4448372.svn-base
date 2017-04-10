package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppCache;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.activity.TrendsDetailActivity;
import com.sinaleju.lifecircle.app.model.Model_NoticeComment;
import com.sinaleju.lifecircle.app.model.Model_NoticeMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_NoticeComment extends AbsTrendsItem {

	private TextView mNoticeTitle;
	protected TextView mTxtMsg;
	private TextView mFromWhere;
	private TextView mCommMsg;

	public Item_NoticeComment(Context context) {
		super(context);
	}

	@Override
	protected View initContentView() {

		View rootView = LayoutInflater.from(getContext()).inflate(
				R.layout.item_notice_meg, mContentView, true);

		initViews(rootView);

		return rootView;
	}

	protected void initViews(View rootView) {
		mNoticeTitle = (TextView) rootView.findViewById(R.id.notice_title);
		mTxtMsg = (TextView) rootView.findViewById(R.id.txtMsg);
		mFromWhere = (TextView) rootView.findViewById(R.id.from_where);
		mCommMsg = (TextView) rootView.findViewById(R.id.comm_msg);
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {
		Model_NoticeComment deliveredMsg = (Model_NoticeComment) model;
		setTextDeliveredTitle(deliveredMsg);

		Model_NoticeMsg comm = deliveredMsg.getmCommentMsg();
		if (comm != null) {
			setCommTitle(comm);
			setCommMsg(comm);
		}

		if (!TextUtils.isEmpty(deliveredMsg.getCommunity_name())) {
			mFromWhere.setText("来自" + deliveredMsg.getCommunity_name());
		} else {
			mFromWhere.setText("");
		}
	}

	private void setTextDeliveredTitle(Model_TrendsMsg msgModel) {
		// setTitle
		setTextTitle(msgModel);
	}

	private void setCommTitle(Model_NoticeMsg m) {
		String name = m.getName().trim() + " ";
		SpannableString span = TrendsSpan.createTrendsSpan(getContext(),
				new SpanText(SpanType.NAME, m.getUid(), name, m.getU_type()));

		if (m.isVIP()) {
			span.setSpan(new ImageSpan(getContext(), R.drawable.icon_vip),
					name.length() - 1, name.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		mNoticeTitle.setText(span);
		mNoticeTitle.setMovementMethod(LinkMovementMethod.getInstance());

	}

	protected void setCommMsg(Model_NoticeMsg m) {
		SpanText[] spans = m.getMSpanTexts();
		if (spans != null) {
			for (SpanText span : spans) {
				if (span.getSpanType() == SpanType.TEXT) {
					span.setItem_id(AppConst.NULL_INT);
				}
			}
			mCommMsg.setText(TrendsSpan.createTrendsSpan(getContext(), spans));
			mCommMsg.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}

	private void setTextTitle(Model_TrendsMsg m) {
		// add name：
		SpanText[] arg = m.getMSpanTexts();
		if (arg == null) {
			return;
		}
		SpanText[] spans = new SpanText[arg.length + 1];
		spans[0] = new SpanText(SpanType.NAME, m.getUid(), m.getName() + ":",
				m.getU_type());
		for (int i = 0; i < arg.length; i++) {
			spans[i + 1] = arg[i];
		}

		mTxtMsg.setText(TrendsSpan.createTrendsSpan(getContext(), spans));
		mTxtMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	protected boolean isNeedHeadPortrait() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {
		// TODO Auto-generated method stub
		if (model == null || !(model instanceof Model_TrendsMsg)) {
			return;
		}
		Model_NoticeComment deliveredMsg = (Model_NoticeComment) model;
		if (deliveredMsg.getDeliveredMsg() == null) {
			model = new Model_TrendsDetailMsg((Model_TrendsMsg) model);
		}
		Intent intent = generateDetailIntent(model);
		getContext().startActivity(intent);

	}

	/**
	 * @param model
	 * @return
	 */
	protected Intent generateDetailIntent(BaseModel model) {
		AppCache.getInstance().put("originalDetailModel",
				(Model_TrendsMsg) model);
		Intent intent = new Intent(getContext(), TrendsDetailActivity.class);
		// 为了体验，这里依然需要通过序列化传送 之后在act启动后 如果之前由于筛选 网络延迟导致的
		// model被置空回收，则通过appCache去判断
		intent.putExtra("msg", model);
		return intent;
	}
}

package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.model.Model_TrendsComment;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Item_TrendsComment extends AbsTrendsItem {

	private static final String TAG = "Item_TrendsComment";
	private TextView mTextTitle;
	private TextView mTextComment;

	public Item_TrendsComment(Context context) {
		super(context);
	}

	@Override
	protected View initContentView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_trends_simple,
				mContentView, true);
		mTextTitle = (TextView) findViewById(R.id.txtTitle);
		mTextComment = (TextView) findViewById(R.id.txtComment);
		mTextComment.setVisibility(View.VISIBLE);
		return null;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

	@Override
	protected boolean isNeedHeadPortrait() {
		return true;
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {

		if (!(model instanceof Model_TrendsComment)) {
			LogUtils.e(TAG, "class cast error");
			return;
		}

		Model_TrendsComment commentModel = (Model_TrendsComment) model;

		// title
		if (commentModel.getTrendsType().equals(TrendsType.CHANGE_SIGN)) {
			if (commentModel.getmUserType() == 0) {
				mTextTitle.setText("更新了签名");
			} else if (commentModel.getmUserType() == 1) {
				mTextTitle.setText("更新了商家介绍");
			} else if (commentModel.getmUserType() == 2) {
				mTextTitle.setText("更新了物业介绍");
			}
		} else if (commentModel.getTrendsType().equals(TrendsType.COMMENT)) {
			SpannableString titleSpan = TrendsSpan.createTrendsSpan(
					getContext(), commentModel.getMSpanTexts());
			mTextTitle.setText(titleSpan);
			mTextTitle.setMovementMethod(LinkMovementMethod.getInstance());
		}
		// comment
		mTextComment.setText(commentModel.getComment());

	}
}

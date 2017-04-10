package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailComment;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.ADTimeUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_TrendsDetailComment extends AbsItemView {
	private ImageView mHeaderPortrait = null;
	private TextView mTxtName = null;
	private TextView mTxtMsg = null;
	private TextView mTxtTime = null;
	private View mLine;
	public Item_TrendsDetailComment(Context context) {
		super(context);
		recheck();
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.item_trends_detail_comment, this);
		initViews();
	}

	private void recheck() {
		if (mHeaderPortrait == null)
			initViews();
	}

	protected void initViews() {
		mHeaderPortrait = (ImageView) findViewById(R.id.imgHeadPortrait);
		mTxtName = (TextView) findViewById(R.id.txtName);
		mTxtMsg = (TextView) findViewById(R.id.txtContent);
		mTxtTime = (TextView) findViewById(R.id.txtTime);
		mLine = findViewById(R.id.line);
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		if (model == null || !(model instanceof Model_TrendsDetailComment)) {
			return;
		}

		Model_TrendsDetailComment m = (Model_TrendsDetailComment) model;
		
		//setLine
		setLine(m);
		
		// setHeader
		setHeaderProtrait(m);

		// setName
		setName(m);

		// setContent
		setContent(m);

		// setTime
		setTime(m);

	}

	private void setLine(Model_TrendsDetailComment m) {
		mLine.setVisibility(m.isNeedTopLine()?View.VISIBLE:View.GONE);
	}

	private void setTime(Model_TrendsDetailComment m) {
		mTxtTime.setText(ADTimeUtils.node(m.getTime()));
	}

	private void setContent(Model_TrendsDetailComment m) {

		SpannableString span = TrendsSpan.createTrendsSpan(getContext(),
				m.getSpans());

		if (span == null)
			return;

		mTxtMsg.setText(span);
		mTxtMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void setName(Model_TrendsDetailComment m) {
		mTxtName.setText(m.getmCommentOwner().getName());
	}

	private void setHeaderProtrait(final Model_TrendsDetailComment m) {
		String url = m.getmCommentOwner().getHeader();
		int type = m.getmCommentOwner().getType();
		
		//default
		if (url == null || url.equals("")) {
			int res = PublicUtils.getUserDefaultHeadImage(type);
			mHeaderPortrait.setImageResource(res);
			return;
		}
		
		//from url
		ImageLoader.getInstance((Activity)getContext()).displayImage(url, mHeaderPortrait, SimpleImageLoaderOptions.getRoundImageOptions(0, 100));
		
		//onclick
		mHeaderPortrait.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppContext.performVisitPeople(getContext(), m.getmCommentOwner().getUid(), m.getmCommentOwner().getType());
			}
		});
	}

}

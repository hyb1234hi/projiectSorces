package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.dialog.VisitingCardDialog;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsIndexFollow;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_TrendsIndexFollow extends Item_TrendsSimple {

	private TextView mFirstName;
	private TextView mSecondName;
	private ImageView mSecondHeader;

	public Item_TrendsIndexFollow(Context context) {
		super(context);
	}

	@Override
	protected View initContentView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_trends_follow, mContentView, true);

		mFirstName = (TextView) findViewById(R.id.txtFirstName);
		mSecondName = (TextView) findViewById(R.id.txtSecondName);
		mSecondHeader = (ImageView) findViewById(R.id.imgSecondHeader);

		return null;
	}

	@Override
	protected boolean isNeedHeadPortrait() {
		return false;
	}
	

	@Override
	protected void setFlagImg(String url) {
		// TODO Auto-generated method stub
		mImgFlag.setBackgroundResource(0);
		mImgFlag.setPadding(0, 0, 0, 0);
		mImgFlag.setScaleType(ScaleType.CENTER_CROP);
		int padding = Math.round(getResources().getDisplayMetrics().density * 5);
		mImgFlag.setPadding(padding, padding, padding, padding);
		mImgFlag.setScaleType(ScaleType.FIT_CENTER);
		mImgFlag.setImageResource(R.drawable.ic_home_page_flag_normal);
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {
		if (!(model instanceof Model_TrendsIndexFollow)) {
			return;
		}

		Model_TrendsIndexFollow m = (Model_TrendsIndexFollow) model;
		SpanText[] spanTexts = m.getMSpanTexts();

		//
		if (spanTexts != null && spanTexts.length == 2) {

			mFirstName.setText("");

			// secondName
			String s_name = spanTexts[1].getText();
			int s_id = spanTexts[1].getItem_id();
			int s_type = spanTexts[1].getUserType();
			mSecondName.setText(s_name);

			// img
			String url = m.getHeadPortraitUrl();
			mSecondHeader.setOnClickListener(new VisitingCardDialog.VisitingCardPopListener(getContext(), s_id, s_type));

			int res = PublicUtils.getUserDefaultHeadImage(m.getU_type());
			mSecondHeader.setImageResource(res);

			if (url == null || url.equals("")) {
				return;
			}
			ImageLoader.getInstance((Activity)getContext()).displayImage(url, mSecondHeader, SimpleImageLoaderOptions.getRoundImageOptions(0, 100));
		}

	}

}

package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.dialog.VisitingCardDialog;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsFollow;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_TrendsFollow extends AbsTrendsItem {

	private TextView mFirstName;
	private TextView mSecondName;
	private ImageView mSecondHeader;

	public Item_TrendsFollow(Context context) {
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
	protected void seconderySet(int type,BaseModel model) {
		if (!(model instanceof Model_TrendsFollow)) {
			return;
		}

		//
		Model_TrendsFollow m = (Model_TrendsFollow) model;
		SpanText[] spanTexts = m.getMSpanTexts();

		//
		if (spanTexts != null && spanTexts.length == 3) {

			// firstName
			String f_name = spanTexts[0].getText();
			// int f_id = spanTexts[0].getItem_id();
			// int f_type = spanTexts[0].getUserType();
			mFirstName.setText(f_name);
			// mFirstName.setOnClickListener(new
			// VisitingCardDialog.VisitingCardPopListener(getContext(), f_id,
			// f_type));

			// secondName
			String s_name = spanTexts[2].getText();
			int s_id = spanTexts[2].getItem_id();
			int s_type = spanTexts[2].getUserType();
			mSecondName.setText(s_name);
			// mSecondName.setOnClickListener(new
			// VisitingCardDialog.VisitingCardPopListener(getContext(), s_id,
			// s_type));

			// img
			String url = m.getFlagUrl();
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

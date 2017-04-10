package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.model.Model_BusinessPagePhone;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_BusinessPagePhone extends AbsItemView {

	private RelativeLayout mPhoneLayout;
	private TextView mPhoneNum;
	private TextView mPhoneTelHits;
	private RatingBar mOneRatingBar;
	private RatingBar mTwoRatingBar;
	private RatingBar mThreeRatingBar;
	private RatingBar mFourRatingBar;

	public Item_BusinessPagePhone(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_home_phone,
				this);
		initViews();
	}

	private void initViews() {
		mPhoneLayout = (RelativeLayout) findViewById(R.id.phone_layout);
		mPhoneNum = (TextView) findViewById(R.id.phone_num);
		mPhoneTelHits = (TextView) findViewById(R.id.phone_tel_hits);
		mOneRatingBar = (RatingBar) findViewById(R.id.one_rating_bar_top);
		mTwoRatingBar = (RatingBar) findViewById(R.id.two_rating_bar_top);
		mThreeRatingBar = (RatingBar) findViewById(R.id.three_rating_bar_top);
		mFourRatingBar = (RatingBar) findViewById(R.id.four_rating_bar_top);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		final Model_BusinessPagePhone phone = (Model_BusinessPagePhone) model;
		if (!TextUtils.isEmpty(phone.getPhone())) {
			mPhoneNum.setText(phone.getPhone());
		} else {
			mPhoneLayout.setVisibility(View.GONE);
			mPhoneTelHits.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(phone.getTel_hits())) {
			mPhoneTelHits.setText("已拨打" + phone.getTel_hits() + "次");
		} else {
			mPhoneTelHits.setText("已拨打0次");
		}
		setRatingBarData(phone);

		if (!phone.isMySelf()) {
			mPhoneLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ApplicationFacade.getInstance()
							.sendNotification(
									AppConst.APP_FACADE_BUSINESS_PHONE_CALL
											+ getContext().hashCode(),
									phone.getPhone());
				}
			});
		}
		disableRefresh();
	}

	private void setRatingBarData(Model_BusinessPagePhone phone) {
		if (!TextUtils.isEmpty(phone.getQuality())) {
			mOneRatingBar.setRating(Integer.parseInt(phone.getQuality()));
		} else {
			mOneRatingBar.setRating(0);
		}

		if (!TextUtils.isEmpty(phone.getPrice())) {
			mTwoRatingBar.setRating(Integer.parseInt(phone.getPrice()));
		} else {
			mTwoRatingBar.setRating(0);
		}

		if (!TextUtils.isEmpty(phone.getSpeed())) {
			mThreeRatingBar.setRating(Integer.parseInt(phone.getSpeed()));
		} else {
			mThreeRatingBar.setRating(0);
		}

		if (!TextUtils.isEmpty(phone.getAttitude())) {
			mFourRatingBar.setRating(Integer.parseInt(phone.getAttitude()));
		} else {
			mFourRatingBar.setRating(0);
		}
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

}

package com.sinaleju.lifecircle.app.activity;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.model.BusImageModel;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageSpecials;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class MerchantSpecialsInfoActivity extends BaseActivity {
	private static final String TAG = "MerchantSpecialsInfoActivity";
	private ImageView mIv_top;
	private ImageView mIv_left;
	private ImageView mIv_middle;
	private ImageView mIv_right;
	private TextView mTv_reBatePrice;
	private TextView mTv_originPrice;
	private TextView mTv_reBatePercent;
	private TextView mTv_specialTitle;
	private TextView mTv_specialExplain;
	private Model_BusinessPageSpecials specialModel;
	private List<BusImageModel> ImageList;
	private LinearLayout mBusPriceLayout;
	private TextView mSpecialTitle;
	private ImageView mLine;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_bus_specialsinfo;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		initTitleBar();
		mIv_top = (ImageView) findViewById(R.id.bus_recomm_top);
		mIv_left = (ImageView) findViewById(R.id.bus_recomm_bot_left);
		mIv_middle = (ImageView) findViewById(R.id.bus_recomm_bot_middle);
		mIv_right = (ImageView) findViewById(R.id.bus_recomm_bot_right);
		mTv_reBatePrice = (TextView) findViewById(R.id.bus_currentprice_text);
		mTv_originPrice = (TextView) findViewById(R.id.bus_originprice_text);
		mTv_reBatePercent = (TextView) findViewById(R.id.bus_rebate_text);
		mTv_specialTitle = (TextView) findViewById(R.id.bus_title_text);
		mTv_specialExplain = (TextView) findViewById(R.id.bus_specialsexplain_text);
		mBusPriceLayout = (LinearLayout) findViewById(R.id.bus_price_layout);
		mSpecialTitle = (TextView) findViewById(R.id.bus_specialsexplaintitle_text);
		mLine = (ImageView) findViewById(R.id.bus_line);
		specialModel = (Model_BusinessPageSpecials) getIntent().getSerializableExtra(
				"Model_BusinessPageSpecials");
		if (specialModel != null) {
			ImageList = specialModel.getImages();
		}
	}

	private void initTitleBar() {
		mTitleBar.setTitleName("特惠详情");
		// mTitleBar.initRightButtonTextOrResId(0, R.drawable.common_ellipsis);
		mTitleBar.showBackButton();
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		/*
		 * mTitleBar.setRightButton1Listener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // showBottomMenu(); } });
		 */
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			int pos = 0;
			switch (view.getId()) {
			case R.id.bus_recomm_top:
				pos = 0;
				break;
			case R.id.bus_recomm_bot_left:
				pos = 1;
				break;
			case R.id.bus_recomm_bot_middle:
				pos = 2;
				break;
			case R.id.bus_recomm_bot_right:
				pos = 3;
				break;
			default:
				break;
			}
			if (ImageList != null && ImageList.size() != 0) {
				String[] urls = new String[ImageList.size()];
				for (int i = 0; i < ImageList.size(); i++) {
					urls[i] = ImageList.get(i).getImageUrl();
				}
				Intent intent = new Intent(MerchantSpecialsInfoActivity.this,
						PhotoAlbumActivity.class);
				intent.putExtra("urls", urls);
				intent.putExtra("pos", pos);
				MerchantSpecialsInfoActivity.this.startActivity(intent);
			}
		}
	};

	@Override
	protected void initData() {
		// 设置图片
		int imageNumber = ImageList.size();
		for (int i = 0; i < imageNumber; i++) {
			BusImageModel imageModel = ImageList.get(i);
			int option = Integer.valueOf(imageModel.getPosition());
			String imgUrl = imageModel.getImageUrl();
			switch (option) {
			case 1:
				PublicUtils.loadHeadImage(mIv_top, imgUrl, R.drawable.bus_recomm_top_bg_src);
				mIv_top.setVisibility(View.VISIBLE);
				break;
			case 2:
				PublicUtils.loadHeadImage(mIv_left, imgUrl, R.drawable.bus_recomm_bot_bg);
				mIv_left.setVisibility(View.VISIBLE);
				break;
			case 3:
				PublicUtils.loadHeadImage(mIv_middle, imgUrl, R.drawable.bus_recomm_bot_bg);
				mIv_middle.setVisibility(View.VISIBLE);
				break;
			case 4:
				PublicUtils.loadHeadImage(mIv_right, imgUrl, R.drawable.bus_recomm_bot_bg);
				mIv_right.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}

		if (!TextUtils.isEmpty(specialModel.getLow_price())) {
			double lowPrice = Double.parseDouble(specialModel.getLow_price());
			mTv_reBatePrice.setText("￥" + lowPrice);
			if (!TextUtils.isEmpty(specialModel.getHeight_price())) {
				// 设置文字信息
				double heightPrice = Double.parseDouble(specialModel.getHeight_price());
				mTv_originPrice.setText("￥" + heightPrice);
				double rebatePercent = lowPrice / heightPrice * 10;
				mTv_reBatePercent.setText(new DecimalFormat("#.0").format(rebatePercent) + "折");
			} else {
				mBusPriceLayout.setVisibility(View.GONE);
			}
		} else {
			mBusPriceLayout.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(specialModel.getTitle())) {
			mTv_specialTitle.setText(specialModel.getTitle());
		} else {
			mTv_specialTitle.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(specialModel.getContent())) {
			mTv_specialExplain.setText(specialModel.getContent());
		} else {
			mSpecialTitle.setVisibility(View.GONE);
			mLine.setVisibility(View.GONE);
			mTv_specialExplain.setVisibility(View.GONE);
		}
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mIv_top.setOnClickListener(clickListener);
		mIv_left.setOnClickListener(clickListener);
		mIv_middle.setOnClickListener(clickListener);
		mIv_right.setOnClickListener(clickListener);
	}

}

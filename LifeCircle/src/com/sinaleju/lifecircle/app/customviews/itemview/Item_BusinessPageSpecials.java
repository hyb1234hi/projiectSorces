package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.activity.MerchantSpecialsInfoActivity;
import com.sinaleju.lifecircle.app.model.BusImageModel;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageSpecials;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_BusinessPageSpecials extends AbsItemView {

	private static final String TAG = "Item_BusinessPageSpecials";
	private TextView mBusHeadTitle;
	private ImageView mTopImage;
	private LinearLayout mBotImageLayout;
	private ImageView mBotLeftImage;
	private ImageView mBotMidImage;
	private ImageView mBotRightImage;
	private TextView mBotContent;

	// private Context mContext;

	public Item_BusinessPageSpecials(Context context) {
		super(context);
		// mContext=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_bus_specials,
				this);

		initViews();
	}

	private void initViews() {
		mBusHeadTitle = (TextView) findViewById(R.id.bus_head_title);
		mTopImage = (ImageView) findViewById(R.id.bus_recomm_top);
		mBotImageLayout = (LinearLayout) findViewById(R.id.bus_bot_image_layout);
		mBotLeftImage = (ImageView) findViewById(R.id.bus_recomm_bot_left);
		mBotMidImage = (ImageView) findViewById(R.id.bus_recomm_bot_middle);
		mBotRightImage = (ImageView) findViewById(R.id.bus_recomm_bot_right);
		mBotContent = (TextView) findViewById(R.id.bus_bottom_text);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		Model_BusinessPageSpecials specials = (Model_BusinessPageSpecials) model;
		if (!TextUtils.isEmpty(specials.getTitle())) {
			mBusHeadTitle.setText(specials.getTitle());
		} else {
			mBusHeadTitle.setText("");
		}
		if (specials.getImages().size() != 0) {
			if (specials.getImages().size() == 1) {
				mBotImageLayout.setVisibility(View.GONE);
			}

			for (int i = 0; i < specials.getImages().size(); i++) {
				BusImageModel image = specials.getImages().get(i);
				if (image.getPosition().equals("1")) {
					setImageShowOrHide(mTopImage, image.getImageUrl());
				} else if (image.getPosition().equals("2")) {
					setImageShowOrHide(mBotLeftImage, image.getImageUrl());
				} else if (image.getPosition().equals("3")) {
					setImageShowOrHide(mBotMidImage, image.getImageUrl());
				} else if (image.getPosition().equals("4")) {
					setImageShowOrHide(mBotRightImage, image.getImageUrl());
				}
			}
		} else {
			mTopImage.setVisibility(View.GONE);
			mBotImageLayout.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(specials.getContent())) {
			mBotContent.setText(specials.getContent());
		} else {
			mBotContent.setText("");
		}
		disableRefresh();
	}

	private void setImageShowOrHide(ImageView image, String url) {
		if (!TextUtils.isEmpty(url)) {
			image.setVisibility(View.VISIBLE);
			loadHeadImage(image, url);
		}
	}

	private void loadHeadImage(ImageView imageView, String url) {
		if (!TextUtils.isEmpty(url)) {
			ImageLoader.getInstance((Activity)getContext()).displayImage(url, imageView,
					SimpleImageLoaderOptions.getOptions(0, true),
					new FadeInImageLoadingListener(imageView));
		}
	}

	@Override
	public boolean enable() {
		return true;
	}

	@Override
	protected void onClickThis(BaseModel model) {
		Model_BusinessPageSpecials specials = (Model_BusinessPageSpecials) model;
		LogUtils.i(TAG, "click this");
		Intent specialIntent = new Intent(getContext(),
				MerchantSpecialsInfoActivity.class);
		specialIntent.putExtra("Model_BusinessPageSpecials", specials);
		getContext().startActivity(specialIntent);
	}

}

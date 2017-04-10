package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.assist.FailReason;
import com.iss.imageloader.core.assist.ImageLoadingListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.activity.PhotoAlbumActivity;
import com.sinaleju.lifecircle.app.customviews.UnfoldGridView;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg.Pic;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsgDeliver;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.ADAnimationUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_TrendsMsgDeliver extends Item_TrendsMsg {
	protected UnfoldGridView mDeliveredGrid;
	// protected TextView mTxtDeliveredTitle;
	protected TextView mTxtDeliveredMsg;
	protected LinearLayout mLyotDeliver;
	protected ImageView mImgDelivered;

	public Item_TrendsMsgDeliver(Context context) {
		super(context);
	}

	@Override
	protected void initViews() {
		super.initViews();

		mLyotDeliver = (LinearLayout) findViewById(R.id.lyotTrendsMsgDelivered);
		mLyotDeliver.setVisibility(View.VISIBLE);
		mImgDelivered = (ImageView) findViewById(R.id.imgDelivered);
		mDeliveredGrid = (UnfoldGridView) findViewById(R.id.imgDeliveredGrid);
		mTxtDeliveredMsg = (TextView) findViewById(R.id.txtMsgDelivered);
		// mTxtDeliveredTitle = (TextView) rootView
		// .findViewById(R.id.txtTitleDelivered);
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {
		super.seconderySet(1,model);

		Model_TrendsMsgDeliver deliveredMsg = (Model_TrendsMsgDeliver) model;
		Model_TrendsMsg m = deliveredMsg.getDeliveredMsg();
		if (m == null) {
			mLyotDeliver.setVisibility(View.GONE);
		}

		setTextDeliveredTitle(m);
		setDeliveredMsg(m);
		setDeliveredPics(m);
	}

	private void setTextDeliveredTitle(Model_TrendsMsg m) {
		// if (m.getName() == null) {
		// mTxtDeliveredTitle.setVisibility(View.GONE);
		// return;
		// }

		// if(m.isShowHeadImage()){
		// mTxtDeliveredTitle.setVisibility(View.VISIBLE);
		// } else {
		// mTxtDeliveredTitle.setVisibility(View.GONE);
		// }

		// String name = m.getName().trim() + " ";
		// SpannableString span = TrendsSpan.createTrendsSpan(getContext(),
		// new SpanText(SpanType.NAME, m.getUid(), name));
		//
		// if (m.isVIP()) {
		// span.setSpan(new ImageSpan(getContext(), R.drawable.icon_vip),
		// name.length() - 1, name.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		// }

		// mTxtDeliveredTitle.setText(span);
		//
		// mTxtDeliveredTitle.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	protected void setMsg(Model_TrendsMsg m) {
		SpanText[] spans = m.getMSpanTexts();
		for (SpanText span : spans) {
			if (span.getSpanType() == SpanType.TEXT) {
				span.setItem_id(AppConst.NULL_INT);
			}
		}
		mTxtMsg.setText(TrendsSpan.createTrendsSpan(getContext(), spans));
		mTxtMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}

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
		}

		mTxtDeliveredMsg.setText(TrendsSpan.createTrendsSpan(getContext(), spans));
		mTxtDeliveredMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void setDeliveredImgGrid(Pic[] pics) {

		mDeliveredGrid.setAdapter(new ImageAdapter(mDeliveredGrid, pics));

	}

	private void setDeliveredPics(Model_TrendsMsg m) {
		Pic[] pics = m.getPics();

		mImgDelivered.setVisibility(View.GONE);
		mDeliveredGrid.setVisibility(View.GONE);

		if (pics == null || pics.length == 0) {
			return;
		}

		if (pics.length == 1) {
			mImgDelivered.setVisibility(View.VISIBLE);
			setDeliverImgMsg(pics[0]);
		} else {

			mDeliveredGrid.setVisibility(View.VISIBLE);

			//re-set layout
			mDeliveredGrid.setNumColumns(pics.length==2?2:3);
			RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mDeliveredGrid.getLayoutParams();
			p.width=(int) (77*(pics.length==2?2:3)*getResources().getDisplayMetrics().density);
			mDeliveredGrid.setLayoutParams(p);	
			
			// cancel last request
			ImageLoader.getInstance((Activity)getContext()).cancelDisplayTask(mImgDelivered);

			setDeliveredImgGrid(pics);
		}
	}

	private void setDeliverImgMsg(final Pic pic) {
		String url = pic.getUrlMiddle();
		if (url != null & !url.equals("")) {
			ImageLoader.getInstance((Activity)getContext()).displayImage(url, mImgDelivered, SimpleImageLoaderOptions.getOptions(R.drawable.image_default, true), new ImageLoadingListener() {
				private boolean anim;

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (anim)
						ADAnimationUtils.fadeIn(view);
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onBitmapCreate(boolean fromMemeory, Bitmap map) {
					this.anim = fromMemeory;
				}
			});
			mImgDelivered.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getContext(), PhotoAlbumActivity.class);
					String[] data = new String[1];
					data[0] = pic.getUrlBig();
					intent.putExtra("urls", data);
					intent.putExtra("pos", 0);

					getContext().startActivity(intent);
				}
			});
		}
	}
}

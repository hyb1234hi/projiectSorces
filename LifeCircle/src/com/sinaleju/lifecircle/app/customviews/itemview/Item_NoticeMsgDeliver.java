package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.activity.PhotoAlbumActivity;
import com.sinaleju.lifecircle.app.customviews.UnfoldGridView;
import com.sinaleju.lifecircle.app.model.Model_NoticeDelive;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg.Pic;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_NoticeMsgDeliver extends Item_NoticeMsg {

	protected UnfoldGridView mDeliveredGrid;
	protected TextView mTxtDeliveredMsg;
	protected LinearLayout mLyotDeliver;
	private ImageView mImgDelivered;

	public Item_NoticeMsgDeliver(Context context) {
		super(context);
	}

	@Override
	protected void initViews(View rootView) {
		super.initViews(rootView);

		mLyotDeliver = (LinearLayout) rootView
				.findViewById(R.id.lyotTrendsMsgDelivered);
		mLyotDeliver.setVisibility(View.VISIBLE);
		mImgDelivered = (ImageView) rootView.findViewById(R.id.imgDelivered);
		mDeliveredGrid = (UnfoldGridView) rootView
				.findViewById(R.id.imgDeliveredGrid);
		mTxtDeliveredMsg = (TextView) rootView
				.findViewById(R.id.txtMsgDelivered);
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {
		super.seconderySet(1,model);

		Model_NoticeDelive deliveredMsg = (Model_NoticeDelive) model;
		Model_TrendsMsg m = deliveredMsg.getDeliveredMsg();
		if (m == null) {
			mLyotDeliver.setVisibility(View.GONE);
		}

		setTextDeliveredTitle(m);
		setDeliveredMsg(m);
		setDeliveredPics(m);
	}

	private void setTextDeliveredTitle(Model_TrendsMsg m) {

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
		spans[0] = new SpanText(SpanType.NAME, m.getUid(), m.getName() + ":",
				m.getU_type());
		for (int i = 0; i < arg.length; i++) {
			spans[i + 1] = arg[i];
		}

		mTxtDeliveredMsg.setText(TrendsSpan.createTrendsSpan(getContext(),
				spans));
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

			// cancel last request
			ImageLoader.getInstance((Activity)getContext()).cancelDisplayTask(mImgDelivered);

			setDeliveredImgGrid(pics);
		}
	}

	private void setDeliverImgMsg(final Pic pic) {
		String url = pic.getUrlMiddle();
		if (url != null & !url.equals("")) {
			ImageLoader.getInstance((Activity)getContext()).displayImage(
					url,
					mImgDelivered,
					SimpleImageLoaderOptions.getOptions(
							R.drawable.image_default, true),
					new FadeInImageLoadingListener(mImgDelivered));

			mImgDelivered.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getContext(),
							PhotoAlbumActivity.class);
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

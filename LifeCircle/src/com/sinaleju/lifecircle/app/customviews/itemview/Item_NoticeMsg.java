package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.assist.FailReason;
import com.iss.imageloader.core.assist.ImageLoadingListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppCache;
import com.sinaleju.lifecircle.app.activity.PhotoAlbumActivity;
import com.sinaleju.lifecircle.app.activity.TrendsDetailActivity;
import com.sinaleju.lifecircle.app.customviews.UnfoldGridView;
import com.sinaleju.lifecircle.app.model.Model_NoticeMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg.Pic;
import com.sinaleju.lifecircle.app.model.Model_TrendsSimple;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.ADAnimationUtils;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

/**
 * @author dan_alan
 */
public class Item_NoticeMsg extends AbsTrendsItem {

	private static final String TAG = "Item_NoticeMsg";

	protected ImageView mImgMsg;
	protected UnfoldGridView mGrid;
	protected TextView mTxtMsg;
	private TextView mNoticeTitle;
	private TextView mNoticeTitleText;
	private TextView mFromWhere;
	private ImageView mNoticeTitleIcon;

	public Item_NoticeMsg(Context context) {
		super(context);
	}

	@Override
	protected void onClickThis(BaseModel model) {

		if (model == null || !(model instanceof Model_TrendsMsg)) {
			return;
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

	@Override
	protected View initContentView() {

		View rootView = LayoutInflater.from(getContext()).inflate(
				R.layout.item_notice_simple_msg, mContentView, true);

		initViews(rootView);

		return rootView;
	}

	protected void initViews(View rootView) {
		mGrid = (UnfoldGridView) rootView.findViewById(R.id.imgGrid);
		mImgMsg = (ImageView) rootView.findViewById(R.id.imgMsg);
		mTxtMsg = (TextView) rootView.findViewById(R.id.txtMsg);
		mNoticeTitle = (TextView) rootView.findViewById(R.id.notice_title);
		mNoticeTitleText = (TextView) rootView
				.findViewById(R.id.notice_title_text);
		mFromWhere = (TextView) rootView.findViewById(R.id.from_where);
		mNoticeTitleIcon = (ImageView) rootView
				.findViewById(R.id.notice_title_icon);
	}

	protected void onCommentClick() {
		Model_NoticeMsg m = (Model_NoticeMsg) mModel;
		Intent intent = new Intent(getContext(), TrendsDetailActivity.class);
		intent.putExtra("msg_id", m.getMsg_id());
		getContext().startActivity(intent);
	}

	protected void onDeliverClick() {
		Model_NoticeMsg m = (Model_NoticeMsg) mModel;
		Intent intent = new Intent(getContext(), TrendsDetailActivity.class);
		intent.putExtra("msg_id", m.getMsg_id());
		intent.putExtra("delivered", true);
		getContext().startActivity(intent);
	}

	@Override
	protected boolean isNeedHeadPortrait() {
		return true;
	}

	@Override
	protected void seconderySet(int type,BaseModel model) {
		if (!(model instanceof Model_TrendsMsg)) {
			LogUtils.e(TAG, "class cast error ");
		}
		final Model_TrendsMsg msgModel = (Model_TrendsMsg) model;

		// setTitle
		setTextTitle(msgModel);

		// setMsg
		setMsg(msgModel);

		// setImg
		setPics(msgModel);

		if (!TextUtils.isEmpty(msgModel.getCommunity_name())) {
			mFromWhere.setText("来自" + msgModel.getCommunity_name());
		} else {
			mFromWhere.setText("");
		}

	}

	private void setPics(Model_TrendsMsg m) {

		mImgMsg.clearAnimation();

		mGrid.setVisibility(View.GONE);
		mImgMsg.setVisibility(View.GONE);

		Pic[] pics = m.getPics();

		if (pics == null || pics.length == 0) {
			return;
		}

		if (pics.length == 1) {
			mImgMsg.setVisibility(View.VISIBLE);
			setImgMsg(pics[0]);
		} else {
			mGrid.setVisibility(View.VISIBLE);
			setImgGrid(pics);
		}
	}

	private void setImgMsg(final Pic pic) {

		String url = pic.getUrlMiddle();
		if (url != null && !url.equals(""))
			ImageLoader.getInstance((Activity)getContext()).displayImage(
					url,
					mImgMsg,
					SimpleImageLoaderOptions.getOptions(
							R.drawable.head_portrailt_bg, true),
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String imageUri, View view) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {

							ADAnimationUtils.fadeIn(view);
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onBitmapCreate(boolean fromMemeory,
								Bitmap map) {
							// TODO Auto-generated method stub

						}
					});
		mImgMsg.setOnClickListener(new OnClickListener() {

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

	private void setTextTitle(Model_TrendsMsg m) {
		SpannableString span = null;
		String name = "";
		if (m.getTrendsType() == TrendsType.NOTICE_LIKE) {
			Model_TrendsSimple simple = m.getSimple();
			Model_TrendsBase.SpanText[] contentTypes = simple.getMSpanTexts();
			if (contentTypes == null) {
				return;
			}
			span = TrendsSpan.createTrendsSpan(getContext(), contentTypes);
		} else {
			name = m.getName().trim() + " ";
			span = TrendsSpan.createTrendsSpan(getContext(), new SpanText(
					SpanType.NAME, m.getUid(), name, m.getU_type()));
		}

		if (m.isVIP()) {
			span.setSpan(new ImageSpan(getContext(), R.drawable.icon_vip),
					name.length() - 1, name.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		mNoticeTitle.setText(span);
		mNoticeTitle.setMovementMethod(LinkMovementMethod.getInstance());
		if (m.getTrendsType() == TrendsType.NOTICE_AT) {
			mNoticeTitleText.setText("提到了你");
			mNoticeTitleIcon.setVisibility(View.GONE);
		} else if (m.getTrendsType() == TrendsType.NOTICE_LIKE) {
			mNoticeTitleText.setText("");
			mNoticeTitleIcon.setVisibility(View.GONE);
		}
	}

	protected void setMsg(Model_TrendsMsg m) {
		mTxtMsg.setText(TrendsSpan.createTrendsSpan(getContext(),
				m.getMSpanTexts()));
		mTxtMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}

	protected void setImgGrid(Pic[] pics) {
		mGrid.setAdapter(new ImageAdapter(mGrid, pics));
	}

	protected class ImageAdapter extends BaseAdapter {

		private Pic[] pics = null;
		private UnfoldGridView grid;

		public ImageAdapter(UnfoldGridView grid, Pic... pics) {
			this.pics = pics;
			this.grid = grid;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pics.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int pos, View arg1, ViewGroup arg2) {
			View view = LayoutInflater.from(getContext()).inflate(
					R.layout.item_img_grid, null);
			ImageView img = (ImageView) view.findViewById(R.id.imgMsg);

			int gridWidth = grid.getWidth() - 8;
			int width = gridWidth / 3;
			int height = gridWidth / 3;

			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					width, height);

			view.setLayoutParams(param);

			String url = pics[pos].getUrlSmall();
			if (url != null && !url.equals(""))
				ImageLoader.getInstance((Activity)getContext()).displayImage(
						url,
						img,
						SimpleImageLoaderOptions.getOptions(
								R.drawable.image_default, true),
						new FadeInImageLoadingListener(img));

			// set onClick
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent((Activity)getContext(),
							PhotoAlbumActivity.class);
					String[] data = new String[pics.length];
					for (int i = 0; i < pics.length; i++) {
						data[i] = pics[i].getUrlBig();
					}

					intent.putExtra("urls", data);
					intent.putExtra("pos", pos);

					getContext().startActivity(intent);
				}
			});
			return view;
		}
	}
}

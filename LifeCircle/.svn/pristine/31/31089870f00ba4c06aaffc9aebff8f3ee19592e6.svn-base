package com.sinaleju.lifecircle.app.customviews.itemview;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.imageloader.core.ImageLoader;
import com.iss.view.common.ToastAlone;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppCache;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.PhotoAlbumActivity;
import com.sinaleju.lifecircle.app.activity.SimpleLocationMapActivity;
import com.sinaleju.lifecircle.app.activity.TrendsDetailActivity;
import com.sinaleju.lifecircle.app.customviews.UnfoldGridView;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg.Pic;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSMsgLike;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

/**
 * @author dan_alan
 */
public class Item_TrendsMsg extends AbsTrendsItem {

	private static final String TAG = "Item_TrendsMsg";
	protected ImageView mImgMsg;
	protected UnfoldGridView mGrid;
	protected TextView mTxtTitle;
	protected TextView mTxtMsg;

	protected RelativeLayout mLayoutCommunity;
	protected TextView mTxtCommunity;

	protected TextView mTxtCommentCount;
	protected TextView mTxtDeliveredCount;
	protected TextView mTxtAgreeCount;

	protected ImageView mImgCommentCount;
	protected ImageView mImgDeliveredCount;

	protected View mLayoutFavor;
	protected View mLayoutComment;
	protected View mLayoutDeliver;

	// protected View mLoadingLyot;

	private LinearLayout mLocationLayout;
	private TextView mLocationText;

	private ImageView mImgAgree;

	// protected ImageView mImgIndicatorComment;
	// protected ImageView mImgIndicatorDeliver;
	protected ImageView mImgIndicatorAnimator;

	protected View includeCommentDeliverFavor;
	protected View includeDeliver_CommentDeliverFavor;

	private View mLayoutLoadingShow;
	private View mLayoutLoadingCaution;

	protected View mBottomLine;
	protected View mLoadingIndicator;
	private String activityName;

	public Item_TrendsMsg(Context context) {
		super(context);
		activityName = context.getClass().getName();
	}

	@Override
	protected void onClickThis(BaseModel model) {
			
		if (model == null|| !(model instanceof Model_TrendsMsg) || activityName.contains("OfficHomeActivity")) {
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

		LayoutInflater.from(getContext()).inflate(R.layout.item_trends_meg,
				mContentView, true);

		initViews();

		return null;
	}

	protected void initViews() {

		// include
		includeCommentDeliverFavor = findViewById(R.id.includeCommentDeliverFavor);

		// include zone
		mLayoutCommunity = (RelativeLayout) includeCommentDeliverFavor
				.findViewById(R.id.linear_community);

		// include image
		mImgAgree = (ImageView) includeCommentDeliverFavor
				.findViewById(R.id.imgAgree);
		mImgDeliveredCount = (ImageView) includeCommentDeliverFavor
				.findViewById(R.id.imgToDeliver);
		mImgCommentCount = (ImageView) includeCommentDeliverFavor
				.findViewById(R.id.imgToComment);

		// include texts
		mTxtCommunity = (TextView) includeCommentDeliverFavor
				.findViewById(R.id.from_community);
		mTxtAgreeCount = (TextView) includeCommentDeliverFavor
				.findViewById(R.id.txtAgreeCount);
		mTxtCommentCount = (TextView) includeCommentDeliverFavor
				.findViewById(R.id.txtCommentCount);
		mTxtDeliveredCount = (TextView) includeCommentDeliverFavor
				.findViewById(R.id.txtDeliveredCount);

		// include layouts
		mLayoutFavor = includeCommentDeliverFavor.findViewById(R.id.lyotFavor);
		mLayoutDeliver = includeCommentDeliverFavor
				.findViewById(R.id.linearDeliveredCount);
		mLayoutComment = includeCommentDeliverFavor
				.findViewById(R.id.linearCommentCount);

		// indicator
		mImgIndicatorAnimator = (ImageView) findViewById(R.id.imgIndicatorAnimator);
		mLoadingIndicator = findViewById(R.id.lyotLoading);
		mLayoutLoadingShow = findViewById(R.id.lyotLoadingShow);
		mLayoutLoadingCaution = findViewById(R.id.txtCaution);
		mBottomLine = findViewById(R.id.view8);

		// pic
		mGrid = (UnfoldGridView) findViewById(R.id.imgGrid);
		mTxtTitle = (TextView) findViewById(R.id.txtTitle);
		mImgMsg = (ImageView) findViewById(R.id.imgMsg);
		mTxtMsg = (TextView) findViewById(R.id.txtMsg);

		// location
		mLocationLayout = (LinearLayout) findViewById(R.id.msg_location_layout);
		mLocationText = (TextView) findViewById(R.id.msg_location_text);

		// listener
		mLayoutComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!AppContext.isValid(getContext()))
					return;
				onCommentClick();
			}
		});

		mLayoutDeliver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!AppContext.isValid(getContext()))
					return;
				onDeliverClick();
			}
		});

		mLayoutFavor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!AppContext.isValid(getContext()))
					return;
				onLikeClick();
			}

		});

	}

	// private void setCommunityWidth(RelativeLayout mLayoutCommunity) {
	// DisplayMetrics dm = new DisplayMetrics();
	// ((Activity) getContext()).getWindowManager().getDefaultDisplay()
	// .getMetrics(dm);
	// int screenWidth = dm.widthPixels - 450;
	// ViewGroup.LayoutParams params = mLayoutCommunity.getLayoutParams();
	// params.width = screenWidth;
	// params.height = params.WRAP_CONTENT;
	// mLayoutCommunity.setLayoutParams(params);
	// }

	protected void showIndicatiorLayout() {
		mLoadingIndicator.setVisibility(View.VISIBLE);
	}

	public void setIndicatorDeliver() {
		// mImgIndicatorComment.setVisibility(View.INVISIBLE);
		// mImgIndicatorAnimator.setVisibility(View.INVISIBLE);
		// mImgIndicatorDeliver.setVisibility(View.VISIBLE);
	}

	public void setIndicatorComment() {
		// mImgIndicatorComment.setVisibility(View.VISIBLE);
		// mImgIndicatorDeliver.setVisibility(View.INVISIBLE);
		// mImgIndicatorAnimator.setVisibility(View.INVISIBLE);
	}

	protected void indicateToComment() {

		// int[] locationComment = new int[2];
		// int[] locationDeliver = new int[2];
		// int[] locationAnimator = new int[2];
		// mImgIndicatorAnimator.getLocationOnScreen(locationAnimator);

		// Animation anim = new TranslateAnimation(locationDeliver[0]
		// - locationAnimator[0],
		// locationComment[0] - locationAnimator[0], 0, 0);
		Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				2.4f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		anim.setDuration(200);
		anim.setFillAfter(true);
		anim.setInterpolator(new LinearInterpolator());
		mImgIndicatorAnimator.setVisibility(View.VISIBLE);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				setCommentCountSelected();

				animCommentCallback();
			}
		});
		mImgIndicatorAnimator.startAnimation(anim);

	}

	public void startIndicateToDeliverAnim() {
		indicateToDeliver(false);
	}

	protected void indicateToDeliver(final boolean callback) {

		// int[] locationComment = new int[2];
		// int[] locationDeliver = new int[2];
		// int[] locationAnimator = new int[2];
		//
		// mImgIndicatorAnimator.getLocationOnScreen(locationAnimator);
		//
		// Animation anim = new TranslateAnimation(locationComment[0]
		// - locationAnimator[0],
		// locationDeliver[0] - locationAnimator[0], 0, 0);
		Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 2.4f, Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0);

		anim.setDuration(200);
		anim.setFillAfter(true);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		mImgIndicatorAnimator.setVisibility(View.VISIBLE);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

				setDelieveredCountSelected();

				if (callback)
					animDeliverCallback();
			}
		});
		mImgIndicatorAnimator.startAnimation(anim);

	}

	protected void indicateToDeliver() {
		indicateToDeliver(true);
	}

	protected void animDeliverCallback() {
	}

	protected void animCommentCallback() {
	}

	public void performDeliveredClick() {
		mTxtDeliveredCount.performClick();
	}

	public void performCommentClick() {
		mTxtCommentCount.performClick();
	}

	public void showLoading() {
		mLoadingIndicator.setVisibility(View.VISIBLE);
		mLayoutLoadingCaution.setVisibility(View.GONE);
		mLayoutLoadingShow.setVisibility(View.VISIBLE);
	}

	public void dismissLoading() {
		mLoadingIndicator.setVisibility(View.GONE);
	}

	public boolean isLoading() {
		return mLoadingIndicator.getVisibility() == View.VISIBLE;
	}

	public void showError() {
		mLoadingIndicator.setVisibility(View.VISIBLE);
		mLayoutLoadingCaution.setVisibility(View.VISIBLE);
		mLayoutLoadingShow.setVisibility(View.GONE);
	}

	protected void onCommentClick() {
		Model_TrendsMsg m = (Model_TrendsMsg) mModel;
		Intent intent = generateDetailIntent(m);
		getContext().startActivity(intent);
	}

	protected void onDeliverClick() {
		Model_TrendsMsg m = (Model_TrendsMsg) mModel;
		Intent intent = generateDetailIntent(m);
		intent.putExtra("delivered", true);
		getContext().startActivity(intent);
	}

	protected void onLikeClick() {
		if (mModel == null)
			return;
		Model_TrendsMsg m = (Model_TrendsMsg) mModel;
		int msgid = m.getMsg_id();
		int like = m.getLike() == 0 ? 1 : 0;
		requestLike(mTxtAgreeCount, mImgAgree, msgid, like, m);
	}

	protected RSMsgLike rs = null;

	protected void requestLike(final TextView txtAgree,
			final ImageView imgAgree, int msg_id, final int like,
			final Model_TrendsMsg m) {
		if (rs != null) {
			return;
		}
		rs = new RSMsgLike(AppContext.curUser().getUid(), msg_id, like);
		LogUtils.e("Item_TrendsMsg", "islike ：： " + like);
		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				rs = null;
				String json = result.toString();
				try {
					JSONObject obj = new JSONObject(json);
					int num = obj.optInt("like_num");
					txtAgree.setText(num == 0 ? "好" : (num + ""));
					imgAgree.setImageResource(like == 1 ? R.drawable.img_agree_checked
							: R.drawable.img_agree_unchecked);
					m.setLike(like);
					m.setAgreeCount(num);
				} catch (JSONException e) {
					ToastAlone.makeText(getContext(), like == 0 ? "取消赞失败" : "赞失败",
							Toast.LENGTH_SHORT).show();
					LogUtils.e(TAG, "", e);
				}

				ToastAlone.makeText(getContext(), like == 0 ? "取消赞成功" : "赞成功", Toast.LENGTH_SHORT).show();

			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				rs = null;
				ToastAlone.makeText(getContext(), like == 0 ? "取消赞失败" : "赞失败",
						Toast.LENGTH_SHORT).show();
				// mImgAgree.setImageResource(R.drawable.item_home_page_praise_uncheck);
				LogUtils.e(TAG, "", ex);
			}
		});
		rs.asyncExecute(getContext());
	}

	@Override
	protected boolean isNeedHeadPortrait() {
		return true;
	}

	@Override
	protected void seconderySet(int type, BaseModel model) {
		if (activityName.contains("OfficHomeActivity")) {
			includeCommentDeliverFavor.setVisibility(View.GONE);
			mTxtMsg.setClickable(false);
		}

		if (!(model instanceof Model_TrendsMsg)) {
			LogUtils.e(TAG, "class cast error ");
		}

		final Model_TrendsMsg msgModel = (Model_TrendsMsg) model;

		if (!TextUtils.isEmpty(msgModel.getLocation())
				&& !TextUtils.isEmpty(msgModel.getCoordinate())) {
			mLocationLayout.setVisibility(View.VISIBLE);
			mLocationText.setText(msgModel.getLocation());
			gotoLocationMapActivity(msgModel);
		} else {
			mLocationLayout.setVisibility(View.GONE);
		}

		// setTitle
		setTextTitle(msgModel);

		// setMsg
		setMsg(msgModel);

		// setImg
		setPics(type, msgModel);

		// setOther
		setOthers(msgModel);

		if (!msgModel.isShowHeader() && msgModel.isShowFlag()) {
			mLayoutCommunity.setVisibility(View.VISIBLE);
			// setCommunityWidth(mLayoutCommunity);
			if (!TextUtils.isEmpty(msgModel.getCommunity_name())) {
				mTxtCommunity.setText(msgModel.getCommunity_name());
			} else {
				mTxtCommunity.setText("");
			}
		} else {
			mLayoutCommunity.setVisibility(View.GONE);
		}

	}

	private void gotoLocationMapActivity(final Model_TrendsMsg msgModel) {
		mLocationLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				double mLon = -1;
				double mLat = -1;
				try {
					if (msgModel.getCoordinate().indexOf(",") != -1) {
						String[] coord = msgModel.getCoordinate().split(",");
						mLat = Double.parseDouble(coord[0]);
						mLon = Double.parseDouble(coord[1]);
					}
					Intent intent = new Intent(getContext(),
							SimpleLocationMapActivity.class);
					intent.putExtra("lon", mLon);
					intent.putExtra("lat", mLat);
					intent.putExtra("location", msgModel.getLocation());
					getContext().startActivity(intent);
				} catch (Exception e) {
					LogUtils.e(TAG, "", e);
				}

			}
		});
	}

	private void setPics(int type, Model_TrendsMsg m) {

		mGrid.setVisibility(View.GONE);
		mImgMsg.setVisibility(View.GONE);

		Pic[] pics = m.getPics();

		if (pics == null || pics.length == 0) {
			mGrid.setVisibility(View.GONE);
			return;
		}

		if (pics.length == 1) {
			mImgMsg.setVisibility(View.VISIBLE);
			setImgMsg(type, pics[0]);
		} else {

			//
			mGrid.setVisibility(View.VISIBLE);

			mGrid.setNumColumns(pics.length == 2 ? 2 : 3);
			RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mGrid
					.getLayoutParams();
			p.width = (int) (77 * (pics.length == 2 ? 2 : 3) * getResources()
					.getDisplayMetrics().density);
			mGrid.setLayoutParams(p);

			// cancel display
			ImageLoader.getInstance((Activity) getContext()).cancelDisplayTask(
					mImgMsg);

			setImgGrid(pics);
		}
	}

	private void setImgMsg(int type, final Pic pic) {

		String url = "";
		if (type == 2) {// type ==2 item image big
			url = pic.getUrlBig();
		} else {
			url = pic.getUrlMiddle();
		}
		LogUtils.v(TAG,
				"setImgMsg  :  type  " + type + "   BigUrl  " + pic.getUrlBig());
		if (url != null && !url.equals(""))
			ImageLoader.getInstance((Activity) getContext()).displayImage(
					url,
					mImgMsg,
					SimpleImageLoaderOptions.getOptions(
							R.drawable.image_default, true),
					new FadeInImageLoadingListener(mImgMsg));

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

	private void setOthers(Model_TrendsMsg msgModel) {

		int commentCount = msgModel.getCommentCount();
		int deliveredCount = msgModel.getDeliveredCount();
		int agreeCount = msgModel.getAgreeCount();
		setCommentCount(commentCount);
		setDeliveredCount(deliveredCount);
		setAgreeCount(agreeCount);
		// mTxtAgreeCount.setText("" + (agreeCount == 0 ? "赞" : agreeCount));

		mImgAgree
				.setImageResource(msgModel.getLike() == 1 ? R.drawable.img_agree_checked
						: R.drawable.img_agree_unchecked);

	}

	public void setCommentCount(int count) {
		mTxtCommentCount.setText("" + (count == 0 ? "评论" : count));
	}

	public void setDeliveredCount(int count) {
		mTxtDeliveredCount.setText("" + (count == 0 ? "转发" : count));
	}

	public void setAgreeCount(int count) {
		mTxtAgreeCount.setText("" + (count == 0 ? "好" : count));
	}

	private void setTextTitle(Model_TrendsMsg m) {
		if (m.getName() == null) {
			mTxtTitle.setVisibility(View.GONE);
			return;
		}

		if (m.isShowHeadImage()) {
			mTxtTitle.setVisibility(View.VISIBLE);
		} else {
			mTxtTitle.setVisibility(View.GONE);
		}

		String name = m.getName().trim() + " ";
		SpannableString span = TrendsSpan.createTrendsSpan(getContext(),
				new SpanText(SpanType.NAME, m.getUid(), name, m.getU_type()));

		if (m.isVIP()) {
			span.setSpan(new ImageSpan(getContext(), R.drawable.icon_vip),
					name.length() - 1, name.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		mTxtTitle.setText(span);
		mTxtTitle.setMovementMethod(LinkMovementMethod.getInstance());

	}

	protected void setMsg(Model_TrendsMsg m) {
		mTxtMsg.setText(TrendsSpan.createTrendsSpan(getContext(),
				m.getMSpanTexts()));
		mTxtMsg.setMovementMethod(LinkMovementMethod.getInstance());
	}

	protected void setImgGrid(Pic[] pics) {
		mGrid.setAdapter(new ImageAdapter(mGrid, pics));
	}

	/**
	 * 
	 */
	protected void setCommentCountSelected() {
		// image
		mImgCommentCount.setImageResource(R.drawable.ic_msg_comment_checked);
		mImgDeliveredCount.setImageResource(R.drawable.ic_msg_deliver);

		// text color
		mTxtCommentCount.setTextColor(0xff3a3a3a);
		mTxtDeliveredCount.setTextColor(0xffb6b6b6);
	}

	/**
	 * 
	 */
	protected void setDelieveredCountSelected() {
		// image
		mImgCommentCount.setImageResource(R.drawable.ic_msg_comment);
		mImgDeliveredCount
				.setImageResource(R.drawable.ic_msg_delivered_checked);

		// text color
		mTxtDeliveredCount.setTextColor(0xff3a3a3a);
		mTxtCommentCount.setTextColor(0xffb6b6b6);
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
			// 这里的view 不进行重用
			View view = LayoutInflater.from(getContext()).inflate(
					R.layout.item_img_grid, null);
			ImageView img = (ImageView) view.findViewById(R.id.imgMsg);

			int gridWidth = grid.getWidth() - 8;
			// int width = gridWidth / 3;
			// int height = gridWidth / 3;
			int width = gridWidth / (getCount() == 2 ? 2 : 3);
			int height = gridWidth / (getCount() == 2 ? 2 : 3);

			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					width, height);

			view.setLayoutParams(param);

			String url = pics[pos].getUrlSmall();
			if (url != null && !url.equals(""))
				ImageLoader.getInstance((Activity) getContext()).displayImage(
						url,
						img,
						SimpleImageLoaderOptions.getOptions(
								R.drawable.image_default, true),
						new FadeInImageLoadingListener(img));

			// set onClick
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getContext(),
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

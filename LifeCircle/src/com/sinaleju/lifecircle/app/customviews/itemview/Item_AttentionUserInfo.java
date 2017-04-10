package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.ChatDetailAct;
import com.sinaleju.lifecircle.app.customviews.RoundCornerImageView;
import com.sinaleju.lifecircle.app.model.Model_AttentionUserInfo;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_AttentionUserInfo extends AbsItemView {
	private static final String TAG = "Item_UserInfo";
	private TextView mTv_name;
	private RoundCornerImageView mIv_headImage;
	private ImageView mIv_vipIcon;
	private ImageButton mIv_msg;
	private Activity mActivity;
	private String Sactivity;
	private int userId;

	// private boolean type; //true：@关注用户；false：关注用户列表

	public Item_AttentionUserInfo(Context context) {
		super(context);
		mActivity = (Activity) context;
		Sactivity = mActivity.getClass().getName();
		LogUtils.i(TAG, "activity_name " + Sactivity);
		this.userId = AppContext.curUser().getUid();
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_attentionuser,
				this);
		mTv_name = (TextView) findViewById(R.id.item_attentionuser_username);
		mIv_headImage = (RoundCornerImageView) findViewById(R.id.item_attentionuser_headimage);
		// mIv_headImage.setRoundHeight(50);
		// mIv_headImage.setRoundWidth(50);
		mIv_vipIcon = (ImageView) findViewById(R.id.attentionuser_vipimage);
		mIv_msg = (ImageButton) findViewById(R.id.attentionuser_msg);

	}

	@Override
	public boolean enable() {
		return true;
	}

	@Override
	protected void onClickThis(BaseModel model) {

		if (!(model instanceof Model_AttentionUserInfo)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}

		Model_AttentionUserInfo userModel = (Model_AttentionUserInfo) model;
		Intent data = new Intent();
		int uId = userModel.getId();
		String name = userModel.getNickName();
		data.putExtra(AppConst.INTENT_MSG_AT_ID, uId);
		data.putExtra(AppConst.INTENT_MSG_AT_TYPE, userModel.getType());
		data.putExtra(AppConst.INTENT_MSG_AT_TEXT, "@" + name);

		if (Sactivity.contains("AttentionUserActivity")) {
			mActivity.setResult(400, data);
			mActivity.finish();
		} else if (Sactivity.contains("FollowListAct")) {
			// Toast.makeText(mActivity, "发送私信", 0).show();
			Intent chatIntent = new Intent(mActivity, ChatDetailAct.class);
			chatIntent.putExtra(ChatDetailAct.NAME_KEY, name);
			chatIntent.putExtra(ChatDetailAct.USER_ID_KEY, userId);
			chatIntent.putExtra(ChatDetailAct.TO_USER_ID_KEY, uId);
			chatIntent.putExtra(ChatDetailAct.TYPE_KEY, Integer.parseInt(userModel.getType()));
			mActivity.startActivity(chatIntent);
			mActivity.finish();
		} else {
			AppContext.gotoIndexActivity(mActivity,
					Integer.valueOf(userModel.getType()), userModel.getId());
			// mActivity.finish();

		}
	}

	@Override
	protected void toSet(int type, BaseModel model) {
		if (!(model instanceof Model_AttentionUserInfo)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}
		// 如果当前Atcity是AttentionUserActivity或者当前用户设置了“只允许我关注的好友给我发发私信”则隐藏Item中的message
		// 小图标。

		final Model_AttentionUserInfo userModel = (Model_AttentionUserInfo) model;
		int send_status = userModel.getSend_status();
		if (Sactivity.contains("AttentionUserActivity")) {
			mIv_msg.setVisibility(View.GONE);
		}else{
			if(send_status == 0){//“0”代表好友可发私信，“1”代表任何人可发私信。
				mIv_msg.setVisibility(View.GONE);
			}else{
				mIv_msg.setVisibility(View.VISIBLE);
			}
		}
		final String name = userModel.getNickName();
		mTv_name.setText(name);
		int userType = Integer.parseInt(userModel.getType());
		int headDrawable = PublicUtils.getUserIndexDefaultHeadImage(userType);
		mIv_headImage.setBackgroundResource(headDrawable);

		ImageLoader.getInstance((Activity) getContext()).displayImage(
				userModel.getUrl(),
				mIv_headImage,
				SimpleImageLoaderOptions
						.getRoundImageOptions(headDrawable, 100));

		LogUtils.i(TAG, "userType:  " + userType + "headDrawable: "
				+ headDrawable);
		// loadIcon(mIv_headImage, userModel, new
		// ProgressBar(mActivity),headDrawable);

		// 是否认证
		if (userModel.getIsOAth() == 1) {
			mIv_vipIcon.setVisibility(View.VISIBLE);
		} else {
			mIv_vipIcon.setVisibility(View.GONE);
		}
		mIv_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent chatIntent = new Intent(mActivity, ChatDetailAct.class);
				chatIntent.putExtra(ChatDetailAct.NAME_KEY, name);
				chatIntent.putExtra(ChatDetailAct.USER_ID_KEY, userId);
				chatIntent.putExtra(ChatDetailAct.TO_USER_ID_KEY,
						userModel.getId());
				chatIntent.putExtra(ChatDetailAct.TYPE_KEY, Integer.parseInt(userModel.getType()));
				mActivity.startActivity(chatIntent);
				// mActivity.finish();
				// Toast.makeText(mActivity, "发送私信", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// // 加载网络图片
	// private void loadIcon(ImageView topImageView, Model_AttentionUserInfo
	// bean,
	// final ProgressBar topProg, int imageId) {
	// DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
	// builder.showStubImage(imageId);
	// builder.cacheOnDisc();
	// builder.decodingOptions(PublicUtils.getOptions());
	// DisplayImageOptions options = SimpleImageLoaderOptions.getOptions(
	// imageId, true);
	// ImageLoader.getInstance().displayImage(bean.getUrl(), topImageView,
	// options, new ImageLoadingListener() {
	//
	// @Override
	// public void onLoadingStarted(String imageUri, View view) {
	// // TODO Auto-generated method stub
	// topProg.setVisibility(View.VISIBLE);
	// // LogUtils.e(TAG, imageUri);
	// }
	//
	// @Override
	// public void onLoadingFailed(String imageUri, View view,
	// FailReason failReason) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onLoadingComplete(String imageUri, View view,
	// Bitmap loadedImage) {
	// topProg.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void onLoadingCancelled(String imageUri, View view) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onBitmapCreate(boolean fromMemeory, Bitmap map) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// }

}

package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.activity.SendMsgMainActivity;
import com.sinaleju.lifecircle.app.activity.ShowBigPhotoActivity;
import com.sinaleju.lifecircle.app.camera.ADCamera;
import com.sinaleju.lifecircle.app.model.SendMsgImageModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public abstract class SendMsgImageAdapter extends BaseAdapter {

	private static final String TAG = "SendMsgImageAdapter";

	private List<SendMsgImageModel> mImageModels = new ArrayList<SendMsgImageModel>();

	private final Context mContext;

	private ADCamera camera = null;

	public abstract void clickToCamera(int position);

	public abstract void clickToDelete(int position);

	public SendMsgImageAdapter(Context context, List<SendMsgImageModel> mImageModels,
			ADCamera camera) {
		this.mContext = context;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		this.mImageModels = mImageModels;
		this.camera = camera;
	}

	@Override
	public int getCount() {
		return mImageModels == null ? 0 : mImageModels.size();
	}

	@Override
	public SendMsgImageModel getItem(int position) {
		if ((null != mImageModels && mImageModels.size() > 0)
				&& (position >= 0 && position < mImageModels.size())) {
			return mImageModels.get(position);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SendMsgImageHolder holder = null;
		if (convertView == null || null == convertView.getTag()) {
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
			int space = PublicUtils.dip2px(mContext, 150);
			int screenWidth = (dm.widthPixels - space) / 4;

			holder = new SendMsgImageHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_send_msg_image, null);
			holder.imageLayout = (FrameLayout) convertView.findViewById(R.id.msg_image_layout);
			holder.imageView = (ImageView) convertView.findViewById(R.id.msg_image);
			holder.deleteIcon = (ImageView) convertView.findViewById(R.id.msg_image_icon);
			LayoutParams param = holder.imageView.getLayoutParams();
			param.width = screenWidth;
			param.height = screenWidth;
			holder.imageView.setLayoutParams(param);
			holder.imageView.setPadding(2, 2, 2, 2);
			holder.imageView.setScaleType(ScaleType.FIT_XY);
			LayoutParams layoutParams = holder.imageLayout.getLayoutParams();
			layoutParams.width = screenWidth + 15;
			layoutParams.height = screenWidth + 15;
			holder.imageLayout.setLayoutParams(layoutParams);
			convertView.setTag(holder);
		} else {
			holder = (SendMsgImageHolder) convertView.getTag();
		}
		SendMsgImageModel model = getItem(position);
		if (model != null) {
			BitmapDrawable drawable = model.getDrawable();
			if (drawable != null) {
				setImageData(holder, drawable, position);
			} else {
				showHaveAddImage(holder, position);
			}
		}

		return convertView;
	}

	private void setImageData(SendMsgImageHolder holder, BitmapDrawable drawable, final int position) {
		holder.imageLayout.setVisibility(View.VISIBLE);
		holder.deleteIcon.setVisibility(View.VISIBLE);
		holder.imageView.setBackgroundResource(R.drawable.send_msg_head_bg);
		holder.imageView.setImageDrawable(drawable);

		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> paths = new ArrayList<String>();
				int count = 0;
				if (mImageModels.size() == 9) {
					if (mImageModels.get(8).getDrawable() == null) {
						count = 8;
					} else {
						count = 9;
					}
				} else {
					count = mImageModels.size() - 1;
				}

				for (int i = 0; i < count; i++) {
					SendMsgImageModel model = mImageModels.get(i);
					paths.add(model.getImagePath());
				}

				Intent intent = new Intent();
				intent.setClass(mContext, ShowBigPhotoActivity.class);
				intent.putStringArrayListExtra(AppConst.INTENT_IMAGE_PATH_ARRAY, paths);
				intent.putExtra(AppConst.INTENT_IMAGE_CURRENT_POSITION, position);
				((SendMsgMainActivity) mContext).startActivityForResult(intent, 201);
			}
		});

		holder.deleteIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickToDelete(position);
			}
		});
	}

	private void showHaveAddImage(SendMsgImageHolder holder, final int position) {
		holder.imageLayout.setVisibility(View.VISIBLE);
		holder.deleteIcon.setVisibility(View.GONE);
		holder.imageView.setBackgroundResource(0);
		holder.imageView.setImageResource(R.drawable.bus_infor_add_image);

		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickToCamera(position);
				gotoGallery(1, 1);
			}
		});
	}

	private int outputX = 400, outputY = 400;
	private boolean scale = true, returndata = false, noFaceDetection = false;

	private void gotoGallery(int aspectX, int aspectY) {
		camera.setDefultSmallImageUri(String.valueOf(System.currentTimeMillis()));
		camera.startGalleryForPhoto(aspectX, aspectY, outputX, outputY, scale, returndata,
				noFaceDetection);
	}

	public static class SendMsgImageHolder {
		public FrameLayout imageLayout;

		public ImageView imageView;

		public ImageView deleteIcon;
	}

}

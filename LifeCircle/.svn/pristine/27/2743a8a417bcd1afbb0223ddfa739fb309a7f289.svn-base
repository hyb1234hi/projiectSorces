package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.BusImageModel;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class BusImageAdapter extends BaseAdapter {

	private static final String TAG = "BusImageAdapter";
	private Context mContext = null;
	List<BusImageModel> mBusImages = new ArrayList<BusImageModel>();

	public BusImageAdapter(Context mContext, List<BusImageModel> mBusImages) {
		super();
		this.mContext = mContext;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		this.mBusImages = mBusImages;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mBusImages == null ? 0 : mBusImages.size();
	}

	@Override
	public BusImageModel getItem(int position) {
		// TODO Auto-generated method stub
		if ((null != mBusImages && mBusImages.size() > 0)
				&& (position >= 0 && position < mBusImages.size())) {
			return mBusImages.get(position);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ImageItemHolder holder = null;
		if (convertView == null || null == convertView.getTag()) {
			holder = new ImageItemHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_bus_imagelist_item, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.bus_item_image);
			convertView.setTag(holder);
		} else {
			holder = (ImageItemHolder) convertView.getTag();
		}

		BusImageModel imageModel = getItem(position);
		if (imageModel != null) {
			if (imageModel.getImageUrl() != null) {
				ImageLoader.getInstance((Activity) mContext).displayImage(
						imageModel.getImageUrl(),
						holder.image,
						SimpleImageLoaderOptions.getOptions(
								R.drawable.image_default, true),
						new FadeInImageLoadingListener(holder.image));
			} else {
				holder.image.setImageResource(R.drawable.image_default);
			}

		}
		return convertView;

	}

	private static class ImageItemHolder {

		public ImageView image;

	}

}

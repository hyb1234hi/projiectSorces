package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.EviewModel;
import com.sinaleju.lifecircle.app.utils.ADTimeUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class BusEviewAdapter extends BaseAdapter {

	private static final String TAG = "BusEviewAdapter";
	private Context mContext = null;
	private List<EviewModel> mEviewModels = new ArrayList<EviewModel>();

	public BusEviewAdapter(Context mContext, List<EviewModel> mEviewModels) {
		super();
		this.mContext = mContext;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		this.mEviewModels = mEviewModels;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mEviewModels == null ? 0 : mEviewModels.size();
	}

	@Override
	public EviewModel getItem(int position) {
		// TODO Auto-generated method stub
		if ((null != mEviewModels && mEviewModels.size() > 0)
				&& (position >= 0 && position < mEviewModels.size())) {
			return mEviewModels.get(position);
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

		EviewHolder holder = null;
		if (convertView == null || null == convertView.getTag()) {
			holder = new EviewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.no_slide_list_item, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.user_head_image);
			holder.name = (TextView) convertView
					.findViewById(R.id.user_head_name);
			holder.time = (TextView) convertView
					.findViewById(R.id.user_head_time);
			holder.text = (TextView) convertView
					.findViewById(R.id.user_head_text);
			holder.one = (RatingBar) convertView
					.findViewById(R.id.one_rating_bar);
			holder.two = (RatingBar) convertView
					.findViewById(R.id.two_rating_bar);
			holder.three = (RatingBar) convertView
					.findViewById(R.id.three_rating_bar);
			holder.four = (RatingBar) convertView
					.findViewById(R.id.four_rating_bar);
			convertView.setTag(holder);
		} else {
			holder = (EviewHolder) convertView.getTag();
		}

		EviewModel eview = getItem(position);
		if (eview != null) {
			if (!TextUtils.isEmpty(eview.getImageUrl())) {
				ImageLoader.getInstance((Activity) mContext).displayImage(
						eview.getImageUrl(),
						holder.image,
						SimpleImageLoaderOptions
								.getRoundImageOptions(PublicUtils
										.getUserDefaultHeadImage(0)));
			} else {
				holder.image.setImageResource(PublicUtils
						.getUserDefaultHeadImage(0));
			}
			if (!TextUtils.isEmpty(eview.getName())) {
				holder.name.setText(eview.getName());
			} else {
				holder.name.setText("");
			}
			if (!TextUtils.isEmpty(eview.getTime())) {
				holder.time.setText(ADTimeUtils.node(Integer.parseInt(eview
						.getTime())));
			} else {
				holder.time.setText("");
			}
			if (!TextUtils.isEmpty(eview.getText())) {
				holder.text.setText(eview.getText());
			} else {
				holder.text.setText("");
			}
			holder.one.setRating(eview.getQualityRB());
			holder.two.setRating(eview.getPriceRB());
			holder.three.setRating(eview.getVelocityRB());
			holder.four.setRating(eview.getAttitudeRB());
		}

		return convertView;

	}

	private static class EviewHolder {

		public ImageView image;

		public TextView name;

		public TextView time;

		public TextView text;

		public RatingBar one;

		public RatingBar two;

		public RatingBar three;

		public RatingBar four;

	}

}

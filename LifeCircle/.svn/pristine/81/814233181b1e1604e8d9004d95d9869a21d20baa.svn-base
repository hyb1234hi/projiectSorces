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
import android.widget.RatingBar;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.EviewModel;
import com.sinaleju.lifecircle.app.utils.ADTimeUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class ServiceDetailCommentAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<EviewModel> mEviewModels = new ArrayList<EviewModel>();

	public static final int FROM_SERVICE_DETAIL_COMMENT = 1;
	public static final int FROM_ALL_COMMENT = 2;
	private static final String TAG = "ServiceDetailCommentAdapter";

	private int fromIndex;

	public ServiceDetailCommentAdapter(int index, Context mContext,
			List<EviewModel> mEviewModels) {
		super();
		this.fromIndex = index;
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
					R.layout.all_comment_list_item, null);
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

		if (fromIndex == FROM_ALL_COMMENT)
			convertView.setBackgroundColor(0XF4F4F4);

		EviewModel eview = getItem(position);
		if (eview != null) {
			holder.name.setText("null".equals(eview.getName()) ? "未知" : eview
					.getName());
			holder.text.setText(eview.getText());
			holder.one.setRating(eview.getQualityRB());
			holder.two.setRating(eview.getPriceRB());
			holder.three.setRating(eview.getVelocityRB());
			holder.four.setRating(eview.getAttitudeRB());
			holder.time.setText(ADTimeUtils.node(Integer.valueOf(eview
					.getTime())));

			/*if (!TextUtils.isEmpty(eview.getImageUrl())) {
				ImageLoader.getInstance().displayImage(
						eview.getImageUrl(),
						holder.image,
						SimpleImageLoaderOptions
								.getRoundImageOptions(PublicUtils
										.getUserLeftDefaultHeadImage(0)));
			} else {
				holder.image.setImageResource(PublicUtils
						.getUserLeftDefaultHeadImage(0));
			}*/
			
			holder.image.setBackgroundResource(R.drawable.bg_frame_header);
			ImageLoader.getInstance((Activity)mContext).displayImage(eview.getImageUrl(), holder.image,
					SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils.getUserIndexDefaultHeadImage(0), 100));
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

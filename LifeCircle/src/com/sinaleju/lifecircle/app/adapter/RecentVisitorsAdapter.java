package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.activity.IndexActivity;
import com.sinaleju.lifecircle.app.model.VisitorsModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class RecentVisitorsAdapter extends BaseAdapter {

	private static final String TAG = "RecentVisitorsAdapter";
	private Context mContext = null;
	private List<VisitorsModel> mVisitors = new ArrayList<VisitorsModel>();

	public RecentVisitorsAdapter(Context mContext, List<VisitorsModel> mVisitors) {
		super();
		this.mContext = mContext;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		this.mVisitors = mVisitors;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mVisitors == null ? 0 : mVisitors.size();
	}

	@Override
	public VisitorsModel getItem(int position) {
		// TODO Auto-generated method stub
		if ((null != mVisitors && mVisitors.size() > 0)
				&& (position >= 0 && position < mVisitors.size())) {
			return mVisitors.get(position);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		VisitorItemHolder holder = null;
		if (convertView == null || null == convertView.getTag()) {
			holder = new VisitorItemHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_recent_visitor, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.recent_visitor_image);
			convertView.setTag(holder);
		} else {
			holder = (VisitorItemHolder) convertView.getTag();
		}

		final VisitorsModel visitor = getItem(position);
		if (visitor != null) {
			if (!TextUtils.isEmpty(visitor.getVisitor_header())) {
				loadHeadImage(holder.image, visitor.getVisitor_header(),
						visitor.getType());
			} else {
				holder.image.setImageResource(PublicUtils
						.getUserIndexDefaultHeadImage(visitor.getType()));
			}
		}

		holder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (visitor.getType() != AppConst.NULL_INT) {
					Intent intent = new Intent();
					intent.setClass(mContext, IndexActivity.class);
					if (visitor.getType() == 0) {
						intent.putExtra("is_personal_index", true);
						intent.putExtra("is_property", false);
					} else if (visitor.getType() == 1) {
						intent.putExtra("is_personal_index", false);
						intent.putExtra("is_property", false);
					} else if (visitor.getType() == 2) {
						intent.putExtra("is_personal_index", false);
						intent.putExtra("is_property", true);
					}
					intent.putExtra("mVisitor_Id", visitor.getVisitor_id());
					mContext.startActivity(intent);
				}
			}
		});

		return convertView;

	}

	private void loadHeadImage(ImageView imageView, String url, int type) {
		if (!TextUtils.isEmpty(url)) {
			ImageLoader.getInstance((Activity)mContext).displayImage(
					url, imageView,
						SimpleImageLoaderOptions.getRoundImageOptions(
							PublicUtils.getUserIndexDefaultHeadImage(type), 100));
		}
	}

	private static class VisitorItemHolder {

		public ImageView image;

	}

}

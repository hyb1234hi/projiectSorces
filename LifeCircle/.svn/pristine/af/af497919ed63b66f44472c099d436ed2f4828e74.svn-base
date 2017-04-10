package com.sinaleju.lifecircle.app.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.customviews.RoundCornerImageView;
import com.sinaleju.lifecircle.app.model.Model_MerchantInfo;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class MerchantListAdapter extends BaseAdapter {
	private List<Model_MerchantInfo> mList;
	private Context mContext;

	public MerchantListAdapter(Context context, List<Model_MerchantInfo> list) {
		this.mList = list;
		this.mContext = context;
	}
	
	public void setList(List<Model_MerchantInfo> newList){
		mList=newList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_merchantlist, null);
			holder.logoImage = (RoundCornerImageView) convertView
					.findViewById(R.id.item_merchantlist_logoimage);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_merchantlist_name);
			holder.intro = (TextView) convertView
					.findViewById(R.id.item_merchantlist_intro);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Model_MerchantInfo model = mList.get(position);
		// 设置logo
		int initLogo = R.drawable.bus_index_default_head_image;
	//	holder.logoImage.setBackgroundResource(initLogo);
		ImageLoader.getInstance((Activity)mContext).displayImage(model.getlogoUrl(),
				holder.logoImage,
				SimpleImageLoaderOptions.getRoundImageOptions(initLogo, 100));
		holder.name.setText(model.getName());
		holder.intro.setText(model.getIntro());
		return convertView;
	}

	class ViewHolder {
		private RoundCornerImageView logoImage;
		private TextView name;
		private TextView intro;
	}

}

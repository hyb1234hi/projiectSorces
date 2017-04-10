package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.HotTopicBean;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class HotTopicAdapter extends BaseAdapter {

	private static final String TAG = "HotTopicAdapter";
	private Context ctx;
	private ArrayList<HotTopicBean> listData;
	
	public HotTopicAdapter(Context ctx, ArrayList<HotTopicBean> list){
		this.ctx = ctx;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + ctx);
		this.listData = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = View.inflate(ctx, R.layout.hot_topic_item, null);
			
			holder.icon = (ImageView)convertView.findViewById(R.id.hot_topic_item_icon);
			holder.text = (TextView)convertView.findViewById(R.id.hot_topic_item_text);
			holder.count = (TextView)convertView.findViewById(R.id.hot_topic_item_count);
		
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.icon.setVisibility(View.VISIBLE);
		if(position == 0){
			holder.icon.setBackgroundResource(R.drawable.topic_gold);
			holder.count.setTextColor(ctx.getResources().getColor(R.color.orange));
		}else if(position == 1){
			holder.icon.setBackgroundResource(R.drawable.topic_silver);
			holder.count.setTextColor(ctx.getResources().getColor(R.color.orange));
		}else if(position == 2){
			holder.icon.setBackgroundResource(R.drawable.topic_copper);
			holder.count.setTextColor(ctx.getResources().getColor(R.color.orange));
		}else{
			holder.icon.setBackgroundResource(R.drawable.topic_gold);
			holder.icon.setVisibility(View.INVISIBLE);
			holder.count.setTextColor(ctx.getResources().getColor(R.color.color_home_page_top_shishi_remen_text_selected));
		}
		holder.text.setText("#" + listData.get(position).getName() + "#");
		holder.count.setText(listData.get(position).getCount());
		
		return convertView;
	}

	class ViewHolder{
		ImageView icon;
		TextView text, count;
	}
}

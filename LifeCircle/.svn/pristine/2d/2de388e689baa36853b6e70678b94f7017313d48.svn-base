package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.PersonalChatBean;
import com.sinaleju.lifecircle.app.utils.ADTimeUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class PersonalChatAdapter extends BaseAdapter {

	private static final String TAG = "PersonalChatAdapter";
	Context ctx;
	ArrayList<PersonalChatBean> listData;

	public PersonalChatAdapter(Context ctx, ArrayList<PersonalChatBean> listData) {
		this.ctx = ctx;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + ctx);
		this.listData = listData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData == null ? 0 : listData.size();
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

		ViewHolder holder = null;

		if (null == convertView) {
			convertView = View.inflate(ctx, R.layout.personal_chat_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.msg_item_head_icon);
			holder.name = (TextView) convertView.findViewById(R.id.msg_item_name);
			holder.content = (TextView) convertView.findViewById(R.id.msg_item_content);
			holder.time = (TextView) convertView.findViewById(R.id.msg_item_time);
			holder.unreadCount = (TextView) convertView
					.findViewById(R.id.chat_and_notice_unread_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PersonalChatBean bean = listData.get(position);

		holder.icon.setBackgroundResource(R.drawable.bg_frame_header);
		ImageLoader.getInstance((Activity) ctx).displayImage(
				bean.getUser_info().getHeader(),
				holder.icon,
				SimpleImageLoaderOptions.getRoundImageOptions(
						PublicUtils.getUserIndexDefaultHeadImage(bean.getUser_info().getType()),
						100));

		// ImageLoader
		// .getInstance()
		// .displayImage(
		// bean.getUser_info().getHeader(),
		// holder.icon,
		// SimpleImageLoaderOptions
		// .getRoundImageOptions(R.drawable.per_visitor_default_head_image));
		holder.name.setText(bean.getUser_info().getDisplay_name());
		holder.time.setText(ADTimeUtils.timeFormat((long) bean.getAddtime() * 1000));
		holder.content.setText(bean.getContent());
		if (bean.getUnread_num() == 0) {
			holder.unreadCount.setVisibility(View.GONE);
		} else if (bean.getUnread_num() > 0) {
			holder.unreadCount.setVisibility(View.VISIBLE);
			holder.unreadCount.setText(String.valueOf(bean.getUnread_num()));
		}

		return convertView;
	}

	class ViewHolder {
		public ImageView icon;
		public TextView name, time, content, unreadCount;
	}
}

package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.ChatDetailBean;
import com.sinaleju.lifecircle.app.model.PersonalChatBean.UserInfo;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class ChatDetailAdapter extends BaseAdapter {

	private static final String TAG = "ChatDetailAdapter";
	private Context ctx;
	private ArrayList<ChatDetailBean> list;
	private int userId;
	private UserInfo userInfo, toUserInfo;
	
	public ChatDetailAdapter(Context ctx, ArrayList<ChatDetailBean> list, int userId, UserInfo userInfo, UserInfo toUserInfo) {
		super();
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + ctx);
		this.list = list;
		this.userId = userId;
		this.userInfo = userInfo;
		this.toUserInfo = toUserInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == list? 0 :list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = View.inflate(ctx, R.layout.chat_detail_item_view, null);
			holder.leftLinear = (LinearLayout)convertView.findViewById(R.id.chat_left_linear);
			holder.leftContent = (TextView)convertView.findViewById(R.id.chat_detail_left_content);
			holder.leftImageView = (ImageView)convertView.findViewById(R.id.chat_detail_left_head_image);
			
			holder.rightLinear = (RelativeLayout)convertView.findViewById(R.id.chat_right_linear);
			holder.rightContent = (TextView)convertView.findViewById(R.id.chat_detail_right_content);
			holder.rightImageView = (ImageView)convertView.findViewById(R.id.chat_detail_right_head_image);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		ChatDetailBean bean = list.get(position);
		if(userId == bean.getUser_id()){//是自己发的
			holder.rightLinear.setVisibility(View.VISIBLE);
			holder.leftLinear.setVisibility(View.GONE);
			holder.rightContent.setText(bean.getContent());
			
			holder.rightImageView.setBackgroundResource(R.drawable.bg_frame_header);
			ImageLoader.getInstance((Activity)ctx).displayImage(userInfo.getHeader(), holder.rightImageView,
					SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils.getUserIndexDefaultHeadImage(userInfo.getType()), 100));
			
//			ImageLoader.getInstance().displayImage(userHeader, 
//					holder.rightImageView, 
//					SimpleImageLoaderOptions.getOptions(R.drawable.per_visitor_default_head_image, true));
		}else{//是对方发的
			holder.leftLinear.setVisibility(View.VISIBLE);
			holder.rightLinear.setVisibility(View.GONE);
			holder.leftContent.setText(bean.getContent());
			
			holder.leftImageView.setBackgroundResource(R.drawable.bg_frame_header);
			ImageLoader.getInstance((Activity)ctx).displayImage(toUserInfo.getHeader(), holder.leftImageView,
					SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils.getUserIndexDefaultHeadImage(toUserInfo.getType()), 200));
			
//			ImageLoader.getInstance().displayImage(toUserInfo.getHeader(), 
//					holder.leftImageView, 
//					SimpleImageLoaderOptions.getOptions(R.drawable.per_visitor_default_head_image, true));
		}
		return convertView;
	}

	class ViewHolder{
		public LinearLayout leftLinear;
		public RelativeLayout rightLinear;
		public TextView leftContent, rightContent;
		public ImageView leftImageView, rightImageView;
	}
	
}

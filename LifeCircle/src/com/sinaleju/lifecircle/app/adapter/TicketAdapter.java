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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.activity.CommWebViewAct;
import com.sinaleju.lifecircle.app.model.TicketModel;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public abstract class TicketAdapter extends BaseAdapter {

	private Context mContext = null;
	private List<TicketModel> mTicketModels = new ArrayList<TicketModel>();

	public abstract void onJoinTicketClick(final TicketModel ticket);

	public TicketAdapter(Context mContext, List<TicketModel> mModels) {
		super();
		this.mContext = mContext;
		this.mTicketModels = mModels;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTicketModels == null ? 0 : mTicketModels.size();
	}

	@Override
	public TicketModel getItem(int position) {
		// TODO Auto-generated method stub
		if ((null != mTicketModels && mTicketModels.size() > 0)
				&& (position >= 0 && position < mTicketModels.size())) {
			return mTicketModels.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket_list, null);
			holder.layout = (RelativeLayout) convertView.findViewById(R.id.ticket_manage_layout);
			holder.name = (TextView) convertView.findViewById(R.id.ticket_manage_name);
			holder.image = (ImageView) convertView.findViewById(R.id.ticket_manage_image);
			holder.text = (TextView) convertView.findViewById(R.id.ticket_manage_text);
			holder.btn = (Button) convertView.findViewById(R.id.ticket_manage_button);
			convertView.setTag(holder);
		} else {
			holder = (EviewHolder) convertView.getTag();
		}

		TicketModel ticket = getItem(position);
		if (ticket != null) {
			setAdapterData(ticket, holder);
			setListener(ticket, holder);
		}

		return convertView;

	}

	private void setAdapterData(TicketModel ticket, EviewHolder holder) {
		if (!TextUtils.isEmpty(ticket.getName()) && !TextUtils.isEmpty(ticket.getWeb_url())) {
			holder.layout.setVisibility(View.VISIBLE);
			holder.name.setText(ticket.getName() + "-券管理");
		} else {
			holder.layout.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(ticket.getBackground())) {
			ImageLoader.getInstance((Activity) mContext).displayImage(ticket.getBackground(),
					holder.image, SimpleImageLoaderOptions.getOptions(0, true),
					new FadeInImageLoadingListener(holder.image));
		} else {
			holder.image.setImageResource(R.drawable.ticket_image);
		}
		if (!TextUtils.isEmpty(ticket.getContent())) {
			holder.text.setText("\t\t" + ticket.getContent());
		} else {
			holder.text.setText("");
		}
		if (ticket.getIsJoin() == 2) {
			holder.btn.setText("加入活动");
		} else if (ticket.getIsJoin() == 1) {
			holder.btn.setText("进入活动");
		}

	}

	private void setListener(final TicketModel ticket, EviewHolder holder) {
		holder.layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, CommWebViewAct.class);
				String ticketUrl = PublicUtils.getTicketUrl(PublicUtils.RETURN_URL);
				intent.putExtra("ticket_url", ticketUrl);
				mContext.startActivity(intent);
			}
		});

		holder.btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onJoinTicketClick(ticket);
			}
		});
	}

	private static class EviewHolder {

		public RelativeLayout layout;

		public TextView name;

		public ImageView image;

		public TextView text;

		public Button btn;

	}

}

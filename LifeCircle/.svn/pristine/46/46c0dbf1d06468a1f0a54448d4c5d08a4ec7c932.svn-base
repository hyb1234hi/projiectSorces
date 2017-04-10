package com.sinaleju.lifecircle.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_AroundInfo;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.utils.LogUtils;


public class LocationSearchAdapter extends BaseAdapter {
	private static final String TAG = "LocationSearchAdapter";
	private  List<MultiModelBase> searchList=null;
	private Context mContext;
	public LocationSearchAdapter(Context context,List<MultiModelBase> list){
		searchList=list;
		mContext=context;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		LogUtils.e(TAG, "count: "+searchList.size());
		return searchList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return searchList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_aroundinfo, null);			
			holder.icon = (ImageView)convertView.findViewById(R.id.around_icon);
			holder.title = (TextView)convertView.findViewById(R.id.around_name);
			holder.address = (TextView)convertView.findViewById(R.id.around_adress);
			holder.fans=(TextView) convertView.findViewById(R.id.around_attention);		
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		Model_AroundInfo actModel = (Model_AroundInfo) searchList.get(position);
		String type = actModel.getType();
		String catagory = actModel.getCategory();
		String name = actModel.getName();
		String address = actModel.getAddress();
		int fans = actModel.getFans();
		LogUtils.i(TAG, "type: "+type+" catagory:  "+catagory+" name:  "+name+"address:  "+address);
		// 设置icon;
		if (type.equals("community")) { //小区
			holder.icon.setBackgroundResource(R.drawable.ac_left_one_icon_seleted);
			holder.fans.setText("已有"+fans+"人加入");
		} else { //商家
			holder.fans.setText("已有"+fans+"人关注");
			if("美食".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn12);
			}else if("休闲娱乐".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn13);
			}else if("购物".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn14);
			}else if("美容美发".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.sy_meirong);
			}else if("结婚".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn16);
			}else if("亲子".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn17);
			}else if("运动健身".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn18);
			}else if("酒店".equals(catagory)){
				holder.icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn19);
			}else if("家教".equals(catagory)){				
				holder.icon.setBackgroundResource(R.drawable.sy_jiajiao);
			}else if("家政".equals(catagory)){				
				holder.icon.setBackgroundResource(R.drawable.sy_jiazheng);
			}else if("房产".equals(catagory)){				
				holder.icon.setBackgroundResource(R.drawable.sy_fangchan);
			}
		}		
		holder.title.setText(name);
		holder.address.setText(address);
		return convertView;
	}

	class ViewHolder{
		ImageView icon;
		TextView title, address,fans;
	}
	}



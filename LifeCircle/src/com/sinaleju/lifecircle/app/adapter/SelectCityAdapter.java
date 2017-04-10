package com.sinaleju.lifecircle.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.CityModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class SelectCityAdapter extends BaseAdapter {
	private static final String TAG = "SelectCityAdapter";
	private Context mContext;
	private List<CityModel> mList;
	
	public SelectCityAdapter(Context context,List<CityModel> list) {
		mContext=context;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		mList=list;		
	}
	public void setList(List<CityModel> list){
		mList=list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler holder=null;
		if(convertView==null){
			holder=new ViewHodler();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_selectcity_city,null);
			holder.name=(TextView) convertView.findViewById(R.id.item_categorycontent_text);
		}else{
			holder=(ViewHodler) convertView.getTag();
		}
		holder.name.setText(mList.get(position).getName());
		convertView.setTag(holder);		
		return convertView ;
	}
	class ViewHodler{
		private TextView name;
	}
	

}

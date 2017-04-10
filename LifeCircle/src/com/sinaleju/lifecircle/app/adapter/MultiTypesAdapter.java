package com.sinaleju.lifecircle.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class MultiTypesAdapter extends BaseAdapter {

	private static final String TAG = "MultiTypesAdapter";
	private MultiModelsSet data;
	private Context mContext;
	
	public MultiTypesAdapter(MultiModelsSet data, Context mContext) {
		this.data = data;
		this.mContext = mContext;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public int getItemViewType(int position) {
		return data.get(position).getModelType();
	}

	@Override
	public int getViewTypeCount() {
		return data.typeCount()<=0?1:data.typeCount();
	}

	@Override
	public Object getItem(int index) {
		if(index>=data.size())
			return null;
		return data.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		
		if (convertView == null) {
			convertView = data.get(pos).createConvertView(mContext);
		}

		((AbsItemView) convertView).set(data.getMSG_TYPE(),data.get(pos));

		return convertView;
	}

}

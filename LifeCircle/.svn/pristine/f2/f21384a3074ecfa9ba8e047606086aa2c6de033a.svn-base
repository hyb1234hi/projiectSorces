package com.sinaleju.lifecircle.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_Community;
import com.sinaleju.lifecircle.app.model.Model_TopicsList;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class TopicResultAdapter extends BaseAdapter {
	private static final String TAG = "TopicResultAdapter";
	private List<MultiModelBase> modelList;
	private Context mContext;

	public TopicResultAdapter(Context mContext) {
		this.mContext = mContext;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
	}

	public void setList(List<MultiModelBase> topicList) {		
		this.modelList = topicList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return modelList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return modelList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler holder = null;
		if (convertView == null) {
			holder = new ViewHodler();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_selectcity_city, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_categorycontent_text);
		} else {
			holder = (ViewHodler) convertView.getTag();
		}
		MultiModelBase base=modelList.get(position);
		if(base instanceof Model_TopicsList){
			Model_TopicsList topics=(Model_TopicsList) base;
			holder.name.setText(topics.getName());
		}else if(base instanceof Model_Community){
			Model_Community community=(Model_Community) base;
			holder.name.setText(community.getmCommunityName());
		}
		convertView.setTag(holder);
		return convertView;
	}

	class ViewHodler {
		private TextView name;
	}

}

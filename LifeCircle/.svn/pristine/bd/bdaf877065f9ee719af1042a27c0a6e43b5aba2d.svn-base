package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.model.Model_TopicsList;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Item_TopicsList extends AbsItemView {
	private static final String TAG = "Item_TopicsList";
	private TextView mTopicsName;
	private Activity mActivity;

	public Item_TopicsList(Context context) {
		super(context);
		mActivity=(Activity) context;
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.item_selectcity_city, this);
		mTopicsName = (TextView) findViewById(R.id.item_categorycontent_text);
	}

	@Override
	public boolean enable() {
		return true;
	}

	@Override
	protected void onClickThis(BaseModel model) {

		if (!(model instanceof Model_TopicsList)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}
		Model_TopicsList TopicsModel = (Model_TopicsList) model;
		//Intent intent = new Intent(getContext(), RegistActivity.class);
		Intent  data=new Intent();
		data.putExtra(AppConst.INTENT_MSG_TOPIC_ID, TopicsModel.getId());
		data.putExtra(AppConst.INTENT_MSG_TOPIC_TEXT, TopicsModel.getName());
		mActivity.setResult(300, data);
		mActivity.finish();
	//	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//	getContext().startActivity(intent);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		if (!(model instanceof Model_TopicsList)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}

		Model_TopicsList topicsModel = (Model_TopicsList) model;
		String name = topicsModel.getName();
		mTopicsName.setText(name);
	}

}

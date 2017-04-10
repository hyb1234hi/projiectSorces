package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_CategoryLabel;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Item_CategoryLabel extends AbsItemView{
	private static final String TAG = "Item_CategoryLabel";
	private TextView mTextLabel;
	public Item_CategoryLabel(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_selectcity_table, this);
	    mTextLabel =(TextView) findViewById(R.id.item_categorylabel_text);
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {
		
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		if(!(model instanceof Model_CategoryLabel)){
			LogUtils.e(TAG, "class cast  error");
			return;
		}
		Model_CategoryLabel actModel=(Model_CategoryLabel) model;
		String labelStr = actModel.getmLabelString();
		mTextLabel.setText(labelStr);
	}


}

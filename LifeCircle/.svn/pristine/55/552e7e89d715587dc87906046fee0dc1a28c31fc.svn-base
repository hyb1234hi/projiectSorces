package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageCount;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_BusinessPageCount extends AbsItemView {

	private TextView mCountTitle;
	private TextView mCountNumber;

	public Item_BusinessPageCount(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_business_count,
				this);

		initViews();
	}

	private void initViews() {
		mCountTitle = (TextView) findViewById(R.id.bus_count_title);
		mCountNumber = (TextView) findViewById(R.id.bus_count_num);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		Model_BusinessPageCount count = (Model_BusinessPageCount) model;
		if (count.isProperty()) {
			mCountTitle.setText("物业信息");
		} else {
			mCountTitle.setText("商家信息");
		}
		if (count.getCount() >= 0) {
			mCountNumber.setText("(共" + count.getCount() + "条)");
		} else {
			mCountNumber.setText("(共0条)");
		}
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

}

package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_NoticeSystem;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_NoticeSystem extends AbsItemView {

	private TextView mSystemTitle;
	private TextView mSystemMsg;

	public Item_NoticeSystem(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_notice_system,
				this);
		initViews();
	}

	private void initViews() {
		mSystemTitle = (TextView) findViewById(R.id.system_title);
		TextPaint tp = mSystemTitle.getPaint();
		tp.setFakeBoldText(true);
		mSystemMsg = (TextView) findViewById(R.id.system_msg);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		final Model_NoticeSystem system = (Model_NoticeSystem) model;
		if (!TextUtils.isEmpty(system.getmSystemMsg())) {
			mSystemMsg.setText(system.getmSystemMsg());
		} else {
			mSystemMsg.setText("");
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

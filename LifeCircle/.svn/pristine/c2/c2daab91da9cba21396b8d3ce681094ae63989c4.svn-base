package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageIntroduce;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_BusinessPageIntroduce extends AbsItemView {

	private TextView mNormalAm;
	private TextView mNormalPm;
	private TextView mHaltAm;
	private TextView mHaltPm;
	private TextView mIntroduceText;
	private TextView mIntroTitle;

	public Item_BusinessPageIntroduce(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_bus_introduce,
				this);

		initViews();
	}

	private void initViews() {
		mNormalAm = (TextView) findViewById(R.id.normal_am);
		mNormalPm = (TextView) findViewById(R.id.normal_pm);
		mHaltAm = (TextView) findViewById(R.id.halt_am);
		mHaltPm = (TextView) findViewById(R.id.halt_pm);
		mIntroduceText = (TextView) findViewById(R.id.bus_introduce_text);
		mIntroTitle = (TextView) findViewById(R.id.bus_intro_title);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		Model_BusinessPageIntroduce introduce = (Model_BusinessPageIntroduce) model;
		if (!TextUtils.isEmpty(introduce.getNormal_am_start())
				&& !TextUtils.isEmpty(introduce.getNormal_am_end())) {
			mNormalAm.setText("上午 " + introduce.getNormal_am_start() + "-"
					+ introduce.getNormal_am_end());
		} else {
			mNormalAm.setText("上午 ");
		}

		if (!TextUtils.isEmpty(introduce.getNormal_pm_start())
				&& !TextUtils.isEmpty(introduce.getNormal_pm_end())) {
			mNormalPm.setText("下午 " + introduce.getNormal_pm_start() + "-"
					+ introduce.getNormal_pm_end());
		} else {
			mNormalPm.setText("下午 ");
		}

		if (!TextUtils.isEmpty(introduce.getHalt_am_start())
				&& !TextUtils.isEmpty(introduce.getHalt_am_end())) {
			mHaltAm.setText("上午 " + introduce.getHalt_am_start() + "-"
					+ introduce.getHalt_am_end());
		} else {
			mHaltAm.setText("上午 ");
		}

		if (!TextUtils.isEmpty(introduce.getHalt_pm_start())
				&& !TextUtils.isEmpty(introduce.getHalt_pm_end())) {
			mHaltPm.setText("下午 " + introduce.getHalt_pm_start() + "-"
					+ introduce.getHalt_pm_end());
		} else {
			mHaltPm.setText("下午 ");
		}

		if (!TextUtils.isEmpty(introduce.getIntroduction())) {
			mIntroduceText.setText(introduce.getIntroduction());
		} else {
			mIntroduceText.setText("");
		}
		if (introduce.isProperty()) {
			mIntroTitle.setText("公司介绍");
		} else {
			mIntroTitle.setText("商家介绍");
		}
		disableRefresh();
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

}

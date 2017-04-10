package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.adapter.BusEviewAdapter;
import com.sinaleju.lifecircle.app.customviews.NoSlideListView;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageEview;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_BusinessPageEview extends AbsItemView {

	private TextView eviewNum = null;
	private NoSlideListView eviewList = null;
	private RelativeLayout showMoreLayout = null;
	private LinearLayout busEviewButton = null;
	private LinearLayout businessEviewLayout = null;
	private TextView mEviewTitle = null;

	public Item_BusinessPageEview(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_business_eview,
				this);

		initViews();
		setItemListeners();
	}

	private void initViews() {
		eviewNum = (TextView) findViewById(R.id.eview_num);
		eviewList = (NoSlideListView) findViewById(R.id.business_eview_list);
		showMoreLayout = (RelativeLayout) findViewById(R.id.show_more);
		busEviewButton = (LinearLayout) findViewById(R.id.bus_eview_button);
		businessEviewLayout = (LinearLayout) findViewById(R.id.business_eview_layout);
		mEviewTitle = (TextView) findViewById(R.id.bus_eview_title);
	}

	@Override
	protected void toSet(int type,BaseModel model) {
		if (eviewList == null) {
			initViews();
		}

		Model_BusinessPageEview eview = (Model_BusinessPageEview) model;
		eviewNum.setText("(共" + eview.getCount() + "条)");
		if (eview.getEviewModels().size() != 0) {
			BusEviewAdapter eviewAdapter = new BusEviewAdapter(getContext(),
					eview.getEviewModels());
			eviewList.setAdapter(eviewAdapter);
		} else {
			businessEviewLayout.setVisibility(View.GONE);
		}
		if (eview.isProperty()) {
			mEviewTitle.setText("业主评价");
		} else {
			mEviewTitle.setText("顾客评价");
		}
		if (eview.getCount() > 2) {
			showMoreLayout.setVisibility(View.VISIBLE);
		} else {
			showMoreLayout.setVisibility(View.GONE);
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

	private void setItemListeners() {

		showMoreLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_BUSINESS_SHOWMORE_EVIEW
								+ getContext().hashCode());
			}
		});

		busEviewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_BUSINESS_EVIEW
								+ getContext().hashCode());
			}
		});
	}

}

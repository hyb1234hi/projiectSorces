package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailName;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_TrendsDetailName extends AbsItemView {
	
	private static final String TAG = "Item_TrendsDetailName";
	private ImageView mHeaderPortrait;
	private TextView mName;
	private int uid = -1;

	public Item_TrendsDetailName(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_trends_detail_name, this);
		
		mHeaderPortrait = (ImageView) findViewById(R.id.imgHeadPortrait);
		mName = (TextView) findViewById(R.id.txtName);
		
	}

	@Override
	public boolean enable() {
		return true;
	}

	@Override
	protected void onClickThis(BaseModel model) {
		if(uid==-1)
			return;
		Model_TrendsDetailName m = (Model_TrendsDetailName) model;
		
		AppContext.gotoIndexActivity(getContext(), m.getType(), m.getUid());
		
	}
	
	@Override
	protected void toSet(int type,BaseModel model) {
		
		if(!(model instanceof Model_TrendsDetailName)){
			LogUtils.e(TAG, "class cast error");
			return;
		}
		Model_TrendsDetailName nameModel = (Model_TrendsDetailName) model;
		String url = nameModel.getHeader();
		String name = nameModel.getName();
		uid  = nameModel.getUid();
		final int utype = nameModel.getType();
		
		//create listener
		OnClickListener l = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppContext.performVisitPeople(getContext(), uid, utype);
			}
		};
		
		//name
		mName.setText(name);
		mName.getPaint().setFakeBoldText(true);
		mName.setOnClickListener(l);
		
		//header
		if (url == null || url.equals("")) {
			int res = PublicUtils.getUserDefaultHeadImage(utype);
			mHeaderPortrait.setImageResource(res);
			return;
		}
		ImageLoader.getInstance((Activity)getContext()).displayImage(url, mHeaderPortrait, SimpleImageLoaderOptions.getRoundImageOptions(0, 100));
		mHeaderPortrait.setOnClickListener(l);
		
		
		ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_TRENDSDETAIL_MSG_NAME, this);
	}

}

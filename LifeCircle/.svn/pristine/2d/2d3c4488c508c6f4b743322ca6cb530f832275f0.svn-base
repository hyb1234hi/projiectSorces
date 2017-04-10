package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.activity.LocationMapActivity;
import com.sinaleju.lifecircle.app.model.Model_AroundInfo;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Item_AroundInfo extends AbsItemView {
	private static final String TAG = "Item_AroundInfo";
	private ImageView mIv_icon;
	private TextView mTv_name;
	private TextView mTv_adress;
	private TextView mTv_fans;
	private ImageView mIv_selected;
	private Activity mActivity;

	public Item_AroundInfo(Context context) {
		super(context);
		mActivity = (Activity) context;
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_aroundinfo,this);
		mIv_icon = (ImageView) findViewById(R.id.around_icon);
		mTv_name = (TextView) findViewById(R.id.around_name);
		mTv_adress = (TextView) findViewById(R.id.around_adress);
		mTv_fans = (TextView) findViewById(R.id.around_attention);
		mIv_selected = (ImageView) findViewById(R.id.around_selected);
	}

	@Override
	public boolean enable() {
		return true;
	}

	@Override
	protected void onClickThis(BaseModel model) {
		//Toast.makeText(mActivity, "click item: ",0).show();

		if (!(model instanceof Model_AroundInfo)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}
		Model_AroundInfo actModel = (Model_AroundInfo) model;
		//Toast.makeText(mActivity, actModel.getName(), Toast.LENGTH_SHORT)	.show();
		Intent data=new Intent();
		data.putExtra(LocationMapActivity.MYLOCATION_NAME, actModel.getName());
		data.putExtra(LocationMapActivity.MYLOCATION_LONGITUDE, actModel.getLongitude());
		data.putExtra(LocationMapActivity.MYLOCATION_LATITUDE, actModel.getLatitude());
		mActivity.setResult(Activity.RESULT_OK, data);
		mActivity.finish();
		

		// ApplicationFacade.getInstance().sendNotification(
		// AppConst.APP_FACADE_COMMUNITY_SELECT_DONE, actModel);

	}

	@Override
	protected void toSet(int type1,BaseModel model) {
		if (!(model instanceof Model_AroundInfo)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}
		Model_AroundInfo actModel = (Model_AroundInfo) model;
		String type = actModel.getType();
		String catagory = actModel.getCategory();
		String name = actModel.getName();
		String address = actModel.getAddress();
		int fans = actModel.getFans();
		// 设置icon;
		if (type.equals("community")) { //小区
			mIv_icon.setBackgroundResource(R.drawable.ac_left_one_icon_seleted);
			mTv_fans.setText("已有"+fans+"人加入");
		} else { //商家
			mTv_fans.setText("已有"+fans+"人关注");
			if("美食".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn12);
			}else if("休闲娱乐".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn13);
			}else if("购物".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn14);
			}else if("美容美发".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.sy_meirong);
			}else if("结婚".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn16);
			}else if("亲子".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn17);
			}else if("运动健身".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn18);
			}else if("酒店".equals(catagory)){
				mIv_icon.setBackgroundResource(R.drawable.fr_right_view_hover_btn19);
			}else if("房产".equals(catagory)){				
				mIv_icon.setBackgroundResource(R.drawable.sy_fangchan);
			}else if("家政".equals(catagory)){				
				mIv_icon.setBackgroundResource(R.drawable.sy_jiazheng);
			}else if("家教".equals(catagory)){				
				mIv_icon.setBackgroundResource(R.drawable.sy_jiajiao);
			}
		}		
		if(LocationMapActivity.callInent.getStringExtra("location").equals(name)){
			mIv_selected.setVisibility(View.VISIBLE);
			if(fans==-1){
				mTv_fans.setText("");
			}
		}else{
			mIv_selected.setVisibility(View.GONE);
		}
		mTv_name.setText(name);
		mTv_adress.setText(address);
	
	}
	
	
}

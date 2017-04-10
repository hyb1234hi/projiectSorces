package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.model.Model_Community;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class Item_Community extends AbsItemView {
	private static final String TAG = "Item_Community";
	private TextView mTextLabel;
	private Activity mActivity;

	public Item_Community(Context context) {
		super(context);
		mActivity = (Activity) context;
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.item_selectcity_city, this);
		mTextLabel = (TextView) findViewById(R.id.item_categorycontent_text);
	}

	@Override
	public boolean enable() {
		return true;
	}

	@Override
	protected void onClickThis(BaseModel model) {

		if (!(model instanceof Model_Community)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}
		Model_Community actModel = (Model_Community) model;
/*<<<<<<< .mine
		// Intent intent = new Intent(getContext(), RegistActivity.class);
		Intent data = new Intent(mActivity, HomeActivity.class);
		data.putExtra("communitymodel", actModel);
		//游客选择城市后进入小区首页
		if (AppContext.ISVISITOR) {
			AppContext.createVisitor(Integer.valueOf(actModel.getId()), actModel.getmCommunityName());
			mActivity.startActivity(data);
			AppContext.ISVISITOR = false;
		} else {
			mActivity.setResult(0, data);
		}
		mActivity.finish();
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// getContext().startActivity(intent);
=======*/

		//发送通知到CommunityActivity
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_COMMUNITY_SELECT_DONE, actModel);

		// // Intent intent = new Intent(getContext(), RegistActivity.class);
		// Intent data = new Intent(mActivity, HomeActivity.class);
		// data.putExtra("communitymodel", actModel);
		// //游客选择城市后进入小区首页
		// if (AppContext.ISVISITOR) {
		// mActivity.startActivity(data);
		// AppContext.ISVISITOR = false;
		// } else {
		// mActivity.setResult(0, data);
		// }
		// mActivity.finish();
		// // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// // getContext().startActivity(intent);

	}

	@Override
	protected void toSet(int type,BaseModel model) {
		if (!(model instanceof Model_Community)) {
			LogUtils.e(TAG, "class cast  error");
			return;
		}

		Model_Community actModel = (Model_Community) model;
		String labelStr = actModel.getmCommunityName();
		mTextLabel.setText(labelStr);
	}

}

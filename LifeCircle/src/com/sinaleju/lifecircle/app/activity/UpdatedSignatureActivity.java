package com.sinaleju.lifecircle.app.activity;

import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSUpdateUserSign;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class UpdatedSignatureActivity extends BaseActivity implements TextWatcher {

	private static final String TAG = "UpdatedSignatureActivity";
	private ImageView mHeadImage = null;
	private TextView mLastNum = null;
	private EditText mMianEdit = null;
	private UserBean mUserBean = null;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_updated_signature;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "------------initViews");
		mHeadImage = (ImageView) findViewById(R.id.sign_head_image);
		mLastNum = (TextView) findViewById(R.id.sign_last_num);
		mMianEdit = (EditText) findViewById(R.id.sign_main_edit);
		mMianEdit.addTextChangedListener(this);
		mUserBean = AppContext.curUser();
		if (mUserBean != null) {
			if (!TextUtils.isEmpty(mUserBean.getHeaderUrl())) {
				ImageLoader.getInstance(UpdatedSignatureActivity.this)
						.displayImage(
								mUserBean.getHeaderUrl(),
								mHeadImage,
								SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils
										.getUserIndexDefaultHeadImage(mUserBean.getType()), 100));
			} else {
				mHeadImage.setImageResource(PublicUtils.getUserIndexDefaultHeadImage(mUserBean
						.getType()));
			}
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		mTitleBar.initRightButtonTextOrResId(0, R.drawable.selector_topbar_submit_button);
		String editHint = "记录下你现在的心情吧...";
		if (mUserBean.getType() == 0) {
			mTitleBar.setTitleName("更新签名");
			editHint = "记录下你现在的心情吧...";
		} else if (mUserBean.getType() == 1) {
			mTitleBar.setTitleName("更新商家介绍");
			editHint = "简单写下当前的优惠活动信息吧...";
		} else if (mUserBean.getType() == 2) {
			mTitleBar.setTitleName("更新物业介绍");
			editHint = "简单介绍下本公司的情况吧...";
		}
		mMianEdit.setHint(editHint);
		if (!TextUtils.isEmpty(mUserBean.getSignature())) {
			mMianEdit.setText(mUserBean.getSignature());
			Editable etext = mMianEdit.getText();
			int position = etext.length();
			Selection.setSelection(etext, position);
		}
	}

	private RSUpdateUserSign rs = null;

	private void updateUserSign() {
		String content = mMianEdit.getText().toString();
		if (TextUtils.isEmpty(content)) {
			content = "";
		}
		if (mUserBean.getSignature().equals(content)) {
			finish();
			return;
		}
		content = content.replaceAll("\n", " ");
		if (rs == null) {
			rs = new RSUpdateUserSign(mUserBean.getUid(), content);

			rs.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					System.out.print("onSuccess: \n" + result);
					hideProgressDialog();
					showToast("更改成功");
					mUserBean.setSignature(mMianEdit.getText().toString());
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH);
					rs = null;
					setResult(601);
					PublicUtils.hideSoftInputMethod(UpdatedSignatureActivity.this);
					finish();
				}

			});

			rs.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					showToast("更改失败");
					rs = null;
				}

			});
			showProgressDialog("正在更改...", true);
			rs.asyncExecute(this);
		}
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicUtils.hideSoftInputMethod(UpdatedSignatureActivity.this);
				finish();
			}
		});

		mTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateUserSign();
			}
		});
	}

	private void setLastNumber() {
		String text = mMianEdit.getText().toString();
		int destLen = text.toCharArray().length;
		int len = 15 - destLen;
		mLastNum.setText(String.valueOf(len));
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		setLastNumber();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

}

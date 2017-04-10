package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.adapter.ChangeImageAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.camera.ADCamera;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class ChangeHeadBgActivity extends BaseActivity {

	private static final String TAG = "ChangeHeadBgActivity";

	private GridView mChangeHeadGridView = null;
	private ChangeImageAdapter adapter = null;
	private List<String> mIntegers = new ArrayList<String>();
	private TextView btnImage = null;
	private TextView btnPhoto = null;
	private ADCamera camera = null;
	private String imagePath = null;
	private UserBean mUserBean = AppContext.curUser();
	private int curIndex = -1;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_change_head_bg;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		// TODO Auto-generated method stub
		mChangeHeadGridView = (GridView) findViewById(R.id.change_head_gridView);
		btnImage = (TextView) findViewById(R.id.change_come_image);
		btnPhoto = (TextView) findViewById(R.id.change_come_photo);

		if (mUserBean.getType() == 0) {
			addData(PublicUtils.perIndexBg);
		} else if (mUserBean.getType() == 1) {
			addData(PublicUtils.busIndexBg);
		} else if (mUserBean.getType() == 2) {
			addData(PublicUtils.proIndexBg);
		}

		adapter = new ChangeImageAdapter(this, mIntegers) {

			@Override
			public void doImageViewClick(int index) {
				// TODO Auto-generated method stub
				if (index >= 0) {
					curIndex = index;
					if (mUserBean.getType() == 0) {
						AppContext.mPerIndexBg = index;
					} else if (mUserBean.getType() == 1) {
						AppContext.mBusIndexBg = index;
					} else if (mUserBean.getType() == 2) {
						AppContext.mProIndexBg = index;
					}
				}

			}
		};

		mChangeHeadGridView.setAdapter(adapter);

		camera = ADCamera.builder(this);
	}

	private void addData(String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (!TextUtils.isEmpty(array[i])) {
				mIntegers.add(array[i]);
			}
		}
	}

	private int aspectX = 4, aspectY = 3, outputX = 400, outputY = 300;
	private boolean scale = true, returndata = false, noFaceDetection = false;

	private void gotoCamera() {
		camera.setDefultSmallImageUri(String.valueOf(System.currentTimeMillis()));
		camera.startCameraForPhoto(ADCamera.TAKE_CAMERA_PICTURE, aspectX, aspectY, outputX,
				outputY, scale, returndata, noFaceDetection);
	}

	private void gotoGallery() {
		camera.setDefultSmallImageUri(String.valueOf(System.currentTimeMillis()));
		camera.startGalleryForPhoto(aspectX, aspectY, outputX, outputY, scale, returndata,
				noFaceDetection);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("更换背景");
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (curIndex >= 0) {
					setResult(312);
				}
				finish();
			}
		});

		btnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoGallery();
			}
		});

		btnPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoCamera();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (curIndex >= 0) {
			if (mUserBean.getType() == 0) {
				AppContext.updatePerIndexPreferences(mUserBean);
			} else if (mUserBean.getType() == 1) {
				AppContext.updateBusIndexPreferences(mUserBean);
			} else if (mUserBean.getType() == 2) {
				AppContext.updateProIndexPreferences(mUserBean);
			}
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (curIndex >= 0) {
				setResult(312);
			}
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 完成照相后回调用此方法
		super.onActivityResult(requestCode, resultCode, data);

		if (camera.onActivityResult(null, requestCode, resultCode, data) && requestCode != 113) {
			imagePath = camera.getPath();
			Intent intent = new Intent();
			intent.putExtra("head_layout_bg_path", imagePath);
			setResult(311, intent);
			finish();
		}
	}

}

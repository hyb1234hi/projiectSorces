package com.sinaleju.lifecircle.app.activity;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iss.loghandler.Log;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.mainscrolllayout.OnViewChangeListener;
import com.sinaleju.lifecircle.app.customviews.mainscrolllayout.ScrollLayoutGuide;
import com.sinaleju.lifecircle.app.utils.LogUtils;
public class GuideActivity extends BaseActivity implements OnViewChangeListener, OnClickListener {

	private ScrollLayoutGuide mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	//private Button mButton_weibo;
	//private Button mButton_login;
	//private Button mButton_browse;
	private LinearLayout pointLLayout;
	private BitmapDrawable mBg1;
	private BitmapDrawable mBg2;
	private BitmapDrawable mBg3;
	//private BitmapDrawable mBg4;
	//private BitmapDrawable mBg5;
	//private static final int REQUEST_CODE_VISITOR_MODE = 10;// 游客模式启动城市选择
	private static final String TAG = "GuideActivity";
	//private unSlipViewPager mViewPager;
	//private ViewPagerAdapter viewPagerAdapter;

	// private Animation alphaAnimation1;
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		mTitleBar.setVisibility(View.GONE);
		return R.layout.ac_guide;		
		
	}
	

	@SuppressWarnings("deprecation")
	private void initBitmapDrawableData() {
		mBg1 = new BitmapDrawable(this.getResources(), readBitMap(this,
				R.drawable.index1));
		mBg2 = new BitmapDrawable(this.getResources(), readBitMap(this,
				R.drawable.index2));
		mBg3 = new BitmapDrawable(this.getResources(), readBitMap(this,
				R.drawable.index3));
//		mBg4 = new BitmapDrawable(this.getResources(), readBitMap(this,
//				R.drawable.start_bg4));
//		mBg5 = new BitmapDrawable(this.getResources(), readBitMap(this,
//				R.drawable.start_bg5));
		mScrollLayout.getChildAt(0).setBackgroundDrawable(mBg1);
		mScrollLayout.getChildAt(1).setBackgroundDrawable(mBg2);
		mScrollLayout.getChildAt(2).setBackgroundDrawable(mBg3);
//		mScrollLayout.getChildAt(3).setBackgroundDrawable(mBg4);
//		mScrollLayout.getChildAt(4).setBackgroundDrawable(mBg5);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		mScrollLayout = (ScrollLayoutGuide) findViewById(R.id.main_scrollLayout);// 背景图片父布局
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);// 圆点父而局
		initBitmapDrawableData();
		//mButton_weibo = (Button) findViewById(R.id.main_button_weibo);
		//mButton_login = (Button) findViewById(R.id.main_button_login);
		//mButton_browse = (Button) findViewById(R.id.main_button_visitorbrowse);
		//mViewPager = (unSlipViewPager) findViewById(R.id.main_viewpager);
		//viewPagerAdapter = new ViewPagerAdapter();
		//mViewPager.setAdapter(viewPagerAdapter);
	}
	@Override
	protected void initData() {
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];

		// 将每个圆点设置为默认状态。
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		// 将第一个圆点设为高亮状态
		imgs[currentItem].setEnabled(false);
		mScrollLayout.setOnViewChangeListener(this);

	}
	@Override
	public void onClick(View v) {
		Log.i("MainActivity", "按钮被单击");
		switch (v.getId()) {
		
		default:

			break;
		}

	}

	
	

	

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);// 设置当前圆点的前一个圆点的状态为默认状态。
		imgs[position].setEnabled(false);// 设置当前圆点的前圆点的状态为高亮状态。
		currentItem = position;
		
	}

	@Override
	protected void onDestroy() {
		
		if (mBg1 != null) {
			mScrollLayout.getChildAt(0).setBackgroundResource(0);
			mBg1.setCallback(null);
			mBg1.getBitmap().recycle();
			mBg1 = null;
		}
		if (mBg2 != null) {
			mScrollLayout.getChildAt(1).setBackgroundResource(0);
			mBg2.setCallback(null);
			mBg2.getBitmap().recycle();
			mBg2 = null;
		}
		if (mBg3 != null) {
			mScrollLayout.getChildAt(2).setBackgroundResource(0);
			mBg3.setCallback(null);
			mBg3.getBitmap().recycle();
			mBg3 = null;
		}
//		if (mBg4 != null) {
//			mScrollLayout.getChildAt(3).setBackgroundResource(0);
//			mBg4.setCallback(null);
//			mBg4.getBitmap().recycle();
//			mBg4 = null;
//		}
//		if (mBg5 != null) {
//			mScrollLayout.getChildAt(4).setBackgroundResource(0);
//			mBg5.setCallback(null);
//			mBg5.getBitmap().recycle();
//			mBg5 = null;
//		}
		super.onDestroy();
	}

	// 读取图片资源
	public Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		try {
			InputStream is = context.getResources().openRawResource(resId);
			return BitmapFactory.decodeStream(is, null, opt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		
	}

}
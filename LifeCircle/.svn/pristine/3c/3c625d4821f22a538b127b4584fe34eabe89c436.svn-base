package com.sinaleju.lifecircle.app.activity;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.customviews.unSlipViewPager;
import com.sinaleju.lifecircle.app.customviews.mainscrolllayout.OnViewChangeListener;
import com.sinaleju.lifecircle.app.customviews.mainscrolllayout.ScrollLayout;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.umeng.analytics.MobclickAgent;

public class StartActivity extends OrmLiteBaseActivity<DatabaseOpenHelper>
		implements OnViewChangeListener, OnClickListener {

	private ScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private Button mButton_weibo;
	private Button mButton_login;
	private Button mButton_browse;
	private LinearLayout pointLLayout;
	private BitmapDrawable mBg1;
	private BitmapDrawable mBg2;
	private BitmapDrawable mBg3;
//	private BitmapDrawable mBg4;
//	private BitmapDrawable mBg5;
	private static final int REQUEST_CODE_VISITOR_MODE = 10;// 游客模式启动城市选择
	private static final String TAG = "StartActivity";
	private unSlipViewPager mViewPager;
	private ViewPagerAdapter viewPagerAdapter;

	// private Animation alphaAnimation1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.v(TAG, "------------initViews");
		((AppContext) getApplicationContext()).clearTask();
		((AppContext) getApplicationContext()).addActs(this);

		setContentView(R.layout.ac_start);
		initView();
		initData();
		setListener();
		initBitmapDrawableData();
	}

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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

	protected void initView() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrollLayout);// 背景图片父布局
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);// 圆点父而局
		mButton_weibo = (Button) findViewById(R.id.main_button_weibo);
		mButton_login = (Button) findViewById(R.id.main_button_login);
		mButton_browse = (Button) findViewById(R.id.main_button_visitorbrowse);
		//mViewPager = (unSlipViewPager) findViewById(R.id.main_viewpager);
		//viewPagerAdapter = new ViewPagerAdapter();
		//mViewPager.setAdapter(viewPagerAdapter);
	}

	protected void initData() {
		// alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
		// alphaAnimation1.setDuration(800);
		// 自动滚动
		// mScrollLayout.autoScroll();
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
		mScrollLayout.SetOnViewChangeListener(this);

	}

	private void setListener() {
		mButton_browse.setOnClickListener(this);
		mButton_login.setOnClickListener(this);
		mButton_weibo.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		LogUtils.i("MainActivity", "按钮被单击");
		switch (v.getId()) {
		case R.id.main_button_weibo:
			// mScrollLayout.stopAutoScroll();
			Intent sinaLoginIntent = new Intent(StartActivity.this,
					LoginActivity.class);
			sinaLoginIntent.putExtra("sinalogin", true);
			startActivity(sinaLoginIntent);

			break;
		case R.id.main_button_login:
			// mScrollLayout.stopAutoScroll();
			Intent loginIntent = new Intent(StartActivity.this,
					LoginActivity.class);
			// Intent loginIntent=new Intent(this, OAuthActivity.class);
			startActivity(loginIntent);

			break;
		case R.id.main_button_visitorbrowse:
			// mScrollLayout.stopAutoScroll();
			MobclickAgent.onEvent(this, PublicUtils.MOBCLICKAGENT_GUEST);
			Intent seletCityActivity = new Intent(this,
					SelectCityActivity.class);
			startActivityForResult(seletCityActivity, REQUEST_CODE_VISITOR_MODE);
			break;
		default:

			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (returnMsgForVisitor(requestCode, resultCode)) {
			// get data
			int community_id = data.getIntExtra("community_id", -1);
			String community_name = data.getStringExtra("community_name");
			int topicCount=data.getIntExtra("community_topic", 0);
			LogUtils.e(TAG, "topicCount: "+topicCount);
			// check data valid
			if (community_id == -1) {
				Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
				return;
			}

			// create visitor
			AppContext.createVisitor(community_id, community_name ,topicCount);

			// goto homeActivity			
			gotoHomeActivity();
		}
	}

	private void gotoHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private boolean returnMsgForVisitor(int requestCode, int resultCode) {
		return requestCode == REQUEST_CODE_VISITOR_MODE
				&& resultCode == Activity.RESULT_OK;
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
		// 更改当前页面文字信息
		// View view=mViewPager.getChildAt(currentItem);
		// if(view!=null){
		// view.startAnimation(alphaAnimation1);
		// }
		//mViewPager.setCurrentItem(currentItem);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mScrollLayout.stopAutoScroll();

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

	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mScrollLayout.getChildCount();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((unSlipViewPager) container).removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View convertView = getLayoutInflater().inflate(
					R.layout.item_startviewpager, null);

			TextView line1 = (TextView) convertView
					.findViewById(R.id.viewpager_text1);
			TextView line2 = (TextView) convertView
					.findViewById(R.id.viewpager_text2);
			TextView line3 = (TextView) convertView
					.findViewById(R.id.viewpager_text3);
			switch (position) {
			case 0:
				line1.setText("");
				line2.setText("与您的小区朋友分享生活与私下聊天");
				line3.setText("");
				break;
			case 1:
				line1.setText("私人消息");
				line2.setText("利用免费的一对一进行私下交流");
				line3.setText("");
				break;
			case 2:
				line1.setText("分享生活");
				line2.setText("记录和分享生活中的每一时刻");
				line3.setText("照片、想法、话题等等");
				break;
			case 3:
				line1.setText("记录生活");
				line2.setText("即时搜索关于朋友、小区、商家");
				line3.setText("以及其它内容的就贴");
				break;
			case 4:
				line1.setText("一体化");
				line2.setText("选择性地将好吗时刻转发到");
				line3.setText("其它社交网络");
				break;

			default:
				break;
			}
			((unSlipViewPager) container).addView(convertView, 0);
			return convertView;
		}

	}
}
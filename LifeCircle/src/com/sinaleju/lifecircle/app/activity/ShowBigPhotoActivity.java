package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.view.photoview.PhotoView;
import com.iss.view.photoview.PhotoViewAttacher.OnViewTapListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.utils.ADBitmapUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class ShowBigPhotoActivity extends IssActivity {

	protected static final String TAG = "ShowBigPhotoActivity";
	private ViewPager mViewPager;
	private List<String> mPaths = null;
	private int selectPos = 0;
	private ImageView btnBack;
	private ImageView btnDelete;
	private int index = 0;
	private SamplePagerAdapter adapter = null;
	private boolean isUrl = false;

	private ArrayList<Integer> mDelete = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_show_big_photo);

		mPaths = getIntent().getStringArrayListExtra(
				AppConst.INTENT_IMAGE_PATH_ARRAY);
		isUrl = getIntent().getBooleanExtra("is_url_image", false);
		selectPos = getIntent().getIntExtra(
				AppConst.INTENT_IMAGE_CURRENT_POSITION, 0);
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		btnBack = (ImageView) findViewById(R.id.show_big_back);
		btnDelete = (ImageView) findViewById(R.id.show_big_delete);
		mViewPager = (ViewPager) findViewById(R.id.show_big_pageView);
	}

	@Override
	protected void initData() {
		if (mPaths != null) {
			adapter = new SamplePagerAdapter();
			mViewPager.setAdapter(adapter);
			mViewPager.setCurrentItem(selectPos);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putIntegerArrayListExtra(
					AppConst.INTENT_IMAGE_PATH_INDEX_ARRAY, mDelete);
			setResult(200, intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void setListener() {
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mPaths.size() != 0 && index <= mPaths.size() - 1) {
					mDelete.add(index);
					mPaths.remove(index);
					adapter = new SamplePagerAdapter();
					mViewPager.setAdapter(adapter);
					mViewPager.setCurrentItem(0);
					index = 0;
				}
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putIntegerArrayListExtra(
						AppConst.INTENT_IMAGE_PATH_INDEX_ARRAY, mDelete);
				setResult(200, intent);
				finish();
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				index = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPaths.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {

			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setOnViewTapListener(new OnViewTapListener() {

				@Override
				public void onViewTap(View view, float x, float y) {
					btnDelete
							.setVisibility(btnDelete.getVisibility() == View.VISIBLE ? View.INVISIBLE
									: View.VISIBLE);
					btnBack.setVisibility(btnBack.getVisibility() == View.VISIBLE ? View.INVISIBLE
							: View.VISIBLE);
				}
			});

			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			if (isUrl) {
				PublicUtils.loadHeadImage(photoView, mPaths.get(position), 0);
			} else {
				Bitmap bitmap = getImageBitmap(mPaths.get(position));
				BitmapDrawable drawable = new BitmapDrawable(bitmap);
				photoView.setImageDrawable(drawable);
			}

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private Bitmap getImageBitmap(String imgPath) {
		return ADBitmapUtils.createBitmapFromFile(this, imgPath);
	}

	private Toast toast = null;

	public void showToast(String text) {
		if (toast == null) {
			toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
			toast.show();
		} else {
			toast.setView(toast.getView());
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}

package com.sinaleju.lifecircle.app.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.iss.app.IssActivity;
import com.iss.imageloader.core.ImageLoader;
import com.iss.view.photoview.PhotoView;
import com.iss.view.photoview.PhotoViewAttacher.OnViewTapListener;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class PhotoAlbumActivity extends IssActivity {

	protected static final String TAG = "PhotoAlbumActivity";
	private ViewPager mViewPager;
	private String[] mPaths = null;
	private int selectPos = 0;
	private ImageView btnBack;
	private ImageView btnDelete;
	private int index = 0;
	private SamplePagerAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_show_big_photo);

		mPaths = getIntent().getStringArrayExtra("urls");
		selectPos = getIntent().getIntExtra("pos", 0);
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "-------------onCreate--------------");
		btnBack = (ImageView) findViewById(R.id.show_big_back);
		btnBack.setVisibility(View.GONE);
		btnDelete = (ImageView) findViewById(R.id.show_big_delete);
		btnDelete.setVisibility(View.GONE);
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
	protected void setListener() {

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
			return mPaths.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {

			View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_frame, null);
			PhotoView photoView = (PhotoView) view.findViewById(R.id.photo);
			photoView.setOnViewTapListener(new OnViewTapListener() {

				@Override
				public void onViewTap(View view, float x, float y) {
					// btnBack.setVisibility(btnBack.getVisibility() ==
					// View.VISIBLE ? View.INVISIBLE
					// : View.VISIBLE);
					finish();
				}
			});
			photoView.setScaleType(ScaleType.FIT_CENTER);

			container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			ImageLoader.getInstance(PhotoAlbumActivity.this).displayImage(mPaths[position], photoView, SimpleImageLoaderOptions.getOptions(0, false));
			return view;
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

package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.utils.ADBitmapUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public abstract class ChangeImageAdapter extends BaseAdapter {

	private static final String TAG = "ChangeImageAdapter";

	private List<String> mIntegers = new ArrayList<String>();

	private final Context mContext;

	private UserBean mUserBean = AppContext.curUser();

	public abstract void doImageViewClick(int index);

	public ChangeImageAdapter(Context context, List<String> mIntegers) {
		this.mContext = context;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + context);
		this.mIntegers = mIntegers;
	}

	@Override
	public int getCount() {
		return mIntegers == null ? 0 : mIntegers.size();
	}

	@Override
	public String getItem(int position) {
		if ((null != mIntegers && mIntegers.size() > 0)
				&& (position >= 0 && position < mIntegers.size())) {
			return mIntegers.get(position);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ChangeBgItemHolder holder = null;
		if (convertView == null || null == convertView.getTag()) {
			DisplayMetrics dm = new DisplayMetrics();
			((Activity) mContext).getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			int screenWidth = dm.widthPixels / 3;

			holder = new ChangeBgItemHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_change_head_bg, null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.change_item_view);
			holder.checkedIcon = (ImageView) convertView
					.findViewById(R.id.change_item_checked_icon);
			LayoutParams param = holder.imageView.getLayoutParams();
			param.width = screenWidth;
			param.height = screenWidth - 15;
			holder.imageView.setLayoutParams(param);
			holder.imageView.setPadding(2, 2, 2, 2);
			holder.imageView.setScaleType(ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else {
			holder = (ChangeBgItemHolder) convertView.getTag();
		}
		String pathName = getItem(position);
		if (!TextUtils.isEmpty(pathName)) {
			Bitmap bitmap = ADBitmapUtils.createBitmapFromAssets(mContext, pathName);
			if(bitmap != null){
				BitmapDrawable drawable = new BitmapDrawable(bitmap);
				holder.imageView.setImageDrawable(drawable);
			}
		}
		if (mUserBean.getType() == 0) {
			clickMap.put(String.valueOf(position),
					AppContext.mPerIndexBg == position ? true : false);
			if (AppContext.mPerIndexBg == position ? true : false) {
				holder.checkedIcon.setVisibility(View.VISIBLE);
			} else {
				holder.checkedIcon.setVisibility(View.GONE);
			}
		} else if (mUserBean.getType() == 1) {
			clickMap.put(String.valueOf(position),
					AppContext.mBusIndexBg == position ? true : false);
			if (AppContext.mBusIndexBg == position ? true : false) {
				holder.checkedIcon.setVisibility(View.VISIBLE);
			} else {
				holder.checkedIcon.setVisibility(View.GONE);
			}
		} else if (mUserBean.getType() == 2) {
			clickMap.put(String.valueOf(position),
					AppContext.mProIndexBg == position ? true : false);
			if (AppContext.mProIndexBg == position ? true : false) {
				holder.checkedIcon.setVisibility(View.VISIBLE);
			} else {
				holder.checkedIcon.setVisibility(View.GONE);
			}
		}
		handCheckImage(position, convertView, parent);

		return convertView;
	}

	private Map<String, Boolean> clickMap = new HashMap<String, Boolean>();

	private void handCheckImage(final int position, View convertView,
			ViewGroup parent) {
		final int pos = position;
		final ViewGroup curParent = parent;
		final ChangeBgItemHolder holder = (ChangeBgItemHolder) convertView
				.getTag();
		final ImageView imageView = holder.imageView;
		final ImageView icon = holder.checkedIcon;
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clickMap.containsKey(String.valueOf(pos))) {
					if (clickMap.get(String.valueOf(pos))) {
						clickMap.remove(String.valueOf(pos));
						clickMap.put(String.valueOf(pos), false);
						icon.setVisibility(View.GONE);
						doImageViewClick(-1);
					} else {
						clickMap.remove(String.valueOf(pos));
						clickMap.put(String.valueOf(pos), true);
						icon.setVisibility(View.VISIBLE);
						for (int i = 0; i < curParent.getChildCount(); i++) {
							if (i != pos) {
								View childView = curParent.getChildAt(i);
								((ImageView) childView
										.findViewById(R.id.change_item_checked_icon))
										.setVisibility(View.GONE);
								clickMap.remove(String.valueOf(i));
								clickMap.put(String.valueOf(i), false);
							}
						}
						doImageViewClick(position);
					}
				}
			}
		});
	}

	public static class ChangeBgItemHolder {
		public ImageView imageView;

		public ImageView checkedIcon;
	}

}

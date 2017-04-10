package com.sinaleju.lifecircle.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import com.iss.imageloader.core.DisplayImageOptions;
import com.iss.imageloader.core.display.RoundedBitmapDisplayer;

public class SimpleImageLoaderOptions {

	// private static DisplayImageOptions options;
	//
	// private SimpleImageLoaderOptions() {
	//
	// }
	//
	// public static DisplayImageOptions newInstance() {
	// return getOptions();
	// }
	//
	// private static DisplayImageOptions getOptions() {
	// if (options == null) {
	// options = new DisplayImageOptions.Builder()
	// // .showStubImage(R.drawable.ic_stub)
	// // .showImageForEmptyUri(R.drawable.ic_empty)
	// // .showImageOnFail(R.drawable.ic_error)
	// // .cacheInMemory()
	// .cacheOnDisc().decodingOptions(getBitmapOptions())
	// .bitmapConfig(Bitmap.Config.RGB_565)
	// // 图片圆角处理，慎用，会增大内存使用
	// // .displayer(new RoundedBitmapDisplayer(30))
	// .build();
	// }
	// return options;
	// }

	public static DisplayImageOptions getOptions(int resId, boolean memoryCache) {

		DisplayImageOptions.Builder builder = getBuilder(resId, memoryCache);

		return builder.build();
	}

	/**
	 * @param resId
	 * @param memoryCache
	 * @return
	 */
	private static DisplayImageOptions.Builder getBuilder(int resId, boolean memoryCache) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();

		if (resId != 0)
			builder.showStubImage(resId).showImageForEmptyUri(resId).showImageOnFail(resId);

		if (memoryCache)
			builder.cacheInMemory();
		
		builder.cacheOnDisc();

		builder.decodingOptions(getBitmapOptions()).bitmapConfig(Bitmap.Config.RGB_565);
		return builder;
	}

	public static DisplayImageOptions getRoundImageOptions(int resId) {
		
		return getRoundImageOptions(resId,8);
	}
	public static DisplayImageOptions getRoundImageOptions(final int resId,int degree) {
		DisplayImageOptions.Builder builder = getBuilder(resId, true);
		builder.displayer(new RoundedBitmapDisplayer(degree));
		return builder.build();
	}

	private static Options getBitmapOptions() {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return opt;
	}

}

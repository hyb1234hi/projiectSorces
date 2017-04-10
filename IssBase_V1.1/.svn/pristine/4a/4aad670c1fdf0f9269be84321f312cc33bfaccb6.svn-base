package com.iss.app;

import com.iss.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.iss.imageloader.cache.memory.impl.WeakMemoryCache;
import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class IssAppContext extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1500000) // 1.5 Mb
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				//.enableLogging() // Not necessary in common
				//.offOutOfMemoryHandling()
				.memoryCache(new WeakMemoryCache())
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}

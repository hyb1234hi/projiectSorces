package com.sinaleju.lifecircle.app;

import java.util.WeakHashMap;

public class AppCache extends WeakHashMap<String,Object>{
	
	private static AppCache sAppCache = null;
	
	public static AppCache getInstance(){
		if(sAppCache == null){
			sAppCache = new AppCache();
		}
		return sAppCache;
	}
	
	private AppCache(){
		//no-args constructor for private
	}
}

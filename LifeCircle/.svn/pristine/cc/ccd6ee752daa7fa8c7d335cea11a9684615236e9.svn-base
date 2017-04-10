package com.sinaleju.lifecircle.app.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.iss.imageloader.core.assist.FailReason;
import com.iss.imageloader.core.assist.ImageLoadingListener;

public class FadeInImageLoadingListener implements ImageLoadingListener{
	
	private boolean anim;
	
	private ImageView img;
	
	public FadeInImageLoadingListener(ImageView img){
		this.img = img;
	}
	@Override
	public void onLoadingStarted(String imageUri, View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if(anim)
			ADAnimationUtils.fadeIn(view);
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		
	}

	@Override
	public void onBitmapCreate(boolean fromMemeory, Bitmap map) {
		this.anim = fromMemeory;
	}

}

package com.sinaleju.lifecircle.app.utils;

import android.view.View;

import com.sinaleju.lifecircle.R;

public class LCCaution {
	private View mEmptyView;
	private View mContentView;
	private int mFailResId = R.drawable.bg_caution_reloading;
	private int mErrorResId = R.drawable.bg_caution_error;
	private View.OnClickListener mFailClickListener;
	private View.OnClickListener mErrorClickListener;
	
	public LCCaution(View empty,View content){
		this.mEmptyView=empty;
		this.mContentView=content;
	}
	
	public void setResid(int failDisplayResid,int errorDisplayResid){
		this.mFailResId=failDisplayResid;
		this.mErrorResId = errorDisplayResid;
	}
	
	public void fail(){
		doCautionWithEmptyView(mFailResId,mFailClickListener);
	}
	
	public void error(){
		doCautionWithEmptyView(mErrorResId,mErrorClickListener);
	}
	
	public void recall(){
		action(false);
	}
	
	private void doCautionWithEmptyView(int resid,View.OnClickListener listener){
		mEmptyView.setBackgroundResource(resid);
		mEmptyView.setOnClickListener(listener);
		action(true);
	}
	
	private void action(boolean caution){
		mEmptyView.setVisibility(caution?View.VISIBLE:View.GONE);
		mContentView.setVisibility(caution?View.GONE:View.VISIBLE);
	}
	
	public void setEmptyViewEnable(boolean enabled){
		mEmptyView.setEnabled(enabled);
	}
	
	public void setOnFailClickListener(View.OnClickListener listener){
		this.mFailClickListener = listener;
	}
	
	public void setOnErrorClickListener(View.OnClickListener listener){
		this.mErrorClickListener = listener;
	}
	
}

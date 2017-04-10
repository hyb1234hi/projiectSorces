package com.sinaleju.lifecircle.app.utils;

import com.sinaleju.lifecircle.app.AppConst;

public class LCPageCounter {

	private int mStart = AppConst.NULL_INT;
	private int mSize = 10;
	private int mLeft = -1;
	private int mTotal = 0;

	public int getStart() {
		return mStart;
	}

	public void setNextStart(int mStart) {
		this.mStart = mStart;
	}

	public int getSize() {
		if (mLeft == -1)
			return mSize;
		return mLeft < mSize ? mLeft : mSize;
	}

	public int getLeft() {
		return mLeft;
	}

	public void setLeft(int left) {
		if (left <= 0) {
			this.mLeft = 0;
		} else
			this.mLeft = left;
	}

	public boolean isMax() {
		return mLeft == 0;
	}

	public int getTotal() {
		return mTotal;
	}

	public void setTotal(int mTotal) {
		this.mTotal = mTotal;
	}

	public void reset() {
		//
		// System.out.println("reset : "+this.toString());
		mTotal = 0;
		mLeft = -1;
		// mSize = 10;
		mStart = AppConst.NULL_INT;
	}

}

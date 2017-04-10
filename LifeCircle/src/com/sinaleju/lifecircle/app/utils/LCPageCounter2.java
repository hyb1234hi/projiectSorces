package com.sinaleju.lifecircle.app.utils;

public class LCPageCounter2 {

	private int mTotalPage = -1;
	private int mCurPage;
	private final int size;

	public LCPageCounter2(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public int getNextStartPage() {
		return mCurPage + 1;
	}

	public int getCurPage(){
		return mCurPage;
	}
	
	public boolean isMax() {
		if (mTotalPage == -1) {
			return false;
		}
		return mCurPage >= mTotalPage;
	}

	public void setTotalPage(int t_page) {
		this.mTotalPage = t_page;
	}

	public void setCurPage(int c_page) {
		mCurPage = c_page;
	}

	public void reset() {
		mTotalPage = -1;
		mCurPage = 0;
	}

}

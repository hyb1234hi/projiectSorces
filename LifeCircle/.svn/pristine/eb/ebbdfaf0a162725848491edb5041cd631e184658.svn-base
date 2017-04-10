package com.sinaleju.lifecircle.app.model.impl;

final class PageCounter{

	public static final int DEFAULT_PAGE_SIZE = 5;
	private int page;
	private final int pagesize;
	private int count;
	private int maxCount;

	PageCounter(int pagesize) {
		this.pagesize = pagesize;
		set();
	}

	private void set() {
		setPage(1);
		setCount(0);
		setMaxCount(0);
	}
	
	void reset(){
		set();
	}

	void addPage() {
		setCount(getPage() * getPagesize());
		setPage(getPage() + 1);
	}

	int getPage() {
		return page;
	}

	void setPage(int page) {
		this.page = page;
	}

	int getCount() {
		return count;
	}

	void setCount(int count) {
		this.count = count;
	}

	int getMaxCount() {
		return maxCount;
	}

	void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	int getPagesize() {
		return pagesize;
	}
	
	boolean isMax(){
		return getMaxCount()>0&&getCount()>=getMaxCount();
	}

}

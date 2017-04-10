package com.sinaleju.lifecircle.app.service.task;

public abstract class SimpleTask implements ITask {
	
	protected OnTaskFinishedListener onTaskFinishedListener;
	private long timeLimit = -1;
	private long startTime = 0;
	private String identify;
	public OnTaskFinishedListener getOnTaskFinishedListener() {
		return onTaskFinishedListener;
	}

	public void setOnTaskFinishedListener(
			OnTaskFinishedListener onTaskFinishedListener) {
		this.onTaskFinishedListener = onTaskFinishedListener;
	}

	private long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	private boolean isTimeLimited() {
		return timeLimit != -1;
	}

	public boolean prepareToStart() {
		if (isTimeLimited())
			setStartTime(System.currentTimeMillis());
		return true;
	}

	public boolean timeUp() {
		if (!isTimeLimited())
			return false;
		long timeNow = System.currentTimeMillis();
		return timeNow - getStartTime() > getTimeLimit();
	}

	private long getStartTime() {
		return startTime;
	}

	private void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public String getIdentify() {
		return identify;
	}

	@Override
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	
	
}

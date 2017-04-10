package com.sinaleju.lifecircle.app.service.task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.Handler;

import com.sinaleju.lifecircle.app.service.Service;
import com.sinaleju.lifecircle.app.service.task.ITask.OnTaskFinishedListener;

class TaskQueue {
//	private static final String TAG = "TaskQueue";
	private Context mContext;
	private int size = 5;
	private LinkedList<Service> excuteList = new LinkedList<Service>();
	private LinkedList<Service> lowList = new LinkedList<Service>();
	private LinkedList<Service> highList = new LinkedList<Service>();
	private HashMap<String, Service> identifyMap = new HashMap<String, Service>();

	TaskQueue(int size) {
		setSize(size);
		setContext(mContext);
	}

	private void setContext(Context mContext) {
		this.mContext = mContext;
	}

	void setSize(int size) {
		this.size = size;
	}

//	private String testGetTastIndexString(Service task) {
//		if (highList.contains(task))
//			return "high -- " + highList.indexOf(task);
//		if (lowList.contains(task))
//			return "low -- " + lowList.indexOf(task);
//		return "gone";
//	}

	boolean excuteTask(final Service task, boolean high) {

		if (identifyMap.get(task.getIdentify()) != null) {
			uptoHigh(task);
			// 检查添加
			loop();
			return false;
		}

		// 添加
		put(task.getIdentify(), task, high);
		task.setOnTaskFinishedListener(new OnTaskFinishedListener() {

			@Override
			public void onTaskFinish() {
				// 移出
				remove(task);

				// 检查添加
				loop();
			}
		});

		// 检查添加
		loop();
		return true;
	}

	private void loop() {
		// Log.e(TAG, "size " + excuteList.size());

		int length = size() - excuteList.size();
		for (int i = 0; i < length; i++) {
			Service task = null;
			task = fetch(highList);
			if (task == null)
				task = fetch(lowList);
			if (task == null)
				break;
			addExcuteList(task);
		}
	}

	private void clean() {

		for (int i = 0; i < excuteList.size(); i++) {
			Service task = excuteList.get(0);
			if (task.timeUp()) {
				remove(task);
				i--;
			}
		}
	}

	private Service fetch(List<Service> taskList) {
		if (taskList.size() == 0)
			return null;
		Service task = taskList.get(0);
		taskList.remove(task);
		return task;
	}

	private int size() {
		return size;
	}

	/**
	 * 第一次成功加入队列
	 * 
	 * @param identify
	 * @param task
	 * @param high
	 */
	private void put(String identify, Service task, boolean high) {
		identifyMap.put(identify, task);

		if (high)
			insertHighList(task);
		else
			addLowList(task);
	}

	/**
	 * 重新设定，改变优先级为高
	 * 
	 * @param task
	 */
	private void uptoHigh(Service task) {
		removeFromLowList(task);
		insertHighList(task);
	}

	/**
	 * 移出TaskQueue
	 * 
	 * @param task
	 */
	private void remove(Service task) {
		removeFromMap(task.getIdentify());
		removeFromExcuteList(task);
	}

	private void addLowList(Service task) {
		lowList.add(task);
	}

	private void insertHighList(Service task) {
		highList.add(0, task);
	}

	private void removeFromLowList(Service task) {
		lowList.remove(task);
	}

	private void removeFromMap(String identify) {
		identifyMap.remove(identify);
	}

	private void removeFromExcuteList(Service task) {
		excuteList.remove(task);
	}

	private void addExcuteList(Service task) {
		task.setTimeLimit(10000);
		excuteList.add(task);
		task.prepareToStart();
		task.asyncExecute(null);
	}

	void stop() {
		excuteList.clear();
		highList.clear();
		lowList.clear();
		identifyMap.clear();
		mHandler.removeCallbacksAndMessages(null);
	}

	private static Handler mHandler = new Handler();

	void recycle() {
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// Log.e(TAG, "handler handling " + excuteList.size());
				clean();
				loop();
				recycle();
			}
		}, 2000);
	}
}

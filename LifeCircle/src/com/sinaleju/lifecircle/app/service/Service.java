package com.sinaleju.lifecircle.app.service;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.AsyncTask;

import com.sinaleju.lifecircle.app.service.task.SimpleTask;
import com.sinaleju.lifecircle.app.utils.LogUtils;

/**
 * 
 * @author sunny.dai
 * 
 */
public abstract class Service extends SimpleTask {

	public static final int TASK_RESULT_SUCCESS = 1;
	public static final int TASK_RESULT_FAULT = 2;
	protected static final String TAG = "Service";
	private AsyncTask<Integer, Integer, Integer> task;
	private boolean isCanceled = false;
	private Object executeResult;
	private OnSuccessHandler onSuccessHandler;
	private OnFaultHandler onFaultHandler;

	private Exception executeException;

	public final Object syncExecute(Context context) throws Exception {
		isCanceled = false;

		executeResult = onExecute(context);

		if (getOnTaskFinishedListener() != null)
			getOnTaskFinishedListener().onTaskFinish();

		return executeResult;
	}

	public final void asyncExecute(final Context context) {
		asyncExecute(context, null);
	}

	public final void asyncExecute(final Context context,  Object lock) {
		LogUtils.v(TAG, "asyncExecute  :: " + context);
		isCanceled = false;
		WeakReference<Object> l = null;
		if (lock != null) {
			l = new WeakReference<Object>(lock);
		}
		final WeakReference<Object> locks = l;
		task = new AsyncTask<Integer, Integer, Integer>() {

			@Override
			protected Integer doInBackground(Integer... arg0) {
				try {
					executeResult = onExecute(context);
					return TASK_RESULT_SUCCESS;
				} catch (Exception e) {
					executeException = e;
					//Item_UserInfo
					LogUtils.e(TAG, "Error when service execute", e);
				}

				return TASK_RESULT_FAULT;
			}

			protected void onPostExecute(Integer result) {
				if (locks != null && locks.get() == null) {
					LogUtils.e(TAG,
							"===== service lock object has bean released ======");
					return;
				}

				if (result == TASK_RESULT_SUCCESS) {
					if (onSuccessHandler != null) {
						onSuccessHandler.onSuccess(executeResult);
					}
				} else if (result == TASK_RESULT_FAULT) {
					if (onFaultHandler != null) {
						onFaultHandler.onFault(executeException);
					}
				}

				if (getOnTaskFinishedListener() != null)
					getOnTaskFinishedListener().onTaskFinish();
			};
		}.execute(0);

	}

	// test
	private long l = 0l;

	public void setLong(long l) {
		this.l = l;
	}

	public long getLong() {
		return l;
	}

	public final void cancel() {
		isCanceled = true;
		if(null != task) task.cancel(true);
	}

	protected abstract Object onExecute(Context context) throws Exception;

	public OnSuccessHandler getOnSuccessHandler() {
		return onSuccessHandler;
	}

	public void setOnSuccessHandler(OnSuccessHandler onSuccessHandler) {
		this.onSuccessHandler = onSuccessHandler;
	}

	public OnFaultHandler getOnFaultHandler() {
		return onFaultHandler;
	}

	public void setOnFaultHandler(OnFaultHandler onFaultHandler) {
		this.onFaultHandler = onFaultHandler;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public interface OnSuccessHandler {
		public void onSuccess(Object result);
	}

	public interface OnFaultHandler {
		public void onFault(Exception ex);
	}

}

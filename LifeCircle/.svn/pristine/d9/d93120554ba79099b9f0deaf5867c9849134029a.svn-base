package com.sinaleju.lifecircle.app.utils;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public class ADHandler<T> extends Handler {
	private WeakReference<T> mReference = null;
	private MessageHandler<T> mMessageHandler = null;

	public ADHandler(T act, MessageHandler<T> mMessageHandler) {
		mReference = new WeakReference<T>(act);
		this.mMessageHandler = mMessageHandler;
	}

	@Override
	public void handleMessage(Message msg) {
		T t = mReference.get();
		if (t == null) {
			removeCallbacks(null);
			return;
		}

		mMessageHandler.handleMessage(t,msg);

	}

	public interface MessageHandler<T> {
		 void handleMessage(T o,Message msg);
	}

}

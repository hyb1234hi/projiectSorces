package com.sinaleju.lifecircle.app.model.impl;

import java.lang.reflect.InvocationTargetException;

import android.content.Context;
import android.view.View;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public abstract class MultiModelBase extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7835820470049960978L;
	private static final String TAG = "MultiModelBase";
	private int mModelType;
	

	public int getModelType() {
		return mModelType;
	}

	public void setModelType(int type) {
		this.mModelType = type;
	}

	public View createConvertView(Context context) {
		Class<? extends AbsItemView> cls = getItemViewClass();
		if (cls != null) {
			try {
				return cls.getConstructor(Context.class).newInstance(context);
			} catch (InstantiationException e) {
				LogUtils.e(TAG, "create a absItemView fail : ", e);
				return null;
			} catch (IllegalAccessException e) {
				LogUtils.e(TAG, "create a absItemView fail : ", e);
				return null;
			} catch (IllegalArgumentException e) {
				LogUtils.e(TAG, "create a absItemView fail : ", e);
				return null;
			} catch (InvocationTargetException e) {
				LogUtils.e(TAG, "create a absItemView fail : "+cls.getName(), e);
				return null;
			} catch (NoSuchMethodException e) {
				LogUtils.e(TAG, "create a absItemView fail : ", e);
				return null;
			}
		}
		LogUtils.i(TAG,
				"can't get new instance of AbsItemView with using  a null class  ");

		return null;
	}

	protected abstract Class<? extends AbsItemView> getItemViewClass();

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	
	
}

package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.model.impl.ItemModelBase;

public abstract class AbsItemView extends FrameLayout implements ItemModelBase {
	protected BaseModel mModel;
	private boolean mFefreshAble = true;

	public AbsItemView(Context context) {
		super(context);
		setBackgroundColor(0xffF4F4F4);
		init();
		setListener();
	}

	public void disableRefresh() {
		this.mFefreshAble = false;
	}

	public void enableRefresh() {
		this.mFefreshAble = true;
	}

	public final void set(int type,BaseModel model) {
		this.mModel = model;

		if (mFefreshAble) {
			toSet(type, model);
		}
	}

	private void setListener() {
		if (enable()) {
			this.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mModel != null)
						onClickThis(mModel);
				}
			});
		}
	}

	public abstract boolean enable();

	protected abstract void onClickThis(BaseModel model);

	protected abstract void toSet(int type,BaseModel model);

}

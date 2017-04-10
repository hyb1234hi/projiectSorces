package com.sinaleju.lifecircle.app.customviews.picker.pickerimpl;

import android.app.Dialog;
import android.content.Context;

public abstract class WheelDialog extends Dialog {

	protected WheelViewSetter wheelSetter;
	private WheelActor actor = null;
	public WheelDialog(Context context) {
		super(context);
	}

	public WheelDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public WheelDialog(Context context, int theme) {
		super(context, theme);
	}

	public WheelViewSetter getWheelSetter() {
		return wheelSetter;
	}

	private void setWheelSetter(WheelViewSetter setter) {
		this.wheelSetter = setter;
	}
	public void init(WheelViewSetter setter){
		setWheelSetter(setter);
		prepareToShow();
	}
	/**
	 * use me  i can do anything
	 * @author sunny.dai
	 */
	public interface WheelActor{
		public void doActionWhenConfirm(Object... o);
	}

	protected abstract void prepareToShow();

	public WheelActor getWheelActor() {
		return actor;
	}

	public void setWheelActor(WheelActor actor) {
		this.actor = actor;
	}

}

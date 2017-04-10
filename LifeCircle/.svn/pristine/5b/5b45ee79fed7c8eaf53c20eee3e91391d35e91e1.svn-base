package com.sinaleju.lifecircle.app.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class ADAnimationUtils {

	/** -------Rotate Animation-------- */
	public static void rotateUp(View v) {
		rotateUp(v, false);
	}

	public static void rotateDown(View v) {
		rotateDown(v, false);
	}

	public static void rotateUp(View v, boolean upright) {
		clearAnimation(v);
		v.startAnimation(generateNormalRotate(0, 180 * (upright ? 1 : -1)));
	}

	public static void rotateDown(View v, boolean upright) {
		clearAnimation(v);
		v.startAnimation(generateNormalRotate(180 * (upright ? 1 : -1), 0));
	}

	private static RotateAnimation generateNormalRotate(float fromDegrees, float toDegrees) {
		RotateAnimation anim = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(350);
		anim.setFillAfter(true);
		return anim;
	}

	/*** -------Translate Animation-------- */
	public static void dropIn(View v) {
		v.startAnimation(generateNormalTranslate(0, 0, -1, 0, true, v));
	}

	public static void dropOut(View v) {
		v.startAnimation(generateNormalTranslate(0, 0, 0, 1, false, v));
	}

	public static void raiseIn(View v) {
		v.startAnimation(generateNormalTranslate(0, 0, 1, 0, true, v));
	}

	public static void raiseOut(View v) {
		v.startAnimation(generateNormalTranslate(0, 0, 0, -1, false, v));
	}
	
	private static TranslateAnimation generateNormalTranslate(float x, float toX, float y, float toY, final boolean makeVisible, final View v) {
		TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF, toX, Animation.RELATIVE_TO_SELF, y, Animation.RELATIVE_TO_SELF,
				toY);
		t.setDuration(350);
		t.setFillAfter(true);
		t.setAnimationListener(new CommonListener(v, makeVisible));
		return t;
	}

	/*** -------Alpha Animation-------- */

	public static void fadeIn(View v) {
		v.startAnimation(generateNormalAlpha(v, true));
	}
	

	public static void fadeOut(View v) {
		v.startAnimation(generateNormalAlpha(v, false));
	}

	private static AlphaAnimation generateNormalAlpha(View v, boolean makeVisible) {
		AlphaAnimation anim = new AlphaAnimation(0, 1);
		anim.setDuration(1000);
		anim.setFillAfter(true);
		anim.setInterpolator(new DecelerateInterpolator());
		anim.setAnimationListener(new CommonListener(v, makeVisible));
		return anim;
	}

	/*** -------Scale Animation-------- */
	
	public static void zoomOut(View v,ZoomAnchor anchor){
		float anchorX = 0;
		float anchorY = 0;
		switch(anchor){
		case LeftBottom:
			anchorY = 1;
			break;
		case RightBottom:
			anchorX = anchorY =1;
			break;
		case LeftTop:
			break;
		case RightTop:
			anchorX = 1;
			break;
		case Center:
			anchorX = anchorY = 0.5f;
			break;
		}
		v.startAnimation(generateNormalScale(v, true, anchorX, anchorY));
	}
	
	public static void zoomIn(View v,ZoomAnchor anchor){
		float anchorX = 0;
		float anchorY = 0;
		switch(anchor){
		case LeftBottom:
			anchorY = 1;
			break;
		case RightBottom:
			anchorX = anchorY =1;
			break;
		case LeftTop:
			break;
		case RightTop:
			anchorX = 1;
			break;
		case Center:
			anchorX = anchorY = 0.5f;
			break;
		}
		v.startAnimation(generateNormalScale(v, false, anchorX, anchorY));
	}
	
	
	private static ScaleAnimation generateNormalScale(View v, boolean out, float px, float py) {
		ScaleAnimation anim = new ScaleAnimation(out ? 0 : 1, out ? 1 : 0, out ? 0 : 1, out ? 1 : 0, Animation.RELATIVE_TO_SELF, px, Animation.RELATIVE_TO_SELF, py);
		anim.setDuration(200);
		anim.setFillAfter(true);
		anim.setAnimationListener(new CommonListener(v, out));
		return anim;
	}


	public static enum ZoomAnchor {
		LeftBottom, RightBottom, LeftTop, RightTop, Center;
	}

	private static void clearAnimation(View v) {
		if (v.getAnimation() != null)
			v.clearAnimation();
	}

	private static class CommonListener implements AnimationListener {
		private View v = null;
		private boolean makeVisible;

		public CommonListener(View v, boolean makeVisible) {
			this.v = v;
			this.makeVisible = makeVisible;
		}

		@Override
		public void onAnimationStart(Animation arg0) {
			v.setVisibility(makeVisible?View.VISIBLE:View.INVISIBLE);
		}

		@Override
		public void onAnimationRepeat(Animation arg0) {

		}

		@Override
		public void onAnimationEnd(Animation arg0) {
//			if (!makeVisible)
//				v.setVisibility(View.GONE);
			v.clearAnimation();
		}
	};
}

package com.sinaleju.lifecircle.app.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.utils.ADAnimationUtils;

/**
 * 自定义title栏布局
 * 
 * @author kenny
 * 
 */
public class TitleBar extends LinearLayout {

	private RelativeLayout titleLayout;
	private TextView mleftButton;
	private AlwaysMarqueeTextView tvTitleName;
	private View mLyotTitleName;

	private TextView mRightButton1;
	private ImageView mSpinnerImage;

	private RadioGroup radioGroup = null;
	private RadioButton radioButtonLeft = null;
	private RadioButton radioButtonRight = null;

	private static final int SAGMENT_COLOR_NORMAL = 0xffd39653;
	private static final int SAGMENT_COLOR_CHECKED = 0xffFFFFFF;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TitleBar(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		titleLayout = (RelativeLayout) View.inflate(context, R.layout.view_title_layout, null);
		mleftButton = (TextView) titleLayout.findViewById(R.id.imavBack);
		tvTitleName = (AlwaysMarqueeTextView) titleLayout.findViewById(R.id.tvTitleName);
		mLyotTitleName = titleLayout.findViewById(R.id.lyotTitleName);
		mSpinnerImage = (ImageView) titleLayout.findViewById(R.id.imgSpinnerDownArrow);
		mRightButton1 = (TextView) titleLayout.findViewById(R.id.tvRightFunc1);

		radioGroup = (RadioGroup) titleLayout.findViewById(R.id.topBarRadioGroup);
		radioButtonRight = (RadioButton) titleLayout.findViewById(R.id.rdioRight);
		radioButtonLeft = (RadioButton) titleLayout.findViewById(R.id.rdioLeft);
		addView(titleLayout, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
	}

	public void setBackgroundResource(int resid) {
		titleLayout.setBackgroundResource(resid);
	}

	/**
	 * 设置返回按钮是否显示 默认为显示
	 * 
	 * @param isShow
	 */
	public void setLeftButtonShow(boolean isShow) {
		if (isShow)
			mleftButton.setVisibility(View.VISIBLE);
		else
			mleftButton.setVisibility(View.GONE);
	}

	public void setRightButton1Show(boolean isShow) {
		if (isShow)
			mRightButton1.setVisibility(View.VISIBLE);
		else
			mRightButton1.setVisibility(View.GONE);
	}

	public void setTitleName(int textId) {
		tvTitleName.setText(textId);
	}

	public String getTitleName() {
		return tvTitleName.getText().toString();
	}

	public void setTitleName(String text) {
		tvTitleName.setText(text);
	}

	public void setTitleListener(View.OnClickListener listener) {
		mLyotTitleName.setOnClickListener(listener);
	}

	public void leftCheck() {
		radioButtonLeft.setChecked(true);
	}

	public boolean isRightChecked() {
		return radioButtonRight.isChecked();
	}

	public boolean isLeftChecked() {
		return radioButtonLeft.isChecked();
	}

	public TextView getRightBtn() {
		return mRightButton1;
	}

	/**
	 * 显示radioGroup
	 * 
	 * @param left
	 * @param right
	 * @param listener
	 */
	public void showRadioGroup(String left, String right, final OnTitleCheckChangeListener listener) {
		hideTitleName();
		showRadioGroup();
		// radioButtonLeft.setChecked(true);
		radioButtonLeft.setText(left);
		radioButtonRight.setText(right);
		setRadioButtonTextCheckColor(true);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (null == listener)
					return;
				if (checkedId == radioButtonLeft.getId()) {
					setRadioButtonTextCheckColor(true);
					listener.onLeftChecked();
				} else if (checkedId == radioButtonRight.getId()) {
					setRadioButtonTextCheckColor(false);
					listener.onRightChecked();
				}
			}
		});

	}

	public void showBackButton() {
		initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		setLeftButtonShow(true);
	}

	private void setRadioButtonTextCheckColor(boolean left) {
		radioButtonLeft.setTextColor(left ? SAGMENT_COLOR_CHECKED : SAGMENT_COLOR_NORMAL);
		radioButtonRight.setTextColor(left ? SAGMENT_COLOR_NORMAL : SAGMENT_COLOR_CHECKED);
	}

	public interface OnTitleCheckChangeListener {

		void onLeftChecked();

		void onRightChecked();
	}

	private void showRadioGroup() {
		radioGroup.setVisibility(View.VISIBLE);
	}

	private void hideTitleName() {
		tvTitleName.setVisibility(View.GONE);
	}

	public void setTitleNameSize(float size) {
		// tvTitleName.setTextSize((int)(size/getResources().getDisplayMetrics().scaledDensity+0.5f));
		tvTitleName.setTextSize(size);
	}

	/**
	 * 设置最右边的按钮的文字或者图片资源<br>
	 * 如不想设置，需设置为0
	 * 
	 * @param textId
	 * @param resId
	 */
	public void initRightButtonTextOrResId(int textId, int resid) {
		mRightButton1.setText(textId == 0 ? "" : getResources().getString(textId));
		mRightButton1.setBackgroundResource(resid == 0 ? R.drawable.topbar_func_button_bg_normal
				: resid);
	}

	/**
	 * 设置最右边的按钮的文字或者图片资源<br>
	 * 如不想设置，文字设置为""或者null<br>
	 * 图片资源需设置为0
	 * 
	 * @param text
	 * @param resId
	 */
	public void initRightButtonTextOrResId(String text, int resid) {
		mRightButton1.setText(text == null ? "" : text);
		mRightButton1.setBackgroundResource(resid == 0 ? R.drawable.topbar_func_button_bg_normal
				: resid);
	}

	public void initLeftButtonTextOrResId(int textId, int resid) {
		mleftButton.setText(textId == 0 ? "" : getResources().getString(textId));
		mleftButton.setBackgroundResource(resid == 0 ? R.drawable.topbar_func_button_bg_normal
				: resid);
	}

	public void initLeftButtonTextOrResId(String text, int resid) {
		mleftButton.setText(text == null ? "" : text);
		mleftButton.setBackgroundResource(resid == 0 ? R.drawable.topbar_func_button_bg_normal
				: resid);
	}

	public void setLeftButtonListener(OnClickListener l) {
		mleftButton.setOnClickListener(l);
	}

	public void setRightButton1Listener(OnClickListener l) {
		mRightButton1.setOnClickListener(l);
	}

	public void showSpinnerImage() {
		mSpinnerImage.setVisibility(View.VISIBLE);
	}

	public boolean isShowSpinner() {
		return mSpinnerImage.getVisibility() == View.VISIBLE;
	}

	public void spinnerOn() {
		ADAnimationUtils.rotateDown(mSpinnerImage);
	}

	public void spinnerOff() {
		ADAnimationUtils.rotateUp(mSpinnerImage);
	}

	public void hideSpinnerImage() {
		mSpinnerImage.setVisibility(View.GONE);
	}

	public View getLeftButtonView() {
		return mleftButton;
	}

}

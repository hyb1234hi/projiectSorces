package com.sinaleju.lifecircle.app.customviews;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class CustomSearchBarView extends FrameLayout{
	/*
	 * 
	 */
	public static enum SearchMode {
		MSG, PEOPLE;
	}

	private SearchMode mSearchMode = SearchMode.MSG;

	private ViewGroup mSearchView;

	private Button mSearchButton;
	private Button mEraseButton;
	private ImageView mArrowView;
	private EditText mEditText;

	
	public EditText getmEditText() {
		return mEditText;
	}

	// label
	private View mLabel;
//	private ImageView mImgLabel;
	private TextView mTxtLabel;
	private int type = -1;


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public CustomSearchBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomSearchBarView(Context context) {
		super(context);
		init();
	}

	private void init() {
		initViews();
		initCallbacks();
	}

	/**
	 * 
	 */
	private void initViews() {
		LayoutInflater.from(getContext()).inflate(R.layout.include_search_for_key, this, true);
		showPopu();
		// view groups
		mSearchView = (ViewGroup) findViewById(R.id.searchView);

		// views
		mSearchButton = (Button) findViewById(R.id.btnSearch);
		mEraseButton = (Button) findViewById(R.id.btnEraseAllText);
		mArrowView = (ImageView) findViewById(R.id.iconSerachArrow);
		mEditText = (EditText) findViewById(R.id.editSearch);

		// label
		mLabel = findViewById(R.id.searchLabel);
//		mImgLabel = (ImageView) findViewById(R.id.imgLabel);
		mTxtLabel = (TextView) findViewById(R.id.txtLabel);

		// set
		mRadioMessage.setChecked(true);

	}

	private void initCallbacks() {
		mLabel.setOnClickListener(mSelectorToggleListener);
		mArrowView.setOnClickListener(mSelectorToggleListener);
		mRadioGroup.setOnCheckedChangeListener(monSelecteLitener);
		mEraseButton.setOnClickListener(mEraseListener);
	}

//	private void toggleSelector() {
//		mRadioGroup.setVisibility(mRadioGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//	}

	private void setLabel(String label) {
		mTxtLabel.setText(label);
//		if (mImgLabel.getVisibility() == View.VISIBLE) {
//			mImgLabel.setVisibility(View.GONE);
//		}
//		if (mTxtLabel.getVisibility() == View.GONE) {
			mTxtLabel.setVisibility(View.VISIBLE);
//		}
	}

	private void setLabelForMessage() {
		setLabel("消息");
	}

	private void setLabelForPeople() {
		setLabel("用户");
	}

	public void setSearchOnClickListener(View.OnClickListener l) {
		LogUtils.i(VIEW_LOG_TAG, "setSearchOnClickListener");
		mSearchButton.setOnClickListener(l);
	}

	private View.OnClickListener mEraseListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			mEditText.setText("");
		}
	};
	private View.OnClickListener mSelectorToggleListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
//			toggleSelector();
			setShowLocation();
		}
	};

	private OnCheckedChangeListener monSelecteLitener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == mRadioMessage.getId())
				setSearchModeInternal(SearchMode.MSG);
			else if (checkedId == mRadioPeople.getId())
				setSearchModeInternal(SearchMode.PEOPLE);
			popupWindow.dismiss();
//			toggleSelector();
//			popupWindow.showAsDropDown(mLabel);
		}
	};

	public void setShowLocation(){
		if(getType() == 1){//搜索页的时候popu位置
			popupWindow.showAtLocation(mLabel, Gravity.LEFT
					| Gravity.TOP, 0, mLabel.getHeight());
		}else {//主页搜索的时候popu位置
			popupWindow.showAtLocation(mLabel, Gravity.LEFT
					| Gravity.TOP, 0, 0);
		}
	}
	public View getSearchBarView() {
		return mSearchView;
	}

	public void requestFocusForEdit() {
		mEditText.setFocusable(true);
		mEditText.setFocusableInTouchMode(true);
		mEditText.requestFocus();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				ADSoftInputUtils.show(getContext());
			}
		}, 300);
	}

	public SearchMode getSearchMode() {
		return mSearchMode;
	}

	private void setSearchModeInternal(SearchMode m) {

		this.mSearchMode = m;

		if (m == SearchMode.MSG)
			setLabelForMessage();
		else
			setLabelForPeople();
	}

	public void setSearchMode(SearchMode m) {

		if (m == SearchMode.MSG) {
			mRadioMessage.setChecked(true);
			mRadioPeople.setChecked(false);
		} else {
			mRadioMessage.setChecked(false);
			mRadioPeople.setChecked(true);
		}
	}

	public String getKeyword() {
		return mEditText.getText().toString();
	}
	private RelativeLayout layout;
	private PopupWindow popupWindow;
	private RadioGroup mRadioGroup;
	private RadioButton mRadioMessage;
	private RadioButton mRadioPeople;
	public void showPopu(){
		layout = (RelativeLayout) LayoutInflater.from(this.getContext()).inflate(
				R.layout.view_pop_search_for_key, null);
		// radioGroup
		mRadioGroup = (RadioGroup) layout.findViewById(R.id.radioGroup);
		mRadioMessage = (RadioButton) layout.findViewById(R.id.radioMessage);
		mRadioPeople = (RadioButton) layout.findViewById(R.id.radioPeople);
		popupWindow = new PopupWindow(this);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow
				.setWidth(300);
		popupWindow.setHeight(300);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		// showAsDropDown会把里面的view作为参照物，所以要那满屏幕parent
		// popupWindow.showAsDropDown(findViewById(R.id.tv_title), x, 10);
//		popupWindow.showAtLocation(findViewById(R.id.main), Gravity.LEFT
//				| Gravity.TOP, x, y);//需要指定Gravity，默认情况是center.
	}
}

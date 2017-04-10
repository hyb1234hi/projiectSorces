package com.sinaleju.lifecircle.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.iss.view.common.ToastAlone;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.SearchForKeyActivity;
import com.sinaleju.lifecircle.app.customviews.CustomSearchBarView;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.umeng.analytics.MobclickAgent;

public class SearchTopBarDialog extends Dialog {
	private ViewGroup mSearchView = null;
	private CustomSearchBarView mSearchBar = null;;
	private static Activity mActivity;
	
	public SearchTopBarDialog(Context context, int theme) {
		super(context, R.style.style_dialog_search_top_bar);
	}

	public SearchTopBarDialog(Context context) {
		this(context, 0);
	}

	public static void pop(Context context) {
		mActivity = ((Activity) context);
		new SearchTopBarDialog(context).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSearchBar = new CustomSearchBarView(getContext());
		mSearchBar.setType(2);
		setContentView(mSearchBar);

		initViews();

		setListeners();

		initData();

	}

	private void initData() {
		mSearchBar.requestFocusForEdit();
	}

	private void setListeners() {
		mSearchBar.setSearchOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!AppContext.isValid(mActivity)){
					return;
				}else{
					if(mSearchBar.getKeyword().equals("")){
						ToastAlone.showToast(getContext(), "请输入搜索内容！", Toast.LENGTH_SHORT);
					}else {
						dismiss();
						gotoSearchActivity();
					}
				}
			}

			/***/
			private void gotoSearchActivity() {
				MobclickAgent.onEvent(getContext(), PublicUtils.MOBCLICKAGENT_SEARCH);
				Intent i = new Intent(getContext(), SearchForKeyActivity.class);
				i.putExtra("searchMode", mSearchBar.getSearchMode());
				i.putExtra("text", mSearchBar.getKeyword());
				getContext().startActivity(i);
			}
		});
	}

	private void initViews() {
		mSearchView = (ViewGroup) findViewById(R.id.searchView);
	}

	/**
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int px = (int) event.getRawX();
			int py = (int) event.getRawY();
			Rect r = getViewDrawRect(mSearchView);
			if (!r.contains(px, py)) {
				LogUtils.e("search", "onTouchEvent");
				InputMethodManager inputMethodManager = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(mSearchBar.getmEditText().getWindowToken(), 0);

				
//				PublicUtils.hideSoftInputMethod(getmActivity());
				dismiss();
				return true;
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * @return
	 */
	private Rect getViewDrawRect(View view) {
		int[] l = new int[2];
		view.getLocationOnScreen(l);
		int x = l[0];
		int y = l[1];
		int w = view.getMeasuredWidth();
		int h = view.getMeasuredHeight();
		Rect r = new Rect(x, y, x + w, y + h);
		return r;
	}

}

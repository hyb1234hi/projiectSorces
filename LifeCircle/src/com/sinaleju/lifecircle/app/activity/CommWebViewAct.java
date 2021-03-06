package com.sinaleju.lifecircle.app.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class CommWebViewAct extends BaseActivity {

	private WebView webView;
	private String url;
	private Button goBackBtn, goForwardBtn, reLoadBtn;
	private boolean isLoading = false;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_ticket_webview;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		webView = (WebView) findViewById(R.id.ticket_web);
		goBackBtn = (Button) findViewById(R.id.web_goback);
		goForwardBtn = (Button) findViewById(R.id.web_goforward);
		reLoadBtn = (Button) findViewById(R.id.web_reload);

		initUserData();
	}

	private void initUserData() {
		url = getIntent().getStringExtra("ticket_url");
		showProgressDialog("正在加载...", true);
		setUrlData();
		setTitleBarData();
	}

	private void setTitleBarData() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		mTitleBar.setTitleName("加载中...");
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}

	private void setUrlData() {
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress >= 100) {
					hideProgressDialog();
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				mTitleBar.setTitleName(title);
				super.onReceivedTitle(view, title);
			}

		});
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				mTitleBar.setTitleName("加载中...");
				isLoading = true;
				reLoadBtn.setBackgroundResource(R.drawable.web_stop_load);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (mTitleBar.getTitleName().equals("加载中...")) {
					mTitleBar.setTitleName("未知标题");
				}
				isLoading = false;
				reLoadBtn.setBackgroundResource(R.drawable.web_reload);
				setGoBackBtnStatus();
				setGoForwardBtnStatus();
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return super.shouldOverrideUrlLoading(view, url);
			}

		});

		if (!TextUtils.isEmpty(url)) {
			webView.loadUrl(url);
		}
		webView.requestFocus();
	}

	private void setGoBackBtnStatus() {
		if (webView.canGoBack()) {
			goBackBtn.setSelected(true);
			goBackBtn.setEnabled(true);
		} else {
			goBackBtn.setSelected(false);
			goBackBtn.setEnabled(false);
		}
	}

	private void setGoForwardBtnStatus() {
		if (webView.canGoForward()) {
			goForwardBtn.setSelected(true);
			goForwardBtn.setEnabled(true);
		} else {
			goForwardBtn.setSelected(false);
			goForwardBtn.setEnabled(false);
		}
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicUtils.hideSoftInputMethod(CommWebViewAct.this);
				finish();
			}
		});

		goBackBtn.setOnClickListener(btnClickListener);
		goForwardBtn.setOnClickListener(btnClickListener);
		reLoadBtn.setOnClickListener(btnClickListener);
	}

	private OnClickListener btnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.web_goback:
				if (webView.canGoBack()) {
					webView.goBack();
					setGoBackBtnStatus();
				}
				break;
			case R.id.web_goforward:
				if (webView.canGoForward()) {
					webView.goForward();
					setGoForwardBtnStatus();
				}
				break;
			case R.id.web_reload:
				if (isLoading) {
					webView.stopLoading();
					isLoading = false;
					reLoadBtn.setBackgroundResource(R.drawable.web_reload);
				} else {
					webView.reload();
				}
				break;
			default:
				break;
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

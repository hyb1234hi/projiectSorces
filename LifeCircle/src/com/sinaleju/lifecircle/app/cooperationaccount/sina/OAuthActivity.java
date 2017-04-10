package com.sinaleju.lifecircle.app.cooperationaccount.sina;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.util.Utility;

/**
 * 
 * @author liyan (liyan9@staff.sina.com.cn)
 */
public class OAuthActivity extends BaseActivity {

	public static String URL_OAUTH2_ACCESS_AUTHORIZE = "https://open.weibo.cn/oauth2/authorize";
	public static final String SHARE_PREFERENCE_KEY = "userinfo";

	static int MARGIN = 4;
	static int PADDING = 2;

	public static final String SINA_KEY = "1485010131";
	public static final String SINA_SECRET = "22bad4c6aa16aba1fc37d5dd742ce3a8";
	public static final String SINA_REDIRECT_URL = "http://haoma.leju.com/";

	private LinearLayout mRoot;
	private WebView mWebView;

	public static Oauth2AccessToken accessToken;

	private SharedPreferences sp;
	private Editor edit;

	private final static String TAG = "OAuthActivity";

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				finish();
				break;
			case 1:
				showToast("微博账号登录失败");
				break;
			case 2:
				showToast("数据异常");
				break;
			default:
				break;
			}

		};
	};

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.sina_oauth_act;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mTitleBar.setTitleName(R.string.sina_weibo);

		mRoot = (LinearLayout) findViewById(R.id.sina_oauth_root);
		mWebView = (WebView) findViewById(R.id.sina_oauth_webview);
		setUpWebView();

		showProgressDialog("正在加载中...", true);

		sp = getSharedPreferences(SHARE_PREFERENCE_KEY, MODE_PRIVATE);
		edit = sp.edit();
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initCallbacks() {

	}

	private String getURL() {
		WeiboParameters parameters = new WeiboParameters();
		parameters.add("client_id", SINA_KEY);
		parameters.add("response_type", "code");
		parameters.add("redirect_uri", SINA_REDIRECT_URL);
		parameters.add("display", "mobile");
		return URL_OAUTH2_ACCESS_AUTHORIZE + "?"
				+ Utility.encodeUrl(parameters);
	}

	private void setUpWebView() {
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.requestFocus();
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		mWebView.setWebViewClient(new WeiboWebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress >= 100) {
					hideProgressDialog();
				}
			}
		});
		mWebView.loadUrl(getURL());
	}

	class WeiboWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (url.startsWith(SINA_REDIRECT_URL)) {
				handleRedirectUrl(view, url);
				setResult(200, getIntent());
				// finish();
				return;
			}
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			hideProgressDialog();
			super.onPageFinished(view, url);
		}

		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
		}

	}

	private void handleRedirectUrl(WebView view, String url) {
		Bundle values = Utility.parseUrl(url);
		String error = values.getString("error");
		String error_code = values.getString("error_code");
		if (error == null && error_code == null) {
			String code = values.getString("code");
			// AccessTokenKeeper.keepAccessToken(MainActivity.this,
			// oauth2AccessToken);
			if (code != null) {
				// 开启新线程获取token android 4.0以后访问HTTP必须异步处理
				HttpThread hThread = new HttpThread(code);
				Thread thread = new Thread(hThread);
				thread.start();
			}
		}

		// String token = values.getString("access_token");
		// String expires_in = values.getString("expires_in");
		// String uid = values.getString("uid");
		// // 截取param 参数数据。
		// String sValues = values.toString();
		// int start = sValues.indexOf("[");
		// int end = sValues.lastIndexOf("]");
		// String param = sValues.substring(start + 1, end);
		// LogUtils.i(TAG, "param:  " + param);
		// OAuthActivity.accessToken = new Oauth2AccessToken(token, expires_in);
		// String origin = "sina";
		// // edit.putString("token", token);
		// edit.putString("param", param);
		// edit.putString("uid", uid);
		// // "2"代表新浪。
		// edit.putString("origin", origin);
		// edit.putString("token", token);
		// edit.putString("expirestime", expires_in);
		// edit.commit();
		// LogUtils.e(TAG, "uid:   " + uid + "  token  " + token
		// + "   expirsetiem  " + expires_in);
		//
		// // 启动新线程获取登录用户信息。
		// UsersAPI uAPI = new UsersAPI(OAuthActivity.accessToken);
		// new MyThread(uAPI, Long.valueOf(uid)).start();
		//
		// if (OAuthActivity.accessToken.isSessionValid()) {
		//
		// AccessTokenKeeper.keepAccessToken(OAuthActivity.this,
		// accessToken);
		// setResult(Activity.RESULT_OK);
		// finish();
		// }
		// } else {
		// showToast("绑定失败");
		// }
	}

	class MyThread extends Thread {
		UsersAPI uAPI;
		long luid;

		public MyThread(UsersAPI userAPI, long luid) {
			uAPI = userAPI;
			this.luid = luid;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			uAPI.show(luid, new RequestListener() {

				@Override
				public void onIOException(IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(WeiboException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onComplete(String response) {
					try {
						JSONObject json = new JSONObject(response);
						String screenname = json.getString("screen_name");
						String profileimage = json
								.getString("profile_image_url");
						edit.putString("headimage", profileimage);
						edit.putString("nickname", screenname);
						edit.commit();
						LogUtils.e(TAG, "nickname:  " + screenname
								+ "  headerImagurl  " + profileimage);

						// 清除缓存
						CookieManager cookieManager = CookieManager
								.getInstance();
						cookieManager.removeAllCookie();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			super.run();
		}

	}

	@Override
	public void finish() {
		hideProgressDialog();
		if (mWebView != null) {
			mRoot.removeAllViews();
			mWebView.destroyDrawingCache();
			mWebView.removeAllViews();
			mWebView.destroy();
		}
		super.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(Activity.RESULT_CANCELED);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 通过Code获取token
	 * 
	 * @author Administrator
	 * 
	 */
	class HttpThread implements Runnable {
		String code;

		public HttpThread(String code) {
			this.code = code;
		}

		@Override
		public void run() {
			try {// key secret 在基本信息里 回调页面 在高级信息里
				HttpPost post = new HttpPost(
						URI.create("https://api.weibo.com/oauth2/access_token?client_id="
								+ SINA_KEY
								+ "&client_secret="
								+ SINA_SECRET
								+ "&grant_type=authorization_code&redirect_uri="
								+ SINA_REDIRECT_URL + "&code=" + code));
				HttpClient httpClient = getNewHttpClient();
				HttpResponse response = (HttpResponse) httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					String temp = EntityUtils.toString(response.getEntity());
					LogUtils.i(TAG, "json: " + temp);
					JSONObject o = new JSONObject(temp);
					String access_token = o.getString("access_token");
					String expires_in = o.getString("expires_in");
					// 这里按照 毫秒计算
					long time = Long.parseLong(expires_in) * 1000
							+ System.currentTimeMillis();
					// if (oauth2AccessToken == null) {
					// oauth2AccessToken = new Oauth2AccessToken();
					// }
					// oauth2AccessToken.setExpiresTime(time);
					// oauth2AccessToken.setToken(access_token);
					//
					// AccessTokenKeeper.keepAccessToken(MainActivity.this,
					// oauth2AccessToken);

					String uid = o.getString("uid");
					// 截取param 参数数据。
					// String sValues = values.toString();
					// int start = sValues.indexOf("[");
					// int end = sValues.lastIndexOf("]");
					// String param = sValues.substring(start + 1, end);
					// LogUtils.i(TAG, "param:  " + param);
					OAuthActivity.accessToken = new Oauth2AccessToken(
							access_token, expires_in);
					String origin = "sina";
					// edit.putString("token", token);
					edit.putString("param", temp);
					edit.putString("uid", uid);
					// "2"代表新浪。
					edit.putString("origin", origin);
					edit.putString("token", access_token);
					edit.putString("expirestime", expires_in);
					edit.commit();
					LogUtils.e(TAG, "uid:   " + uid + "  token  "
							+ access_token + "   expirsetiem  " + expires_in);

					// 启动新线程获取登录用户信息。
					UsersAPI uAPI = new UsersAPI(OAuthActivity.accessToken);
					new MyThread(uAPI, Long.valueOf(uid)).start();

					if (OAuthActivity.accessToken.isSessionValid()) {
						AccessTokenKeeper.keepAccessToken(OAuthActivity.this,
								accessToken);
						setResult(Activity.RESULT_OK);
						mHandler.sendEmptyMessage(0);
					} else {
						mHandler.sendEmptyMessage(1);
					}
				} 
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				mHandler.sendEmptyMessage(2);
			} catch (ParseException e) {
				e.printStackTrace();
				mHandler.sendEmptyMessage(2);
			} catch (IOException e) {
				e.printStackTrace();
				mHandler.sendEmptyMessage(2);
			} catch (JSONException e) {
				e.printStackTrace();
				mHandler.sendEmptyMessage(2);
			}
		}

	}

	
	public static HttpClient getNewHttpClient() {
	   try {
	       KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	       trustStore.load(null, null);

	       SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
	       sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	       HttpParams params = new BasicHttpParams();
	       HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	       HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	       SchemeRegistry registry = new SchemeRegistry();
	       registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	       registry.register(new Scheme("https", sf, 443));

	       ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	       return new DefaultHttpClient(ccm, params);
	   } catch (Exception e) {
	       return new DefaultHttpClient();
	   }
	} 
	
	
	
	
	
	

}

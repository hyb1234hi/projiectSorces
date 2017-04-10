package com.sinaleju.lifecircle.app.cooperationaccount.sina;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSConfirmComplete;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSUserInfo;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

/**
 * 新浪微博绑定工具类
 * 
 * @author Administrator
 * 
 */
public class SinaBindingUtils {
	private static boolean  isBinding=false;
	private static boolean bindingSuccess=false;
	protected static final String TAG = "SinaBindingUtils";
	protected static ProgressDialog mProgressDialog = null;
 
	/**
	 * 判断当前用户是否绑定新浪微博
	 * 
	 * @return
	 */
	public static boolean isBinding(final Context context) {		 
		
		int user_id = AppContext.curUser().getUid();
		// 从服务器获取绑定信息
		RSUserInfo rsUserInfo = new RSUserInfo(user_id);
		rsUserInfo.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				LogUtils.i(TAG, "result: "+ result.toString());
				hideProgressDialog();
				UserBean curUser = AppContext.curUser();
				JSONObject dataObj=null;
				
				try {
					dataObj = new JSONObject(result.toString());
					JSONArray platformArray = dataObj
							.optJSONArray("platforminfo");

					int platformLength = platformArray.length();
					if (platformArray != null && platformLength > 0) {
						for (int i = 0; i < platformLength; i++) {
							JSONObject platformObj = platformArray
									.getJSONObject(i);
							int isDel = platformObj.optInt("is_del");
							if (isDel == 0) {
								String param = platformObj.optString("param");
								JSONObject paramObj = new JSONObject(param);
								curUser.setToken(paramObj
										.getString("access_token"));
								curUser.setExpiresTime(paramObj
										.getString("expires_in"));
								curUser.setPlatform_id(platformObj.optString("platform_id"));
								curUser.setPlatform_name(platformObj.optString("platform_name"));
								isBinding=true;
								return;
							} 
						}
					} else {
						//curUser.setIsBindingSina(false);
						isBinding=false;
						return;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					isBinding=false;
					return;
				}				
			}
		});
		rsUserInfo.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				isBinding=false;
				hideProgressDialog();
				return ;
				/*if (ex instanceof ADRemoteException) {
					Toast.makeText(context, ((ADRemoteException) ex).msg(),
							Toast.LENGTH_SHORT).show();
					return;
				} else if (ex instanceof ConnectTimeoutException) {
					Toast.makeText(context, "请求超时，请重试", Toast.LENGTH_SHORT).show();
					return;
				}*/
			}
		});
		if (PublicUtils.isNetworkAvailable(context)) {
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(context);
			}
			showProgressDialog("数据获取中...", true);
			rsUserInfo.asyncExecute(context);
		} else {
			isBinding=false;
			Toast.makeText(context, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
		}
		LogUtils.e(TAG, "isBinding: "+ isBinding);
		return isBinding;

	}

	/**
	 * 绑定新浪微博
	 * 
	 * @return
	 */

	public static boolean bindingSina(Context context) {		

		SharedPreferences sp = context.getSharedPreferences(
				OAuthActivity.SHARE_PREFERENCE_KEY, Context.MODE_PRIVATE);

		String user_id = String.valueOf(AppContext.curUser().getUid());
		final String platform_name = sp.getString("origin", "");
		final String platform_id = sp.getString("uid", "");
		String param = sp.getString("param", "");
		String type = "2";
		final String token = sp.getString("token", "");
		final String expiresTiem=sp.getString("expirestime", "");
		LogUtils.e(TAG, "..............");
		RSConfirmComplete rsBinding = new RSConfirmComplete(platform_id,
				platform_name, param, String.valueOf(user_id), type);
		rsBinding.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				LogUtils.i(TAG, result.toString());
				// 绑定成功
				// 将token信息设置进当前用户信息中。
				//AppContext.curUser().setSina_accesstoken(token);
				UserBean curUser=AppContext.curUser();
				curUser.setToken(token);
				curUser.setExpiresTime(expiresTiem);
				curUser.setPlatform_id(platform_id);
				curUser.setPlatform_name(platform_name);
				//AppContext.curUser().setIsBindingSina(true);
				bindingSuccess=true;
				hideProgressDialog();
				return;
			}
		});
		rsBinding.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				/*if (ex instanceof ADRemoteException) {
					msg = ((ADRemoteException) ex).msg();
				} else if (ex instanceof ConnectTimeoutException) {
					msg = "请求超时，请重试";
				}*/
				bindingSuccess=false;
				hideProgressDialog();
				return;
				
			}

		});
		if (PublicUtils.isNetworkAvailable(context)) {
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(context);
			}
			showProgressDialog("正在绑定微博账号...", true);
			rsBinding.asyncExecute(context);

		} else {
			bindingSuccess=false;
			Toast.makeText(context, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
		}
		LogUtils.e(TAG, "isBinding: "+ bindingSuccess);
		return bindingSuccess;
	}

	protected static void showProgressDialog(String msg, boolean isCancel) {
		mProgressDialog.setMessage(msg != null ? msg : "");
		mProgressDialog.setCancelable(isCancel);
		mProgressDialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// cancelTask();
					}
				});
		mProgressDialog.show();
	}

	protected static void hideProgressDialog() {
		LogUtils.i(TAG, "hideProgressDialog()");
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			LogUtils.i(TAG, "hideProgressDialog().......");
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

}

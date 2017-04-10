package com.sinaleju.lifecircle.app.fragment;

import java.sql.SQLException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.AboutAct;
import com.sinaleju.lifecircle.app.activity.GuideActivity;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.activity.OfficHomeActivity;
import com.sinaleju.lifecircle.app.activity.SuggestionCommitAct;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.cooperationaccount.sina.OAuthActivity;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.LifeCircleService;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSConfirmComplete;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSRemoveRelation;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSUserInfo;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSUserSetup;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.VersionUpdateUtils;

public class SettingFragment extends BaseFragment implements OnClickListener {
	protected static final String TAG = "SettingActivity";
	private Button mBt_sina;
	private Button mBt_exit;
	private SharedPreferences sp;
	private int user_id;
	private boolean isBinding_sina;
	private View mContentView = null;
	private final int GOTOOAUTHACTIVITY = 100;
	private TitleBar mTitleBar;
	private String platform_id;
	private String platform_name;
	private UserBean curUser;
	private RadioButton allLetter;
	private RadioButton attentionLetter;

	private String send_lettet_stats = "0"; // 私信设置:1表示允许所有人，0：只能我关注的人给我发）
	private String comment_stats = "0"; // 是否被评论
	private String mark_up = "1";// 是否被@
	private String fans = "0";// 是否允许加粉丝
	private String praise = "1";// 是否允许被赞

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtils.v(TAG, "-------------initView-------");
		if (mContentView == null) {
			mContentView = inflater.inflate(R.layout.fr_set, container, false);
			initView();
			initData();
			setListener();
		} else {
			((ViewGroup) mContentView.getParent()).removeAllViews();
		}
		return mContentView;
	}

	@Override
	protected void initView() {
		mTitleBar = (TitleBar) mContentView.findViewById(R.id.mTitleBar);
		initTitleLayout();
		mBt_sina = (Button) mContentView.findViewById(R.id.set_share_sina);
		mBt_exit = (Button) mContentView.findViewById(R.id.set_button_exit);
		allLetter = (RadioButton) mContentView.findViewById(R.id.set_allletter);
		attentionLetter = (RadioButton) mContentView
				.findViewById(R.id.set_attentionletter);

	}

	protected void initTitleLayout() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.setTitleName("设置");
		mTitleBar.initLeftButtonTextOrResId(0,
				R.drawable.selector_home_page_top_bar_left_button);
		// mTitleBar.initRightButtonTextOrResId(0,R.drawable.ic_home_page_top_bar_right_button);

		mTitleBar.setLeftButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.e(TAG, "返回主页");
				((AppContext) getActivity().getApplication()).getHomeActivity()
						.toggle();
			}
		});
	}

	@Override
	protected void initData() {
		curUser = AppContext.curUser();
		user_id = curUser.getUid();
		// 从服务器获取绑定信息
		RSUserInfo rsUserInfo = new RSUserInfo(user_id);
		rsUserInfo.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				UserBean curUser = AppContext.curUser();
				JSONObject dataObj;
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
								if(curUser != null){
									curUser.setToken(paramObj
											.optString("access_token"));
									curUser.setExpiresTime(paramObj
											.optString("expires_in"));
									curUser.setPlatform_id(platformObj
											.optString("platform_id"));
									curUser.setPlatform_name(platformObj
											.optString("platform_name"));
									curUser.setIsBindingSina(true);
								}
							} else {
								if(curUser != null){
									curUser.setIsBindingSina(false);
								}
							}
						}
					} else {
						if(curUser != null){
							curUser.setIsBindingSina(false);
						}
					}

					JSONObject userInfoObject = dataObj
							.optJSONObject("userinfo");
					JSONObject userSetupObj = userInfoObject
							.optJSONObject("user_setup");
					send_lettet_stats = userSetupObj.optString("send");
					comment_stats = userSetupObj.optString("comment");
					mark_up = userSetupObj.optString("mark_up");
					fans = userSetupObj.optString("fans");
					praise = userSetupObj.optString("praise");
					if (send_lettet_stats.equals("1")) {
						allLetter.setChecked(true);
						attentionLetter.setChecked(false);
					} else {
						allLetter.setChecked(false);
						attentionLetter.setChecked(true);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception ex){}

			}
		});
		rsUserInfo.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}

			}
		});
		if (PublicUtils.isNetworkAvailable(getActivity())) {
			showProgressDialog("数据获取中...", true);
			rsUserInfo.asyncExecute(getActivity());
		} else {
			showToast("网络异常，请检查网络");
		}
		sp = getActivity().getSharedPreferences("userinfo", 0);
		if (AppContext.curUser().getIsBindingSina()) {
			isBinding_sina = true;
			mBt_sina.setText("已连接");
			mBt_sina.setBackgroundResource(R.drawable.main_button_focusebg);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_allletter:
			// showToast("所有人");
			attentionLetter.setChecked(false);
			if (!send_lettet_stats.equals("1")) {
				send_lettet_stats = "1";
				setupLetter(send_lettet_stats);
			}
			LogUtils.i(TAG, "send_lettet_stats " + send_lettet_stats);
			break;
		case R.id.set_attentionletter:
			// showToast("我关注的人");
			allLetter.setChecked(false);
			if (!send_lettet_stats.equals("0")) {
				send_lettet_stats = "0";
				setupLetter(send_lettet_stats);
			}
			LogUtils.i(TAG, "send_lettet_stats " + send_lettet_stats);
			break;

		case R.id.set_share_sina:
			// 绑定新浪微博
			if (isBinding_sina) {
				// 当前已绑定过，单击解除绑定状态
				platform_id = AppContext.curUser().getPlatform_id();
				platform_name = AppContext.curUser().getPlatform_name();
				LogUtils.i(TAG, "request.........   " + "paltfrom_id: "
						+ platform_id + "  platform_name:  " + platform_name);
				RSRemoveRelation rsRemoveRelation = new RSRemoveRelation(
						platform_id, platform_name);
				rsRemoveRelation.setOnSuccessHandler(new OnSuccessHandler() {

					@Override
					public void onSuccess(Object result) {
						LogUtils.i(TAG, result.toString());
						hideProgressDialog();

						AppContext.curUser().setIsBindingSina(false);
						isBinding_sina = false;
						mBt_sina.setText("连接");
						mBt_sina.setBackgroundResource(R.drawable.main_button_unfocusbg);

						// AppContext.curUser().setSina_accesstoken(token);
					}
				});
				rsRemoveRelation.setOnFaultHandler(new OnFaultHandler() {

					@Override
					public void onFault(Exception ex) {
						hideProgressDialog();
						if (ex instanceof ADRemoteException) {
							showToast(((ADRemoteException) ex).msg());
						} else if (ex instanceof ConnectTimeoutException) {
							showToast("网络异常，请检查网络");
						}
						LogUtils.e(TAG, ex.toString());
					}
				});
				if (PublicUtils.isNetworkAvailable(getActivity())) {
					showProgressDialog("正在断开连接...", true);
					rsRemoveRelation.asyncExecute(getActivity(), this);
				} else {
					showToast("网络异常，请检查网络");
				}

			} else {
				// 如果没有绑定过，则调用第三方登录页面。
				Intent OAthIntent = new Intent(getActivity(),
						OAuthActivity.class);
				startActivityForResult(OAthIntent, GOTOOAUTHACTIVITY);
			}
			break;
		case R.id.set_button_exit:
			// showProgressDialog("退出", true);
			try {
				getActivity().stopService(
						new Intent(getActivity(), LifeCircleService.class));
				AppContext.logOff((HomeActivity) getActivity());
				// getActivity().finish();
			} catch (SQLException e) {
				LogUtils.e(TAG, e.toString());
				showToast("退出失败");
			}
			break;
		case R.id.set_about:// 关于
			Intent aboutIntent = new Intent(getActivity(), AboutAct.class);
			startActivity(aboutIntent);
			break;
		case R.id.set_suggestion:// 意见反馈
			Intent suggestionIntent = new Intent(getActivity(),
					SuggestionCommitAct.class);
			startActivity(suggestionIntent);
			break;
		case R.id.set_officialhome: // 官方主页
			// 进入userId=0的个人的主页
			// AppContext.gotoIndexActivity(getActivity(), 0, 1);
			Intent intent = new Intent(getActivity(), OfficHomeActivity.class);
			startActivity(intent);

			/*
			 * intent.putExtra("is_personal_index", true);
			 * intent.putExtra("is_property", false);
			 * intent.putExtra("mVisitor_Id", visitorId);
			 */

			// Uri officialHomeUri = Uri.parse("http://www.sina.cn");
			// Intent browseInt = new Intent(Intent.ACTION_VIEW,
			// officialHomeUri);
			// startActivity(browseInt);
			break;
		case R.id.set_newversion:// 新版本检测
			// checkVersion();
			new VersionUpdateUtils(getActivity(), true).checkVersion();

			break;
		case R.id.set_guide:// 功能引导页
			Intent guideIntent = new Intent(getActivity(), GuideActivity.class);
			startActivity(guideIntent);
			break;
		default:
			break;
		}

	}

	// private void checkVersion() {
	// String curVersion = PublicUtils.getAppVersion(getActivity());
	// RSCheckVersion RsCheckVersion = new RSCheckVersion(curVersion);
	// RsCheckVersion.setOnSuccessHandler(new OnSuccessHandler() {
	//
	// @Override
	// public void onSuccess(Object result) {
	// hideProgressDialog();
	//
	// try {
	// JSONObject resultObj = new JSONObject(result.toString());
	// if (resultObj != null) {
	// // TODO 获取新版本信息。
	// String name = resultObj.optString("name"); // 应用名称
	// String version_number = resultObj
	// .optString("version_number");// 最新版本号
	// String hre = resultObj.optString("href_android"); // 最新应用地址
	// String version_content = resultObj.optString("version_content"); //
	// 最新版本更新内容
	// showUpdateVersionDialog(version_number, version_content, hre);
	// }
	//
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// LogUtils.i(TAG, result.toString());
	//
	// }
	//
	// });
	// RsCheckVersion.setOnFaultHandler(new OnFaultHandler() {
	//
	// @Override
	// public void onFault(Exception ex) {
	// hideProgressDialog();
	// // showUpdateVersionDialog("2.0", "更新内容", hre);
	// if (ex instanceof ADRemoteException) {
	// showToast(((ADRemoteException) ex).msg());
	// } else if (ex instanceof ConnectTimeoutException) {
	// showToast("请求超时，请重试");
	// }
	// }
	// });
	// if (PublicUtils.isNetworkAvailable(getActivity())) {
	// showProgressDialog("新版本检测中...", true);
	// RsCheckVersion.asyncExecute(getActivity());
	// } else {
	// showToast("网络连接异常，请重试");
	// }

	// }

	/**
	 * 版本更新对话框
	 * 
	 * @param version_number
	 * @param version_content
	 * @param hre
	 */
	// private void showUpdateVersionDialog(String version_number,
	// String version_content, String hre) {
	// new AlertDialog.Builder(getActivity())
	// .setTitle("发现新版本：  " + version_number )
	// .setMessage(version_content)
	// .setPositiveButton("马上更新",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // 确定，下载最新应用程序
	//
	// }
	// })
	// .setNegativeButton("稍候更新",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	//
	// }
	// }).show();

	// }

	/*
	 * 隐私设置
	 */
	private void setupLetter(String stats) {
		// 发送私信设置
		RSUserSetup userSetup = new RSUserSetup(user_id + "", stats);
		userSetup.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				showToast("设置成功");
				// 改变私信设置单选按钮的状态
				/*
				 * if(send_lettet_stats.equals("1")){
				 * attentionLetter.setChecked(false); }else{
				 * allLetter.setChecked(false); }
				 */

			}
		});
		userSetup.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if (ex instanceof ADRemoteException) {
					showToast(((ADRemoteException) ex).msg());
				} else if (ex instanceof ConnectTimeoutException) {
					showToast("请求超时，请重试");
				}
				if (send_lettet_stats.equals("1")) {
					allLetter.setChecked(false);
					send_lettet_stats = "0";
					attentionLetter.setChecked(true);
				} else {
					attentionLetter.setChecked(false);
					send_lettet_stats = "1";
					allLetter.setChecked(true);
				}
			}
		});
		if (PublicUtils.isNetworkAvailable(getActivity())) {
			showProgressDialog("设置中...", true);
			userSetup.asyncExecute(getActivity());
		} else {
			if (send_lettet_stats.equals("1")) {
				allLetter.setChecked(false);
				send_lettet_stats = "0";
				attentionLetter.setChecked(true);
			} else {
				attentionLetter.setChecked(false);
				send_lettet_stats = "1";
				allLetter.setChecked(true);
			}
			showToast("网络错误，请重试");
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogUtils.e(TAG, "resultCode  " + resultCode);
		if (resultCode == Activity.RESULT_OK) {
			platform_name = sp.getString("origin", "");
			platform_id = sp.getString("uid", "");
			LogUtils.i(TAG, "onActivityResult.........   " + "paltfrom_id: "
					+ platform_id + "  platform_name:  " + platform_name);
			String param = sp.getString("param", "");

			RSConfirmComplete rsBinding = new RSConfirmComplete(platform_id,
					platform_name, param, String.valueOf(user_id), "2");
			rsBinding.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					LogUtils.i(TAG, result.toString());
					hideProgressDialog();
					String obj = result.toString();
					JSONObject jsonObjet = null;
					try {
						jsonObjet = new JSONObject(obj);
						String sResult = jsonObjet.optString("result");
						if (sResult.equals("1")) {
							// 当前微博账号已绑定过其它账号：即绑定失败。
							showToast(jsonObjet.optString("message"));
							Object dataObj = jsonObjet.opt("data");
							if (dataObj instanceof String) {
								// 可以绑定
								curUser.setExpiresTime(sp.getString(
										"expirestime", ""));
								curUser.setPlatform_id(platform_id);
								curUser.setPlatform_name(platform_name);
								curUser.setIsBindingSina(true);
								mBt_sina.setText("已连接");
								mBt_sina.setBackgroundResource(R.drawable.main_button_focusebg);
								isBinding_sina = true;
							}

						} else {
							showToast(jsonObjet.optString("message"));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// AppContext.curUser().setSina_accesstoken(sp.getString("token",
					// ""));

				}
			});

			rsBinding.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					if (ex instanceof ADRemoteException) {
						Toast.makeText(getActivity(),
								((ADRemoteException) ex).msg(),
								Toast.LENGTH_SHORT).show();
					} else if (ex instanceof ConnectTimeoutException) {
						Toast.makeText(getActivity(), "请求超时，请重试",
								Toast.LENGTH_SHORT).show();
					}

					LogUtils.e(TAG, ex.toString());
				}
			});

			if (PublicUtils.isNetworkAvailable(getActivity())) {
				showProgressDialog("连接中...", true);
				rsBinding.asyncExecute(getActivity(), this);

			} else {
				showToast("网络异常，请检查网络");
			}
		}
	}

	@Override
	protected void setListener() {
		// mTitleBar.setLeftButtonListener(this);
		mBt_exit.setOnClickListener(this);
		mBt_sina.setOnClickListener(this);
		allLetter.setOnClickListener(this);
		attentionLetter.setOnClickListener(this);
		mContentView.findViewById(R.id.set_about).setOnClickListener(this);
		mContentView.findViewById(R.id.set_suggestion).setOnClickListener(this);
		mContentView.findViewById(R.id.set_officialhome).setOnClickListener(
				this);
		mContentView.findViewById(R.id.set_newversion).setOnClickListener(this);
		mContentView.findViewById(R.id.set_guide).setOnClickListener(this);
	}

}

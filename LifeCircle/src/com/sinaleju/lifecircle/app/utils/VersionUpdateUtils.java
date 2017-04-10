package com.sinaleju.lifecircle.app.utils;

import java.io.File;
import java.io.IOException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSCheckVersion;

public class VersionUpdateUtils {
	private final String TAG = "VersionUpdateUtils";
	private ProgressDialog mProgressDialog = null;
	private Toast toast = null;
	private Context context;
	private ProgressBar pb;
	private TextView pt;
	private AlertDialog pbDialog;
	private Downloader downloader;
	private int totalLen;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: // 如果消息类型是0
				totalLen = msg.getData().getInt("totalLen"); // 获取总长度
				pb.setMax(100); // 设置进度条总长度
				break;
			case 1: // 如果消息类型是1
				int totalFinish = msg.getData().getInt("totalFinish"); // 获取完成进度
				pb.setProgress(totalFinish); // 设置进度条进度
				LogUtils.e(TAG, "totalFinish  :: " + totalFinish);
				LogUtils.e(TAG, "totalLen  :: " + totalLen);
				pt.setText(totalFinish + "%"); // 设置百分比
				break;
			case 2: // 如果消息类型是2 完成下载
				dismissDownloding();
				File file = (File) msg.obj;
				installApk(file);
				break;
			case 404:
				// LogUtils.e(TAG, "404");
				showToast("更新失败，请稍后重试");
				pbDialog.dismiss();
				break;
			}
		}

	};
	private TextView pc;
	private boolean isShowProgress;

	public VersionUpdateUtils(Context context, boolean isShowProgress) {
		this.context = context;
		this.isShowProgress = isShowProgress;
	}

	public void checkVersion() {
		String curVersion = PublicUtils.getAppVersion(context);
		RSCheckVersion RsCheckVersion = new RSCheckVersion(curVersion);
		RsCheckVersion.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				if (isShowProgress) {
					hideProgressDialog();
				}

				try {
					JSONObject resultObj = new JSONObject(result.toString());
					if (resultObj != null) {
						// TODO 获取新版本信息。
						String name = resultObj.optString("name"); // 应用名称
						String version_number = resultObj.optString("version_number");// 最新版本号
						String hre = resultObj.optString("href_android"); // 最新应用地址
						String version_content = resultObj.optString("version_content"); // 最新版本更新内容
						showUpdateVersionDialog(version_number, version_content, hre);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtils.i(TAG, result.toString());

			}

		});
		RsCheckVersion.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				if (isShowProgress) {
					hideProgressDialog();
					if (ex instanceof ADRemoteException) {
						showToast(((ADRemoteException) ex).msg());
					} else if (ex instanceof ConnectTimeoutException) {
						showToast("请求超时，请重试");
					}
				}
			}
		});
		if (PublicUtils.isNetworkAvailable(context)) {
			if (isShowProgress) {
				showProgressDialog("新版本检测中...", true);
			}
			RsCheckVersion.asyncExecute(context);
		} else {
			if (isShowProgress) {
				showToast("网络连接异常，请重试");
			}
		}

	}

	/**
	 * 版本更新对话框
	 * 
	 * @param version_number
	 * @param version_content
	 * @param hre
	 */
	private void showUpdateVersionDialog(String version_number, String version_content,
			final String hre) {
		AlertDialog d = new AlertDialog.Builder(context).setTitle("发现新版本：  " + version_number)
				.setMessage(version_content).setCancelable(false)
				.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 确定，下载最新应用程序
						showDownloding(hre);
						pc.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dismissDownloding();
								downloader.pause();
							}
						});
					}
				}).setNegativeButton("稍候更新", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();
		d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		d.show();
	}

	/**
	 * 显示 请求中 对话框
	 * 
	 * @param msg
	 * @param isCancel
	 */
	private void showProgressDialog(String msg, boolean isCancel) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage(msg != null ? msg : "");
		mProgressDialog.setCancelable(isCancel);
		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancelTask();
			}
		});
		mProgressDialog.show();
	}

	private void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	private void cancelTask() {

	}

	private void showToast(String text) {
		if (toast == null) {

			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.show();

		} else {
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setText(text);
			toast.show();
		}
	}

	/**
	 * 显示 下载进度框
	 * 
	 * @param address
	 */
	private void showDownloding(final String address) {
		View pbview = View.inflate(context, R.layout.dialog_pub_down, null);
		pbDialog = new AlertDialog.Builder(context).create();
		pbDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		pbDialog.setView(pbview);
		pb = (ProgressBar) pbview.findViewById(R.id.downloadPB);
		pt = (TextView) pbview.findViewById(R.id.percentTV);
		pc = (TextView) pbview.findViewById(R.id.downcanlce);
		pbDialog.setCancelable(false);
		pbDialog.show();
		new Thread() {
			public void run() {
				try {
					downloader = new Downloader(address, handler);
					downloader.download();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	/**
	 * 取消下载进度框
	 */
	private void dismissDownloding() {
		try {
			if (pbDialog != null && pbDialog.isShowing()) {
				pbDialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 安装APK
	 * 
	 * @param file
	 */
	private void installApk(File file) {
		// 更新登录状态，以便在版本更新后第一次运行时显示新手引导面。
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.remove("loginState");
		// edit.putBoolean(, true);
		edit.commit();

		
		Intent i = new Intent(Intent.ACTION_VIEW);
		if (!file.exists()) {
			return;
		}
		i.setDataAndType(Uri.parse("file://" + file.toString()),
				"application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(i);
	}

}

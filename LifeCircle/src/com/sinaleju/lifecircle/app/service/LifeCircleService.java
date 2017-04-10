package com.sinaleju.lifecircle.app.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSCheckUnreadNotice;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class LifeCircleService extends Service {

	private static final String TAG = "LifeCircleService";

	public static boolean isNewLetter = false;
	public static boolean isNewNotice = false;
	private static int letterLastId = 0;
	private static int noticeLastId = 0;

	private Timer timer = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				getNewMessageState(getApplicationContext());
			}

		}, 0, 1000 * 30);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (timer != null) {
			timer.cancel();
		}
		super.onDestroy();
	}

	private void startNotification(String title, String content, int notificationId) {
		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		sendNotifyToStatusBar(getApplicationContext(), R.drawable.ic_app, content, title, content,
				intent, notificationId);
	}

	private void sendNotifyToStatusBar(Context context, int icon, String tickerText, String title,
			String content, Intent intent, int notificationId) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, title, content, pendingIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notificationManager.notify(notificationId, notification);
	}

	private void getNewMessageState(Context context) {
		if (AppContext.curUser() == null) {
			return;
		}
		RSCheckUnreadNotice rs = new RSCheckUnreadNotice(AppContext.curUser().getUid());
		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				try {
					JSONObject dataObj = new JSONObject(result.toString());
					LogUtils.e(TAG, "result : " + dataObj.toString());
					isNewLetter = dataObj.optBoolean("unread_chat");
					isNewNotice = dataObj.optBoolean("unread_notice");
					int tempLetterId = dataObj.optInt("last_chat_id");
					int tempNoticeId = dataObj.optInt("last_notice_id");
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE);
					LogUtils.i(TAG, "isNewLetter: " + LifeCircleService.isNewLetter
							+ "  isNewNotice: " + LifeCircleService.isNewNotice + " inNewMessage: "
							+ LifeCircleService.getIsNewMessage());
					if (getIsNewLetter()) {
						if (!isTopActivity() && tempLetterId != letterLastId) {
							startNotification("新私信", "您收到了新私信", 100001);
						}
						if (tempLetterId != letterLastId) {
							letterLastId = tempLetterId;
						}
					}
					if (getIsNewNotice()) {
						if (!isTopActivity() && tempNoticeId != noticeLastId) {
							startNotification("新通知", "您收到了新通知", 100002);
						}
						if (tempNoticeId != noticeLastId) {
							noticeLastId = tempNoticeId;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {

			}

		});
		rs.asyncExecute(context);
	}

	public static void manuallyNewMessageState(Context context) {

		RSCheckUnreadNotice rs = new RSCheckUnreadNotice(AppContext.curUser().getUid());

		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				try {
					JSONObject dataObj = new JSONObject(result.toString());
					LogUtils.e(TAG, "result : " + dataObj.toString());
					isNewLetter = dataObj.optBoolean("unread_chat");
					isNewNotice = dataObj.optBoolean("unread_notice");
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_APPCONTEXT_NEWMESSAGE);
					LogUtils.i(TAG, "isNewLetter: " + LifeCircleService.isNewLetter
							+ "  isNewNotice: " + LifeCircleService.isNewNotice + " inNewMessage: "
							+ LifeCircleService.getIsNewMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {

			}

		});
		rs.asyncExecute(context);
	}

	public static boolean getIsNewLetter() {
		return isNewLetter;
	}

	public static boolean getIsNewNotice() {
		return isNewNotice;
	}

	public static boolean getIsNewMessage() {
		return isNewLetter || isNewNotice;
	}

	private boolean isTopActivity() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			String packageName = info.packageName;
			ActivityManager activityManager = (ActivityManager) getApplicationContext()
					.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
			if (tasksInfo.size() > 0) {
				LogUtils.e(TAG,
						"---------------com.sinaleju.lifecircle-----------"
								+ tasksInfo.get(0).topActivity.getPackageName());
				// 应用程序位于堆栈的顶层
				if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e(TAG, "-------------Get RunningTaskInfo Error-----------------");
			return true;
		}
		return false;
	}

}

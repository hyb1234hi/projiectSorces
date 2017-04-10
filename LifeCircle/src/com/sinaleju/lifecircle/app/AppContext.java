package com.sinaleju.lifecircle.app;

import java.io.File;
import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;

import com.iss.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.iss.imageloader.cache.memory.impl.WeakMemoryCache;
import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.ImageLoaderConfiguration;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.activity.IndexActivity;
import com.sinaleju.lifecircle.app.activity.LoginActivity;
import com.sinaleju.lifecircle.app.activity.StartActivity;
import com.sinaleju.lifecircle.app.cooperationaccount.sina.AccessTokenKeeper;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.dialog.VisitingCardDialog;
import com.sinaleju.lifecircle.app.model.Visitor;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSUserInfo;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.VersionUpdateUtils;

public class AppContext extends Application {
	public static final boolean TEST = true;

	protected static final String TAG = "AppContext";

	public static SharedPreferences appPreferences = null;
	public static SharedPreferences mFuDongPreferences = null;
	public static int mPerIndexBg = 0;
	public static int mBusIndexBg = 0;
	public static int mProIndexBg = 0;

	public static int width = 0;
	public static int height = 0;

	private boolean mNeedShowHome = false;
	private boolean mNeedShowSendMsg = false;
	private boolean mNeedShowBusList = false;

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("app onCreate ----->");
		builtImgLoader();
		//
		didLoad();
		initFuDongPreferences();
		new VersionUpdateUtils(AppContext.this, false).checkVersion();
	}

	private void initFuDongPreferences() {
		mFuDongPreferences = getApplicationContext().getSharedPreferences(
				AppConst.APP_PREFERENCES_FU_DONG_NAME, 0);
		if (mFuDongPreferences != null) {
			setmNeedShowHome(mFuDongPreferences.getBoolean(
					AppConst.APP_PREFERENCES_NEED_SHOW_HOME_ACT, true));
			setmNeedShowSendMsg(mFuDongPreferences.getBoolean(
					AppConst.APP_PREFERENCES_NEED_SHOW_SEND_MSG, true));
			setmNeedShowBusList(mFuDongPreferences.getBoolean(
					AppConst.APP_PREFERENCES_NEED_SHOW_BUS_LIST, true));
		}
	}

	public void updateNeedShowHome() {
		if (mFuDongPreferences != null) {
			mFuDongPreferences.edit()
					.putBoolean(AppConst.APP_PREFERENCES_NEED_SHOW_HOME_ACT, mNeedShowHome)
					.commit();
		}
	}

	public void updateNeedShowSendMsg() {
		if (mFuDongPreferences != null) {
			mFuDongPreferences.edit()
					.putBoolean(AppConst.APP_PREFERENCES_NEED_SHOW_SEND_MSG, mNeedShowSendMsg)
					.commit();
		}
	}

	public void updateNeedShowBusList() {
		if (mFuDongPreferences != null) {
			mFuDongPreferences.edit()
					.putBoolean(AppConst.APP_PREFERENCES_NEED_SHOW_BUS_LIST, mNeedShowBusList)
					.commit();
		}
	}

	public void initPreferencesData(UserBean userBean) {
		if (appPreferences != null) {
			mPerIndexBg = appPreferences.getInt(AppConst.APP_PREFERENCES_KEY_PER_INDEX_BG
					+ userBean.getUid(), 0);
			mBusIndexBg = appPreferences.getInt(AppConst.APP_PREFERENCES_KEY_BUS_INDEX_BG
					+ userBean.getUid(), 0);
			mProIndexBg = appPreferences.getInt(AppConst.APP_PREFERENCES_KEY_PRO_INDEX_BG
					+ userBean.getUid(), 0);
		}
	}

	public void initPreferences(UserBean userBean) {
		if (appPreferences == null)
			appPreferences = getApplicationContext().getSharedPreferences(
					AppConst.APP_SHARED_PREFERENCES_FILE_NAME + userBean.getUid(), 0);
	}

	public static void updatePerIndexPreferences(UserBean userBean) {
		if (appPreferences != null) {
			appPreferences
					.edit()
					.putInt(AppConst.APP_PREFERENCES_KEY_PER_INDEX_BG + userBean.getUid(),
							mPerIndexBg).commit();
		}
	}

	public static void updateBusIndexPreferences(UserBean userBean) {
		if (appPreferences != null) {
			appPreferences
					.edit()
					.putInt(AppConst.APP_PREFERENCES_KEY_BUS_INDEX_BG + userBean.getUid(),
							mBusIndexBg).commit();
		}
	}

	public static void updateProIndexPreferences(UserBean userBean) {
		if (appPreferences != null) {
			appPreferences
					.edit()
					.putInt(AppConst.APP_PREFERENCES_KEY_PRO_INDEX_BG + userBean.getUid(),
							mProIndexBg).commit();
		}
	}

	public boolean ismNeedShowHome() {
		return mNeedShowHome;
	}

	public void setmNeedShowHome(boolean mNeedShowHome) {
		this.mNeedShowHome = mNeedShowHome;
	}

	public boolean ismNeedShowSendMsg() {
		return mNeedShowSendMsg;
	}

	public void setmNeedShowSendMsg(boolean mNeedShowSendMsg) {
		this.mNeedShowSendMsg = mNeedShowSendMsg;
	}

	public boolean ismNeedShowBusList() {
		return mNeedShowBusList;
	}

	public void setmNeedShowBusList(boolean mNeedShowBusList) {
		this.mNeedShowBusList = mNeedShowBusList;
	}

	public static void logOff(HomeActivity act) throws SQLException {
		LogUtils.i(TAG, "user origin " + curUser().getOrigin());
		// if (curUser().getOrigin().equals("sina")) { // 如果是新浪账户登录则在退出时清除其登录状态。
		// }
		AccessTokenKeeper.clear(act);
		UserBean.deleteAll(act.getHelper());
		Intent intent = new Intent();
		intent.setClass(act, StartActivity.class);
		act.startActivity(intent);
		sUser = null;
		sVisitor = null;

	}

	// public static void gotoTopicDetailActivity(Activity a){
	// Intent intent = new Intent
	// }

	private void didLoad() {
		// refreshUser
		try {
			refreshUser(null);
		} catch (SQLException e) {
			LogUtils.e(TAG, "", e);
		}
	}

	public static boolean isVisitor() {
		return sUser == null && sVisitor != null;
	}

	public static Visitor curVisitor() {
		return sVisitor;
	}

	public static boolean isValid(final Context c) {
		if (isVisitor()) {
			new AlertDialog.Builder(c).setTitle("登录").setMessage("该功能需要您登录，是否跳转到登录页面？")
					.setNegativeButton("返回", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface d, int arg1) {
							d.dismiss();
						}
					}).setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent = new Intent(c, LoginActivity.class);
							c.startActivity(intent);
						}
					}).show();

			return false;
		} else if (sUser == null && sVisitor == null) {
			return false;
		}
		return true;
	}

	private void builtImgLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(1500000)
				// 1.5 Mb
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// .enableLogging() // Not necessary in common
				// .offOutOfMemoryHandling()
				.memoryCache(new WeakMemoryCache()).build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance(null).init(config);
	}

	public static String getThumbFolderPath() {
		String path = Environment.getExternalStorageDirectory() + "/" + AppConst.APP_FOLDER_NAME
				+ "/" + AppConst.THUMBNAIL_FLODER_NAME + "/";
		makedirs(path);
		return path;
	}

	public static String getImageFolderPath() {
		String path = Environment.getExternalStorageDirectory() + "/" + AppConst.APP_FOLDER_NAME
				+ "/" + AppConst.IMG_FLODER_NAME + "/";
		makedirs(path);
		return path;
	}

	private static void makedirs(String path) {

		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
	}

	private HomeActivity mHomeActivity;

	public void setHomeActivity(HomeActivity homeActivity) {
		this.mHomeActivity = homeActivity;
	}

	public HomeActivity getHomeActivity() {
		return mHomeActivity;
	}

	private static UserBean sUser = null;
	private static Visitor sVisitor = null;

	public static void createVisitor(int community_id, String community_name, int topicCount) {
		sUser = null;
		sVisitor = new Visitor(community_id, community_name, topicCount);
	}

	public void refreshUser(DatabaseOpenHelper helper) throws SQLException {
		if (helper == null) {
			helper = (DatabaseOpenHelper) OpenHelperManager.getHelper(this,
					DatabaseOpenHelper.class);
			sUser = UserBean.query(helper.getUserBeanDao());
			OpenHelperManager.releaseHelper();
			return;
		}

		try {
			sUser = UserBean.query(helper.getUserBeanDao());
		} catch (SQLException e) {
			LogUtils.e(TAG, "", e);
		}

		// initPerenference
		initPreferences(sUser);
		initPreferencesData(sUser);
	}

	public static UserBean curUser() {
		return sUser;
	}

	public void createUser(DatabaseOpenHelper helper, int uid) throws SQLException {
		sVisitor = null;
		UserBean.deleteAll(helper);
		UserBean.create(helper, uid);
		refreshUser(helper);

	}

	public void updateUserInfo(final DatabaseOpenHelper helper, final OnSuccessHandler success,
			final OnFaultHandler fault) {

		if (curUser() == null) {
			LogUtils.e(TAG, "cur user is null , must be a visitor mode");
			return;
		}

		RSUserInfo rs = null;

		rs = new RSUserInfo(curUser().getUid());

		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				// LogUtils.e(TAG, "userInfo : " + result.toString());
				try {
					// 获取当前内存中的user
					UserBean bean = curUser();
					// 解析数据
					bean.parseJson(result.toString());
					// 删除数据库中的原数据
					UserBean.deleteAll(helper);
					// 将心的数据缓存
					bean.cache(helper);
					LogUtils.i(TAG, "userInfo : " + sUser.getUid());

					if (success != null)
						success.onSuccess(sUser);

				} catch (JSONException e) {
					if (fault != null) {
						fault.onFault(e);
					}
					LogUtils.e(TAG, "userInfo : ", e);
				} catch (SQLException e) {
					if (fault != null) {
						fault.onFault(e);
					}
					LogUtils.e(TAG, "userInfo : ", e);
				}
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				LogUtils.e(TAG, "userInfo : ", ex);
				if (fault != null) {
					fault.onFault(ex);
				}

			}
		});
		rs.asyncExecute(this);
		// dao.createOrUpdate(getUserBean(dao));
	}

	/**
	 * 
	 * @param context
	 * @param type
	 *            0是个人 1是商家 2是物业
	 * @param visitorId
	 */
	public static void gotoIndexActivity(Context context, int type, int visitorId) {
		if (isValid(context)) {
			Intent intent = new Intent();
			intent.setClass(context, IndexActivity.class);
			if (type == 0) {
				intent.putExtra("is_personal_index", true);
				intent.putExtra("is_property", false);
				intent.putExtra("mVisitor_Id", visitorId);
				context.startActivity(intent);
			} else if (type == 1) {
				intent.putExtra("is_personal_index", false);
				intent.putExtra("is_property", false);
				intent.putExtra("mVisitor_Id", visitorId);
				context.startActivity(intent);
			} else if (type == 2) {
				intent.putExtra("is_personal_index", false);
				intent.putExtra("is_property", true);
				intent.putExtra("mVisitor_Id", visitorId);
				context.startActivity(intent);
			}
		}
	}

	public static void performVisitPeople(Context c, int uid, int u_type) {
		if (isValid(c)) {
			if (uid == sUser.getUid()) {
				gotoIndexActivity(c, u_type, uid);
			} else {
				VisitingCardDialog.pop(c, uid, u_type);
			}
		}
	}

	private List<WeakReference<Activity>> mActs = new LinkedList<WeakReference<Activity>>();

	public void addActs(Activity act) {
		WeakReference<Activity> a = new WeakReference<Activity>(act);
		mActs.add(a);
	}

	public void finishAct(Activity act) {

	}

	public void clearTask() {
		clearAllTaskExcept(null);
	}

	public void clearAllTaskExcept(Activity act) {
		for (WeakReference<Activity> wr : mActs) {
			Activity a = wr.get();
			if (a != null && a != act) {
				a.finish();
			}
		}
	}

}

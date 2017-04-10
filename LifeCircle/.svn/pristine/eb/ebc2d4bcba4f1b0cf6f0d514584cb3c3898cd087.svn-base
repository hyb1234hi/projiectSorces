package com.sinaleju.lifecircle.app.activity;

import java.sql.SQLException;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.db.DSMerchantInsert;
import com.sinaleju.lifecircle.app.utils.ADHandler;
import com.sinaleju.lifecircle.app.utils.ADHandler.MessageHandler;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends OrmLiteBaseActivity<DatabaseOpenHelper> {
	private ImageView mBackground = null;
	private BitmapDrawable mBackgroundDrawable;
	private static final int MSG_GO = 1;
	private static final long START_DELAY = 1000;
	protected static final String TAG = "WelcomeActivity";

	/** Called when this activity is first create. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_welcome);
		MobclickAgent.openActivityDurationTrack(false);
		// init
		init();
		start();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		mBackground = (ImageView) findViewById(R.id.img);
		mBackgroundDrawable = new BitmapDrawable(BitmapFactory.decodeResource(
				getResources(), R.drawable.bg_welcome));
		mBackground.setImageDrawable(mBackgroundDrawable);
	}

	private void start() {

		try {
			if (appSettingReady()) {
				//
				final long startTime = System.currentTimeMillis();
				DSMerchantInsert ds = new DSMerchantInsert(getHelper()
						.getWritableDatabase());
				ds.setOnSuccessHandler(new OnSuccessHandler() {

					@Override
					public void onSuccess(Object result) {
						go(Math.abs(START_DELAY - System.currentTimeMillis() + startTime));
					}
				});
				ds.setOnFaultHandler(new OnFaultHandler() {

					@Override
					public void onFault(Exception ex) {
						finish();
						LogUtils.e(TAG, "", ex);
					}
				});
				ds.asyncExecute(this);
			} else {
				go(START_DELAY);
			}
		} catch (SQLException e) {
			LogUtils.e(TAG, "", e);
		}

	}

	private void go(long time) {
		System.out.println("启动耗时：" + time);
		new ADHandler<WelcomeActivity>(this,
				new MessageHandler<WelcomeActivity>() {

					@Override
					public void handleMessage(WelcomeActivity o, Message msg) {
						if (msg.what == MSG_GO) {

							if (AppContext.curUser() != null) {
								gotoHomeActivity();
							} else {
								gotoStartActivity();
							}

							finish();
						}
					}
				})
				.sendEmptyMessageDelayed(MSG_GO, time);
	}

	protected void gotoStartActivity() {
		Intent intent = new Intent(this, StartActivity.class);
		startActivity(intent);
	}

	protected void gotoHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		if (mBackgroundDrawable != null) {
			mBackgroundDrawable.getBitmap().recycle();
			mBackgroundDrawable = null;
			// System.gc();
		}
		super.onDestroy();
	}

	private boolean appSettingReady() throws SQLException {
		return merchantSettingReady();
	}

	private boolean merchantSettingReady() throws SQLException {
		return getHelper().getMerchantCategoryBeanDao().queryForAll().size() == 0;
	}
	
}

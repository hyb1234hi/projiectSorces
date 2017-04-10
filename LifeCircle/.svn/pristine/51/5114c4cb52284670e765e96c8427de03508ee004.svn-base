package com.sinaleju.lifecircle.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup.LayoutParams;
import android.widget.TextView;

import com.iss.imageloader.core.DisplayImageOptions;
import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.SinaParameterEncryptor;

public class PublicUtils {

	public static String[] perIndexBg = { "per_index_bg.png", "per_index_bg_1.png",
			"per_index_bg_2.png" };
	public static String[] busIndexBg = { "bus_index_bg.png", "bus_index_bg_1.png",
			"bus_index_bg_2.png" };
	public static String[] proIndexBg = { "pro_index_bg.png", "pro_index_bg_1.png",
			"pro_index_bg_2.png" };

	public static final String LOG_TAG = "dongdianzhou" + PublicUtils.class.getName();

	public static final String MOBCLICKAGENT_FILTER = "filter";// 首页筛选
	public static final String MOBCLICKAGENT_GUEST = "guest";// 游客浏览
	public static final String MOBCLICKAGENT_SEARCH = "search";// 首页搜索框
	public static final String MOBCLICKAGENT_TOPIC = "topic";// 右侧话题
	public static final String MOBCLICKAGENT_AROUND_MERCHANT = "around_merchant";// 右侧商家搜索

	/***
	 * 给输入汉字的text加粗
	 */
	public static void blodChineseText(TextView textView) {
		TextPaint tp = textView.getPaint();
		tp.setFakeBoldText(true);
	}

	/***
	 * 处理服务器拿到的pic宽和高格式
	 * 
	 * @param pic
	 * @return
	 */
	public static String[] operationPicformget2(String pic) {
		if (!TextUtils.isEmpty(pic)) {
			String[] split = pic.split("[|]");
			if (split != null && split.length > 0) {
				return split;
			}
			System.out.println(LOG_TAG + " picData: " + split[0]);
		}
		return null;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	// 判断是否为url
	public static boolean isUrl(String url) {
		boolean isUrl = false;
		if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
			isUrl = true;
		}
		return isUrl;
	}

	/**
	 * 读drawable包下图片
	 * 
	 * @return
	 */
	public static Bitmap obtainPictureBitmap(Context myContext, int position) {
		try {
			InputStream inputStream = myContext.getResources().openRawResource(position);
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, opt);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取设备的系统版本
	public static String getDeviceSystemVersion() {
		// System.out.println(LOG_TAG + " 当前的系统版本是�?"
		// + System.getProperty("os.version") + Build.VERSION.INCREMENTAL);
		return System.getProperty("os.version") + Build.VERSION.INCREMENTAL;
	}

	// 获取设备的类�?
	public static String getSystemType() {
		// System.out.println(LOG_TAG + "你的设备的型号为�? + Build.MODEL +
		// Build.PRODUCT);
		return android.os.Build.VERSION.SDK;// android.os.Build.VERSION.SDK
											// //Build.MODEL + Build.PRODUCT
	}

	// 获取机器唯一标示符IMEI
	public static String getMachineCode(Context context) {
		TelephonyManager TelephonyMgr = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		String szImei = TelephonyMgr.getDeviceId();
		return szImei;
	}

	public static String getDateString(int time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((long) time * 1000);

		Calendar nowCal = Calendar.getInstance();
		nowCal.setTimeInMillis(System.currentTimeMillis());

		Formatter ft = new Formatter(Locale.CHINA);

		if (cal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR)
				&& cal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)) {
			if (nowCal.get(Calendar.DAY_OF_MONTH) >= cal.get(Calendar.DAY_OF_MONTH)) {
				if (nowCal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH) == 0) {
					return ft.format("%1$tT", cal).toString();
				} else if (nowCal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH) == 1) {
					return "一天前";
				} else if (nowCal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH) == 2) {
					return "两天前";
				} else if (nowCal.get(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH) == 3) {
					return "三天前";
				} else {
					return ft.format("%1$tY-%1$tm-%1$td", cal).toString();
				}
			} else {
				return ft.format("%1$tY-%1$tm-%1$td", cal).toString();
			}

		} else {
			return ft.format("%1$tY-%1$tm-%1$td", cal).toString();
		}

	}

	public static String getKnowledgeDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);

		Calendar nowCal = Calendar.getInstance();
		nowCal.setTimeInMillis(System.currentTimeMillis());

		Formatter ft = new Formatter(Locale.CHINA);
		return ft.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM", cal).toString();
	}

	public static String getDesginDetailCallDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);

		// Calendar nowCal = Calendar.getInstance();
		// nowCal.setTimeInMillis(System.currentTimeMillis());

		Formatter ft = new Formatter(Locale.CHINA);
		return ft.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", cal).toString();
	}

	public static String getCommDateString(int time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((long) time * 1000);

		Calendar nowCal = Calendar.getInstance();
		nowCal.setTimeInMillis(System.currentTimeMillis());

		Formatter ft = new Formatter(Locale.CHINA);
		return ft.format("%1$tY-%1$tm-%1$td", cal).toString();
	}

	public static String getNowTime() {
		long timeTemp = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeTemp);
		Formatter ft = new Formatter(Locale.CHINA);
		return ft.format("%1$tY-%1$tm-%1$td", cal).toString();
	}

	/**
	 * 动态设置configChanges
	 */
	public static void setConfigChanges(Context context) {
		String systemType = getSystemType();
		int systemTypeInt = Integer.parseInt(systemType);
		if (systemTypeInt > 13) {

		}
	}

	/***
	 * 判断当前网络是否连接
	 * 
	 * @param con
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		if (netinfo.isConnected()) {
			return true;
		}
		return false;
	}

	// 获取应用程序的版本号
	public static String getAppVersion(Context context) {
		String appVersionName;
		try {
			appVersionName = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionName;
			return appVersionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * 设置Listview的高度
	 */

	public static void setListViewHeight(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItem = listAdapter.getView(i, null, listView);

			listItem.measure(0, 0);

			totalHeight += listItem.getMeasuredHeight();

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);

	}

	public static int getCharacterNum(String string) {
		if (string == null || TextUtils.isEmpty(string)) {
			return 0;
		} else {
			char[] charArray = string.toCharArray();
			int len = charArray.length;
			return len;
		}
	}

	public static String getYearMonth() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return year + "," + month;
	}

	public static String getDay() {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		return date + "";
	}

	public static void saveDatatoLocalFile(String data, String filePath) {
		if (!TextUtils.isEmpty(data) && !TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);
			if (file != null && file.exists()) {
				file.delete();
			}
			FileOutputStream outStr = null;
			try {
				if (file.createNewFile()) {
					outStr = new FileOutputStream(file);
					if (outStr != null) {
						outStr.write(data.getBytes());
						outStr.flush();
					}
					LogUtils.i("PublicUtils", "saveDatatoLocalFile  -----------   file path"
							+ filePath);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (outStr != null) {
					try {
						outStr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						outStr = null;
					}
				}
			}

		}
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue, float fontScale) {
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static TextView getRadioButton(Context mContext) {
		TextView textView = new TextView(mContext);
		LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.leftMargin = 8;
		textView.setPadding(17, 0, 17, 0);
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(15);
		textView.setTextColor(Color.BLACK);
		textView.setLayoutParams(layoutParams);
		return textView;
	}

	public static Bitmap getImage(String path) {
		Bitmap b = null;
		if (path == null || path.length() == 0) {
			return null;
		} else {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
				b = BitmapFactory.decodeFile(path, options);
			} catch (Exception e) {

			}
		}
		return b;
	}

	public static Options getOptions() {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return opt;
	}

	public static Options getSimpleOptions(int size) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inSampleSize = size;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return opt;
	}

	public static Options getBigQualityOptions() {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return opt;
	}

	// 判断当前网络是否为wifi
	public static boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 从sharePreference中取数据
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getStringData(Context context, String projectName, String key) {
		SharedPreferences preference = context.getSharedPreferences(projectName,
				Context.MODE_PRIVATE);
		String value = preference.getString(key, "");
		return value;
	}

	public static void loadHeadImage(ImageView imageView, String url, int drawable) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		if (drawable != 0) {
			builder.showStubImage(drawable);
		}
		builder.cacheOnDisc();
		builder.decodingOptions(PublicUtils.getOptions());
		DisplayImageOptions options = builder.build();
		ImageLoader.getInstance(null).displayImage(url, imageView, options);
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
		Bitmap output = Bitmap
				.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static int getUserDefaultHeadImage(int type) {
		int drawable = R.drawable.per_left_default_head_image;
		if (type == 0) {
			drawable = R.drawable.per_left_default_head_image;
		} else if (type == 1) {
			drawable = R.drawable.bus_left_default_head_image;
		} else if (type == 2) {
			drawable = R.drawable.pro_left_default_head_image;
		}
		return drawable;
	}

	public static int getUserIndexDefaultHeadImage(int type) {
		int drawable = R.drawable.per_index_default_head_image;
		if (type == 0) {
			drawable = R.drawable.per_index_default_head_image;
		} else if (type == 1) {
			drawable = R.drawable.bus_index_default_head_image;
		} else if (type == 2) {
			drawable = R.drawable.pro_index_default_head_image;
		}
		return drawable;
	}

	public static String getUserIndexDefaultBg(int userType) {
		String drawable = null;
		if (userType == 0) {
			if (AppContext.mPerIndexBg >= 0) {
				drawable = perIndexBg[AppContext.mPerIndexBg];
			}
		} else if (userType == 1) {
			if (AppContext.mBusIndexBg >= 0) {
				drawable = busIndexBg[AppContext.mBusIndexBg];
			}
		} else if (userType == 2) {
			if (AppContext.mProIndexBg >= 0) {
				drawable = proIndexBg[AppContext.mProIndexBg];
			}
		}
		return drawable;
	}

	public static String getUserIndexDefaultBgForNull(int userType) {
		String drawable = null;
		if (userType == 0) {
			if (AppContext.mPerIndexBg >= 0) {
				drawable = perIndexBg[0];
			}
		} else if (userType == 1) {
			if (AppContext.mBusIndexBg >= 0) {
				drawable = busIndexBg[0];
			}
		} else if (userType == 2) {
			if (AppContext.mProIndexBg >= 0) {
				drawable = proIndexBg[0];
			}
		}
		return drawable;
	}

	public static int getIndexDefaultBg(int type) {
		int drawable = R.drawable.per_index_bg;
		if (type == 0) {
			drawable = R.drawable.per_index_bg;
		} else if (type == 1) {
			drawable = R.drawable.bus_index_bg;
		} else if (type == 2) {
			drawable = R.drawable.pro_index_bg;
		}
		return drawable;
	}

	public static void hideSoftInputMethod(Context ct) {
		if (ct != null && ((Activity) ct).getCurrentFocus() != null) {
			InputMethodManager imm = (InputMethodManager) ct
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			IBinder binder = ((Activity) ct).getCurrentFocus().getWindowToken();
			if (binder != null) {
				imm.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
			}

		}
	}

	public static final String RETURN_URL = "http://m.quanup.com/";

	public static String getTicketUrl(String text) {
		String url = "";
		Map<String, String> map = new HashMap<String, String>();
		map.put("login_name", AppContext.curUser().getLogin_name());
		map.put("id", AppContext.curUser().getUid() + "");
		map.put("type", AppContext.curUser().getType() + "");
		map.put("display_name", AppContext.curUser().getName());
		map.put("add_time", System.currentTimeMillis() / 1000 + "");
		map.put("return_url", text);
		map = SinaParameterEncryptor.mapWithEncryptedToken(map);
		map.put(RemoteConst.REQUEST_KEY_KEY, RemoteConst.REQUEST_KEY_VALUE);
		String baseUrl = "http://m.quanup.com/?app=Api&c=Mobile_Login&a=doLogin";
		url = getBaseUrlString(map, baseUrl);
		return url;
	}

	public static String getBaseUrlString(Map<String, String> params, String baseUrl) {
		String paramStr = "";
		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			paramStr += paramStr = "&" + key + "=" + val;
		}
		if (!paramStr.equals("")) {
			baseUrl += paramStr;
		}
		LogUtils.d("ticket need login url :", baseUrl);
		return baseUrl;
	}

}

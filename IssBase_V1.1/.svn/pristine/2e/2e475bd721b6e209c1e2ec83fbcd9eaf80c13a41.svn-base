package com.iss.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 异步图片下载器
 * 
 * @author huangyu
 * 
 */
public class AsyncImageLoader {
	private static Map<String, Bitmap> imageCache = new HashMap<String, Bitmap>();

	public static ExecutorService pool = Executors.newFixedThreadPool(2); 

	public static final String imageCachePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/sinaiditu/imageCache/";

	private static final String TAG = "AsyncImageLoader";
	
	public static String imageCachePath_data;
	
	public static void clearImageMap() {
		imageCache.clear();
	}

	public static Bitmap loadDrawable(final String imageUrl, final ImageCallback callback) {
				Log.e(TAG,"imageUrl : "+imageUrl);
		Bitmap drawable = null;
		drawable = imageCache.get(imageUrl);
		if(drawable!=null){
			return drawable;
		}
		if (avaiableSdcard()) {// 如果有sd卡，读取sd卡
			drawable = getPicByPath(imageCachePath, imageUrl);
			if (drawable != null) {
				Log.e(TAG,"get bitmap from disk");
				 imageCache.put(imageUrl, drawable);
				return drawable;
			}
		} else {// 如果没有sd卡，读取手机里面
			drawable = getPicByPath(imageCachePath_data, imageUrl);
			if (drawable != null) {
				Log.e(TAG,"get bitmap from disk");
				 imageCache.put(imageUrl, drawable);
				return drawable;
			}
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Bitmap) msg.obj, imageUrl);
			}
		};
		Runnable task = new Runnable() {
			public void run() {
//				Bitmap drawable = loadImageFromUrl(imageUrl);
				Bitmap drawable = getBiggerBitmapFromURL(imageUrl);
				handler.sendMessage(handler.obtainMessage(0, drawable));
			};
		};

		pool.execute(task);
		
		return null;
	}

	/**
	 * 判断是否存在sd卡
	 * 
	 * @return
	 */
	public static boolean avaiableSdcard() {
		String status = Environment.getExternalStorageState();

		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取图片
	 * 
	 * @param picName
	 * @return
	 */
	public static Drawable getPic_Draw_ByPath(String path, String picName) {
		picName = picName.substring(picName.lastIndexOf("/") + 1);
		String filePath = path + picName;
		return Drawable.createFromPath(filePath);
	}

	/**
	 * 获取图片
	 * 
	 * @param picName
	 * @return
	 */
	public static Bitmap getPicByPath(String path, String picName) {
		
		picName = getStr(picName);
		
//		picName = picName.substring(picName.lastIndexOf("/") + 1);
		String filePath = path + picName;
		
		//判断文件是否存在
			File file = new File(filePath);
			if(!file.exists()){//文件不存在
				return null;
			}
		
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);

		return bitmap;
	}

	public static Bitmap loadDrawable(String imageUrl) {
		if (imageCache.containsKey(imageUrl)) {
			Bitmap softReference = imageCache.get(imageUrl);
			if (softReference != null) {
				return softReference;
			} else {
				return null;
			}
		}
		return null;
	}

	protected static Bitmap loadImageFromUrl(String imageUrl) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.connect();
			/*BitmapFactory.Options options = new BitmapFactory.Options(); 
			options.inSampleSize = 1;   
			//只进行大小判断  
			options.inJustDecodeBounds = true;  
			BitmapFactory.decodeStream(url.openStream(),null,options);
			int width = options.outWidth;
//			if(width > 200){
//				options.inSampleSize = 2;
//				LogUtil.printInfo("options.inSampleSize : "+options.inSampleSize);
//			}
			options.inJustDecodeBounds = false; 
			options.inDither = false;*/   
			Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
			conn.disconnect();
			if(bitmap!=null){
				Log.e(TAG,"get bitmap from net");
				 imageCache.put(imageUrl, bitmap);
				 savePic(bitmap, imageUrl);// 保存图片
			}
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	public static void savePic(Bitmap bitmap, String imageUrl) {
		if (bitmap != null && imageUrl != null && !"".equals(imageUrl)) {
			if (avaiableSdcard()) {// 如果有sd卡，保存在sd卡
				savePicToSdcard(bitmap, imageUrl);
			} else {// 如果没有sd卡，保存在手机里面
				saveToDataDir(bitmap, imageUrl);
			}
		}
	}
	/**
	 * 这个方法是保存的名字    可以根据不同的url 来定义
	 * @param str
	 * @return
	 */
	private static String getStr(String str) {
//		String sub1 = str.substring(0, str.lastIndexOf("/"));
//		if(sub1.lastIndexOf("/mobile")!=-1){
//			sub1 = sub1.substring(0,sub1.lastIndexOf("/mobile"));
//		}
//		sub1 = sub1.substring(sub1.lastIndexOf("/")+1, sub1.length());
//		String sub2 = str.substring(str.lastIndexOf("/")+1, str.length());
//		return sub1+sub2;
		String[] strN = str.split("/");
		
		String strname = strN[strN.length-3]+strN[strN.length-2]+strN[strN.length-1];
		return strname ;
	}

	/**
	 * 将图片保存在sd卡
	 * 
	 * @param bitmap
	 *            图片
	 * @param picName
	 *            图片名称（同新闻id名）
	 */
	private static void savePicToSdcard(Bitmap bitmap, String picName) {
		
		picName = getStr(picName);
		
		String path = imageCachePath;
		File file = new File(path + picName);
		FileOutputStream outputStream;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
				outputStream = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 74, outputStream);
				outputStream.close();
			} catch (Exception e) {
				// Log.e("", e.toString());
			}
		}
	}

	/**
	 * 保存文件到应用目录
	 * 
	 * @param bitmap
	 * @param fileName
	 *            文件名称
	 */
	static void saveToDataDir(Bitmap bitmap, String fileName) {
//		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		fileName = getStr(fileName);
		String path = imageCachePath_data;
		File file = new File(path + fileName);
		FileOutputStream outputStream;
		if (!file.exists()) {
			try {
				file.getParentFile().mkdirs();
				file.createNewFile();
				outputStream = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
				outputStream.close();
			} catch (Exception e) {
				Log.e("", e.toString());
			}
		}
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl);
	}
	
	

	/**
	 * 取得网络图片
	 * 
	 * @param urlPath
	 * @param context
	 * @return
	 */
	protected static Bitmap getBiggerBitmapFromURL(final String urlPath) {
		byte[] imageByte = getImageFromURL(urlPath.trim());

		Bitmap bitmap = null;
		if (imageByte != null) {
			// 以下是把图片转化为缩略图再加载
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
					imageByte.length, options);
			options.inJustDecodeBounds = false;
			int be = (int) (options.outHeight / (float) 200);
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;
			try {
				bitmap = BitmapFactory.decodeByteArray(imageByte, 0,
						imageByte.length, options);
				savePic(bitmap, urlPath);
			} catch (OutOfMemoryError e) {
				Log.v("", e.getMessage());
			}

		}
		return bitmap;
	}
	
	public static byte[] getImageFromURL(String urlPath) {
		byte[] data = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30 * 1000);
			is = conn.getInputStream();
			if (conn.getResponseCode() == 200) {
				data = readInputStream(is);
			} else
				Log.v("", "net state" + conn.getResponseCode());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
			try {
				if (is != null) {
					is.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public static byte[] readInputStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
		} catch (OutOfMemoryError e) {
			Log.v("", e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = null;
		try {
			data = baos.toByteArray();
		} catch (OutOfMemoryError e) {
			Log.v("", e.getMessage());
		}
		try {
			is.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}

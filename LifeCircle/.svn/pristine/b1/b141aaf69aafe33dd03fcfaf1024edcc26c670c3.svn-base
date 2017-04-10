package com.sinaleju.lifecircle.app.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

public class ADBitmapUtils {
	public static void saveBitmapToSDCard(Bitmap thumb, String filePath)
			throws IOException {
		File myCaptureFile = new File(filePath);
		BufferedOutputStream bos = null;
		bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		if (thumb != null)
			thumb.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}

	public static Bitmap createBitmapFromFile(Context a, String path) {
		Uri uri = Uri.parse("file:///" + path);
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opt = PublicUtils.getOptions();
			bitmap = BitmapFactory.decodeStream(a.getContentResolver()
					.openInputStream(uri), null, opt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public static Bitmap createBitmapFromAssets(Context myContext,
			String pathName) {
		Bitmap bitmap = null;
		try {
			InputStream is = myContext.getResources().getAssets()
					.open(pathName);
			BitmapFactory.Options opt = PublicUtils.getOptions();
			bitmap = BitmapFactory.decodeStream(is, null, opt);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	public static BitmapDrawable createBitmapDrawableFromAssets(Context myContext,
			String pathName) {
		BitmapDrawable drawable = null;
		Bitmap bitmap = createBitmapFromAssets(myContext, pathName);
		if(bitmap != null){
			drawable = new BitmapDrawable(bitmap);
		}
		return drawable;
	}

	public static Bitmap createThumbFromFile(Context a, String path) {
		Uri uri = Uri.parse("file:///" + path);
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opt = PublicUtils.getOptions();
			opt.inSampleSize = 3;
			bitmap = BitmapFactory.decodeStream(a.getContentResolver()
					.openInputStream(uri), null, opt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public static boolean saveBitmapToSDCardNoPress(Bitmap thumb,
			String filePath) throws IOException {

		File myCaptureFile = new File(filePath);
		if (myCaptureFile.exists())
			return false;
		BufferedOutputStream bos = null;
		bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		thumb.compress(Bitmap.CompressFormat.PNG, 100, bos);
		bos.flush();
		bos.close();
		return true;
	}

	public static Bitmap rotate(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2,
					(float) b.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
						b.getHeight(), m, true);
				if (b != b2) {
					b.recycle(); // Android开发网再次提示Bitmap操作完应该显示的释放
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
				// Android123建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
			}
		}
		return b;
	}
}

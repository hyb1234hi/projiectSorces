package com.sinaleju.lifecircle.app.camera;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.iss.imageloader.core.display.RoundedBitmapDisplayer;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class ADCamera {

	public static final int RC_GET_IMAGE_VIA_CAMERA = 10;

	// data
	private static final String TAG = "MainActivity";
	private static final int TAKE_BIG_PICTURE = 112;
	private static final int TAKE_SMALL_PICTURE = 113;
	public static final int CROP_BIG_PICTURE = 114;
	private static final int CROP_SMALL_PICTURE = 115;
	private static final int CHOOSE_BIG_PICTURE = 116;
	private static final int CHOOSE_SMALL_PICTURE = 117;

	public static final int TAKE_CAMERA_PICTURE = 118;// 通用的一个从相机获取图片的处理
	public static final int CROP_BIT_QUALITY_PICTURE = 119;// 通用的一个从相机获取图片的处理

	private Uri imageUri;// to store the big bitmap
	private Activity a;
	private String path;
	private Fragment f;

	private ADCamera(Activity activity, Fragment f) {
		this.a = activity;
		this.f = f;
	}

	public static ADCamera builder(Activity activity) {
		return new ADCamera(activity, null);
	}

	public static ADCamera builder(Activity activity, Fragment f) {

		return new ADCamera(activity, f);
	}

	public ADCamera setImageUri(String savePath) {
		this.imageUri = Uri.parse("file:///" + savePath);
		return this;
	}

	public ADCamera setDefultBigImageUri(String imgName) {
		setPath(AppContext.getImageFolderPath() + imgName);
		this.imageUri = Uri.parse("file:///" + path);
		return this;
	}

	public ADCamera setDefultSmallImageUri(String imgName) {
		setPath(AppContext.getThumbFolderPath() + imgName);
		this.imageUri = Uri.parse("file:///" + path);
		return this;
	}

	private void cropImageUriWithParams(Uri uri, int aspectX, int aspectY, int outputX,
			int outputY, boolean scale, boolean returndata, boolean noFaceDetection, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");

		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// intent.putExtra("outputX", outputX);
		// intent.putExtra("outputY", outputY);
		intent.putExtra("scale", scale);
		intent.putExtra("return-data", returndata);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", noFaceDetection); // no face
		startActivityForResult(intent, requestCode);
	}

	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		cropImageUriWithParams(uri, 1, 1, outputX, outputY, true, false, true, requestCode);
	}

	public boolean onActivityResult(ImageView imageView, int requestCode, int resultCode,
			Intent data) {

		if (resultCode != Activity.RESULT_OK) {// result is not correct
			LogUtils.e(TAG, "requestCode = " + requestCode);
			LogUtils.e(TAG, "resultCode = " + resultCode);
			LogUtils.e(TAG, "data = " + data);
			return false;
		} else {
			switch (requestCode) {
			case TAKE_BIG_PICTURE:
				LogUtils.d(TAG, "TAKE_BIG_PICTURE: data = " + data);
				cropImageUri(imageUri, 800, 800, CROP_BIG_PICTURE);
				return true;
			case CROP_BIG_PICTURE:// from crop_big_picture
				LogUtils.d(TAG, "CROP_BIG_PICTURE: data = " + data);
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					imageView.setImageBitmap(bitmap);
				}
				return true;
			case CROP_BIT_QUALITY_PICTURE:// from crop_big_picture
				LogUtils.d(TAG, "CROP_BIG_PICTURE: data = " + data);
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri, PublicUtils.getBigQualityOptions());
					imageView.setImageBitmap(bitmap);
				}
				return true;
			case TAKE_SMALL_PICTURE:
				LogUtils.i(TAG, "TAKE_SMALL_PICTURE: data = " + data);
				cropImageUri(imageUri, 200, 200, CROP_SMALL_PICTURE);
				return true;
			case CROP_SMALL_PICTURE:
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					imageView.setImageBitmap(bitmap);
				} else {
					LogUtils.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				return true;
			case CHOOSE_BIG_PICTURE:
				LogUtils.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					imageView.setImageBitmap(bitmap);
				}
				return true;
			case CHOOSE_SMALL_PICTURE:
				if (data != null && imageView != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					imageView.setImageBitmap(bitmap);
				} else {
					LogUtils.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}
				return true;
			case TAKE_CAMERA_PICTURE:
				cropImageUriWithParams(imageUri, aspectX, aspectY, outputX, outputY, scale,
						returndata, noFaceDetection, CROP_BIG_PICTURE);
				return true;
			default:
				return false;
			}
		}
	}

	public boolean onHeadActivityResult(ImageView imageView, int requestCode, int resultCode,
			Intent data) {

		if (resultCode != Activity.RESULT_OK) {// result is not correct
			LogUtils.e(TAG, "requestCode = " + requestCode);
			LogUtils.e(TAG, "resultCode = " + resultCode);
			LogUtils.e(TAG, "data = " + data);
			return false;
		} else {
			switch (requestCode) {
			case TAKE_BIG_PICTURE:
				LogUtils.d(TAG, "TAKE_BIG_PICTURE: data = " + data);
				cropImageUri(imageUri, 400, 400, CROP_BIG_PICTURE);
				return true;
			case CROP_BIG_PICTURE:// from crop_big_picture
				LogUtils.d(TAG, "CROP_BIG_PICTURE: data = " + data);
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					if (bitmap != null) {
						imageView.setImageBitmap(RoundedBitmapDisplayer.roundCorners(bitmap,
								imageView, 100));
					}
				}
				return true;
			case TAKE_SMALL_PICTURE:
				LogUtils.i(TAG, "TAKE_SMALL_PICTURE: data = " + data);
				cropImageUri(imageUri, 400, 400, CROP_BIG_PICTURE);
				return true;
			case CROP_SMALL_PICTURE:
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					if (bitmap != null) {
						imageView.setImageBitmap(RoundedBitmapDisplayer.roundCorners(bitmap,
								imageView, 100));
					}
				} else {
					LogUtils.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				return true;
			case CHOOSE_BIG_PICTURE:
				LogUtils.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);
				if (imageUri != null && imageView != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					if (bitmap != null) {
						imageView.setImageBitmap(RoundedBitmapDisplayer.roundCorners(bitmap,
								imageView, 100));
					}
				}
				return true;
			case CHOOSE_SMALL_PICTURE:
				if (data != null && imageView != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					if (bitmap != null) {
						imageView.setImageBitmap(RoundedBitmapDisplayer.roundCorners(bitmap,
								imageView, 100));
					}
				} else {
					LogUtils.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}
				return true;
			default:
				return false;
			}
		}
	}

	private void startActivityForResult(Intent intent, int requestCode) {
		if (a instanceof FragmentActivity && f != null) {
			((FragmentActivity) a).startActivityFromFragment(f, intent, requestCode);
		} else {
			a.startActivityForResult(intent, requestCode);
		}
	}

	public BitmapDrawable onImageActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) { // result is not correct
			LogUtils.e(TAG, "requestCode = " + requestCode);
			LogUtils.e(TAG, "resultCode = " + resultCode);
			LogUtils.e(TAG, "data = " + data);
			return null;
		} else {
			switch (requestCode) {
			case TAKE_BIG_PICTURE:
				LogUtils.d(TAG, "TAKE_BIG_PICTURE: data = " + data);
				cropImageUri(imageUri, 400, 400, CROP_BIG_PICTURE);
				return null;
			case CROP_BIG_PICTURE:// from crop_big_picture
				LogUtils.d(TAG, "CROP_BIG_PICTURE: data = " + data);
				if (imageUri != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					BitmapDrawable drawable = new BitmapDrawable(bitmap);
					return drawable;
				}
				return null;
			case TAKE_SMALL_PICTURE:
				LogUtils.i(TAG, "TAKE_SMALL_PICTURE: data = " + data);
				cropImageUri(imageUri, 400, 400, CROP_BIG_PICTURE);
				return null;
			case CROP_SMALL_PICTURE:
				if (imageUri != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					BitmapDrawable drawable = new BitmapDrawable(bitmap);
					return drawable;
				} else {
					LogUtils.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				return null;
			case CHOOSE_BIG_PICTURE:
				LogUtils.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);
				if (imageUri != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					BitmapDrawable drawable = new BitmapDrawable(bitmap);
					return drawable;
				}
				return null;
			case CHOOSE_SMALL_PICTURE:
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					BitmapDrawable drawable = new BitmapDrawable(bitmap);
					return drawable;
				} else {
					LogUtils.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}
				return null;
			default:
				return null;
			}
		}
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {
		return decodeUriAsBitmap(a, uri);
	}

	private Bitmap decodeUriAsBitmap(Uri uri, Options options) {
		return decodeUriAsBitmap(a, uri, options);
	}

	public Bitmap decodeUriAsBitmap(Activity a, Uri uri) {
		return decodeUriAsBitmap(a, uri, null);
	}

	public Bitmap decodeUriAsBitmap(Activity a, Uri uri, Options options) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(a.getContentResolver().openInputStream(uri), null,
					options == null ? PublicUtils.getSimpleOptions(2) : options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError e) {
			try {
				bitmap = BitmapFactory.decodeStream(a.getContentResolver().openInputStream(uri),
						null, options == null ? PublicUtils.getSimpleOptions(4) : options);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return null;
			} catch (OutOfMemoryError e1) {
				return null;
			}
			return bitmap;
		}
		return bitmap;
	}

	public void startCameraForBigPhoto() {
		startCamera(TAKE_BIG_PICTURE);
	}

	public void startCameraForSmallPhoto() {
		startCamera(TAKE_SMALL_PICTURE);
	}

	private int aspectX;
	private int aspectY;
	private int outputX;
	private int outputY;
	private boolean scale;
	private boolean returndata;
	private boolean noFaceDetection;

	public void startCameraForPhoto(int index, int aspectX, int aspectY, int outputX, int outputY,
			boolean scale, boolean returndata, boolean noFaceDetection) {
		startCamera(index);
		this.aspectX = aspectX;
		this.aspectY = aspectY;
		this.outputX = outputX;
		this.outputY = outputY;
		this.scale = scale;
		this.returndata = returndata;
		this.noFaceDetection = noFaceDetection;
	}

	private void startCamera(int way) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, way);
	}

	public void startGalleryForBigPhoto() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		startActivityForResult(intent, CHOOSE_BIG_PICTURE);
	}

	public void startGalleryForPhoto(int aspectX, int aspectY, int outputX, int outputY,
			boolean scale, boolean returndata, boolean noFaceDetection) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);

		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", scale);
		intent.putExtra("return-data", returndata);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", noFaceDetection); // no face
		startActivityForResult(intent, CHOOSE_BIG_PICTURE);
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	private void setPath(String path) {
		this.path = path;
	}
}

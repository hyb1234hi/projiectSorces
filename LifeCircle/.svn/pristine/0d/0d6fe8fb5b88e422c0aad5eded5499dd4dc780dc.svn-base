package com.sinaleju.lifecircle.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;

public class Downloader {
	
	private static URL url;		// 目标地址
	private File dataFile;	// 本地文件
	private String filePath;
	private File tempFile;	// 用来存储每个线程下载的进度
	private static final int THREAD_AMOUNT = 3;					// 线程数
	private static final String DIR_PATH = "/mnt/sdcard/";		// 下载目录
	private int threadLen;										// 每个线程下载多少
	private int totalFinish;
	private int totalLen;
	private long begin;
	private boolean isPause;
	private String address;
	private Handler handler;

	public Downloader(String address, Handler handler) throws IOException {		// 通过构造函数传入下载地址
		url = new URL(address);
		this.address = address;
		dataFile = new File(DIR_PATH, address.substring(address.lastIndexOf("/") + 1));
		tempFile = new File(dataFile.getAbsolutePath() + ".temp");
		this.handler = handler;
	}
	
	public void pause() {
		isPause = true;
	}

	public void download() throws IOException {
		isPause = false;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3000);
		conn.setRequestProperty("Accept-Encoding", "identity");
		totalLen = conn.getContentLength();
		threadLen = (totalLen + THREAD_AMOUNT - 1) / THREAD_AMOUNT;			// 计算每个线程要下载的长度
		LogUtils.e("Downloader", "url  :: " + url +"       totalLen :: " + totalLen);
		// 发送消息, 通知主线程总长度
		Message msg = new Message();
		msg.getData().putInt("totalLen", totalLen);
		msg.what = 0;
		handler.sendMessage(msg);
		
//		if (!dataFile.exists()) {
//			RandomAccessFile raf = new RandomAccessFile(dataFile, "rws");	// 在本地创建一个和服务端大小相同的文件
//			raf.setLength(totalLen);										// 设置文件的大小
//			raf.close();
//		}
		
//		if (!tempFile.exists()) {
//			RandomAccessFile raf = new RandomAccessFile(tempFile, "rws");	// 创建临时文件, 用来记录每个线程已下载多少
//			for (int i = 0; i < THREAD_AMOUNT; i++)
//				raf.writeInt(0);
//			raf.close();
//		}
		
//		for (int i = 0; i < THREAD_AMOUNT; i++)								// 开启3条线程, 每个线程下载一部分数据到本地文件中
//			new DownloadThread(i).start();
		new Thread(new startLoadAPK()).start();
		begin = System.currentTimeMillis();
	}
	
	public class startLoadAPK implements Runnable {
		Message mMessage = Message.obtain();
		boolean hasSD = ExistSDCard();
		long sdSize = getSDFreeSize();
		InputStream is1 = null;
		StringBuffer sb = new StringBuffer();
		String s = null;

		public void run() {
			if (hasSD == true) {
				if (sdSize > 10000) {
					filePath = dataFile.getPath();
				} else {
					filePath = "/data/data/com.sinaleju.app/files/" + address.substring(address.lastIndexOf("/") + 1);
				}
			} else {
				filePath = "/data/data/com.sinaleju.app/files/" + address.substring(address.lastIndexOf("/") + 1);
			}
			File file = new File(filePath);
			if (file.exists() == false || file.isDirectory() == false) {
				file.mkdirs();
			}

			try {
				URL url = new URL(Downloader.url.toString());

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				if (conn.getResponseCode() == HttpStatus.SC_OK) {
					is1 = conn.getInputStream();
					if (is1 != null) {

						File apkfile = new File(filePath);
						if (apkfile.exists() == true) {
							apkfile.delete();
						}
						apkfile.createNewFile();
						FileOutputStream fos = new FileOutputStream(apkfile);
						byte[] buf = new byte[8 * 1024];
						int numread = 0;
						int count = 0;
						while ((numread = is1.read(buf)) > 0) {
							if(isPause)
								return;
							count += numread;
							totalFinish = (int) (((float) count / totalLen) * 100);
//							handler.sendEmptyMessage(DOWNLOADING);
							LogUtils.e("startLoadAPK", "totalFinish :  " + totalFinish  + "     totalLen :: " + totalLen);
							fos.write(buf, 0, numread);
							
							// 发消息, 通知主线程完成了多少
							Message msg = new Message();
							msg.getData().putInt("totalFinish", totalFinish);
							msg.what = 1;
							handler.sendMessage(msg);
						}
						fos.flush();
						fos.close();
						is1.close();
						mMessage.what = 2;
						mMessage.obj =dataFile;
						// return;
					}
				} else {
					mMessage.what = 404;
				}
			}

			catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				mMessage.what = 404;
				e.printStackTrace();
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				mMessage.what = 404;
				e2.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				mMessage.what = 404;
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					is1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sb = null;
				s = null;
				System.gc();
			}
			handler.sendMessage(mMessage);

		}
	}
	// 判断是否具有SD卡
	public static boolean ExistSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	// 获得SD剩余的容量
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		// 获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		// return freeBlocks * blockSize; //单位Byte
		return (freeBlocks * blockSize) / 1024; // 单位KB
		// return (freeBlocks * blockSize)/1024 /1024; //单位MB
	}
	
	private class DownloadThread extends Thread {
		private int id; 
		public DownloadThread(int id) {
			this.id = id;
		}
		public void run() {
			try {
				RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rws");
				tempRaf.seek(id * 4);							// 将指针移动到当前线程的位置
				int threadFinish = tempRaf.readInt();			// 读取当前线程已完成了多少
				synchronized(Downloader.this) {
					totalFinish += threadFinish;					// 统计所有线程总共完成了多少
				}
				
				int start = id * threadLen + threadFinish;		// 起始位置
				int end = id * threadLen + threadLen - 1;		// 结束位置
				System.out.println("线程" + id + ": " + start + "-" + end);
			
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(3000);
				conn.setRequestProperty("Range", "bytes=" + start + "-" + end);		// 设置当前线程下载的范围
				
				InputStream in = conn.getInputStream();
				RandomAccessFile dataRaf = new RandomAccessFile(dataFile, "rws");
				dataRaf.seek(start);			// 设置保存数据的位置
				
				byte[] buffer = new byte[1024 * 100];
				int len;
				while ((len = in.read(buffer)) != -1) {
					if (isPause)
						return;
					dataRaf.write(buffer, 0, len);
					threadFinish += len;				// 每次拷贝数据之后, 统计完成了多少
					tempRaf.seek(id * 4);				// 指向当前线程的位置
					tempRaf.writeInt(threadFinish);		// 将完成了多少写入到文件
					synchronized(Downloader.this) {
						totalFinish += len;				// 统计所有线程总共完成了多少
						
						// 发消息, 通知主线程完成了多少
						Message msg = new Message();
						msg.getData().putInt("totalFinish", totalFinish);
						msg.what = 1;
						LogUtils.e("Downloader", "totalFinish :: " + totalFinish);
						handler.sendMessage(msg);
					}
				}
				dataRaf.close();
				tempRaf.close();
				
				System.out.println("线程" + id + "下载完毕");
				if (totalFinish == totalLen) {			// 如果文件全部下载完毕
					System.out.println("下载完成, 耗时: " + (System.currentTimeMillis() - begin));
					Message msg = new Message();
					msg.what = 2;
					msg.obj =dataFile;
					handler.sendMessage(msg);
					tempFile.delete();
				}
			} catch (IOException e) {
				handler.sendEmptyMessage(404);
				e.printStackTrace();
			}
		}
	}
}


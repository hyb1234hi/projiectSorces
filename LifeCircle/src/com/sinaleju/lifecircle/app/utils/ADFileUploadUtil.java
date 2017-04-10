package com.sinaleju.lifecircle.app.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;

public class ADFileUploadUtil {

	public static int upload(String filePath, int sort, int msg_id) {
		int resultCode = 0;
		try {
			// 定义数据分隔线
			String BOUNDARY = "------------------------7dc2fd5c0894";
			// 定义最后数据分隔线
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

			URL url = new URL("http://shq.leju.com/api/message/upload.json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			StringBuffer params = new StringBuffer();

			// form_name参数
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"form_name\"\r\n\r\n");
			params.append("default");
			params.append("\r\n");

			// msg_id
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"message_id\"\r\n\r\n");
			params.append(msg_id);
			params.append("\r\n");

			// sort参数
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"sort\"\r\n\r\n");
			params.append(sort);
			params.append("\r\n");

			out.write(params.toString().getBytes());

			File file = new File(filePath);
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"default\";filename=\""
					+ file.getName() + "\"\r\n");
			// 这里不能漏掉，根据文件类型来来做处理，由于上传的是图片，所以这里可以写成image/pjpeg
			sb.append("Content-Type:image/pjpeg\r\n\r\n");
			out.write(sb.toString().getBytes());

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes());
			in.close();
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			StringBuilder builder = new StringBuilder();
			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}
			System.out.println(builder.toString());

			// 包装结果
			JSONObject json = new JSONObject(builder.toString());

			// 返回码处理
			resultCode = json.getInt("result");
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}

		return resultCode;
	}

	public static String uploadUserHeadImage(String formName, String uploadUrl,
			String filePath, int user_id) {
		// 定义BufferedReader输入流来读取URL的响应
		StringBuilder builder = new StringBuilder();
		try {
			// 定义数据分隔线
			String BOUNDARY = "------------------------7dc2fd5c0894";
			// 定义最后数据分隔线
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

			URL url = new URL(uploadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			StringBuffer params = new StringBuffer();

			// form_name参数
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"form_name\"\r\n\r\n");
			params.append(formName);
			params.append("\r\n");

			// user_id
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"user_id\"\r\n\r\n");
			params.append(user_id);
			params.append("\r\n");

			out.write(params.toString().getBytes());

			File file = new File(filePath);
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"" + formName
					+ "\";filename=\"" + file.getName() + "\"\r\n");
			// 这里不能漏掉，根据文件类型来来做处理，由于上传的是图片，所以这里可以写成image/pjpeg
			sb.append("Content-Type:image/pjpeg\r\n\r\n");
			out.write(sb.toString().getBytes());

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes());
			in.close();
			out.write(end_data);
			out.flush();
			out.close();

			InputStream response = conn.getInputStream();

			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(response));
			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}
			System.out.println(builder.toString());
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		return builder.toString();
	}

	public static String uploadUserHeadImageFromAss(Context context,
			String formName, String uploadUrl, String fileName, int user_id) {
		// 定义BufferedReader输入流来读取URL的响应
		StringBuilder builder = new StringBuilder();
		try {
			// 定义数据分隔线
			String BOUNDARY = "------------------------7dc2fd5c0894";
			// 定义最后数据分隔线
			byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

			URL url = new URL(uploadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			StringBuffer params = new StringBuffer();

			// form_name参数
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"form_name\"\r\n\r\n");
			params.append(formName);
			params.append("\r\n");

			// user_id
			params.append("--" + BOUNDARY + "\r\n");
			params.append("Content-Disposition: form-data; name=\"user_id\"\r\n\r\n");
			params.append(user_id);
			params.append("\r\n");

			out.write(params.toString().getBytes());

			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"" + formName
					+ "\";filename=\"" + fileName + "\"\r\n");
			// 这里不能漏掉，根据文件类型来来做处理，由于上传的是图片，所以这里可以写成image/pjpeg
			sb.append("Content-Type:image/pjpeg\r\n\r\n");
			out.write(sb.toString().getBytes());

			DataInputStream in = new DataInputStream(context.getResources()
					.getAssets().open(fileName));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes());
			in.close();
			out.write(end_data);
			out.flush();
			out.close();

			InputStream response = conn.getInputStream();

			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(response));
			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}
			System.out.println(builder.toString());
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		return builder.toString();
	}

	public static void postFile(String url, String filePath)
			throws ParseException, IOException {

		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

		HttpPost httppost = new HttpPost(url);
		File file = new File(filePath);

		FileEntity reqEntity = new FileEntity(file, "binary/octet-stream");

		httppost.setEntity(reqEntity);
		reqEntity.setContentType("binary/octet-stream");
		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());
		if (resEntity != null) {
			System.out.println(EntityUtils.toString(resEntity));
		}

		if (resEntity != null) {
			resEntity.consumeContent();
		}

		httpclient.getConnectionManager().shutdown();

	}
}

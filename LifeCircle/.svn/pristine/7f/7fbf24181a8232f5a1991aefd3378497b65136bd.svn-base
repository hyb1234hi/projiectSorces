package com.sinaleju.lifecircle.app.service.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.service.DBService;
import com.sinaleju.lifecircle.app.utils.LogUtils;

@SuppressLint("UseSparseArrays")
public class DSMerchantInsert extends DBService {
	private static final int TYPE_CATE = 0;
	private static final int TYPE_SUBCATE = 1;
	// private static final int TYPE_CITY = 1;
	// private static final int TYPE_TOWN = 2;
	private SQLiteDatabase db = null;

	public DSMerchantInsert(SQLiteDatabase db) {
		this.db = db;
	}

	@Override
	protected Object onExecute(Context context) throws Exception {
		readFileToInsertTable(
				context.getResources().openRawResource(R.raw.merchant_category),
				"merchantcategorybean", TYPE_CATE);
		readFileToInsertTable(
				context.getResources().openRawResource(
						R.raw.merchant_subcategory), "merchantsubcategorybean",
				TYPE_SUBCATE);
		return null;
	}

	private String s = "";

	private String readFileToInsertTable(InputStream is, String tableName,
			int type) {
		BufferedReader bufferReader = null;
		String tempStr = null;
		StringBuffer sb = null;
		try {
			bufferReader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));
			sb = new StringBuffer();
			long time = System.currentTimeMillis();

			LogUtils.e(TAG, "start at " + time);
			AndroidDatabaseConnection androidDatabaseConnection = new AndroidDatabaseConnection(
					db, true);
			androidDatabaseConnection.setAutoCommit(false);
			while ((tempStr = bufferReader.readLine()) != null) {
				
				//check parent needy
				String parentStr = ")";
				if (type == TYPE_SUBCATE) {
					parentStr = ",`parent_id`)";
				}
				tempStr=tempStr.substring(0,tempStr.length()-1)+";";
				//execute
				androidDatabaseConnection.executeStatement("INSERT INTO `"
						+ tableName + "` (`id`, `name`" + parentStr + " VALUES"
						+ tempStr,
						AndroidDatabaseConnection.DEFAULT_RESULT_FLAGS);

			}

			androidDatabaseConnection.commit(null);
			
			long endtime = System.currentTimeMillis();
			LogUtils.e(TAG, "end at " + time + "----" + (endtime - time) + "毫秒");

		} catch (FileNotFoundException e) {
			LogUtils.e(TAG, "file not found ", e);
		} catch (IOException e) {
			LogUtils.e(TAG, "IO", e);
		} catch (SQLException e) {
			LogUtils.e(TAG, "SQLException", e);
		} catch (java.sql.SQLException e) {
			LogUtils.e(TAG, "java.sql.SQLException", e);
		} finally {
			if (bufferReader != null) {
				try {
					bufferReader.close();
				} catch (IOException e) {
					e.getStackTrace();
				}
			}

		}
		return sb.toString();
	}
}

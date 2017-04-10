package com.sinaleju.lifecircle.app.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantCategoryBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantSubcategoryBean;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.service.db.DSMerchantInsert;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "lifecircle.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "DatabaseOpenHelper";
	private Context mContext;
	
	private Dao<UserBean,Integer> mUserBeanDao = null;
	private Dao<CommunityBean,Integer> mCommunityBeanDao = null;
	private Dao<MerchantCategoryBean,Integer> mMerchantCategoryBeanDao = null;
	private Dao<MerchantSubcategoryBean,Integer> mMerchantSubcategoryBeanDao = null;
	
	
	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
		this.mContext = context;
	}

	public DatabaseOpenHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, int configFile) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, UserBean.class);
			TableUtils.createTable(connectionSource, CommunityBean.class);
			TableUtils.createTable(connectionSource, MerchantCategoryBean.class);
			TableUtils.createTable(connectionSource, MerchantSubcategoryBean.class);
			
		} catch (SQLException e) {
			LogUtils.e(TAG, "数据库建表出错", e);
		} catch (Exception e) {
			LogUtils.e(TAG, "数据库建表出错", e);
		}
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub

	}
	
	
	public void setup(SQLiteDatabase db) throws Exception{
		DSMerchantInsert ds = new DSMerchantInsert(db);
		ds.syncExecute(mContext);
	}
	
	
	/**
	 * UserBeanDao
	 * @return
	 */
	public Dao<UserBean,Integer> getUserBeanDao(){
		if(mUserBeanDao == null){
			try {
				mUserBeanDao = getDao(UserBean.class);
			} catch (SQLException e) {
				LogUtils.e(TAG, "get UserBeanDao error: ", e);
			}
		}
		return mUserBeanDao;
	}
	
	/**
	 * CommunityBeanDao
	 * @return
	 */
	public Dao<CommunityBean,Integer> getCommunityBeanDao(){
		if(mCommunityBeanDao == null){
			try {
				mCommunityBeanDao = getDao(CommunityBean.class);
			} catch (SQLException e) {
				LogUtils.e(TAG, "get UserBeanDao error: ", e);
			}
		}
		return mCommunityBeanDao;
	}
	
	/**
	 * mMerchantCategoryBeanDao
	 * @return
	 */
	public Dao<MerchantCategoryBean,Integer> getMerchantCategoryBeanDao(){
		if(mMerchantCategoryBeanDao == null){
			try {
				mMerchantCategoryBeanDao = getDao(MerchantCategoryBean.class);
			} catch (SQLException e) {
				LogUtils.e(TAG, "get mMerchantCategoryBeanDao error: ", e);
			}
		}
		return mMerchantCategoryBeanDao;
	}
	
	/**
	 * mMerchantSubcategoryBeanDao
	 * @return
	 */
	public Dao<MerchantSubcategoryBean,Integer> getMerchantSubcategoryBeanDao(){
		if(mMerchantSubcategoryBeanDao == null){
			try {
				mMerchantSubcategoryBeanDao = getDao(MerchantSubcategoryBean.class);
			} catch (SQLException e) {
				LogUtils.e(TAG, "get mMerchantSubcategoryBeanDao error: ", e);
			}
		}
		return mMerchantSubcategoryBeanDao;
	}

}

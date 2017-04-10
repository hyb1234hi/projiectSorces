package com.sinaleju.lifecircle.app.database;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantCategoryBean;
import com.sinaleju.lifecircle.app.database.entity.MerchantSubcategoryBean;
import com.sinaleju.lifecircle.app.database.entity.UserBean;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	private static final Class<?> classes[] = new Class[] { UserBean.class,
			CommunityBean.class, MerchantCategoryBean.class,
			MerchantSubcategoryBean.class };

	public static void main(String[] args) {

		System.out.println("------main--------");

		try {
			writeConfigFile("ormlite_config.txt", classes);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

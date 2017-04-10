package com.sinaleju.lifecircle.app.database.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.model.impl.I_MODEL;

public abstract class AbstractBaseBean implements Serializable,I_MODEL {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2156405429461828363L;

	@SuppressWarnings("unchecked")
	public <T> void delete(Dao<T, Integer> dao, DatabaseOpenHelper helper)
			throws SQLException {
		dao.delete((T) this);
	}

	@SuppressWarnings("unchecked")
	public <T> void create(Dao<T, Integer> dao, DatabaseOpenHelper helper)
			throws SQLException {
		dao.create((T) this);
	}

	@SuppressWarnings("unchecked")
	public <T> void update(Dao<T, Integer> dao, DatabaseOpenHelper helper)
			throws SQLException {
		dao.update((T) this);
	}

	@SuppressWarnings("unchecked")
	public <T> void updateOrCreate(Dao<T, Integer> dao,
			DatabaseOpenHelper helper) throws SQLException {
		dao.createOrUpdate((T) this);
	}

	@SuppressWarnings("unchecked")
	public <T> void refresh(Dao<T, Integer> dao, DatabaseOpenHelper helper)
			throws SQLException {
		dao.refresh((T) this);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> queryForAll(Dao<T, Integer> dao,
			DatabaseOpenHelper helper) throws SQLException {

		return dao.queryForAll();
	}

	@SuppressWarnings("unchecked")
	public static <T> T queryById(Dao<T, Integer> dao, int id,
			DatabaseOpenHelper helper) throws SQLException {

		return dao.queryForId(id);
	}

}

package com.sinaleju.lifecircle.app.database.entity;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sinaleju.lifecircle.app.customviews.picker.pickers.D_CommonImpl.D_Parent;
import com.sinaleju.lifecircle.app.database.impl.AbstractBaseBean;
import com.sinaleju.lifecircle.app.utils.LogUtils;

@DatabaseTable
public class MerchantCategoryBean extends AbstractBaseBean implements
		D_Parent<MerchantSubcategoryBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1635235460556434863L;
	private static final String TAG = "MerchantCategoryBean";
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String name;
	@ForeignCollectionField(eager=false)
	private ForeignCollection<MerchantSubcategoryBean> mChildren;

	private List<MerchantSubcategoryBean> mChildrenList = new LinkedList<MerchantSubcategoryBean>();

	public MerchantCategoryBean() {
		// no-args constructor
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ForeignCollection<MerchantSubcategoryBean> getmChildren() {
		return mChildren;
	}

	public List<MerchantSubcategoryBean> getmChildrenList() throws SQLException {

		if (mChildrenList.size() > 0)
			return mChildrenList;

		CloseableIterator<MerchantSubcategoryBean> itrator = mChildren
				.closeableIterator();
		while (itrator.hasNext()) {
			mChildrenList.add(itrator.next());
		}

		itrator.close();

		return mChildrenList;
	}

	@Override
	public List<MerchantSubcategoryBean> getDChildren() {
		List<MerchantSubcategoryBean> list = null;
		try {
			list = getmChildrenList();
		} catch (SQLException e) {
			LogUtils.e(TAG, "", e);
		}
		return list;
	}

	@Override
	public String getWName() {
		return getName();
	}

}

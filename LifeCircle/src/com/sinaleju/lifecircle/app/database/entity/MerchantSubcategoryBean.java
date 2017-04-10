package com.sinaleju.lifecircle.app.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sinaleju.lifecircle.app.customviews.picker.pickers.D_CommonImpl.D_Children;
import com.sinaleju.lifecircle.app.database.impl.AbstractBaseBean;

@DatabaseTable
public class MerchantSubcategoryBean extends AbstractBaseBean implements
		D_Children<MerchantCategoryBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1635235460556434863L;
	@DatabaseField(id = true, canBeNull = false)
	private int id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private MerchantCategoryBean parent;

	public MerchantSubcategoryBean() {
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

	public MerchantCategoryBean getParent() {
		return parent;
	}

	public void setParent(MerchantCategoryBean parent) {
		this.parent = parent;
	}

	@Override
	public MerchantCategoryBean getDParent() {
		return getParent();
	}

	@Override
	public String getWName() {
		return getName();
	}

}

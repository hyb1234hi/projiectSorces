package com.sinaleju.lifecircle.app.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sinaleju.lifecircle.app.database.impl.AbstractBaseBean;

@DatabaseTable
public class CommunityBean extends AbstractBaseBean{
	private static final long serialVersionUID = 6599825867496561047L;
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int cid;
	@DatabaseField
	private String name;
	
	@DatabaseField
	private int topicCount;
	
	@DatabaseField
	private int type; //1、正常小区 2、虚拟小区
	
	@DatabaseField(canBeNull=false,foreign=true,foreignAutoRefresh=true,foreignAutoCreate=true)
	private UserBean parent;
	
	private String property_name;
	private int property_id;
	
	public CommunityBean(){
		//no-args constructor
	}
	
	
	
	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public int getTopicCount() {
		return topicCount;
	}



	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}



	public int getId() {
		return id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserBean getParent() {
		return parent;
	}

	public void setParent(UserBean parent) {
		this.parent = parent;
	}


	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public int getProperty_id() {
		return property_id;
	}

	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}
	
}

package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TopicsList;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_TopicsList  extends MultiModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5132172360320019599L;

	private int  id;
	private int count;;
	private int addTime;
	private String name;
	public Model_TopicsList(){
		
	}

	public Model_TopicsList(int id, int count ,int addTime,String name) {
		this.id = id;
		this.count=count;
		this.addTime=addTime;
		this.name = name;
	}

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}



	public int getAddTime() {
		return addTime;
	}



	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Model_TopicsList other=(Model_TopicsList) o;
		return this.name.equals(other.name);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		// TODO Auto-generated method stub
		return Item_TopicsList.class;
	}

}

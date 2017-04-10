package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_Community;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_Community extends MultiModelBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3303157422861284015L;
	public Model_Community(String label){
		setmCommunityName(label);
	}
	public Model_Community(int id,String label,int topicCount){
		setId(id);
		setmCommunityName(label);
		setTopicCount(topicCount);
	}
	public Model_Community() {
		// TODO Auto-generated constructor stub
	}
	private String mCommunityName;
	private int id;//小区Id;
	private int cityId; //城市Id;
	private String alphabet;//小区首字母;
	private Double longitude;//小区坐标经度;
	private Double latitude;//小区坐标纬度;
	private int TopicCount;	
	private int type;//1：实际小区;2：虚拟小区
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTopicCount() {
		return TopicCount;
	}
	public void setTopicCount(int topicCount) {
		TopicCount = topicCount;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_Community.class;
	}
	public String getmCommunityName() {
		return mCommunityName;
	}
	public void setmCommunityName(String mCommunityName) {
		this.mCommunityName = mCommunityName;
	}
	public String getAlphabet() {
		return alphabet;
	}
	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//	@Override
//	public boolean equals(Object o) {
//		// TODO Auto-generated method stub
//		Model_Community other=(Model_Community) o;
//		return this.mCommunityName.equals(other.getmCommunityName());
//	//	return super.equals();
//	}
//	@Override
//	public int hashCode() {
//		// TODO Auto-generated method stub
//		return  Integer.valueOf(this.id);
//	}
	
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	

}

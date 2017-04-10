package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_AttentionUserInfo;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_AttentionUserInfo extends MultiModelBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7513577956826409496L;

	private int id ;
	private int isOAth;
	private String url = null;
	private String name=null;
	private String nickName=null;
	private String type=null;
	private int send_status;
	public Model_AttentionUserInfo(){
		
	}
	public Model_AttentionUserInfo(int id , int isOAth,String url,String nickName,String type, int send_status){
		this.id=id;
		this.isOAth=isOAth;
		this.url=url;
		this.nickName=nickName;
		this.type=type;
		this.send_status=send_status;
	}
	
	

	public int getSend_status() {
		return send_status;
	}
	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getIsOAth() {
		return isOAth;
	}
	public void setIsOAth(int isOAth) {
		this.isOAth = isOAth;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		// TODO Auto-generated method stub
		return Item_AttentionUserInfo.class;
	}

}

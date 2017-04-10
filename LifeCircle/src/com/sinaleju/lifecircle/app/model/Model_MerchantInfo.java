package com.sinaleju.lifecircle.app.model;


import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_MerchantInfo extends MultiModelBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7513577956826409496L;

	private int id ;
	private String logoUrl = null; //商家logo地址
	private String webUrl=null;//商家主页地址
	private String name=null;//商家名称
	private String intro=null;//商家简介
	public Model_MerchantInfo(){
		
	}
	public Model_MerchantInfo(int id , String  logoUrl,String name,String intro,String webUrl){
		this.id=id;
		this.logoUrl=logoUrl;
		this.name=name;
		this.intro=intro;
		this.webUrl=webUrl;
	}
	
	


	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getlogoUrl() {
		return logoUrl;
	}

	public void setlogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		// TODO Auto-generated method stub
		return null;
	}

}

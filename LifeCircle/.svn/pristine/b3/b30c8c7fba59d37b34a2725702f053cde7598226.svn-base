package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;

public class RSGetMerchantList extends StringRS {

	Map<String, String> params;
	
	public RSGetMerchantList(){
		//no-args constructor
	}
	// type:商家类型；lastIndexId: 上页查询结果的最小ID，默认0，即从第一条查起，size: 每页显示条数
	public RSGetMerchantList(String type,int lastIndexId,int size){
		params = new HashMap<String, String>();
		params.put("type", type);
		if(lastIndexId!=-1){
			params.put("page", lastIndexId+"");			
		}
		if(size!=-1){
			params.put("size", size+"");			
		}
	}
	
	public void setParams(Map<String, String> params){
		this.params = params;
	}
	
	@Override
	public Map<String, String> getUsingParams() {
		return params;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_MERCHANT_QXZG_LIST;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}

}

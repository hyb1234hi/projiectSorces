package com.sinaleju.lifecircle.app.service.remote_impl.entity;

import java.util.HashMap;
import java.util.Map;

import com.sinaleju.lifecircle.app.service.remote_impl.RemoteConst;
import com.sinaleju.lifecircle.app.service.remote_impl.StringRS;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class RSTrends extends StringRS {
	// community_id int(3) 是 小区 id 默认是该用户最新加入的小区 （前提是登录状态下）
	// user_id string(32) 否 用户id
	// last_id int 上次请求的最后一条id
	// page_size int 否 每页数据量 默认10
	// owner_type Int 否 用户类型 (个人，商家，物业)
	// cate String 否 用户所属分类 （如：商家：美食，休闲娱乐 购物。。。。。）多选使用逗号分隔的分类值 如：2,3,4
	// audit_type int 否 用户是否认证 0 否 1 是 默认不区分
	// follow_status Int 否 过滤关注状态 0 不过滤 1 我关注的

	private int commmunity_id;
	private int user_id;
	private int last_id;
	private int page_size;
	private int owner_type;
	private int type;
	private String cate;
	private int audit_type;
	private int follow_status;
	public static final int TYPE_HOT = 2;
	public static final int TYPE_TIME = 1;
	private static final String TAG = "RSTrends";
	private int page;//this is for TYPE_HOT

	public RSTrends(int commmunity_id, int last_id, int page_size, int type) {
		this(commmunity_id, last_id, page_size, type, NULL_INT, NULL_INT,
				NULL_INT, NULL_INT, null);
	}
	public RSTrends(int commmunity_id, int last_id, int page_size, int type,int user_id) {
		this(commmunity_id, last_id, page_size, type, user_id, NULL_INT,
				NULL_INT, NULL_INT, null);
	}

	public RSTrends(int commmunity_id, int last_id, int page_size, int type,
			int user_id, int owner_type, int audit_type, int follow_status,
			String cate) {
		this(commmunity_id,last_id,page_size,type,user_id,owner_type,audit_type,follow_status,cate,StringRS.NULL_INT);
	}
	
	public RSTrends(int commmunity_id, int page_size, int type,
			int user_id, int owner_type, int audit_type, int follow_status,
			String cate,int page) {
		this(commmunity_id,StringRS.NULL_INT,page_size,type,user_id,owner_type,audit_type,follow_status,cate,page);
	}
	
	public RSTrends(int commmunity_id, int last_id, int page_size, int type,
			int user_id, int owner_type, int audit_type, int follow_status,
			String cate,int page) {
		this.commmunity_id = commmunity_id;
		this.last_id = last_id;
		this.page_size = page_size;
		this.type = type;

		this.user_id = user_id;
		this.owner_type = owner_type;
		this.audit_type = audit_type;
		this.follow_status = follow_status;

		this.cate = cate;
		
		this.page = page;
		toString();
	}

	@Override
	public Map<String, String> getUsingParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("community_id", commmunity_id + "");
		if (!isNullInt(last_id))
			map.put("last_id", last_id + "");
		if (!isNullInt(page_size))
			map.put("page_size", page_size + "");
		if (!isNullInt(type))
			map.put("type", type + "");
		if (!isNullInt(user_id))
			map.put("user_id", user_id + "");
		if (!isNullInt(owner_type))
			map.put("owner_type", owner_type + "");
		if (!isNullInt(audit_type))
			map.put("audit_type", audit_type + "");
		if (!isNullInt(follow_status))
			map.put("follow_status", follow_status + "");
		if(!isNullInt(page)){
			map.put("page", page+"");
		}
		
		if (cate != null)//修改过滤器条件
			map.put("tag", cate + "");
		return map;
	}

	@Override
	public String getCustomUrl() {
		return RemoteConst.URL_TRENDS;
	}

	@Override
	public int getMethod() {
		return StringRS.METHOD_GET;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		LogUtils.e(TAG, "commmunity_id :: " + commmunity_id + 
				" :: last_id :: " + last_id +   
				" :: page_size :: " + page_size + 
				" :: type :: " + type + 
				" :: user_id :: " + user_id + 
				" :: owner_type :: " + owner_type + 
				" :: audit_type :: " + audit_type + 
				" :: follow_status :: " + follow_status + 
				" :: cate :: " + cate + 
				" :: page :: " + page );
		return super.toString();
	}

}

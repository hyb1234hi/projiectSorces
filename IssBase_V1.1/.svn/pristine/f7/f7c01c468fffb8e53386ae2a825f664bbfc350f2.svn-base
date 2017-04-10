package com.iss.bean;

import java.io.Serializable;

import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;

public abstract class BaseBean<T> implements Serializable {
	private static final long serialVersionUID = -804757173578073135L;

	/**
	 * 将json对象转化为Bean实例
	 * @param jsonObj
	 * @return
	 */
	public abstract T parseJSON(JSONObject jsonObj);

	/**
	 * 将Bean实例转化为json对象
	 * @return
	 */
	public abstract JSONObject toJSON();

	/**
	 * 将数据库的cursor转化为Bean实例（如果对象涉及在数据库存取，需实现此方法）
	 * @param cursor
	 * @return
	 */
	public abstract T cursorToBean(Cursor cursor);

	/**
	 * 将Bean实例转化为一个ContentValues实例，供存入数据库使用（如果对象涉及在数据库存取，需实现此方法）
	 * @return
	 */
	public abstract ContentValues beanToValues();
	
	
	
	public T  parseJSON(Gson gson,String json){
		return (T)gson.fromJson(json, this.getClass());
	}
	
	
}
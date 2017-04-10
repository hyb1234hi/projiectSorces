package com.sinaleju.lifecircle.app.model;

/**
 * 
 * @author leju
 *可能认识的人bean
 */
public class RecognizedFriendsBean {

	private String id;
	private String login_name;
	private String type;
	private String display_name;
	private String display_pinyin;
	private String header;
	private String fans_num;
	private String follow_num;
	private String origin;
	private String is_auth;
	private String common;
	private String status = "0";//0 是未加关注  1是已加入关注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getDisplay_pinyin() {
		return display_pinyin;
	}
	public void setDisplay_pinyin(String display_pinyin) {
		this.display_pinyin = display_pinyin;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getFans_num() {
		return fans_num;
	}
	public void setFans_num(String fans_num) {
		this.fans_num = fans_num;
	}
	public String getFollow_num() {
		return follow_num;
	}
	public void setFollow_num(String follow_num) {
		this.follow_num = follow_num;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getIs_auth() {
		return is_auth;
	}
	public void setIs_auth(String is_auth) {
		this.is_auth = is_auth;
	}
	
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

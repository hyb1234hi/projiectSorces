package com.sinaleju.lifecircle.app.service.remote_impl;

/**
 * 
 * @author sunny.dai
 * 
 */
public interface RemoteConst {
	/**
	 * token 请求key
	 */
	String REQUEST_KEY_TOKEN = "token";
	/**
	 * key key
	 */
	String REQUEST_KEY_KEY = "keyid";
	/**
	 * key 值
	 */
	String REQUEST_KEY_VALUE = "10001";
	/**
	 * 密钥value
	 */
	String REQUEST_VALUE_SECRET = "ed47d3d12c2e1d4";
	/**
	 * 密钥key
	 */
	String REQUEST_KEY_SECRET = "app_key";

	/**
	 * 接口基础地址
	 */
	String URL_BASIC = "http://shq.leju.com/api/";
	// String URL_BASIC = "http://10.207.45.131:8080/api/";

	/** 手机邮箱注册接口 （POST） --1 */
	String URL_USER_REGIST = "user/regist.json";

	/** 手机注册获取验证码接口 （POST） --2 */
	String URL_USER_AUTOCODE = "user/authcode.json";

	/** 用户注册类别，包括个人和商户，以及子类 （POST） --3 */
	String URL_USER_USERTYPE = "user/usertype.json";

	/** 用户合作账号登录后，完善信息接口 （POST） --4 */
	String URL_USER_COMPLETEINFO = "user/authorization.json";

	/** 判断用户是否使用合作账号登录过（POST） --5 */
	String URL_USER_AUTHORIZELOGIN = "user/authorizelogin.json";

	/** 手机邮箱登录接口 （POST） --6 */
	String URL_USER_LOGIN = "user/login.json";

	/** 手机邮箱找回密码接口 （POST） --7 */
	String URL_USER_FINDPASSWORD = "user/findpass.json";

	/** 获取所有/指定小区关注列表接口 （POST） --8 */
	String URL_PERSON_FOLLOWLIST = "user/follow_list.json";

	/** 热门话题 接口 （POST） --9 */
	String URL_TOPIC_LIST = "topic/lists.json";
	/** 话题列表接口 （POST） --9 */
	String URL_TOPIC_INSERT = "topic/topic_list.json";

	/** 查询城市接口 （POST） --10 */
	String URL_COMMUNITY_FINDCITY = "joincommunity/citylist.json";

	/** 查询小区接口 （POST） --11 */
	String URL_COMMUNITY_FINDCOMMUNITY = "joincommunity/communitylist.json";

	/** 手机邮箱找回密码接口 （GET） --12 */
	String URL_TRENDS = "community/timeline.json";

	/** 信息发布 （POST） --13 */
	String URL_MESSAGE_PUBLISH = "message/publish.json";

	/** 上传照片 （POST） --14 */
	String URL_MESSAGE_UPLOAD = "message/upload.json";

	/** 评论消息接口 （POST） --15 */
	String URL_MESSAGE_COMMENT = "message/comment.json";

	/** 转发消息接口 （POST） --16 */
	String URL_MESSAGE_FORWARD = "message/forward.json";

	/** 手机邮箱找回密码接口 （GET） --17 */
	String URL_TRENDS_DETAIL_CONTENT = "message/detail.json";

	/** 评论列表 （GET） --18 */
	String URL_TRENDS_DETAIL_COMMENT_LIST = "message/comment_list.json";

	/** 转发列表（GET） --19 */
	String URL_TRENDS_DETAIL_DELIVER_LIST = "message/forward_list.json";

	/** 转发列表（POST） --20 */
	String URL_USER_INFO = "user/info.json";

	/** 获取加入小区列表 --21 */
	String URL_USER_ADDEDCOMMUNITY = "user/community_list.json";

	/** 添加新小区--22 */
	String URL_COMMUNITY_ADDNEWCOMMNUITY = "joincommunity/addcommunity.json";

	/** 删除小区--23 */
	String URL_COMMUNITY_DELETECOMMNUITY = "joincommunity/delcommunity.json";

	/** 服务详情（GET） --24 */
	String URL_SERVICE_DETAIL = "service/detail.json";

	/** 周边商家列表页（GET） --25 */
	String URL_RIGHT_BUSINESS_LIST = "merchant/around_search.json";

	/** 公共服务评价（POST） --26 */
	String URL_SERVICE_APPRAISE = "score/add_comment.json";

	/** 所有商业评价列表（GET） --27 */
	String URL_ALL_COMMENT = "score/score_list.json";

	/** 个人首页展示（GET） --28 */
	String URL_PERSONAL_INDEX = "personal/index.json";

	/** 邀请好友-获取列表（GET） --29 */
	String URL_INVITE_LIST = "user/invitelist.json";

	/** 6.14. 邀请好友-可能认识的人（GET） --29 */
	String URL_RECOGNIZED_FRIENDS = "user/searchknow.json";

	/** 加（取消）关注（GET） --29 */
	String URL_FOLLOW = "user/follow.json";

	/** (个人/商家/物业)时间轴（POST） --29 */
	String URL_USER_TIME_LINE = "user/time_line.json";

	/** 商户首页展示（POST） --30 */
	String URL_BUSINESS_INDEX = "merchant/index.json";

	/** 物业首页展示（POST） --31 */
	String URL_PROPERTY_INDEX = "property/index.json";

	/** 话题详情 --31 */
	String URL_TOPIC_DETAIL = "topic/detail.json";

	/** 编辑个人/商家/物业签名（POST） --32 */
	String URL_USER_SIGNATURE = "user/signature.json";

	/** yao请好友-搜索框 */
	String URL_SEARCH_FRIENDS_BY_KEY = "user/search.json";

	/** 加（取消）关注（POST） --33 */
	String URL_USER_FOLLOW = "user/follow.json";

	/** 修改个人资料（POST） --34 */
	String URL_PERSONAL_EDIT_INFO = "personal/edit_info.json";

	/** 商家认证申请（POST） --34 */
	String URL_BUSINESS_AUTH_APPLY = "merchant/check_request.json";

	/** 修改商家资料（POST） --35 */
	String URL_BUSINESS_EDIT_INFO = "merchant/edit_info.json";

	/** 修改物业资料（POST） --36 */
	String URL_PROPERTY_EDIT_INFO = "property/edit_info.json";

	/** 喜欢/取消喜欢（POST） --37 */
	String URL_MESSAGE_LIKE = "message/like.json";

	/** 小区首页top (GET)--38 */
	// String URL_HOME_PAGE_TOP = "community/communityInfoWithPro.json";
	String URL_HOME_PAGE_TOP = "community/communityinfo_with_pro.json";

	/** 商户物业-拨打电话次数（GET） --38 */
	String URL_TEL_HITS = "user/tel_hits.json";

	/** 修改密码 (POST)--39 */
	String URL_USER_CHANGE_PASSWORD = "user/change_passwd.json";

	/** 第三方用户修改密码同时创建用户 (POST)--40 */
	String URL_USER_CREAT_USER = "user/create_user.json";

	/** 第三方用户修改密码同时创建用户 (POST)--41 */
	String URL_USER_REMOVE_RELATION = "user/removerelation.json";

	/** 删除信息 (POST)--42 */
	String URL_MSG_DELETE = "message/deleteMsg.json";

	/** 取私信列表(POST)--43 */
	String URL_PERSONAL_CHAT_LIST = "chat/lists.json";

	/** 取通知列表(GET)--44 */
	String URL_PERSONAL_NOTICE_LIST = "notice/lists.json";

	/** 用户设置--44 */
	String URL_USER_SETUP = "user/user_setup.json";

	/** 用户反馈--45 */
	String URL_FEEDBACK = "feedback/add.json";

	/** 私信明细(GET)--46 */
	String URL_PERSONAL_CHAT_DETAIL = "chat/detail.json";

	/** 发送私信(POST)--47 */
	String URL_SEND_CHAT = "chat/publish.json";

	/** 版本检测--48 */
	String URL_VERSION_CHECK_VERSION = "version/check_version.json";

	/** 删除私信(GET)--49 */
	String URL_DELETE_CHAT = "chat/delete.json";
	/** 删除私信(GET)--50 */
	String URL_USER_VISITING_CARD = "user/show_header.json";

	/** 查询是否有未读的私信或通知--51 */
	String URL_NOTICE_CHAT_NOTICE_UNREAD = "notice/chat_notice_unread.json";

	/** 通过关键字查询消息(GET) --52 */
	String URL_SEARCH_MESSAGE = "community/search_message.json";

	/** 获取坐标周边小区与商家信息--53 */
	String URL_MESSAGE_AROUND_MAPDATA = "message/around_mapdata.json";

	/** 用户发信息时， 插入位置，搜索当前城市全部小区与商家--54 */
	String URL_MESSAGE_SEARCH_MAPDATA = "message/search_mapdata.json";

	/** 获取券享中国商家列表--55 */
	String URL_MERCHANT_QXZG_LIST = "merchant/qxzg_list.json";

	/** 获取虚拟小区（活动）的列表--56 */
	String URL_ACTIVITY_LIST = "community/activity_list.json";

	/** 获取公共主页信息--56 */
	String URL_NOTICE_MYTIP = "notice/mytip.json";

}

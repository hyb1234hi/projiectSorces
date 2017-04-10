package com.sinaleju.lifecircle.app.database.entity;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sinaleju.lifecircle.app.database.DatabaseOpenHelper;
import com.sinaleju.lifecircle.app.database.impl.AbstractBaseBean;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageImage;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageSpecials;
import com.sinaleju.lifecircle.app.utils.LogUtils;

@DatabaseTable
public class UserBean extends AbstractBaseBean {
	private static final long serialVersionUID = -1969387771102381580L;
	private static final String TAG = "UserBean";

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int uid;

	/** basic info */
	@DatabaseField
	private String login_name;// 登录账户
	@DatabaseField
	private String password;// 登录账户
	@DatabaseField
	private String display_name;// 个人昵称/商家（物业）名称
	@DatabaseField
	private String display_pinyin;// 昵称首字母
	@DatabaseField
	private String signature;// 签名
	@DatabaseField
	private String header;// 头像
	@DatabaseField
	private int type;// 用户类型（0个人、1商户、2物业）
	@DatabaseField
	private int fans_num;// 粉丝数
	@DatabaseField
	private int follow_num;// 关注数
	@DatabaseField
	private String background;// 背景图片
	@DatabaseField
	private String origin;// 注册来源 （sys系统注册 sina/tencent/renren/kaixin)
	@DatabaseField
	private int fid;// 授权帐号ID
	@DatabaseField
	private int is_auth;// 认证状态 0 未认证 1 待认证 2 已认证
	@DatabaseField
	private String sina_accesstoken;// 新浪token

	/** personal */
	@DatabaseField
	private int sex;// 性别0:保密，1男，2：女
	@DatabaseField
	private String birthday; // 生日
	@DatabaseField
	private String astro;// 星座
	@DatabaseField
	private String hometown;// 家乡
	@DatabaseField
	private String location;// 现居住地
	@DatabaseField
	private String qq;// QQ
	@DatabaseField
	private String mobile;// 手机
	@DatabaseField
	private String identities;// 身份证号

	/** merchant/property */
	@DatabaseField
	private String city_en;// 城市英文缩写
	@DatabaseField
	private String email;// 邮箱
	@DatabaseField
	private String category;// 一级分类
	@DatabaseField
	private String subcategory;// 二级分类
	@DatabaseField
	private String biz_method;// 经营方式
	@DatabaseField
	private String address;// 商家地址
	@DatabaseField
	private String phone;// 商家电话
	@DatabaseField
	private String normal_am_start;// 营业时间（上午开始时间）
	@DatabaseField
	private String normal_am_end;// 营业时间（上午结束时间）
	@DatabaseField
	private String normal_pm_start;// 营业时间（下午开始时间）
	@DatabaseField
	private String normal_pm_end;// '营业时间（下午结束时间）
	@DatabaseField
	private String halt_am_start;// 休息日（上午开始时间）
	@DatabaseField
	private String halt_am_end;// 休息日（上午结束时间）
	@DatabaseField
	private String halt_pm_start;// 休息日(下午开始时间)
	@DatabaseField
	private String halt_pm_end;// 休息日（下午结束时间）
	@DatabaseField
	private String traffic_routes;// 交通路线
	@DatabaseField
	private String per_capita;// 人均消费
	@DatabaseField
	private int city_id;// 城市（id）
	@DatabaseField
	private int area_id;// 地区(id)
	@DatabaseField
	private int circle_id;// 商圈(id)
	@DatabaseField
	private String longitude;// 经度
	@DatabaseField
	private String latitude;// 纬度
	@DatabaseField
	private String introduction;// 商家介绍
	@DatabaseField
	private int tel_hits;// 电话拨打次数
	@DatabaseField
	private String legal_person;// 法人身份证扫描件
	@DatabaseField
	private String license;// 营业执照扫描件
	@DatabaseField
	private String tax;// 税务登记证扫描件
	@DatabaseField
	private String license_num;// 营业执照号
	@DatabaseField
	private String contact_person;// 联系人
	@DatabaseField
	private String contact_tel;// 联系人电话
	@DatabaseField
	private String image_info;// 物业图片信息
	@DatabaseField
	private int category_id; // 一级分类id
	@DatabaseField
	private String companyName;// 物业图片信息
	@DatabaseField
	private int subcategory_id; // 二级分类id

	private Model_BusinessPageSpecials busSpecials;
	private Model_BusinessPageImage busImages;
	private String busLocationText;

	/** platformInfo */
	private String platform_id;// 授权平台UId,
	private String platform_name;// 授权平台名称,
	private String token;// 第三方账号token
	private String expiresTime;
	private String add_time;// 绑定时间,
	private int is_del;// 是否已删除 1是已删除 0 未删除,
	
	private Boolean isBindingSina;

	/** property */

	@ForeignCollectionField
	private ForeignCollection<CommunityBean> mCommunityCollection;

	private CommunityBean mCurrentCommunity = null;

	private List<CommunityBean> mCommunityBeans = null;

	public UserBean() {
		// no-args constructor
	}

	public static UserBean query(Dao<UserBean, Integer> dao)
			throws SQLException {
		return dao.queryBuilder().queryForFirst();
	}

	public static void create(DatabaseOpenHelper helper, int uid)
			throws SQLException {
		UserBean bean = new UserBean();
		bean.setUid(uid);
		helper.getUserBeanDao().create(bean);

	}

	public static void deleteAll(DatabaseOpenHelper helper) throws SQLException {
		helper.getUserBeanDao().delete(helper.getUserBeanDao().queryForAll());
		helper.getCommunityBeanDao().delete(
				helper.getCommunityBeanDao().queryForAll());
	}

	public Model_BusinessPageSpecials getBusSpecials() {
		return busSpecials;
	}

	public void setBusSpecials(Model_BusinessPageSpecials busSpecials) {
		this.busSpecials = busSpecials;
	}

	public Model_BusinessPageImage getBusImages() {
		return busImages;
	}

	public void setBusImages(Model_BusinessPageImage busImages) {
		this.busImages = busImages;
	}

	public Boolean getIsBindingSina() {
		return isBindingSina;
	}

	public void setIsBindingSina(Boolean isBindingSina) {
		this.isBindingSina = isBindingSina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return display_name;
	}

	public void setName(String name) {
		this.display_name = name;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getSubcategory_id() {
		return subcategory_id;
	}

	public void setSubcategory_id(int subcategory_id) {
		this.subcategory_id = subcategory_id;
	}

	public String getHeaderUrl() {
		return header;
	}

	public void setHeaderUrl(String headerUrl) {
		this.header = headerUrl;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setCoummnities(List<CommunityBean> list) {
		this.mCommunityBeans = list;
	}

	public List<CommunityBean> getCommunities() {
		if (mCommunityBeans == null) {
			try {
				mCommunityBeans = new LinkedList<CommunityBean>();

				CloseableIterator<CommunityBean> iterator = mCommunityCollection
						.closeableIterator();

				while (iterator.hasNext()) {
					mCommunityBeans.add(iterator.next());
				}

				try {
					iterator.close();
				} catch (SQLException e) {
					LogUtils.e(TAG, "closeIterator close error ", e);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return mCommunityBeans;
	}

	public int getCurrentCommunityId() {
		return getCurCommunity().getCid();
	}

	public CommunityBean getCurCommunity() {
		if (mCurrentCommunity == null) {
			if(getCommunities() != null && getCommunities().size() > 0)
				mCurrentCommunity = getCommunities().get(0);
		}
		return mCurrentCommunity;
	}

	public void addNewCommunity(CommunityBean bean) {
		mCommunityBeans.add(bean);
		mCurrentCommunity = bean;
	}

	public void removeCommunity(int id) {
		for (int i = 0; i < mCommunityBeans.size(); i++) {
			if (mCommunityBeans.get(i).getCid() == id) {
				if (mCurrentCommunity != null
						&& mCurrentCommunity.getCid() == id) {
					mCurrentCommunity = null;
				}
				mCommunityBeans.remove(i);
				break;
			}
		}

	}

	public void setCurrentCommunity(CommunityBean bean) {
		List<CommunityBean> beans = getCommunities();
		for (int i = 0; i < beans.size(); i++) {
			CommunityBean b = beans.get(i);
			if (b.getCid() == bean.getCid()) {
				this.mCurrentCommunity = b;
				break;
			}
		}
	}

	public void cache(DatabaseOpenHelper helper) throws SQLException {
		helper.getUserBeanDao().createOrUpdate(this);

		// clear all communityBeans in table
		helper.getCommunityBeanDao().delete(
				helper.getCommunityBeanDao().queryForAll());

		// insert new coummunity beans from 'mCommunityBeans" field which is set
		// with updating info
		List<CommunityBean> beans = getCommunities();
		for (int i = 0; i < beans.size(); i++) {
			CommunityBean bean = beans.get(i);
			bean.setParent(this);
			if (bean != null) {
				helper.getCommunityBeanDao().create(bean);
			}
		}
	}

	public void update(DatabaseOpenHelper helper) throws SQLException {
		helper.getUserBeanDao().update(this);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDisplay_pinyin() {
		return display_pinyin;
	}

	public void setDisplay_pinyin(String display_pinyin) {
		this.display_pinyin = display_pinyin;
	}

	public int getFans_num() {
		return fans_num;
	}

	public void setFans_num(int fans_num) {
		this.fans_num = fans_num;
	}

	public int getFollow_num() {
		return follow_num;
	}

	public void setFollow_num(int follow_num) {
		this.follow_num = follow_num;
	}

	public String getBackgroud() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getIs_auth() {
		return is_auth;
	}

	public void setIs_auth(int is_auth) {
		this.is_auth = is_auth;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAstro() {
		return astro;
	}

	public void setAstro(String astro) {
		this.astro = astro;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentities() {
		return identities;
	}

	public void setIdentities(String identities) {
		this.identities = identities;
	}

	public String getCity_en() {
		return city_en;
	}

	public void setCity_en(String city_en) {
		this.city_en = city_en;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getBiz_method() {
		return biz_method;
	}

	public void setBiz_method(String biz_method) {
		this.biz_method = biz_method;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTraffic_routes() {
		return traffic_routes;
	}

	public void setTraffic_routes(String traffic_routes) {
		this.traffic_routes = traffic_routes;
	}

	public String getPer_capita() {
		return per_capita;
	}

	public void setPer_capita(String per_capita) {
		this.per_capita = per_capita;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public int getArea_id() {
		return area_id;
	}

	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}

	public int getCircle_id() {
		return circle_id;
	}

	public void setCircle_id(int circle_id) {
		this.circle_id = circle_id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getTel_hits() {
		return tel_hits;
	}

	public void setTel_hits(int tel_hits) {
		this.tel_hits = tel_hits;
	}

	public String getLegal_person() {
		return legal_person;
	}

	public void setLegal_person(String legal_person) {
		this.legal_person = legal_person;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getLicense_num() {
		return license_num;
	}

	public void setLicense_num(String license_num) {
		this.license_num = license_num;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public String getBusLocationText() {
		return busLocationText;
	}

	public void setBusLocationText(String busLocationText) {
		this.busLocationText = busLocationText;
	}
	
	public boolean isJoinInCommunity(int cid){
		List<CommunityBean> beans = getCommunities();
		for(CommunityBean bean:beans){
			if(bean.getCid()==cid){
				return true;
			}
		}
		return false;
	}

	public void parseJson(String data) throws JSONException {
		JSONObject dataObj = new JSONObject(data);
		JSONObject userinfo = dataObj.getJSONObject("userinfo");

		// basic info
		setLogin_name(userinfo.optString("login_name"));
		setPassword(userinfo.optString("password"));
		setType(userinfo.optInt("type"));
		setSignature(userinfo.optString("signature"));
		setName(userinfo.optString("display_name"));
		setDisplay_pinyin(userinfo.optString("display_pinyin"));
		setHeaderUrl(userinfo.optString("header"));
		setFans_num(userinfo.optInt("fans_num"));
		setFollow_num(userinfo.optInt("follow_num"));
		setBackground(userinfo.optString("background"));
		setOrigin(userinfo.optString("origin"));
		setFid(userinfo.optInt("fid"));
		setIs_auth(userinfo.optInt("is_auth"));
		setSex(userinfo.optInt("sex"));
		setBirthday(userinfo.optString("birthday"));
		setAstro(userinfo.optString("astro"));
		setQq(userinfo.optString("qq"));
		setHometown(userinfo.optString("hometown"));
		setLocation(userinfo.optString("location"));
		setMobile(userinfo.optString("mobile"));
		setIdentities(userinfo.optString("identities"));

		/** merchant/property */
		setCity_en(userinfo.optString("city_en"));
		setEmail(userinfo.optString("email"));
		setCategory(userinfo.optString("category"));
		setSubcategory(userinfo.optString("subcategory"));
		setCategory_id(userinfo.optInt("category_id"));
		setSubcategory_id(userinfo.optInt("subcategory_id"));
		setBiz_method(userinfo.optString("biz_method"));
		setAddress(userinfo.optString("address"));
		setPhone(userinfo.optString("phone"));
		setNormal_am_start(userinfo.optString("normal_am_start"));
		setNormal_am_end(userinfo.optString("normal_am_end"));
		setNormal_pm_start(userinfo.optString("normal_pm_start"));
		setNormal_pm_end(userinfo.optString("normal_pm_end"));
		setHalt_am_start(userinfo.optString("halt_am_start"));
		setHalt_am_end(userinfo.optString("halt_am_end"));
		setHalt_pm_start(userinfo.optString("halt_pm_start"));
		setHalt_pm_end(userinfo.optString("halt_pm_end"));
		setTraffic_routes(userinfo.optString("traffic_routes"));
		setPer_capita(userinfo.optString("per_capita"));
		setCity_id(userinfo.optInt("city_id"));
		setArea_id(userinfo.optInt("area_id"));
		setCircle_id(userinfo.optInt("circle_id"));
		setLongitude(userinfo.optString("longitude"));
		setLatitude(userinfo.optString("latitude"));
		setIntroduction(userinfo.optString("introduction"));
		setTel_hits(userinfo.optInt("tel_hits"));
		setLegal_person(userinfo.optString("legal_person"));
		setLicense(userinfo.optString("license"));
		setTax(userinfo.optString("tax"));
		setLicense_num(userinfo.optString("license_num"));
		setContact_person(userinfo.optString("contact_person"));
		setContact_tel(userinfo.optString("contact_tel"));
		// setImage_info(userinfo.optString("image_info"));//不取了
		// setUid(userinfo.optInt("id"));

		JSONArray communityList = dataObj.getJSONArray("communitylist");

		List<CommunityBean> beans = new LinkedList<CommunityBean>();

		for (int i = 0; i < communityList.length(); i++) {
			JSONObject obj = communityList.optJSONObject(i);
			CommunityBean bean = new CommunityBean();
			bean.setCid(obj.optInt("id"));
			bean.setName(obj.optString("name"));
			bean.setTopicCount(obj.optInt("topic_num"));
			bean.setType(obj.optInt("type"));
			LogUtils.e(TAG, "communityType: "+bean.getType());
			bean.setParent(this);
			beans.add(bean);
			
		}

		setCoummnities(beans);

		// platformInfo

		JSONArray platformArray = dataObj.optJSONArray("platforminfo");

		int platformLength = platformArray.length();
		if (platformArray != null && platformLength > 0) {
			for (int i = 0; i < platformLength; i++) {
				JSONObject platformObj = platformArray.getJSONObject(i);
				int is_del = platformObj.getInt("is_del");
				if (is_del != 1) { // 未删除绑定状态
					String param = platformObj.getString("param");
					JSONObject paramObj = new JSONObject(param);
					long currentTime = System.currentTimeMillis() / 1000;
					//etAdd_time(platformObj.getString("add_time"));
					long addTime = Long.valueOf(platformObj.getString("add_time"));
					setExpiresTime(paramObj.getString("expires_in"));
					long expirsTime = Long.valueOf(getExpiresTime());
					if (currentTime - addTime < expirsTime) {
						// token未过期
						setPlatform_id(platformObj.getString("platform_id"));
						// LogUtils.i(TAG, "patform_id:   "+getPlatform_id());
						setPlatform_name(platformObj.getString("platform_name"));
						setIsBindingSina(true);
						setToken(paramObj.getString("access_token"));
						//setSina_accesstoken(getToken());		
						
					}

				} else {
					setIsBindingSina(false);
				}

			}
		}else{
			setIsBindingSina(false);
		}

	}

	//public Boolean getIsBindingSina() {
	//	return isBindingSina;
	//}

	//public void setIsBindingSina(Boolean isBindingSina) {
	//	this.isBindingSina = isBindingSina;
	//}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNormal_am_start() {
		return normal_am_start;
	}

	public void setNormal_am_start(String normal_am_start) {
		this.normal_am_start = normal_am_start;
	}

	public String getNormal_am_end() {
		return normal_am_end;
	}

	public void setNormal_am_end(String normal_am_end) {
		this.normal_am_end = normal_am_end;
	}

	public String getNormal_pm_start() {
		return normal_pm_start;
	}

	public void setNormal_pm_start(String normal_pm_start) {
		this.normal_pm_start = normal_pm_start;
	}

	public String getNormal_pm_end() {
		return normal_pm_end;
	}

	public void setNormal_pm_end(String normal_pm_end) {
		this.normal_pm_end = normal_pm_end;
	}

	public String getHalt_am_start() {
		return halt_am_start;
	}

	public void setHalt_am_start(String halt_am_start) {
		this.halt_am_start = halt_am_start;
	}

	public String getHalt_am_end() {
		return halt_am_end;
	}

	public void setHalt_am_end(String halt_am_end) {
		this.halt_am_end = halt_am_end;
	}

	public String getHalt_pm_start() {
		return halt_pm_start;
	}

	public void setHalt_pm_start(String halt_pm_start) {
		this.halt_pm_start = halt_pm_start;
	}

	public String getHalt_pm_end() {
		return halt_pm_end;
	}

	public void setHalt_pm_end(String halt_pm_end) {
		this.halt_pm_end = halt_pm_end;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getImage_info() {
		return image_info;
	}

	public void setImage_info(String image_info) {
		this.image_info = image_info;
	}

	public String getSina_accesstoken() {
		return sina_accesstoken;
	}

	public void setSina_accesstoken(String sina_accesstoken) {
		this.sina_accesstoken = sina_accesstoken;
	}

	public CommunityBean[] getSortCommunityArray() {
		List<CommunityBean> beans = getCommunities();
		if (beans != null && beans.size() > 0) {
			CommunityBean[] arr = new CommunityBean[beans.size()];
			arr[0] = getCurCommunity();
			int index = 1;
			for (int i = 0; i < arr.length && index < arr.length; i++) {
				CommunityBean bean = beans.get(i);
				if (bean == arr[0]) {
					continue;
				}
				arr[index] = bean;
				index++;
			}
			return arr;
		}
		return null;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPlatform_id() {
		return platform_id;
	}

	public void setPlatform_id(String platform_id) {
		this.platform_id = platform_id;
	}

	public String getPlatform_name() {
		return platform_name;
	}

	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(String expiresTime) {
		this.expiresTime = expiresTime;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public int getIs_del() {
		return is_del;
	}

	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}

}

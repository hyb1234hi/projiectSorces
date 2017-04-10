package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.Model_BusinessPageCount;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.model.Model_TrendsComment;
import com.sinaleju.lifecircle.app.model.Model_TrendsFollow;
import com.sinaleju.lifecircle.app.model.Model_TrendsIndexFollow;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg.Pic;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsgDeliver;
import com.sinaleju.lifecircle.app.model.Model_TrendsSimple;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_Trends implements MultiJSONParserBase<MultiModelBase> {

	private static final String TAG = "JSONParser_Trends";

	private boolean isShowHeadImage = true;

	private int mUserType = 0;

	public JSONParser_Trends() {
		this(true, 0);
	}

	public JSONParser_Trends(boolean isShowHeadImage, int mUserType) {
		super();
		this.isShowHeadImage = isShowHeadImage;
		this.mUserType = mUserType;
	}

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set) throws JSONException {

		List<MultiModelBase> data = new LinkedList<MultiModelBase>();

		JSONObject jsonData = new JSONObject(json);

		// set page counter info
		set.setNextPageStart(jsonData.optInt("smallest_id"));
		set.setPageCounterTotalValue(jsonData.optInt("total"));
		set.setPageLeft(jsonData.optInt("surplus"));

		// set user timeline count
		int curPage = 0;
		curPage = jsonData.optInt("curPage");
		if (!isShowHeadImage && mUserType != 0 && curPage == 1) {
			Model_BusinessPageCount count = new Model_BusinessPageCount();
			count.setCount(jsonData.optInt("count"));
			count.setProperty(mUserType == 2);
			data.add(count);
		}

		// json array
		JSONArray arr = jsonData.optJSONArray("list");
		if (arr == null || arr.length() == 0) {
			LogUtils.e(TAG, "list is null or size is zero ");
			return null;
		}

		// parse list
		for (int i = 0; i < arr.length(); ++i) {
			JSONObject obj = arr.getJSONObject(i);

			String type = obj.getString("type");
			long add_time = obj.optLong("add_time");
			Model_TrendsBase addModel = null;

			/** type check */
			if (type.equals(TrendsType.MSG.getStrValue()) 
					||type.equals(TrendsType.FOOD.getStrValue())
					||type.equals(TrendsType.ENTIRMENT.getStrValue())
					||type.equals(TrendsType.SHOP.getStrValue())
					||type.equals(TrendsType.BEAUTY.getStrValue())
					||type.equals(TrendsType.MARRAGE.getStrValue())
					||type.equals(TrendsType.CHILDREN.getStrValue())
					||type.equals(TrendsType.GYM.getStrValue())
					||type.equals(TrendsType.HOTEL.getStrValue())
					||type.equals(TrendsType.LIFE.getStrValue())
					||type.equals(TrendsType.FANGCHAN.getStrValue())
					||type.equals(TrendsType.JIAJIAO.getStrValue())
					) {// msg
				JSONObject retweetedObj = obj.optJSONObject("retweeted");
				if (retweetedObj == null) {
					addModel = creatMsg(obj, type);

				} else {
					// retweeted
					addModel = createMsgDeliver(obj, type, retweetedObj);
				}
				if (addModel != null) {
					addModel.setCommunity_name(obj.optString("community_name"));
				}
			} else {// trends

				if (type.equals(TrendsType.JOIN.getStrValue()) || type.equals(TrendsType.DIAL.getStrValue())
						|| type.equals(TrendsType.QUIT.getStrValue())
						|| type.equals(TrendsType.JION_LC.getStrValue())
						|| type.equals(TrendsType.CHANG_HEADER.getStrValue())) {
					addModel = createSimple(obj, type);
				} else if (type.equals(TrendsType.COMMENT.getStrValue())
						|| type.equals(TrendsType.CHANGE_SIGN.getStrValue())) {
					addModel = createComment(obj, type);
				} else if (type.equals(TrendsType.FOLLOW.getStrValue())) {
					addModel = createFollow(obj, type);
				}

			}

			if (addModel != null) {
				addModel.setShowHeader(isShowHeadImage);
				addModel.setUid(obj.optInt("user_id"));
				addModel.setmTag(obj.optString("tag"));
				addModel.setAddTime(add_time);
				// add
				data.add(addModel);
			}

		}
		// }
		return data;
	}

	private Model_TrendsFollow createFollow(JSONObject obj, String type) {
		// 如果是关注类型的动态，由于个人商家的接口不返回 第一个 NAME类型的 spanText 只有 “关注了xxx”
		Model_TrendsFollow model = null;
		JSONArray arr = obj.optJSONArray("content_show");
		if (arr != null && arr.length() > 1) {
			if (arr.length() == 2) {
				model = new Model_TrendsIndexFollow();
			} else if (arr.length() == 3) {
				model = new Model_TrendsFollow();
			}
			if (model != null) {
				model.setTrendsType(type);
				parseContentShow(obj, model);
			}
		}
		return model;
	}

	/**
	 * 创建一个评论model
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	protected Model_TrendsComment createComment(JSONObject obj, String type) {
		Model_TrendsComment model = new Model_TrendsComment();
		model.setTrendsType(type);
		String comment = "";
		if (type.equals(TrendsType.CHANGE_SIGN.getStrValue())) {
			model.setmUserType(mUserType);
			comment = obj.optString("content");
		} else if (type.equals(TrendsType.COMMENT.getStrValue())) {
			parseContentShow(obj, model);
			comment = obj.optString("service_comment");
		}
		model.setComment(comment);
		return model;
	}

	/**
	 * 创建一个简单的model
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	protected Model_TrendsSimple createSimple(JSONObject obj, String type) {
		Model_TrendsSimple model = new Model_TrendsSimple();
		model.setTrendsType(type);
		if (type.equals(TrendsType.JION_LC.getStrValue())) {
			model.setText(obj.optString("content"));
		} else if (type.equals(TrendsType.CHANG_HEADER.getStrValue())) {
			model.setText(obj.optString("content"));
		} else {
			parseContentShow(obj, model);
		}
		return model;
	}

	/**
	 * 创建一个转发 model
	 * 
	 * @param obj
	 * @param type
	 * @param retweetedObj
	 * @return
	 */
	private Model_TrendsMsgDeliver createMsgDeliver(JSONObject obj, String type,
			JSONObject retweetedObj) {
		Model_TrendsMsgDeliver model = new Model_TrendsMsgDeliver();
		model.setShowHeadImage(isShowHeadImage);
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);

		Model_TrendsMsg retweetedModel = new Model_TrendsMsg();
		parseToTrendsMsg(retweetedObj, retweetedModel);

		model.setDeliveredMsg(retweetedModel);
		return model;
	}

	/**
	 * 创建一个消息model
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	private Model_TrendsMsg creatMsg(JSONObject obj, String type) {
		Model_TrendsMsg model = new Model_TrendsMsg();
		model.setShowHeadImage(isShowHeadImage);
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);
		return model;
	}

	protected void parseToTrendsMsg(JSONObject json, Model_TrendsMsg m) {

		// basic setting
		//
		m.setAgreeCount(json.optInt("like_num"));
		m.setCommentCount(json.optInt("comment_num"));
		m.setDeliveredCount(json.optInt("forward_num"));
		m.setLike(json.optInt("is_like"));
		//
		m.setCommunity_id(json.optInt("community_id"));
		m.setCommunity_name(json.optString("community_name"));
		m.setTrends_id(json.optInt("id"));

		// msg_id
		m.setMsg_id(json.optInt("message_id"));

		// content
		m.setMsgInfo(json.optString("content"));

		// location
		m.setLocation(json.optString("location"));
		// coordinate
		m.setCoordinate(json.optString("coordinate"));

		// content show
		SpanText[] spans = parseContentShow(json, null, m);
		if (spans != null)
			m.setSpanTexts(spans);

		// userinfo
		JSONObject userinfo = json.optJSONObject("user_info");
		if (userinfo != null) {
			String headerUrl = userinfo.optString("header");
			String name = userinfo.optString("display_name");
			int type = userinfo.optInt("type");
			int uid = userinfo.optInt("id");
			boolean vip = userinfo.optInt("is_auth") == 1 ? true : false;

			m.setUid(uid);
			m.setU_type(type);
			m.setName(name);
			m.setHeadPortraitUrl(headerUrl);
			m.setVIP(vip);
		}

		// pics
		JSONArray pics = json.optJSONArray("pic");
		if (pics != null && pics.length() > 0) {
			Pic[] msgPics = new Pic[pics.length()];
			for (int i = 0; i < pics.length(); i++) {
				JSONObject p = pics.optJSONObject(i);
				JSONObject pb = p.optJSONObject("pic_big");// big image
				JSONObject pm = p.optJSONObject("pic_middle");// mid image
				JSONObject ps = p.optJSONObject("pic_small");// small image
				msgPics[i] = new Pic();

				msgPics[i].setUrlBig(pb.optString("url"));
				msgPics[i].setUrlMiddle(pm.optString("url"));
				msgPics[i].setUrlSmall(ps.optString("url"));
			}
			m.setPics(msgPics);
		}

	}

	protected SpanText[] parseContentShow(JSONObject json, Model_TrendsSimple model) {
		return parseContentShow(json, model, null);
	}

	protected SpanText[] parseContentShow(JSONObject json, Model_TrendsSimple simpleModel,
			Model_TrendsMsg msgModel) {

		// 如果是空 返回空字符串
		if (json == null || json.equals(""))
			return new SpanText[] { new SpanText(SpanType.TEXT, 0, "") };

		// 解析
		JSONArray arr = json.optJSONArray("content_show");
		SpanText[] spans = null;
		int nameCount = 0;

		// content show parse and set
		if (arr != null && arr.length() != 0) {
			spans = new SpanText[arr.length()];

			for (int i = 0; i < arr.length(); ++i) {

				//
				JSONObject j_span = arr.optJSONObject(i);

				// get
				int type = j_span.optInt("type");
				int item_id = j_span.optInt("item_id");
				String text = j_span.optString("text");

				// init
				SpanText span = new SpanText();

				// Name的时候回返回header，这个时候需要用到参数model 将header放到model中供item去显示
				if (type == SpanType.NAME.value() && simpleModel != null) {
					// 下面的usertype设置全是在trendSpan中的 跟头像没关系
					if (nameCount == 0) {
						String headPortraitUrl = j_span.optString("header");
						simpleModel.setHeadPortraitUrl(headPortraitUrl);
						span.setUserType(j_span.optInt("user_type"));
						simpleModel.setU_type(j_span.optInt("user_type"));
						nameCount = 1;
					} else if (nameCount == 1) {
						if (simpleModel.getTrendsType().equals(TrendsType.FOLLOW)) {
							String flagUrl = j_span.optString("header");
							simpleModel.setFlagUrl(flagUrl);
							span.setUserType(j_span.optInt("user_type"));
							simpleModel.setFlag_type(j_span.optInt("user_type"));
							simpleModel.setFlagUid(item_id);
							nameCount = 2;
						}
					}
				}

				// at和name的时候 会返回userType 跟上面的一样
				if (type == SpanType.NAME.value() || type == SpanType.AT.value()) {
					span.setUserType(j_span.optInt("user_type"));
				}

				// 如果是消息 并且 是纯文本的 设置纯文本的item_id就是msg_id 点击纯文本可以调到详情页
				if (type == SpanType.TEXT.value() && msgModel != null) {
					item_id = msgModel.getMsg_id();
					span.setTag(msgModel);
				}

				// set other
				span.setSpanType(type);
				span.setText(text);
				span.setItem_id(item_id);// 如果msgId!=-1说明是

				//
				spans[i] = span;

			}

		}
		if (simpleModel != null)
			simpleModel.setSpanTexts(spans);
		return spans;
	}
}

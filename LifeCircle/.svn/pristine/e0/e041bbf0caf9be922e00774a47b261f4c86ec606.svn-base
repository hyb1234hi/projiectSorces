package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.model.Model_NoticeComment;
import com.sinaleju.lifecircle.app.model.Model_NoticeDelive;
import com.sinaleju.lifecircle.app.model.Model_NoticeMsg;
import com.sinaleju.lifecircle.app.model.Model_NoticeSystem;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg.Pic;
import com.sinaleju.lifecircle.app.model.Model_TrendsSimple;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_PersonalNotice implements MultiJSONParserBase<MultiModelBase> {

	private static final String TAG = "JSONParser_PersonalNotice";

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set) throws JSONException {

		List<MultiModelBase> data = new LinkedList<MultiModelBase>();

		JSONObject jsonData = new JSONObject(json);

		// set page counter info
		set.setNextPageStart(jsonData.optInt("smallest_id"));
		set.setPageCounterTotalValue(jsonData.optInt("total"));
		set.setPageLeft(jsonData.optInt("surplus"));

		// json array
		JSONArray arr = jsonData.optJSONArray("list");
		if (arr == null || arr.length() == 0) {
			LogUtils.e(TAG, "list is null or size is zero ");
			return null;
		}

		// parse list
		for (int i = 0; i < arr.length(); ++i) {
			JSONObject root = arr.getJSONObject(i);
			int type = 0;
			type = root.getInt("type");
			long add_time = root.optLong("add_time");
			if (type > 0) {
				type += 17;
			}

			Model_TrendsBase addModel = null;
			JSONObject obj = root.optJSONObject("msg_info");
			/** type check */
			
			LogUtils.e(TAG, "type :: " + type);
			if (type == TrendsType.NOTICE_COMM.getValue()) {// msg
				if (obj != null) {
					JSONObject retweetedObj = obj.optJSONObject("retweeted");
					JSONObject commObj = root.optJSONObject("comment_info");
					addModel = createNoticeComment(obj, type, retweetedObj, commObj);
				}
			} else if (type == TrendsType.NOTICE_AT.getValue()
					|| type == TrendsType.NOTICE_LIKE.getValue()) {
				if (obj != null) {
					JSONObject retweetedObj = obj.optJSONObject("retweeted");
					if (retweetedObj == null) {
						addModel = creatMsg(obj, type, root);
					} else {
						addModel = createMsgDeliver(obj, type, retweetedObj, root);
					}
				}
			} else if (type == TrendsType.NOTICE_ATTEN.getValue()) {
				addModel = createSimple(root, type);
			} else if (type == TrendsType.NOTICE_SYSTEM.getValue()) {
				addModel = createSystem(root, type);
			}

			if (addModel != null) {
				JSONObject community = root.optJSONObject("community_info");
				if (community != null) {
					addModel.setCommunity_name(community.optString("name"));
				}
				if (obj != null) {
					addModel.setUid(obj.optInt("user_id"));
				}
				addModel.setAddTime(add_time);
				data.add(addModel);
			}

		}
		return data;
	}

	private Model_NoticeSystem createSystem(JSONObject obj, int type) {
		Model_NoticeSystem system = new Model_NoticeSystem();
		if (obj != null) {
			system.setmSystemMsg(obj.optString("content"));
		}
		return system;
	}

	/**
	 * 创建一个简单的model
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	protected Model_TrendsSimple createSimple(JSONObject obj, int type) {
		Model_TrendsSimple model = new Model_TrendsSimple();
		model.setTrendsType(type);
		parseContentShow(obj, model);
		return model;
	}

	private Model_NoticeComment createNoticeComment(JSONObject obj, int type,
			JSONObject retweetedObj, JSONObject commObj) {
		Model_NoticeComment model = new Model_NoticeComment();
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);

		if (retweetedObj != null) {
			Model_NoticeMsg retweetedModel = new Model_NoticeMsg();
			parseToTrendsMsg(retweetedObj, retweetedModel);
			model.setDeliveredMsg(retweetedModel);
		}

		if (commObj != null) {
			Model_NoticeMsg commdModel = new Model_NoticeMsg();
			parseToTrendsMsg(commObj, commdModel);
			model.setmCommentMsg(commdModel);
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
	private Model_NoticeDelive createMsgDeliver(JSONObject obj, int type, JSONObject retweetedObj,
			JSONObject root) {
		Model_NoticeDelive model = new Model_NoticeDelive();
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);

		Model_NoticeMsg retweetedModel = new Model_NoticeMsg();
		parseToTrendsMsg(retweetedObj, retweetedModel);

		Model_TrendsSimple simple = new Model_TrendsSimple();
		simple.setTrendsType(type);
		parseContentShow(root, simple);

		model.setDeliveredMsg(retweetedModel);
		model.setSimple(simple);
		return model;
	}

	/**
	 * 创建一个消息model
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	private Model_NoticeMsg creatMsg(JSONObject obj, int type, JSONObject root) {
		Model_NoticeMsg model = new Model_NoticeMsg();
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);

		Model_TrendsSimple simple = new Model_TrendsSimple();
		simple.setTrendsType(type);
		parseContentShow(root, simple);

		model.setSimple(simple);
		return model;
	}

	protected void parseToTrendsMsg(JSONObject json, Model_TrendsMsg m) {

		// basic setting
		m.setLike(json.optInt("is_like"));

		//
		m.setCommunity_id(json.optInt("community_id"));
		m.setCommunity_name(json.optString("community_name"));
		m.setTrends_id(json.optInt("id"));

		// msg_id
		m.setMsg_id(json.optInt("message_id"));

		// content
		m.setMsgInfo(json.optString("content"));

		// content show
		SpanText[] spans = parseContentShow(json, null, m.getMsg_id());
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
		return parseContentShow(json, model, AppConst.NULL_INT);
	}

	protected SpanText[] parseContentShow(JSONObject json, Model_TrendsSimple model, int msgId) {

		// 如果是空 返回空字符串
		if (json == null || json.equals(""))
			return new SpanText[] { new SpanText(SpanType.TEXT, 0, "") };

		// 解析
		JSONArray arr = null;
		if (msgId == AppConst.NULL_INT) {
			arr = json.optJSONArray("notice_info");
		} else {
			arr = json.optJSONArray("content_show");
		}

		SpanText[] spans = null;

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
				if (type == SpanType.NAME.value() && model != null) {
					// TODO://下面的usertype设置全是在trendSpan中的 跟头像没关系
					String headPortraitUrl = j_span.optString("header");
					model.setHeadPortraitUrl(headPortraitUrl);
					span.setUserType(j_span.optInt("user_type"));
					model.setU_type(j_span.optInt("user_type"));
					String flagUrl = j_span.optString("header");
					model.setFlagUrl(flagUrl);
					model.setFlag_type(j_span.optInt("user_type"));
					model.setFlagUid(item_id);
				}

				// at和name的时候 会返回userType 跟上面的一样
				if (type == SpanType.NAME.value() || type == SpanType.AT.value()) {
					span.setUserType(j_span.optInt("user_type"));
				}

				// 如果是消息 并且 是纯文本的 设置纯文本的item_id就是msg_id 点击纯文本可以调到详情页
				if (type == SpanType.TEXT.value() && msgId != AppConst.NULL_INT) {
					item_id = msgId;
				}

				// set other
				span.setSpanType(type);
				span.setText(text);
				span.setItem_id(item_id);// 如果msgId!=-1说明是

				//
				spans[i] = span;

			}

		}
		if (model != null)
			model.setSpanTexts(spans);
		return spans;
	}
}

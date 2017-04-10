package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailDeliver;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsDetailName;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;

public class JSONParser_TrendsDetailContent extends JSONParser_Trends {

	private Model_TrendsMsg mModel;

	public JSONParser_TrendsDetailContent() {
		// no-args constructor
	}

	public JSONParser_TrendsDetailContent(Model_TrendsMsg m) {
		this.mModel = m;
	}

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set) throws JSONException {
		List<MultiModelBase> data = new LinkedList<MultiModelBase>();
		if (mModel == null) {

			JSONObject dataObj = new JSONObject(json);

			JSONObject retweetedObj = dataObj.optJSONObject("retweeted");

			// item 0
			Model_TrendsDetailName nameModel = new Model_TrendsDetailName();
			JSONObject userinfo = dataObj.optJSONObject("user_info");
			nameModel.parse(userinfo);

			data.add(nameModel);

			// item 1
			if (retweetedObj != null) {
				Model_TrendsDetailDeliver model = createDetailMsgDeliver(dataObj, TrendsType.MSG.getStrValue(), retweetedObj);
				model.setHideTimeLine(true);
				data.add(model);
			} else {
				Model_TrendsDetailMsg model = creatDetailMsg(dataObj, TrendsType.MSG.getStrValue());
				model.setHideTimeLine(true);
				data.add(model);
			}
		}else{
			// item 0
			Model_TrendsDetailName nameModel = new Model_TrendsDetailName();
			nameModel.setHeader(mModel.getHeadPortraitUrl());
			nameModel.setModelType(mModel.getModelType());
			nameModel.setName(mModel.getName());
			nameModel.setType(mModel.getU_type());
			nameModel.setUid(mModel.getUid());
			nameModel.setVIP(mModel.isVIP());
			
			data.add(nameModel);
			
			// item 1
			data.add(mModel);
		}

		// item 2

		return data;
	}

	protected Model_TrendsDetailDeliver createDetailMsgDeliver(JSONObject obj, String type, JSONObject retweetedObj) {
		Model_TrendsDetailDeliver model = new Model_TrendsDetailDeliver();
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);

		Model_TrendsDetailMsg retweetedModel = new Model_TrendsDetailMsg();
		parseToTrendsMsg(retweetedObj, retweetedModel);

		model.setDeliveredDetailMsg(retweetedModel);
		return model;
	}

	protected Model_TrendsDetailMsg creatDetailMsg(JSONObject obj, String type) {
		Model_TrendsDetailMsg model = new Model_TrendsDetailMsg();
		model.setTrendsType(type);
		parseToTrendsMsg(obj, model);
		return model;
	}
}

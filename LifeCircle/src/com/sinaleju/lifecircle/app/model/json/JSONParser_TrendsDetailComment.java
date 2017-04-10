package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.Model_TrendsDetailComment;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;

public class JSONParser_TrendsDetailComment implements
		MultiJSONParserBase<MultiModelBase> {

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException {

		// data
		List<MultiModelBase> data = new LinkedList<MultiModelBase>();

		// obj
		JSONObject dataObj = new JSONObject(json);
		
		//set
		set.setNextPageStart(dataObj.optInt("smallest_id"));
		set.setPageCounterTotalValue(dataObj.optInt("total"));
		set.setPageLeft(dataObj.optInt("surplus"));
		

		// list
		JSONArray list = dataObj.getJSONArray("list");
		for (int i = 0; i < list.length(); ++i) {
			Model_TrendsDetailComment model = new Model_TrendsDetailComment();

//			// hide topline
//			if (i == 0 && set.size() == 2) {
//				model.setNeedTopLine(false);
//			}
			JSONObject obj = list.getJSONObject(i);
			JSONObject userinfo = obj.getJSONObject("user_info");
			if (userinfo == null)
				continue;

			// userinfo set
			model.setmCommentOwner(userinfo);
			// msgId
			model.setmMsgId(obj.optInt("id"));
			// time
			model.setTime(obj.optInt("add_time"));
			// spantext
			model.setSpanTexts(obj.optJSONArray("content_show"));

			data.add(model);

		}

		return data;
	}

}

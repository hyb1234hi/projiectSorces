package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.Model_CategoryLabel;
import com.sinaleju.lifecircle.app.model.Model_TopicsList;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_AddedCommunityList implements
		MultiJSONParserBase<MultiModelBase> {

	private static final String TAG = "JSONParser_AddedCommunityList";
	private final boolean TEST = false;
	public  List<MultiModelBase> data;

	// private Map<String, List<MultiModelBase>> mReferenceMap = new
	// HashMap<String, List<MultiModelBase>>();

	public JSONParser_AddedCommunityList() {
		init();
	}

	private void init() {
		data = new LinkedList<MultiModelBase>();
		// initTempList();
	}

	public List<MultiModelBase> getDateList() {
		return data;
	}

	// private void initTempList() {
	// List<MultiModelBase> near = new LinkedList<MultiModelBase>();
	// mReferenceMap.put("附近小区", near);
	// for (short i = 65; i <= 90; ++i) {
	// List<MultiModelBase> l = new LinkedList<MultiModelBase>();
	// char c = (char) i;
	// String letterStr = String.valueOf(c);
	// MultiModelBase model = new Model_CategoryLabel(letterStr);
	// l.add(model);
	// mReferenceMap.put(letterStr, l);
	// }
	// }

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException {

		LogUtils.i(TAG, json);

		if (TEST) {
			List<MultiModelBase> latestList = new LinkedList<MultiModelBase>();
			latestList.add(new Model_CategoryLabel("最近使用"));
			latestList.add(new Model_TopicsList(0, 10, 12345, "#古天乐#"));
			latestList.add(new Model_TopicsList(1, 10, 12345, "#郑爽#"));
			latestList.add(new Model_TopicsList(2, 10, 12345, "#快乐大本营#"));
			latestList.add(new Model_TopicsList(3, 10, 12345, "#郑媛媛#"));
			latestList.add(new Model_TopicsList(4, 10, 12345, "#停电#"));
			latestList.add(new Model_CategoryLabel("热门话题"));
			latestList.add(new Model_TopicsList(2, 10, 12345, "#快乐大本营#"));
			latestList.add(new Model_TopicsList(0, 10, 12345, "#古天乐#"));
			latestList.add(new Model_TopicsList(5, 10, 12345, "#新疆暴动#"));
			data.addAll(latestList);

		} else {

			JSONObject jsonObj = new JSONObject(json);

			// 添加最近使用话题
			List<MultiModelBase> latestList = new LinkedList<MultiModelBase>();
			latestList.add(new Model_CategoryLabel("最近使用"));

			// JSONObject dataObj=jsonObj.optJSONObject("data");
			// int curPage=dataObj.optInt("curPage");
			// int totalPage =dataObj.optInt("totalPage");
			JSONArray hottestArray = jsonObj.optJSONArray("hottest");
			for (int i = 0; i < hottestArray.length(); i++) {
				JSONObject listJson = hottestArray.optJSONObject(i);

				Model_TopicsList toplicModel = new Model_TopicsList();

				toplicModel.setId(listJson.optInt("id"));
				toplicModel.setName("#"+listJson.optString("name")+"#");
				
				toplicModel.setCount(listJson.optInt("count"));
				toplicModel.setAddTime(listJson.optInt("add_time"));

				latestList.add(toplicModel);
			}
			data.addAll(latestList);

			// 热门话题;
			List<MultiModelBase> newestList = new LinkedList<MultiModelBase>();
			newestList.add(new Model_CategoryLabel("热门话题"));

			JSONArray newestArray = jsonObj.optJSONArray("newest");

			for (int i = 0; i < newestArray.length(); i++) {
				JSONObject newestJson = newestArray.optJSONObject(i);
				Model_TopicsList newestToplicModel = new Model_TopicsList();

				newestToplicModel.setId(newestJson.optInt("id"));
				newestToplicModel.setName("#"+newestJson.optString("name")+"#");
				newestToplicModel.setCount(newestJson.optInt("count"));
				newestToplicModel.setAddTime(newestJson.optInt("add_time"));

				newestList.add(newestToplicModel);

			}
			data.addAll(newestList);
		}
		return data;
	}

}

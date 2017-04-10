package com.sinaleju.lifecircle.app.model.json;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.Model_CategoryLabel;
import com.sinaleju.lifecircle.app.model.Model_Community;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_CommunityChoose implements
		MultiJSONParserBase<MultiModelBase> {

	/*
	 * private static final String[] LETTERS = {"附近小区","A", "B", "C", "D", "E",
	 * "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
	 * "T", "U", "V", "W", "X", "Y", "Z" };
	 */

	private static final String TAG = "JSONParser_CommunityChoose";
	public static List<MultiModelBase> data;
	private Map<String, List<MultiModelBase>> mReferenceMap = new HashMap<String, List<MultiModelBase>>();

	public JSONParser_CommunityChoose() {
		init();
	}

	private void init() {
		data = new LinkedList<MultiModelBase>();
		initTempList();
	}

	private void initTempList() {
		// List<MultiModelBase> near = new LinkedList<MultiModelBase>();
		// mReferenceMap.put("附近小区", near);
		for (short i = 65; i <= 91; ++i) {
			List<MultiModelBase> l = new LinkedList<MultiModelBase>();

			char c = (char) i;
			String letterStr = String.valueOf(c);

			MultiModelBase model = new Model_CategoryLabel(letterStr);
			l.add(model);

			mReferenceMap.put(letterStr, l);

		}
	}

	private List<MultiModelBase> getListByLetter(String letter) {
		return mReferenceMap.get(letter);
	}

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException {

		LogUtils.i(TAG, json);
		// if (AppContext.TEST) {

		// add nearlist

		JSONObject jsonObj = new JSONObject(json);
		List<MultiModelBase> nearList = new LinkedList<MultiModelBase>();
		// 附近小区;
		JSONArray nearArray = jsonObj.optJSONArray("nearby");
		if (nearArray != null && nearArray.length() > 0) {
			nearList.add(new Model_CategoryLabel("附近小区"));
			for (int i = 0; i < nearArray.length(); i++) {
				JSONObject nearJson = nearArray.optJSONObject(i);
				Model_Community nearCommunity = new Model_Community();
				nearCommunity.setCityId(nearJson.optInt("city_id"));
				nearCommunity.setId(nearJson.optInt("id"));
				nearCommunity.setAlphabet(nearJson.optString("alphabet"));
				nearCommunity.setLongitude(nearJson.optDouble("longitude"));
				nearCommunity.setLatitude(nearJson.optDouble("latitude"));
				nearCommunity.setmCommunityName(nearJson.optString("name"));
				nearCommunity.setTopicCount(nearJson.optInt("topic_num"));
				nearCommunity.setType(nearJson.optInt("type"));
				nearList.add(nearCommunity);
			}

			// add to data
			data.addAll(nearList);
		}
		// 小区列表
		JSONObject listObj = jsonObj.optJSONObject("list");
		for (short i = 65; i < 91; i++) {
			char c = (char) i;
			String letter = String.valueOf(c);
			List<MultiModelBase> list = getListByLetter(letter);
			JSONArray communityArray = listObj.optJSONArray(letter);
			int length = communityArray.length();
			if (length > 0) {
				for (int j = 0; j < length; j++) {
					JSONObject commnityObj = communityArray.optJSONObject(j);
					Model_Community listCommunity = new Model_Community();
					listCommunity.setCityId(commnityObj.optInt("city_id"));
					listCommunity.setId(commnityObj.optInt("id"));
					listCommunity
							.setAlphabet(commnityObj.optString("alphabet"));
					listCommunity.setLongitude(commnityObj
							.optDouble("longitude"));
					listCommunity.setLatitude(commnityObj.optDouble("latitude"));
					listCommunity.setmCommunityName(commnityObj
							.optString("name"));
					listCommunity.setTopicCount(commnityObj.optInt("topic_num"));
					listCommunity.setType(commnityObj.optInt("type"));
					list.add(listCommunity);
				}
				data.addAll(list);
			}
		}
		// JSONArray communityArray = jsonObj.optJSONArray("list");

		/*
		 * JSONObject jsonObj = new JSONObject(); jsonObj.put("letter", "A");
		 * jsonObj.put("id", 001); jsonObj.put("name", "AMB大厦");
		 * communityArray.put(jsonObj);
		 */
		// LogUtils.e(TAG, communityArray.toString());

		// add models from jsonArray
		/*
		 * for (int i = 0; i < communityArray.length(); i++) { JSONObject obj =
		 * communityArray.getJSONObject(i); String letter =
		 * obj.optString("alphabet"); if (letter != null || !letter.equals(""))
		 * { List<MultiModelBase> list = getListByLetter(letter);
		 * 
		 * // crate a model Model_Community model = new Model_Community();
		 * 
		 * // set model model.setId(obj.optString("id"));
		 * model.setmCommunityName(obj.getString("name"));
		 * 
		 * // add model list.add(model);
		 * 
		 * }
		 */

		// add modelList to data

		//for (String key : mReferenceMap.keySet()) {
		//	List<MultiModelBase> list = getListByLetter(key);
		//	if (list.size() > 1) {
		//		data.addAll(list);
		//	}
			// LogUtils.e(TAG, key);
			// LogUtils.i(TAG,"listLength  "+list.size());
		//}

		// }

		return data;
	}

}

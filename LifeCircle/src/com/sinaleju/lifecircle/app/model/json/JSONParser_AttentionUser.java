package com.sinaleju.lifecircle.app.model.json;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.model.Model_AttentionUserInfo;
import com.sinaleju.lifecircle.app.model.Model_CategoryLabel;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_AttentionUser implements
		MultiJSONParserBase<MultiModelBase> {

	/*
	 * private static final String[] LETTERS = {"附近小区","A", "B", "C", "D", "E",
	 * "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
	 * "T", "U", "V", "W", "X", "Y", "Z" };
	 */

	private static final String TAG = "JSONParser_AttentionUser";
	public  List<MultiModelBase> data;
	public static List<MultiModelBase> resultData;
	private final boolean TEST = false;

	private Map<String, List<MultiModelBase>> mReferenceMap = new HashMap<String, List<MultiModelBase>>();

	public JSONParser_AttentionUser() {
		init();
	}

	private void init() {
		data = new LinkedList<MultiModelBase>();
		resultData=new LinkedList<MultiModelBase>( );
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

		if (this.TEST) {
			List<MultiModelBase> latestList = new LinkedList<MultiModelBase>();
			latestList.add(new Model_CategoryLabel("最近联系人"));
			latestList
					.add(new Model_AttentionUserInfo(
							1,
							0,
							"https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
							"张晓风", "0",0));
			latestList
					.add(new Model_AttentionUserInfo(
							2,
							1,
							"https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
							"牛大力", "0",0));
			latestList
					.add(new Model_AttentionUserInfo(
							3,
							0,
							"https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
							"小柯", "0",0));
			data.addAll(latestList);
			LogUtils.e(TAG, data.toString());
		} else {

			// 添加最近联系人

			JSONObject jsonObj = new JSONObject(json);
			// JSONObject dataObj=jsonObj.optJSONObject("data");
			List<MultiModelBase> latestList = new LinkedList<MultiModelBase>();
			JSONArray nearArray = jsonObj.optJSONArray("recent");
			//int nearArrayLength = nearArray.length();
			if (nearArray!=null && nearArray.length() > 0) {
				latestList.add(new Model_CategoryLabel("最近联系的人")); // 最近联系人;
				for (int i = 0; i < nearArray.length(); i++) {
					JSONObject latestJson = nearArray.optJSONObject(i);
					Model_AttentionUserInfo latestUser = new Model_AttentionUserInfo();
					latestUser.setId(Integer.valueOf(latestJson.optInt("id")));
					latestUser.setName((latestJson.optString("login_name")));
					latestUser.setNickName(latestJson.optString("display_name"));
					latestUser.setUrl((latestJson.optString("header")));
					latestUser.setIsOAth(latestJson.optInt("is_auth"));
					latestUser.setType(latestJson.optString("type"));
					latestUser.setSend_status(latestJson.optInt("send_status"));
					latestList.add(latestUser);
				}

				// add to data
				data.addAll(latestList);
			}

			JSONObject listObj = jsonObj.optJSONObject("list");
			// #、A-Z联系人

			List<MultiModelBase> jingList = new LinkedList<MultiModelBase>();
			JSONArray jingArray = listObj.optJSONArray("#");
			if (jingArray != null && jingArray.length() > 0) {
				jingList.add(new Model_CategoryLabel("#")); // #联系人;
				for (int i = 0; i < jingArray.length(); i++) {
					JSONObject jingJson = jingArray.optJSONObject(i);
					Model_AttentionUserInfo jingUser = new Model_AttentionUserInfo();
					jingUser.setId(Integer.valueOf(jingJson.optInt("id")));
					jingUser.setName((jingJson.optString("login_name")));
					jingUser.setNickName(jingJson.optString("display_name"));
					jingUser.setUrl((jingJson.optString("header")));
					jingUser.setIsOAth(jingJson.optInt("is_auth"));
					jingUser.setType(jingJson.optString("type"));
					jingUser.setSend_status(jingJson.optInt("send_status"));
					jingList.add(jingUser);

				}

				// add to data
				data.addAll(jingList);
				resultData.addAll(jingList);
			}

			for (short i = 65; i < 91; i++) {
				char c = (char) i;
				String letter = String.valueOf(c);
				List<MultiModelBase> list = getListByLetter(letter);
				JSONArray userArray = listObj.optJSONArray(letter);
				int length = userArray.length();
				if (length > 0) {
					for (int j = 0; j < length; j++) {
						JSONObject userObj = userArray.optJSONObject(j);
						Model_AttentionUserInfo allUser = new Model_AttentionUserInfo();
						allUser.setId(Integer.valueOf(userObj.optInt("id")));
						allUser.setName((userObj.optString("login_name")));
						allUser.setNickName(userObj.optString("display_name"));
						allUser.setUrl((userObj.optString("header")));
						allUser.setIsOAth(userObj.optInt("is_auth"));
						allUser.setType(userObj.optString("type"));
						allUser.setSend_status(userObj.optInt("send_status"));
						list.add(allUser);
					}
					data.addAll(list);
					resultData.addAll(list);
				}
			}

			/*for (String key : mReferenceMap.keySet()) {
				List<MultiModelBase> list = getListByLetter(key);
				if (list.size() > 1) {
					data.addAll(list);
				}
			}*/

		}
		return data;
	}
}

package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.model.Model_PersonalHeader;
import com.sinaleju.lifecircle.app.model.VisitorsModel;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class JSONParser_PersonalHeader implements
		MultiJSONParserBase<MultiModelBase> {

	private static final String TAG = "JSONParser_PersonalHeader";

	private boolean isMySelf = true;

	private TitleBar mTitleBar = null;

	private UserBean mUserBean = AppContext.curUser();

	public JSONParser_PersonalHeader(boolean isMySelf, TitleBar mTitleBar) {
		super();
		this.isMySelf = isMySelf;
		this.mTitleBar = mTitleBar;
	}

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException {

		List<MultiModelBase> data = new LinkedList<MultiModelBase>();
		JSONObject jsonData = new JSONObject(json);

		LogUtils.i(TAG, "json is " + json);
		// 个人主页Head数据
		Model_PersonalHeader model = new Model_PersonalHeader();
		model.setDisplay_name(jsonData.optString("display_name"));
		model.setSignature(jsonData.optString("signature"));
		model.setSex(jsonData.optString("sex"));
		model.setBackground(jsonData.optString("background"));
		model.setHeader(jsonData.optString("header"));
		if (isMySelf) {
			mUserBean.setBackground(jsonData.optString("background"));
			mUserBean.setHeaderUrl(jsonData.optString("header"));
		}
		model.setFans_num(jsonData.optString("fans_num"));
		model.setFollow_num(jsonData.optString("follow_num"));
		model.setLogin_name(jsonData.optString("login_name"));
		model.setCategory(jsonData.optString("category"));
		model.setBirthday(jsonData.optString("birthday"));
		model.setAstro(jsonData.optString("astro"));
		model.setHometown(jsonData.optString("hometown"));
		model.setLocation(jsonData.optString("astro"));
		model.setQq(jsonData.optString("qq"));
		model.setMobile(jsonData.optString("mobile"));
		model.setType(jsonData.optInt("type"));
		model.setSend_status(jsonData.optInt("send_status"));
		JSONArray array = jsonData.optJSONArray("visitors");
		if (array != null && array.length() != 0) {
			List<VisitorsModel> visitors = new ArrayList<VisitorsModel>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject v = array.optJSONObject(i);
				VisitorsModel visitorsModel = new VisitorsModel();
				visitorsModel.setVisitor_id(v.optInt("visitor_id"));
				visitorsModel.setType(v.optInt("type"));
				visitorsModel.setVisitor_header(v.optString("visitor_header"));
				visitors.add(visitorsModel);
			}
			model.setVisitors(visitors);
		}
		model.setVisitor_follow_status(jsonData
				.optString("visitor_follow_status"));
		model.setMySelf(isMySelf);
		data.add(model);

		if (model.getDisplay_name() != null) {
			mTitleBar.setTitleName(model.getDisplay_name());
		}

		return data;
	}

}

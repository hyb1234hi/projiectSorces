package com.sinaleju.lifecircle.app.model.json;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.customviews.TitleBar;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.model.BusImageModel;
import com.sinaleju.lifecircle.app.model.EviewModel;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageAddress;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageEview;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageHeader;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageImage;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageIntroduce;
import com.sinaleju.lifecircle.app.model.Model_BusinessPagePhone;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageSpecials;
import com.sinaleju.lifecircle.app.model.VisitorsModel;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;

public class JSONParser_BusinessHeader implements MultiJSONParserBase<MultiModelBase> {

	private boolean isMySelf = true;

	private TitleBar mTitleBar = null;

	private boolean isProperty = false; // 是否是物业

	private UserBean mUserBean = AppContext.curUser();

	public JSONParser_BusinessHeader(boolean isMySelf, TitleBar mTitleBar, boolean isProperty) {
		super();
		this.isMySelf = isMySelf;
		this.mTitleBar = mTitleBar;
		this.isProperty = isProperty;
	}

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set) throws JSONException {
		// TODO Auto-generated method stub
		List<MultiModelBase> data = new LinkedList<MultiModelBase>();

		JSONObject jsonData = new JSONObject(json);

		// 商家Head数据
		setBusinessHeadData(data, jsonData);

		// 商家电话item
		setBusinessPhoneData(data, jsonData);

		// 商家特惠详情item
		setBusinessSpecialsData(data, jsonData);

		// 商家介绍item
		setBusinessIntroduceData(data, jsonData);

		// 商家地址item
		setBusinessAddressData(data, jsonData);

		// 商家图片item
		setBusinessImageData(data, jsonData);

		// 商家评价item
		setBusinessEviewData(data, jsonData);

		return data;
	}

	private void setBusinessEviewData(List<MultiModelBase> data, JSONObject jsonData) {
		Model_BusinessPageEview eview = new Model_BusinessPageEview();
		JSONObject object = jsonData.optJSONObject("score_list");
		eview.setSmallest_id(object.optInt("smallest_id"));
		eview.setSurplus(object.optInt("surplus"));
		eview.setCount(object.optInt("count"));
		JSONArray eviewArray = object.optJSONArray("list");
		if (eviewArray != null && eviewArray.length() != 0) {
			List<EviewModel> eviewModels = new ArrayList<EviewModel>();
			for (int i = 0; i < eviewArray.length(); i++) {
				JSONObject v = eviewArray.optJSONObject(i);
				EviewModel eviewModel = new EviewModel();
				eviewModel.setId(v.optString("from_user_id"));
				eviewModel.setName(v.optString("from_user_name"));
				eviewModel.setImageUrl(v.optString("from_user_header"));
				eviewModel.setTime(v.optString("add_time"));
				eviewModel.setText(v.optString("content"));
				eviewModel.setQualityRB(v.optInt("quality"));
				eviewModel.setPriceRB(v.optInt("price"));
				eviewModel.setVelocityRB(v.optInt("speed"));
				eviewModel.setAttitudeRB(v.optInt("attitude"));
				eviewModels.add(eviewModel);
			}
			eview.setEviewModels(eviewModels);
		}
		eview.setProperty(isProperty);
		data.add(eview);
	}

	private void setBusinessImageData(List<MultiModelBase> data, JSONObject jsonData) {
		Model_BusinessPageImage image = new Model_BusinessPageImage();
		JSONArray imageArray = jsonData.optJSONArray("images");
		List<BusImageModel> busImages = new ArrayList<BusImageModel>();
		if (imageArray != null && imageArray.length() != 0) {
			for (int i = 0; i < imageArray.length(); i++) {
				JSONObject v = imageArray.optJSONObject(i);
				BusImageModel busImage = new BusImageModel();
				busImage.setPosition(v.optString("id"));
				if (!TextUtils.isEmpty(v.optString("url"))) {
					busImage.setImageUrl(v.optString("url"));
					busImages.add(busImage);
				}
			}
			image.setImages(busImages);
		}
		image.setMySelf(isMySelf);
		image.setProperty(isProperty);
		if (isMySelf) {
			mUserBean.setBusImages(image);
		}
		if (busImages.size() != 0) {
			data.add(image);
		}
	}

	private void setBusinessAddressData(List<MultiModelBase> data, JSONObject jsonData) {
		Model_BusinessPageAddress address = new Model_BusinessPageAddress();
		address.setAddress(jsonData.optString("address"));
		address.setBusLocationText(jsonData.optString("map_address"));
		address.setmLon(jsonData.optDouble("longitude"));
		address.setmLat(jsonData.optDouble("latitude"));
		if (isMySelf) {
			mUserBean.setBusLocationText(jsonData.optString("map_address"));
		}
		address.setProperty(isProperty);
		if (!TextUtils.isEmpty(address.getAddress())
				|| !TextUtils.isEmpty(address.getBusLocationText())) {
			address.setMySelf(isMySelf);
			data.add(address);
		}
	}

	private void setBusinessIntroduceData(List<MultiModelBase> data, JSONObject jsonData) {
		Model_BusinessPageIntroduce introduce = new Model_BusinessPageIntroduce();
		introduce.setNormal_am_start(setIntroduceTime(jsonData.optInt("normal_am_start")));
		introduce.setNormal_am_end(setIntroduceTime(jsonData.optInt("normal_am_end")));
		introduce.setNormal_pm_start(setIntroduceTime(jsonData.optInt("normal_pm_start")));
		introduce.setNormal_pm_end(setIntroduceTime(jsonData.optInt("normal_pm_end")));
		introduce.setHalt_am_start(setIntroduceTime(jsonData.optInt("halt_am_start")));
		introduce.setHalt_am_end(setIntroduceTime(jsonData.optInt("halt_am_end")));
		introduce.setHalt_pm_start(setIntroduceTime(jsonData.optInt("halt_pm_start")));
		introduce.setHalt_pm_end(setIntroduceTime(jsonData.optInt("halt_pm_end")));
		introduce.setIntroduction(jsonData.optString("introduction"));
		introduce.setProperty(isProperty);
		data.add(introduce);
	}

	private void setBusinessSpecialsData(List<MultiModelBase> data, JSONObject jsonData) {
		if (!isProperty) {
			Model_BusinessPageSpecials specials = new Model_BusinessPageSpecials();
			JSONObject obj = jsonData.optJSONObject("special_info");
			if (obj != null) {
				specials.setTitle(obj.optString("title"));
				specials.setHeight_price(obj.optString("height_price"));
				specials.setLow_price(obj.optString("low_price"));
				specials.setContent(obj.optString("content"));
				JSONArray speImageArray = obj.optJSONArray("images");
				List<BusImageModel> speImages = new ArrayList<BusImageModel>();
				if (speImageArray != null && speImageArray.length() != 0) {
					for (int i = 0; i < speImageArray.length(); i++) {
						JSONObject v = speImageArray.optJSONObject(i);
						BusImageModel speImage = new BusImageModel();
						speImage.setPosition(v.optString("id"));
						if (!TextUtils.isEmpty(v.optString("url"))) {
							speImage.setImageUrl(v.optString("url"));
							speImages.add(speImage);
						}
					}
					specials.setImages(speImages);
				}
				if (isMySelf) {
					mUserBean.setBusSpecials(specials);
				}
				if (speImages.size() != 0 || !TextUtils.isEmpty(specials.getTitle())) {
					data.add(specials);
				}
			}
		}
	}

	private void setBusinessHeadData(List<MultiModelBase> data, JSONObject jsonData) {
		Model_BusinessPageHeader model = new Model_BusinessPageHeader();
		model.setDisplay_name(jsonData.optString("display_name"));
		model.setName(jsonData.optString("name"));
		model.setSignature(jsonData.optString("signature"));
		model.setBackground(jsonData.optString("background"));
		model.setHeader(jsonData.optString("header"));
		if (isMySelf) {
			mUserBean.setCompanyName(jsonData.optString("name"));
			mUserBean.setCategory_id(jsonData.optInt("category_id"));
			mUserBean.setSubcategory_id(jsonData.optInt("subcategory_id"));
			mUserBean.setBackground(jsonData.optString("background"));
			mUserBean.setHeaderUrl(jsonData.optString("header"));
		}
		model.setFans_num(jsonData.optString("fans_num"));
		model.setFollow_num(jsonData.optString("follow_num"));
		model.setTraffic_routes(jsonData.optString("traffic_routes"));
		model.setLatitude(jsonData.optString("latitude"));
		model.setLongitude(jsonData.optString("longitude"));
		model.setType(jsonData.optInt("type"));
		model.setIs_auth(jsonData.optInt("is_auth"));
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
		model.setVisitor_follow_status(jsonData.optString("visitor_follow_status"));
		model.setMySelf(isMySelf);
		model.setProperty(isProperty);
		if (!TextUtils.isEmpty(model.getDisplay_name())) {
			mTitleBar.setTitleName(model.getDisplay_name());
		}
		data.add(model);
	}

	private void setBusinessPhoneData(List<MultiModelBase> data, JSONObject jsonData) {
		Model_BusinessPagePhone phone = new Model_BusinessPagePhone();
		phone.setPhone(jsonData.optString("phone"));
		phone.setTel_hits(jsonData.optString("tel_hits"));
		phone.setQuality(jsonData.optString("quality"));
		phone.setPrice(jsonData.optString("price"));
		phone.setSpeed(jsonData.optString("speed"));
		phone.setAttitude(jsonData.optString("attitude"));
		phone.setMySelf(isMySelf);
		data.add(phone);
	}

	private String setIntroduceTime(int time) {
		StringBuffer sbBuffer = new StringBuffer();
		if (time < 10) {
			sbBuffer.append("0" + time + ":00");
		} else {
			sbBuffer.append(time + ":00");
		}
		return sbBuffer.toString();
	}

}

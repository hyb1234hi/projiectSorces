package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_HomePageTop;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_HomePageTop extends MultiModelBase {
	/**
	 */
	private static final long serialVersionUID = -1844347163743519749L;
	private int mTopicCount;
	private int property_id = -1;
	private int followType;
	private String background;
	private String property_name;
	private Weather mWeather;
	private Advertising mAdvertising;
	public Advertising getmAdvertising() {
		return mAdvertising;
	}

	public void setmAdvertising(Advertising mAdvertising) {
		this.mAdvertising = mAdvertising;
	}

	//广告位信息
	public static class Advertising implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int id;
		private String herf;
		private String img;
		private int city_id;
		private int add_time;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getHerf() {
			return herf;
		}
		public void setHerf(String herf) {
			this.herf = herf;
		}
		public String getImg() {
			return img;
		}
		public void setImg(String img) {
			this.img = img;
		}
		public int getCity_id() {
			return city_id;
		}
		public void setCity_id(int city_id) {
			this.city_id = city_id;
		}
		public int getAdd_time() {
			return add_time;
		}
		public void setAdd_time(int add_time) {
			this.add_time = add_time;
		}
		
	}
	//天气信息
	public static class Weather implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5908541731201265800L;
		private int temperature;
		private int temp_low;
		private int temp_high;
		private String desc;
		private int icon;

		public int getTemperature() {
			return temperature;
		}

		public void setTemperature(int temperature) {
			this.temperature = temperature;
		}

		public int getTemp_low() {
			return temp_low;
		}

		public void setTemp_low(int temp_low) {
			this.temp_low = temp_low;
		}

		public int getTemp_high() {
			return temp_high;
		}

		public void setTemp_high(int temp_high) {
			this.temp_high = temp_high;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public int getIcon() {
			return icon;
		}

		public void setIcon(int icon) {
			this.icon = icon;
		}
	}

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_HomePageTop.class;
	}

	public int getTopicCount() {
		return mTopicCount;
	}

	public void setTopicCount(int mTopicCount) {
		this.mTopicCount = mTopicCount;
	}

	public int getProperty_id() {
		return property_id;
	}

	public void setProperty_id(int property_id) {
		this.property_id = property_id;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public int getFollowType() {
		return followType;
	}

	public void setFollowType(int followType) {
		this.followType = followType;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Weather getWeather() {
		return mWeather;
	}

	public void setWeather(Weather mWeather) {
		this.mWeather = mWeather;
	}

	public void setWeather(JSONObject obj) {
		if (obj != null) {
			if (this.mWeather == null)
				this.mWeather = new Weather();
			
			mWeather.setDesc(obj.optString("info"));
			mWeather.setIcon(obj.optInt("img1"));
			mWeather.setTemp_high(obj.optInt("tmp1"));
			mWeather.setTemp_low(obj.optInt("tmp2"));
			mWeather.setTemperature(obj.optInt("now_tem"));
		}
	}
	
	public void setAdvertising(JSONObject obj){
		if (obj != null) {
			if (this.mAdvertising == null)
				this.mAdvertising = new Advertising();
			mAdvertising.setAdd_time(obj.optInt("add_time"));
			mAdvertising.setCity_id(obj.optInt("city_id"));
			mAdvertising.setHerf(obj.optString("herf"));
			mAdvertising.setId(obj.optInt("id"));
			mAdvertising.setImg(obj.optString("img"));
		}
	}

}

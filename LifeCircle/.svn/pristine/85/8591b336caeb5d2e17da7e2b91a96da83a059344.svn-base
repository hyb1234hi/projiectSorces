package com.sinaleju.lifecircle.app.model;

import java.io.Serializable;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsMsg;

public class Model_TrendsMsg extends Model_TrendsBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1531813744662525920L;
	private int mCommentCount;// 评论数
	private int mDeliveredCount;// 转发数
	private int mAgreeCount;// 赞数
	private int msg_id;
	private int like;
	private String mMsgInfo;
	private Pic[] mPics;
	private String name;
	private boolean VIP;
	private String location;
	private String coordinate;
	private Model_TrendsSimple simple;

	private boolean isShowHeadImage = true;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_TrendsMsg.class;
	}

	public int getCommentCount() {
		return mCommentCount;
	}

	public void setCommentCount(int mCommentCount) {
		this.mCommentCount = mCommentCount;
	}

	public int getDeliveredCount() {
		return mDeliveredCount;
	}

	public void setDeliveredCount(int mDeliveredCount) {
		this.mDeliveredCount = mDeliveredCount;
	}

	public int getAgreeCount() {
		return mAgreeCount;
	}

	public void setAgreeCount(int mAgreeCount) {
		this.mAgreeCount = mAgreeCount;
	}

	public boolean isVIP() {
		return VIP;
	}

	public void setVIP(boolean vIP) {
		VIP = vIP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public Pic[] getPics() {
		return mPics;
	}

	public void setPics(Pic[] mPics) {
		this.mPics = mPics;
	}

	public boolean isShowHeadImage() {
		return isShowHeadImage;
	}

	public void setShowHeadImage(boolean isShowHeadImage) {
		this.isShowHeadImage = isShowHeadImage;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public String getMsgInfo() {
		return mMsgInfo;
	}

	public void setMsgInfo(String mMsgInfo) {
		this.mMsgInfo = mMsgInfo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public static class Pic implements Serializable {
		/**
		 */
		private static final long serialVersionUID = -5250236802429751205L;
		private String urlSmall;
		private String urlMiddle;
		private String urlBig;

		public String getUrlSmall() {
			return urlSmall;
		}

		public void setUrlSmall(String urlSmall) {
			this.urlSmall = urlSmall;
		}

		public String getUrlMiddle() {
			return urlMiddle;
		}

		public void setUrlMiddle(String urlMiddle) {
			this.urlMiddle = urlMiddle;
		}

		public String getUrlBig() {
			return urlBig;
		}

		public void setUrlBig(String urlBig) {
			this.urlBig = urlBig;
		}

	}

	public Model_TrendsSimple getSimple() {
		return simple;
	}

	public void setSimple(Model_TrendsSimple simple) {
		this.simple = simple;
	}

}

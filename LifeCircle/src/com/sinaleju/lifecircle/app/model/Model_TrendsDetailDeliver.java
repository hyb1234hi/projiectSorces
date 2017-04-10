package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsDetailDeliver;

public class Model_TrendsDetailDeliver extends Model_TrendsMsgDeliver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5820812596059537957L;
	private Model_TrendsDetailMsg mDeliveredMsg;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_TrendsDetailDeliver.class;
	}

	public Model_TrendsDetailMsg getDeliveredMsg() {
		return mDeliveredMsg;
	}

	public void setDeliveredDetailMsg(Model_TrendsDetailMsg mDeliveredMsg) {
		this.mDeliveredMsg = mDeliveredMsg;
	}

	public Model_TrendsDetailDeliver() {
		//no-args constructor
	}

	public Model_TrendsDetailDeliver(Model_TrendsMsgDeliver m) {
		copyFromDeliver(m);
	}

	public void copyFromDeliver(Model_TrendsMsgDeliver m) {
		
		if(m == null){
			return;
		}
		
		setAgreeCount(m.getAgreeCount());
		setCommentCount(m.getCommentCount());
		setCommunity_id(m.getCommunity_id());
		setCoordinate(m.getCoordinate());
		setDeliveredCount(m.getDeliveredCount());
		setFlag_type(m.getFlag_type());
		setFlagUid(m.getFlagUid());
		setFlagUrl(m.getFlagUrl());
		setHeadPortraitUrl(m.getHeadPortraitUrl());
		setHideTimeLine(true);
		setLike(m.getLike());
		setLocation(m.getLocation());
		setModelType(m.getModelType());
		setMsg_id(m.getMsg_id());
		setMsgInfo(m.getMsgInfo());
		setName(m.getName());
		setPics(m.getPics());
//		setShowFlag(false);
//		setShowHeader(false);
//		setShowHeadImage(false);
		setSpanTexts(m.getMSpanTexts());
		setTrends_id(m.getTrends_id());
		setTrendsType(m.getTrendsType());
		setU_type(m.getU_type());
		setUid(m.getUid());
		setVIP(m.isVIP());
		setmTag(m.getmTag());
		m.getDeliveredMsg().setmTag(m.getmTag());

		setDeliveredDetailMsg(new Model_TrendsDetailMsg(m.getDeliveredMsg()));

	}

}

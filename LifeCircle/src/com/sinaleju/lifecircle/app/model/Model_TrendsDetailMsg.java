package com.sinaleju.lifecircle.app.model;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsDetailMsg;


public class Model_TrendsDetailMsg extends Model_TrendsMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8704190671030903118L;

	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_TrendsDetailMsg.class;
	}
	
	public Model_TrendsDetailMsg(){
		
	}
	public Model_TrendsDetailMsg(Model_TrendsMsg m){
		coypFromMsg(m);
	}
	
	public void coypFromMsg(Model_TrendsMsg m){
		
		if(m == null ){
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
//		setHideTimeLine(m.isHideTimeLine());
		setHideTimeLine(true);
		setLike(m.getLike());
		setLocation(m.getLocation());
		setModelType(m.getModelType());
		setMsg_id(m.getMsg_id());
		setMsgInfo(m.getMsgInfo());
		setName(m.getName());
		setPics(m.getPics());
//		setShowFlag(true);
//		setShowHeader(true);
//		setShowHeadImage(true);
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
	}
	
}

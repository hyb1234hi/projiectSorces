package com.sinaleju.lifecircle.app.model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sinaleju.lifecircle.app.customviews.itemview.AbsItemView;
import com.sinaleju.lifecircle.app.customviews.itemview.Item_TrendsDetailComment;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;

public class Model_TrendsDetailComment extends MultiModelBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2187957149258354911L;
	private int mTime;
	private SpanText[] mSpans;
	private Model_UserInfo mCommentOwner;
	private int mMsgId;
	private boolean needTopLine = true;
	@Override
	protected Class<? extends AbsItemView> getItemViewClass() {
		return Item_TrendsDetailComment.class;
	}


	public int getTime() {
		return mTime;
	}


	public void setTime(int mTime) {
		this.mTime = mTime;
	}


	public SpanText[] getSpans() {
		return mSpans;
	}


	public void setSpans(SpanText[] mSpans) {
		this.mSpans = mSpans;
	}



	public Model_UserInfo getmCommentOwner() {
		return mCommentOwner;
	}


	public void setmCommentOwner(Model_UserInfo mCommentOwner) {
		this.mCommentOwner = mCommentOwner;
	}
	public void setmCommentOwner(JSONObject userinfo) {
		this.mCommentOwner = new Model_UserInfo();
		this.mCommentOwner.parse(userinfo);
	}


	public int getmMsgId() {
		return mMsgId;
	}


	public void setmMsgId(int mMsgId) {
		this.mMsgId = mMsgId;
	}
	
	public void setSpanTexts(JSONArray content_show){
		
		if(content_show==null||content_show.length()==0){
			return;
		}
		
		SpanText[] spans = new SpanText[content_show.length()];
		
		for(int i=0;i<content_show.length();i++){
			SpanText span = new SpanText();
			JSONObject obj = content_show.optJSONObject(i);
			if(obj!=null){
				span.setItem_id(obj.optInt("item_id"));
				span.setSpanType(obj.optInt("type"));
				span.setText(obj.optString("text"));
			}
			
			spans[i] = span;
			
		}
		
		//set
		setSpans(spans);
	}


	public boolean isNeedTopLine() {
		return needTopLine;
	}


	public void setNeedTopLine(boolean needTopLine) {
		this.needTopLine = needTopLine;
	}
}

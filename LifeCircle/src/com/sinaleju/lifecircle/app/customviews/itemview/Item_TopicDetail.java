package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.HotTopicAct;
import com.sinaleju.lifecircle.app.activity.SendMsgMainActivity;
import com.sinaleju.lifecircle.app.model.Model_TopicDetail;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_TopicDetail extends AbsItemView {
	private View btnJionConversation;
	private View btnTopicList;
	private TextView txtAmount;
	private TextView txtTopic;

	public Item_TopicDetail(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_topic_detail, this);
		txtTopic = (TextView) findViewById(R.id.txtTopic);
		txtAmount = (TextView) findViewById(R.id.txtAmount);
		btnTopicList = findViewById(R.id.btnTopiclist);
		btnJionConversation = findViewById(R.id.btnJionConversation);

		btnTopicList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), HotTopicAct.class);
				if (AppContext.isVisitor()) {
					i.putExtra("community_id", AppContext.curVisitor().getCommunity_id());
				} else {
					i.putExtra("community_id", AppContext.curUser().getCurrentCommunityId());
				}
				getContext().startActivity(i);
			}
		});
		btnJionConversation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (AppContext.isValid(getContext())) {
					Model_TopicDetail m = (Model_TopicDetail) mModel;
					if (m != null) {
						Intent intent = new Intent(getContext(), SendMsgMainActivity.class);
						intent.putExtra(AppConst.INTENT_MSG_TOPIC_TEXT, m.getName());
						intent.putExtra(AppConst.INTENT_MSG_TOPIC_ID, m.getTopic_id());
						getContext().startActivity(intent);

					}
				}
			}
		});
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

	@Override
	protected void toSet(int type, BaseModel model) {
		if (!(model instanceof Model_TopicDetail)) {
			return;
		}
		Model_TopicDetail m = (Model_TopicDetail) model;

		txtTopic.setText(m.getName());
		txtAmount.setText("讨论("
				+ (m.getAmount() > 10000 ? m.getAmount() / 10000 + "万" : m.getAmount()) + ")");
	}

}

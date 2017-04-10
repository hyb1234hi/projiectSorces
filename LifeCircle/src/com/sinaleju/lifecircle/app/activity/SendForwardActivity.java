package com.sinaleju.lifecircle.app.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.TopicOrAtModel;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSMsgForward;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class SendForwardActivity extends BaseActivity implements TextWatcher {

	private static final String TAG = "SendForwardActivity";
	private ImageView mHeadImage = null;
	private EditText mMianEdit = null;
	private CheckBox mIsComment = null;
	private TextView mForFrom = null;
	private TextView mLastNum = null;

	private Button mBtnAt, mBtnTopic;
	List<TopicOrAtModel> mTopicModels = new ArrayList<TopicOrAtModel>();
	List<TopicOrAtModel> mAtModels = new ArrayList<TopicOrAtModel>();

	private ImageView mForHeadImage = null;
	private TextView mForHeadName = null;
	private TextView mForHeadText = null;
	private UserBean mUserBean = null;
	private int mMsgId = 0;
	private RSMsgForward rs = null;

	private String mForHeaderUrl = null;
	private String mForName = null;
	private String mForText = null;
	private int mForUserId = 0;
	private int mForMsgId = 0;
	private int mForUserType = 0;
	private SpanText[] mMainSpanTexts = null;
	private String mMsgUserName = null;
	private static final int WEIBO_MAX_LENGTH = 140;
	private int mMsgCommunityId = -1;
	private String mTag = "xiaoxi";

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_send_forward;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "------------initViews");
		mHeadImage = (ImageView) findViewById(R.id.msg_head_image);
		mMianEdit = (EditText) findViewById(R.id.msg_main_edit);
		mIsComment = (CheckBox) findViewById(R.id.commment_check_box);
		mForFrom = (TextView) findViewById(R.id.forward_where_from);
		mLastNum = (TextView) findViewById(R.id.msg_last_num);

		mBtnAt = (Button) findViewById(R.id.msg_at_btn_layout);
		mBtnTopic = (Button) findViewById(R.id.msg_topic_btn_layout);

		mForHeadImage = (ImageView) findViewById(R.id.msg_forward_head_image);
		mForHeadName = (TextView) findViewById(R.id.msg_forward_head_name);
		mForHeadText = (TextView) findViewById(R.id.msg_forward_head_text);

		mMianEdit.addTextChangedListener(this);

		mUserBean = AppContext.curUser();
		if (mUserBean != null) {
			if (!TextUtils.isEmpty(mUserBean.getHeaderUrl())) {
				mHeadImage.setBackgroundResource(R.drawable.bg_frame_header);
				ImageLoader.getInstance(SendForwardActivity.this)
						.displayImage(
								mUserBean.getHeaderUrl(),
								mHeadImage,
								SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils
										.getUserIndexDefaultHeadImage(mUserBean.getType()), 100));
			} else {
				mHeadImage.setImageResource(PublicUtils.getUserIndexDefaultHeadImage(mUserBean
						.getType()));
			}
		}
		setForwardUserData();
	}

	private void setForwardUserData() {
		mTag = getIntent().getStringExtra("tag");
		mMsgCommunityId = getIntent().getIntExtra("cid", -1);
		mMsgId = getIntent().getIntExtra("msg_id", 0);
		mForHeaderUrl = getIntent().getStringExtra("header");
		mForName = getIntent().getStringExtra("name");
		mForText = getIntent().getStringExtra("text");

		mForMsgId = getIntent().getIntExtra("forward_id", 0);
		mForUserId = getIntent().getIntExtra("uid", 0);
		mForUserType = getIntent().getIntExtra("type", 0);
		mMsgUserName = getIntent().getStringExtra("msg_user_name");

		if (!TextUtils.isEmpty(mForHeaderUrl)) {
			PublicUtils.loadHeadImage(mForHeadImage, mForHeaderUrl,
					PublicUtils.getUserDefaultHeadImage(mForUserType));
		} else {
			mForHeadImage.setImageResource(PublicUtils.getUserDefaultHeadImage(mForUserType));
		}
		if (!TextUtils.isEmpty(mForName)) {
			mForHeadName.setText(mForName);
			mForFrom.setText(mForName);
		}
		if (mForText != null) {
			mForHeadText.setText(mForText);
		}
		setTitleBarData();
		setForwardTitle();
	}

	private void setTitleBarData() {
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		mTitleBar.initRightButtonTextOrResId(0, R.drawable.common_submit_press);
		mTitleBar.setTitleName("转发");
	}

	private void addForwardUserData() {
		if (!TextUtils.isEmpty(mMsgUserName)) {
			if (mForUserId > 0) {
				TopicOrAtModel model = new TopicOrAtModel();
				model.setId(mForUserId);
				model.setText("@" + mMsgUserName);
				model.setType(String.valueOf(mForUserType));
				mAtModels.add(model);
			}
		}
	}

	private void setForwardTitle() {
		Parcelable[] parcelable = getIntent().getParcelableArrayExtra("span_texts");
		if (parcelable != null && parcelable.length != 0) {
			mMainSpanTexts = new SpanText[parcelable.length];
			for (int i = 0; i < parcelable.length; i++) {
				if (parcelable[i] instanceof SpanText) {
					SpanText t = (SpanText) parcelable[i];
					mMainSpanTexts[i] = t;
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		if (mMainSpanTexts != null && mMainSpanTexts.length != 0) {
			for (int i = 0; i < mMainSpanTexts.length; i++) {
				SpanText spanText = mMainSpanTexts[i];
				sb.append(spanText.getText());
				if (spanText.getSpanType() == SpanType.TOPIC) {
					TopicOrAtModel model = new TopicOrAtModel();
					model.setId(spanText.getItem_id());
					model.setText(spanText.getText());
					mTopicModels.add(model);
				} else if (spanText.getSpanType() == SpanType.AT) {
					TopicOrAtModel model = new TopicOrAtModel();
					model.setId(spanText.getItem_id());
					model.setText(spanText.getText().trim());
					model.setType(String.valueOf(spanText.getUserType()));
					mAtModels.add(model);
				}
			}
		}

		addForwardUserData();

		if (!TextUtils.isEmpty(sb.toString())) {
			if (!TextUtils.isEmpty(mMsgUserName)) {
				mMianEdit.setText("// @" + mMsgUserName + " : ");
			}
			mMianEdit.setText(mMianEdit.getText().toString() + sb.toString());
		} else {
			if (!TextUtils.isEmpty(mMsgUserName)) {
				mMianEdit.setText("// @" + mMsgUserName + " ");
			}
			mMianEdit.setText(mMianEdit.getText().toString() + sb.toString());
		}

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (view.getId()) {
			case R.id.msg_at_btn_layout:
				intent.setClass(SendForwardActivity.this, AttentionUserActivity.class);
				intent.putExtra(AppConst.INTENT_MSG_USER_ID, mUserBean.getUid());
				intent.putExtra(AppConst.INTENT_MSG_COMMUNITY_ID, mUserBean.getCurrentCommunityId());
				startActivityForResult(intent, 401);
				break;
			case R.id.msg_topic_btn_layout:
				intent.setClass(SendForwardActivity.this, TopicsActivity.class);
				intent.putExtra(AppConst.INTENT_MSG_COMMUNITY_ID, mUserBean.getCurrentCommunityId());
				startActivityForResult(intent, 301);
				break;
			default:
				break;
			}
		}
	};

	private boolean isValidTextLength(String text) {
		return text.length() <= WEIBO_MAX_LENGTH;
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicUtils.hideSoftInputMethod(SendForwardActivity.this);
				finish();
			}
		});

		mTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = mMianEdit.getText().toString();
				try {
					if (TextUtils.isEmpty(content.trim())) {
						showToast("您还未填写内容");
					} else {
						if (isValidTextLength(content)) {
							sendMsgForward();
						} else {
							showToast("内容不能超过140个字");
						}
					}
				} catch (SQLException e) {
					LogUtils.e(TAG, "sendForward error", e);
				}
			}
		});

		mBtnAt.setOnClickListener(clickListener);
		mBtnTopic.setOnClickListener(clickListener);

	}

	private void setTopicArray(List<TopicOrAtModel> mModels, Intent intent) {
		String topic = intent.getStringExtra(AppConst.INTENT_MSG_TOPIC_TEXT);
		int id = intent.getIntExtra(AppConst.INTENT_MSG_TOPIC_ID, -1);
		setTitleAtOrTopic(" " + topic + " ");
		TopicOrAtModel model = new TopicOrAtModel();
		model.setId(id);
		model.setText(topic);
		mModels.add(model);
	}

	private void setTitleAtOrTopic(String text) {
		int index = mMianEdit.getSelectionStart();// 获取光标所在位置
		Editable edit = mMianEdit.getEditableText();// 获取EditText的文字
		if (index < 0 || index >= edit.length()) {
			edit.append(text);
		} else {
			edit.insert(index, text);// 光标所在位置插入文字
		}
	}

	private void setAtArray(List<TopicOrAtModel> mModels, Intent intent) {
		String at = intent.getStringExtra(AppConst.INTENT_MSG_AT_TEXT);
		int id = intent.getIntExtra(AppConst.INTENT_MSG_AT_ID, -1);
		String type = intent.getStringExtra(AppConst.INTENT_MSG_AT_TYPE);
		setTitleAtOrTopic(" " + at + " ");
		TopicOrAtModel model = new TopicOrAtModel();
		model.setId(id);
		model.setText(at);
		model.setType(type);
		mModels.add(model);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 301 && resultCode == 300) {
			setTopicArray(mTopicModels, data);
			return;
		}

		if (requestCode == 401 && resultCode == 400) {
			setAtArray(mAtModels, data);
			return;
		}
	}

	private String getTopicOrAtJsonString(List<TopicOrAtModel> mModels, String content) {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < mModels.size(); i++) {
			TopicOrAtModel model = mModels.get(i);
			if (model.getId() != -1) {
				if (content.indexOf(model.getText()) != -1) {
					try {
						obj.put(model.getId() + "", model.getText());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj.toString();
	}

	private String getUserTypeJsonString(List<TopicOrAtModel> mModels, String content) {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < mModels.size(); i++) {
			TopicOrAtModel model = mModels.get(i);
			if (model.getId() != -1) {
				if (content.indexOf(model.getText()) != -1) {
					try {
						if (model.getType() != null) {
							obj.put(model.getId() + "", model.getType());
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return obj.toString();
	}

	private void sendMsgForward() throws SQLException {
		int comment = 0;
		comment = mIsComment.isChecked() ? 1 : 0;
		String content = mMianEdit.getText().toString();
		String temp = mMianEdit.getText().toString().trim();
		String topic = getTopicOrAtJsonString(mTopicModels, content);
		String at = getTopicOrAtJsonString(mAtModels, content);
		String userType = getUserTypeJsonString(mAtModels, content);

		if (TextUtils.isEmpty(temp)) {
			content = "转发消息";
		}

		content = content.replaceAll("\n", " ");
		if (TextUtils.isEmpty(mTag)) {
			mTag = "xiaoxi";
		}
		if (rs == null) {
			rs = new RSMsgForward(mUserBean.getUid(), mMsgId, content, topic, at, userType,
					comment, mMsgCommunityId, mForMsgId, mTag);

			rs.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					System.out.print("onSuccess: \n" + result);
					hideProgressDialog();
					showToast("转发消息成功");
					Intent intent = new Intent();
					if (mIsComment.isChecked()) {
						intent.putExtra("extra", true);
					} else {
						intent.putExtra("extra", false);
					}
					setResult(RESULT_OK, intent);
					rs = null;
					PublicUtils.hideSoftInputMethod(SendForwardActivity.this);
					finish();
				}

			});

			rs.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					showToast("转发消息失败");
					rs = null;
				}

			});
			showProgressDialog("正在转发消息...", true);
			rs.asyncExecute(this);
		}

	}

	private void setLastNumber() {
		String text = mMianEdit.getText().toString();
		// color set
		mLastNum.setTextColor(isValidTextLength(text) ? getResources().getColor(
				R.color.msg_last_number) : Color.RED);
		int destLen = text.toCharArray().length;
		int len = WEIBO_MAX_LENGTH - destLen;
		mLastNum.setText(String.valueOf(len));

		if (text.length() > 0 && text.length() <= WEIBO_MAX_LENGTH) {
			mTitleBar.initRightButtonTextOrResId(0, R.drawable.selector_topbar_submit_button);
		} else {
			mTitleBar.initRightButtonTextOrResId(0, R.drawable.common_submit_press);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		setLastNumber();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		hideProgressDialog();
		PublicUtils.hideSoftInputMethod(this);
		super.onDestroy();
	}

}

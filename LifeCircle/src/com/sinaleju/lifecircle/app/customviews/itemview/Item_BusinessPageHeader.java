package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.adapter.RecentVisitorsAdapter;
import com.sinaleju.lifecircle.app.customviews.HorizontalListView;
import com.sinaleju.lifecircle.app.model.AttenBtnModel;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageHeader;
import com.sinaleju.lifecircle.app.model.VisitorsModel;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.FadeInImageLoadingListener;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class Item_BusinessPageHeader extends AbsItemView {

	private HorizontalListView mVisitorListView;
	private ImageView mHeadLayoutImage;
	private ImageView mHeadImage;
	private TextView mHeadName;
	private Button mHeadMsgBtn;
	private TextView mHeadSign;
	private TextView mHeadFansNum;
	private TextView mHeadAttenNum;
	private Button mHeadAttenBtn;
	private ImageView mProJiaV;
	private ImageView mHeadSignIcon;

	public Item_BusinessPageHeader(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_home_page_part_one, this);
		initViews();
		setListeners();
	}

	@Override
	public boolean enable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

	@Override
	protected void toSet(int type, BaseModel model) {
		final Model_BusinessPageHeader busModel = (Model_BusinessPageHeader) model;
		setHeadData(busModel);
		setVisitor_follow_status(busModel);
		setVisitorData(busModel);
		if (busModel.isMySelf()) {
			setMySelfUserData();
		} else {
			setNotMySelfUserData(busModel);
		}

		mHeadAttenBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_BUSINESS_HEAD_ATTENBTN + getContext().hashCode());
			}
		});
		disableRefresh();
	}

	private void setHeadData(Model_BusinessPageHeader busModel) {
		if (!TextUtils.isEmpty(busModel.getHeader()) && !busModel.getHeader().equals("null")) {
			ImageLoader.getInstance((Activity) getContext()).displayImage(
					busModel.getHeader(),
					mHeadImage,
					SimpleImageLoaderOptions.getRoundImageOptions(
							PublicUtils.getUserIndexDefaultHeadImage(busModel.getType()), 100),
					new FadeInImageLoadingListener(mHeadImage));
		} else {
			mHeadImage
					.setImageResource(PublicUtils.getUserIndexDefaultHeadImage(busModel.getType()));
		}
		if (busModel.isProperty()) {
			mProJiaV.setVisibility(View.VISIBLE);
		} else {
			if (busModel.getIs_auth() == 2) {
				mProJiaV.setVisibility(View.VISIBLE);
			} else {
				mProJiaV.setVisibility(View.GONE);
			}
		}
		if (!TextUtils.isEmpty(busModel.getBackground())
				&& !busModel.getBackground().equals("null")) {
			ImageLoader.getInstance((Activity) getContext()).displayImage(
					busModel.getBackground(),
					mHeadLayoutImage,
					SimpleImageLoaderOptions.getOptions(
							PublicUtils.getIndexDefaultBg(busModel.getType()), true),
					new FadeInImageLoadingListener(mHeadLayoutImage));
		} else {
			mHeadLayoutImage.setImageResource(PublicUtils.getIndexDefaultBg(busModel.getType()));
		}

		if (!TextUtils.isEmpty(busModel.getDisplay_name())) {
			mHeadName.setText(busModel.getDisplay_name());
		} else {
			mHeadName.setText("");
		}

		if (!TextUtils.isEmpty(busModel.getSignature())) {
			mHeadSign.setText(busModel.getSignature());
		} else {
			if (busModel.isMySelf()) {
				if (busModel.isProperty()) {
					mHeadSign.setText("简单介绍下本公司的情况吧...");
				} else {
					mHeadSign.setText("简单写下当前的优惠活动信息吧...");
				}
			} else {
				mHeadSign.setText("");
			}
		}

		if (!TextUtils.isEmpty(busModel.getFans_num())) {
			mHeadFansNum.setText("粉丝(" + busModel.getFans_num() + ")");
		}

		if (!TextUtils.isEmpty(busModel.getFollow_num())) {
			mHeadAttenNum.setText("关注(" + busModel.getFollow_num() + ")");
		}
	}

	private void setVisitor_follow_status(Model_BusinessPageHeader busModel) {
		if (busModel.getVisitor_follow_status() != null) {
			if (busModel.getVisitor_follow_status().equals("0")) {
				mHeadAttenBtn.setBackgroundResource(R.drawable.jia_guan_zhu);
			} else if (busModel.getVisitor_follow_status().equals("1")) {
				mHeadAttenBtn.setBackgroundResource(R.drawable.yi_guan_zhu);
			} else if (busModel.getVisitor_follow_status().equals("2")) {
				mHeadAttenBtn.setBackgroundResource(R.drawable.wu_xiang_guan_zhu);
			}
		}
	}

	private void setVisitorData(Model_BusinessPageHeader busModel) {
		if (busModel.getVisitors() != null && busModel.getVisitors().size() != 0) {
			final RecentVisitorsAdapter adapter = new RecentVisitorsAdapter(getContext(),
					busModel.getVisitors());
			mVisitorListView.setAdapter(adapter);
			mVisitorListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
					// TODO Auto-generated method stub
					VisitorsModel visitor = adapter.getItem(pos);
					if (visitor.getType() != AppConst.NULL_INT) {
						AppContext.gotoIndexActivity(getContext(), visitor.getType(),
								visitor.getVisitor_id());
					}
				}
			});
		}
	}

	private void setNotMySelfUserData(final Model_BusinessPageHeader busModel) {
		if (busModel.getSend_status() == 0) {
			mHeadMsgBtn.setVisibility(View.GONE);
		} else if (busModel.getSend_status() == 1) {
			mHeadMsgBtn.setVisibility(View.VISIBLE);
		}
		mHeadAttenBtn.setVisibility(View.VISIBLE);
		mHeadSignIcon.setVisibility(View.GONE);
		AttenBtnModel aBtnModel = new AttenBtnModel();
		aBtnModel.setId(busModel.getVisitor_follow_status());
		aBtnModel.setAttenBtn(mHeadAttenBtn);
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_BUSINESS_HEAD_ATTENBTN_MODEL + getContext().hashCode(),
				aBtnModel);
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_BUSINESS_SHOW_CHAT_BTN + getContext().hashCode(), busModel);
		if (mHeadMsgBtn.getVisibility() == View.VISIBLE) {
			mHeadMsgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ApplicationFacade.getInstance().sendNotification(
							AppConst.APP_FACADE_BUSINESS_CHAT_CLICK + getContext().hashCode(),
							busModel);
				}
			});
		}
	}

	private void setMySelfUserData() {
		mHeadMsgBtn.setVisibility(View.GONE);
		mHeadAttenBtn.setVisibility(View.GONE);
		mHeadSignIcon.setVisibility(View.VISIBLE);
		// set un-controls
		AppContext appContext = (AppContext) getContext().getApplicationContext();
		appContext.getHomeActivity().addUnControledViews(mVisitorListView);

		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_BUSINESS_HEAD_ITEMVIEW + getContext().hashCode(), this);
		mHeadLayoutImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_BUSINESS_IMAGE_LAYOUT_CLICK + getContext().hashCode());
			}
		});
		mHeadSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_BUSINESS_SIGN_CLICK + getContext().hashCode());
			}
		});
	}

	private void initViews() {
		mHeadLayoutImage = (ImageView) findViewById(R.id.head_layout_image);
		mVisitorListView = (HorizontalListView) findViewById(R.id.visitor_list);
		mHeadImage = (ImageView) findViewById(R.id.head_image);
		mHeadName = (TextView) findViewById(R.id.head_name);
		mHeadMsgBtn = (Button) findViewById(R.id.head_msg_btn);
		mHeadSign = (TextView) findViewById(R.id.head_sign);
		mHeadFansNum = (TextView) findViewById(R.id.head_fans_num);
		mHeadAttenNum = (TextView) findViewById(R.id.head_atten_num);
		mHeadAttenBtn = (Button) findViewById(R.id.head_atten_btn);
		mProJiaV = (ImageView) findViewById(R.id.head_jiaV);
		mHeadSignIcon = (ImageView) findViewById(R.id.head_sign_icon);
	}

	private void setListeners() {

		mHeadImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApplicationFacade.getInstance().sendNotification(
						AppConst.APP_FACADE_BUSINESS_HOMEFRAGMENT_BOTTOM + getContext().hashCode(),
						mHeadImage);
			}
		});
	}

	public HorizontalListView getmVisitorListView() {
		return mVisitorListView;
	}

	public void setmVisitorListView(HorizontalListView mVisitorListView) {
		this.mVisitorListView = mVisitorListView;
	}

	public ImageView getmHeadLayoutImage() {
		return mHeadLayoutImage;
	}

	public void setmHeadLayoutImage(ImageView mHeadLayoutImage) {
		this.mHeadLayoutImage = mHeadLayoutImage;
	}

	public ImageView getmHeadImage() {
		return mHeadImage;
	}

	public void setmHeadImage(ImageView mHeadImage) {
		this.mHeadImage = mHeadImage;
	}

}

package com.sinaleju.lifecircle.app.customviews.itemview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.model.Model_NoticeComment;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.model.Model_TrendsSimple;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public abstract class AbsTrendsItem extends AbsItemView {

	private static final String TAG = "AbsTrendsItem";
	protected ImageView mImgHeadPortrait;// 头像
	protected ImageView mImgFlag;// 时间轴标识image
	// protected ImageView mImgFlagFrame;// 时间轴标识image
	protected ViewGroup mContentView;

	// protected RelativeLayout mLyotTimeline;

	private View mDividLine;
	private View mTimeLine;

	public AbsTrendsItem(Context context) {
		super(context);
	}

	protected abstract View initContentView();

	protected abstract boolean isNeedHeadPortrait();

	protected abstract void seconderySet(int type, BaseModel model);

	@Override
	protected void onClickThis(BaseModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

	}

	@Override
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_trends_base, this, true);
		mDividLine = findViewById(R.id.line);
		mTimeLine = findViewById(R.id.time_line);
		mImgHeadPortrait = (ImageView) findViewById(R.id.imgHeadPortrait);
		mImgFlag = (ImageView) findViewById(R.id.imgFlag);
		mContentView = (ViewGroup) findViewById(R.id.lyotContent);

		// set
		mImgHeadPortrait.setVisibility(isNeedHeadPortrait() ? View.VISIBLE : View.GONE);

		//
		initContentView();

	}

	@Override
	public void toSet(int type, BaseModel model) {

		if (!(model instanceof Model_TrendsBase)) {
			LogUtils.e(TAG, "class cast error");
			return;
		}

		// set imgs
		Model_TrendsBase m = (Model_TrendsBase) model;

		// whole timeline
		setDividline(m);

		// line
		if (!m.isHideTimeLine()) {
			// header
			setHeader(m);
			// flag
			setFlag(m);
		} else {
			hideTimeLine();
		}
		// secondery set
		seconderySet(type, model);

	}

	private void hideTimeLine() {
		mImgHeadPortrait.setVisibility(View.GONE);
		mImgFlag.setVisibility(View.GONE);
		mTimeLine.setVisibility(View.GONE);
	}

	protected void setDividline(Model_TrendsBase m) {
		mDividLine.setVisibility(m.isHideDividLine() ? View.GONE : View.VISIBLE);
	}

	protected void setFlag(Model_TrendsBase m) {
		if (m.isShowFlag()) {
			mImgFlag.setVisibility(View.VISIBLE);
			setFlagImg(m.getFlagUrl());
			setFlagListener(m);
		} else {
			mImgFlag.setVisibility(View.GONE);
		}
	}

	private void setFlagListener(final Model_TrendsBase m) {
		if (m.getFlag_type() != AppConst.NULL_INT && m.getFlagUid() != AppConst.NULL_INT) {

			mImgFlag.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onFlagClick(m);
				}

			});
		} else {
			mImgFlag.setOnClickListener(null);
		}
	}

	private void setHeader(Model_TrendsBase m) {
		if (m.isShowHeader()) {
			mImgHeadPortrait.setVisibility(View.VISIBLE);
			if (m.getTrendsType() == TrendsType.NOTICE_COMM) {
				m = ((Model_NoticeComment) m).getmCommentMsg();
			} else if (m.getTrendsType() == TrendsType.NOTICE_LIKE) {
				m = ((Model_TrendsMsg) m).getSimple();
			} else if (m.getTrendsType() == TrendsType.NOTICE_ATTEN) {
				m = (Model_TrendsSimple) m;
			}
			final Model_TrendsBase temp = m;
			setHeaderPortrait(m.getHeadPortraitUrl());
			mImgHeadPortrait.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onHeaderClick(temp);
				}
			});
		} else {
			mImgHeadPortrait.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean enable() {
		return true;
	}

	protected void setHeadPortraitVisibility(int visibility) {
		mImgHeadPortrait.setVisibility(visibility);
	}

	protected void setFlagVisibility(int visibility) {
		mImgFlag.setVisibility(visibility);
	}

	protected void setTimeLineVisibility(int visibility) {
		mImgHeadPortrait.setVisibility(View.GONE);
		mImgFlag.setVisibility(View.GONE);
		mTimeLine.setVisibility(View.GONE);
		mDividLine.setVisibility(View.GONE);
	}

	protected void setHeaderPortrait(String url) {
		Model_TrendsBase m = (Model_TrendsBase) mModel;
		TrendsType t_type = m.getTrendsType();
		if (t_type != null
				&& (t_type.equals(TrendsType.JOIN) || t_type.equals(TrendsType.QUIT) || t_type
						.equals(TrendsType.FOLLOW))) {
			mImgHeadPortrait.setBackgroundResource(0);
			mImgHeadPortrait.setImageResource(R.drawable.ic_time_line_header_broadcast);
		} else {
			int res = PublicUtils.getUserDefaultHeadImage(m.getU_type());
			if (url == null || url.equals("")) {
				mImgHeadPortrait.setImageResource(res);
				return;
			}
			ImageLoader.getInstance((Activity) getContext()).displayImage(url, mImgHeadPortrait,
					SimpleImageLoaderOptions.getRoundImageOptions(res, 100));
		}
	}

	// 0 普通消息
	// 1 美食 美食
	// 2 休闲娱乐
	// 3 购物
	// 4 丽人
	// 5 结婚
	// 6 亲子
	// 7 运动健身
	// 8 酒店
	// 9 生活服务
	private static final int[] FLAGS = { R.drawable.nt_putong_hl, R.drawable.nt_meishi_hl,
			R.drawable.nt_yule_hl, R.drawable.nt_gouwu_hl, R.drawable.nt_meifa_hl,
			R.drawable.nt_jiehun_hl, R.drawable.nt_qinzi_hl, R.drawable.nt_jianshen_hl,
			R.drawable.nt_jiudian_hl, R.drawable.nt_jiazheng_hl, R.drawable.nt_fangchan_hl,
			R.drawable.nt_jiajiao_hl };

	protected void setFlagImg(String url) {

		Model_TrendsBase m = (Model_TrendsBase) mModel;
		TrendsType t_type = m.getTrendsType();
		String tag = m.getmTag();
		// mImgFlagFrame.setVisibility(View.GONE);
		mImgFlag.setBackgroundResource(0);
		mImgFlag.setPadding(0, 0, 0, 0);
		mImgFlag.setScaleType(ScaleType.CENTER_CROP);

		// cancel
		ImageLoader.getInstance((Activity) getContext()).cancelDisplayTask(mImgFlag);

		// TrendsType[] types = TrendsType.values();
		TrendsType[] types = TrendsType.class.getEnumConstants();
		// 个人商家
		// MSG("xiaoxi"), FOOD("meishi"), ENTIRMENT("yule"), SHOP("gouwu"),
		// BEAUTY("meirong"), MARRAGE(
		// "hunqing"), CHILDREN("qinzi"), GYM("yundong"), HOTEL("jiudian"),
		// LIFE("jiazheng"), JOIN("jiaxiaoqu"), FOLLOW(
		// "guanzhu"), DIAL("dianhua"), COMMENT("pingjia"), QUIT("tuichu"),
		// JION_LC("jiaquanzi"), CHANG_HEADER(
		// "touxiang"), CHANGE_SIGN("qianming"), NOTICE_COMM(0x12),
		// NOTICE_AT(0x13), NOTICE_ATTEN(
		// 0x14), NOTICE_LIKE(0x15), NOTICE_SYSTEM(0x16);
		if (tag != null && !tag.equals("")) {
			if (tag.equals(TrendsType.MSG.getStrValue())
					|| tag.equals(TrendsType.FOOD.getStrValue())
					|| tag.equals(TrendsType.ENTIRMENT.getStrValue())
					|| tag.equals(TrendsType.SHOP.getStrValue())
					|| tag.equals(TrendsType.BEAUTY.getStrValue())
					|| tag.equals(TrendsType.MARRAGE.getStrValue())
					|| tag.equals(TrendsType.CHILDREN.getStrValue())
					|| tag.equals(TrendsType.GYM.getStrValue())
					|| tag.equals(TrendsType.HOTEL.getStrValue())
					|| tag.equals(TrendsType.LIFE.getStrValue())
					|| tag.equals(TrendsType.FANGCHAN.getStrValue())
					|| tag.equals(TrendsType.JIAJIAO.getStrValue())) {// 发消息
				if (!tag.equals(TrendsType.MSG.getStrValue())) {// 商家消息
					int padding = Math.round(getResources().getDisplayMetrics().density * 1);
					mImgFlag.setPadding(padding, padding, padding, padding);
					mImgFlag.setBackgroundResource(R.drawable.bg_frame_header);
				}
				if (tag.equals(TrendsType.MSG.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[0]);
				} else if (tag.equals(TrendsType.FOOD.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[1]);
				} else if (tag.equals(TrendsType.ENTIRMENT.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[2]);
				} else if (tag.equals(TrendsType.SHOP.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[3]);
				} else if (tag.equals(TrendsType.BEAUTY.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[4]);
				} else if (tag.equals(TrendsType.MARRAGE.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[5]);
				} else if (tag.equals(TrendsType.CHILDREN.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[6]);
				} else if (tag.equals(TrendsType.GYM.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[7]);
				} else if (tag.equals(TrendsType.HOTEL.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[8]);
				} else if (tag.equals(TrendsType.LIFE.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[9]);
				} else if (tag.equals(TrendsType.FANGCHAN.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[10]);
				} else if (tag.equals(TrendsType.JIAJIAO.getStrValue())) {
					mImgFlag.setImageResource(FLAGS[11]);
				}
			}
		}
		if (t_type != null && tag != null && !tag.equals("")
				&& tag.equals(TrendsType.MSG.getStrValue())) {
			if (t_type.equals(TrendsType.COMMENT)) {// 评价了
				mImgFlag.setImageResource(R.drawable.ic_home_page_flag_comment);
			} else if (t_type.equals(TrendsType.DIAL)) {// 拨打服务电话
				int padding = Math.round(getResources().getDisplayMetrics().density * 5);
				mImgFlag.setPadding(padding, padding, padding, padding);
				mImgFlag.setScaleType(ScaleType.FIT_CENTER);
				mImgFlag.setImageResource(R.drawable.ic_home_page_flag_normal);
			} else if (t_type.equals(TrendsType.JION_LC) || t_type.equals(TrendsType.CHANG_HEADER)
					|| t_type.equals(TrendsType.CHANGE_SIGN)) {
				int padding = Math.round(getResources().getDisplayMetrics().density * 5);
				mImgFlag.setPadding(padding, padding, padding, padding);
				mImgFlag.setScaleType(ScaleType.FIT_CENTER);
				mImgFlag.setImageResource(R.drawable.ic_home_page_flag_normal);
			} else if (t_type.equals(TrendsType.MSG)) {
				mImgFlag.setImageResource(FLAGS[0]);
			} else {// 关注，退出、加入小区
				if (t_type.equals(TrendsType.JOIN) || t_type.equals(TrendsType.QUIT)) {
					if (!m.isShowHeader()) {
						int padding = Math.round(getResources().getDisplayMetrics().density * 5);
						mImgFlag.setPadding(padding, padding, padding, padding);
						mImgFlag.setScaleType(ScaleType.FIT_CENTER);
						mImgFlag.setImageResource(R.drawable.ic_home_page_flag_normal);
						return;
					}
				}
				int padding = Math.round(getResources().getDisplayMetrics().density * 1);
				mImgFlag.setPadding(padding, padding, padding, padding);
				mImgFlag.setBackgroundResource(R.drawable.bg_frame_header);
				ImageLoader.getInstance((Activity) getContext()).displayImage(
						m.getHeadPortraitUrl(),
						mImgFlag,
						SimpleImageLoaderOptions.getRoundImageOptions(
								PublicUtils.getUserDefaultHeadImage(m.getU_type()), 100));
			}
		}
		if (t_type != null) {
			if (t_type == TrendsType.NOTICE_AT || t_type == TrendsType.NOTICE_ATTEN
					|| t_type == TrendsType.NOTICE_COMM || t_type == TrendsType.NOTICE_LIKE
					|| t_type == TrendsType.NOTICE_SYSTEM) {
				int padding = Math.round(getResources().getDisplayMetrics().density * 5);
				mImgFlag.setPadding(padding, padding, padding, padding);
				mImgFlag.setScaleType(ScaleType.FIT_CENTER);
				mImgFlag.setImageResource(R.drawable.ic_home_page_flag_normal);
			}
		}

	}

	/**
	 * @param temp
	 */
	protected void onHeaderClick(final Model_TrendsBase temp) {
		if (temp.getTrendsType().equals(TrendsType.FOLLOW)) {
			return;
		}
		if (temp.getTrendsType() == TrendsType.NOTICE_COMM
				|| temp.getTrendsType() == TrendsType.NOTICE_LIKE
				|| temp.getTrendsType() == TrendsType.NOTICE_ATTEN) {
			AppContext.performVisitPeople(getContext(), temp.getFlagUid(), temp.getFlag_type());
			return;
		}
		AppContext.performVisitPeople(getContext(), temp.getUid(), temp.getU_type());
	}

	/**
	 * @param m
	 */
	protected void onFlagClick(final Model_TrendsBase m) {
		int uid = m.getFlagUid();
		int type = m.getFlag_type();
		if (m.getTrendsType().getStrValue().equals(TrendsType.FOLLOW.getStrValue())) {
			uid = m.getUid();
			type = m.getU_type();
		}

		AppContext.performVisitPeople(getContext(), uid, type);
	}

}

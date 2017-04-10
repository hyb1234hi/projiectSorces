package com.sinaleju.lifecircle.app.customviews;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.TrendsType;
import com.sinaleju.lifecircle.app.utils.ADAnimationUtils;
import com.sinaleju.lifecircle.app.utils.ADAnimationUtils.ZoomAnchor;

public class HomePageSelectorView extends LinearLayout implements OnClickListener {
	private LinearLayout  rg_1;
	private Button mCbAll;
	private Button mCbFood;
	private Button mCbEntertainment;
	private Button mCbShopping;
	private Button mCbBeauty;
	private LinearLayout  rg_2;
	private Button mCbMarrige;
	private Button mCbBaby;
	private Button mCbGym;
	private Button mCbHotel;
	private Button mCbLife;
	private Button mCbJiajiao;
	private Button mCbQinzi;
	private String cate = null;
//
//	private RadioGroup mRadioGroupUser;
//	private RadioButton mUserAll;
//	private RadioButton mUserPersonal;
//	private RadioButton mUserProperty;
	private RadioButton mUserMerchant;
//
//	private RadioGroup mRadioGroupVIP;
//	private RadioButton mVipAll;
//	private RadioButton mVipYet;
//	private RadioButton mVipNot;
//
//	private RadioGroup mRadioGroupFollow;
//	private RadioButton mFollowAll;
//	private RadioButton mFollowYet;
//	private RadioButton mFollowNot;

	private View mLyotMerchant;
	
	private List<Button> mCBList = new LinkedList<Button>();
	/***/
	private int mUserArg = -1;
	private int mVIPArg = -1;
	private int mFollowArg = -1;
	/**所属分类*/
	private String tag = "";
	
	private ICallRefreshableView back;
	public ICallRefreshableView getBack() {
		return back;
	}

	public void setBack(ICallRefreshableView back) {
		this.back = back;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String mCate) {
		this.tag = mCate;
	}

	private boolean isShowing;

	public HomePageSelectorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HomePageSelectorView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		initViews();
		initCBList();

		// hideMerchantZone();
		// hide();
	}

	private void initViews() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_home_page_selector, this);

		mLyotMerchant = findViewById(R.id.lyotMerchant);

		rg_1 = (LinearLayout) findViewById(R.id.rg_1);
		mCbAll = (Button) findViewById(R.id.buttonAll);
		mCbFood = (Button) findViewById(R.id.buttonFood);
		mCbEntertainment = (Button) findViewById(R.id.buttonPlay);
		mCbShopping = (Button) findViewById(R.id.buttonShopping);
		mCbBeauty = (Button) findViewById(R.id.buttonBeauty);
		
		rg_2 = (LinearLayout) findViewById(R.id.rg_2);
		mCbMarrige = (Button) findViewById(R.id.buttonMarrige);
		mCbBaby = (Button) findViewById(R.id.buttonBaby);
		mCbGym = (Button) findViewById(R.id.buttonGym);
		mCbHotel = (Button) findViewById(R.id.buttonHotel);
		mCbLife = (Button) findViewById(R.id.buttonLife);
		
		mCbJiajiao = (Button)findViewById(R.id.buttonJiajiao);
		mCbQinzi = (Button)findViewById(R.id.buttonQinzi);
		
		mCbAll.setOnClickListener(this);
		mCbFood.setOnClickListener(this);
		mCbEntertainment.setOnClickListener(this);
		mCbShopping.setOnClickListener(this);
		mCbBeauty.setOnClickListener(this);
		mCbMarrige.setOnClickListener(this);
		mCbBaby.setOnClickListener(this);
		mCbGym.setOnClickListener(this);
		mCbHotel.setOnClickListener(this);
		mCbLife.setOnClickListener(this);
		mCbJiajiao.setOnClickListener(this);
		mCbQinzi.setOnClickListener(this);
	}

	private void initCBList() {
		mCBList.add(mCbFood);
		mCBList.add(mCbEntertainment);
		mCBList.add(mCbShopping);
		mCBList.add(mCbBeauty);
		mCBList.add(mCbMarrige);
		mCBList.add(mCbBaby);
		mCBList.add(mCbGym);
		mCBList.add(mCbHotel);
		mCBList.add(mCbLife);
	}
	public String getMerchantCate() {
		String cate = null;
		cate = this.cate;
//		LogUtils.e(VIEW_LOG_TAG, "sb  ::  "+sb +"  cate  :: " + cate);
		return cate;
	}

	private boolean canSetCheckAll = true;
	private boolean canSetCheck = true;

	private void onlySetUIForCompoundButtonCheck(CompoundButton b, boolean c) {
		canSetCheck = false;
		b.setChecked(c);
		canSetCheck = true;
	}
	private int i = 0;
	
	private void showMerchantZone() {
		if (mLyotMerchant.getVisibility() == View.GONE)
			mLyotMerchant.setVisibility(View.VISIBLE);
		setVisibility(View.VISIBLE);
	}

	private void hideMerchantZone() {
		if (mLyotMerchant.getVisibility() == View.VISIBLE)
			mLyotMerchant.setVisibility(View.GONE);
		setVisibility(View.INVISIBLE);
	}

	public void toggle() {
		if (getVisibility() == View.GONE || getVisibility() == View.INVISIBLE) {
			show(false);
		} else {
			hide(false);
		}
	}

	public void show(boolean anim) {
		setShowing(true);
		if (anim)
			ADAnimationUtils.zoomOut(this, ZoomAnchor.LeftBottom);
		else
			setVisibility(View.VISIBLE);
		showMerchantZone();
	}

	public void hide(boolean anim) {
		setShowing(false);
		if (anim)
			ADAnimationUtils.zoomIn(this, ZoomAnchor.LeftBottom);
		else
			setVisibility(View.INVISIBLE);
		
		hideMerchantZone();
	}
	
	private void setUserArg(int arg) {
		this.mUserArg = arg;
	}

	private void setVIPArg(int arg) {
		this.mVIPArg = arg;
	}

	private void setFollowArg(int arg) {
		this.mFollowArg = arg;
	}

	public int getUserArg() {
		return mUserArg;
	}

	public int getVIPArg() {
		return mVIPArg;
	}

	public int getFollowArg() {
		return mFollowArg;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return true;
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}
	/**
	 * 回调  刷新 
	 * @author issuser
	 *
	 */
	public interface ICallRefreshableView{
		void onBack();
	}
	
	private void setDefaultBg(){
		mCbAll.setBackgroundResource(R.drawable.nt_quanbu);
		mCbFood.setBackgroundResource(R.drawable.nt_meishi);
		mCbEntertainment.setBackgroundResource(R.drawable.nt_yule);
		mCbShopping.setBackgroundResource(R.drawable.nt_gouwu);
		mCbBeauty.setBackgroundResource(R.drawable.nt_meifa);
		mCbMarrige.setBackgroundResource(R.drawable.nt_jiehun);
		mCbBaby.setBackgroundResource(R.drawable.nt_fangchan);
		mCbGym.setBackgroundResource(R.drawable.nt_jianshen);
		mCbHotel.setBackgroundResource(R.drawable.nt_jiudian);
		mCbLife.setBackgroundResource(R.drawable.nt_jiazheng);
		mCbJiajiao.setBackgroundResource(R.drawable.nt_jiajiao);
		mCbQinzi.setBackgroundResource(R.drawable.nt_qinzi);
	}
	public void setAllSelect(){
		mCbAll.setBackgroundResource(R.drawable.nt_quanbu_hl);
		mCbFood.setBackgroundResource(R.drawable.nt_meishi_hl);
		mCbEntertainment.setBackgroundResource(R.drawable.nt_yule_hl);
		mCbShopping.setBackgroundResource(R.drawable.nt_gouwu_hl);
		mCbBeauty.setBackgroundResource(R.drawable.nt_meifa_hl);
		mCbMarrige.setBackgroundResource(R.drawable.nt_jiehun_hl);
		mCbBaby.setBackgroundResource(R.drawable.nt_fangchan_hl);
		mCbGym.setBackgroundResource(R.drawable.nt_jianshen_hl);
		mCbHotel.setBackgroundResource(R.drawable.nt_jiudian_hl);
		mCbLife.setBackgroundResource(R.drawable.nt_jiazheng_hl);
		mCbJiajiao.setBackgroundResource(R.drawable.nt_jiajiao_hl);
		mCbQinzi.setBackgroundResource(R.drawable.nt_qinzi_hl);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == mCbAll.getId()){
			setAllSelect();
			setTag(null);
		}else if (v.getId() == mCbLife.getId()) {
			setDefaultBg();
			mCbLife.setBackgroundResource(R.drawable.nt_jiazheng_hl);
			setTag(TrendsType.LIFE.getStrValue());
		}else if (v.getId() == mCbBaby.getId()) {
			setDefaultBg();
			mCbBaby.setBackgroundResource(R.drawable.nt_fangchan_hl);
			setTag(TrendsType.FANGCHAN.getStrValue());
		}else if (v.getId() == mCbGym.getId()) {
			setDefaultBg();
			mCbGym.setBackgroundResource(R.drawable.nt_jianshen_hl);
			setTag(TrendsType.GYM.getStrValue());
		}else if (v.getId() == mCbBeauty.getId()) {
			setDefaultBg();
			mCbBeauty.setBackgroundResource(R.drawable.nt_meifa_hl);
			setTag(TrendsType.BEAUTY.getStrValue());
		}else if (v.getId() == mCbFood.getId()) {
			setDefaultBg();
			mCbFood.setBackgroundResource(R.drawable.nt_meishi_hl);
			setTag(TrendsType.FOOD.getStrValue());
		}else if (v.getId() == mCbEntertainment.getId()) {
			setDefaultBg();
			mCbEntertainment.setBackgroundResource(R.drawable.nt_yule_hl);
			setTag(TrendsType.ENTIRMENT.getStrValue());
		}else if (v.getId() == mCbJiajiao.getId()) {
			setDefaultBg();
			mCbJiajiao.setBackgroundResource(R.drawable.nt_jiajiao_hl);
			setTag(TrendsType.JIAJIAO.getStrValue());
		}else if (v.getId() == mCbHotel.getId()) {
			setDefaultBg();
			mCbHotel.setBackgroundResource(R.drawable.nt_jiudian_hl);
			setTag(TrendsType.HOTEL.getStrValue());
		}else if (v.getId() == mCbShopping.getId()) {
			setDefaultBg();
			mCbShopping.setBackgroundResource(R.drawable.nt_gouwu_hl);
			setTag(TrendsType.SHOP.getStrValue());
		}else if (v.getId() == mCbMarrige.getId()) {
			setDefaultBg();
			mCbMarrige.setBackgroundResource(R.drawable.nt_jiehun_hl);
			setTag(TrendsType.MARRAGE.getStrValue());
		}else if (v.getId() == mCbQinzi.getId()) {
			setDefaultBg();
			mCbQinzi.setBackgroundResource(R.drawable.nt_qinzi_hl);
			setTag(TrendsType.CHILDREN.getStrValue());
		}
		//点击选择后   回调
		if(back != null){
			back.onBack();
		}
//		hide(true);
	}
}

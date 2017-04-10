package com.sinaleju.lifecircle.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.HotTopicAct;
import com.sinaleju.lifecircle.app.activity.MerchantListActivity;
import com.sinaleju.lifecircle.app.activity.NearbyBusinessDetailAct;
import com.sinaleju.lifecircle.app.activity.ServiceDetailAct;
import com.sinaleju.lifecircle.app.base_fragment.BaseFragment;
import com.sinaleju.lifecircle.app.customviews.NoSlideGridView;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSCommunityInfoWithPro;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @ClassName: RightHomeFragment
 * @Description: TODO 首页右滑界面
 * @author zhenwei
 * @date 2013-8-23 下午12:08:08
 * 
 */
public class RightHomeFragment extends BaseFragment implements OnClickListener {

	private View mContentView = null;

	private ScrollView rightFragmentLinear;
	// private LinearLayout loadingLinear;
	private TextView title, topicCount_hot, topicCount_ticket,
			ticket_topicName;
	private LinearLayout hotSUbject, property, squareLayout, nearbyLayout,
			propertyLayout, merchantLayout;
	private GridView propertyServiceGV;
	private NoSlideGridView nearbyGV, merchantCategoryGV;

	private ArrayList<HashMap<String, Object>> propertyServiceList;
	private ArrayList<HashMap<String, Object>> nearbyList,
			merchantCategoryList;
	private ArrayList<ArrayList<HashMap<String, Object>>> listData;

	private final int MERCHANT_CATEGORY_STYLE = 0;
	private final int PROPERTY_SERVICE_SYTLE = 1;
	private final int NEARBY_BUSINESS_SYTLE = 2;

	public static String USER_ID_KEY = "userid";

	public static final int INVALIDATE_USER_ID = 0;

	private static final String TAG = "RightHomeFragment";

	private String communityName;

	private int communityId = INVALIDATE_USER_ID;
	private int propertyId;
	private int communityType = 1;

	private int lastCommunityId = -1;

	private RSCommunityInfoWithPro rs;

	private HashMap<String, Object> propertyData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LogUtils.v(TAG, "-------------onCreateView--------------");
		mContentView = inflater.inflate(R.layout.fr_right_view, container,
				false);

		initView();
		initData();
		setListener();
		return mContentView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		LogUtils.e(TAG, "onCreate...");
		registMediator();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		unregistMsediator();
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		if (communityId == INVALIDATE_USER_ID) {
			try {
				if (AppContext.isVisitor()) {
					communityId = AppContext.curVisitor().getCommunity_id();
				} else {
					communityId = AppContext.curUser().getCurrentCommunityId();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int id = arg0.getId();
		Object item = arg0.getTag();
		ItemInfo itemInfo = (ItemInfo) ((HashMap<String, Object>) item)
				.get("iteminfo");
		String categoryName = (String) ((HashMap<String, Object>) item)
				.get("name");

		if (null != itemInfo) {
			int style = itemInfo.getStyle();
			int index = itemInfo.getIndex();
			Intent intent = null;
			switch (style) {
			case MERCHANT_CATEGORY_STYLE: // 商家分类
				//Toast.makeText(getActivity(),"index: " + index + " name: " + categoryName, 0).show();
				MobclickAgent.onEvent(getActivity(), PublicUtils.MOBCLICKAGENT_AROUND_MERCHANT);
				Intent merchantIntent = new Intent(getActivity(),
						MerchantListActivity.class);
				merchantIntent.putExtra("merchant_type", index + "");
				startActivity(merchantIntent);
				break;

			case PROPERTY_SERVICE_SYTLE: // 物业服务
				intent = new Intent(getActivity(), ServiceDetailAct.class);
				intent.putExtra("service_id", index);
				intent.putExtra("community_id", communityId);
				if (AppContext.curUser() != null)
					intent.putExtra(USER_ID_KEY, AppContext.curUser().getUid());
				startActivity(intent);
				break;
			case NEARBY_BUSINESS_SYTLE:// 周边商家
				if (communityId == INVALIDATE_USER_ID) {
					Toast.makeText(getActivity(), "没有小区信息,或小区信息加载失败！",
							Toast.LENGTH_LONG).show();
					return;
				}
				intent = new Intent(getActivity(),
						NearbyBusinessDetailAct.class);
				intent.putExtra("category_id", index);
				intent.putExtra("community_id", communityId);
				intent.putExtra("community_name", categoryName);
				if (AppContext.curUser() != null)
					intent.putExtra(USER_ID_KEY, AppContext.curUser().getUid());
				startActivity(intent);
				break;
			}
		}

	}

	@Override
	protected void initView() {
		LogUtils.e(TAG, "initView......");
		// TODO Auto-generated method stub
		rightFragmentLinear = (ScrollView) mContentView
				.findViewById(R.id.right_home_fragment_container);
		// loadingLinear =
		// (LinearLayout)mContentView.findViewById(R.id.right_fragment_loading_linear);
		title = (TextView) mContentView
				.findViewById(R.id.right_fragment_title_textview);
		topicCount_hot = (TextView) mContentView
				.findViewById(R.id.hot_topic_count);
		topicCount_ticket = (TextView) mContentView
				.findViewById(R.id.square_hot_topic_count);
		ticket_topicName = (TextView) mContentView
				.findViewById(R.id.topic_name);
		hotSUbject = (LinearLayout) mContentView
				.findViewById(R.id.fr_right_view_hot_btn);
		property = (LinearLayout) mContentView
				.findViewById(R.id.fr_right_view_property_btn);
		squareLayout = (LinearLayout) mContentView
				.findViewById(R.id.fr_right_view_ticket_ll);
		nearbyLayout = (LinearLayout) mContentView
				.findViewById(R.id.fr_right_nearby_ll);
		propertyLayout = (LinearLayout) mContentView
				.findViewById(R.id.fr_right_property_service_ll);
		merchantLayout = (LinearLayout) mContentView
				.findViewById(R.id.fr_right_merchantcategory_ll);

		propertyServiceGV = (GridView) mContentView
				.findViewById(R.id.fr_right_property_service_gv);
		nearbyGV = (NoSlideGridView) mContentView
				.findViewById(R.id.fr_right_nearby_gv);
		merchantCategoryGV = (NoSlideGridView) mContentView
				.findViewById(R.id.fr_right_merchantcategory_gv);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		LogUtils.e(TAG, "initData.....");
		// 商家分类数据列表
		merchantCategoryList = new ArrayList<HashMap<String, Object>>();

		ItemInfo info = new ItemInfo();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// map = new HashMap<String, Object>();
		// info=new ItemInfo();
		info.setStyle(MERCHANT_CATEGORY_STYLE);
		info.setIndex(100000);
		map.put("iteminfo", info);
		map.put("name", "家居");
		map.put("image", R.drawable.fr_right_view_share_jiaju);
		merchantCategoryList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(MERCHANT_CATEGORY_STYLE);
		info.setIndex(110000);
		map.put("iteminfo", info);
		map.put("name", "建材");
		map.put("image", R.drawable.fr_right_view_share_jiancai);
		merchantCategoryList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(MERCHANT_CATEGORY_STYLE);
		info.setIndex(120000);
		map.put("iteminfo", info);
		map.put("name", "房产");
		map.put("image", R.drawable.fr_right_view_share_fangchan);
		merchantCategoryList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(MERCHANT_CATEGORY_STYLE);
		info.setIndex(130000);
		map.put("iteminfo", info);
		map.put("name", "旅行");
		map.put("image", R.drawable.fr_right_view_share_lvxing);
		merchantCategoryList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(MERCHANT_CATEGORY_STYLE);
		info.setIndex(140000);
		map.put("iteminfo", info);
		map.put("name", "汽车");
		map.put("image", R.drawable.fr_right_view_share_qiche);
		merchantCategoryList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(MERCHANT_CATEGORY_STYLE);
		info.setIndex(150000);
		map.put("iteminfo", info);
		map.put("name", "装潢");
		map.put("image", R.drawable.fr_right_view_share_zhuanghuang);
		merchantCategoryList.add(map);

		// 物业服务数据列表
		propertyServiceList = new ArrayList<HashMap<String, Object>>();

		/*
		 * propertyData = new HashMap<String, Object>();
		 * info.setStyle(PROPERTY_SERVICE_SYTLE); info.setIndex(1);
		 * propertyData.put("iteminfo", info); propertyData.put("name", "物业管理");
		 * propertyData.put("image", R.drawable.fr_right_view_hover_btn1);
		 * propertyServiceList.add(propertyData);
		 */

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(2);
		map.put("iteminfo", info);
		map.put("name", "供水报修");
		map.put("image", R.drawable.fr_right_view_hover_btn2);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(3);
		map.put("iteminfo", info);
		map.put("name", "供电报修");
		map.put("image", R.drawable.fr_right_view_hover_btn3);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(4);
		map.put("iteminfo", info);
		map.put("name", "供气报修");
		map.put("image", R.drawable.fr_right_view_hover_btn4);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(5);
		map.put("iteminfo", info);
		map.put("name", "歌华有线");
		map.put("image", R.drawable.fr_right_view_hover_btn5);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(6);
		map.put("iteminfo", info);
		map.put("name", "电信宽带");
		map.put("image", R.drawable.fr_right_view_hover_btn6);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(7);
		map.put("iteminfo", info);
		map.put("name", "联通宽带");
		map.put("image", R.drawable.fr_right_view_hover_btn7);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(8);
		map.put("iteminfo", info);
		map.put("name", "移动宽带");
		map.put("image", R.drawable.fr_right_view_hover_btn8);
		propertyServiceList.add(map);

		/*
		 * map = new HashMap<String, Object>(); info = new ItemInfo();
		 * info.setStyle(PROPERTY_SERVICE_SYTLE); info.setIndex(9);
		 * map.put("iteminfo", info); map.put("name", "铁通宽带"); map.put("image",
		 * R.drawable.fr_right_view_hover_btn9); propertyServiceList.add(map);
		 */
		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(10);
		map.put("iteminfo", info);
		map.put("name", "长城宽带");
		map.put("image", R.drawable.fr_right_view_hover_btn10);
		propertyServiceList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(PROPERTY_SERVICE_SYTLE);
		info.setIndex(11);
		map.put("iteminfo", info);
		map.put("name", "方正宽带");
		map.put("image", R.drawable.fr_right_view_hover_btn11);
		propertyServiceList.add(map);

		// 周边商家数据列表：家政、房产、运动健身、美容美发、美食、休闲娱乐、家教、酒店住宿、购物、结婚、亲子。
		nearbyList = new ArrayList<HashMap<String, Object>>();

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(1);
		map.put("iteminfo", info);
		map.put("name", "家政");
		map.put("image", R.drawable.fr_right_view_hover_jiazheng);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(2);
		map.put("iteminfo", info);
		map.put("name", "房产");
		map.put("image", R.drawable.fr_right_view_hover_fangchan);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(3);
		map.put("iteminfo", info);
		map.put("name", "运动健身");
		map.put("image", R.drawable.fr_right_view_hover_btn18);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(4);
		map.put("iteminfo", info);
		map.put("name", "美容美发");
		map.put("image", R.drawable.fr_right_view_hover_meirong);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(5);
		map.put("iteminfo", info);
		map.put("name", "美食");
		map.put("image", R.drawable.fr_right_view_hover_btn12);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(6);
		map.put("iteminfo", info);
		map.put("name", "休闲娱乐");
		map.put("image", R.drawable.fr_right_view_hover_btn13);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(7);
		map.put("iteminfo", info);
		map.put("name", "家教");
		map.put("image", R.drawable.fr_right_view_hover_jiajiao);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(8);
		map.put("iteminfo", info);
		map.put("name", "酒店住宿");
		map.put("image", R.drawable.fr_right_view_hover_btn19);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(9);
		map.put("iteminfo", info);
		map.put("name", "购物");
		map.put("image", R.drawable.fr_right_view_hover_btn14);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(10);
		map.put("iteminfo", info);
		map.put("name", "结婚");
		map.put("image", R.drawable.fr_right_view_hover_btn16);
		nearbyList.add(map);

		map = new HashMap<String, Object>();
		info = new ItemInfo();
		info.setStyle(NEARBY_BUSINESS_SYTLE);
		info.setIndex(11);
		map.put("iteminfo", info);
		map.put("name", "亲子");
		map.put("image", R.drawable.fr_right_view_hover_btn17);
		nearbyList.add(map);

		GVAdapter merchantAdapter = new GVAdapter(merchantCategoryList);
		merchantCategoryGV.setAdapter(merchantAdapter);

		GVAdapter propertyServiceAdapter = new GVAdapter(propertyServiceList);
		propertyServiceGV.setAdapter(propertyServiceAdapter);

		GVAdapter nearbyGVAdapter = new GVAdapter(nearbyList);
		nearbyGV.setAdapter(nearbyGVAdapter);

	}

	@Override
	protected void setListener() {

		squareLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int communityId = INVALIDATE_USER_ID;
				try {
					if (AppContext.isVisitor()) {
						communityId = AppContext.curVisitor().getCommunity_id();
					} else {
						communityId = AppContext.curUser()
								.getCurrentCommunityId();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (communityId == INVALIDATE_USER_ID) {
					Toast.makeText(getActivity(), "没有小区信息,或小区信息加载失败！",
							Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(getActivity(), HotTopicAct.class);
				intent.putExtra("community_id", communityId);// 小区id
				startActivity(intent);
			}
		});
		hotSUbject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int communityId = INVALIDATE_USER_ID;
				try {
					if (AppContext.isVisitor()) {
						communityId = AppContext.curVisitor().getCommunity_id();
					} else {
						communityId = AppContext.curUser()
								.getCurrentCommunityId();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (communityId == INVALIDATE_USER_ID) {
					Toast.makeText(getActivity(), "没有小区信息,或小区信息加载失败！",
							Toast.LENGTH_LONG).show();
					return;
				}
				MobclickAgent.onEvent(getActivity(), PublicUtils.MOBCLICKAGENT_TOPIC);
				Intent intent = new Intent(getActivity(), HotTopicAct.class);
				intent.putExtra("community_id", communityId);// 小区id
				startActivity(intent);
			}
		});

		property.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AppContext.gotoIndexActivity(getActivity(), 2, propertyId);
			}
		});

	}

	public void updateCommunity(String community) {
		// 给标题赋值
		// this.communityName = community;
		// if(null != title) title.setText(community);
		// 取当前小区的小区id和物业id
		LogUtils.e(TAG, "communityName: " + community);
		try {
			if (AppContext.isVisitor()) {
				communityId = AppContext.curVisitor().getCommunity_id();
				if (null != AppContext.curVisitor().getCommunity()) {
					// 热门话题的数量
					// ((TextView)hotSUbject.findViewById(R.id.hot_topic_count))
					// .setText(String.valueOf(AppContext.curVisitor().getCommunity().getTopicCount()));
					String topicCount = String.valueOf(AppContext.curVisitor()
							.getTopicCount());
					topicCount_hot.setText(topicCount);
					topicCount_ticket.setText(topicCount);

					propertyId = AppContext.curVisitor().getCommunity()
							.getProperty_id();
					LogUtils.e(TAG, "topicCount: " + topicCount+"propertyId: "+propertyId);
				}
			} else {
				communityId = AppContext.curUser().getCurrentCommunityId();
				communityType = AppContext.curUser().getCurCommunity()
						.getType();
				if (null != AppContext.curUser().getCurCommunity()) {
					// 热门话题的数量
					String topicCount = String.valueOf(AppContext.curUser()
							.getCurCommunity().getTopicCount());
					topicCount_hot.setText(topicCount);
					topicCount_ticket.setText(topicCount);

					propertyId = AppContext.curUser().getCurCommunity()
							.getProperty_id();
					LogUtils.e(TAG, "topicCount: " + topicCount+"propertyId: "+propertyId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果当前用户在主页面选择了“券享中国”，则加载“商家分类"内容。
		LogUtils.i(TAG, "communityId: " + communityId + " type "
				+ communityType);

		if (/* communityId==1 */communityType == 2) {
			title.setText("分类");
			ticket_topicName.setText("话题广场");
			merchantLayout.setVisibility(View.VISIBLE);
			nearbyLayout.setVisibility(View.GONE);
			propertyLayout.setVisibility(View.GONE);
		} else {
			title.setText("本小区百事通");
			ticket_topicName.setText("热门话题");
			merchantLayout.setVisibility(View.GONE);
			nearbyLayout.setVisibility(View.VISIBLE);
			propertyLayout.setVisibility(View.VISIBLE);
		}

		// 如果有物业 则以两列形式分别显示热门话题和物业。
		if (propertyId != 0) {
			squareLayout.setVisibility(View.GONE);
			hotSUbject.setVisibility(View.VISIBLE);
			property.setVisibility(View.VISIBLE);
		}else{
			squareLayout.setVisibility(View.VISIBLE);
			hotSUbject.setVisibility(View.GONE);
			property.setVisibility(View.GONE);
		}
		LogUtils.e(TAG, "update");
	}

	class ItemInfo {
		// 是物业服务还是周边商家
		private int style;
		private int propertyId;
		private int index;

		public int getStyle() {
			return style;
		}

		public void setStyle(int style) {
			this.style = style;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getPropertyId() {
			return propertyId;
		}

		public void setPropertyId(int propertyId) {
			this.propertyId = propertyId;
		}
	}

	class GVAdapter extends BaseAdapter {

		private ArrayList<HashMap<String, Object>> listData;

		public GVAdapter(ArrayList<HashMap<String, Object>> listData) {
			this.listData = listData;
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (null == convertView) {
				convertView = View.inflate(getActivity(),
						R.layout.fr_right_gv_item, null);
			}

			Button btn = (Button) convertView
					.findViewById(R.id.fr_right_item_btn);
			TextView tv = (TextView) convertView
					.findViewById(R.id.fr_right_item_text);

			btn.setBackgroundResource((Integer) listData.get(position).get(
					"image"));
			tv.setText((String) listData.get(position).get("name"));

			btn.setTag(listData.get(position));
			btn.setOnClickListener(RightHomeFragment.this);
			return convertView;
		}

	}

	public void registMediator() {
		LogUtils.e(TAG, "registMediator....");
		ApplicationFacade.getInstance().registerMediator(
				new RightHomeMediator(RightHomeMediator.NAME, null));
	}

	private void unregistMsediator() {
		ApplicationFacade.getInstance().removeMediator(RightHomeMediator.NAME);
	}

	private class RightHomeMediator extends Mediator {
		public static final String NAME = "RightHomeFragment";

		public RightHomeMediator(String mediatorName, Object viewComponent) {
			super(mediatorName, viewComponent);
		}

		@Override
		public void handleNotification(INotification n) {
			String name = n.getName();
			LogUtils.e(TAG, "handleNotification  " + name);
			Object body = n.getBody();
			if (name.equals(AppConst.APP_FACADE_RIGHT_HOME_FRAGMENT_USER_UI_REFRESH)) {
				updateCommunity(body.toString());
			}
		}

		@Override
		public String[] listNotificationInterests() {
			return new String[] { AppConst.APP_FACADE_RIGHT_HOME_FRAGMENT_USER_UI_REFRESH };
		}

	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart(TAG);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

}
package com.sinaleju.lifecircle.app.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.BusinessDistanceView;
import com.sinaleju.lifecircle.app.customviews.BusinessDistanceView.ClickCallback;
import com.sinaleju.lifecircle.app.fragment.RightHomeFragment;
import com.sinaleju.lifecircle.app.model.NearbyBusinessBean;
import com.sinaleju.lifecircle.app.model.json.JSONParser_NearbyBusinessList;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSNearbyBusinessDetail;
import com.sinaleju.lifecircle.app.utils.DialogUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

/**
 * 
 * @ClassName: NearbyBusinessDetailAct
 * @Description: TODO 附近商家列表
 * @author zhenwei
 * @date 2013-8-23 下午12:05:06
 * 
 */
public class NearbyBusinessDetailAct extends BaseActivity implements OnClickListener {

	private static final String TAG = "NearbyBusinessDetailAct";
	private BusinessDistanceView distanceView;
	private EditText searchET;
	private Button deleteBtn, searchBtn;
	private ListView listView;
//	private ImageView mBaseImageView;
	private RelativeLayout rl_near_frame = null;
	private ImageView iv_near_2 = null;
	private RSNearbyBusinessDetail rs;

	private int community_id;// 所选小区id；
	private int category_id;// 商家分类id；
	private int distance;// 距离；
	private String key_word;// 搜索关键字

	private ArrayList<NearbyBusinessBean> listData;
	private HashMap<String, ArrayList<NearbyBusinessBean>> hashMapCache;
	private TextView mTv_topic;

	// private HashMap<String, String> params;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.nearby_business_detail;
	}

	@Override
	protected void initView() {

		LogUtils.v(TAG, "-------------onCreate--------------");
		searchET = (EditText) findViewById(R.id.search_et);
		distanceView = (BusinessDistanceView) findViewById(R.id.nearby_business_distance_view);
		searchBtn = (Button) findViewById(R.id.search_btn);
		deleteBtn = (Button) findViewById(R.id.delete_btn);
		listView = (ListView) findViewById(R.id.nearby_business_listview);
		rl_near_frame = (RelativeLayout)findViewById(R.id.rl_near_frame);
		iv_near_2 = (ImageView)findViewById(R.id.iv_near_2);
//		mBaseImageView = (ImageView) findViewById(R.id.base_fudong_image);

		if (((AppContext) getApplication()).ismNeedShowBusList()) {
			((AppContext) getApplication()).setmNeedShowBusList(false);
			((AppContext) getApplication()).updateNeedShowBusList();
			showFuDongView();
		}

		deleteBtn.setOnClickListener(this);
		searchBtn.setOnClickListener(this);

		String categoryName = getIntent().getStringExtra("community_name");

		mTitleBar.setTitleName(categoryName);
		mTv_topic = (TextView) findViewById(R.id.topic);
		mTv_topic.setText("#" + categoryName + "#");

		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);
		mTitleBar.setLeftButtonListener(this);

		community_id = getIntent()
				.getIntExtra("community_id", RightHomeFragment.INVALIDATE_USER_ID);
		category_id = getIntent().getIntExtra("category_id", RightHomeFragment.INVALIDATE_USER_ID);
		distance = 0;
		key_word = "";

		hashMapCache = new HashMap<String, ArrayList<NearbyBusinessBean>>();
		loadData(true);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				AppContext.gotoIndexActivity(NearbyBusinessDetailAct.this, 1,
						Integer.valueOf(listData.get(arg2).getVisitor_id()));
			}
		});

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initCallbacks() {

		mTv_topic.setOnClickListener(this);
		distanceView.setClickCallback(new ClickCallback() {

			@Override
			public void clickCallback(int position) {
				switch (position) {
				case 0:
					distance = 1;
					break;
				case 1:
					distance = 2;
					break;
				case 2:
					distance = 3;
					break;
				case 3:
					distance = 4;
					break;
				}
				rs = null;
				loadData(true);
			}
		});

		searchET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				key_word = s.toString().replace(" ", "");
				// rs = null;
				if (!TextUtils.isEmpty(key_word)) {
					searchFromListData(key_word);
				} else {
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("community_id", String.valueOf(community_id));
					params.put("category_id", String.valueOf(category_id));
					if (distance != 0)
						params.put("distance", String.valueOf(distance));
					ArrayList<NearbyBusinessBean> listData = hashMapCache.get(params.toString());
					if (null != listData && listData.size() != 0) {
						fillData(listData, false);
					} else {
						loadData(true);
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.topic:
			if (AppContext.isValid(mActivity)) {
				String topic = mTv_topic.getText().toString();
				Intent SendIntent = new Intent(this, SendMsgMainActivity.class);
				SendIntent.putExtra(AppConst.INTENT_MSG_TOPIC_ID, -1);
				SendIntent.putExtra(AppConst.INTENT_MSG_TOPIC_TEXT, topic);
				startActivity(SendIntent);
			}

			break;
		case R.id.delete_btn:
			if (!"".equals(searchET.getText().toString().trim())) {
				searchET.setText("");
				key_word = "";
			}
			break;
		case R.id.imavBack:
			PublicUtils.hideSoftInputMethod(NearbyBusinessDetailAct.this);
			finish();
			break;
		case R.id.nearby_item_tele_btn:
			DialogUtils.showDialDialog(this, (String) v.getTag());
			break;
		case R.id.search_btn:
			key_word = searchET.getText().toString().replace(" ", "");
			if (!TextUtils.isEmpty(key_word)) {
				loadData(true);
			}
			break;
		}
	}

	private void loadData(final boolean isShowDialog) {
		if (rs != null)
			rs.cancel();

		rs = new RSNearbyBusinessDetail();
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("community_id", String.valueOf(community_id));
		params.put("category_id", String.valueOf(category_id));
		if (distance != 0)
			params.put("distance", String.valueOf(distance));
		if (!TextUtils.isEmpty(key_word))
			params.put("key_word", key_word);

		listData = hashMapCache.get(params.toString());
		if (null != listData) {
			fillData(listData, isShowDialog);
			return;
		}

		rs.setParams(params);

		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();

				if (null != result) {
					listData = JSONParser_NearbyBusinessList.parser(result.toString());
					hashMapCache.put(params.toString(), listData);
					fillData(listData, isShowDialog);
				} else {
					if (isShowDialog)
						showToast(getString(R.string.empty_data));
				}
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				listView.setAdapter(null);
				iv_near_2.setVisibility(View.GONE);
				if (isShowDialog)
					showToast(getString(R.string.net_error));
			}
		});

		if (isShowDialog)
			showProgressDialog(getString(R.string.loading_data), true);
		rs.asyncExecute(this);
	}

	@Override
	protected void cancelTask() {
		if (rs != null)
			rs.cancel();
	}

	private void fillData(ArrayList<NearbyBusinessBean> listData, boolean isShowDialog) {
		if (null != listData && listData.size() > 0) {
			iv_near_2.setVisibility(View.VISIBLE);
			NearbyBusinessAdapter adapter = new NearbyBusinessAdapter(this, listData);
			listView.setAdapter(adapter);
		} else {
			listView.setAdapter(null);
			iv_near_2.setVisibility(View.GONE);
			if (isShowDialog)
				showToast(getString(R.string.empty_data));
		}
	}

	// 如果该范围内的listdata已返回数据后 搜索则从listdata中进行搜索
	private void searchFromListData(final String keyWord) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (null != msg.obj) {
					ArrayList<NearbyBusinessBean> resultListData = (ArrayList<NearbyBusinessBean>) msg.obj;
					fillData(resultListData, false);
				}
			}
		};

		new Thread() {

			@Override
			public void run() {
				ArrayList<NearbyBusinessBean> resultListData = null;
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("community_id", String.valueOf(community_id));
				params.put("category_id", String.valueOf(category_id));
				if (distance != 0)
					params.put("distance", String.valueOf(distance));

				ArrayList<NearbyBusinessBean> listData = hashMapCache.get(params.toString());
				if (null != listData && listData.size() != 0) {
					resultListData = new ArrayList<NearbyBusinessBean>();
					for (int i = 0; i < listData.size(); i++) {
						if (listData.get(i).getDisplay_name().contains(keyWord)) {
							resultListData.add(listData.get(i));
						}
						// if(listData.get(i).getDisplay_name().toLowerCase().contains(keyWord.toLowerCase())){
						// resultListData.add(listData.get(i));
						// }
					}

					handler.sendMessage(handler.obtainMessage(0, resultListData));
				}
			}

		}.start();
	}

	class NearbyBusinessAdapter extends BaseAdapter {

		ArrayList<NearbyBusinessBean> listData;
		Context ctx;

		public NearbyBusinessAdapter(Context ctx, ArrayList<NearbyBusinessBean> listData) {
			this.listData = listData;
			this.ctx = ctx;
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
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (null == convertView) {
				convertView = View.inflate(ctx, R.layout.nearby_business_detail_item, null);
				holder = new ViewHolder();
				holder.openStyle = (ImageView) convertView.findViewById(R.id.nearby_item_openstyle);
				holder.dialView = (Button) convertView.findViewById(R.id.nearby_item_tele_btn);
				holder.name = (TextView) convertView.findViewById(R.id.nearby_item_name);
				holder.distance = (TextView) convertView.findViewById(R.id.nearby_item_distance);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			NearbyBusinessBean bean = listData.get(position);
			if ("1".equals(bean.getIs_open())) {
				holder.openStyle.setBackgroundResource(R.drawable.open_icon);
			} else {
				holder.openStyle.setBackgroundResource(R.drawable.close_icon);
			}

			holder.name.setText(listData.get(position).getDisplay_name());
			holder.distance.setText(getDistance(listData.get(position).getDistance()));

			holder.dialView.setTag(listData.get(position).getPhone());
			holder.dialView.setOnClickListener(NearbyBusinessDetailAct.this);
			return convertView;
		}

		class ViewHolder {
			ImageView openStyle;
			Button dialView;
			TextView name, distance;
		}
	}

	private String getDistance(String distance) {
		if (TextUtils.isEmpty(distance)) {
			return "0m";
		} else {
			double dis = Double.valueOf(distance);
			if (dis >= 1000) {
				return getKM(dis) + "km";
			} else {
				return distance + "m";
			}

		}
	}

	/**
	 * 米转化成千米 保留一位小数
	 * 
	 * @param a
	 * @return
	 */
	public String getKM(double a) {
		DecimalFormat nf = new DecimalFormat("#0.0");
		return nf.format(a / 1000);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		BitmapDrawable bdOne = (BitmapDrawable) mBaseImageView.getBackground();
//		// 别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
//		mBaseImageView.setBackgroundResource(0);
//		if (bdOne != null) {
//			bdOne.setCallback(null);
//			bdOne.getBitmap().recycle();
//		}
		super.onDestroy();
	}

	private void showFuDongView() {
		rl_near_frame.setVisibility(View.VISIBLE);
		rl_near_frame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_near_frame.setVisibility(View.GONE);
			}
		});
//		mBaseImageView.setVisibility(View.VISIBLE);
//		Bitmap bm = BitmapFactory.decodeResource(this.getResources(), imageId);
//		BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
//		mBaseImageView.setBackgroundDrawable(bd);
//		mBaseImageView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mBaseImageView.setVisibility(View.GONE);
//			}
//		});
	}
}

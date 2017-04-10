package com.sinaleju.lifecircle.app.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.adapter.ServiceDetailCommentAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.bottommenu.BaseBottomMenu;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.fragment.RightHomeFragment;
import com.sinaleju.lifecircle.app.model.EviewModel;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSServiceDetail;
import com.sinaleju.lifecircle.app.utils.DialogUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;

/**
 * 服务详情页
 * @author leju
 *
 */
public class ServiceDetailAct extends BaseActivity implements OnClickListener{

	private static final String TAG = "ServiceDetailAct";
	private TextView serviceName, teleTV, teleDialCountTV, detailTV, commentCountTV, noCommentTips;
	private RatingBar qualityRB, priceRB, velocityRB, attitudeRB;
	private ListView commentListView;
	
	private LinearLayout container;
	private LinearLayout moreComment;
	
	private LinearLayout dialLinear;
	private Button dialBtn;
	
	private RSServiceDetail rd;
	
	private int row_id; 
	private int table_choose;
	private int user_id;
	
	private ServiceDetailbean detailBean;
	
	private final int COMMENT_REQUEST_CODE = 1;
	
	@Override
	protected int getLayoutId() {
		return R.layout.fr_right_service_detail;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		container = (LinearLayout)findViewById(R.id.service_detail_linear);
		serviceName = (TextView)findViewById(R.id.service_name);
		teleTV = (TextView)findViewById(R.id.service_detail_tel);
		teleDialCountTV = (TextView)findViewById(R.id.service_detail_dial_count);
		
		dialLinear = (LinearLayout)findViewById(R.id.service_dial_linear);
		dialBtn = (Button)findViewById(R.id.service_detail_dial_btn);
		qualityRB = (RatingBar)findViewById(R.id.service_detail_quality_rb);
		priceRB = (RatingBar)findViewById(R.id.service_detail_price_rb);
		velocityRB = (RatingBar)findViewById(R.id.service_detail_velocity_rb);
		attitudeRB = (RatingBar)findViewById(R.id.service_detail_attitude_rb);
		
		noCommentTips = (TextView)findViewById(R.id.service_detail_no_comment_tv);
		detailTV = (TextView)findViewById(R.id.service_detail_tv);
		commentCountTV = (TextView)findViewById(R.id.service_detail_comment_count);
	
		commentListView = (ListView)findViewById(R.id.service_detail_listview);
		moreComment = (LinearLayout)findViewById(R.id.right_service_detail_more);
		//bottom menu
		initBottomMenu(BaseBottomMenu.TYPE_BG_RED+"对服务评价");
		
		init();
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initCallbacks() {
		setMySelfListener();
	}

	private void init(){
		mTitleBar.setTitleName(R.string.right_service_title);
		//mTitleBar.initRightButtonTextOrResId("", R.drawable.service_detail_right_icon);
		mTitleBar.initRightButtonTextOrResId("", R.drawable.selector_top_bar_more);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);
		
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);
		
		user_id = getIntent().getIntExtra(RightHomeFragment.USER_ID_KEY, RightHomeFragment.INVALIDATE_USER_ID);
		row_id = getIntent().getIntExtra("service_id", 0);
		
		table_choose = AllCommentAct.TABLE_CHOOSE_SERVICE;
		
		moreComment.setOnClickListener(this);
		dialLinear.setOnClickListener(this);
		dialBtn.setOnClickListener(this);
		
		loadData(getString(R.string.loading_data));
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.imavBack:
			finish();
			break;
			
		case R.id.tvRightFunc1:
//			mBottomMenu.removeAllViews();
//			mBottomMenu.addADeleteMenuButton("对服务评价");
//			//initBottomMenu(new String[] { "我来纠错"});
//			setMySelfListener();
			showBottomMenu();
			break;
		case R.id.right_service_detail_more:
			Intent intent = new Intent(this, AllCommentAct.class);
			intent.putExtra("table_choose", AllCommentAct.TABLE_CHOOSE_SERVICE);
			intent.putExtra("row_id", row_id);
			if(RightHomeFragment.INVALIDATE_USER_ID != user_id) intent.putExtra(RightHomeFragment.USER_ID_KEY, user_id);
			startActivity(intent);
			
			break;
		case R.id.service_dial_linear:
		case R.id.service_detail_dial_btn:
			showDialDialog(teleTV.getText().toString());
			break;
		}
	}
	
	private void setMySelfListener(){
		setBottomMenuButtonListener(0, new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(RightHomeFragment.INVALIDATE_USER_ID == user_id) showToast("请先进行登录！！");
				else{
					Intent intent = new Intent(ServiceDetailAct.this, BusinessEviewActivity.class);
					intent.putExtra("table_choose", table_choose);
					intent.putExtra("row_id", row_id);
					intent.putExtra(RightHomeFragment.USER_ID_KEY, user_id);
					startActivityForResult(intent, COMMENT_REQUEST_CODE);
					dismissBottomMenu();
				}
			}
		});

		setBottomMenuButtonListener(1, new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissBottomMenu();
			}
		});

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == BusinessEviewActivity.SUCESS_RESULT_CODE){
			loadData("正在更新评论列表");
		}
	}

	private void showDialDialog(final String mPhoneNo){
		DialogUtils.showDialDialog(this, mPhoneNo);
	}
	
	private void loadData(String loadingTips){
		//if(rd != null) return;

		rd = new RSServiceDetail();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("service_id", row_id + "");
		rd.setParams(params);
		
		rd.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				container.setVisibility(View.VISIBLE);
				detailBean = parse(result.toString());
				fillData(detailBean);
			}
		});
		rd.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				
				if(ex instanceof ADRemoteException) showToast(ex.getMessage());
				else showToast(getString(R.string.net_error));
			}
		});
		
		showProgressDialog(loadingTips, true);
		rd.asyncExecute(this);
	}
	
	
	@Override
	protected void cancelTask() {
		if(null != rd) rd.cancel();
	}

	private void fillData(ServiceDetailbean bean){
		if(null != bean){
			//mTitleBar.setTitleName(bean.content);
			serviceName.setText(bean.name);
			teleTV.setText(bean.tel);
			teleDialCountTV.setText("已拨打" + bean.tel_hits + "次");
			qualityRB.setRating(Float.valueOf(bean.quality));
			priceRB.setRating(Float.valueOf(bean.price));
			velocityRB.setRating(Float.valueOf(bean.speed));
			attitudeRB.setRating(Float.valueOf(bean.attitude));
			
			detailTV.setText(bean.content);
			commentCountTV.setText( TextUtils.isEmpty(bean.commentCount)? "0" : bean.commentCount );
			if(TextUtils.isEmpty(bean.commentCount) || Integer.valueOf(bean.commentCount) == 0){
				moreComment.setVisibility(View.GONE);
				commentListView.setVisibility(View.GONE);
				noCommentTips.setVisibility(View.VISIBLE);
			}else{
				moreComment.setVisibility(View.VISIBLE);
				commentListView.setVisibility(View.VISIBLE);
				noCommentTips.setVisibility(View.GONE);
			}
//			if(!TextUtils.isEmpty(bean.leaveCount) && Integer.valueOf(bean.leaveCount) == 0){
//				moreComment.setVisibility(View.GONE);
//			}
			
			if(null != bean.score_list){
				ServiceDetailCommentAdapter adapter = new ServiceDetailCommentAdapter(ServiceDetailCommentAdapter.FROM_SERVICE_DETAIL_COMMENT, 
						mContext, 
						bean.score_list);
				
				commentListView.setAdapter(adapter);
			}
		}
	}
	
	private ServiceDetailbean parse(String json){
		if(!TextUtils.isEmpty(json)){
			ServiceDetailbean bean = new ServiceDetailbean();
			try {
				JSONObject object = new JSONObject(json);
				bean.content = object.optString("content", "");
				bean.attitude = object.optString("attitude", "0");
				bean.price = object.optString("price", "0");
				bean.tel_hits = object.optString("tel_hits", "0");
				bean.speed = object.optString("speed", "0");
				bean.tel = object.optString("tel");
				bean.name = object.optString("name");
				bean.quality = object.optString("quality");
				bean.img = object.optString("img");
				bean.commentCount = object.optString("score_count");
				
				String list = object.optString("score_list");
				JSONObject subO = new JSONObject(list);
				if(null != subO){
					bean.leaveCount = subO.optString("surplus");
					JSONArray array = subO.optJSONArray("list");
					
					ArrayList<EviewModel> listData = null;
					EviewModel model;
					if(null != array){
						listData = new ArrayList<EviewModel>();
						
						for(int i=0; i<array.length(); i++){
							JSONObject o = array.getJSONObject(i);
							model = new EviewModel();
							model.setId(o.optString("id"));
							model.setImageUrl(o.optString("from_user_header"));
							model.setName(o.optString("from_user_name"));
							model.setText(o.optString("content"));
							model.setAttitudeRB((float)o.optDouble("attitude", 0));
							model.setQualityRB((float)o.optDouble("quality", 0));
							model.setVelocityRB((float)o.optDouble("speed", 0));
							model.setPriceRB((float)o.optDouble("price", 0));
							model.setTime(o.optString("add_time"));
							listData.add(model);
						}
					}
					bean.score_list = listData;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bean;
		}
		
		return null;
	}
	
	class ServiceDetailbean{
		public String content;
		public String attitude;
		public String price;
		public String tel_hits;
		public String speed;
		public String tel;
		public String name;
		public String quality;
		public String img;
		public String commentCount;
		public String leaveCount;
		public ArrayList<EviewModel> score_list;
	}
}

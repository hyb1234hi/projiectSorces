package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iss.imageloader.core.ImageLoader;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.IndexActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.fragment.FindFriendsFragment;
import com.sinaleju.lifecircle.app.model.RecognizedFriendsBean;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAddFollower;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;

public class FindFriendRecognizedAdapter extends BaseAdapter {

	private FindFriendsFragment fragment;
	private Context ctx;
	private ArrayList<RecognizedFriendsBean> listData;
	private String uid;
	
	private final String ADD_FOLLOW = "1";
	private final String CALCLE_FOLLOW = "0";
	
	protected ProgressDialog mProgressDialog = null;
	
	//加关注
	private RSAddFollower rs;
	
	private int index;//一下两种状态
	public static final int RECONIZED_ADAPTER = 1;
	public static final int SEARCH_RECONIZED_ADAPTER = 2;
	private static final String TAG = "FindFriendRecognizedAdapter";
	
	public FindFriendRecognizedAdapter(int index, FindFriendsFragment fragment, Context ctx, ArrayList<RecognizedFriendsBean> list, String userId){
		this.index = index;
		this.fragment = fragment;
		this.ctx = ctx;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + ctx);
		this.listData = list;
		this.uid = userId;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(null == convertView){
			convertView = View.inflate(ctx, R.layout.sina_friends_item, null);
			holder = new ViewHolder();
			
			holder.icon = (ImageView)convertView.findViewById(R.id.sina_friends_icon);
			holder.name = (TextView)convertView.findViewById(R.id.sina_friends_name);
			holder.tips = (TextView)convertView.findViewById(R.id.sina_friends_contact_tips);
			holder.btn = (Button)convertView.findViewById(R.id.sina_friend_invite_add_btn);
			holder.addedAttention = (TextView)convertView.findViewById(R.id.sina_friend_tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		RecognizedFriendsBean bean = listData.get(position);
		holder.name.setText(bean.getDisplay_name());
		//holder.tips.setVisibility(View.VISIBLE);
		LogUtils.e(TAG, "commonNumber: "+bean.getCommon());
		String common=bean.getCommon();
		if(!"".equals(common)&&!"0".equals(common)){		
			holder.tips.setText(bean.getCommon() + "个共同好友");
			holder.tips.setVisibility(View.VISIBLE);
		}
		
		int headDefaultId ;
		if("0".equals(bean.getType())) //个人
			headDefaultId = R.drawable.per_visitor_default_head_image;
		else if("1".equals(bean.getType()))//商户
			headDefaultId = R.drawable.bus_left_default_head_image;
		else //物业
			headDefaultId = R.drawable.pro_left_default_head_image;
		
		holder.icon.setBackgroundResource(R.drawable.bg_frame_header);
		ImageLoader.getInstance((Activity)ctx).displayImage(listData.get(position).getHeader(), holder.icon,
				SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils.getUserIndexDefaultHeadImage(Integer.valueOf(listData.get(position).getType())), 100));
		
		holder.btn.setVisibility(View.GONE);
		holder.addedAttention.setVisibility(View.GONE);
		
		holder.btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				follow(listData.get(position), ADD_FOLLOW);
			}
		});
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToIndex(listData.get(position));
			}
		});
		
		LogUtils.d("zhenwei", "listData.get(position).getStatus()=" + listData.get(position).getStatus());
		if("0".equals(listData.get(position).getStatus())){  //未加入关注
			holder.btn.setVisibility(View.VISIBLE);
		}else if("1".equals(listData.get(position).getStatus())){//加入了关注
			holder.addedAttention.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	class ViewHolder{
		public ImageView icon;
		public TextView name, tips, addedAttention;
		public Button btn;
	}
	
	private void follow(final RecognizedFriendsBean bean, String type){
		
		showProgressDialog(ctx.getString(R.string.loading_data), false);
		
		rs = new RSAddFollower();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", uid);
		params.put("visitor_id", bean.getId());
		params.put("type", type);
		
		rs.setParams(params);
		rs.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				Toast.makeText(ctx, "加关注成功！", Toast.LENGTH_LONG).show();
				bean.setStatus("1");//置成已加入关注的状态
				notifyDataSetChanged();
				if(index == SEARCH_RECONIZED_ADAPTER) fragment.updateFollowStatus(bean);
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if(ex instanceof ADRemoteException){
					Toast.makeText(ctx, ((ADRemoteException)ex).msg(), Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(ctx, "加关注失败！", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		rs.asyncExecute(ctx);
	}
	
	private void goToIndex(RecognizedFriendsBean bean){
		gotoIndexActivity(ctx, Integer.valueOf(bean.getType()), Integer.valueOf(bean.getId()));
	}
	
	/**
	 * 
	 * @param context
	 * @param type  0是个人  1是商家  2是物业
	 * @param visitorId
	 */
	public void gotoIndexActivity(Context context, int type,
			int visitorId) {
		//fragment.hideInput(fragment.getView());
		ADSoftInputUtils.hide(ctx);
		if (AppContext.isValid((Activity) context)) {
			Intent intent = new Intent();
			intent.setClass(context, IndexActivity.class);
			if (type == 0) {
				intent.putExtra("is_personal_index", true);
				intent.putExtra("is_property", false);
				intent.putExtra("mVisitor_Id", visitorId);
			} else if (type == 1) {
				intent.putExtra("is_personal_index", false);
				intent.putExtra("is_property", false);
				intent.putExtra("mVisitor_Id", visitorId);
			} else if (type == 2) {
				intent.putExtra("is_personal_index", false);
				intent.putExtra("is_property", true);
				intent.putExtra("mVisitor_Id", visitorId);
			}
			fragment.startActivityForResult(intent, FindFriendsFragment.INDEX_REQUEST_CODE);
		}
	}
	
	public void showProgressDialog(String msg, boolean isCancel) {
		if(null == mProgressDialog) mProgressDialog = new ProgressDialog(ctx);
		mProgressDialog.setMessage(msg != null ? msg : "");
		mProgressDialog.setCancelable(isCancel);
		mProgressDialog.show();
	}
	
	public void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}
}

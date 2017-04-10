package com.sinaleju.lifecircle.app.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
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
import com.sinaleju.lifecircle.app.activity.SinaFriensAct;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.model.SinaFriendsBean;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSAddFollower;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;
import com.sinaleju.lifecircle.app.utils.SimpleImageLoaderOptions;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;

public class SinaFriendsAdapter extends BaseAdapter {

	private static final String TAG = "SinaFriendsAdapter";
	private Context ctx;
	private ArrayList<SinaFriendsBean> listData;
	private String uid;
	
	private final int SINA_SUCESS = 1;//发微博成功
	private final int SINA_FAILURE = 2;//发微博失败
	
	private final String ADD_FOLLOW = "1";
	private final String CALCLE_FOLLOW = "0";
	
	private int index;// 新浪微博还是通讯录1 是微博  2是通讯录
	
	public SinaFriendsAdapter(Context ctx, ArrayList<SinaFriendsBean> listData, String uid, int index){
		this.ctx = ctx;
		this.listData = listData;
		this.uid = uid;
		this.index = index;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + ctx);
		
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
			holder = new ViewHolder();
			convertView = View.inflate(ctx, R.layout.sina_friends_item, null);
			
			holder.icon = (ImageView)convertView.findViewById(R.id.sina_friends_icon);
			holder.name = (TextView)convertView.findViewById(R.id.sina_friends_name);
			holder.tips = (TextView)convertView.findViewById(R.id.sina_friends_contact_tips);
			holder.inviteRegistBtn = (Button)convertView.findViewById(R.id.sina_friend_invite_regist_btn);
			holder.inviteAddBtn = (Button)convertView.findViewById(R.id.sina_friend_invite_add_btn);
			holder.invitedTV = (TextView)convertView.findViewById(R.id.sina_friend_tv);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(index == SinaFriensAct.FROM_CONTACT_INDEX) holder.tips.setVisibility(View.VISIBLE);
		
		final SinaFriendsBean bean = listData.get(position);
		holder.name.setText(bean.getName());
		
		int headDefaultId ;
		if("0".equals(bean.getType())) //个人
			headDefaultId = R.drawable.per_visitor_default_head_image;
		else if("1".equals(bean.getType()))//商户
			headDefaultId = R.drawable.bus_index_default_head_image;
		else //物业
			headDefaultId = R.drawable.pro_left_default_head_image;
		
		holder.icon.setBackgroundResource(R.drawable.bg_frame_header);
		ImageLoader.getInstance((Activity)ctx).displayImage(bean.getProfile_image_url(), holder.icon,
				SimpleImageLoaderOptions.getRoundImageOptions(PublicUtils.getUserIndexDefaultHeadImage(Integer.valueOf(listData.get(position).getType())), 100));
		
		holder.inviteRegistBtn.setVisibility(View.GONE);
		holder.inviteAddBtn.setVisibility(View.GONE);
		holder.invitedTV.setVisibility(View.GONE);
		
		if("0".equals(bean.getStatus())){  //没有注册本app  邀请
			holder.inviteRegistBtn.setVisibility(View.VISIBLE);
		}else if("1".equals(bean.getStatus())){  //注册了本app  加关注
			holder.inviteAddBtn.setVisibility(View.VISIBLE);
		}else if("2".equals(bean.getStatus())){   //已加入了关注
			holder.invitedTV.setVisibility(View.VISIBLE);
		}
		
		//邀请
		holder.inviteRegistBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(index == SinaFriensAct.FROM_SINA_INDEX) inviteRegis(bean);
				else inviteBySms(bean);
			}
		});
		
		//加关注
		holder.inviteAddBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inviteAdd(bean, ADD_FOLLOW);
			}
		});
		return convertView;
	}

	class ViewHolder{
		ImageView icon;
		TextView name, tips;
		//         邀请按钮                               加关注按钮
		Button inviteRegistBtn, inviteAddBtn;
		TextView invitedTV;
	}
	
	/**
	 * 新浪邀请
	 */
	private void inviteRegis(SinaFriendsBean bean){
		((SinaFriensAct)ctx).showProgressDialog(ctx.getString(R.string.loading_data), true);
		
		//Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(ctx);
		Oauth2AccessToken accessToken =new Oauth2AccessToken(AppContext.curUser().getToken(), AppContext.curUser().getExpiresTime());
		StatusesAPI statuse = new StatusesAPI(accessToken);
		statuse.update(ctx.getString(R.string.send_weibo_at, "@" + bean.getName()), "0", "0", new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
				((SinaFriensAct)ctx).hideProgressDialog();
				mHandler.sendEmptyMessage(SINA_FAILURE);
			}
			
			@Override
			public void onError(WeiboException arg0) {
				((SinaFriensAct)ctx).hideProgressDialog();
				mHandler.sendEmptyMessage(SINA_FAILURE);
			}
			
			@Override
			public void onComplete(String arg0) {
				((SinaFriensAct)ctx).hideProgressDialog();
				mHandler.sendEmptyMessage(SINA_SUCESS);
			}
		});
	}
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			int what = msg.what;
			switch(what){
			case SINA_FAILURE:
				Toast.makeText(ctx, "邀请好友失败！", Toast.LENGTH_LONG).show();
				break;
			case SINA_SUCESS:
				Toast.makeText(ctx, "邀请好友成功！", Toast.LENGTH_LONG).show();
			break;
			}
		}
		
	};
	
	private void inviteBySms(SinaFriendsBean bean){
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		String phoneNumber1 = bean.getTelephone();
		Uri uri = Uri.parse("smsto:" + phoneNumber1);
		mIntent.setData(uri);
		mIntent.putExtra("sms_body",
				ctx.getString(R.string.invite_sms_body, bean.getName()));
		ctx.startActivity(mIntent);
	}
	
	/**
	 * 加关注
	 */
	private void inviteAdd(final SinaFriendsBean bean, String type){
		
		((BaseActivity)ctx).showProgressDialog(ctx.getString(R.string.loading_data), false);
		
		RSAddFollower rs = new RSAddFollower();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", uid);
		params.put("visitor_id", bean.getUserId());
		params.put("type", type);
		
		rs.setParams(params);
		rs.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				((BaseActivity)ctx).hideProgressDialog();
				Toast.makeText(ctx, "加关注成功！", Toast.LENGTH_LONG).show();
				bean.setStatus("2");
				notifyDataSetChanged();
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				((BaseActivity)ctx).hideProgressDialog();
				Toast.makeText(ctx, "加关注失败！", Toast.LENGTH_LONG).show();
			}
		});
		
		rs.asyncExecute(ctx);
	}
}

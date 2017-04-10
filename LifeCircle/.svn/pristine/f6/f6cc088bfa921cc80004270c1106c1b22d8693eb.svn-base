package com.sinaleju.lifecircle.app.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.adapter.SinaFriendsAdapter;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.fragment.RightHomeFragment;
import com.sinaleju.lifecircle.app.model.SinaFriendsBean;
import com.sinaleju.lifecircle.app.model.json.JSONParser_SinaFriends;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSGetSinaFriends;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.FriendshipsAPI;
import com.weibo.sdk.android.net.RequestListener;

/**
 *  通讯录   新浪微博  好友界面
 * @author leju
 *
 */
public class SinaFriensAct extends BaseActivity implements OnClickListener{

	private Button delete, searchBtn;
	private EditText searchET;
	private PullToRefreshListView listView;
	private View footer;
	
	//新浪授权时保存的id
	private long sinaUid;
	
	//用户登录的uid
	private int uid;
	
	private LinearLayout searchBar;
	
	private RSGetSinaFriends rs;
	
	private ArrayList<SinaFriendsBean> listData;// 数据结果
	private ArrayList<SinaFriendsBean> searchListData;// 搜索数据结果

	private SinaFriendsAdapter adapter;
	
	private List<ContentValues> contactList;  //通讯录得列表
	
	private int totalNum;
	
	private final int NUM_PER_PAGE = 15;
	
	private int handleContactCount;
	
	private boolean isLoadingData;
	
	private final int SINA_SUCESS = 1;//获取微博列表成功
	private final int SINA_FAILURE = 2;//获取微博列表失败
	
	private final String CONTACT_URI = "content://com.android.contacts/data/phones";
	
	private AsyncQueryHandler asyncQuery;  
    private static final String NAME = "name", NUMBER = "number", SORT_KEY = "sort_key";
    
    private int index;//新浪微博还是通讯录
    
    public static final int FROM_SINA_INDEX = 1;
    public static final int FROM_CONTACT_INDEX = 2;
	private static final String TAG = "SinaFriensAct";
    
    private final int requestCode = 1;
    
    private String token;
    private String  expires_in;
    private int page=0;
    
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.sina_friends_act;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "------------initViews");
		searchBar = (LinearLayout)findViewById(R.id.friends_search_bar);
		searchBtn = (Button)findViewById(R.id.search_btn);
		delete = (Button)findViewById(R.id.delete_btn);
		
		searchET = (EditText)findViewById(R.id.search_et);
		listView = (PullToRefreshListView)findViewById(R.id.sina_friends_listview);
		
		searchET.setHint("输入昵称查找");
		
		footer = View.inflate(this, R.layout.loading_item, null);
		
		uid = getIntent().getIntExtra(RightHomeFragment.USER_ID_KEY, 0);
		index = getIntent().getIntExtra("index", 1);
		
		if(index == FROM_SINA_INDEX) mTitleBar.setTitleName(R.string.sina_weibo);
		else mTitleBar.setTitleName(R.string.contact_friends);
		
		mTitleBar.showBackButton();
		
		String userId =AppContext.curUser().getPlatform_id();//PublicUtils.getStringData(this, OAuthActivity.SHARE_PREFERENCE_KEY, "uid");
		sinaUid  = TextUtils.isEmpty(userId) ? 0l:Long.valueOf(userId);
		LogUtils.i(TAG, "sinaUid:  "+sinaUid);
		handleContactCount = 0;
		isLoadingData = false;
		
		if(index == FROM_SINA_INDEX){
			searchBar.setVisibility(View.GONE);
			if(sinaUid > 1l){
				loadDataFromSina();
			}else{
				showToast("微博授权失败！");
			}
		}else{
			loadDataFromContact();
		}
	}

	@Override
	protected void initData() {
		LogUtils.i(TAG, "initData");
		

	}

	@Override
	protected void initCallbacks() {
		
		searchBtn.setOnClickListener(this);
		delete.setOnClickListener(this);
		
		mTitleBar.setLeftButtonListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SinaFriendsBean bean = (SinaFriendsBean)listData.get(arg2);
				if(!TextUtils.isEmpty(bean.getUserId())){
					gotoIndexActivity(SinaFriensAct.this, Integer.valueOf(bean.getType()), Integer.valueOf(bean.getUserId()), arg2);
				}
			}
			
		});
		
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			//	LogUtils.e(TAG, "totalNum: "+totalNum+"listData.size: "+listData.size());
				if(null == listData || (listData != null && totalNum > listData.size())){
					if(index == FROM_SINA_INDEX) loadDataFromSina();
					else loadDataFromContact();
				}else{
					showToast("已经是最后一页了！");
					comlpeteRefresh();
				}
			}
		});
		
		/*listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lastItemCount = firstVisibleItem + visibleItemCount;
				if(null != listData && lastItemCount == listData.size() && totalNum > listData.size()){
					if(index == FROM_SINA_INDEX) loadDataFromSina();
					else loadDataFromContact();
				}
			}
		});*/
		
		searchET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(index == FROM_CONTACT_INDEX){
					if(!TextUtils.isEmpty(s.toString())){
						handleSearch(s.toString(), false);
					}else {
						if(null != listData && listData.size() > 0){
							adapter = new SinaFriendsAdapter(SinaFriensAct.this, listData, String.valueOf(uid), FROM_CONTACT_INDEX);
							listView.setAdapter(adapter);
						}else{
							loadDataFromContact();
						}
					}
				}
			}
		});
	}

	private void comlpeteRefresh(){
		comlpeteHandler.sendEmptyMessageDelayed(0, 100);
	}
	
	Handler comlpeteHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			listView.onRefreshComplete();
		}
		
	};
	
	
	/**
	 * 
	 * @param context
	 * @param type  0是个人  1是商家  2是物业
	 * @param visitorId
	 */
	public void gotoIndexActivity(Context context, int type,
			int visitorId, int position) {
		if (AppContext.isValid((Activity) context)) {
			Intent intent = new Intent();
			intent.setClass(context, IndexActivity.class);
			intent.putExtra("position", position);
			if (type == 0) {
				intent.putExtra("is_personal_index", true);
				intent.putExtra("is_property", false);
				intent.putExtra("mVisitor_Id", visitorId);
				startActivityForResult(intent, requestCode);
			} else if (type == 1) {
				intent.putExtra("is_personal_index", false);
				intent.putExtra("is_property", false);
				intent.putExtra("mVisitor_Id", visitorId);
				startActivityForResult(intent, requestCode);
			} else if (type == 2) {
				intent.putExtra("is_personal_index", false);
				intent.putExtra("is_property", true);
				intent.putExtra("mVisitor_Id", visitorId);
				startActivityForResult(intent, requestCode);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.imavBack:
			finish();
			break;
		case R.id.search_btn:
			if(!TextUtils.isEmpty(searchET.getText().toString())){
				handleSearch(searchET.getText().toString(), true);
			}
			break;
		case R.id.delete_btn:
			searchET.setText("");
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == IndexActivity.SUCESS_RESULT_CODE && null != data){
			int status = data.getIntExtra("follow", -1);
			int mVisitor_Id = data.getIntExtra("mVisitor_Id", -1);
			int position = data.getIntExtra("position", -1);
			//重置一下status indexAct中的status中0代表取消关注 1代表已关注 2代表互相关注，  而本act中0 未注册 1 未关注 2 已关注
			if(status == 0) status = 1;
			else if(status == 1 || status == 2) status = 2;
			
			if(status != -1 && mVisitor_Id != -1 && position != -1){
				int oldStatus = Integer.valueOf(listData.get(position).getStatus());
				if(oldStatus != status){
					listData.get(position).setStatus(String.valueOf(status));
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	//从新浪微博中取好友
	private void loadDataFromSina(){
		
		if(isLoadingData) return;
		
		isLoadingData = true;
		
		if(null == listData) showProgressDialog(getString(R.string.loading_data), false);		
		//取第几页数据
		//int page = listData == null ? 1 : listData.size()/NUM_PER_PAGE + 1;
		 token=AppContext.curUser().getToken();
		 expires_in=AppContext.curUser().getExpiresTime();
		LogUtils.i(TAG, /*"isValide:  "+accessToken.isSessionValid()+*/"  token: "+token+"  expiresTime:  "+expires_in);
		Oauth2AccessToken accessToken = new Oauth2AccessToken(token, expires_in);//AccessTokenKeeper.readAccessToken(this);
		FriendshipsAPI friendship = new FriendshipsAPI(accessToken);
		friendship.bilateral(sinaUid, NUM_PER_PAGE, ++page, new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(SINA_FAILURE);
				
			}
			
			@Override
			public void onError(WeiboException arg0) {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(SINA_FAILURE);
			}
			
			@Override
			public void onComplete(String arg0) {
				// TODO Auto-generated method stub
				
				mHandler.sendMessage(mHandler.obtainMessage(SINA_SUCESS, arg0));
			}
		});
	}
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			int what = msg.what;
			switch(what){
			case SINA_FAILURE:
				showToast("从微博获取互相关注列表失败！");
				hideProgressDialog();
				isLoadingData = false;
				break;
			case SINA_SUCESS:
				String json = (String)msg.obj;
				
				ArrayList<SinaFriendsBean> list = null;
				if(!TextUtils.isEmpty(json)){
					HashMap<String, Object> map = JSONParser_SinaFriends.parserFromSina(json);
					if(null != map){
						totalNum = (Integer)map.get("totalNum");
						list = (ArrayList<SinaFriendsBean>) map.get("list");
					}
				}
				LogUtils.i(TAG, "page: "+page);
				if(null != list && list.size() >0 ){
					loadDataFromLocalService(false, list);
				}else{
					comlpeteRefresh();
					if(page==1){						
						showToast("您的新浪微博还没有互相关注的好友！");
					}else{
						showToast("已经是最后一页了！");
					}
					hideProgressDialog();
					isLoadingData = false;
				}
			break;
			}
			
		}
		
	};
	

	//从通讯录中取好友
	private void loadDataFromContact(){
		if(isLoadingData) return;
		isLoadingData = true;
		
		if(null == contactList){
			Uri uri = Uri.parse(CONTACT_URI);  
			String[] projection = { "_id", "display_name", "data1", "sort_key" };  
			if(null == asyncQuery) asyncQuery = new MyAsyncQueryHandler(getContentResolver());
			asyncQuery.startQuery(0, null, uri, projection, null, null,  
					"sort_key COLLATE LOCALIZED asc");
		}else{
			handleContactList();
		}
	}
	
	private class MyAsyncQueryHandler extends AsyncQueryHandler {  
		  
        public MyAsyncQueryHandler(ContentResolver cr) {  
            super(cr);  
        }  
  
        @Override  
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {  
            if (cursor != null && cursor.getCount() > 0) {  
            	totalNum = cursor.getCount();
            	
            	contactList = new ArrayList<ContentValues>();  
                cursor.moveToFirst();  
                for (int i = 0; i < cursor.getCount(); i++) {
                    ContentValues cv = new ContentValues();  
                    cursor.moveToPosition(i);  
                    String name = cursor.getString(1);  
                    String number = cursor.getString(2);  
                    String sortKey = cursor.getString(3);
                    if (number.startsWith("+86")) {  
                        cv.put(NAME, name);  
                        cv.put(NUMBER, number.substring(3).replace(" ", "").replace(",", ""));  //ȥ��+86
                        cv.put(SORT_KEY, sortKey);  
                    } else {  
                        cv.put(NAME, name);  
                        cv.put(NUMBER, number.replace(" ", "").replace(",", ""));  
                        cv.put(SORT_KEY, sortKey);  
                    }  
                    contactList.add(cv);  
                }  
                if (contactList.size() > 0) {  
                    //setAdapter(list);
                	handleContactList();
                }else{
                	showToast("没有搜到通讯录好友！！！");
                	isLoadingData = false;
                }
            }else{
            	showToast("没有搜到通讯录好友！！！");
            	isLoadingData = false;
            }
        }  
  
    }  
	
	//
	private void handleContactList(){
		
		/*for(int i=0 ;i <contactList.size(); i++){
			handleSearch(contactList.get(i).getAsString(SORT_KEY));
		}*/
		if(null == listData) showProgressDialog(getString(R.string.loading_data), false);
		
		ArrayList<SinaFriendsBean> list = null;
		if(null != contactList){
			list = new ArrayList<SinaFriendsBean>();
			
			int count = Math.min(contactList.size(), handleContactCount + NUM_PER_PAGE);
			for(int i=handleContactCount; i<count; i++){
				SinaFriendsBean bean = new SinaFriendsBean();
				bean.setName(contactList.get(i).getAsString(NAME));
				bean.setTelephone(contactList.get(i).getAsString(NUMBER));
				list.add(bean);
				
				handleContactCount ++;
			}
		}
		
		if(null != list) loadDataFromLocalService(false, list);
		else isLoadingData = false;
	}
	
	/**
	 * 将通讯录好友或新浪微博好友发送  分部分发送到服务器后台
	 * @param isSearch 是否是搜索结果
	 * @param list
	 */
	private void loadDataFromLocalService(final boolean isSearch, final ArrayList<SinaFriendsBean> list){
		if(null != rs && !rs.isCanceled()) rs.cancel(); 
		
		rs = new RSGetSinaFriends();
		
		HashMap<String, String> params = new HashMap<String, String>();
		StringBuffer item_name = new StringBuffer();
		for(int i=0; i<list.size(); i++){
			if(i == 0){
				if(index == FROM_SINA_INDEX)item_name.append(list.get(i).getIdstr());
				else item_name.append(list.get(i).getTelephone());
			}
			else{
				if(index == FROM_SINA_INDEX) item_name.append(",").append(list.get(i).getIdstr());
				else item_name.append(",").append(list.get(i).getTelephone());
			}
		}
		params.put("item_name", item_name.toString());
		if(index == FROM_SINA_INDEX) params.put("item_type", "2");
		else  params.put("item_type", "1");
		
		params.put("user_id", String.valueOf(uid));///当前登录者id  以后要换
		
		rs.setPramas(params);
		rs.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				comlpeteRefresh();
				rs = null;
				isLoadingData = false;
				if(null != result){
					ArrayList<SinaFriendsBean> listData = 
							JSONParser_SinaFriends.parserFromLocalService(list, result.toString());
					if(!isSearch) fillData(listData);
					else fillDataBySearch(list);
				}
			}
		});
		
		rs.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				showToast(getString(R.string.net_error));
				hideProgressDialog();
				comlpeteRefresh();
				rs = null; 
				isLoadingData = false;
			}
		});
		
		rs.asyncExecute(this);
	}
	
	
	@Override
	protected void cancelTask() {
		if(null != rs)  rs.cancel();
	}

	private void fillData(ArrayList<SinaFriendsBean> list){
		
		if(null == listData) listData = list;
		else listData.addAll(list);
		
		if(null == adapter){
			adapter = new SinaFriendsAdapter(this, listData, String.valueOf(uid), index);
			listView.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
		
	}
	
	private void fillDataBySearch(ArrayList<SinaFriendsBean> list){
		
		searchListData = list;
		
		adapter = new SinaFriendsAdapter(this, searchListData, String.valueOf(uid), index);
		listView.setAdapter(adapter);
	}

	
	private void handleSearch(final String key, final boolean isShowingDialog){
		
		//PinyinHelper.toHanyuPinyinString(str, outputFormat, seperater)
		
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				ArrayList<SinaFriendsBean> list = (ArrayList<SinaFriendsBean>)msg.obj;
				
				if(list.size() > 0){
					if(isShowingDialog) showProgressDialog(getString(R.string.searching_data), true);
					loadDataFromLocalService(true, list);
				}else{
					if(isShowingDialog) showToast("没有搜索到数据！");
					//listView.setAdapter(null);
				}
			}
			
		};
		
		Thread thread = new Thread(){

			@Override
			public void run() {
				
				ArrayList<SinaFriendsBean> list = new ArrayList<SinaFriendsBean>();
				if(null != contactList){
					for(int i=0; i<contactList.size(); i++){
						String name = contactList.get(i).getAsString(NAME);
						
						String pinyinName = getPinYin(name);
						if(!TextUtils.isEmpty(pinyinName) && (pinyinName.contains(key) || name.contains(key))){
							SinaFriendsBean bean = new SinaFriendsBean();
							bean.setTelephone(contactList.get(i).getAsString(NUMBER));
							bean.setName(name);
							list.add(bean);
						}
					}
				}
				
				Message msg = handler.obtainMessage(0, list);
				msg.sendToTarget();
			}
			
		};
		thread.start();
	}
	
	public String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		// System.out.println(t1.length);
		String[] t2 = new String[t1.length];
		// System.out.println(t2.length);
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				// System.out.println(t1[i]);
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，直接取出字符并连接到字符串t4后
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t4;
	}

}

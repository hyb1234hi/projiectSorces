package com.sinaleju.lifecircle.app.activity;

import java.util.HashMap;

import org.apache.http.conn.ConnectTimeoutException;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.exception.ADRemoteException;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSSuggestionCommit;
import com.sinaleju.lifecircle.app.utils.ADSoftInputUtils;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class SuggestionCommitAct extends BaseActivity implements OnClickListener{

	private static final String TAG = "SuggestionCommitAct";
	private EditText suggestionEditText;
	private TextView count;
	
	private RSSuggestionCommit rs;
	
	private final int TOTAL_COUNT = 400;
	
	private int user_id;
	
	@Override
	protected int getLayoutId() {
		return R.layout.suggesstion_commit;
	}

	@Override
	protected void initView() {
		LogUtils.v(TAG, "------------initViews");
		mTitleBar.setTitleName(R.string.suggestion_commit);
		mTitleBar.initRightButtonTextOrResId(null, R.drawable.selector_topbar_submit_button);
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId("", R.drawable.selector_topbar_back_button);
		
		mTitleBar.setLeftButtonListener(this);
		mTitleBar.setRightButton1Listener(this);		
		suggestionEditText = (EditText)findViewById(R.id.suggestion_commit_edittext);
		suggestionEditText.requestFocus();
		count = (TextView)findViewById(R.id.suggestion_commit_count);
		
		user_id =AppContext.curUser().getUid(); //getIntent().getIntExtra(RightHomeFragment.USER_ID_KEY, RightHomeFragment.INVALIDATE_USER_ID);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initCallbacks() {
		suggestionEditText.addTextChangedListener(new TextWatcher() {
			
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
				count.setText(String.valueOf(TOTAL_COUNT - s.length()));
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		
		switch(id){
		case R.id.imavBack:
			ADSoftInputUtils.hide(mActivity);
			finish();
			break;
		case R.id.tvRightFunc1:
			commitData();
			break;
		}
	}

	private void commitData(){
		String content = suggestionEditText.getText().toString();
		if(TextUtils.isEmpty(content)){
			showToast("意见内容不能为空！");
			return;
		}
		
		
		rs = new RSSuggestionCommit();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", String.valueOf(user_id));
		params.put("opinion", suggestionEditText.getText().toString());
		
		rs.setParams(params);
		rs.setOnSuccessHandler(new OnSuccessHandler() {
			
			@Override
			public void onSuccess(Object result) {
				hideProgressDialog();
				showToast("意见反馈成功");
				finish();
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {
			
			@Override
			public void onFault(Exception ex) {
				hideProgressDialog();
				if(ex instanceof ADRemoteException){
					showToast(((ADRemoteException) ex).msg());
				}else if(ex instanceof ConnectTimeoutException){
					showToast("网络请求超时，请重试");
				}
			}
		});
		if (PublicUtils.isNetworkAvailable(mContext)) {
			showProgressDialog(getString(R.string.loading_data), true);
			rs.asyncExecute(this);
		}else{
			showToast("网络连接异常，请重试");
		}
	}

	@Override
	protected void cancelTask() {
		rs.cancel();
	}
	
	
}

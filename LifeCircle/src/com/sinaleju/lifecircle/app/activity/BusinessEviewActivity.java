package com.sinaleju.lifecircle.app.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.fragment.RightHomeFragment;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSServiceAppraise;

/**
 * 
 * @ClassName: BusinessEviewActivity 
* @Description: TODO  发表评论的dialog界面 
* @author zhenwei 
* @date 2013-8-23 下午12:00:06 
*
 */
public class BusinessEviewActivity extends Activity {

	private RatingBar oneBar;
	private RatingBar twoBar;
	private RatingBar threeBar;
	private RatingBar fourBar;
	private EditText eviewEdit;
	private Button submitBtn;
	private ImageView cancelView;
	private TextView totalCount;
	
	private int table_choose;// 评价对象
	private int row_id;//
	private int user_id;// 评价者Id

	private RSServiceAppraise rs;// 公共服务评价
	private ProgressDialog uncancelProgressDialog = null;

	private final int TOTAL_COUNT = 40;
	
	public static final int SUCESS_RESULT_CODE = 601;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_business_eview);

		Intent intent = getIntent();
		table_choose = intent.getIntExtra("table_choose",
				AllCommentAct.TABLE_CHOOSE_SERVICE);
		row_id = intent.getIntExtra("row_id", AllCommentAct.ROW_ID_ONE);
		user_id = intent.getIntExtra(RightHomeFragment.USER_ID_KEY, RightHomeFragment.INVALIDATE_USER_ID);

		initView();
		initData();
		setListener();
	}

	private void initView() {
		oneBar = (RatingBar) findViewById(R.id.ac_one_rating_bar);
		twoBar = (RatingBar) findViewById(R.id.ac_two_rating_bar);
		threeBar = (RatingBar) findViewById(R.id.ac_three_rating_bar);
		fourBar = (RatingBar) findViewById(R.id.ac_four_rating_bar);
		eviewEdit = (EditText) findViewById(R.id.eview_edit_text);
		submitBtn = (Button) findViewById(R.id.eview_pingjia_submit);
		cancelView = (ImageView) findViewById(R.id.eview_cancel_btn);
		totalCount = (TextView) findViewById(R.id.comment_count_tv);
	}

	private void initData(){
		totalCount.setText(0 + "/" + TOTAL_COUNT);
		
		eviewEdit.setOnEditorActionListener(new OnEditorActionListener() {            
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {                
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        
	}
	
	private void setListener() {
		submitBtn.setOnClickListener(clickListener);
		cancelView.setOnClickListener(clickListener);
		
		eviewEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				totalCount.setText(s.length() + "/" + TOTAL_COUNT);
			}
		});
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.eview_pingjia_submit:
				commitData();
				// finish();
				break;
			case R.id.eview_cancel_btn:
				finish();
				break;
			default:
				break;
			}
		}
	};

	private void commitData() {
		if (TextUtils.isEmpty(eviewEdit.getText().toString())) {
			Toast.makeText(this, "评价内容不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		showUncancelProgressDialog("正在提交评价内容！");

		rs = new RSServiceAppraise();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("table_choose", String.valueOf(table_choose));
		params.put("row_id", String.valueOf(row_id));
		params.put("user_id", String.valueOf(user_id));
		params.put("content", eviewEdit.getText().toString());
		params.put("quality", (int) oneBar.getRating() + "");
		params.put("price", (int) twoBar.getRating() + "");
		params.put("speed", (int) threeBar.getRating() + "");
		params.put("attitude", (int) fourBar.getRating() + "");
		rs.setParams(params);

		rs.setOnSuccessHandler(new OnSuccessHandler() {

			@Override
			public void onSuccess(Object result) {
				Toast.makeText(BusinessEviewActivity.this, "评价成功！",
						Toast.LENGTH_SHORT).show();
				hideUncancelProgressDialog();
				setResult(SUCESS_RESULT_CODE);
				finish();
			}
		});
		rs.setOnFaultHandler(new OnFaultHandler() {

			@Override
			public void onFault(Exception ex) {
				Toast.makeText(BusinessEviewActivity.this, "评价失败！",
						Toast.LENGTH_SHORT).show();
				hideUncancelProgressDialog();
			}
		});
		rs.asyncExecute(this);
	}

	protected void showUncancelProgressDialog(String msg) {
		uncancelProgressDialog = new ProgressDialog(this);
		uncancelProgressDialog.setMessage(msg != null ? msg : "");
		uncancelProgressDialog.setCancelable(false);
		uncancelProgressDialog.show();
	}

	protected void hideUncancelProgressDialog() {
		if (uncancelProgressDialog != null
				&& uncancelProgressDialog.isShowing())
			uncancelProgressDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		hideUncancelProgressDialog();
	}
	
}

package com.sinaleju.lifecircle.app.activity;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.base_activity.BaseActivity;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelFactory;
import com.sinaleju.lifecircle.app.database.entity.UserBean;
import com.sinaleju.lifecircle.app.service.Service.OnFaultHandler;
import com.sinaleju.lifecircle.app.service.Service.OnSuccessHandler;
import com.sinaleju.lifecircle.app.service.remote_impl.entity.RSPersonalInfoEdit;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class PersonalInformationActivity extends BaseActivity {

	private EditText nickName, qq, phone, hometown, living;
	private TextView loginNum, identity, sex, birthday, star;
	private Button nickNameIcon, identityIcon, birthdayIcon, hometownIcon, livingIcon, qqIcon,
			phoneIcon;
	private RelativeLayout identityLayout, sexTextLayout, birthdayLayout;
	private LinearLayout changePasswordLayout;

	private String[] mIdentityArray = new String[] { "业主", "租户", "周边个人" };
	private static final int SHOW_DATAPICK = 0;
	private static final int DATE_DIALOG_ID = 1;
	private int mYear;
	private int mMonth;
	private int mDay;
	private static final String[] mBirthStar = { "水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座",
			"处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };
	private static final int[] mBirthStarEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
	private static final String TAG = "PersonalInformationActivity";
	private String[] mSexArray = new String[] { "保密", "男", "女" };

	private UserBean mUserBean = null;

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.ac_personal_information;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LogUtils.v(TAG, "-------------onCreate--------------");
		nickName = (EditText) findViewById(R.id.nick_name_edit);
		qq = (EditText) findViewById(R.id.qq_edit);
		phone = (EditText) findViewById(R.id.phone_edit);
		hometown = (EditText) findViewById(R.id.hometown_edit);
		living = (EditText) findViewById(R.id.living_edit);
		loginNum = (TextView) findViewById(R.id.login_number);
		identity = (TextView) findViewById(R.id.identity_type);
		sex = (TextView) findViewById(R.id.sex_text);
		birthday = (TextView) findViewById(R.id.birth_date);
		star = (TextView) findViewById(R.id.birth_star);
		nickNameIcon = (Button) findViewById(R.id.nick_name_edit_icon);
		identityIcon = (Button) findViewById(R.id.identity_type_icon);
		birthdayIcon = (Button) findViewById(R.id.birth_date_icon);
		hometownIcon = (Button) findViewById(R.id.hometown_icon);
		livingIcon = (Button) findViewById(R.id.living_icon);
		qqIcon = (Button) findViewById(R.id.qq_edit_icon);
		phoneIcon = (Button) findViewById(R.id.phone_edit_icon);
		identityLayout = (RelativeLayout) findViewById(R.id.identity_type_layout);
		sexTextLayout = (RelativeLayout) findViewById(R.id.sex_text_layout);
		birthdayLayout = (RelativeLayout) findViewById(R.id.birth_date_layout);
		changePasswordLayout = (LinearLayout) findViewById(R.id.change_password_layout);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonShow(true);
		mTitleBar.initLeftButtonTextOrResId(0, R.drawable.selector_topbar_back_button);
		mTitleBar.initRightButtonTextOrResId(0, R.drawable.selector_topbar_save_button);
		mTitleBar.setTitleName("个人资料");

		// 初始化为当前的日期
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDateDisplay();

		mUserBean = AppContext.curUser();
		if (mUserBean != null) {
			initUserData();
		}
	}

	private void initUserData() {
		if (!TextUtils.isEmpty(mUserBean.getName())) {
			nickName.setText(mUserBean.getName());
		} else {
			nickName.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getLogin_name())) {
			loginNum.setText(mUserBean.getLogin_name());
		} else {
			loginNum.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getCategory())) {
			int index = Integer.parseInt(mUserBean.getCategory()) - 1;
			if (index >= 0 && index < mIdentityArray.length) {
				identity.setText(mIdentityArray[index]);
			}
		} else {
			identity.setText(mIdentityArray[0]);
		}
		if (mUserBean.getSex() == 0) {
			sex.setText("保密");
			sexType = 0;
		} else if (mUserBean.getSex() == 1) {
			sex.setText("男");
			sexType = 1;
		} else if (mUserBean.getSex() == 2) {
			sex.setText("女");
			sexType = 2;
		} else {
			sex.setText("保密");
			sexType = 0;
		}
		if (!TextUtils.isEmpty(mUserBean.getBirthday())) {
			birthday.setText(mUserBean.getBirthday());
		} else {
			birthday.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getAstro())) {
			star.setText(mUserBean.getAstro());
		} else {
			star.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getHometown())) {
			hometown.setText(mUserBean.getHometown());
		} else {
			hometown.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getLocation())) {
			living.setText(mUserBean.getLocation());
		} else {
			living.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getQq())) {
			qq.setText(mUserBean.getQq());
		} else {
			qq.setText("");
		}
		if (!TextUtils.isEmpty(mUserBean.getMobile())) {
			phone.setText(mUserBean.getMobile());
		} else {
			phone.setText("");
		}
	}

	private int getSexText(String style) {
		int temp = 0;
		for (int i = 0; i < mSexArray.length; i++) {
			if (style.equals(mSexArray[i])) {
				temp = i;
			}
		}
		return temp;
	}

	private RSPersonalInfoEdit rs = null;
	private int type = 1;
	private int sexType = 0;

	private void editPersonalInfo() {
		String content = nickName.getText().toString();
		if (TextUtils.isEmpty(content.trim())) {
			showToast("昵称不能为空");
			return;
		}
		if (identity.getText().toString().equals("业主")) {
			type = 1;
		} else if (identity.getText().toString().equals("租户")) {
			type = 2;
		} else if (identity.getText().toString().equals("周边个人")) {
			type = 3;
		}
		sexType = getSexText(sex.getText().toString());
		if (rs == null) {
			rs = new RSPersonalInfoEdit(mUserBean.getUid(), content, type, sexType, birthday
					.getText().toString(), star.getText().toString(),
					hometown.getText().toString(), living.getText().toString(), qq.getText()
							.toString(), phone.getText().toString());

			rs.setOnSuccessHandler(new OnSuccessHandler() {

				@Override
				public void onSuccess(Object result) {
					System.out.print("onSuccess: \n" + result);
					hideProgressDialog();
					showToast("保存资料成功");
					setResult(601);
					setUserBeanData(type);
					PublicUtils.hideSoftInputMethod(PersonalInformationActivity.this);
					finish();
					rs = null;
				}

			});

			rs.setOnFaultHandler(new OnFaultHandler() {

				@Override
				public void onFault(Exception ex) {
					hideProgressDialog();
					showToast("保存资料失败");
					rs = null;
				}

			});
			showProgressDialog("正在保存资料...", true);
			rs.asyncExecute(this);
		}

	}

	private void setUserBeanData(int type) {
		mUserBean.setName(nickName.getText().toString());
		mUserBean.setCategory(type + "");
		mUserBean.setSex(sexType);
		mUserBean.setBirthday(birthday.getText().toString());
		mUserBean.setAstro(star.getText().toString());
		mUserBean.setHometown(hometown.getText().toString());
		mUserBean.setLocation(living.getText().toString());
		mUserBean.setQq(qq.getText().toString());
		mUserBean.setMobile(phone.getText().toString());
		ApplicationFacade.getInstance().sendNotification(
				AppConst.APP_FACADE_LEFT_HOME_FRAGMENT_USER_UI_REFRESH);
	}

	@Override
	protected void initCallbacks() {
		// TODO Auto-generated method stub
		mTitleBar.setLeftButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicUtils.hideSoftInputMethod(PersonalInformationActivity.this);
				finish();
			}
		});

		mTitleBar.setRightButton1Listener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editPersonalInfo();
			}
		});

		identityLayout.setOnClickListener(clickListener);
		sexTextLayout.setOnClickListener(clickListener);
		birthdayLayout.setOnClickListener(clickListener);
		changePasswordLayout.setOnClickListener(clickListener);
		nickName.setOnFocusChangeListener(focusChangeListener);
		hometown.setOnFocusChangeListener(focusChangeListener);
		living.setOnFocusChangeListener(focusChangeListener);
		qq.setOnFocusChangeListener(focusChangeListener);
		phone.setOnFocusChangeListener(focusChangeListener);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.identity_type_layout:
				WheelFactory.getSingleWheelWithStringArray(mContext, mIdentityArray, identity, 0)
						.show();
				break;
			case R.id.sex_text_layout:
				WheelFactory.getSingleWheelWithStringArray(mContext, mSexArray, sex, sexType)
						.show();
				break;
			case R.id.birth_date_layout:
				showDatePicker();
				break;
			case R.id.change_password_layout:
				// 修改密码
				Intent changeIntent = new Intent(mContext, ChangePosswordActivity.class);
				startActivity(changeIntent);
				break;
			default:
				break;
			}
		}
	};

	OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View view, boolean hasFocus) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.nick_name_edit:
				if (hasFocus) {
					nickNameIcon.setVisibility(View.GONE);
				} else {
					nickNameIcon.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.qq_edit:
				if (hasFocus) {
					qqIcon.setVisibility(View.GONE);
				} else {
					qqIcon.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.phone_edit:
				if (hasFocus) {
					phoneIcon.setVisibility(View.GONE);
				} else {
					phoneIcon.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.hometown_edit:
				if (hasFocus) {
					hometownIcon.setVisibility(View.GONE);
				} else {
					hometownIcon.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.living_edit:
				if (hasFocus) {
					livingIcon.setVisibility(View.GONE);
				} else {
					livingIcon.setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
			}
		}
	};

	private void showDatePicker() {
		Message msg = new Message();
		msg.what = SHOW_DATAPICK;
		dateandtimeHandler.sendMessage(msg);
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	private void updateDateDisplay() {
		String y_m_d = new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay).toString();
		birthday.setText(y_m_d);
		star.setText(getBirthStarString());
	}

	protected android.app.Dialog onCreateDialog(int id) {
		switch (id) {

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);

		}
		return null;
	}

	protected void onPrepareDialog(int id, android.app.Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;

		default:
			break;
		}

	};

	Handler dateandtimeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case SHOW_DATAPICK:
				showDialog(DATE_DIALOG_ID);
				break;
			}
		}

	};

	private String getBirthStarString() {
		if (mDay < mBirthStarEdgeDay[mMonth]) {
			mMonth = mMonth - 1;
		}
		if (mMonth >= 0) {
			return mBirthStar[mMonth];
		}
		// default to return 魔羯
		return mBirthStar[0];
	}

}

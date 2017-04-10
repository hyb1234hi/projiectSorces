package com.sinaleju.lifecircle.app.base_fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	protected ProgressDialog mProgressDialog = null;
	protected Toast toast = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	protected void initView() {

	}

	protected void initData() {

	}

	protected void setListener() {

	}

	protected void showProgressDialog(String msg, boolean isCancel) {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setMessage(msg != null ? msg : "");
		mProgressDialog.setCancelable(isCancel);
		mProgressDialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						cancelTask();
					}
				});
		mProgressDialog.show();
	}

	protected void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}

	protected void cancelTask() {

	}

	@Override
	public void onResume() {
		super.onResume();
		// initData();
	}

	protected void showToast(int strId) {
		showToast(getString(strId));
	}

	protected void showToast(String text) {
		if (toast == null) {
			if (getActivity() != null) {
				toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
				toast.show();
			}
		} else {
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setText(text);
			toast.show();
		}
	}
}

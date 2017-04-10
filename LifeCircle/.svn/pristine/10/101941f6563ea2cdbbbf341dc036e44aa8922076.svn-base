package com.sinaleju.lifecircle.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.sinaleju.lifecircle.R;

public class DialogUtils {

	public static void showDialDialog(final Context ctx, final String mPhoneNo){
		new AlertDialog.Builder(ctx).setTitle("拨打电话")
		.setMessage(mPhoneNo)
		.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ mPhoneNo));
				ctx.startActivity(intent);
			}
		}).setNegativeButton(R.string.dialog_cancle, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
		
	}
}

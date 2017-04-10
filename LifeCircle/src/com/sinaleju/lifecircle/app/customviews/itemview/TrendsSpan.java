package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppCache;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.activity.CommWebViewAct;
import com.sinaleju.lifecircle.app.activity.TopicDetailActivity;
import com.sinaleju.lifecircle.app.activity.TrendsDetailActivity;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanText;
import com.sinaleju.lifecircle.app.model.Model_TrendsBase.SpanType;
import com.sinaleju.lifecircle.app.model.Model_TrendsMsg;
import com.sinaleju.lifecircle.app.utils.LogUtils;
import com.sinaleju.lifecircle.app.utils.PublicUtils;

public class TrendsSpan {

	protected static final String TAG = "TrendsSpan";
	private static Toast toast;
	private static final int COLOR_AT = 0xff699ACC;
	private static String activityName;

	public static SpannableString createTrendsSpan(final Context context, SpanText... types) {
		activityName = context.getClass().getName();
		if (types == null || types.length == 0) {
			LogUtils.e(TAG, "空的 types");
			return new SpannableString("");
		}

		// // space
		// for (int i = 0; i < types.length; i++) {
		// SpanText t = types[i];
		// if (t.getSpanType() == SpanType.NAME || t.getSpanType() ==
		// SpanType.COMMUNITY) {
		// t.setText((i > 0 ? " " : "") + t.getText().trim() + " ");
		// }
		// }
		//
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < types.length; i++) {
			sb.append(types[i].getText());
		}
		SpannableString span = new SpannableString(sb.toString());

		int index = 0;
		for (int i = 0; i < types.length; i++) {
			final SpanText type = types[i];
			final String text = type.getText();
			final SpanType t_type = type.getSpanType();
			int len = text.length();

			if (t_type == SpanType.NAME || t_type == SpanType.AT || t_type == SpanType.TOPIC
					|| t_type == SpanType.COMMUNITY || t_type == SpanType.TEXT) {

				if (t_type != SpanType.TEXT) {
					span.setSpan(new ForegroundColorSpan(t_type == SpanType.NAME
							|| t_type == SpanType.COMMUNITY ? Color.BLACK : COLOR_AT), index, index
							+ len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
				span.setSpan(new ClickableSpan() {

					@Override
					public void updateDrawState(TextPaint ds) {
						// if (t_type == SpanType.NAME || t_type ==
						// SpanType.COMMUNITY)
						// ds.setFakeBoldText(true);
						// TODO:super.updateDrawState(ds); when url

					}

					@Override
					public void onClick(View widget) {
						if (activityName.contains("OfficHomeActivity")) {
							return;
						}
						if (t_type == SpanType.TOPIC) {
							Intent intent = new Intent(context, TopicDetailActivity.class);
							intent.putExtra(AppConst.INTENT_TOPIC_DETAIL_ID, type.getItem_id());
							context.startActivity(intent);
							return;
						}
						if (t_type == SpanType.NAME || t_type == SpanType.AT) {

							// visiting card show
							AppContext.performVisitPeople(context, type.getItem_id(),
									type.getUserType());
							return;
						}

						if (t_type == SpanType.COMMUNITY) {

							return;
						}
						if (t_type == SpanType.TEXT) {
							//
							Object o = type.getTag();
							if (o != null) {
								Intent intent = new Intent(context, TrendsDetailActivity.class);
								AppCache.getInstance().put("originalDetailModel",
										(Model_TrendsMsg) o);
								// 为了体验，这里依然需要通过序列化传送 之后在act启动后 如果之前由于筛选 网络延迟导致的
								// o被置空回收，则通过appCache去判断
								intent.putExtra("msg", (Model_TrendsMsg) o);
								context.startActivity(intent);
							}
							return;
						}
					}

				}, index, index + len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (t_type == SpanType.EMOTION) {

			} else if (t_type == SpanType.URL || t_type == SpanType.TICKET) {
				span.setSpan(new ImageSpan(context, R.drawable.url_link_icon), index, index + len,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				span.setSpan(new ClickableSpan() {

					@Override
					public void onClick(View widget) {
						// TODO Auto-generated method stub
						if (t_type == SpanType.URL) {
							gotoCommWebViewAct(text, context);
						} else if (t_type == SpanType.TICKET) {
							String ticketUrl = PublicUtils.getTicketUrl(text);
							gotoCommWebViewAct(ticketUrl, context);
						}
					}
				}, index, index + len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			index += len;
		}

		return span;
	}

	private static void gotoCommWebViewAct(String url, Context context) {
		Intent intent = new Intent(context, CommWebViewAct.class);
		intent.putExtra("ticket_url", url.trim());
		context.startActivity(intent);
	}
}

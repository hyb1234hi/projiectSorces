package com.sinaleju.lifecircle.app.chatandnotice;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;

public class TabView extends LinearLayout implements OnClickListener {

	private View mRoot;

	private TextView msgTV, noticeTV;
	private TextView slideBlock;// aniNoticeTV;

	private ClickCallback clickListener;

	private final int DURATION = 200;

	private final int LEFT_TO_RIGHT = 1;
	private final int RIGHT_TO_LEFT = 2;

	private int index;

	private String msg, notice;

	private int SLIDE_BLOCK_WIDTH;
	private int width;// 总的宽度
	private float padding;//

	private ImageView chatIcon;
	private ImageView noticeIcon;

	public TabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mRoot = View.inflate(getContext(), R.layout.msg_and_notice_tab_view,
				this);

		msgTV = (TextView) findViewById(R.id.msg_tab);
		noticeTV = (TextView) findViewById(R.id.notice_tab);
		slideBlock = (TextView) findViewById(R.id.msg_selected_tab);
		// aniNoticeTV = (TextView)findViewById(R.id.notice_selected_tab);
		chatIcon = (ImageView) findViewById(R.id.msg_new_icon);
		noticeIcon = (ImageView) findViewById(R.id.notice_new_icon);

		index = LEFT_TO_RIGHT;

		setListener();
	}

	public void setChatIconShowOrHide(boolean isShow) {
		if (isShow) {
			chatIcon.setVisibility(View.VISIBLE);
		} else {
			chatIcon.setVisibility(View.INVISIBLE);
		}
	}

	public void setNoticeIconShowOrHide(boolean isShow) {
		if (isShow) {
			noticeIcon.setVisibility(View.VISIBLE);
		} else {
			noticeIcon.setVisibility(View.INVISIBLE);
		}
	}

	private void setListener() {
		msgTV.setOnClickListener(this);
		noticeTV.setOnClickListener(this);
	}

	public void setClickCallback(ClickCallback clickListener) {
		this.clickListener = clickListener;
	}

	public void setText(String text1, String text2) {
		this.msg = text1;
		this.notice = text2;

		msgTV.setText(msg);
		slideBlock.setText(msg);
		noticeTV.setText(notice);
		// aniNoticeTV.setText(notice);
	}

	public void setBG(int resourceId) {
		mRoot.setBackgroundResource(resourceId);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.msg_tab:
			if (index == LEFT_TO_RIGHT)
				return;
			index = LEFT_TO_RIGHT;

			startTranslateAnimation(slideBlock, msgTV.getText().toString(),
					SLIDE_BLOCK_WIDTH, 0, 0, 0);
			break;
		case R.id.notice_tab:
			if (index == RIGHT_TO_LEFT)
				return;
			index = RIGHT_TO_LEFT;

			startTranslateAnimation(slideBlock, noticeTV.getText().toString(),
					0, SLIDE_BLOCK_WIDTH + padding, 0, 0);
			break;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 在xml配置中外层的布局有个padding是1dp
		padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
				getContext().getResources().getDisplayMetrics());

		SLIDE_BLOCK_WIDTH = (int) (w / 2 - padding * 2);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) slideBlock
				.getLayoutParams();
		params.width = SLIDE_BLOCK_WIDTH;
		slideBlock.setLayoutParams(params);
	}

	private TranslateAnimation startTranslateAnimation(final TextView tv,
			String tvText, float startX, float toX, float startY, float toY) {
		TranslateAnimation anim = new TranslateAnimation(startX, toX, startY,
				toY);
		anim.setDuration(DURATION);
		anim.setFillAfter(true);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (null != clickListener) {
					clickListener
							.click(index == LEFT_TO_RIGHT ? ClickCallback.FIRST_INDEX
									: ClickCallback.SECOND_INDEX);
				}

				if (index == LEFT_TO_RIGHT) {
					tv.setText(msgTV.getText());
				} else {
					tv.setText(noticeTV.getText());
				}
			}
		});

		tv.startAnimation(anim);
		return anim;
	}

	public interface ClickCallback {
		final int FIRST_INDEX = 1;
		final int SECOND_INDEX = 2;

		void click(int index);
	}
}

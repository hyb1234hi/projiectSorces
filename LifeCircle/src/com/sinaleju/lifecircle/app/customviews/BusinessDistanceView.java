package com.sinaleju.lifecircle.app.customviews;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class BusinessDistanceView extends LinearLayout implements OnClickListener{

	private View mRoot;
	private Context mContext;
	
	private final int FIRST_INDEX = 0, SECOND_INDEX = 1, THIRD_INDEX = 2, FOUR_INDEX = 3;
	
	private RelativeLayout layout;
	private Button btn1, btn2, btn3, btn4;
	private TextView frontIndicatior;
	private ImageView connect1, connect2, connect3;
	
	private ArrayList<Button> btnList;
	private ArrayList<ImageView> imgList;
	
	private ClickCallback callback;
	
	private int index;
	
	public BusinessDistanceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public BusinessDistanceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context mContext){
		this.mContext = mContext;
		
		mRoot = View.inflate(getContext(), R.layout.business_distance_view, this);
		
		layout = (RelativeLayout)mRoot.findViewById(R.id.nearby_layout);
		btn1 = (Button)mRoot.findViewById(R.id.business_distance_btn1);
		btn2 = (Button)mRoot.findViewById(R.id.business_distance_btn2);
		btn3 = (Button)mRoot.findViewById(R.id.business_distance_btn3);
		btn4 = (Button)mRoot.findViewById(R.id.business_distance_btn4);
		frontIndicatior = (TextView)mRoot.findViewById(R.id.business_distance_front_btn);
		
		connect1 = (ImageView)mRoot.findViewById(R.id.business_distance_connect1);
		connect2 = (ImageView)mRoot.findViewById(R.id.business_distance_connect2);
		connect3 = (ImageView)mRoot.findViewById(R.id.business_distance_connect3);
		
		btnList = new ArrayList<Button>();
		btnList.add(btn1);
		btnList.add(btn2);
		btnList.add(btn3);
		btnList.add(btn4);
		
		imgList = new ArrayList<ImageView>();
		imgList.add(connect1);
		imgList.add(connect2);
		imgList.add(connect3);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		
		//setSelectedPosition(0);
	}
	
	private void setSelectedPosition(int position){
		for(int i=0; i<=position; i++){
			if(i ==0){//第一个
				btnList.get(i).setBackgroundResource(R.drawable.nearby_left_selected_icon);
			}else if(i == (btnList.size() - 1)){//最后一个
				btnList.get(i).setBackgroundResource(R.drawable.nearby_right_unselected_icon);
			}else{//中间的
				btnList.get(i).setBackgroundResource(R.drawable.nearby_middle_selected_icon);
			}
			if(i - 1 >= 0)imgList.get(i-1).setBackgroundResource(R.drawable.nearby_connect_selected_icon);
		}
		
		for(int i=position+1; i<btnList.size(); i++){
			if(i < btnList.size()-1){
				btnList.get(i).setBackgroundResource(R.drawable.nearby_middle_unselected_icon);
			}else{
				btnList.get(i).setBackgroundResource(R.drawable.nearby_right_unselected_icon);
			}
			if(i - 1 >= 0)imgList.get(i-1).setBackgroundResource(R.drawable.nearby_connect_unselected_icon);
		}
		int []parentLocations = new int[2];
		layout.getLocationOnScreen(parentLocations);
		
		int [] lastLocations = new int[2];
		btnList.get(index).getLocationOnScreen(lastLocations);
		int [] curLocation = new int[2];
		btnList.get(position).getLocationOnScreen(curLocation);
		
		frontIndicatior.setText((position+1) + "km");
		if(frontIndicatior.getVisibility() != View.VISIBLE) frontIndicatior.setVisibility(View.VISIBLE);
		
		if(position == btnList.size() -1){
			frontIndicatior.setBackgroundResource(R.drawable.nearby_cur_right_selected_icon);
			frontIndicatior.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
		}else if(position == 0){
			frontIndicatior.setBackgroundResource(R.drawable.nearby_cur_left_selected_icon);
			frontIndicatior.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
		}else{
			frontIndicatior.setBackgroundResource(R.drawable.nearby_cur_middle_selected_icon);
			frontIndicatior.setGravity(Gravity.CENTER);
		}
		
		moveFrontBg(frontIndicatior, lastLocations[0] - parentLocations[0], curLocation[0] - parentLocations[0], 0, 0);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.business_distance_btn1:
			LogUtils.d("zhenwei", "business_distance_btn1 onClick!!");
			setSelectedPosition(FIRST_INDEX);
			index = FIRST_INDEX;
			//moveFrontBg(btn1, 0, 300, 0, 0);
			if(null != callback) callback.clickCallback(0);
			break;
		
		case R.id.business_distance_btn2:
			setSelectedPosition(SECOND_INDEX);
			index = SECOND_INDEX;
			if(null != callback) callback.clickCallback(1);
			break;
			
		case R.id.business_distance_btn3:
			setSelectedPosition(THIRD_INDEX);
			index = THIRD_INDEX;
			if(null != callback) callback.clickCallback(2);
			break;
			
		case R.id.business_distance_btn4:
			setSelectedPosition(FOUR_INDEX);
			index = FOUR_INDEX;
			if(null != callback) callback.clickCallback(3);
			break;
			
		}
	}
	
	public void moveFrontBg(View v, int startX, int toX, int startY, int toY) {
		TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
		anim.setDuration(150);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}
	
	public void setClickCallback(ClickCallback callback){
		this.callback = callback;
	}
	
	public interface ClickCallback{
		void clickCallback(int position);
	}
}

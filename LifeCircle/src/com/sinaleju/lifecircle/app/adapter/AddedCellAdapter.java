package com.sinaleju.lifecircle.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.HomeActivity;
import com.sinaleju.lifecircle.app.database.entity.CommunityBean;
import com.sinaleju.lifecircle.app.fragment.MyCommunitiesFragment;
import com.sinaleju.lifecircle.app.utils.LogUtils;

public class AddedCellAdapter extends BaseAdapter {

	protected static final String TAG = "AddedCellAdapter";
	private Context mContext = null;
	private List<CommunityBean> mAddedCellModels = new ArrayList<CommunityBean>();
	private MyCommunitiesFragment addedCellActivity = null;
	//private boolean mIsHeadClick = false;
	private int index = 0;
	private  LinearLayout previousLayout=null;
	private Animation push=null;
	private Animation pull=null;
	public AddedCellAdapter( Context context ,MyCommunitiesFragment activity,
			List<CommunityBean> mAddedCellModels) {
		super();
		this.mContext = context;
		LogUtils.v(TAG, "------------Adapter-------mContext ::" + mContext);
		addedCellActivity = activity;
		this.mAddedCellModels = mAddedCellModels;
		push=AnimationUtils.loadAnimation(mContext, R.anim.anim_editcommunity_push);
		pull=AnimationUtils.loadAnimation(mContext, R.anim.anim_editcommunity_pull);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		LogUtils.i(TAG, "count: "+mAddedCellModels.size());
		return mAddedCellModels == null ? 0 : mAddedCellModels.size();
	}

	@Override
	public CommunityBean getItem(int position) {
		// TODO Auto-generated method stub
		if ((null != mAddedCellModels && mAddedCellModels.size() > 0)
				&& (position >= 0 && position < mAddedCellModels.size())) {
			return mAddedCellModels.get(position);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LogUtils.i(TAG, "parent.count: "+parent.getChildCount() +"positon: "+position);
		AddedCellItemHolder cellHolder = null;
		if (convertView == null || null == convertView.getTag()) {
			cellHolder = new AddedCellItemHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_added_cell, null);
			cellHolder.cellName = (TextView) convertView
					.findViewById(R.id.added_cell_name);
			//cellHolder.cellBtn = (LinearLayout) convertView.findViewById(R.id.added_cell_button);
			convertView.setTag(cellHolder);
		} else {
			cellHolder = (AddedCellItemHolder) convertView.getTag();
		}

		CommunityBean cell = getItem(position);
		if (cell != null) {
			cellHolder.cellName.setText(cell.getName());
		}
		addDrawerEvent(position, convertView, parent, cell);

		return convertView;

	}

	public void cleanDrawerManager() {
		if (drawerManager != null) {
			drawerManager.cleanDrawer();
		}
	}

	public class DrawerManager {

		private ViewGroup mDrawerRecord;

		private int mDrawerPosition = -1;

		private Object mContactOrPhone = null;

		public void openDrawer(ViewGroup drawer, int position) {
			if (mDrawerRecord != null && mDrawerPosition != -1) {
				mDrawerRecord.setVisibility(View.GONE);
			}
			drawer.setVisibility(View.VISIBLE);
			mDrawerRecord = drawer;
			mDrawerPosition = position;
			mContactOrPhone = (getItem(position) == null) ? null
					: getItem(position);
		}

		public void closeDrawer(ViewGroup drawer, int position) {
			drawer.setVisibility(View.GONE);
			if (position != -1 && position == mDrawerPosition) {
				mDrawerRecord = null;
				mDrawerPosition = -1;
				mContactOrPhone = null;
			}
		}

		public void closeDrawer(Object contactOrPhone) {
			if (mContactOrPhone != null
					&& !mContactOrPhone.equals(contactOrPhone)) {
				if (mDrawerRecord != null && mDrawerPosition != -1) {
					mDrawerRecord.setVisibility(View.GONE);
				}
				mDrawerRecord = null;
				mDrawerPosition = -1;
				mContactOrPhone = null;
			}
		}

		public boolean reopenDrawer(ViewGroup drawer, int position) {
			if (mContactOrPhone == null) {
				return false;
			}
			CommunityBean cell = getItem(position);
			if (cell == null) {
				return false;
			}
			if (mDrawerRecord.getVisibility() == View.GONE) {
				drawer.setVisibility(View.VISIBLE);
				mDrawerRecord = drawer;
				mDrawerPosition = position;
				return true;
			}
			return false;
		}

		public void cleanDrawer() {
			mDrawerRecord = null;
			mDrawerPosition = -1;
			mContactOrPhone = null;
		}

		public void headDrawer() {
			if (null != mDrawerRecord) {
				mDrawerRecord.setVisibility(View.GONE);
			}
		}

	}

	private DrawerManager drawerManager = new DrawerManager();

	public void hideDrawer() {
		drawerManager.headDrawer();
		cleanDrawerManager();
	}

	public void clearDrawer(Object contactOrPhone) {
		drawerManager.closeDrawer(contactOrPhone);
	}

	//private ScrollListViewBottomViewListener mScrollListView;

	public interface ScrollListViewBottomViewListener {
		public void scrollBottomView();
	}

//	public void setScrollListViewBottomViewListener(
//			ScrollListViewBottomViewListener scrollListView) {
//		mScrollListView = scrollListView;
//	}

	private void addDrawerEvent(final int position, View convertView,
			ViewGroup parent, final CommunityBean cell) {
		final int pos = position;
		//LogUtils.i(TAG, "parent.count: "+parent.getChildCount());
		//final ViewGroup curParent = parent;
		final View curView = convertView;
		//final AddedCellItemHolder cellHolder = (AddedCellItemHolder) convertView	.getTag();
		//final LinearLayout drawer = (LinearLayout) convertView.findViewById(R.id.drawer);
		//test
		final LinearLayout editLayout=(LinearLayout) convertView.findViewById(R.id.item_added_cell_editlayout);
		
		
		if (curView == null || editLayout == null) {
			return;
		}
		//drawer.setVisibility(View.GONE);
		//test
		editLayout.setVisibility(View.GONE);		
		
		/*cellHolder.cellBtn*/curView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {					
				Button quitButton=(Button) editLayout.findViewById(R.id.editlayout_exit);
				Button enterButton=(Button) editLayout.findViewById(R.id.editlayout_enter);
				quitButton.setOnClickListener(clickListener);
				enterButton.setOnClickListener(clickListener);
			//	LinearLayout quitLayout = null;
			//	LinearLayout enterLayout = null;
				// LinearLayout replaceLayout = null;
				index = pos;
				//int curPos = curParent.indexOfChild(curView);
				switch (editLayout.getVisibility()) {
				case View.GONE:
					//隐藏上一个编辑层
					hideEditLayout();
//					if(previousLayout!=null&&previousLayout.getVisibility()==View.VISIBLE){
//						previousLayout.startAnimation(pull);
//						previousLayout.setVisibility(View.GONE);
//					}
					editLayout.startAnimation(push);
					editLayout.setVisibility(View.VISIBLE);
					previousLayout=editLayout;
//					if (drawer.getChildCount() > 0) {
//						drawer.removeAllViews();
//					}
//					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//							LinearLayout.LayoutParams.MATCH_PARENT,
//							LinearLayout.LayoutParams.WRAP_CONTENT);
//
//					View itemView = LayoutInflater.from(mContext).inflate(
//							R.layout.item_xiala_cell, null);
//					quitLayout = (LinearLayout) itemView
//							.findViewById(R.id.cell_item_quit_layout);
//					enterLayout = (LinearLayout) itemView
//							.findViewById(R.id.cell_item_enter_layout);
//					// replaceLayout = (LinearLayout)
//					// itemView.findViewById(R.id.cell_item_replace_layout);
//					quitLayout.setOnClickListener(clickListener);
//					enterLayout.setOnClickListener(clickListener);
					// replaceLayout.setOnClickListener(clickListener);
					//drawer.addView(itemView, params);
					//showheadClick(pos, curParent, drawer, curPos);
					//drawerManager.reopenDrawer(drawer, position);
					break;
				case View.VISIBLE:
					
					editLayout.setVisibility(View.GONE);
					editLayout.startAnimation(pull);
					
//					if (mIsHeadClick) {
//						hideHeadClick(pos, curParent, drawer, curPos);
//					}
					break;
				default:
					break;
				}
			}

		});
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			//case R.id.cell_item_quit_layout:
			case R.id.editlayout_exit:
				hideEditLayout();
				// 删除当前小区
				if (mAddedCellModels.size() > 1) {
					int Community_id = mAddedCellModels.get(index).getCid();
					addedCellActivity.deleteCommnity(Community_id);
				}else{
					Toast.makeText(mContext, "你至少要加入一个小区哟", Toast.LENGTH_SHORT).show();
				}
				ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH);
				
				//refresh homefragment
				break;
				// Toast.makeText(mContext, index + "",
				// Toast.LENGTH_SHORT).show();
				
			//case R.id.cell_item_enter_layout:
			case R.id.editlayout_enter:
				hideEditLayout();
				// 进入当前小区
				AppContext.curUser().setCurrentCommunity(
						mAddedCellModels.get(index));
				ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_HOME_FRAGMENT_NEED_REFRESH);
				ApplicationFacade.getInstance().sendNotification(AppConst.APP_FACADE_ENTER_HOMEPAGE_TOP);
				((HomeActivity)addedCellActivity.getActivity()).backToMainPage();
				
				//refresh homefragment
				//Intent homeIntent = new Intent(mContext, HomeActivity.class);
				//mContext.startActivity(homeIntent);
				//addedCellActivity.getActivity().
				// Toast.makeText(mContext, index + "",
				// Toast.LENGTH_SHORT).show();
				break;
			// case R.id.cell_item_replace_layout:
			// Toast.makeText(mContext, index + "", Toast.LENGTH_SHORT).show();
			// break;
			default:
				break;
			}
		}
	};

//	private void showheadClick(final int pos, final ViewGroup curParent,
//			final LinearLayout drawer, int curPos) {
//		Animation pullDrawer = AnimationUtils.loadAnimation(mContext,
//				R.anim.anim_drawer_pull);
//		drawer.clearAnimation();
//		drawer.startAnimation(pullDrawer);
//		for (int i = curPos + 1; i < curParent.getChildCount(); i++) {
//			View childView = curParent.getChildAt(i);
//			childView.clearAnimation();
//			childView.startAnimation(pullDrawer);
//		}
//		drawerManager.openDrawer(drawer, pos);
//		if ((curPos == curParent.getChildCount() - 1 || curPos == curParent
//				.getChildCount() - 2) && mScrollListView != null) {
//			mScrollListView.scrollBottomView();
//		}
//		mIsHeadClick = true;
//	}
//
//	private void hideHeadClick(final int pos, final ViewGroup curParent,
//			final LinearLayout drawer, int curPos) {
//		Animation pushDrawer = AnimationUtils.loadAnimation(mContext,
//				R.anim.anim_drawer_push);
//		pushDrawer.setAnimationListener(new Animation.AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				drawerManager.closeDrawer(drawer, pos);
//			}
//		});
//		drawer.clearAnimation();
//		drawer.startAnimation(pushDrawer);
//		for (int i = curPos + 1; i < curParent.getChildCount(); i++) {
//			View childView = curParent.getChildAt(i);
//			childView.clearAnimation();
//			childView.startAnimation(pushDrawer);
//		}
//		mIsHeadClick = false;
//	}
//
//	public boolean getIsHeadClick() {
//		return mIsHeadClick;
//	}
//
//	public void setIsHeadClick(boolean isHide) {
//		mIsHeadClick = isHide;
//	}

	private static class AddedCellItemHolder {

		public TextView cellName;

		//public LinearLayout cellBtn;

	}
	public void hideEditLayout(){
		if(previousLayout!=null&&previousLayout.getVisibility()==View.VISIBLE){
			previousLayout.startAnimation(pull);
			previousLayout.setVisibility(View.GONE);
		}
	}
	

}

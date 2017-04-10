package com.sinaleju.lifecircle.app.customviews.itemview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.AppConst;
import com.sinaleju.lifecircle.app.AppContext;
import com.sinaleju.lifecircle.app.ApplicationFacade;
import com.sinaleju.lifecircle.app.activity.PhotoAlbumActivity;
import com.sinaleju.lifecircle.app.adapter.BusImageAdapter;
import com.sinaleju.lifecircle.app.customviews.HorizontalListView;
import com.sinaleju.lifecircle.app.model.Model_BusinessPageImage;
import com.sinaleju.lifecircle.app.model.impl.BaseModel;

public class Item_BusinessPageImage extends AbsItemView {

	private HorizontalListView listView = null;
	private TextView mImageTitle = null;

	public Item_BusinessPageImage(Context context) {
		super(context);
	}

	@Override
	public void init() {
		LayoutInflater.from(getContext())
				.inflate(R.layout.item_bus_image, this);

		initViews();
		setItemListeners();
	}

	private void initViews() {
		listView = (HorizontalListView) findViewById(R.id.bus_image_list);
		mImageTitle = (TextView) findViewById(R.id.bus_image_title);

		// set un-controls
		AppContext appContext = (AppContext) getContext()
				.getApplicationContext();
		appContext.getHomeActivity().addUnControledViews(listView);

	}

	@Override
	protected void toSet(int type,BaseModel model) {

		if (listView == null) {
			initViews();
		}
		final Model_BusinessPageImage image = (Model_BusinessPageImage) model;
		if (image.getImages().size() != 0) {
			BusImageAdapter adapter = new BusImageAdapter(getContext(),
					image.getImages());
			listView.setAdapter(adapter);
		}
		if (image.isMySelf()) {
			// set un-controls
			AppContext appContext = (AppContext) getContext()
					.getApplicationContext();
			appContext.getHomeActivity().addUnControledViews(listView);
			ApplicationFacade.getInstance().sendNotification(
					AppConst.APP_FACADE_BUSINESS_IMAGE_LIST
							+ getContext().hashCode(), listView);
		}
		if (image.isProperty()) {
			mImageTitle.setText("公司图片");
		} else {
			mImageTitle.setText("商家图片");
		}
		if (image.getImages().size() != 0) {
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					// TODO Auto-generated method stub
					String[] urls = new String[image.getImages().size()];
					for (int i = 0; i < image.getImages().size(); i++) {
						urls[i] = image.getImages().get(i).getImageUrl();
					}
					Intent intent = new Intent(getContext(),
							PhotoAlbumActivity.class);
					intent.putExtra("urls", urls);
					intent.putExtra("pos", pos);
					getContext().startActivity(intent);
				}

			});
		}
		disableRefresh();
	}

	@Override
	public boolean enable() {
		return false;
	}

	@Override
	protected void onClickThis(BaseModel model) {

	}

	private void setItemListeners() {

	}

}

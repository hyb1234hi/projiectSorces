package com.sinaleju.lifecircle.app.customviews.picker;

import java.util.List;

import android.content.Context;

import com.sinaleju.lifecircle.R;
import com.sinaleju.lifecircle.app.customviews.picker.pickerimpl.WheelItem;

/**
 * To use this kind of WheelAdapter you need push a List of WheelItem(
 * {@link WheelItem}) as data;
 * 
 * @author sunny.dai
 * 
 */
public class WheelAdapter extends AbstractWheelTextAdapter {
	private List<WheelItem> data;
	
	/**
	 * Constructor
	 * @param context android Context
	 * @param areaList 
	 */
	public WheelAdapter(Context context, List<WheelItem> areaList) {
		super(context, R.layout.item_station_wheel_text);
		data = areaList;
		setItemTextResource(R.id.station_wheel_textView);
	}

	@Override
	public int getItemsCount() {
		return data.size();
	}

	@Override
	public CharSequence getItemText(int index) {
		if(getItemsCount()==0)
			return "";
		return data.get(index).getName();
	}

	public WheelItem getItem(int position) {
		if (data.size()>0&&position >= 0) {
			return data.get(position);
		}
		return null;
	}

	

}

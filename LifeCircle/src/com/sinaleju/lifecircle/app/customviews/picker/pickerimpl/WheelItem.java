package com.sinaleju.lifecircle.app.customviews.picker.pickerimpl;

import java.util.ArrayList;
import java.util.List;

public class WheelItem {

	private String name;
	private Object tag;

	public WheelItem(String name, Object tag) {
		this.name = name;
		this.tag = tag;
	}

	/**
	 * 
	 * @param list
	 *            usually is list with beans,can be null
	 * @param names
	 * @return
	 */
	public static List<WheelItem> makeItemsFromObjects(
			List<? extends I_WheelItemModel> list) {
		List<WheelItem> items = new ArrayList<WheelItem>();
		for (int i = 0; i < list.size(); i++) {
			I_WheelItemModel m = list.get(i);
			if (m != null)
				items.add(new WheelItem(m.getWName(), m));
		}
		return items;
	}

	/**
	 * 
	 * @param list
	 *            usually is list with beans,can be null
	 * @param names
	 * @return
	 */
	public static <T> List<WheelItem> makeItemsFromObjects(List<T> list,
			String[] names) {
		List<WheelItem> items = new ArrayList<WheelItem>();
		for (int i = 0; i < names.length; i++)
			items.add(new WheelItem(names[i], list == null ? null : list.get(i)));
		return items;
	}

	/**
	 * obj是index
	 * 
	 * @param names
	 * @return
	 */
	public static <T> List<WheelItem> makeItemsFromObjects(String[] names) {
		List<WheelItem> items = new ArrayList<WheelItem>();
		for (int i = 0; i < names.length; i++)
			items.add(new WheelItem(names[i], i));
		return items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public interface I_WheelItemModel {
		String getWName();
	}
}

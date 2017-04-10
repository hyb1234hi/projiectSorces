package com.sinaleju.lifecircle.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ADTimeUtils {
	
	public static String node(int timestamp){
		
		int nowtime = (int) (System.currentTimeMillis()/1000);
		int nodetime = nowtime - timestamp;
		
		if(nodetime<0)
			return "未知";
		
		int temp = 0;
		if( (temp = nodetime/(24*60*60))>0){
			if(temp>365){
				return temp/365+"年前";
			}
			if(temp>30){
				return temp/30+"月前";
			}
			return temp+"天前";
		}else if((temp = nodetime/(60*60))>0){
			return temp+"小时前";
		}else if((temp = nodetime/60)>1){
			return temp+"分钟前";
		}else{
			return "刚刚";
		}
	}
	
	public static String timeFormat(long timestamp){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date(timestamp));
	}
}

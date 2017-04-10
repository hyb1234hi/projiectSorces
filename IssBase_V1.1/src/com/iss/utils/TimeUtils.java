package com.iss.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class TimeUtils {

    
    private static final String TAG = "TimeUtils";

	/**
     * 获得当前日期时间格式
     * 
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
    /**
     * 获得当前日期时间格式
     * 
     * @return HH:mm
     */
    public static String getTimeCur() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
    /**
     * 获得时间戳日期时间格式
     * 
     * @return HH:mm
     */
    public static String getTime(String timeStr) {
    	long mill = 0;
    	try{
    	  mill = Long.parseLong(timeStr);
    	}catch(Exception e){
    		e.printStackTrace();
    		return "-1";
    	}
    	 Date date=new Date(mill*1000L);
         String strs="";
         try {
//         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         strs=sdf.format(date);
         } catch (Exception e) {
         e.printStackTrace();
         }
         return strs;
    	
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//        Date date = new Date();
//        return simpleDateFormat.format(date);
    }
    /**
     * 获得当前日期时间格式
     * 
     * @return yyyyMMddHHmmss
     */
    public static String getDateMyFormat() {
        String str1 = getDateFormat();
        String str2 = str1.split(" ")[0];// yyyy-MM-dd
        String str3 = str1.split(" ")[1];// HH:mm:ss
        String year = str2.split("-")[0];
        String month = str2.split("-")[1];
        String day = str2.split("-")[2];
        String hour = str3.split(":")[0];
        String min = str3.split(":")[1];
        String sec = str3.split(":")[2];
        return year + month + day + hour + min + sec;
    }
    
    /**
     * 获得输入时间日期时间格式
     * @param timeMillis  yyyy-MM-dd HH:mm:ss
     * @return yyyyMMddHHmmss
     */
    public static String getDateMyFormat(String timeMillis) {
        String str2 = timeMillis.split(" ")[0];// yyyy-MM-dd
        String str3 = timeMillis.split(" ")[1];// HH:mm:ss
        String year = str2.split("-")[0];
        String month = str2.split("-")[1];
        String day = str2.split("-")[2];
        String hour = str3.split(":")[0];
        String min = str3.split(":")[1];
        String sec = str3.split(":")[2];
        return year + month + day + hour + min + sec;
    }
    
    /**
     * 获得当前日期时间格式
     * @param date yyyy-M-d
     * @return yyyyMMdd
     */
    public static String getDateFormat(String date) {
                 
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        if(month.length() == 1){
            month = "0" + month;
        }
        if(day.length() == 1){
            day = "0" + day;
        }
        return year + month + day ;
    	
    }
    /**
     * 获得当前日期时间格式
     * @param date YYYYMMDD
     * @return YYYY-MM-DD
     */
    public static String getDateService(String date){
    	String year=date.substring(0, 4);
    	String month=date.substring(4, 6);
    	String day=date.substring(6,8);
    	return year+"-"+month+"-"+day;
    }
    
    /**
     * 获得当前日期时间格式
     * @param date YYYYMMDDHHMMSS 
     * @return YYYY-MM-DD HH:MM:SS
     */
    public static String getDateSpecific(String date){
    	if(TextUtils.isEmpty(date)||date.length()<14){
    		//返回当前时间,如果date位数不够的话
    		return getDateFormat();
    	}
    	String year=date.substring(0, 4);
    	String month=date.substring(4, 6);
    	String day=date.substring(6,8);
    	String hour = date.substring(8,10);
    	String min = date.substring(10,12);
    	String sec = date.substring(12,14);
    	return year+"-"+month+"-"+day+" "+hour+":"+min+":"+sec;
    }
    /**
     * 获得当前日期时间格式
     * @param date HH:mm
     * @return HHmm
     */
    public static String getTimeFormat(String time) {

        String hour = time.split(":")[0];
        String min = time.split(":")[1];
        if(hour.length() == 1){
            hour = "0" + hour;
        }
        if(min.length() == 1){
            min = "0" + min;
        }
        return hour + min ;
    }
    
    /**
     * 获得当前日期时间格式
     * @param time HHmm
     * @return HH:mm
     */
    public static String getTimeReturn(String time) {
        String hour = "00" ;
        String min = "00" ;
       if(time.length()==4){
           hour = time.substring(0, 2); 
           min = time.substring(2, 4);
       }
        return hour +":"+ min ;
    }
    
    /**
     * 
     * @param day 输入日期格式 yyyy-MM-dd
     * @leaveDay 差距的天数 -1 or +1
     * @return 前后的日期
     */
    public static String getLeaveDayFormat(String day,int leaveDay){
        // TODO 获得前一天的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");;
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
               calendar.add(Calendar.DATE, leaveDay);    //得到前一天
//                calendar.add(Calendar.MONTH, -1);    //得到前一个月
//               int year = calendar.get(Calendar.YEAR);
//               int month = calendar.get(Calendar.MONTH)+1; 
              date =  calendar.getTime();
              return simpleDateFormat.format(date);
    }
    
    /**
     * 
     * @param day 输入日期格式 yyyy-MM-dd
     * @return 前一天的日期
     */
    public static String getBeforeDayFormat(String day){
        // TODO 获得前一天的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");;
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
               calendar.add(Calendar.DATE, -1);    //得到前一天
//                calendar.add(Calendar.MONTH, -1);    //得到前一个月
//               int year = calendar.get(Calendar.YEAR);
//               int month = calendar.get(Calendar.MONTH)+1; 
              date =  calendar.getTime();
              return simpleDateFormat.format(date);
    }
    
    /**
     * 
     * @param day 输入日期格式 yyyy-MM-dd
     * @return
     */
    public static String getNextDayFormat(String day){
        // TODO 获得前一天的日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");;
        Date date = null;
        try {
            date = simpleDateFormat.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
               calendar.add(Calendar.DATE, +1);    //得到后一天
//                calendar.add(Calendar.MONTH, -1);    //得到前一个月
//               int year = calendar.get(Calendar.YEAR);
//               int month = calendar.get(Calendar.MONTH)+1; 
              date =  calendar.getTime();
              return simpleDateFormat.format(date);
    }
    
    /**
     *  将时间戳转换为时间
     * @param mill   System.currentTimeMillis()
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String convertForTimeMillis(long mill){
        Date date=new Date(mill*1000L);
        String strs="";
        try {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strs=sdf.format(date);
        } catch (Exception e) {
        e.printStackTrace();
        }
        return strs;
       
        }
    /**
     * 将时间戳直接转换为时间
    * @param mill   System.currentTimeMillis()
     * @return yyyyMMddHHmmss
     */
    public static String getDateTimeForTimeMillis(long mill){
        return getDateMyFormat(convertForTimeMillis(mill));
        
    }
    
    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMode(Context context,View windowToken) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    /**
     * 对比两个字符串哪里不一样
     * @param firstStr
     * @param otherStr
     */
    public static void strcmp(String firstStr,String otherStr){
    	if(TextUtils.isEmpty(firstStr) || TextUtils.isEmpty(otherStr)){
    		return;
    	}
    	int firstLen = firstStr.length();
    	int otherLen = otherStr.length();
    	Log.i(TAG,"firstStr="+firstStr);
    	Log.i(TAG,"otherStr="+otherStr);
    	Log.i(TAG,"firstLen : "+ firstLen + " , otherLen : "+otherLen);
    	if(firstStr.equals(otherStr)){
    		return;
    	}
    	int tempLen = 0;
    	if(firstLen >= otherLen){
    		tempLen = otherLen;
    	}
    	if(firstLen < otherLen){
    		tempLen = firstLen;
    	}
    	for(int i =0;i<tempLen;i++){
		char firstChar =	firstStr.charAt(i);
		char otherChar = otherStr.charAt(i);
		if(firstChar == otherChar){
			//一样的字符
			
		}else{
			//字符不一样时
			Log.i(TAG, "firstChar:"+firstChar + ", otherChar:"+otherChar);
		}
		}
    }
    
    
    /**
     * 获得输入日期的星期
     * @param inputDate	需要转换的日期 yyyy-MM-dd
     * @return 星期×
     */
    public static String getWeekDay(String inputDate){
//    	 String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
    	String weekStrArr1[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    	   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");;
           Date date = null;
           try {
               date = simpleDateFormat.parse(inputDate);
           } catch (ParseException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
    	 Calendar calendar = Calendar.getInstance();
    	 calendar.setTime(date);
    	int outWeek = calendar.get(Calendar.DAY_OF_WEEK);//返回的是1-7的整数，1为周日，2为周一，以此类推。
    	 return weekStrArr1[outWeek-1];
    }
    /*
    String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
    
    public String[] getWeekA(int y,int c, int m, int d){
            String[] weekArr = new String[7];
            for(int i = 0; i < weekArr.length; i++){
                    weekArr[i] = "";
            }
            for(int i = 0; i < weekArr.length; i++){
                    weekArr[i] = weekStrArr1[getWeekB(y, c, m, d + i)];
            }
            return weekArr;
    }
    /**
     * 根据日期获得星期
     * @param y 年 比如10年
     * @param c 世纪比如20世纪
     * @param m 月
     * @param d 日
     * @return
     */
    /*
    private int getWeekB(int y, int c, int m, int d) {
            if(m == 1){
                    m = 13;
                    y = y-1;
            }else if(m == 2){
                    m = 14;
                    y = y-1;
            }
            int tempDate = (y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7;
            if(tempDate < 0){
                    return 7+tempDate;
            }
            return tempDate;
    }

    */
    /**
     *  检测时间是否在某个时间段内
     * @param timeSlot 时间段  00：00--24：00
     * @param time  需要检测的时间 00：23
     * @return
     */
    public static boolean isInsideTime(String timeSlot,String time){
    	String startTime = timeSlot.split("--")[0];
    	String endTime = timeSlot.split("--")[1];
    	boolean isGreaterStart = isCompareTime(time,startTime);
    	boolean isLessEnd = isCompareTime(endTime,time );
    	if(isGreaterStart&&isLessEnd){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 比较两个时间的大小
     * @param time1 00：23
     * @param time2 00：25
     * @return time1大于等于time2 为 true,time1小于time2 为 false
     */
    public static boolean isCompareTime(String time1,String time2){
    	if(time1.equals("24:00")||time2.equals("00:00")||time1.equals("24：00")||time2.equals("00：00")){
    		return true;
    	}
    	if(time2.equals("24:00")||time1.equals("00:00")||time2.equals("24：00")||time1.equals("00：00")){
    		return false;
    	}
//    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	DateFormat df=new SimpleDateFormat("HH:mm");
    	Calendar c1=Calendar.getInstance();
    	Calendar c2=Calendar.getInstance();
    	try
    	{
    	c1.setTime(df.parse(time1));
    	c2.setTime(df.parse(time2));
    	}catch(java.text.ParseException e){
    	System.err.println("格式不正确");
    	}
    	int result=c1.compareTo(c2);

    	if(result<0){
    	return false;
    	}
    	else if(result>=0){
    	return true;
    	}
    	return true;
    }
    
    /**
     * 比较两个时间的大小
     * @param date1 2012-5-11
     * @param date2 2012-5-11
     * @return date1大于等于date2 为 true,date1小于date2 为 false
     */
    public static boolean isCompareDate(String date1,String date2){
//    	if(date1.equals("24:00")||date2.equals("00:00")||date1.equals("24：00")||date2.equals("00：00")){
//    		return true;
//    	}
//    	if(date2.equals("24:00")||date1.equals("00:00")||date2.equals("24：00")||date1.equals("00：00")){
//    		return false;
//    	}
//    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//    	DateFormat df=new SimpleDateFormat("HH:mm");
    	Calendar c1=Calendar.getInstance();
    	Calendar c2=Calendar.getInstance();
    	try
    	{
    	c1.setTime(df.parse(date1));
    	c2.setTime(df.parse(date2));
    	}catch(java.text.ParseException e){
    	System.err.println("格式不正确");
    	}
    	int result=c1.compareTo(c2);

    	if(result<0){
    	return false;
    	}
    	else if(result>=0){
    	return true;
    	}
    	return true;
    }
    
    /**
     * 设置需要高亮的字
     * @param wholeText 原始字符串
     * @param spanableText  需要高亮的字符串
     * @return  高亮后的字符串
     */
    public static SpannableString getSpanableText(String wholeText,
            String spanableText) {
        if (TextUtils.isEmpty(wholeText))
            wholeText = "";
        SpannableString spannableString = new SpannableString(wholeText);
        if (spanableText.equals(""))
            return spannableString;
        wholeText = wholeText.toLowerCase();
        spanableText = spanableText.toLowerCase();
        int startPos = wholeText.indexOf(spanableText);
        if (startPos == -1) {
            int tmpLength = spanableText.length();
            String tmpResult = "";
            for( int i=1; i<=tmpLength; i++ ){
                tmpResult = spanableText.substring(0,tmpLength-i);
                int tmpPos = wholeText.indexOf(tmpResult);
                if( tmpPos==-1 ) {
                    tmpResult = spanableText.substring(i,tmpLength);
                    tmpPos = wholeText.indexOf(tmpResult);
                }
                if( tmpPos!=-1 ) break;
                tmpResult = "";
            }
            if( tmpResult.length()!=0 ) {
                return getSpanableText(wholeText,tmpResult);
            } else {
                return spannableString;
            }
        }
        int endPos = startPos + spanableText.length();
        do {
            endPos = startPos + spanableText.length();
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW),
                    startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            startPos = wholeText.indexOf(spanableText, endPos);
        } while (startPos != -1);
        return spannableString;
    }
    
}

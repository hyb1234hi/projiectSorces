package com.iss.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {

	/**
      * 以最省内存的方式读取本地资源的图片
      * @see 
      * imageButton_fav.setImageResource(R.drawable.guide_fav_1) </br>修改为</br>
      * imageButton_fav.setImageBitmap(BitmapUtils.readBitMap(this, R.drawable.guide_fav_1));
      * @param context
      * @param resId
      * @return
      */  
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();  
        opt.inPreferredConfig = Bitmap.Config.RGB_565;   
        opt.inPurgeable = true;  
        opt.inInputShareable = true;  
           //获取资源图片  
        try{
        InputStream is = context.getResources().openRawResource(resId);  
        return BitmapFactory.decodeStream(is,null,opt);  
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }

    
}

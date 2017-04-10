package com.sinaleju.lifecircle.app.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {


    /**
     * md5???
     * @param str ???md5?????��??
     * @return ??????md5�??�?
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        /*
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        // 16�??�??�??9�??25�?
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
        */
        String md_key = byte2hex(byteArray);
        LogUtils.v("cxm", "md5=="+md_key);
        return md_key;
    }

//    public static void main(String[] args) {
//        
//        Md5Util my = new Md5Util();
//        my.testDigest();
//    }

//    public void testDigest() {
//        try {
//            String myinfo = "???�??信�?";
//
//             java.security.MessageDigest
//             alga=java.security.MessageDigest.getInstance("MD5");
////            MessageDigest alga = MessageDigest.getInstance("SHA-1");
//            alga.update(myinfo.getBytes());
//            byte[] digesta = alga.digest();
//            System.out.println("??��???�??:" + byte2hex(digesta));
//            // ???????��?�???��?人�???��??myinfo)???�?digesta) 对�???��?��?????��????�??�?
//            MessageDigest algb = MessageDigest.getInstance("SHA-1");
//            algb.update(myinfo.getBytes());
//            if (algb.isEqual(digesta, algb.digest())) {
//                System.out.println("信�?�??正常");
//            } else {
//                System.out.println("???�????);
//            }
//
//        } catch (java.security.NoSuchAlgorithmException ex) {
//            System.out.println("??????�??");
//        }
//
//    }

    /**
     *  �???�转�??�?
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
//        return hs.toUpperCase();
        return hs.toLowerCase();
    }

}

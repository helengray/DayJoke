package com.helen.dayjoke.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static String format(String str){
        try {
            if(!TextUtils.isEmpty(str)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss", Locale.CHINA);
                Date date = sdf.parse(str);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                return format.format(date);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    public static String formatTime(String str) {
        try {
            if(!TextUtils.isEmpty(str)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss", Locale.CHINA);
                Date date = sdf.parse(str);
                SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
                return format.format(date);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    public static String format(long t){
        Date date = new Date(t*1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(date);
    }
}

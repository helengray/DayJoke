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

    /**
     * 毫秒变成时间格式 小时:分:分钟
     * @param ms 毫秒数
     * @return 时间格式
     */
    public static String formatHHMMSS(int ms) {
        int second = ms / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String strTemp;
        if (0 != hh) {
            strTemp = String.format(Locale.CHINA,"%02d:%02d:%02d", hh, mm, ss);
        } else {
            strTemp = String.format(Locale.CHINA,"%02d:%02d", mm, ss);
        }
        return strTemp;
    }
}

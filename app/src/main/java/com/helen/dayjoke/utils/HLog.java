package com.helen.dayjoke.utils;

import android.util.Log;

import com.helen.dayjoke.BuildConfig;


/**
 * Created by Helen on 2016/4/6.
 *
 */
public class HLog {
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static void  d(String tag,String msg){
        if(DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void  d(String tag,String msg,Throwable throwable){
        if(DEBUG) {
            Log.d(tag, msg, throwable);
        }
    }

    public static void e(String tag,String msg){
        if(DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag,String msg,Throwable throwable){
        if(DEBUG) {
            Log.e(tag, msg, throwable);
        }
    }
}

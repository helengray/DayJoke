package com.helen.dayjoke.ui.application;

import com.facebook.common.util.ByteConstants;

import okhttp3.MediaType;

/**
 * Created by Helen on 2016/5/5.
 *
 */
public class Constant {
    /**网络缓存文件夹*/
    public static final String CACHE_HTTP = "network_cache";
    /**图片缓存文件夹*/
    public static final String CACHE_IMAGE = "image_cache";
    /**图片保存文件夹-系统文件夹Pictures下*/
    public static final String IMAGE_SAVE_PATH = "dayjoke";
    /**崩溃日志文件夹*/
    public static final String CACHE_LOG = "crash";
    /**崩溃文件名称*/
    public static final String LOG_FILE_NAME = "AppCrash.log";
    /**apk文件夹*/
    public static final String DOWNLOAD_APK_FILE = "dayjoke/apk";
    /**apk文件*/
    public static final String DOWNLOAD_APK_NAME = "dayjoke.apk";
    /**日志文件最大大小*/
    public static final int LOG_MAX_SIZE = ByteConstants.MB;//1M

    public static final long MAX_CACHE_SIZE = 100 * ByteConstants.MB;
    public static final long LOW_CACHE_SIZE = 50 * ByteConstants.MB;
    public static final long VERY_LOW_CACHE_SIZE = 10 * ByteConstants.MB;
    public static final int REQ_CODE_CONS_CHANGE = 1000;
    /**保存星座索引的key*/
    public static final String KEY_CONSTELLATION_INDEX = "KEY_CONSTELLATION_INDEX";
    /**RequestBody 文本格式*/
    public static final MediaType TEXT = MediaType.parse("text/plain");
    /**RequestBody Json格式*/
    public static final MediaType JSON = MediaType.parse("application/json");
    /**文本笑话详情广告*/
    public static final String AD_ID_TEXT_DETAIL = "2529153";
    /**图片笑话详情广告*/
    public static final String AD_ID_PIC_DETAIL = "2529625";

    public static final int PAGE_SIZE = 15;
}

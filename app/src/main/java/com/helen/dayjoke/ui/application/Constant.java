package com.helen.dayjoke.ui.application;

import com.facebook.common.util.ByteConstants;

/**
 * Created by 李晓伟 on 2016/5/5.
 *
 */
public class Constant {
    /**网络缓存文件夹*/
    public static final String CACHE_HTTP = "network_cache";
    /**图片缓存文件夹*/
    public static final String CACHE_IMAGE = "image_cache";
    /**图片保存文件夹-系统文件夹Pictures下*/
    public static final String IMAGE_SAVE_PATH = "dayjoke";
    public static final long MAX_CACHE_SIZE = 100 * ByteConstants.MB;
    public static final long LOW_CACHE_SIZE = 50 * ByteConstants.MB;
    public static final long VERY_LOW_CACHE_SIZE = 10 * ByteConstants.MB;
    public static final int REQ_CODE_CONS_CHANGE = 1000;
    /**保存星座索引的key*/
    public static final String KEY_CONSTELLATION_INDEX = "KEY_CONSTELLATION_INDEX";
}

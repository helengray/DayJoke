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
    public static final String APP_ID="1105324471";

    public static final int PAGE_SIZE = 15;

    /**友盟事件ID*/
    public class Event{
        /**文本笑话tab*/
        public static final String EVENT_ID_TAB_TEXT = "10000";
        /**文本笑话详情*/
        public static final String EVENT_ID_TAB_TEXT_DETAIL = "10000_1";
        /**视频tab*/
        public static final String EVENT_ID_TAB_VIDEO = "20000";
        /**视频播放详情*/
        public static final String EVENT_ID_TAB_VIDEO_DETAIL = "20000_1";
        /**趣图tab*/
        public static final String EVENT_ID_TAB_PIC = "30000";
        /**趣图查看详情*/
        public static final String EVENT_ID_TAB_PIC_DETAIL = "30000_1";
        /**糗图tab*/
        public static final String EVENT_ID_TAB_QIU = "40000";
        /**糗图详情*/
        public static final String EVENT_ID_TAB_QIU_DETAIL = "40000_1";
        /**福利tab*/
        public static final String EVENT_ID_TAB_WELFARE = "50000";
        /**福利-随机*/
        public static final String EVENT_ID_TAB_WELFARE_SUIJI = "50001";
        /**福利-随机详情*/
        public static final String EVENT_ID_TAB_WELFARE_SUIJI_DETAIL = "50001_1";
        /**福利-大胸妹*/
        public static final String EVENT_ID_TAB_WELFARE_DAXIONG = "50002";
        /**福利-大胸妹详情*/
        public static final String EVENT_ID_TAB_WELFARE_DAXIONG_DETAIL = "50002_1";
        /**福利-小清新*/
        public static final String EVENT_ID_TAB_WELFARE_QINGXIN = "50003";
        /**福利-小清新详情*/
        public static final String EVENT_ID_TAB_WELFARE_QINGXIN_DETAIL = "50003_1";
        /**福利-文艺*/
        public static final String EVENT_ID_TAB_WELFARE_WENYI = "50004";
        /**福利-文艺详情*/
        public static final String EVENT_ID_TAB_WELFARE_WENYI_DETAIL = "50004_1";
        /**福利-性感*/
        public static final String EVENT_ID_TAB_WELFARE_XINGGAN = "50005";
        /**福利-性感详情*/
        public static final String EVENT_ID_TAB_WELFARE_XINGGAN_DETAIL = "50005_1";
        /**福利-大长腿*/
        public static final String EVENT_ID_TAB_WELFARE_DACHUANGTUI = "50006";
        /**福利-大长腿详情*/
        public static final String EVENT_ID_TAB_WELFARE_DACHUANGTUI_DETAIL = "50006_1";
        /**福利-黑丝*/
        public static final String EVENT_ID_TAB_WELFARE_HEISI = "50007";
        /**福利-黑丝详情*/
        public static final String EVENT_ID_TAB_WELFARE_HEISI_DETAIL = "50007_1";
        /**福利-翘臀*/
        public static final String EVENT_ID_TAB_WELFARE_QIAOTUN = "50008";
        /**福利-翘臀详情*/
        public static final String EVENT_ID_TAB_WELFARE_QIAOTUN_DETAIL = "50008_1";
        /**图片查看汇总*/
        public static final String EVENT_ID_GALLERY = "00000";
        /**广告展示*/
        public static final String EVENT_ID_AD_SHOW= "60000";
    }
}

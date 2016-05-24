package com.helen.dayjoke.entity;

import com.helen.dayjoke.utils.TimeUtil;

/**
 * Created by Helen on 2016/5/24.
 *
 */
public class Mito extends BaseEn{
    public static final int TYPE_DA_MEI = 34;//大妹
    public static final int TYPE_XIAO_QING_XIN = 35;//小清新
    public static final int TYPE_WEN_YI = 36;//文艺
    public static final int TYPE_XING_GAN = 37;//性感
    public static final int TYPE_DA_CHANGG_TUI = 38;//大长腿
    public static final int TYPE_HEI_SI = 39;//黑丝
    public static final int TYPE_XIAO_QIAO_TUN = 40;//小翘臀

    private String id;
    private String title;
    private String thumb;
    private long createtime;
    private transient String timeFormat;

    public String getTimeFormat() {
        if(timeFormat == null){
            timeFormat = TimeUtil.format(createtime);
        }
        return timeFormat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        if(thumb == null){
            return "";
        }
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}

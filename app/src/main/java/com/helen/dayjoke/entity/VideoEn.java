package com.helen.dayjoke.entity;

/**
 * Created by Helen on 2016/7/27.
 *
 */
public class VideoEn extends QiuTuEn{
    private String high_url;
    private String low_url;
    private String pic_url;

    public String getHigh_url() {
        return high_url;
    }

    public void setHigh_url(String high_url) {
        this.high_url = high_url;
    }

    public String getLow_url() {
        return low_url;
    }

    public void setLow_url(String low_url) {
        this.low_url = low_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}

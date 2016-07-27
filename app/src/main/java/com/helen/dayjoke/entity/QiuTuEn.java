package com.helen.dayjoke.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Helen on 2016/7/27.
 * 糗图
 */
public class QiuTuEn extends BaseEn{
    private String id;
    private String image;
    private String content;
    private long published_at;
    private String time;
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPublished_at() {
        return published_at*1000;
    }

    public void setPublished_at(long published_at) {
        this.published_at = published_at;
    }

    public String getTime() {
        if(time == null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.CANADA);
            time = dateFormat.format(new Date(getPublished_at()));
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        if(pic == null){
            StringBuilder builder = new StringBuilder("http://pic.qiushibaike.com/system/pictures/");
            int length = id.length();
            builder.append(id.substring(0,length-4)).append("/").append(id).append("/medium/").append(image);
            pic = builder.toString();
        }
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}

package com.helen.dayjoke.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 李晓伟 on 2016/4/27.
 *
 */
public class JokeEn extends BaseEn{
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_PIC = 2;
    private String id;
    @SerializedName("ct")
    private String time;
    @SerializedName("text")
    private String content;
    private String img;
    private String title;
    private int type;//1:文本笑话 2:图片笑话

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        if(!TextUtils.isEmpty(time)){
            return time.split("\\.")[0];
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        if(img == null){
            return "";
        }
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

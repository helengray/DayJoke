package com.helen.dayjoke.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.helen.dayjoke.utils.TimeUtil;

/**
 * 笑话实体类
 */
public class JokeEn extends BaseEn implements Parcelable {
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

    private transient String timeFormat;

    public String getTimeFormat() {
        if(timeFormat == null){
            timeFormat = TimeUtil.formatTime(time);
        }
        return timeFormat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return TimeUtil.format(time);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.time);
        dest.writeString(this.content);
        dest.writeString(this.img);
        dest.writeString(this.title);
        dest.writeInt(this.type);
    }

    public JokeEn() {
    }

    protected JokeEn(Parcel in) {
        this.id = in.readString();
        this.time = in.readString();
        this.content = in.readString();
        this.img = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<JokeEn> CREATOR = new Parcelable.Creator<JokeEn>() {
        @Override
        public JokeEn createFromParcel(Parcel source) {
            return new JokeEn(source);
        }

        @Override
        public JokeEn[] newArray(int size) {
            return new JokeEn[size];
        }
    };
}

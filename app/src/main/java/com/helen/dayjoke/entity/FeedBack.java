package com.helen.dayjoke.entity;


import com.google.gson.Gson;

/**
 * Created by 李晓伟 on 2016/5/5.
 * 用户反馈
 */
public class FeedBack extends BaseEn{
    private String content;
    private String contact;
    private BmobFile fileLog;

    public BmobFile getFileLog() {
        return fileLog;
    }

    public void setFileLog(BmobFile fileLog) {
        this.fileLog = fileLog;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

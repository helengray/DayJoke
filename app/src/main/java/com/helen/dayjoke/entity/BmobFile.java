package com.helen.dayjoke.entity;

import com.google.gson.Gson;

/**
 * Created by 李晓伟 on 2016/5/6.
 *
 */
public class BmobFile extends BaseEn{
    private String filename = null;
    private String group = null;
    private String url = null;
    private String __type = "File";

    public BmobFile(String filename, String group, String url) {
        this.filename = filename;
        this.group = group;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

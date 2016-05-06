package com.helen.dayjoke.entity;

/**
 * Created by 李晓伟 on 2016/5/6.
 * Bmob 文件上传返回body内容
 */
public class FileBodyEn extends BaseEn{
    private String filename;
    private String url;
    private String cdn;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    @Override
    public String toString() {
        return "FileBodyEn{" +
                "filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", cdn='" + cdn + '\'' +
                '}';
    }
}

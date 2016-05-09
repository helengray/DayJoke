package com.helen.dayjoke.entity;

/**
 * Created by 李晓伟 on 2016/5/6.
 *
 */
public class VersionInfo extends BaseEn{
    private String objectId;
    private String version;
    private String updateContent;
    private BmobFile apkFile;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateContent() {
        if(updateContent == null){
            return "";
        }
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public BmobFile getApkFile() {
        return apkFile;
    }

    public void setApkFile(BmobFile apkFile) {
        this.apkFile = apkFile;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "objectId='" + objectId + '\'' +
                ", version='" + version + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", apkFile=" + apkFile +
                '}';
    }
}

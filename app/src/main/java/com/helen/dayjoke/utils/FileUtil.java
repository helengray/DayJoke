package com.helen.dayjoke.utils;

import java.io.File;

/**
 * Created by 李晓伟 on 2016/5/5.
 *
 */
public class FileUtil {
    /**
     * 统计文件夹大小
     */
    public static long getFileSize(File dir){
        long size = 0;
        if(dir != null && dir.exists()) {
            File files[] = dir.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    size = size + getFileSize(f);
                } else {
                    size = size + f.length();
                }
            }
        }
        return size;
    }

    /**
     * 删除dir目录下的所有文件
     */
    public static void deleteDir(File dir){
        if (dir==null||!dir.exists()) {
            return;
        } else {
            if (dir.isFile()) {
                dir.delete();
                return;
            }
        }
        if (dir.isDirectory()) {
            File[] childFile = dir.listFiles();
            if (childFile == null || childFile.length == 0) {
                dir.delete();
                return;
            }
            for (File f : childFile) {
                deleteDir(f);
            }
            dir.delete();
        }
    }
}

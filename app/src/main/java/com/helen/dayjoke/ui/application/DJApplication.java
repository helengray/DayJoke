package com.helen.dayjoke.ui.application;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.utils.EnvironmentUtil;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by Helen on 2016/4/28.
 *
 */
public class DJApplication extends Application{
    private static DJApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        String processName = EnvironmentUtil.getProcessName(this,android.os.Process.myPid());
        if(getPackageName().equals(processName)){
            init();
        }
    }

    private void init(){
        instance = this;
        HCrashHandler.init(this);
        initFresco();
        initUmeng();
    }

    private void initUmeng() {
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        //MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.setCheckDevice(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }


    private void initFresco(){
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(EnvironmentUtil.getCacheFile())
                .setBaseDirectoryName(Constant.CACHE_IMAGE)
                .setMaxCacheSize(Constant.MAX_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(Constant.LOW_CACHE_SIZE)
                .setMaxCacheSizeOnVeryLowDiskSpace(Constant.VERY_LOW_CACHE_SIZE)
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(this, config);
        //清理fresco缓存
        //Fresco.getImagePipeline().clearCaches();
    }

    public static DJApplication getInstance() {
        return instance;
    }
}

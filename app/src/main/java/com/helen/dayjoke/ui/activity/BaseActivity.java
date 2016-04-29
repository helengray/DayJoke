package com.helen.dayjoke.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.common.logging.FLog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李晓伟 on 2016/4/27.
 *
 */
public class BaseActivity extends AppCompatActivity {

    private static Map<String,WeakReference<BaseActivity>> activitiesMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitiesMap.put(BaseActivity.this.toString(), new WeakReference<BaseActivity>(this));
    }

    @Override
    protected void onDestroy() {
        activitiesMap.remove(BaseActivity.this.toString());
        super.onDestroy();
    }

    /**
     * 关闭所有activity
     */
    public static void finishAll(){
        try {
            if(activitiesMap != null){
                for (Map.Entry<String,WeakReference<BaseActivity>> entry:activitiesMap.entrySet()){
                    WeakReference<BaseActivity> weakReferenceAct = entry.getValue();
                    if(weakReferenceAct!=null && weakReferenceAct.get()!=null){
                        BaseActivity activity = weakReferenceAct.get();
                        if(!activity.isFinishing()){
                            activity.finish();
                        }
                        weakReferenceAct.clear();
                    }
                }
                activitiesMap.clear();
            }
        }catch (Exception e){
            FLog.e("finishAll", e, null);
        }
    }
}

package com.helen.dayjoke.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.common.logging.FLog;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class BaseActivity extends AppCompatActivity {

    private static Map<String,WeakReference<BaseActivity>> activitiesMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitiesMap.put(BaseActivity.this.toString(), new WeakReference<BaseActivity>(this));
        /*Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        int color = getResources().getColor(R.color.colorAccent);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            window.setStatusBarColor(color);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(this));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }*/

    }

    /*public static int getStatusBarHeight(Context context){
        return context.getResources().getDimensionPixelSize(R.dimen.actionBarSize);
    }*/

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

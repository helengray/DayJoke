package com.helen.dayjoke.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobads.AdView;
import com.helen.dayjoke.R;
import com.helen.dayjoke.utils.HLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Helen on 2016/8/30.
 *
 */
public class HoleActivity extends TitlebarActivity implements View.OnClickListener{
    LinearLayout mAdLayout;
    Button mBtnShow;
    EditText mEditTime;
    EditText mEditNumber;
    TextView mTextTotal;


    Handler mHandler;
    RefreshRunnable mRefreshRunnable;
    int mAdCount = 8;
    int mRefreshTime = 1;
    SharedPreferences mSharedPreferences;

    int showTotal = 0;
    String currKey="";

    SparseArray<View> mViewCaches = new SparseArray<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole);
        mAdLayout = (LinearLayout) findViewById(R.id.layout_ad);
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnShow.setOnClickListener(this);
        mEditTime = (EditText) findViewById(R.id.et_refresh_time);
        mEditNumber = (EditText) findViewById(R.id.et_ad_number);
        mTextTotal = (TextView) findViewById(R.id.txt_total);



        mSharedPreferences = getSharedPreferences("ad_db",MODE_PRIVATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currTime = format.format(new Date());
        HLog.d("HoleActivity",currTime);
        currKey = currTime+"_total";
        showTotal = mSharedPreferences.getInt(currKey,0);
        showAD(mAdCount);

        mHandler = new Handler();
        mRefreshRunnable = new RefreshRunnable();
    }

    private void showAD(int num){
        showTotal = showTotal + num;
        mSharedPreferences.edit().putInt(currKey,showTotal).apply();
        mTextTotal.setText(String.valueOf(showTotal));
        String[] adPlaceIds = new String[]{"2758122","2758123","2758124"};
        mViewCaches.clear();
        for (int i=0;i<num;i++){
            String adPlaceId = adPlaceIds[i%3];
            AdView adView = new AdView(this,adPlaceId);
            mViewCaches.put(i,adView);
            mAdLayout.addView(adView);
        }
    }

    /**
     * 模拟点击屏幕
     */
    private void dispatchTouchEvent(){
       /* try {
            Random random = new Random();
            int i = random.nextInt(mAdCount);
            View view = mViewCaches.get(i);
            view.performClick();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        Rect rect = new Rect();
        mAdLayout.getGlobalVisibleRect(rect);
        HLog.d("HoleActivity","location:"+rect.toString());
        Point top = new Point(rect.left,rect.top);
        Point bottom = new Point(rect.right,rect.bottom);
        Random random = new Random();
        int x = random.nextInt(bottom.x)-top.x;
        int y = random.nextInt(bottom.y)-top.y;
        HLog.d("HoleActivity","location:x="+x+",y="+y);
        long time = System.currentTimeMillis();
        MotionEvent downEvent = MotionEvent.obtain(time,time,MotionEvent.ACTION_DOWN,x,y,0);
        time +=1000;
        MotionEvent upEvent = MotionEvent.obtain(time,time,MotionEvent.ACTION_UP,x,y,0);
        mAdLayout.onTouchEvent(downEvent);
        mAdLayout.onTouchEvent(upEvent);
    }

    private void stopRunnable(){
        if(mHandler != null && mRefreshRunnable != null){
            mHandler.removeCallbacks(mRefreshRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        mAdLayout.removeAllViews();
        stopRunnable();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        dispatchTouchEvent();
        String time = mEditTime.getText().toString();
        if(!TextUtils.isEmpty(time)){
            mRefreshTime = Integer.valueOf(time);
        }

        String count = mEditNumber.getText().toString();
        if(!TextUtils.isEmpty(count)){
            mAdCount = Integer.valueOf(count);
        }
        mHandler.removeCallbacks(mRefreshRunnable);
        mHandler.postDelayed(mRefreshRunnable, mRefreshTime*60* 1000);
    }

    @Override
    protected void onPause() {
        stopRunnable();
        super.onPause();
    }

    class RefreshRunnable implements Runnable{

        @Override
        public void run() {
            if(!isDestroyed()) {
                mAdLayout.removeAllViews();
                showAD(mAdCount);
                if(mRefreshTime != 0) {
                    mHandler.postDelayed(this, mRefreshTime * 60 * 1000);
                }
            }
        }
    }
}

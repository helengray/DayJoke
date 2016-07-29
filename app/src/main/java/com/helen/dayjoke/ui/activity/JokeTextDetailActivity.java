package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.JokeEn;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.HLog;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerADListener;
import com.qq.e.ads.banner.BannerView;

/**
 * Created by Helen on 2016/5/11.
 *
 */
public class JokeTextDetailActivity extends TitlebarActivity{
    public static final String TAG = "ADView";
    public static final String KEY_DATA = "data";
    private JokeEn mJokeEn;

    public static void launcher(Context context, JokeEn jokeEn){
        Intent intent = new Intent(context,JokeTextDetailActivity.class);
        intent.putExtra(KEY_DATA, (Parcelable) jokeEn);
        context.startActivity(intent);
    }

    private TextView mTextViewTitle;
    private TextView mTextViewContent;
    private TextView mTextViewTime;
    private RelativeLayout mAdLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_text_detail);
        mJokeEn = getIntent().getParcelableExtra(KEY_DATA);
        mTextViewTitle = (TextView) findViewById(R.id.tv_title);
        mTextViewContent = (TextView) findViewById(R.id.tv_content);
        mTextViewTime = (TextView) findViewById(R.id.tv_time);
        mAdLayout = (RelativeLayout) findViewById(R.id.layout_ad);
        initData();
        initAD();
    }
    private BannerView mAdView;
    private void initAD(){
        mAdView = new BannerView(this, ADSize.BANNER, Constant.APP_ID, "8020516397617412");
        mAdView.setRefresh(5);
        mAdView.setADListener(new BannerADListener() {
            @Override
            public void onNoAD(int i) {
                HLog.d(TAG,"onNoAD code="+i);
            }

            @Override
            public void onADReceiv() {
                mAdLayout.setVisibility(View.VISIBLE);
                HLog.d(TAG,"onADReceive");
            }

            @Override
            public void onADExposure() {
                HLog.d(TAG,"onADExposure");
            }

            @Override
            public void onADClosed() {
                HLog.d(TAG,"onADClosed");
            }

            @Override
            public void onADClicked() {
                HLog.d(TAG,"onADClicked");
            }

            @Override
            public void onADLeftApplication() {
                HLog.d(TAG,"onADLeftApplication");
            }

            @Override
            public void onADOpenOverlay() {
                HLog.d(TAG,"onADOpenOverlay");
            }

            @Override
            public void onADCloseOverlay() {
                HLog.d(TAG,"onADCloseOverlay");
            }
        });
        mAdView.loadAD();
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        mAdLayout.addView(mAdView, rllp);
    }


    @Override
    protected void onDestroy() {
        mAdLayout.removeAllViews();
        mAdView.destroy();
        //adView.destroy();
        super.onDestroy();
    }

    private void initData() {
        mTextViewTitle.setText(mJokeEn.getTitle());
        mTextViewContent.setText(Html.fromHtml(mJokeEn.getContent()));
        mTextViewTime.setText("————"+mJokeEn.getTime());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_DATA,mJokeEn);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mJokeEn = savedInstanceState.getParcelable(KEY_DATA);
    }
}

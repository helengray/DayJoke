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
import com.helen.dayjoke.ui.application.DJApplication;
import com.helen.dayjoke.utils.HLog;

import th.ds.wa.normal.banner.AdViewListener;
import th.ds.wa.normal.banner.BannerManager;

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

    private void initAD(){
        View adView = BannerManager.getInstance(DJApplication.getInstance()).getBanner(this);
        if(adView != null) {
            BannerManager.getInstance(DJApplication.getInstance()).setAdListener(new AdViewListener() {
                @Override
                public void onReceivedAd() {
                    HLog.d(TAG,"onReceivedAd");
                }

                @Override
                public void onSwitchedAd() {
                    HLog.d(TAG,"onSwitchedAd");
                }

                @Override
                public void onFailedToReceivedAd() {
                    HLog.d(TAG,"onFailedToReceivedAd");
                }
            });
            RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            mAdLayout.addView(adView, rllp);
        }
    }


    @Override
    protected void onDestroy() {
        mAdLayout.removeAllViews();
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

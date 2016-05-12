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

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.JokeEn;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.HLog;

import org.json.JSONObject;

/**
 * Created by Helen on 2016/5/11.
 *
 */
public class JokeTextDetailActivity extends TitlebarActivity{
    public static final String TAG = "ADView";
    public static final String KEY_DATA = "data";
    private JokeEn mJokeEn;
    private AdView adView;
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
        adView = new AdView(this, Constant.AD_ID_TEXT_DETAIL);
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
                HLog.d(TAG, "onAdSwitch");
            }

            public void onAdShow(JSONObject info) {
                // 广告已经渲染出来
                HLog.d(TAG, "onAdShow " + info.toString());
                mAdLayout.setVisibility(View.VISIBLE);
            }

            public void onAdReady(AdView adView) {
                // 资源已经缓存完毕，还没有渲染出来
                HLog.d(TAG, "onAdReady " + adView);
            }

            public void onAdFailed(String reason) {
                HLog.d(TAG, "onAdFailed " + reason);
                mAdLayout.setVisibility(View.GONE);
            }

            public void onAdClick(JSONObject info) {
                HLog.d(TAG, "onAdClick " + info.toString());
            }
        });
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        mAdLayout.addView(adView,rllp);
    }


    @Override
    protected void onDestroy() {
        mAdLayout.removeAllViews();
        adView.destroy();
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

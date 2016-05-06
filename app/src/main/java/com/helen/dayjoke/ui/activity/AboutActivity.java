package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.VersionInfo;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 李晓伟 on 2016/5/6.
 * 关于
 */
public class AboutActivity extends TitlebarActivity implements View.OnClickListener{

    public static void launcher(Context context){
        context.startActivity(new Intent(context,AboutActivity.class));
    }

    private TextView mNewVersion;
    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);
        findViewById(R.id.layout_check_update).setOnClickListener(this);
        findViewById(R.id.layout_open_source).setOnClickListener(this);
        findViewById(R.id.layout_share).setOnClickListener(this);
        TextView currentVersion = (TextView) findViewById(R.id.text_current_version);
        currentVersion.setText(getString(R.string.v, BuildConfig.VERSION_NAME));
        mNewVersion = (TextView) findViewById(R.id.text_new_version);
        mAPIService = APIManager.getInstance().getAPIService();
        checkUpdate();
    }

    private void checkUpdate(){
        mAPIService.getVersionInfo("bql=select * from VersionInfo limit 1 order by createAt")
                .map(new Func1<ArrayList<VersionInfo>, String>() {
                    @Override
                    public String call(ArrayList<VersionInfo> versionInfos) {
                        return versionInfos.get(0).getVersion();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(Log.getStackTraceString(e));
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }
                });




        String newVersion = "2.0.0";
        String str = getString(R.string.new_version)+getNewVersionHtml(getString(R.string.v, newVersion));
        mNewVersion.setText(Html.fromHtml(str));
    }

    private String getNewVersionHtml(String newVersion){
        return "<font color='#ff3d00'>" +newVersion+ "</font>";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_check_update:

                break;
            case R.id.layout_open_source:

                break;
            case R.id.layout_share:

                break;
        }
    }
}

package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.VersionInfo;
import com.helen.dayjoke.entity.constellation.VersionInfoResponseEn;
import com.helen.dayjoke.ui.service.DownloadService;
import com.helen.dayjoke.ui.view.MaterialDialog;

import java.util.List;

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
    private VersionInfo mVersionInfo;
    private boolean hasNew = false;
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
        mAPIService.getVersionInfo(1,"-version")
                .map(new Func1<VersionInfoResponseEn, List<VersionInfo>>() {
                    @Override
                    public List<VersionInfo> call(VersionInfoResponseEn responseEn) {
                        return responseEn.results;
                    }
                })
                .map(new Func1<List<VersionInfo>, String>() {
                    @Override
                    public String call(List<VersionInfo> versionInfos) {
                        if(versionInfos != null && !versionInfos.isEmpty()){
                            mVersionInfo = versionInfos.get(0);
                            System.out.println(mVersionInfo);
                            return mVersionInfo.getVersion();
                        }
                        return BuildConfig.VERSION_NAME;
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
                        hasNew = false;
                        mNewVersion.setVisibility(View.VISIBLE);
                        mNewVersion.setText(R.string.no_new_version);
                    }

                    @Override
                    public void onNext(String newVersion) {
                        String version = BuildConfig.VERSION_NAME;
                        if(newVersion.compareTo(version) > 0){
                            hasNew = true;
                            String str = getString(R.string.new_version)+getNewVersionHtml(getString(R.string.v, newVersion));
                            mNewVersion.setText(Html.fromHtml(str));
                        }else {
                            hasNew = false;
                            mNewVersion.setText(R.string.no_new_version);
                        }
                        mNewVersion.setVisibility(View.VISIBLE);
                    }
                });

    }

    private String getNewVersionHtml(String newVersion){
        return "<font color='#ff3d00'>" +newVersion+ "</font>";
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_check_update:
                final MaterialDialog materialDialog = new MaterialDialog(this);
                materialDialog.setTitle(getString(R.string.alert));
                materialDialog.setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(hasNewVersion()) {
                            Intent intent = new Intent(AboutActivity.this, DownloadService.class);
                            intent.putExtra(DownloadService.KEY_URL,mVersionInfo.getApkFile().getUrl());
                            startService(intent);
                        }
                        materialDialog.dismiss();
                    }
                });
                materialDialog.setNegativeButton(getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                });

                if(hasNewVersion()){
                    materialDialog.setMessage(Html.fromHtml(mVersionInfo.getUpdateContent()));
                }else {
                    materialDialog.setMessage(getString(R.string.no_new_version));
                }
                materialDialog.show();
                break;
            case R.id.layout_open_source:

                break;
            case R.id.layout_share:

                break;
        }
    }

    private boolean hasNewVersion(){
        return mVersionInfo != null && hasNew;
    }
}

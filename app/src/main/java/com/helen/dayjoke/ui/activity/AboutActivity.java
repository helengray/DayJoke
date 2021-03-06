package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.VersionInfo;
import com.helen.dayjoke.entity.constellation.BombResponseEn;
import com.helen.dayjoke.ui.service.DownloadService;
import com.helen.dayjoke.ui.view.MaterialDialog;
import com.helen.dayjoke.utils.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Helen on 2016/5/6.
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
        findViewById(R.id.iv_zhifubao).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveDrawable();
                ToastUtil.showToast(AboutActivity.this,getString(R.string.pic_save));
                return true;
            }
        });
        TextView currentVersion = (TextView) findViewById(R.id.text_current_version);
        currentVersion.setText(getString(R.string.v, BuildConfig.VERSION_NAME));
        mNewVersion = (TextView) findViewById(R.id.text_new_version);
        mAPIService = APIManager.getInstance().getAPIService();
        checkUpdate();
    }

    private void saveDrawable(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_zhifubao_white);
        MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,getString(R.string.zhi_fu_bao),getString(R.string.zhi_fu_bao));
    }

    private void checkUpdate(){
        mAPIService.getVersionInfo(1,"-version")
                .map(new Func1<BombResponseEn<VersionInfo>, List<VersionInfo>>() {
                    @Override
                    public List<VersionInfo> call(BombResponseEn<VersionInfo> responseEn) {
                        return responseEn.results;
                    }
                })
                .map(new Func1<List<VersionInfo>, String>() {
                    @Override
                    public String call(List<VersionInfo> versionInfos) {
                        if(versionInfos != null && !versionInfos.isEmpty()){
                            mVersionInfo = versionInfos.get(0);
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

                if(hasNewVersion()){
                    materialDialog.setPositiveButton(getString(R.string.update), new View.OnClickListener() {
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
                    materialDialog.setMessage(Html.fromHtml(mVersionInfo.getUpdateContent()));
                }else {
                    materialDialog.setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialDialog.dismiss();
                        }
                    });
                    materialDialog.setMessage(getString(R.string.no_new_version));
                }
                materialDialog.show();
                break;
            case R.id.layout_open_source:
                OpenSourceActivity.launcher(this);
                break;
            case R.id.layout_share:
                String message = getString(R.string.share_tip);
                /*if(mVersionInfo != null){
                    message += mVersionInfo.getApkFile().getUrl();
                }*/
                shareIntent(this,getString(R.string.app_name),message);
                break;
        }
    }

    /**
     * 分享Intent
     */
    public static void shareIntent(Context context, String titleString,
                                   String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, titleString);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.more_share)));
        } catch (Exception e) {
            ToastUtil.showToast(context, context.getString(R.string.not_find_share_soft));
        }

    }

    private boolean hasNewVersion(){
        return mVersionInfo != null && hasNew;
    }
}

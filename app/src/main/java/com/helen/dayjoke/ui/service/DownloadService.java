package com.helen.dayjoke.ui.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.helen.dayjoke.R;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.EnvironmentUtil;

import java.io.File;

/**
 * Created by 李晓伟 on 2016/5/9.
 *
 */
public class DownloadService extends Service{
    public static final String KEY_URL = "url";
    private DownloadManager dm;
    private long enqueue;
    private BroadcastReceiver receiver;
    private String url;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(receiver == null) {
            url = intent.getStringExtra(KEY_URL);
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if(enqueue == id){
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(new File(EnvironmentUtil.getDownloadFile(),Constant.DOWNLOAD_APK_NAME)),
                                "application/vnd.android.package-archive");
                        startActivity(intent);
                        stopSelf();
                    }
                }
            };
            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            startDownload();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload() {
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(url));
        request.setTitle(getString(R.string.app_name));
        request.setDescription(getString(R.string.new_version_download));
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Constant.DOWNLOAD_APK_NAME);
        enqueue = dm.enqueue(request);
    }
}

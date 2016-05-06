package com.helen.dayjoke.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.common.util.ByteConstants;
import com.helen.dayjoke.R;
import com.helen.dayjoke.entity.constellation.Constellation;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.EnvironmentUtil;
import com.helen.dayjoke.utils.FileUtil;
import com.helen.dayjoke.utils.SPUtil;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.text.DecimalFormat;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SettingActivity extends TitlebarActivity implements View.OnClickListener{

    public static void launcher(BaseActivity context,int requestCode){
        context.startActivityForResult(new Intent(context,SettingActivity.class),requestCode);
    }

    private TextView mBtnClear;
    private MaterialSpinner mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.setting);
        initView();
    }

    private void initView() {
        mSpinner = (MaterialSpinner) findViewById(R.id.spinner);
        int length = Constellation.CONSTELLATIONS.length;
        String items[] = new String[length];
        for (int i = 0;i< length;i++){
            items[i] = Constellation.CONSTELLATIONS[i].getName() + "("+Constellation.CONSTELLATIONS[i].getDate()+")";
        }
        mSpinner.setItems(items);
        int index = SPUtil.getInstance().getInt(Constant.KEY_CONSTELLATION_INDEX, 0);
        mSpinner.setSelectedIndex(index);
        mBtnClear = (TextView) findViewById(R.id.btn_clear_cache);
        mBtnClear.setOnClickListener(this);
        calculateFileSize();
    }

    @Override
    public void onBackPressed() {
        setResultBack();
    }

    private void setResultBack(){
        int index = mSpinner.getSelectedIndex();
        SPUtil.getInstance().putInt(Constant.KEY_CONSTELLATION_INDEX,index).commit();
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 计算缓存大小
     */
    private void calculateFileSize(){
        Observable.just(EnvironmentUtil.getCacheFile())
                .map(new Func1<File, Long>() {
                    @Override
                    public Long call(File file) {
                        return FileUtil.getFileSize(file);
                    }
                })
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long size) {
                        DecimalFormat df = new DecimalFormat("###0.00");
                        double s = size/ ByteConstants.MB;
                        return df.format(s);
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
                        mBtnClear.setText(getString(R.string.clear_cache,"0"));
                    }

                    @Override
                    public void onNext(String size) {
                        mBtnClear.setText(getString(R.string.clear_cache,size));
                    }
                });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.btn_clear_cache:
                Observable.just(EnvironmentUtil.getCacheFile())
                        .map(new Func1<File, Boolean>() {
                            @Override
                            public Boolean call(File file) {
                                FileUtil.deleteDir(file);
                                return true;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Boolean>() {
                            @Override
                            public void onCompleted() {
                                calculateFileSize();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Snackbar.make(v, R.string.clear_cache_error,Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(Boolean b) {
                                Snackbar.make(v, R.string.clear_done,Snackbar.LENGTH_LONG).show();
                            }
                        });
                break;
        }
    }

}

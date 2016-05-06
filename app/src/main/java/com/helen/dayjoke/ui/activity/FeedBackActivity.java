package com.helen.dayjoke.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.helen.dayjoke.R;
import com.helen.dayjoke.api.APIManager;
import com.helen.dayjoke.api.APIService;
import com.helen.dayjoke.entity.BaseEn;
import com.helen.dayjoke.entity.BmobFile;
import com.helen.dayjoke.entity.FeedBack;
import com.helen.dayjoke.entity.FileBodyEn;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.EnvironmentUtil;
import com.helen.dayjoke.utils.ToastUtil;

import java.io.File;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 李晓伟 on 2016/5/5.
 *
 */
public class FeedBackActivity extends TitlebarActivity implements View.OnClickListener{

    public static void launcher(Context context){
        context.startActivity(new Intent(context,FeedBackActivity.class));
    }

    private EditText mEditTextContent;
    private EditText mEditTextContact;
    private CheckBox mCheckBox;
    private Button mBtnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(R.string.title_feedback);
        initView();
    }

    private APIService mAPIService;
    private void initView() {
        mEditTextContent = (EditText) findViewById(R.id.et_feedback_content);
        mEditTextContact = (EditText) findViewById(R.id.et_contact);
        mCheckBox = (CheckBox) findViewById(R.id.cb_upload_log);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mAPIService = APIManager.getInstance().getAPIService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                send();
                break;
            default:break;
        }
    }

    /**
     * 发送
     */
    private void send() {
        final String content = mEditTextContent.getText().toString().trim();
        final String contact = mEditTextContact.getText().toString();
        if(TextUtils.isEmpty(content)){
            ToastUtil.showToast(this,R.string.hint_input_feedback);
            return;
        }
        boolean isUploadLog = mCheckBox.isChecked();
        FeedBack feedBack = new FeedBack();
        feedBack.setContent(content);
        feedBack.setContact(contact);
        File logFile = null;
        if(isUploadLog){
            logFile = new File(EnvironmentUtil.getCacheFile() + File.separator + Constant.CACHE_LOG,Constant.LOG_FILE_NAME);
        }
        upload(feedBack,logFile);
    }

    private Subscriber<BaseEn> mSubscriber;

    /**
     * 上传
     */
    private void upload(final FeedBack feedBack , File logFile){
        if(mSubscriber == null || mSubscriber.isUnsubscribed()){
            mSubscriber = new Subscriber<BaseEn>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(BaseEn o) {
                    ToastUtil.showToast(FeedBackActivity.this,getString(R.string.thanks_to_feedback));
                }
            };
        }
        if(logFile != null && logFile.exists()) {
            RequestBody requestBody = RequestBody.create(Constant.TEXT,logFile);
            mAPIService.postFileUpload(logFile.getName(),requestBody)
                    .flatMap(new Func1<FileBodyEn, Observable<BaseEn>>() {
                        @Override
                        public Observable<BaseEn> call(FileBodyEn fileBodyEn) {
                            BmobFile bmobFile = new BmobFile(fileBodyEn.getFilename(),fileBodyEn.getCdn(),fileBodyEn.getUrl());
                            feedBack.setFileLog(bmobFile);
                            return doPostFeedback(feedBack);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mSubscriber);
        }else{
            feedBack.setFileLog(null);

            doPostFeedback(feedBack)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mSubscriber);
        }
    }

    /**
     * 执行
     */
    private Observable<BaseEn> doPostFeedback(FeedBack feedBack){
        RequestBody feedbackBody = RequestBody.create(Constant.JSON, feedBack.toString());
        return mAPIService.postFeedback(FeedBack.class.getSimpleName(),feedbackBody);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscriber != null && !mSubscriber.isUnsubscribed()){
            mSubscriber.unsubscribe();
        }
    }
}

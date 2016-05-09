package com.helen.dayjoke.api.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by 李晓伟 on 2016/5/9.
 *
 */
public class ProgressInterceptor implements Interceptor{
    private static final String TAG = "ProgressInterceptor";
    private ProgressListener mProgressListener;

    public ProgressInterceptor(ProgressListener listener){
        this.mProgressListener = listener;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(), mProgressListener))
                .build();
    }

}

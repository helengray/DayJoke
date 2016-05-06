package com.helen.dayjoke.api.interceptor;

import com.helen.dayjoke.utils.EnvironmentUtil;
import com.helen.dayjoke.utils.HLog;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 李晓伟 on 2016/4/28.
 *
 */
public class NetworkInterceptor implements Interceptor {
    public static final String TAG = "NetworkInterceptor.java";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("apikey","cd3546573f9f857c77f603aaa5f004f2");
        builder.addHeader("X-Bmob-Application-Id","893c2b4926e7201960116466fa76d2ca");
        builder.addHeader("X-Bmob-REST-API-Key","4688fb9639a6d64fae95f36f28f07a40");
        if(!EnvironmentUtil.isNetworkConnected()){
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        Request newRequest = builder.build();
        Response response = chain.proceed(newRequest);
        if(EnvironmentUtil.isNetworkConnected()){
            HLog.d(TAG,"response from network");
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = newRequest.cacheControl().toString();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }else {
            HLog.d(TAG,"response from cache");
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }

}

package com.helen.dayjoke.api;

import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.api.interceptor.LogInterceptor;
import com.helen.dayjoke.api.interceptor.NetworkInterceptor;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.EnvironmentUtil;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public class APIManager {
    private Retrofit retrofit;
    private APIManager(){
        File cacheFile = new File(EnvironmentUtil.getCacheFile(), Constant.CACHE_HTTP);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addNetworkInterceptor(new NetworkInterceptor());
        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new LogInterceptor());
        }
        builder.cache(new Cache(cacheFile, Constant.MAX_CACHE_SIZE));
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static APIManager getInstance(){
        return APIManagerInstance.INSTANCE;
    }

    private static class APIManagerInstance{
        final static APIManager INSTANCE = new APIManager();
    }

    private <T> T getService(Class<T> clazz){
        return retrofit.create(clazz);
    }

    public APIService getAPIService(){
        return getService(APIService.class);
    }
}

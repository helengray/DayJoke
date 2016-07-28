package com.helen.dayjoke.api;

import com.helen.dayjoke.BuildConfig;
import com.helen.dayjoke.api.interceptor.LogInterceptor;
import com.helen.dayjoke.api.interceptor.NetworkInterceptor;
import com.helen.dayjoke.entity.ResultList;
import com.helen.dayjoke.entity.VideoEn;
import com.helen.dayjoke.ui.application.Constant;
import com.helen.dayjoke.utils.EnvironmentUtil;

import java.io.File;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public void getVideo(int page,Subscriber<List<VideoEn>> subscriber){
        getAPIService().getVideo(page,Constant.PAGE_SIZE)
                .map(new Func1<ResultList<VideoEn>, List<VideoEn>>() {
                    @Override
                    public List<VideoEn> call(ResultList<VideoEn> videoEnResultList) {
                        if(videoEnResultList != null){
                            return videoEnResultList.getItems();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }
}

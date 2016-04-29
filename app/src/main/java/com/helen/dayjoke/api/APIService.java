package com.helen.dayjoke.api;

import com.helen.dayjoke.entity.ResponseEn;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 李晓伟 on 2016/4/27.
 *
 */
public interface APIService {
    String BASE_URL = "http://apis.baidu.com/showapi_open_bus/showapi_joke/";
    @Headers({"Cache-Control: public, max-age=3600","apikey:cd3546573f9f857c77f603aaa5f004f2"})
    @GET("joke_text")
    Observable<ResponseEn> getTextJoke(@Query("page") String page);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("joke_pic")
    Observable<ResponseEn> getPicJoke(@Query("page") String page);
}

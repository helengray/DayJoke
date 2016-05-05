package com.helen.dayjoke.api;

import com.helen.dayjoke.entity.ConstellationEn;
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
    String BASE_URL = "http://apis.baidu.com/";

    @Headers({"Cache-Control: public, max-age=3600"})
    @GET("showapi_open_bus/showapi_joke/joke_text")
    Observable<ResponseEn> getTextJoke(@Query("page") String page);

    @Headers("Cache-Control: public, max-age=3600")//1小时
    @GET("showapi_open_bus/showapi_joke/joke_pic")
    Observable<ResponseEn> getPicJoke(@Query("page") String page);

    @Headers("Cache-Control: public, max-age=43200")//12小时
    @GET("bbtapi/constellation/constellation_query")
    Observable<ConstellationEn> getConstellation(@Query("consName") String consName,@Query("type") String type);
}

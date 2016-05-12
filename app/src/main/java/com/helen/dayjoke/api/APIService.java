package com.helen.dayjoke.api;

import com.helen.dayjoke.entity.BaseEn;
import com.helen.dayjoke.entity.ConstellationEn;
import com.helen.dayjoke.entity.FileBodyEn;
import com.helen.dayjoke.entity.ResponseEn;
import com.helen.dayjoke.entity.constellation.VersionInfoResponseEn;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Helen on 2016/4/27.
 *
 */
public interface APIService {
    String BASE_URL = "http://apis.baidu.com/";

    @Headers("Cache-Control: public, max-age=600")
    @GET("showapi_open_bus/showapi_joke/joke_text")
    Observable<ResponseEn> getTextJoke(@Query("page") String page);

    @Headers("Cache-Control: public, max-age=600")//10分钟
    @GET("showapi_open_bus/showapi_joke/joke_pic")
    Observable<ResponseEn> getPicJoke(@Query("page") String page);

    @Headers("Cache-Control: public, max-age=21600")//6小时
    @GET("bbtapi/constellation/constellation_query")
    Observable<ConstellationEn> getConstellation(@Query("consName") String consName,@Query("type") String type);

    //Bomb api
    @POST("https://api.bmob.cn/2/files/{fileName}")
    Observable<FileBodyEn> postFileUpload(@Path("fileName") String fileName, @Body RequestBody file);

    @POST("https://api.bmob.cn/1/classes/FeedBack")
    Observable<BaseEn> postFeedback(@Body RequestBody feedBack);

    @GET("https://api.bmob.cn/1/classes/VersionInfo")
    Observable<VersionInfoResponseEn> getVersionInfo(@Query("limit") int limit, @Query("order") String bql);

}

package club.litiansai.retrofit.api;

import java.util.Map;

import club.litiansai.retrofit.entity.Result;
import club.litiansai.retrofit.entity.UserEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("/path")
    Call<String> login1(@Query("test") String test);

    @GET("/path")
    Call<String> login2(@QueryMap Map<String, String> map);


    @POST("/path")
    @FormUrlEncoded // 放到请求体中
    Call<String> test02(@Field("test") String test);

    @POST("/path")
    @FormUrlEncoded // 放到请求体中
    Call<String> test03(@FieldMap Map<String, String> map);

    @POST("/path")
    Call<Result> test04(@Body UserEntity user);


}




























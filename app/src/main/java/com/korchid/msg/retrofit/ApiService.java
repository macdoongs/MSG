package com.korchid.msg.retrofit;

import com.google.gson.JsonObject;
import com.korchid.msg.Repos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mac0314 on 2017-01-03.
 */

// http://gun0912.tistory.com/30
public interface ApiService {
    public static final String API_URL = "https://www.korchid.com/";

    @GET("/msg/user/{userid}/repos")
    @Headers("Accept-Encoding: application/json")
    //Call<List<JsonObject>> listRepos(@Path("userid") String userid);
    //Call<JsonObject> listRepos(@Path("userid") String userid);
    //Call<List<Repos>> listRepos(@Path("userid") String userid);
    Call<Repos> Repos(@Path("userid") String userid);

    @FormUrlEncoded
    @POST("/msg/user/signup")
    Call<Response> userSignup(@Field("phoneNumber") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/msg/user/login")
    Call<List<User>> userLogin(@Field("phoneNumber") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/msg/user/recovery/password")
    Call<List<UserAuth>> listRecoveryPassword(@Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("/msg/user/load")
    Call<List<UserData>> listLoadUserData(@Field("userId") int userId);

    @GET("/msg/user/{phoneNumber}/duplicate")
    @Headers("Accept-Encoding: application/json")
    Call<List<User>> listUserDuplicateCheck(@Path("phoneNumber") String phoneNumber);


}

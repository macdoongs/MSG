package com.korchid.msg.retrofit;

import com.google.gson.JsonObject;
import com.korchid.msg.Repos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by mac0314 on 2017-01-03.
 */

// http://gun0912.tistory.com/30
public interface ApiService {
    public static final String API_URL = "https://www.korchid.com/";

    @GET("/msg-user/{userid}/repos")
    @Headers("Accept-Encoding: application/json")
    //Call<List<JsonObject>> listRepos(@Path("userid") String userid);
    //Call<JsonObject> listRepos(@Path("userid") String userid);
    //Call<List<Repos>> listRepos(@Path("userid") String userid);
    Call<Repos> listRepos(@Path("userid") String userid);
}

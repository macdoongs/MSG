package com.korchid.msg.retrofit;

import com.korchid.msg.Repos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mac on 2017-01-03.
 */

// http://gun0912.tistory.com/30
public interface LoadingUser {
    @GET("/msg-user/{userid}/repos")
    List<Repos> listRepos(@Path("userid") String userid);

}

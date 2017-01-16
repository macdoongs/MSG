package com.korchid.msg.retrofit;

import com.korchid.msg.Repos;
import com.korchid.msg.retrofit.response.Res;
import com.korchid.msg.retrofit.response.ReservationMessage;
import com.korchid.msg.retrofit.response.User;
import com.korchid.msg.retrofit.response.UserAuth;
import com.korchid.msg.retrofit.response.UserData;
import com.korchid.msg.retrofit.response.UserMap;

import java.util.List;

import retrofit2.Call;
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


    @POST("/msg/user/signup")
    @FormUrlEncoded
    Call<Res> userSignup(@Field("phoneNumber") String phoneNumber,
                         @Field("password") String password);


    @POST("/msg/user/login")
    @FormUrlEncoded
    Call<List<User>> userLogin(@Field("phoneNumber") String phoneNumber,
                               @Field("password") String password);


    @POST("/msg/user/recovery/password")
    @FormUrlEncoded
    Call<List<UserAuth>> listRecoveryPassword(@Field("phoneNumber") String phoneNumber);

    @POST("/msg/user/load")
    @FormUrlEncoded
    Call<List<UserData>> listLoadUserData(@Field("userId") int userId);

    @GET("/msg/user/{phoneNumber}/duplicate")
    @Headers("Accept-Encoding: application/json")
    Call<List<User>> listUserDuplicateCheck(@Path("phoneNumber") String phoneNumber);

    @POST("/msg/user/setting")
    @FormUrlEncoded
    Call<Res> userSetting(@Field("userId") int userId,
                                      @Field("messageAlert") Boolean messageAlert,
                                      @Field("reserveEnable") Boolean reserveEnable,
                                      @Field("reserveAlert") Boolean reserveAlert,
                                      @Field("weekNumber") int weekNumber,
                                      @Field("reserveNumber") int reserveNumber);

    @POST("/msg/user/info")
    @FormUrlEncoded
    Call<Res> userInfo(@Field("userId") int userId,
                                    @Field("nickname") String nickname,
                                    @Field("sex") String sex,
                                    @Field("birthday") String birthday,
                                    @Field("profile") String profile);

    @POST("/msg/user/invitation")
    @FormUrlEncoded
    Call<Res> userInvitation(@Field("userId") int userId,
                             @Field("receiverPhoneNumber") String receiverPhoneNumber,
                             @Field("roleName") String roleName);

    @POST("/msg/user/reservation")
    @FormUrlEncoded
    Call<Res> userReservation(@Field("senderId") int senderId,
                              @Field("receiverId") int receiverId,
                              @Field("reservationMessageId") int reservationMessageId);


    @POST("/msg/user/mapping")
    @FormUrlEncoded
    Call<Res> userMapping(@Field("parentId") int parentId,
                          @Field("childId") int childId,
                          @Field("deviceToken") String deviceToken) ;

    @GET("/msg/user/mapping/me/{userId}/myRole/{userRole}")
    @Headers("Accept-Encoding: application/json")
    Call<List<UserMap>> userMapping(@Path("userId") int userId,
                              @Path("userRole") String userRole);


    @POST("/msg/firebase/register")
    @FormUrlEncoded
    Call<Res> firebaseRegister(@Field("userId") int userId,
                               @Field("deviceToken") String deviceToken);


    @POST("/msg/error")
    @FormUrlEncoded
    Call<Res> error();

    @POST("/msg/error/user")
    @FormUrlEncoded
    Call<Res> userError(@Field("userId") int userId);


    @GET("/msg/user/reservation/message/type/{typeId}")
    @Headers("Accept-Encoding: application/json")
    Call<List<ReservationMessage>> reservationMessage(@Path("typeId") int typeId);


    // TODO REQUEST LIST
/*
    @POST("/msg/auth/sms/send")
    @FormUrlEncoded
    Call<Response> ;

    @POST("/msg/auth/sms/check")
    @FormUrlEncoded
    Call ;





    @GET("/msg/user/chatting/sender/{senderId}/receiver/{receiverId}")
    @Headers("Accept-Encoding: application/json")
    @FormUrlEncoded
    Call ;

    @POST("/msg/chatting/topic/subscription")
    @FormUrlEncoded
    Call ;

    @POST("/msg/chatting/topic/unsubscription")
    @FormUrlEncoded
    Call ;

    */
}

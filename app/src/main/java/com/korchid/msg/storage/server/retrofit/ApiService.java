package com.korchid.msg.storage.server.retrofit;

import com.korchid.msg.storage.server.retrofit.response.DuplicateCheck;
import com.korchid.msg.storage.server.retrofit.response.Load;
import com.korchid.msg.storage.server.retrofit.response.Login;
import com.korchid.msg.storage.server.retrofit.response.Res;
import com.korchid.msg.storage.server.retrofit.response.ReservationMessage;
import com.korchid.msg.storage.server.retrofit.response.Signup;
import com.korchid.msg.storage.server.retrofit.response.User;
import com.korchid.msg.storage.server.retrofit.response.UserAuth;
import com.korchid.msg.storage.server.retrofit.response.UserData;
import com.korchid.msg.storage.server.retrofit.response.UserMap;

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


    @POST("/msg/user/Signup")
    @FormUrlEncoded
    Call<Signup> userSignup(@Field("phoneNumber") String phoneNumber,
                            @Field("password") String password);


    @POST("/msg/user/login")
    @FormUrlEncoded
    Call<Login> userLogin(@Field("phoneNumber") String phoneNumber,
                          @Field("password") String password);


    @GET("/msg/user/load/{userId}")
    @Headers("Accept-Encoding: application/json")
    Call<Load> listLoadUserData(@Path("userId") int userId);

    @GET("/msg/user/{phoneNumber}/duplicate")
    @Headers("Accept-Encoding: application/json")
    Call<DuplicateCheck> listUserDuplicateCheck(@Path("phoneNumber") String phoneNumber);


    @POST("/msg/user/recovery/password")
    @FormUrlEncoded
    Call<List<UserAuth>> listRecoveryPassword(@Field("phoneNumber") String phoneNumber);

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

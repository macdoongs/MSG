package com.korchid.msg.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.korchid.msg.storage.server.retrofit.RestfulAdapter;
import com.korchid.msg.R;
import com.korchid.msg.storage.server.retrofit.response.Load;
import com.korchid.msg.storage.server.retrofit.response.User;
import com.korchid.msg.storage.server.retrofit.response.UserData;
import com.korchid.msg.ui.StatusBar;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.MESSAGE_ALERT;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_ALERT;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_CHECK;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_ENABLE;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_TIMES;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_WEEK_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_RESERVATION_SETTING;
import static com.korchid.msg.global.QuickstartPreferences.USER_BIRTHDAY;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_INFO_CHECK;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_NICKNAME;
import static com.korchid.msg.global.QuickstartPreferences.USER_PROFILE;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_SEX;

/**
 * Created by mac0314 on 2016-11-28.
 */


// Loading screen
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    private static final int SPLASH_LOGIN = 1500;
    private static final int SPLASH_LOGOUT = 1000;


    private int userId;
    private String userNickname;
    private String userSex;
    private Date userBirthday;
    private String userProfile;
    private String userRole;
    private int userMessageAlert;
    private int userReserveEnable;
    private int userReserveAlert;
    private int userWeekNumber;
    private int userReserveNumber;
    private int userRoleNumber;

    private String loginState = "";
    private int duration = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        loginState = getIntent().getStringExtra(USER_LOGIN_STATE);
        userId = getIntent().getIntExtra(USER_ID_NUMBER, 0);



        if(loginState.equals("LOGIN")){
            duration = SPLASH_LOGIN;

            // TODO modify real userId data
            userId = 16;
            Call<Load> userDataCall = RestfulAdapter.getInstance().loadUserData(userId);

            userDataCall.enqueue(new Callback<Load>() {
                @Override
                public void onResponse(Call<Load> call, Response<Load> response) {
                    Log.d(TAG, "onResponse");

                    if(response.body().getLoad()){
                        User user = response.body().getUser();


                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt(USER_ROLE_NUMBER, userRoleNumber);

                        editor.apply();


                        userNickname = user.getNickname_sn();
                        userSex = user.getSex_sn();
                        userBirthday = user.getBirthday_dt();
                        userProfile = user.getProfile_ln();
                        userMessageAlert = user.getMessage_alert();
                        userReserveEnable = user.getReserve_enable();
                        userReserveAlert = user.getReserve_alert();
                        userWeekNumber = user.getWeek_number();
                        userReserveNumber = user.getReserve_number();
                        userRoleNumber = user.getRole_number();

                        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

                        editor = sharedPreferences.edit();

                        editor.putString(USER_NICKNAME, userNickname);
                        editor.putLong(USER_BIRTHDAY, userBirthday.getTime());
                        editor.putString(USER_PROFILE, userProfile);
                        editor.putString(USER_SEX, userSex);
                        editor.putString(USER_ROLE, userRole);

                        editor.apply();

                        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_RESERVATION_SETTING, 0);

                        editor = sharedPreferences.edit();

                        editor.putInt(MESSAGE_ALERT, userMessageAlert);
                        editor.putInt(RESERVATION_ENABLE, userReserveEnable);
                        editor.putInt(RESERVATION_ALERT, userReserveAlert);
                        editor.putInt(RESERVATION_WEEK_NUMBER, userWeekNumber);
                        editor.putInt(RESERVATION_TIMES, userReserveNumber);

                        editor.apply();

                    }else{
                        Toast.makeText(getApplicationContext(), "정보를 가져오지 못했습니다.", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<Load> call, Throwable t) {
                    Log.d(TAG, "onFailure");
                    Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                }
            });



        }else{
            duration = SPLASH_LOGOUT;
        }


        Handler handler = new Handler();
        // Loading page Duration : 2 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();

                if(loginState.equals("LOGIN")){

                    intent.putExtra(USER_LOGIN_STATE, loginState);

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);
                    Boolean isSetUserInfo = sharedPreferences.getBoolean(USER_INFO_CHECK, false);

                    if(isSetUserInfo){
                        intent.putExtra(USER_ID_NUMBER, userId);
                        intent.putExtra(USER_NICKNAME, userNickname);
                        intent.putExtra(USER_SEX, userSex);
                        //intent.putExtra(USER_BIRTHDAY, userBirthday.getTime());
                        intent.putExtra(USER_PROFILE, userProfile);
                        intent.putExtra(USER_ROLE, userRole);
                    }

                    sharedPreferences = getSharedPreferences(SHARED_PREF_USER_RESERVATION_SETTING, 0);

                    Boolean isSetUserSetting = sharedPreferences.getBoolean(RESERVATION_CHECK, false);

                    if(isSetUserSetting){
                        intent.putExtra(MESSAGE_ALERT, userMessageAlert);
                        intent.putExtra(RESERVATION_ENABLE, userReserveEnable);
                        intent.putExtra(RESERVATION_ALERT, userMessageAlert);
                        intent.putExtra(RESERVATION_WEEK_NUMBER, userWeekNumber);
                        intent.putExtra(RESERVATION_TIMES, userReserveNumber);
                    }


                }


                setResult(RESULT_OK, intent);

                finish();
            }
        }, duration);
    }

    private void initView(){

        StatusBar statusBar = new StatusBar(this);
    }

}

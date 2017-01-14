package com.korchid.msg.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.api.client.util.DateTime;
import com.korchid.msg.adapter.RestfulAdapter;
import com.korchid.msg.R;
import com.korchid.msg.retrofit.response.UserData;
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

            Call<List<UserData>> userDataCall = RestfulAdapter.getInstance().listLoadUserData(userId);

            userDataCall.enqueue(new Callback<List<UserData>>() {
                @Override
                public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                    //Log.d(TAG, "onResponse");

                    if(response.body().isEmpty()){
                        return;
                    }else{
                        UserData userData = response.body().get(0);

                        if(userData != null) {

                            userNickname = userData.getNickname_sn();
                            userSex = userData.getSex_sn();
                            userBirthday = userData.getBirthday_dt();
                            userProfile = userData.getProfile_ln();
                            userRole = userData.getRole_name_sn();
                            userMessageAlert = userData.getMessage_alert();
                            userReserveEnable = userData.getReserve_enable();
                            userReserveAlert = userData.getReserve_alert();
                            userWeekNumber = userData.getWeek_number();
                            userReserveNumber = userData.getReserve_number();

                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(USER_NICKNAME, userNickname);
                            editor.putLong(USER_BIRTHDAY, userBirthday.getTime());
                            editor.putString(USER_PROFILE, userProfile);
                            editor.putString(USER_SEX, userSex);
                            editor.putString(USER_ROLE, userRole);

                            sharedPreferences = getSharedPreferences(SHARED_PREF_USER_RESERVATION_SETTING, 0);

                            editor = sharedPreferences.edit();

                            editor.putInt(MESSAGE_ALERT, userMessageAlert);
                            editor.putInt(RESERVATION_ENABLE, userReserveEnable);
                            editor.putInt(RESERVATION_ALERT, userReserveAlert);
                            editor.putInt(RESERVATION_WEEK_NUMBER, userWeekNumber);
                            editor.putInt(RESERVATION_TIMES, userReserveNumber);


                            editor.apply();
                        }
                    }


                }

                @Override
                public void onFailure(Call<List<UserData>> call, Throwable t) {
                    Log.d(TAG, "onFailure");
                    //Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                }
            });



        }else{
            duration = SPLASH_LOGOUT;
        }





        SharedPreferences sharedPreferences = getSharedPreferences("MAPPING", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String topic = sharedPreferences.getString("TOPIC", "");

        // TODO if no sharedPreferences data, Load user data - web server
        if(topic.equals("")){
            String userId = "2";//getIntent().getStringExtra("USER_ID_NUM");
/*
            String stringUrl = "https://www.korchid.com/msg-user/" + userId;

            Handler httpHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    String response = msg.getData().getString("response");

                    Log.d(TAG, "response : " + response);

                    String[] line = response.split("\n");

                        String[] partition = line[0].split(" / ");
                        //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        if(line[0].equals("Error")){
                            Toast.makeText(getApplicationContext(), "No ID! please join.", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_LONG).show();

                            for(int i=0; i<partition.length; i++){
                                Log.d(TAG, "partition " + i + " : " + partition[i]);
                            }



                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();


                        }

                } // Handler End
            };



            HttpGet httpGet = new HttpGet(stringUrl, httpHandler);
            httpGet.start();
*/
        }else{




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

package com.korchid.msg.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.korchid.msg.Repos;
import com.korchid.msg.adapter.RestfulAdapter;
import com.korchid.msg.http.HttpGet;
import com.korchid.msg.R;
import com.korchid.msg.retrofit.ApiService;
import com.korchid.msg.retrofit.UserData;
import com.korchid.msg.ui.StatusBar;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.korchid.msg.retrofit.ApiService.API_URL;

/**
 * Created by mac0314 on 2016-11-28.
 */


// Loading screen
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    private int duration = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        duration = getIntent().getIntExtra("duration", duration);


        Call<List<UserData>> userDataCall = RestfulAdapter.getInstance().listLoadUserData(16);

        userDataCall.enqueue(new Callback<List<UserData>>() {
            @Override
            public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                Log.d(TAG, "nickname : " + response.body().get(0).getNickname_sn());
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                Log.d(TAG, "onFailure");
                //Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
            }
        });



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

                finish();
            }
        }, duration);
    }

    private void initView(){

        StatusBar statusBar = new StatusBar(this);
    }

}

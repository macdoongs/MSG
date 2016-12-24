package com.korchid.msg.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.korchid.msg.http.HttpGet;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

/**
 * Created by mac0314 on 2016-11-28.
 */


// Loading screen
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    int duration = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StatusBar statusBar = new StatusBar(this);

        duration = getIntent().getIntExtra("duration", duration);


        SharedPreferences sharedPreferences = getSharedPreferences("MAPPING", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String topic = sharedPreferences.getString("TOPIC", "");

        if(topic.equals("")){
            String userId = getIntent().getStringExtra("USER_ID_NUM");

            String stringUrl = "https://www.korchid.com/msg-mapping/" + userId;

            Handler httpHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    String response = msg.getData().getString("response");

                    Log.d(TAG, "response : " + response);

                    String[] line = response.split("\n");
/*
                        String[] partition = line[0].split("/");
                        //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        if(line[0].equals("Error")){
                            Toast.makeText(getApplicationContext(), "No ID! please join.", Toast.LENGTH_LONG).show();
                        }else if(line[0].equals("No")){
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_LONG).show();

                            for(int i=0; i<partition.length; i++){
                                Log.d(TAG, "partition " + i + " : " + partition[i]);
                            }




                            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", 0);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("USER_LOGIN", "LOGIN");
                            editor.putString("USER_PHONE", phoneNumber);
                            editor.putString("USER_PASSWORD", password);
                            editor.putString("USER_ID_NUM", partition[0]);
                            editor.putString("USER_TOKEN", partition[1]);
                            editor.commit(); // Apply file

                            Log.d(TAG, "SharedPreference");
                            Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString("USER_LOGIN", "LOGOUT"));
                            Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString("USER_PHONE", "000-0000-0000"));
                            Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString("USER_PASSWORD", "123123"));

                            Intent intent = new Intent();
                            //intent.putExtra("result_msg", "결과가 넘어간다 얍!");
                            setResult(RESULT_OK, intent);
                            finish();


                        }
*/
                } // Handler End
            };



            HttpGet httpGet = new HttpGet(stringUrl, httpHandler);
            httpGet.start();

        }else{




        }


        Handler handler = new Handler();
        // Duration : 2 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, duration);
    }
}

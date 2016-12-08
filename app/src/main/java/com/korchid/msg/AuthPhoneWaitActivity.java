package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AuthPhoneWaitActivity extends AppCompatActivity {
    private static final String TAG = "AuthPhoneWaitActivity";
    Button btn_auth;
    String phoneNum;
    String password;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone_wait);



        phoneNum = getIntent().getExtras().getString("phoneNum");
        password = getIntent().getExtras().getString("password");

        token = "dNb4wEIZKv8:APA91bGwcxOlb-_kLZCbNzR6GVxTh9CW7Hb8mnTmo1iBp_Vfr0UEWe3ZsLL6vv02bMMLpi27hL6A57dCRJaFG5Cy4k-kc6QN8ecoT5Uf8V4jzT6J5qkBdZ8ZQoC4O-WgJt566NL-5AnE";

        Log.d(TAG, "phoneNum : " + phoneNum);
        Log.d(TAG, "password : " + password);

        btn_auth = (Button) findViewById(R.id.authButton);
        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                OutputStreamWriter request = null;

                URL url = null;
                String response = null;

                try {
                    url = new URL("https://www.korchid.com/sms-sender/" + phoneNum + "/" + password + "/" + token);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestMethod("GET");

                    request = new OutputStreamWriter(connection.getOutputStream());
                    request.flush();
                    request.close();
                    String line = "";

                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    isr.close();
                    reader.close();


                } catch (IOException e) {
                    // Error
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();


    }
}

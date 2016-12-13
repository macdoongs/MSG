package com.korchid.msg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPhoneActivity extends AppCompatActivity {
    private static final String TAG = "LoginPhoneActivity";

    private Button btn_back;
    private Button btn_login;

    private EditText et_phoneNumber;
    private EditText et_password;

    private String phoneNumber;
    private String password;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        sharedPreferences = getSharedPreferences("login", 0);

        editor = sharedPreferences.edit();

        final String state = sharedPreferences.getString("USER_LOGIN", "LOGOUT");


        Log.d(TAG, "Login : " + state);

        if (state.equals("LOGIN")) {
            // Delete preference value
            // 1. Remove "key" data
            //editor.remove("key");

            // 2. Remove xml data
            editor.clear();
            editor.commit();

            Log.d(TAG, "SharedPreference");
            Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString("USER_LOGIN", "LOGOUT"));
            Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString("USER_PHONE", "000-0000-0000"));
            Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString("USER_PASSWORD", "123123"));

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }


        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO check
                phoneNumber = et_phoneNumber.getText().toString();
                password = et_password.getText().toString();

                if(phoneNumber.equals("010-2368-0314")){
                    if(password.equals("123")){
                        // http://mommoo.tistory.com/38
                        // Use Environmental variable 'SharedPreference'


                        editor.putString("USER_LOGIN", "LOGIN");
                        editor.putString("USER_PHONE", phoneNumber);
                        editor.putString("USER_PASSWORD", password);
                        editor.commit(); // Apply file



                        // if sharedPreferences.getString value is 0, assign 2th parameter
                        Log.d(TAG, "SharedPreference");
                        Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString("USER_LOGIN", "LOGOUT"));
                        Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString("USER_PHONE", "000-0000-0000"));
                        Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString("USER_PASSWORD", "123123"));
                        Intent intent = new Intent();
                        //intent.putExtra("result_msg", "결과가 넘어간다 얍!");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            }
        });
    }

}

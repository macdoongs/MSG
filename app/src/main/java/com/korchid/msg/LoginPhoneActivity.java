package com.korchid.msg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.util.HashMap;

public class LoginPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginPhoneActivity";

    private Button btn_login;
    private Button btn_findPassword;

    private EditText et_phoneNumber;
    private EditText et_password;

    private String phoneNumber;
    private String password;
    private int viewId;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Login");


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


        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_findPassword = (Button) findViewById(R.id.btn_findPassword);
        btn_findPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_login:{
                phoneNumber = et_phoneNumber.getText().toString();
                password = et_password.getText().toString();

                String stringUrl = "https://www.korchid.com/msg-login";
                HashMap<String, String> params = new HashMap<>();
                params.put("phoneNumber", phoneNumber);
                params.put("password", password);

                Handler httpHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String response = msg.getData().getString("response");

                        String[] line = response.split("\n");

                        //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        if(line[0].equals("Error")){
                            Toast.makeText(getApplicationContext(), "No ID! please join.", Toast.LENGTH_LONG).show();
                        }else if(line[0].equals("No")){
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_LONG).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("login", 0);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("USER_LOGIN", "LOGIN");
                            editor.putString("USER_PHONE", phoneNumber);
                            editor.putString("USER_PASSWORD", password);
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

                    }
                };

                HttpPost httpPost = new HttpPost(stringUrl, params, httpHandler);
                httpPost.start();
                break;
            }
            case R.id.btn_findPassword:{

                break;
            }
            default:{
                break;
            }
        }
    }// onClick End


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }

        return true;
    }

}

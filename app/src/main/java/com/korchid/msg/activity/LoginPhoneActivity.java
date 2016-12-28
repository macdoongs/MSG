package com.korchid.msg.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.http.HttpPost;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.util.HashMap;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_TOKEN;
import static com.korchid.msg.global.QuickstartPreferences.USER_PASSWORD;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;

public class LoginPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginPhoneActivity";

    private Button btn_login;
    private Button btn_findPassword;

    private EditText et_phoneNumber;
    private EditText et_password;

    private String phoneNumber;
    private String userPhoneNumber;
    private String password;
    private int viewId;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        initView();

        // Read user device phone number
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        userPhoneNumber = telManager.getLine1Number();


        final String state = getIntent().getStringExtra(USER_LOGIN_STATE);

        Log.d(TAG, "Login : " + state);

        if (state.equals("LOGIN")) {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();
            editor.commit();

            Intent intent = new Intent();
            intent.putExtra(USER_LOGIN_STATE, "LOGOUT");
            setResult(RESULT_OK, intent);
            finish();
        }

        et_phoneNumber.setText(userPhoneNumber);


    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Login");

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_findPassword = (Button) findViewById(R.id.btn_findPassword);

        btn_login.setOnClickListener(this);
        btn_login.setEnabled(false);

        // http://egloos.zum.com/killins/v/3008925
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                String input2 = et_phoneNumber.getText().toString();

                if(input.length() > 0 && input2.length() > 0){
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.color.colorPrimary);
                }else{
                    btn_login.setEnabled(false);
                }
            }
        };
        et_password.addTextChangedListener(textWatcher);

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




                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(USER_LOGIN_STATE, "LOGIN");
                            editor.putString(USER_PHONE_NUMBER, phoneNumber);
                            editor.putString(USER_PASSWORD, password);
                            editor.putString(USER_ID_NUMBER, partition[0]);
                            editor.putString(USER_LOGIN_TOKEN, partition[1]);
                            editor.commit(); // Apply file

                            Log.d(TAG, "SharedPreference");
                            Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString(USER_LOGIN_STATE, "LOGOUT"));
                            Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString(USER_PHONE_NUMBER, "000-0000-0000"));
                            Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString(USER_PASSWORD, "123123"));

                            Intent intent = new Intent();
                            //intent.putExtra("result_msg", "결과가 넘어간다 얍!");
                            intent.putExtra(USER_LOGIN_STATE, "LOGIN");
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
                Intent intent = new Intent(getApplicationContext(), FindPasswordActivity.class);
                intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);
                Log.d(TAG, "userPhoneNumber : " + userPhoneNumber);
                startActivityForResult(intent, 0);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case RESULT_OK:{
                // find password activity
                if(requestCode == 0){
                    Log.d(TAG, "find password OK");
                    password = data.getStringExtra(USER_PASSWORD);
                    this.et_password.setText(password);
                }
                break;
            }
            case RESULT_CANCELED:{
                break;
            }
            default:{
                break;
            }
        }

    }
}

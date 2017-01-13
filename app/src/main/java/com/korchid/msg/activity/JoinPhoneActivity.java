package com.korchid.msg.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.http.HttpPost;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.util.HashMap;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_PASSWORD;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;

// Register phone number and password
public class JoinPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "JoinPhoneActivity";

    private TextView tv_phoneNumber;

    private EditText et_password;
    private EditText et_passwordConfirm;
    private Button btn_register;

    private String phoneNumber = "";
    private String password = "";
    private String passwordConfirm = "";

    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_phone);

        initView();

        phoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        tv_phoneNumber.setText(phoneNumber);


    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);
        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Join");

        tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordConfirm = (EditText) findViewById(R.id.et_passwordConfirm);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);

        btn_register.setEnabled(false);

        et_passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                String input2 = et_password.getText().toString();

                if(input.length() > 0 && input2.length() > 0){
                    btn_register.setEnabled(true);
                    btn_register.setBackgroundResource(R.drawable.rounded_button_p_2r);
                }else{
                    btn_register.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_register:{
                password = et_password.getText().toString();
                passwordConfirm = et_passwordConfirm.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(JoinPhoneActivity.this);

                if(phoneNumber.equals("")){
                    builder.setTitle("Warning");
                    builder.setMessage("Check your phone number.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else if(password.equals("")) {
                    builder.setTitle("Warning");
                    builder.setMessage("Check your password.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else if(!password.equals(passwordConfirm)) {
                    builder.setTitle("Don't match");
                    builder.setMessage("Check your confirm password.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else{
                    builder.setTitle("Confirm");
                    builder.setMessage("Send auth sms message.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {

                            String url = "https://www.korchid.com/msg/user/signup/";

                            HashMap<String, String> params = new HashMap<>();
                            params.put("phoneNumber", phoneNumber);
                            params.put("password", password);

                            Handler httpHandler = new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    String response = msg.getData().getString("response");

                                    String[] line = response.split("\n");

                                    //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                                    if(line[0].equals("OK")){
                                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                                        // http://mommoo.tistory.com/38
                                        // Use Environmental variable 'SharedPreference'
                                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putString(USER_LOGIN_STATE, "LOGIN");
                                        editor.putString(USER_PHONE_NUMBER, phoneNumber);
                                        editor.putString(USER_PASSWORD, password);
                                        editor.commit(); // Apply file

                                        /*
                                        // Delete preference value
                                        // 1. Remove "key" data
                                        editor.remove("key");

                                        // 2. Remove xml data
                                        editor.clear();
                                        */

                                        // if sharedPreferences.getString value is 0, assign 2th parameter
                                        Log.d(TAG, "SharedPreference");
                                        Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString(USER_LOGIN_STATE, "LOGOUT"));
                                        Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString(USER_PHONE_NUMBER, "000-0000-0000"));
                                        Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString(USER_PASSWORD, "123123"));
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Already join!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };

                            HttpPost httpPost = new HttpPost(url, params, httpHandler);
                            httpPost.start();

                            Intent intent = new Intent();
                            //intent.putExtra("result_msg", "Example");
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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

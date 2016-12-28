package com.korchid.msg.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.korchid.msg.R;
import com.korchid.msg.http.HttpPost;

import java.util.HashMap;

import static com.korchid.msg.global.QuickstartPreferences.USER_PASSWORD;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;

// Find user password
public class FindPasswordActivity extends AppCompatActivity {
    private static final String TAG = "FindPasswordActivtiy";

    private String userPhoneNumber = "";
    private String password = "";

    private TextView tv_phoneNumber;
    private TextView tv_password;

    private Button btn_complete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        initView();

        userPhoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        Log.d(TAG, "userPhoneNumber : " + userPhoneNumber);

        Intent intent = new Intent(getApplicationContext(), AuthPhoneWaitActivity.class);
        intent.putExtra("mode", "find");
        intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);

        startActivityForResult(intent, 0);
    }

    private void initView(){
        tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);
        tv_password = (TextView) findViewById(R.id.tv_password);

        btn_complete = (Button) findViewById(R.id.btn_complete);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);
                intent.putExtra(USER_PASSWORD, password);

                Log.d(TAG, "phone : " + userPhoneNumber);
                Log.d(TAG, "password : " + password);


                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (resultCode){
            case RESULT_OK:{
                String url = "https://www.korchid.com/msg-find-password";

                HashMap<String, String> params = new HashMap<>();
                params.put("phoneNumber", userPhoneNumber);

                final String userPassword = data.getStringExtra(USER_PASSWORD);

                Handler httpHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String response = msg.getData().getString("response");

                        //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        String[] line = response.split("\n");

                        if(line[0].equals("ERROR")){
                            Toast.makeText(getApplicationContext(), "No ID!", Toast.LENGTH_LONG).show();
                        }else{
                            //Toast.makeText(getApplicationContext(), "Password : " + password, Toast.LENGTH_LONG).show();

                            tv_phoneNumber.setText(userPhoneNumber);
                            tv_password.setText(userPassword);

                        }
                    }
                };

                HttpPost httpPost = new HttpPost(url, params, httpHandler);
                httpPost.start();

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

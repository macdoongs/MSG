package com.korchid.msg.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.korchid.msg.R;

import static com.korchid.msg.global.QuickstartPreferences.USER_PASSWORD;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;

// Find user password
public class FindPasswordActivity extends AppCompatActivity {
    private static final String TAG = "FindPasswordActivtiy";

    private String userPhoneNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        userPhoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        Log.d(TAG, "userPhoneNumber : " + userPhoneNumber);

        Intent intent = new Intent(getApplicationContext(), AuthPhoneWaitActivity.class);
        intent.putExtra("mode", "find");
        intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);

        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (resultCode){
            case RESULT_OK:{
                //Log.d(TAG, "OK");
                String password = data.getStringExtra(USER_PASSWORD);
                Toast.makeText(getApplicationContext(), "Password : " + password, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);
                intent.putExtra(USER_PASSWORD, password);

                Log.d(TAG, "phone : " + userPhoneNumber);
                Log.d(TAG, "password : " + password);


                setResult(RESULT_OK, intent);
                finish();

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

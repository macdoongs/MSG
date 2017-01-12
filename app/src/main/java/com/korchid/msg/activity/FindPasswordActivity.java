package com.korchid.msg.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.korchid.msg.R;
import com.korchid.msg.adapter.RestfulAdapter;
import com.korchid.msg.retrofit.response.UserAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.AUTH_MODE;
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

        userPhoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        Log.d(TAG, "userPhoneNumber : " + userPhoneNumber);

        initView();


        Intent intent = new Intent(getApplicationContext(), AuthPhoneWaitActivity.class);
        intent.putExtra(AUTH_MODE, "find");
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
                Log.d(TAG, "onActivityResult: " + userPhoneNumber);

                Call<List<UserAuth>> userDataCall = RestfulAdapter.getInstance().listRecoveryPassword(userPhoneNumber);

                userDataCall.enqueue(new Callback<List<UserAuth>>() {
                    @Override
                    public void onResponse(Call<List<UserAuth>> call, Response<List<UserAuth>> response) {
                        Log.d(TAG, "password : " + response.body().get(0).getPassword_sn());
                    }

                    @Override
                    public void onFailure(Call<List<UserAuth>> call, Throwable t) {
                        Log.d(TAG, "onFailure");
                        Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                    }
                });

                break;
            }
            case RESULT_CANCELED:{
                finish();
                break;
            }
            default:{
                break;
            }
        }
    }

}

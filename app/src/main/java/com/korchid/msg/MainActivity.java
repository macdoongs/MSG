package com.korchid.msg;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.korchid.msg.service.ServiceThread;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{//implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private static final int SPLASH_LOGIN = 1000;
    private static final int SPLASH_LOGOUT = 1500;

    private GoogleApiClient mGoogleApiClient;

    enum REQUEST_CODE {LOGIN, LOGOUT}

    private FirebaseAnalytics mFirebaseAnalytics;


    private Button btn_join;
    private Button btn_login;

    private Button btn_next;
    private Button btn_invite;

    private Button btn_reserve;
    private Button btn_temp;
    private Button btn_userInfo;

    private String loginState;
    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_main, getResources().getString(R.string.app_name));

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        final ServiceThread serviceThread = new ServiceThread(new Handler());
        serviceThread.timer = "2016-12-13 14:55";
        serviceThread.start();

        Log.d(TAG, "timer : " + serviceThread.timer);

        serviceThread.timer = "2016-12-16 19:15";

        Log.d(TAG, "timer : " + serviceThread.timer);



        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        btn_invite = (Button) findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(this);

        btn_join = (Button) findViewById(R.id.btn_join);

        // Use Environmental variable 'SharedPreference'
        SharedPreferences sharedPreferences = getSharedPreferences("login", 0);
        /*
        // Develop mode
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
        */



        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        Intent intent = new Intent(this, SplashActivity.class);
        loginState = sharedPreferences.getString("USER_LOGIN", "LOGOUT");
        if(loginState.equals("LOGIN")){
            btn_join.setVisibility(View.GONE);
            btn_login.setText("Logout");
            GlobalApplication.getGlobalApplicationContext().setUserId(sharedPreferences.getString("USER_PHONE", "000-0000-0000"));
            GlobalApplication.getGlobalApplicationContext().setUserPassword(sharedPreferences.getString("USER_PASSWORD", "000000"));

            intent.putExtra("duration",  SPLASH_LOGIN);
        }else{
            btn_join.setVisibility(View.VISIBLE);


            intent.putExtra("duration",  SPLASH_LOGOUT);
        }
        btn_join.setOnClickListener(this);
        // Loading screen
        startActivity(intent);




        btn_reserve = (Button) findViewById(R.id.btn_reserve);
        btn_reserve.setOnClickListener(this);

        btn_temp = (Button) findViewById(R.id.btn_temp);
        btn_temp.setOnClickListener(this);

        btn_userInfo = (Button) findViewById(R.id.btn_userInfo);
        btn_userInfo.setOnClickListener(this);


        // if sharedPreferences.getString value is 0, assign 2th parameter
        Log.d(TAG, "SharedPreference");
        Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString("USER_LOGIN", "LOGOUT"));
        Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString("USER_PHONE", "000-0000-0000"));
        Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString("USER_PASSWORD", "000000"));
    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_next:{
                startActivity(new Intent(getApplicationContext(), SelectParentActivity.class));
                break;
            }
            case R.id.btn_invite:{
                startActivity(new Intent(getApplicationContext(), InviteActivity.class));
                break;
            }
            case R.id.btn_join:{
                // http://developljy.tistory.com/16
                startActivityForResult(new Intent(getApplicationContext(), AuthPhoneActivity.class), 0);
                break;
            }
            case R.id.btn_login:{
                Intent intent = new Intent(getApplicationContext(), LoginPhoneActivity.class);

                if(btn_login.getText().toString().equals("Login")){
                    startActivityForResult(intent, 0);
                }else{
                    startActivityForResult(intent, 1);
                }
                break;
            }
            case R.id.btn_reserve:{
                startActivity(new Intent(getApplicationContext(), ReserveActivity.class));
                break;
            }
            case R.id.btn_temp:{
                startActivity(new Intent(getApplicationContext(), DBTest.class));
                break;
            }
            case R.id.btn_userInfo:{
                startActivity(new Intent(getApplicationContext(), UserInfoActivity.class));
                break;
            }
            default:{
                break;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case RESULT_OK:{
                if(requestCode == 0){
                    btn_join.setVisibility(View.GONE);
                    btn_login.setText("Logout");
                }else if(requestCode == 1){
                    btn_join.setVisibility(View.VISIBLE);
                    btn_login.setText("Login");
                }

                break;
            }
            default:{

                break;
            }
        }
    }
    /*
    public void mOnClick(View view){
            switch (view.getId()){
                case R.id.btn_google:
                Toast.makeText(this, "접속합니다", Toast.LENGTH_SHORT).show();

                mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

                mGoogleApiClient.connect();

                break;
            }
        }

    @Override
    public void onConnected(Bundle bundle) {
                Log.d(TAG, "구글 플레이 연결이 되었습니다.");

                if (!mGoogleApiClient.isConnected() || Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) == null) {

                    Log.d(TAG, "onConnected 연결 실패");

                } else {
                    Log.d(TAG, "onConnected 연결 성공");

                    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

                    if (currentPerson.hasImage()) {

                        Log.d(TAG, "이미지 경로는 : " + currentPerson.getImage().getUrl());

                            Glide.with(MainActivity.this)
                                    .load(currentPerson.getImage().getUrl())
                                    .into(userphoto);

                    }
                    if (currentPerson.hasDisplayName()) {
                        Log.d(TAG,"디스플레이 이름 : "+ currentPerson.getDisplayName());
                        Log.d(TAG, "디스플레이 아이디는 : " + currentPerson.getId());
                    }

                }
            }

    @Override
    public void onConnectionSuspended(int i) {

            }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.d(TAG, "연결 에러 " + connectionResult);

                if (connectionResult.hasResolution()) {
                    Log.e(TAG,
                    String.format(
                    "Connection to Play Services Failed, error: %d, reason: %s",
                    connectionResult.getErrorCode(),
                    connectionResult.toString()));
                    try {
                        connectionResult.startResolutionForResult(this, 0);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, e.toString(), e);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "이미 로그인 중", Toast.LENGTH_SHORT).show();
                }
            }
    */


    /* Terminate application - alert dialog
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("TERMINATE");
        builder.setMessage("Do you really want?");
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    */
}

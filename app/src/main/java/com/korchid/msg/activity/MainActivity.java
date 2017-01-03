package com.korchid.msg.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.korchid.msg.global.QuickstartPreferences;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.global.GlobalApplication;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;
import com.korchid.msg.service.ServiceThread;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_PASSWORD;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{//implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private static final int SPLASH_LOGIN = 1000;
    private static final int SPLASH_LOGOUT = 1500;

    public static Activity activity;


    enum REQUEST_CODE {LOGIN, LOGOUT}

    private FirebaseAnalytics mFirebaseAnalytics;


    private Button btn_join;
    private Button btn_login;

    private Button btn_next;
    private Button btn_invite;

    private Button btn_reserve;
    private Button btn_temp;
    private Button btn_userInfo;

    private String userPhoneNumber = "";
    private String loginState = "";
    private String userRole = "";
    private int viewId;
    private int duration = 0;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save for terminating in next page
        activity = this;


        // Use Environmental variable 'SharedPreference'
        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

        initView();


        // TODO
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        FirebaseCrash.log("Error report test");

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        // [END subscribe_topics]

        // Log and toast
        String msg = getString(R.string.msg_subscribed);
        Log.d(TAG, msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        msg = getString(R.string.msg_token_fmt, token);
        Log.d(TAG, msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();


        final ServiceThread serviceThread = new ServiceThread(new Handler());
        //serviceThread.timer = "2016-12-13 14:55";
        serviceThread.start();

        //Log.d(TAG, "timer : " + serviceThread.timer);

        //serviceThread.timer = "2016-12-16 19:15";

        //Log.d(TAG, "timer : " + serviceThread.timer);

        int id=0;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        }
        else {
            id = extras.getInt("notificationId");
        }

        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        nm.cancel(id);

/*
        // if sharedPreferences.getString value is 0, assign 2th parameter
        Log.d(TAG, "SharedPreference");
        Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString(USER_LOGIN_STATE, "LOGOUT"));
        Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString(USER_PHONE_NUMBER, "000-0000-0000"));
        Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString(USER_PASSWORD, "000000"));
*/
        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

        userRole = sharedPreferences.getString(USER_ROLE, "");

        Log.d(TAG, "USER_ROLE : " + userRole);



    }

    private void initView(){
        // Modify UI
        StatusBar statusBar = new StatusBar(this);
        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_main, getResources().getString(R.string.app_name));

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        btn_invite = (Button) findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(this);

        btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);


        btn_reserve = (Button) findViewById(R.id.btn_reserve);
        btn_reserve.setOnClickListener(this);

        btn_temp = (Button) findViewById(R.id.btn_temp);
        btn_temp.setOnClickListener(this);

        btn_userInfo = (Button) findViewById(R.id.btn_userInfo);
        btn_userInfo.setOnClickListener(this);


/*
        // Develop mode
        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
*/

        userPhoneNumber = sharedPreferences.getString(USER_PHONE_NUMBER, "010-0000-0000");
        //Log.d(TAG, "USER phone number : " + userPhoneNumber);




        btn_next.setVisibility(View.GONE);

        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(USER_ID_NUMBER, sharedPreferences.getString(USER_ID_NUMBER , "0"));
        loginState = sharedPreferences.getString(USER_LOGIN_STATE, "LOGOUT");
        //Log.d(TAG, "Login state : " + loginState);
        if(loginState.equals("LOGIN")){
            // Current state : Login
            btn_join.setVisibility(View.GONE);
            btn_invite.setVisibility(View.VISIBLE);
            btn_reserve.setVisibility(View.VISIBLE);
            btn_temp.setVisibility(View.VISIBLE);
            btn_userInfo.setVisibility(View.VISIBLE);
            btn_login.setText("LOGOUT");

            btn_next.setVisibility(View.VISIBLE);

            intent.putExtra("duration",  SPLASH_LOGIN);
        }else{
            // Current state : Logout
            btn_join.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.GONE);
            btn_invite.setVisibility(View.GONE);
            btn_reserve.setVisibility(View.GONE);
            btn_temp.setVisibility(View.GONE);
            btn_userInfo.setVisibility(View.GONE);

            intent.putExtra("duration",  SPLASH_LOGOUT);
        }

        // Loading screen
        startActivity(intent);

    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_next:{
                Intent intent = new Intent(getApplicationContext(), SelectParentActivity.class);
                intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);
                intent.putExtra(USER_ROLE, userRole);
                startActivity(intent);
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
                intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);
                int requestCode;

                if(loginState.equals("LOGOUT")){
                    // If current state = logout
                    //Log.d(TAG, "Current state : Logout");
                    intent.putExtra(USER_LOGIN_STATE, "LOGOUT");
                    requestCode = 0;
                }else{
                    // If current state = login
                    //Log.d(TAG, "Current state : Login");
                    intent.putExtra(USER_LOGIN_STATE, "LOGIN");
                    requestCode = 1;
                }
/*
                if(btn_login.getText().toString().equals("LOGIN")){
                    // If current state = logout
                    //Log.d(TAG, "Current state : Logout");
                    intent.putExtra(USER_LOGIN_STATE, "LOGOUT");
                    requestCode = 0;
                }else{
                    // If current state = login
                    //Log.d(TAG, "Current state : Login");
                    intent.putExtra(USER_LOGIN_STATE, "LOGIN");
                    requestCode = 1;
                }
*/
                startActivityForResult(intent, requestCode);

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

                loginState = data.getStringExtra(USER_LOGIN_STATE);

                if(requestCode == 0){
                    // LOGIN success
                    btn_join.setVisibility(View.GONE);
                    btn_next.setVisibility(View.VISIBLE);
                    btn_login.setText("LOGOUT");

                    Log.d(TAG, "loginState : " + loginState);

                    btn_invite.setVisibility(View.VISIBLE);
                    btn_reserve.setVisibility(View.VISIBLE);
                    btn_temp.setVisibility(View.VISIBLE);
                    btn_userInfo.setVisibility(View.VISIBLE);
                }else if(requestCode == 1){
                    // LOGOUT success
                    btn_join.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.GONE);
                    btn_login.setText("LOGIN");
                    Log.d(TAG, "loginState : " + loginState);

                    btn_invite.setVisibility(View.GONE);
                    btn_reserve.setVisibility(View.GONE);
                    btn_temp.setVisibility(View.GONE);
                    btn_userInfo.setVisibility(View.GONE);
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

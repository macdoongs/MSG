package com.korchid.msg.main;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.korchid.msg.member.LoginPhoneActivity;
import com.korchid.msg.member.auth.AuthPhoneActivity;
import com.korchid.msg.member.SelectOpponentActivity;
import com.korchid.msg.member.invitation.InviteActivity;
import com.korchid.msg.storage.server.retrofit.ApiService;
import com.korchid.msg.storage.server.retrofit.RestfulAdapter;
import com.korchid.msg.firebase.fcm.MyFirebaseInstanceIDService;
import com.korchid.msg.member.chatting.mqtt.impl.MqttTopic;
import com.korchid.msg.member.chatting.mqtt.service.MqttService;
import com.korchid.msg.storage.server.retrofit.response.Upload;
import com.korchid.msg.storage.server.retrofit.response.UserMap;
import com.korchid.msg.R;
import com.korchid.msg.member.setting.user.UserInfoActivity;
import com.korchid.msg.storage.sqlite.DBTest;
import com.korchid.msg.ui.StatusBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.korchid.msg.global.QuickstartPreferences.MESSAGE_ALERT;
import static com.korchid.msg.global.QuickstartPreferences.OPPONENT_USER_ID;
import static com.korchid.msg.global.QuickstartPreferences.OPPONENT_USER_NICKNAME;
import static com.korchid.msg.global.QuickstartPreferences.OPPONENT_USER_PHONENUMBER;
import static com.korchid.msg.global.QuickstartPreferences.OPPONENT_USER_PROFILE;
import static com.korchid.msg.global.QuickstartPreferences.OPPONENT_USER_TOPICS;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_ALERT;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_ENABLE;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_TIMES;
import static com.korchid.msg.global.QuickstartPreferences.RESERVATION_WEEK_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_BIRTHDAY;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_NICKNAME;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_PROFILE;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_SEX;

/**
 * Created by mac0314 on 2016-11-28.
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    public static Activity activity;

    Intent serviceIntent;

    enum Request {JOIN, LOGIN, LOGOUT}

    private FirebaseAnalytics mFirebaseAnalytics;

    private ImageView iv_download;

    private Button btn_join;
    private Button btn_login;

    private Button btn_next;
    private Button btn_invite;

    private Button btn_temp;
    private Button btn_userInfo;

    private Button btn_upload;
    private Button btn_download;

    private String userPhoneNumber = "";

    private int userId = 0;

    private String userNickname = "";
    private String userSex = "";
    private Date userBirthday = new Date();
    private String userProfile = "";
    private String userRole = "";

    private int userRoleNumber = 0;

    private int userMessageAlert = 0;
    private int userReserveEnable = 0;
    private int userReserveAlert = 0;
    private int userWeekNumber = 0;
    private int userReserveNumber = 0;

    private static String loginState = "LOGOUT";

    private int viewId;

    private int duration = 0;

    private List<UserMap> userMapList = new ArrayList<>();

    private ArrayList<MqttTopic> topics = new ArrayList<MqttTopic>();

    private ArrayList<Integer> opponentUserId = new ArrayList<>();
    private ArrayList<String> opponentUserNickname = new ArrayList<>();
    private ArrayList<String> opponentUserPhoneNumber = new ArrayList<>();
    private ArrayList<String> opponentUserProfile = new ArrayList<>();
    private ArrayList<MqttTopic> mqttTopics = new ArrayList<>();

    SharedPreferences sharedPreferences;


    final int REQ_PICK_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save for terminating in next page
        activity = this;

        // TODO
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Use Environmental variable 'SharedPreference'
        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);




        SharedPreferences.Editor editor = sharedPreferences.edit();
/*
        // Develop mode -------------------------------------------------------------------------------
        // Clear SharedPreferences
        // TODO remove
        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_RESERVATION_SETTING, 0);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        sharedPreferences = getSharedPreferences(SHARED_PREF_CONNECTION, 0);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        // --------------------------------------------------------------------------------------------
*/
        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

        userPhoneNumber = sharedPreferences.getString(USER_PHONE_NUMBER, "010-0000-0000");
        //Log.d(TAG, "USER phone number : " + userPhoneNumber);
        loginState = sharedPreferences.getString(USER_LOGIN_STATE, "LOGOUT");
        userId = sharedPreferences.getInt(USER_ID_NUMBER , 0);



        sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

        userRoleNumber = sharedPreferences.getInt(USER_ROLE_NUMBER, 0);
        userRole = sharedPreferences.getString(USER_ROLE, "");
        Log.d(TAG, "USER_ROLE : " + userRole);


        initView();


        // Crash test
        //FirebaseCrash.report(new Exception("MSG Android non-fatal error"));
        //FirebaseCrash.log("Error report test");



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
        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

        MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();

        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "deviceToken : " + deviceToken);
/*
        // Test - Modify instanceId
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String deviceToken = FirebaseInstanceId.getInstance().getToken();


        Log.d(TAG, "1. Device Token : " + deviceToken);

        deviceToken = sharedPreferences.getString(USER_DEVICE_TOKEN, "");

        if(deviceToken.equals("")){

        }else{
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.d(TAG, "2. Device Token : " + deviceToken);
        }

        // Get token
        deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "3. Device Token : " + deviceToken);


        // Log and toast
        msg = getString(R.string.msg_token_fmt, deviceToken);
        Log.d(TAG, msg);
        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
*/
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


    }

    private void initView(){
        // Modify UI
        StatusBar statusBar = new StatusBar(this);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        btn_invite = (Button) findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(this);

        btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_temp = (Button) findViewById(R.id.btn_temp);
        btn_temp.setOnClickListener(this);

        btn_userInfo = (Button) findViewById(R.id.btn_userInfo);
        btn_userInfo.setOnClickListener(this);

        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);

        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);

        iv_download = (ImageView) findViewById(R.id.iv_download);

        Intent intent = new Intent(this, SplashActivity.class);


        int requestCode = 0;


        intent.putExtra(USER_ID_NUMBER, userId);
        intent.putExtra(USER_LOGIN_STATE, loginState);


        Log.d(TAG, "Login state : " + loginState);
        if(loginState.equals("LOGIN")){
            // Current state : Login
            btn_join.setVisibility(View.GONE);
            btn_invite.setVisibility(View.VISIBLE);
            btn_temp.setVisibility(View.VISIBLE);
            btn_userInfo.setVisibility(View.VISIBLE);
            btn_login.setText("LOGOUT");

            Log.d(TAG, "userRole : " + userRole);
            Log.d(TAG, "userRoleNumber : " + userRoleNumber);
            if(userRoleNumber == 0){
                btn_next.setVisibility(View.GONE);
            }else{
                loadingData(userId, userRole);
            }


            requestCode = 1;

        }else{
            // Current state : Logout
            btn_join.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.GONE);
            btn_invite.setVisibility(View.GONE);
            btn_temp.setVisibility(View.GONE);
            btn_userInfo.setVisibility(View.GONE);

            requestCode = 0;
/*
            //TODO Fix error
            try{
                if(isServiceRunningCheck()){
                    stopService(serviceIntent);
                }
            }catch (Exception e){
                e.getStackTrace();
            }
*/
        }

        // Start SplashActivity
        // Loading screen
        startActivityForResult(intent, requestCode);

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
                //TODO Fix error - After SplashActivity, userRole = null
                Log.d(TAG, "btn_next");
                Log.d(TAG, "userRole : " + userRole);
                // Temp data
                userRole = "child";
                Intent intent = new Intent(getApplicationContext(), SelectOpponentActivity.class);

                intent.putExtra(USER_ID_NUMBER, userId);
                intent.putExtra(USER_NICKNAME, userNickname);
                intent.putExtra(USER_SEX, userSex);
                intent.putExtra(USER_BIRTHDAY, userBirthday.getTime());
                intent.putExtra(USER_PROFILE, userProfile);
                intent.putExtra(USER_ROLE, userRole);
                intent.putExtra(MESSAGE_ALERT, userMessageAlert);
                intent.putExtra(RESERVATION_ENABLE, userReserveEnable);
                intent.putExtra(RESERVATION_ALERT, userMessageAlert);
                intent.putExtra(RESERVATION_WEEK_NUMBER, userWeekNumber);
                intent.putExtra(RESERVATION_TIMES, userReserveNumber);
                intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);

                intent.putExtra(OPPONENT_USER_ID, opponentUserId);
                intent.putExtra(OPPONENT_USER_NICKNAME, opponentUserNickname);
                intent.putExtra(OPPONENT_USER_PHONENUMBER, opponentUserPhoneNumber);
                intent.putExtra(OPPONENT_USER_PROFILE, opponentUserProfile);
                intent.putExtra(OPPONENT_USER_TOPICS, mqttTopics);

                startActivity(intent);
                break;
            }
            case R.id.btn_invite:{
                Intent intent = new Intent(getApplicationContext(), InviteActivity.class);

                intent.putExtra(USER_ID_NUMBER, userId);

                startActivityForResult(intent, 4);
                break;
            }
            case R.id.btn_join:{
                // http://developljy.tistory.com/16
                int requestCode = 0;

                startActivityForResult(new Intent(getApplicationContext(), AuthPhoneActivity.class), requestCode);
                break;
            }
            case R.id.btn_login:{
                // crash test
                //FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");
                //causeCrash();

                Intent intent = new Intent(getApplicationContext(), LoginPhoneActivity.class);
                int requestCode;

                if(loginState.equals("LOGOUT")){
                    // If current state = logout
                    //Log.d(TAG, "Current state : Logout");

                    intent.putExtra(USER_PHONE_NUMBER, userPhoneNumber);
                    intent.putExtra(USER_LOGIN_STATE, "LOGOUT");
                    requestCode = 2;
                    /*
                    // Temp data
                    ArrayList<String> topicArrayList = new ArrayList<>();

                    topicArrayList.add("Sajouiot03");
                    topicArrayList.add("Sajouiot02");
                    topicArrayList.add("Sajouiot01");

                    Boolean isExected = true;

                    serviceIntent = new Intent(getApplicationContext(), MqttService.class);
                    serviceIntent.putExtra("topic", topicArrayList);
                    serviceIntent.putExtra("mode", isExected);
                    //serviceIntent.putStringArrayListExtra("topic", topicArrayList);


                    startService(serviceIntent);
                    */
                }else{
                    // If current state = login
                    //Log.d(TAG, "Current state : Login");
                    intent.putExtra(USER_LOGIN_STATE, "LOGIN");
                    requestCode = 3;

                    /*
                    try{
                        if(isServiceRunningCheck()){
                            stopService(serviceIntent);
                        }
                    }catch (Exception e){
                        e.getStackTrace();
                    }
                    */
                }

                startActivityForResult(intent, requestCode);

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
            case R.id.btn_upload:{
                Intent pickerIntent = new Intent(Intent.ACTION_PICK);
                pickerIntent.setType("image/*");
                startActivityForResult(pickerIntent, REQ_PICK_CODE);
                break;
            }
            case R.id.btn_download:{
                String fileName = "her_logo.png";
                Call<ResponseBody> userDataCall = RestfulAdapter.getInstance().downloadFile(fileName);

                userDataCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "onResponse");
                        // display the image data in a ImageView or save it
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        iv_download.setImageBitmap(bm);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure");
                        Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                    }
                });
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

                switch (requestCode){
                    // SplashActivity
                    case 0:{
                        // Current state : logout

                        break;
                    }
                    // SplashActivity
                    case 1:{
                        // Current state : login

                        loginState = data.getStringExtra(USER_LOGIN_STATE);

                        userId = data.getIntExtra(USER_ID_NUMBER, 0);
                        userNickname = data.getStringExtra(USER_NICKNAME);
                        userSex = data.getStringExtra(USER_SEX);
                        userBirthday.setTime(data.getLongExtra(USER_NICKNAME, 0));
                        userProfile = data.getStringExtra(USER_PROFILE);
                        userRole = data.getStringExtra(USER_ROLE);
                        userMessageAlert = data.getIntExtra(MESSAGE_ALERT, 0);
                        userReserveEnable = data.getIntExtra(RESERVATION_ENABLE, 0);
                        userReserveAlert= data.getIntExtra(RESERVATION_ALERT, 0);
                        userWeekNumber = data.getIntExtra(RESERVATION_WEEK_NUMBER, 0);
                        userReserveNumber = data.getIntExtra(RESERVATION_TIMES, 0);

                        loadingData(userId, userRole);


                        // Check variables
                        Log.d(TAG, "loginState : " + loginState);

                        Log.d(TAG, "userId : " + userId);
                        Log.d(TAG, "userNickname : " + userNickname);
                        Log.d(TAG, "userSex : " + userSex);
                        Log.d(TAG, "userBirthday : " + userBirthday);
                        Log.d(TAG, "userProfile : " + userProfile);
                        Log.d(TAG, "userRole : " + userRole);
                        Log.d(TAG, "userMessageAlert : " + userMessageAlert);
                        Log.d(TAG, "userReserveEnable : " + userReserveEnable);
                        Log.d(TAG, "userReserveAlert : " + userReserveAlert);
                        Log.d(TAG, "userWeekNumber : " + userWeekNumber);
                        Log.d(TAG, "userReserveNumber : " + userReserveNumber);


                        break;
                    }
                    // LoginPhoneActivity
                    case 2:{
                        // LOGIN success
                        loadingData(userId, userRole);

                        loginState = data.getStringExtra(USER_LOGIN_STATE);

                        userPhoneNumber = data.getStringExtra(USER_PHONE_NUMBER);

                        btn_join.setVisibility(View.GONE);
                        btn_next.setVisibility(View.VISIBLE);
                        btn_login.setText("LOGOUT");

                        Log.d(TAG, "loginState : " + loginState);

                        btn_invite.setVisibility(View.VISIBLE);
                        btn_temp.setVisibility(View.VISIBLE);
                        btn_userInfo.setVisibility(View.VISIBLE);
/*
                        //TODO Fix error
                        //MqttService.mqttTopic = "Sajouiot03";
                        ArrayList<String> arrayList = new ArrayList<>();

                        arrayList.add("Sajouiot03");
                        arrayList.add("Sajouiot02");
                        arrayList.add("Sajouiot01");
*/
                        serviceIntent = new Intent(getApplicationContext(), MqttService.class);
                        serviceIntent.putExtra("topic", mqttTopics);

                        Boolean isExected = true;
                        serviceIntent.putExtra("mode", isExected);
                        startService(serviceIntent);

                        break;
                    }
                    // LoginPhoneActivity
                    case 3:{
                        // LOGOUT success

                        loginState = data.getStringExtra(USER_LOGIN_STATE);

                        btn_join.setVisibility(View.VISIBLE);
                        btn_next.setVisibility(View.GONE);
                        btn_login.setText("LOGIN");
                        Log.d(TAG, "loginState : " + loginState);

                        btn_invite.setVisibility(View.GONE);
                        btn_temp.setVisibility(View.GONE);
                        btn_userInfo.setVisibility(View.GONE);

                        //TODO Fix error
                        try {
                            if(isServiceRunningCheck()){
                                Log.d(TAG, "Current state : Running service");
                                stopService(serviceIntent);
                            }
                        }catch(Exception e){
                            e.getStackTrace();
                        }


                        break;
                    }
                    case REQ_PICK_CODE:{

                        Uri uri = data.getData();

                        uploadFile(uri);

                        File file = new File(uri.getPath());
                        // ImageView에 선택된 image를 설정
                        // mImageView.setImageURI(uri);
                        break;
                    }
                    // InviteActivity
                    case 4:{

                        userRole = data.getStringExtra(USER_ROLE);

                        btn_next.setVisibility(View.VISIBLE);

                        break;
                    }
                    default:{
                        break;
                    }

                } // End request code switch

                break;
            } // End Result_OK
            default:{

                break;
            }
        } // End result code switch
    }

    private void uploadFile(Uri fileUri) {

        String filePath = getRealPathFromUri(fileUri);
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {

                // creates RequestBody instance from file
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // MultipartBody.Part is used to send also the actual filename
                MultipartBody.Part body = MultipartBody.Part.createFormData("myfile", file.getName(), requestFile);
                // adds another part within the multipart request
                String descriptionString = "Sample description";
                RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
                // executes the request
                Call<Upload> call = RestfulAdapter.getInstance().uploadFile(body, description);
                call.enqueue(new Callback<Upload>() {
                    @Override
                    public void onResponse(Call<Upload> call, Response<Upload> response) {
                        Log.i(TAG, "success");
                        //Log.i(TAG, "response : " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Upload> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        }
    }

    public String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(this, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(this, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(this, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public void loadingData(int userId, String userRole){
        Log.d(TAG, "loadingData");
        Log.d(TAG, "userId : " + userId + ", userRole : " + userRole);
        // TODO Choose Role - branch


        // TODO Change temp data to variable userId, userRole
        Call<List<UserMap>> userMapCall = RestfulAdapter.getInstance().userMapping(16, "parent");

        userMapCall.enqueue(new Callback<List<UserMap>>() {
            @Override
            public void onResponse(Call<List<UserMap>> call, Response<List<UserMap>> response) {
                //Log.d(TAG, response.body().get(0).getNickname_sn());

                userMapList = response.body();
                //List<UserMap> mapList = response.body();


                for(int i=0; i < response.body().size(); i++){
                    UserMap userMap = response.body().get(i);

                    int userMapId = userMap.getUser_id();
                    String userMapNickname = userMap.getNickname_sn();
                    String userMapPhoneNumber = userMap.getPhone_number_sn();
                    String userMapProfile = userMap.getProfile_ln();
                    String userMapTopic = userMap.getTopic_mn();

                    Log.d(TAG, "userMap " + i + "th");
                    Log.d(TAG, "id" + userMapId);
                    Log.d(TAG, "nickname : " + userMapNickname);
                    Log.d(TAG, "phoneNumber : " + userMapPhoneNumber);
                    Log.d(TAG, "profile : " + userMapProfile);
                    Log.d(TAG, "topic : " + userMapTopic);

                    opponentUserId.add(i, userMapId);
                    opponentUserNickname.add(i, userMapNickname);
                    opponentUserPhoneNumber.add(i, userMapPhoneNumber);
                    opponentUserProfile.add(i, userMapProfile);

                    MqttTopic topic = new MqttTopic(userMapTopic);
                    mqttTopics.add(i, topic);
                }


                btn_next.setVisibility(View.VISIBLE);

                /*
                //TODO Fix error

                ArrayList<String> arrayList = new ArrayList<>();

                arrayList.add("Sajouiot03");
                arrayList.add("Sajouiot02");
                arrayList.add("Sajouiot01");

                serviceIntent = new Intent(getApplicationContext(), MqttService.class);
                serviceIntent.putExtra("topic", arrayList);
                Boolean isExected = true;
                serviceIntent.putExtra("mode", isExected);
                //serviceIntent.putStringArrayListExtra("topic", topicArrayList);


                startService(serviceIntent);
                */
            }

            @Override
            public void onFailure(Call<List<UserMap>> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.korchid.msg.member.chatting.mqtt.service.MqttService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    // Crash test
    private void causeCrash() {
        throw new NullPointerException("Fake null pointer exception");
    }
}

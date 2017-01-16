package com.korchid.msg.activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.suitebuilder.TestMethod;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.korchid.msg.Chatting;
import com.korchid.msg.adapter.ChattingAdapter;
import com.korchid.msg.alarm.AlarmMatchingBroadCastReceiver;
import com.korchid.msg.http.HttpPost;
import com.korchid.msg.sqlite.DBHelper;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.mqtt.MqttServiceDelegate;
import com.korchid.msg.mqtt.MqttServiceDelegate.MessageHandler;
import com.korchid.msg.mqtt.MqttServiceDelegate.MessageReceiver;
import com.korchid.msg.mqtt.MqttServiceDelegate.StatusHandler;
import com.korchid.msg.mqtt.MqttServiceDelegate.StatusReceiver;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;
import com.korchid.msg.mqtt.service.MqttService;
import com.korchid.msg.mqtt.service.MqttService.ConnectionStatus;

import org.json.JSONException;
import org.json.JSONObject;

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
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_NICKNAME;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_PROFILE;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;
import static com.korchid.msg.global.QuickstartPreferences.USER_SEX;


/**
 * Created by mac0314 on 2016-11-28.
 */

// Chatting between parent and child
public class ChattingActivity extends AppCompatActivity implements View.OnClickListener, MessageHandler, StatusHandler {
    private static final String TAG = "ChattingActivity";


    private Button btn_setting;
    private Button btn_menu;
    private Button btn_plus;
    private Button btn_send;


    private GridLayout expandedMenu;
    private ListView lv_message;

    private EditText et_message;

    private TextView tv_myMessage;
    private TextView tv_yourMessage;

    private Handler handler = new Handler();

    private MessageReceiver msgReceiver;
    private StatusReceiver statusReceiver;


    private String userPhoneNumber;

    private int userId;
    private String userNickname;
    private String userSex;
    private Date userBirthday = new Date();
    private String userProfile;
    private String userRole;
    private int userMessageAlert;
    private int userReserveEnable;
    private int userReserveAlert;
    private int userWeekNumber;
    private int userReserveNumber;


    private int opponentUserId;
    private String opponentUserNickname;
    private String opponentUserPhoneNumber;
    private String opponentUserProfile;
    private String mqttTopic;


    private Boolean expandedState = false;

    private ArrayList<Chatting> m_arr;
    private ChattingAdapter adapter;
    private static String chatMessage = new String();



    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MSG.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        // Cancel AlarmManager - reservation message
        int requestCode = 0;
        AlarmManager mManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmMatchingBroadCastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mManager.cancel(pi);

        //
        intent = getIntent();

        userId = getIntent().getIntExtra(USER_ID_NUMBER, 0);
        Log.d(TAG, "userId : " + userId);
        userNickname = getIntent().getStringExtra(USER_NICKNAME);
        userSex = getIntent().getStringExtra(USER_SEX);
        userBirthday.setTime(getIntent().getLongExtra(USER_NICKNAME, 0));
        userProfile = getIntent().getStringExtra(USER_PROFILE);
        userRole = getIntent().getStringExtra(USER_ROLE);
        userMessageAlert = getIntent().getIntExtra(MESSAGE_ALERT, 0);
        userReserveEnable = getIntent().getIntExtra(RESERVATION_ENABLE, 0);
        userReserveAlert= getIntent().getIntExtra(RESERVATION_ALERT, 0);
        userWeekNumber = getIntent().getIntExtra(RESERVATION_WEEK_NUMBER, 0);
        userReserveNumber = getIntent().getIntExtra(RESERVATION_TIMES, 0);

        userPhoneNumber = intent.getStringExtra(USER_PHONE_NUMBER);


        userProfile = null;


        //opponentUserNickname = intent.getStringExtra("parentName");
        opponentUserId = intent.getIntExtra(OPPONENT_USER_ID, 0);
        opponentUserNickname = intent.getStringExtra(OPPONENT_USER_NICKNAME);
        opponentUserPhoneNumber = intent.getStringExtra(OPPONENT_USER_PHONENUMBER);
        opponentUserProfile = intent.getStringExtra(OPPONENT_USER_PROFILE);
        mqttTopic = intent.getStringExtra(OPPONENT_USER_TOPICS);

        // Temp data
        opponentUserProfile = intent.getStringExtra("opponentProfile");

        m_arr = new ArrayList<Chatting>();

//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);
//        userNickname = sharedPreferences.getString(USER_NICKNAME, "");


        try {
            String storedMessage = dbHelper.getTableChatting(mqttTopic);
            Log.d(TAG, "result : " + storedMessage);
            String[] strArr = storedMessage.split("\n");
            for (int i = 0; i < strArr.length; i++) {
                String[] stringSplit = strArr[i].split(" / ");
                int id = Integer.parseInt(stringSplit[0]);
                int senderId = Integer.parseInt(stringSplit[1]);
                String senderNickname = stringSplit[2];
                int receiverId = Integer.parseInt(stringSplit[3]);
                String topic = stringSplit[4];
                String type = stringSplit[5];
                String content = stringSplit[6];
                Chatting.Type messageType = Chatting.Type.MESSAGE;
                if(type.equals("message")){
                    messageType = Chatting.Type.MESSAGE;
                }
                Log.d(TAG, "Chatting : " + senderId + "/" + senderNickname + "/" + receiverId + "/" + mqttTopic + "/" +content);

                m_arr.add(new Chatting(senderId, receiverId, senderNickname, messageType, content));
            }
        }catch (Exception e){
            Log.d(TAG, "Error : " + e.getMessage());
        }

        initView();

        Log.d(TAG, "Topic : " + mqttTopic);



        // Register mqtt topic - Web server
        String url = "https://www.korchid.com/msg/user/chatting/topic/subscription";

        HashMap<String, String> params = new HashMap<>();
        params.put("topic", mqttTopic);


        HttpPost httpPost = new HttpPost(url, params, new Handler());
        httpPost.start();

        //Init Receivers
        bindStatusReceiver();
        bindMessageReceiver();

        //MqttServiceDelegate.topic = mqttTopic;



        //Start service if not started
        MqttServiceDelegate.startService(this, mqttTopic);


    }



    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, opponentUserNickname);

        btn_plus = (Button)findViewById(R.id.btn_plus);


        lv_message = (ListView)findViewById(R.id.lv_message);


        et_message = (EditText)findViewById(R.id.et_message);

        btn_send = (Button)findViewById(R.id.btn_send);

        expandedMenu = (GridLayout) findViewById(R.id.expandedMenu);



        btn_send.setOnClickListener(this);
        btn_plus.setOnClickListener(this);

        btn_send.setEnabled(false);

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

                if(input.length() > 0){
                    btn_send.setEnabled(true);
                    btn_send.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    btn_send.setEnabled(false);
                    btn_send.setTextColor(getResources().getColor(R.color.colorTransparent));
                }
            }
        };

        et_message.addTextChangedListener(textWatcher);

        adapter = new ChattingAdapter(ChattingActivity.this, m_arr, userId, opponentUserProfile);
        lv_message.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId){
            case R.id.btn_send:{
                String message = et_message.getText().toString();

                // MQTT message - JSON format
                // http://humble.tistory.com/20

                Log.d(TAG, "btn_send");

                //Test data
                int receiverId = opponentUserId;
                String messageType = "message";

                JSONObject obj = new JSONObject();
                try {
                    obj.put("senderId", userId);
                    obj.put("receiverId", receiverId);
                    obj.put("senderNickname", userNickname);
                    obj.put("topic", mqttTopic);
                    obj.put("messageType", messageType);
                    obj.put("message", message);

                    System.out.println(obj.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO modify topic
                String topic = mqttTopic;

                MqttServiceDelegate.publish(
                        ChattingActivity.this,
                        topic,
                        //message.getBytes()
                        obj.toString().getBytes()
                );

                et_message.setText("");
                break;
            }
            case R.id.btn_plus:{
                //Toast.makeText(getApplicationContext(), "Plus button", Toast.LENGTH_LONG).show();

                if(expandedState){
                    expandedMenu.bringToFront();
                    expandedMenu.setVisibility(View.GONE);
                    expandedState = false;
                }else{
                    expandedMenu.bringToFront();
                    expandedMenu.setVisibility(View.VISIBLE);
                    expandedState = true;
                }
                break;
            }
            default:{
                break;
            }

        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리

        int id = item.getItemId();

        switch (id){
            case android.R.id.home: {
                this.finish();
                break;
            }
            case R.id.itemLogo: {

                break;
            }
            case R.id.itemShare:{

                break;
            }
            case R.id.itemSetting: {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                break;
            }
            default: {
                break;
            }
        }

        return true;
    }

    //비트맵의 byte배열을 얻는다
    public byte[] getImageByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        return out.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        super.onBackPressed();
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
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG, "onDestroy");

        MqttServiceDelegate.stopService(this);

        unbindMessageReceiver();
        unbindStatusReceiver();

        // Register mqtt topic - Web server
        String url = "https://www.korchid.com/msg/user/chatting/topic/unsubscription";

        HashMap<String, String> params = new HashMap<>();
        params.put("topic", mqttTopic);


        HttpPost httpPost = new HttpPost(url, params, new Handler());
        httpPost.start();


        super.onDestroy();
    }

    private void bindMessageReceiver(){
        Log.d(TAG, "bindMessageReceiver");
        msgReceiver = new MessageReceiver();
        msgReceiver.registerHandler(this);
        registerReceiver(msgReceiver,
                new IntentFilter(MqttService.MQTT_MSG_RECEIVED_INTENT));
    }

    private void unbindMessageReceiver(){
        Log.d(TAG, "unbindMessageReceiver");
        if(msgReceiver != null){
            msgReceiver.unregisterHandler(this);
            unregisterReceiver(msgReceiver);
            msgReceiver = null;
        }
    }

    private void bindStatusReceiver(){
        Log.d(TAG, "bindStatusReceiver");
        statusReceiver = new StatusReceiver();
        statusReceiver.registerHandler(this);
        registerReceiver(statusReceiver,
                new IntentFilter(MqttService.MQTT_STATUS_INTENT));
    }

    private void unbindStatusReceiver(){
        Log.d(TAG, "unbindStatusReceiver");
        if(statusReceiver != null){
            statusReceiver.unregisterHandler(this);
            unregisterReceiver(statusReceiver);
            statusReceiver = null;
        }
    }

    private String getCurrentTimestamp(){
        return new Timestamp(new Date().getTime()).toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        //MqttServiceDelegate.topic = mqttTopic;

        //Start service if not started
        //MqttServiceDelegate.startService(this, mqttTopic);
    }

    @Override
    public void handleMessage(String topic, byte[] payload) {
        Log.d(TAG, "handleMessage");

        String data = new String(payload);


        int senderId = 0;
        int receiverId = 0;
        String senderNickname = "";
        String messageType = "";
        Chatting.Type type;
        String message = "";

        try {
            JSONObject jsonObject = new JSONObject(data);

            senderId = jsonObject.getInt("senderId");
            receiverId = jsonObject.getInt("receiverId");
            senderNickname = jsonObject.getString("senderNickname");
            messageType = jsonObject.getString("messageType");
            message = jsonObject.getString("message");

            if(messageType.equals("message")){
                type = Chatting.Type.MESSAGE;
            }else{
                type = Chatting.Type.IMAGE;
            }

        }catch (Exception e){
            e.getStackTrace();
        }

        Log.d(TAG, data);

        if(!topic.equals(mqttTopic)){
            return;
        }

        if(data != null){
            // Test data
            type = Chatting.Type.MESSAGE;

            m_arr.add(new Chatting(senderId, receiverId,  senderNickname, type, message));

            lv_message.setSelection(adapter.getCount()-1);
        }

    }

    public void listUpdate(){

        adapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.

    }

    @Override
    public void handleStatus(ConnectionStatus status, String reason) {
        Log.d(TAG, "handleStatus: status = " + status + ", reason = " + reason);
    }

}

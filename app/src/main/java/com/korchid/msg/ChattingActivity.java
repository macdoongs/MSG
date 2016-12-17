package com.korchid.msg;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.korchid.msg.MqttServiceDelegate.MessageHandler;
import com.korchid.msg.MqttServiceDelegate.MessageReceiver;
import com.korchid.msg.MqttServiceDelegate.StatusHandler;
import com.korchid.msg.MqttServiceDelegate.StatusReceiver;
import com.korchid.msg.service.MqttService;
import com.korchid.msg.service.MqttService.ConnectionStatus;


/**
 * Created by mac0314 on 2016-11-28.
 */

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener, MessageHandler, StatusHandler {
    private static final String TAG = "ChattingActivity";

    private Button btn_setting;
    private Button btn_menu;
    private Button btn_plus;
    private Button btn_send;


    private LinearLayout slidingPanel;
    private GridLayout expandedMenu;
    private ListView lv_message;
    private ImageView iv_profile;

    private EditText et_message;

    private Handler handler = new Handler();

    private MessageReceiver msgReceiver;
    private StatusReceiver statusReceiver;

    private String nickname;
    private String count;
    private String title;
    private String message1;
    private int viewId;
    byte[] pic;



    private Boolean slidingState = false;
    private Boolean expandedState = false;
    private Animation aleft;
    private Animation bleft;

    private ArrayList<Chatting> m_arr;
    private ChattingAdapter adapter;
    private static String chatMessage = new String();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content);

        Log.d(TAG, "onCreate");

        aleft = AnimationUtils.loadAnimation(this, R.anim.aleft);
        bleft = AnimationUtils.loadAnimation(this, R.anim.bleft);

        expandedMenu = (GridLayout) findViewById(R.id.expandedMenu);


        Intent intent = getIntent();
        pic = null;
        count = "";
        nickname = "Me";
        title = intent.getStringExtra("topic");
        m_arr = new ArrayList<Chatting>();

        Log.d(TAG, "Topic : " + title);

        //Toast.makeText(getApplicationContext(), "Topic : " + title, Toast.LENGTH_LONG).show();


        btn_plus = (Button)findViewById(R.id.btn_plus);
        iv_profile = (ImageView)findViewById(R.id.iv_profile);

        try {
            Uri uri = GlobalApplication.getGlobalApplicationContext().getProfileImage();

            Log.d(TAG, "Uri : " + uri);
            if(uri != null){
                iv_profile.setImageURI(GlobalApplication.getGlobalApplicationContext().getProfileImage());
            }

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        lv_message = (ListView)findViewById(R.id.lv_message);

        et_message = (EditText)findViewById(R.id.et_message);
        btn_send = (Button)findViewById(R.id.btn_send);

        adapter = new ChattingAdapter(ChattingActivity.this, m_arr);
        lv_message.setAdapter(adapter);

/*
        // TODO Send Image and message
        btn_temp = (Button) findViewById(R.id.btn_temp);
        btn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";

                //이미지를 불려온다
                byte[] data = getImageByte(BitmapFactory.decodeResource(getResources(), R.drawable.logo));

                message1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:rqp xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><op>1</op><to>/mobius-yt/"+title+"/chatting</to><fr>S0.2.481.1.20160721051547725</fr><rqi>rqi201607211554337900rzY</rqi><ty>4</ty><pc><cin><rn></rn><con>";

                String message2 = nickname+": "+ data.toString();
                Log.d(TAG, "Message : " + message2);
                String message3 = "</con></cin></pc></m2m:rqp>";

                message = message1 + message2 + message3;

                String topic = "/oneM2M/req/"+ title +"/:mobius-yt/xml";

                MqttServiceDelegate.publish(
                        ChattingActivity.this,
                        topic,
                        //publishEditView.getText().toString().getBytes()
                        message.getBytes()
                );

            }
        });
*/


        //Init Receivers
        bindStatusReceiver();
        bindMessageReceiver();

        //MqttServiceDelegate.topic = title;

        //Start service if not started
        MqttServiceDelegate.startService(this, title);

        btn_send.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        btn_menu.setOnClickListener(this);
        btn_plus.setOnClickListener(this);

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_send:{
                String message = "";

                message1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:rqp xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><op>1</op><to>/mobius-yt/"+title+"/chatting</to><fr>S0.2.481.1.20160721051547725</fr><rqi>rqi201607211554337900rzY</rqi><ty>4</ty><pc><cin><rn></rn><con>";

                String message2 = nickname+": "+ et_message.getText().toString();
                String message3 = "</con></cin></pc></m2m:rqp>";

                message = message1 + message2 + message3;

                String topic = "/oneM2M/req/"+ title +"/:mobius-yt/xml";

                MqttServiceDelegate.publish(
                        ChattingActivity.this,
                        topic,
                        //publishEditView.getText().toString().getBytes()
                        message.getBytes()
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
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
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
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if(slidingState == true){
            slidingPanel.bringToFront();
            slidingPanel.startAnimation(bleft);
            slidingPanel.setVisibility(View.GONE);
            slidingState = false;
        }else{
            super.onBackPressed();
        }
    }

    public class MobiusConfig{
        public final static String MOBIUS_ROOT_URL = "http://52.78.68.226:7579/mobius-yt";
    }

    public interface IReceived{
        void getResponseBody(String msg);
    }

    class RequestProfile extends Thread {
        Handler handler;

        @Override
        public void run() {
            StringBuilder stringBuilder = new StringBuilder();
            String urlStr = "https://www.korchid.com/images/app_icon.jpg";


            try {

            }catch (Exception e){

            }
        }
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG, "onDestroy");

        MqttServiceDelegate.stopService(this);

        unbindMessageReceiver();
        unbindStatusReceiver();

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
        //MqttServiceDelegate.topic = title;

        //Start service if not started
        MqttServiceDelegate.startService(this, title);
    }

    @Override
    public void handleMessage(String topic, byte[] payload) {
        Log.d(TAG, "handleMessage");
        String message = new String(payload);
        String name = "";

        String changingString = "";
        String changedString="";
        int start;
        int end;
        changingString = message;

        String roomTopic = "/oneM2M/req/"+ title +"/:mobius-yt/xml";


        Log.d(TAG, topic);
        Log.d(TAG, message);

        String splitedString[] = changingString.split("</con>");

        if(!topic.equals(roomTopic)){
            return;
        }

        int i = -1;
        while(true){
            i++;
            try{
                start = splitedString[i].indexOf("<con>");
                start = start+5;

                if(!splitedString[i].substring(splitedString[i].length()-9,splitedString[i].length()-1).equals("Instance")){
                    changedString += splitedString[i].substring(start, splitedString[i].length());
                    //    Toast.makeText(getApplicationContext(), splitedString[i].substring(splitedString[i].length()-9,splitedString[i].length()-1),Toast.LENGTH_LONG).show();
                }

                start = changingString.indexOf("<con>");
                start = start+5;

                end = changingString.indexOf("</con>");
                changedString = changingString.substring(start, end);
                if(!changedString.equals("l version=\"1.0\" encoding=\"UTF-8\"?>")){
                    chatMessage = changedString;
                    /*String[] ReturnList = changedString.split(":");

                    name = ReturnList[0];
                    chatMessage = ReturnList[1];*/

                }
            }
            catch(Exception e){
                Log.e(TAG, e.getMessage());

                break;
            }

        }

        if(chatMessage != null){

            if(!changedString.equals("l version=\"1.0\" encoding=\"UTF-8\"?>")){
                m_arr.add(new Chatting(nickname, chatMessage));
            }

            chatMessage="";
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

package com.korchid.msg;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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

public class ChattingActivity extends AppCompatActivity implements MessageHandler, StatusHandler {
    private Button setting;
    private Button menu;
    private Button plus;

    private Handler handler = new Handler();

    String nickname;
    String count;
    String title;
    String message1;
    byte[] pic;

    private MessageReceiver msgReceiver;
    private StatusReceiver statusReceiver;

    Boolean slidingState = false;
    Boolean expandedState = false;
    Animation aleft;
    Animation bleft;

    LinearLayout slidingPanel;
    GridLayout expandedMenu;
    ListView messageView;
    ImageView imageView;

    private EditText publishEditView;
    private Button publishButton;
    private ImageView profileImage;
    private ArrayList<Chatting> m_arr;
    private ChattingAdapter adapter;
    private static String chatMessage = new String();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);


        aleft = AnimationUtils.loadAnimation(this, R.anim.aleft);
        bleft = AnimationUtils.loadAnimation(this, R.anim.bleft);

        slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
        expandedMenu = (GridLayout) findViewById(R.id.expandedMenu);
        profileImage = (ImageView) findViewById(R.id.imageView);


        Intent intent = getIntent();
//        pic = data.getByteArrayExtra("pic");
//        nickname = data.getStringExtra("NICK");
//        title = data.getStringExtra("title");
//        count = data.getStringExtra("EXPLAIN");
        pic = null;
        count = "";
        nickname = "Me";
        title = intent.getStringExtra("topic");
        m_arr = new ArrayList<Chatting>();

        //Toast.makeText(getApplicationContext(), "Topic : " + title, Toast.LENGTH_LONG).show();


        setting = (Button)findViewById(R.id.setting);
        menu = (Button)findViewById(R.id.menu);
        plus = (Button)findViewById(R.id.plus);
        imageView = (ImageView)findViewById(R.id.imageView3);

        messageView = (ListView)findViewById(R.id.messageView);

        publishEditView = (EditText)findViewById(R.id.publishEditView);
        publishButton = (Button)findViewById(R.id.publishButton);

        adapter = new ChattingAdapter(ChattingActivity.this, m_arr);
        messageView.setAdapter(adapter);



        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";

                message1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:rqp xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><op>1</op><to>/mobius-yt/"+title+"/chatting</to><fr>S0.2.481.1.20160721051547725</fr><rqi>rqi201607211554337900rzY</rqi><ty>4</ty><pc><cin><rn></rn><con>";

                String message2 = nickname+": "+publishEditView.getText().toString();
                String message3 = "</con></cin></pc></m2m:rqp>";

                message = message1 + message2 + message3;

                String topic = "/oneM2M/req/"+ title +"/:mobius-yt/xml";

                MqttServiceDelegate.publish(
                        ChattingActivity.this,
                        topic,
                        //publishEditView.getText().toString().getBytes()
                        message.getBytes()
                );

                publishEditView.setText("");
            }
        });

        //Init Receivers
        bindStatusReceiver();
        bindMessageReceiver();

        //Start service if not started
        MqttServiceDelegate.startService(this);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Click setting", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Sliding menu", Toast.LENGTH_LONG).show();
                slidingPanel.bringToFront();
                slidingPanel.setVisibility(View.VISIBLE);
                slidingPanel.startAnimation(aleft);
                slidingState = true;
            }
        });

        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
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

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
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


//    class RetrieveRequest extends Thread {
//
//        //private final Logger LOG = Logger.getLogger(RetrieveRequest.class.getName());
//
//        private IReceived receiver;
//
//
//        private String ae_name = "Sajouiot03"; //change to your ae name
//        private String container_name = "arduino"; //change to your sensing data container name
//
//        public RetrieveRequest(String aeName, String containerName){
//            this.ae_name = aeName;
//            this.container_name = containerName;
//        }
//
//        public RetrieveRequest(){
//
//        }
//
//        public void setReceiver(IReceived hanlder){
//            this.receiver = hanlder;
//        }
//
//        @Override
//        public void run() {
//            try{
//
//                StringBuilder sb = new StringBuilder();
//                sb.append(MobiusConfig.MOBIUS_ROOT_URL).append("/")
//                        .append(ae_name).append("/")
//                        .append(container_name).append("/")
//                        .append("latest");
//
//                URL mUrl = new URL(sb.toString());
//
//                HttpURLConnection conn = (HttpURLConnection)mUrl.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                conn.setDoOutput(false);
//
//                conn.setRequestProperty("Accept", "application/xml");
//                conn.setRequestProperty("X-M2M-RI", "12345");
//                conn.setRequestProperty("X-M2M-Origin", "SOrigin");
//                conn.setRequestProperty("nmtype", "long");
//
//                conn.connect();
//
//                String strResp = "";
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String strLine;
//                while((strLine = in.readLine()) != null) {
//                    strResp += strLine;
//                }
//
//                if(receiver != null){
//                    receiver.getResponseBody(strResp);
//                }
//                conn.disconnect();
//
//            }catch(Exception exp){
//                //LOG.log(Level.WARNING, exp.getMessage());
//            }
//        }
//    }

    @Override
    protected void onDestroy()
    {
        //LOG.debug("onDestroy");

        MqttServiceDelegate.stopService(this);

        unbindMessageReceiver();
        unbindStatusReceiver();

        super.onDestroy();
    }

    private void bindMessageReceiver(){
        msgReceiver = new MessageReceiver();
        msgReceiver.registerHandler(this);
        registerReceiver(msgReceiver,
                new IntentFilter(MqttService.MQTT_MSG_RECEIVED_INTENT));
    }

    private void unbindMessageReceiver(){
        if(msgReceiver != null){
            msgReceiver.unregisterHandler(this);
            unregisterReceiver(msgReceiver);
            msgReceiver = null;
        }
    }

    private void bindStatusReceiver(){
        statusReceiver = new StatusReceiver();
        statusReceiver.registerHandler(this);
        registerReceiver(statusReceiver,
                new IntentFilter(MqttService.MQTT_STATUS_INTENT));
    }

    private void unbindStatusReceiver(){
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
    public void handleMessage(String topic, byte[] payload) {
        String message = new String(payload);
        String name = "";

        String changingString = "";
        String changedString="";
        int start;
        int end;
        changingString = message;

        String splitedString[] = changingString.split("</con>");

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
            catch(Exception e){break;}

        }

        if(chatMessage != null){

            if(!changedString.equals("l version=\"1.0\" encoding=\"UTF-8\"?>")){
                m_arr.add(new Chatting(nickname, chatMessage));
            }

            chatMessage="";
            messageView.setSelection(adapter.getCount()-1);
        }



    }
    //lv.setDivider(null); 구분선을 없에고 싶으면 null 값을 set합니다.

    //lv.setDividerHeight(5); 구분선의 굵기를 좀 더 크게 하고싶으면 숫자로 높이 지정


    public void listUpdate(){

        adapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.

    }



    @Override
    public void handleStatus(ConnectionStatus status, String reason) {
        //	LOG.debug("handleStatus: status="+status+", reason="+reason);
    }

}

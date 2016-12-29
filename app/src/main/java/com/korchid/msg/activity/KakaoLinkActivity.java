package com.korchid.msg.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.korchid.msg.http.HttpGet;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

// Wait connecting parent and send kakao link
public class KakaoLinkActivity extends AppCompatActivity {
    private static final String TAG = "KakaoLinkActivity";

    private TextView tv_waitTime;
    private TextView tv_parentName;
    private TextView tv_parentPhoneNumber;

    private Button btn_kakaoLink;

    private Timer inviteTimer;
    private TimerTask inviteTask;

    private long inviteTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_link);


        String choosingId = "5";
        String receiverPhoneNumber = getIntent().getStringExtra("receiverPhoneNumber");

        initView();

        String strUrl = "https://www.korchid.com/msg-wait-connection/" + choosingId + "/" + receiverPhoneNumber;
        Handler httpHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String response = msg.getData().getString("response");

                String[] line = response.split("\n");

                //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                if(line[0].equals("Error")){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                }else{
                    Log.d(TAG, "line : " + line[0]);

                    String[] section = line[0].split("/");
                    String invitingId = section[0];
                    String isConnection = section[1];
                    String strInviteTime = section[2];


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(strInviteTime);

                        Log.d(TAG, "Date : " + date);

                        inviteTime = date.getTime();
                        Log.d(TAG, "inviteTime : " + inviteTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    final long waitTime = inviteTime + 86400000; // plus 1 day

                    final Handler handler = new Handler();

                    if(inviteTimer != null){
                        inviteTimer.cancel();
                    }

                    inviteTask = new TimerTask() {
                        @Override
                        public void run() {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    long currentTime = (waitTime - System.currentTimeMillis()) / 1000;
                                    if(currentTime >= 0) {
                                        long hour = currentTime / (60 * 60);
                                        long min = (currentTime - hour * 60 * 60) / 60;
                                        long sec = currentTime - hour * 60 * 60 - min * 60;


                                        String time = "" + hour + ":" + min + ":" + sec;
                                        Log.d(TAG, "time : " + time);
                                        tv_waitTime.setText(time);
                                        tv_waitTime.setTextColor(Color.BLUE);
                                        tv_waitTime.setTextSize(20.0f);
                                    }
                                }
                            });
                        }
                    };

                    inviteTimer = new Timer();
                    inviteTimer.schedule(inviteTask, 1000, 1000);

                }
            }
        };
        HttpGet httpGet = new HttpGet(strUrl, httpHandler);
        httpGet.start();




    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "초대 기다리기");

        tv_waitTime = (TextView) findViewById(R.id.tv_waitTime);
        tv_parentName = (TextView) findViewById(R.id.tv_parentName);
        tv_parentPhoneNumber = (TextView) findViewById(R.id.tv_parentPhoneNumber);

        btn_kakaoLink = (Button) findViewById(R.id.btn_kakaoLink);
        btn_kakaoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    // https://developers.kakao.com/docs/android#Kakao-계정-로그인이-필요없는-앱설정

                    KakaoLink kakaoLink = KakaoLink.getKakaoLink(getApplicationContext());

                    KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();


                    // Kakao text message
                    String text = "http://www.korchid.com/dropbox-release";

                    kakaoTalkLinkMessageBuilder.addText(text);

                    // Kakao image
                        /*
                        String imageSrc = [원하는 Image가 위치한 Url]
                        int width = [이미지가 display될 가로 크기]
                        int height = [이미지가 display될 세로 크기]
                        kakaoTalkLinkMessageBuilder.addImage(imageSrc, width, height);
                        */
//
//                    // Kakao app link
//                    kakaoTalkLinkMessageBuilder.addAppLink("MSG 앱 다운로드", new AppActionBuilder().setUrl("https://www.dropbox.com/sh/6km9zercq8c87yx/AAA73VJNraeRjrcNUheG9mj3a?dl=0").build()); // PC 카카오톡 에서 사용하게 될 웹사이트 주소

                    // Kakao web link
                    //kakaoTalkLinkMessageBuilder.addWebLink("MSG 앱 다운로드", "http://www.korchid.com/dropbox-release");
                    //kakaoTalkLinkMessageBuilder.addWebLink("MSG 앱 다운로드", "market://details?id=com.korchid.msg");

                    kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, KakaoLinkActivity.this);
                }catch (Exception e){
                    Log.e(TAG, "kakaoLink Err : " + e.getMessage());
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        inviteTimer.cancel();
        super.onDestroy();
    }
}

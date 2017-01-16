package com.korchid.msg.member.invitation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.korchid.msg.storage.server.retrofit.RestfulAdapter;
import com.korchid.msg.storage.server.retrofit.response.Res;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;

// Wait connecting parent and send kakao link
public class KakaoLinkActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "KakaoLinkActivity";

    private GridLayout expandedMenu;

    private TextView tv_waitTime;
    private TextView tv_opponentNickname;
    private TextView tv_opponentPhoneNumber;

    private Button btn_sendSMS;
    private Button btn_kakaoLink;
    private Button btn_send;
    private Button btn_confirm;

    private Timer inviteTimer;
    private TimerTask inviteTask;

    private Boolean expandedState = false;

    int userId;
    String userRole = "";
    String receiverNickname = "";
    String receiverPhoneNumber = "";
    private long inviteTime = 0;
    private String strInviteTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_link);


        userId = getIntent().getIntExtra(USER_ID_NUMBER, 0);
        userRole = getIntent().getStringExtra(USER_ROLE);
        receiverNickname = getIntent().getStringExtra("receiverNickname");
        receiverPhoneNumber = getIntent().getStringExtra("receiverPhoneNumber");
        strInviteTime = getIntent().getStringExtra("waitTime");

        initView();

        tv_opponentNickname.setText(receiverNickname);
        tv_opponentPhoneNumber.setText(receiverPhoneNumber);

        Call<Res> userCall = RestfulAdapter.getInstance().userInvitation(userId, receiverPhoneNumber, userRole);

        userCall.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                Res res = response.body();

                if(res.getAffectedRows() == 1){
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

            @Override
            public void onFailure(Call<Res> call, Throwable t) {
                Log.d(TAG, "onFailure");

                Toast.makeText(getApplicationContext(), "Please check your id and password", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "초대 기다리기");

        tv_waitTime = (TextView) findViewById(R.id.tv_waitTime);
        tv_opponentNickname = (TextView) findViewById(R.id.tv_parentName);
        tv_opponentPhoneNumber = (TextView) findViewById(R.id.tv_parentPhoneNumber);

        expandedMenu = (GridLayout) findViewById(R.id.expandedMenu);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        btn_sendSMS = (Button) findViewById(R.id.btn_sendSMS);
        btn_sendSMS.setOnClickListener(this);

        btn_kakaoLink = (Button) findViewById(R.id.btn_kakaoLink);
        btn_kakaoLink.setOnClickListener(this);

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        if(inviteTimer != null){
            inviteTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId){
            case R.id.btn_send:{
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
            case R.id.btn_sendSMS:{
                String message = "혜윰 초대해요~ 연락 자주하고 싶어요!! http://www.korchid.com/dropbox-release";

                /*
                // Temp
                // SMS Compose
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + opponentUserPhoneNumber));
                intent.putExtra("sms_body", message);
                startActivityForResult(intent, 1);
                */

                // TODO convert function
                // SMS Auto-sender
                //Intent intent;
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(receiverPhoneNumber, null, message, null, null);

                break;
            }
            case R.id.btn_kakaoLink:{
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
                break;
            }
            case R.id.btn_confirm:{
                Intent intent = new Intent();

                intent.putExtra(USER_ROLE, userRole);
                intent.putExtra("receiverNickname", receiverNickname);
                intent.putExtra("receiverPhoneNumber", receiverPhoneNumber);

                setResult(RESULT_OK, intent);

                finish();

                break;
            }
            default:{
                break;
            }
        }
    }
}

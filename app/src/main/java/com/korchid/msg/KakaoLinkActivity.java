package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

public class KakaoLinkActivity extends AppCompatActivity {
    private static final String TAG = "KakaoLinkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_link);

        StatusBar statusBar = new StatusBar(this);

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

            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this);
        }catch (Exception e){
            Log.e(TAG, "kakaoLink Err : " + e.getMessage());
        }

        finish();
    }
}

package com.korchid.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Mac on 2016-12-01.
 */

public class SMSReceiver  extends BroadcastReceiver{

    // 지정한 특정 액션이 일어나면 수행되는 메서드
    @Override
    public void onReceive(Context context, Intent intent) {

        // SMS를 받았을 경우에만 반응하도록 if문을 삽입
        if (intent.getAction().equals(
                "android.provider.Telephony.SMS_RECEIVED")) {
            StringBuilder sms = new StringBuilder();    // SMS문자를 저장할 곳
            Bundle bundle = intent.getExtras();         // Bundle객체에 문자를 받아온다

            if (bundle != null) {
                // 번들에 포함된 문자 데이터를 객체 배열로 받아온다
                Object[] pdusObj = (Object[]) bundle.get("pdus");

                // SMS를 받아올 SmsMessage 배열을 만든다
                SmsMessage[] messages = new SmsMessage[pdusObj.length];
                for (int i = 0; i < pdusObj.length; i++) {
                    messages[i] =
                            SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    // SmsMessage의 static메서드인 createFromPdu로 pdusObj의
                    // 데이터를 message에 담는다
                    // 이 때 pdusObj는 byte배열로 형변환을 해줘야 함
                }

                // SmsMessage배열에 담긴 데이터를 append메서드로 sms에 저장
                for (SmsMessage smsMessage : messages) {
                    // getMessageBody메서드는 문자 본문을 받아오는 메서드
                    sms.append(smsMessage.getMessageBody());
                }

                sms.toString(); // StringBuilder 객체 sms를 String으로 변환
            }
        }
    }
}

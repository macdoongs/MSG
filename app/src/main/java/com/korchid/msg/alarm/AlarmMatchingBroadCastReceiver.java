package com.korchid.msg.alarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.korchid.msg.mqtt.MqttServiceDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.korchid.msg.global.QuickstartPreferences.USER_NICKNAME;

/**
 * Created by mac0314 on 2017-01-12.
 */

public class AlarmMatchingBroadCastReceiver extends AlarmBroadCastReciever{

    private static Boolean isExecute = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        Log.d(getClass().getName(),"time second : " + new Date().toString());


        // 알람 스타트
        AlarmUtil.getInstance().startMatchingAlarm(context);

        if(isExecute) {
            Log.d(getClass().getName(), "isExecute : " + isExecute);
        }else{
            Log.d(getClass().getName(), "isExecute : " + isExecute);

            final int senderId = intent.getIntExtra("senderId", 0);
            final int receiverId = intent.getIntExtra("receiverId", 0);
            final String userNickname = intent.getStringExtra("userNickname");
            final String message = intent.getStringExtra("message");
            final String topic = intent.getStringExtra("topic");

            Log.d(getClass().getName(), "senderId : " + senderId);

            JSONObject obj = new JSONObject();
            try {
                obj.put("senderId", senderId);
                obj.put("receiverId", receiverId);
                obj.put(USER_NICKNAME, userNickname);
                obj.put("message", message);

                System.out.println(obj.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            MqttServiceDelegate.publish(context, topic, obj.toString().getBytes());
            isExecute = true;
        }
    }

}

package com.korchid.msg.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.korchid.msg.activity.SelectOpponentActivity;
import com.korchid.msg.mqtt.MqttServiceDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static com.korchid.msg.global.QuickstartPreferences.USER_NICKNAME;

/**
 * Created by mac0314 on 2017-01-12.
 */

public class AlarmMatchingBroadCastReceiver extends AlarmBroadCastReciever{
    private static final String TAG = "AlarmMatchingReceiver";
    // TODO complete code - Setting reservation time

    private static Boolean isExecute = false;
    private static int requestCode = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
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
            final String senderNickname = intent.getStringExtra("senderNickname");
            final String topic = intent.getStringExtra("topic");
            final String messageType = intent.getStringExtra("messageType");
            final String message = intent.getStringExtra("message");

            Log.d(getClass().getName(), "senderId : " + senderId);

            JSONObject obj = new JSONObject();
            try {
                obj.put("senderId", senderId);
                obj.put("senderNickname", senderNickname);
                obj.put("receiverId", receiverId);
                obj.put("topic", topic);
                obj.put("messageType", messageType);
                obj.put("message", message);

                System.out.println(obj.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Send message
            MqttServiceDelegate.publish(context, topic, obj.toString().getBytes());

            isExecute = true;
        }
    }

}

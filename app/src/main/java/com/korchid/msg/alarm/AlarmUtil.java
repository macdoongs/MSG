package com.korchid.msg.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by mac0314 on 2017-01-06.
 */


// http://gogorchg.tistory.com/entry/Android-AlarmManager%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Schedule-%EA%B4%80%EB%A6%AC
public class AlarmUtil {
    private static final String TAG = "AlarmUtil";
    private static final int FIVE_SECOND = 5 * 1000;
    private static final int ONE_MINUES = 60 * 1000;


    private static int senderId = 0;
    private static int receiverId = 0;
    private static String userNickname = "";
    private static String message = "";
    private static String topic = "";


    private static AlarmUtil _instance;

    public static AlarmUtil getInstance() {
        if (_instance == null) _instance = new AlarmUtil();
        return _instance;
    }

    public void startMatchingAlarm(Context context) {
        Log.d(TAG, "sender : " + senderId);

        // AlarmOneSecondBroadcastReceiver 초기화
        Intent alarmIntent = new Intent(context, AlarmMatchingBroadCastReceiver.class);
        alarmIntent.putExtra("senderId", senderId);
        alarmIntent.putExtra("receiverId", receiverId);
        alarmIntent.putExtra("userNickname", userNickname);
        alarmIntent.putExtra("message", message);
        alarmIntent.putExtra("topic", topic);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);


        startAlarm(context, pendingIntent, FIVE_SECOND);
    }

    private void startAlarm(Context context, PendingIntent pendingIntent, int delay) {

        // AlarmManager 호출
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 예약시간과 일치할 때 AlarmMatchingBroadcastReceiver 호출 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        }
    }

    public static int getSenderId() {
        return senderId;
    }

    public static void setSenderId(int senderId) {
        AlarmUtil.senderId = senderId;
    }

    public static int getReceiverId() {
        return receiverId;
    }

    public static void setReceiverId(int receiverId) {
        AlarmUtil.receiverId = receiverId;
    }

    public static String getUserNickname() {
        return userNickname;
    }

    public static void setUserNickname(String userNickname) {
        AlarmUtil.userNickname = userNickname;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        AlarmUtil.message = message;
    }

    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        AlarmUtil.topic = topic;
    }
}

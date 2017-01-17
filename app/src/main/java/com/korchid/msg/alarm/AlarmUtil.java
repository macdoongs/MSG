package com.korchid.msg.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by mac0314 on 2017-01-06.
 */


// http://gogorchg.tistory.com/entry/Android-AlarmManager%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Schedule-%EA%B4%80%EB%A6%AC
public class AlarmUtil {
    private static final String TAG = "AlarmUtil";


    private static int requestCode = 0;

    private static int senderId = 0;
    private static String senderNickname = "";
    private static int receiverId = 0;
    private static String topic = "";
    private static String messageType = "";
    private static String message = "";


    private static AlarmUtil _instance;

    public static AlarmUtil getInstance() {
        if (_instance == null) _instance = new AlarmUtil();
        return _instance;
    }

    public void startMatchingAlarm(Context context) {
        Log.d(TAG, "startMatchingAlarm");
        Log.d(TAG, "sender : " + senderId);



        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        //cal.add(Calendar.SECOND, 10);
        calendar.set(Calendar.MONTH,Calendar.JANUARY);  //first month is 0!!! January is

        calendar.set(Calendar.DATE, 17);  //1-31zero!!!
        calendar.set(Calendar.YEAR, 2017);//year...

        calendar.set(Calendar.HOUR_OF_DAY, 0);  //HOUR
        calendar.set(Calendar.MINUTE, 20);       //MIN
        calendar.set(Calendar.SECOND, 0);       //SEC


        // AlarmOneSecondBroadcastReceiver 초기화
        Intent alarmIntent = new Intent(context, AlarmMatchingBroadCastReceiver.class);

        alarmIntent.putExtra("senderId", senderId);
        alarmIntent.putExtra("senderNickname", senderNickname);
        alarmIntent.putExtra("receiverId", receiverId);
        alarmIntent.putExtra("topic", topic);
        alarmIntent.putExtra("messageType", messageType);
        alarmIntent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, alarmIntent, 0);

        startAlarm(context, pendingIntent, calendar);
    }

    private void startAlarm(Context context, PendingIntent pendingIntent, Calendar calendar) {
        Log.d(TAG, "startAlarm");

        // AlarmManager 호출
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 예약시간과 일치할 때 AlarmMatchingBroadcastReceiver 호출 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }
    }


    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        AlarmUtil.message = message;
    }

    public static int getSenderId() {
        return senderId;
    }

    public static void setSenderId(int senderId) {
        AlarmUtil.senderId = senderId;
    }

    public static String getSenderNickname() {
        return senderNickname;
    }

    public static void setSenderNickname(String senderNickname) {
        AlarmUtil.senderNickname = senderNickname;
    }

    public static int getReceiverId() {
        return receiverId;
    }

    public static void setReceiverId(int receiverId) {
        AlarmUtil.receiverId = receiverId;
    }

    public static String getTopic() {
        return topic;
    }

    public static void setTopic(String topic) {
        AlarmUtil.topic = topic;
    }

    public static String getMessageType() {
        return messageType;
    }

    public static void setMessageType(String messageType) {
        AlarmUtil.messageType = messageType;
    }
}

package com.korchid.msg.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by mac0314 on 2017-01-06.
 */


// http://gogorchg.tistory.com/entry/Android-AlarmManager%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Schedule-%EA%B4%80%EB%A6%AC
public class AlarmUtil {
    private final static int FIVE_SECOND = 5 * 1000;
    private final static int ONE_MINUES = 60 * 1000;

    private static AlarmUtil _instance;

    public static AlarmUtil getInstance() {
        if (_instance == null) _instance = new AlarmUtil();
        return _instance;
    }

    public void startFiveSecondAlarm(Context context) {
        // AlarmOneSecondBroadcastReceiver 초기화
        Intent alarmIntent = new Intent(context, AlarmFiveSecondBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        // 1초 뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
        startAlarm(context, pendingIntent, FIVE_SECOND);
    }

    private void startAlarm(Context context, PendingIntent pendingIntent, int delay) {

        // AlarmManager 호출
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 1분뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        }
    }

}

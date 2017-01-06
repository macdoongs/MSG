package com.korchid.msg.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;

/**
 * Created by mac0314 on 2017-01-06.
 */

public class AlarmBroadCastReciever extends BroadcastReceiver {
    public static boolean isLaunched = false;
    public final static String KEY_DEFAULT = "default";
    public final static String INTENTFILTER_BROADCAST_TIMER = "recv_timer";


    @Override
    public void onReceive(Context context, Intent intent) {
        isLaunched = true;

        // 현재 시간을 화면에 보낸다.
        saveTime(context);
    }

    private void saveTime(Context context) {
        long currentTime = System.currentTimeMillis();
        Intent intent = new Intent(INTENTFILTER_BROADCAST_TIMER);
        intent.putExtra(KEY_DEFAULT, currentTime);
        context.sendBroadcast(intent);
    }
}

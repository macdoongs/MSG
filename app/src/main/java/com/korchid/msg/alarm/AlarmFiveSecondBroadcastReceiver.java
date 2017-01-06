package com.korchid.msg.alarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by mac0314 on 2017-01-06.
 */

public class AlarmFiveSecondBroadcastReceiver extends AlarmBroadCastReciever{
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);

        // 알람 스타트
        AlarmUtil.getInstance().startFiveSecondAlarm(context);

        Log.d(getClass().getName(),"time second : " + new Date().toString());
    }
}

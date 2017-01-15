package com.korchid.msg.mqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.korchid.msg.mqtt.service.MqttService;

/**
 * Created by mac0314 on 2017-01-12.
 */

public class ChattingBroadCastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            MqttService.mqttTopic = "Sajouiot03";

            Intent serviceIntent = new Intent(context, MqttService.class);
            context.startService(serviceIntent);
        }
    }
}

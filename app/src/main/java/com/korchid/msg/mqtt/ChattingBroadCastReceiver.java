package com.korchid.msg.mqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.korchid.msg.mqtt.impl.MqttTopic;
import com.korchid.msg.mqtt.service.MqttService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac0314 on 2017-01-12.
 */

public class ChattingBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG ="ChattingReceiver";
    private ArrayList<MqttTopic> topics = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Intent serviceIntent = new Intent(context, MqttService.class);
            serviceIntent.putExtra("topic", topics);
            context.startService(serviceIntent);
        }
    }

    public ArrayList<MqttTopic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<MqttTopic> topics) {
        this.topics = topics;
    }

}

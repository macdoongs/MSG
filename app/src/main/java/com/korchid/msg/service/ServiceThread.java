package com.korchid.msg.service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.korchid.msg.http.HttpPost;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mac0314 on 2016-12-05.
 */

// http://twinw.tistory.com/50
// Message reservation time check thread
public class ServiceThread extends Thread {
    private static final String TAG = "ServiceThread";
    Handler handler;
    private boolean isRun = true;
    private boolean isMatch = false;
    public static String timer = "";
    Message message;

    public ServiceThread(Handler handler, Message message){
        this.handler = handler;
        this.message = message;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        Log.d(TAG, "run");

        handler.sendMessage(message);
    }

}

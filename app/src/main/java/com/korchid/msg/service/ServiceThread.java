package com.korchid.msg.service;

import android.os.Handler;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mac0314 on 2016-12-05.
 */

// http://twinw.tistory.com/50
public class ServiceThread extends Thread {
    private static final String TAG = "ServiceThread";
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){


            long now = System.currentTimeMillis();
            Date date = new Date(now);
            // 출력될 포맷 설정
            String to = date.toString();

            //TODO

            Log.d(TAG, to);
            try{
                Thread.sleep(10000); //10초씩 쉰다.
            }catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

}

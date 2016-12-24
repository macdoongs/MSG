package com.korchid.msg.service;

import android.os.Handler;
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

        Log.d(TAG, timer);

        while(isRun){

            // http://kanzler.tistory.com/50
            // http://sumi3360.blogspot.kr/2014/01/android.html
            // Current time
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            Date nextDate;

           // nextDate.setDate(date.getDate()+1);

            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
            String strDate = dateFormat.format(date);
            try {
                Date date1 = dateFormat.parse("2016-12-13 14:25");
                if(!isMatch){
                    if(strDate.equals(timer)){
                        Log.d(TAG, "Complete match!");
                        isMatch = true;
                    }else{
                        Log.d(TAG, "No match, " + strDate);
                    }
                }

            }catch (Exception e){
                // Error handling sample
                String strUrl = "https://www.korchid.com/msg-error";
                HashMap<String, String> params = new HashMap<>();
                params.put("userId", "");
                params.put("log", "Log : " + e.getMessage());


                HttpPost httpPost = new HttpPost(strUrl, params, new Handler());
                httpPost.start();

                Log.e(TAG, e.getMessage());
            }

            // 출력될 포맷 설정
            String to = date.toString();

            String tempDate = "2014-01-29 13:30";

            Log.d(TAG, to);
            try{
                Thread.sleep(60000); //10초씩 쉰다.
            }catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

}

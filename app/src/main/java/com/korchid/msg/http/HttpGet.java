package com.korchid.msg.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mac0314 on 2016-12-17.
 */

public class HttpGet extends Thread {
    private static String TAG = "HttpGet";
    HttpURLConnection connection;
    URL url = null;
    Handler handler;

    String response = null;

    public HttpGet(String stringUrl, Handler handler) {
        this.handler = handler;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //Log.d(TAG, "URL : " + this.url.toString());
    }

    @Override
    public void run() {
        Log.d(TAG, "run");

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            String line = "";

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            // From Thread to Activity
            Message message = Message.obtain(handler);
            Bundle data = new Bundle();
            data.putString("response", sb.toString());
            message.setData(data);
            handler.sendMessage(message);

            isr.close();
            reader.close();


        } catch (IOException e) {
            // Error
            Log.e(TAG, "IOException : " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

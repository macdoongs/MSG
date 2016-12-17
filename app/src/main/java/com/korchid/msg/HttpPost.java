package com.korchid.msg;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac0314 on 2016-12-17.
 */

public class HttpPost extends Thread {
    private static String TAG = "HttpPost";
    HttpURLConnection connection;
    HashMap<String, String> params;
    URL url = null;

    String response = null;

    public HttpPost (String stringUrl, HashMap<String, String> params){
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.params = params;
    }

    @Override
    public void run() {
        Log.d(TAG, "run");

        // http://hayageek.com/android-http-post-get/
        // Post
        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");


            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();

            String line = "";

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();
            reader.close();

            Log.d(TAG, "post sb : " + sb.toString());

        } catch (IOException e) {
            // Error
            Log.e(TAG, "IOException : " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage());
            e.printStackTrace();
        }

    }

    // http://stackoverflow.com/questions/9767952/how-to-add-parameters-to-httpurlconnection-using-post
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}

package com.korchid.msg.adapter;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korchid.msg.Chatting;
import com.korchid.msg.R;
import com.korchid.msg.activity.ChattingActivity;
import com.korchid.msg.mqtt.service.MqttService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class ChattingAdapter extends BaseAdapter implements View.OnLongClickListener{
    private static final String TAG = "ChattingAdapter";

    private LayoutInflater mInflater;

    private Activity MessagingActivity;

    private ArrayList<Chatting> chattingArrayList;

    private TextView tv_myMessage;
    private TextView tv_yourMessage;

    private int senderId;
    private int receiverId;
    private int userId = 0;
    private Chatting.Type messageType = Chatting.Type.MESSAGE;
    private String message = "";
    private String senderNickname = "";
    private String opponentProfile = "";

    public ChattingAdapter (Activity activity, ArrayList<Chatting> chattingArrayList, int userId, String opponentProfile) {
        this.MessagingActivity = activity;
        this.chattingArrayList = chattingArrayList;
        this.userId = userId;
        this.opponentProfile = opponentProfile;

        mInflater = (LayoutInflater)MessagingActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chattingArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return chattingArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView");

        senderId = chattingArrayList.get(position).getSenderId();
        receiverId = chattingArrayList.get(position).getReceiverId();
        messageType = chattingArrayList.get(position).getMessageType();
        senderNickname = chattingArrayList.get(position).getSenderNickname();
        message = chattingArrayList.get(position).getMessage();

        if(convertView == null){

            int resource = R.layout.chatting_message;

            convertView = mInflater.inflate(resource, parent, false);
        }

        LinearLayout ll_container = (LinearLayout) convertView.findViewById(R.id.ll_container);
        GridLayout gl_container = (GridLayout) convertView.findViewById(R.id.gl_container);



        if(senderId == userId){
            // Application user oneself
            tv_myMessage = (TextView) convertView.findViewById(R.id.tv_myMessage);
            ll_container.setVisibility(View.VISIBLE);
            gl_container.setVisibility(View.INVISIBLE);
            tv_myMessage.setText(message);


            tv_myMessage.setOnLongClickListener(this);


            /*
            // Imageview test
            tv_myMessage.setVisibility(View.GONE);
            ImageView iv_myImage = (ImageView) convertView.findViewById(R.id.iv_myImage);
            iv_myImage.setImageResource(R.drawable.background);
            */
        }else{
            // Counterpart
            final ImageView iv_yourProfile = (ImageView) convertView.findViewById(R.id.iv_yourProfile);

            TextView tv_yourName = (TextView) convertView.findViewById(R.id.tv_yourName);
            tv_yourMessage = (TextView) convertView.findViewById(R.id.tv_yourMessage);
            ll_container.setVisibility(View.INVISIBLE);
            gl_container.setVisibility(View.VISIBLE);
            tv_yourName.setText(senderNickname);
            tv_yourMessage.setText(message);

            tv_yourMessage.setOnLongClickListener(this);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        URL url = new URL(opponentProfile);
                        InputStream is = url.openStream();
                        URLConnection conn = url.openConnection();

                        conn.connect();
                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

                        final Bitmap bm = BitmapFactory.decodeStream(bis);
                        Handler handler = new Handler();
                        handler.post(new Runnable() {

                            @Override
                            public void run() {  // 화면에 그려줄 작업
                                iv_yourProfile.setImageBitmap(bm);
                            }
                        });
                        iv_yourProfile.setImageBitmap(bm); //비트맵 객체로 보여주기
                    } catch(Exception e){

                    }

                }
            });

            thread.start();
        }

        return convertView;
    }

    // http://stackoverflow.com/questions/14189544/copy-with-clipboard-manager-that-supports-old-and-new-android-versions
    @Override
    public boolean onLongClick(View v) {
        int viewId = v.getId();

        switch (viewId){
            case R.id.tv_myMessage:{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) MessagingActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    final android.content.ClipData clipData = android.content.ClipData
                            .newPlainText("tv_myMessage", tv_myMessage.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                } else {
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) MessagingActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(tv_myMessage.getText().toString());
                }
                break;
            }
            case R.id.tv_yourMessage:{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) MessagingActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    final android.content.ClipData clipData = android.content.ClipData
                            .newPlainText("tv_yourMessage", tv_yourMessage.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                } else {
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) MessagingActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(tv_yourMessage.getText().toString());
                }
                break;
            }
            default:{
                break;
            }
        }
        return false;
    }
}

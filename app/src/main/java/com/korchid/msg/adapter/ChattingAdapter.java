package com.korchid.msg.adapter;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

    private String name = "";
    private String message = "";
    private String userPhoneNumber = "";

    public ChattingAdapter (Activity activity, ArrayList<Chatting> chattingArrayList, String userPhoneNumber) {
        this.MessagingActivity = activity;
        this.chattingArrayList = chattingArrayList;
        this.userPhoneNumber = userPhoneNumber;

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

        String[] ReturnList = chattingArrayList.get(position).getMessage().split(":");
        name = ReturnList[0];
        message = ReturnList[1];


        if(convertView == null){

            int resource = R.layout.chatting_message;

            convertView = mInflater.inflate(resource, parent, false);
        }

        LinearLayout ll_container = (LinearLayout) convertView.findViewById(R.id.ll_container);
        GridLayout gl_container = (GridLayout) convertView.findViewById(R.id.gl_container);



        if(name.equals(chattingArrayList.get(position).getUser())){
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
            TextView tv_yourName = (TextView) convertView.findViewById(R.id.tv_yourName);
            tv_yourMessage = (TextView) convertView.findViewById(R.id.tv_yourMessage);
            ll_container.setVisibility(View.INVISIBLE);
            gl_container.setVisibility(View.VISIBLE);
            tv_yourName.setText(name);
            tv_yourMessage.setText(message);

            tv_yourMessage.setOnLongClickListener(this);
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

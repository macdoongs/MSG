package com.korchid.msg.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korchid.msg.Chatting;
import com.korchid.msg.R;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class ChattingAdapter extends BaseAdapter {
        private static final String TAG = "ChattingAdapter";

        private LayoutInflater mInflater;

        private Activity MessagingActivity;

        private ArrayList<Chatting> chattingArrayList;

        private String name = "";
        private String message = "";

        public ChattingAdapter (Activity activity, ArrayList<Chatting> chattingArrayList) {
            this.MessagingActivity = activity;
            this.chattingArrayList = chattingArrayList;

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

            String[] ReturnList = chattingArrayList.get(position).message.split(":");
            name = ReturnList[0];
            message = ReturnList[1];


            if(convertView == null){

                int resource = R.layout.chatting_message;

                convertView = mInflater.inflate(resource, parent, false);
            }


            LinearLayout ll_container = (LinearLayout) convertView.findViewById(R.id.ll_container);
            GridLayout gl_container = (GridLayout) convertView.findViewById(R.id.gl_container);

            if(name.equals(chattingArrayList.get(position).user)){
                // Application user oneself
                TextView tv_myName = (TextView) convertView.findViewById(R.id.tv_myName);
                TextView tv_myMessage = (TextView) convertView.findViewById(R.id.tv_myMessage);
                ll_container.setVisibility(View.VISIBLE);
                gl_container.setVisibility(View.INVISIBLE);
                tv_myName.setText(name);
                tv_myMessage.setText(message);
            }else{
                // Counterpart
                TextView tv_yourName = (TextView) convertView.findViewById(R.id.tv_yourName);
                TextView tv_yourMessage = (TextView) convertView.findViewById(R.id.tv_yourMessage);
                ll_container.setVisibility(View.INVISIBLE);
                gl_container.setVisibility(View.VISIBLE);
                tv_yourName.setText(name);
                tv_yourMessage.setText(message);
            }

            return convertView;

        }
}

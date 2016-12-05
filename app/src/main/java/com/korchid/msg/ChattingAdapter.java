package com.korchid.msg;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class ChattingAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private Activity MessagingActivity;

        private ArrayList<Chatting> messageArray;

        private String name = "";
        private String message = "";

        public ChattingAdapter (Activity act, ArrayList<Chatting> mArr) {
            this.MessagingActivity = act;
            this.messageArray = mArr;

            mInflater = (LayoutInflater)MessagingActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        //설명 2:

        @Override
        public int getCount() {
            return messageArray.size();
        }

        @Override
        public Object getItem(int position) {
            return messageArray.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        //설명 3:

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            String[] ReturnList = messageArray.get(position).message.split(":");
            name = ReturnList[0];
            message = ReturnList[1];


            if(convertView == null){

                int res = 0;

                res = R.layout.chatting_message;

                convertView = mInflater.inflate(res, parent, false);
            }


            LinearLayout layout_view = (LinearLayout) convertView.findViewById(R.id.vi_view);
            GridLayout layout_view2 = (GridLayout) convertView.findViewById(R.id.vi_view2);
            //LinearLayout layout_view2 = (LinearLayout) convertView.findViewById(R.id.vi_view2);

            if(name.equals(messageArray.get(position).user)){
                TextView id = (TextView) convertView.findViewById(R.id.text_id);
                TextView chat = (TextView) convertView.findViewById(R.id.text_chat);
                layout_view.setVisibility(View.VISIBLE);
                layout_view2.setVisibility(View.INVISIBLE);
                id.setText(name);
                chat.setText(message);
            }else{
                TextView id2 = (TextView) convertView.findViewById(R.id.text_id2);
                TextView chat2 = (TextView) convertView.findViewById(R.id.text_chat2);
                layout_view.setVisibility(View.INVISIBLE);
                layout_view2.setVisibility(View.VISIBLE);
                id2.setText(name);
                chat2.setText(message);
            }

            /*  버튼에 이벤트처리를 하기위해선 setTag를 이용해서 사용할 수 있습니다.

               *   Button btn 가 있다면, btn.setTag(position)을 활용해서 각 버튼들

               *   이벤트처리를 할 수 있습니다.

            */
            return convertView;

        }
}

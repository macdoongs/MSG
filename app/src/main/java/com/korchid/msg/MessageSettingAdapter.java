package com.korchid.msg;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-30.
 */

public class MessageSettingAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private Activity m_activity;

    private ArrayList<Setting> settingArray;

    public MessageSettingAdapter(Activity act, ArrayList<Setting> arr_item) {
        this.m_activity = act;
        this.settingArray = arr_item;

        mInflater = (LayoutInflater)m_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return settingArray.size();
    }

    @Override
    public Object getItem(int position) {
        return settingArray.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            int res = 0;

            res = R.layout.message_setting_menu;

            convertView = mInflater.inflate(res, parent, false);

        }

        ImageView imView = (ImageView)convertView.findViewById(R.id.vi_image);

        TextView title = (TextView)convertView.findViewById(R.id.vi_title);

        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.vi_view);

        int resId=  m_activity.getResources().getIdentifier(settingArray.get(position).picture, "drawable", m_activity.getPackageName());

        imView.setBackgroundResource(resId);

        title.setText(settingArray.get(position).menu);


        layout_view.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                GoIntent(position);
            }
        });

        return convertView;

    }



    //설명 4:

    public void GoIntent(int a){
        if(settingArray.get(a).id==0){

        }else if(settingArray.get(a).id==1){

        }else if(settingArray.get(a).id==2){

        }else if(settingArray.get(a).id==3){

        }else if(settingArray.get(a).id==4){
        }

    }

}
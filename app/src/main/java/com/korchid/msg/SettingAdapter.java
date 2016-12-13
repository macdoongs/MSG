package com.korchid.msg;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class SettingAdapter extends BaseAdapter {
    private static final String TAG = "SettingAdapter";

    public enum Type {SETTING, MESSAGE_SETTING}

    private ArrayList<Setting> settingArray;
    private ArrayList<MessageSetting> messageSettingArrayList;

    private LayoutInflater mInflater;
    private Activity mActivity;

    private Type type;

    public SettingAdapter(Activity activity, ArrayList<Setting> settingArray) {
        this.mActivity = activity;

        this.settingArray = settingArray;


        mInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            int resource = R.layout.setting_menu;

            convertView = mInflater.inflate(resource, parent, false);
        }

        ImageView iv_title = (ImageView)convertView.findViewById(R.id.iv_title);

        TextView tv_title = (TextView)convertView.findViewById(R.id.tv_title);

        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.ll_container);

        int resourceId=  mActivity.getResources().getIdentifier(settingArray.get(position).getPicture(), "drawable", mActivity.getPackageName());

        iv_title.setBackgroundResource(resourceId);

        tv_title.setText(settingArray.get(position).getTitle());


        layout_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goIntent(position);
            }
        });

        return convertView;

    }

    public void goIntent(int idx){
        Log.d(TAG, settingArray.get(idx).getTitle());

    }

}
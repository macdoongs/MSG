package com.korchid.msg;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class SettingAdapter extends BaseAdapter {
    private static final String TAG = "SettingAdapter";

    public enum Type {SETTING, MESSAGE_SETTING}

    private ArrayList<Setting> settingArray;
    private ArrayList<MessageSetting> messageSettingArrayList;
    private ArrayList<String> messageArrayList;
    private ArrayList<Object> arrayList;

    private Setting setting;
    private MessageSetting messageSetting;

    private LayoutInflater mInflater;
    private Activity mActivity;

    private Type type;

    CheckBox cb_option;


    public SettingAdapter(Activity activity, ArrayList<Object> settingArray, Type type) {
        this.mActivity = activity;
        this.arrayList = settingArray;
        this.type = type;

        messageArrayList = new ArrayList<>();

        mInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView");
        if(convertView == null){
            int resource = R.layout.setting_menu;

            convertView = mInflater.inflate(resource, parent, false);
        }

        ImageView iv_title = (ImageView)convertView.findViewById(R.id.iv_title);

        cb_option = (CheckBox) convertView.findViewById(R.id.cb_option);

        TextView tv_title = (TextView)convertView.findViewById(R.id.tv_title);

        LinearLayout layout_view =  (LinearLayout)convertView.findViewById(R.id.ll_container);

        /*
        http://ldelight.tistory.com/entry/Android-%EB%AC%B8%EC%9E%90%EC%97%B4%EB%A1%9C-Resource-%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0
        문자열로 해당 Resource (혹은 레이아웃에 포함된 View) 의 ID 값을 가져온다;
                getResources().getIdentifier(파일명, 디렉토리명, 패키지명);
        또는,
                getResources().getIdentifier(패키지명:디렉토리/파일명, null, null);
        으로도 가능하다.
        */
        int resourceId = 0;

        if(type == Type.SETTING){
            setting = (Setting) arrayList.get(position);
            Log.d(TAG, "Setting : " + setting.getTitle());
            tv_title.setText(setting.getTitle());
            cb_option.setVisibility(View.GONE);
            resourceId =  mActivity.getResources().getIdentifier( setting.getPicture(), "drawable", mActivity.getPackageName());
        }else if(type == Type.MESSAGE_SETTING){
            messageSetting = (MessageSetting) arrayList.get(position);
            Log.d(TAG, "Message Setting : " + messageSetting.getTitle());
            tv_title.setText(messageSetting.getTitle());
            cb_option.setVisibility(View.VISIBLE);
            cb_option.setChecked(false);
            resourceId =  mActivity.getResources().getIdentifier( messageSetting.getPicture(), "drawable", mActivity.getPackageName());
        }


        iv_title.setBackgroundResource(resourceId);

        layout_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goIntent(position);
            }
        });

        return convertView;

    }

    public void goIntent(int idx){
        Log.d(TAG, "goIntent");
        switch (type){
            case SETTING:{
                setting = (Setting) arrayList.get(idx);
                Log.d(TAG, "idx : " + idx + " " + setting.getTitle());
                //Toast.makeText(mActivity.getApplicationContext(), setting.getTitle(), Toast.LENGTH_SHORT).show();

                break;
            }
            case MESSAGE_SETTING:{
                messageSetting = (MessageSetting) arrayList.get(idx);
                Log.d(TAG, "idx : " + idx + " " + messageSetting.getTitle() + messageSetting.getTime());
                //Toast.makeText(mActivity.getApplicationContext(), messageSetting.getTitle(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Size : " + messageArrayList.size());



                if(messageArrayList.size() < 5){
                    String reservationMessage = messageSetting.getTitle() + "/" + messageSetting.getTime();
                    messageArrayList.add(reservationMessage);
                    cb_option.setChecked(true);
                }

                break;
            }
            default:{

                break;
            }
        }

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
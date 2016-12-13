package com.korchid.msg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-30.
 */

public class MessageSettingActivity extends AppCompatActivity {
    private static final String TAG = "MessageSettingActivity";
    private String nickname;
    private String title;

    private ArrayList settingArrayList;
    private SettingAdapter settingAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent data = getIntent();

        setList();
    }

    private void setList(){
        Log.d(TAG, "setList");

        settingArrayList = new ArrayList();

        ListView lv_setting = (ListView)findViewById(R.id.lv_setting);

        settingArrayList.add(new MessageSetting(0, "@drawable/plane", MessageSetting.Type.POLITE, "잘지내시죠?"));

        settingArrayList.add(new MessageSetting(1, "@drawable/plane", MessageSetting.Type.POLITE, "별일 없으신가요?"));

        settingArrayList.add(new MessageSetting(2, "@drawable/plane", MessageSetting.Type.POLITE, "건강은 어떠세요?"));

        settingArrayList.add(new MessageSetting(3, "@drawable/plane", MessageSetting.Type.POLITE, "식사하셨어요?"));

        settingAdapter = new SettingAdapter(MessageSettingActivity.this,  settingArrayList);

        lv_setting.setAdapter(settingAdapter);

    }

    public void listUpdate(){
        Log.d(TAG, "listUpdate");
        settingAdapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.
    }
}
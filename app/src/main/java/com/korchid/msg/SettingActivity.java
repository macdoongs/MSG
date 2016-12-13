package com.korchid.msg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";

    private ArrayList<Setting> settingArrayList;
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
        settingArrayList = new ArrayList<Setting>();

        ListView lv_setting = (ListView)findViewById(R.id.lv_setting);

        settingArrayList.add(new Setting(0, "@drawable/mainsetting", "메시지 설정"));

        settingArrayList.add(new Setting(1, "@drawable/mainsetting", "사용자 추가"));

        settingArrayList.add(new Setting(2, "@drawable/mainsetting", "환경 설정"));

        settingArrayList.add(new Setting(3, "@drawable/mainsetting", "돌아가기"));

        settingAdapter = new SettingAdapter(SettingActivity.this, settingArrayList);

        lv_setting.setAdapter(settingAdapter);
    }

    public void listUpdate(){
        Log.d(TAG, "listUpdate");
        settingAdapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.
    }
}
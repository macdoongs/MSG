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

    private MessageSetting.Type type;


    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        // Enum data - intent
        type = (MessageSetting.Type) intent.getSerializableExtra("Type");
        Log.d(TAG, "Type : " + type);

        setList(type);
    }

    private void setList(MessageSetting.Type type){
        Log.d(TAG, "setList");

        settingArrayList = new ArrayList();

        ListView lv_setting = (ListView)findViewById(R.id.lv_setting);
        if(type == MessageSetting.Type.POLITE){
            settingArrayList.add(new MessageSetting(0, "@drawable/plane", type, "엄마 뭐하세요?"));

            settingArrayList.add(new MessageSetting(1, "@drawable/plane", type, "엄마 어디 아픈데 없죠?"));

            settingArrayList.add(new MessageSetting(2, "@drawable/plane", type, "엄마 뭐 필요한거 있어요?"));

            settingArrayList.add(new MessageSetting(3, "@drawable/plane", type, "엄마 요새 바쁘세요?"));
        }else if(type == MessageSetting.Type.IMPOLITE){
            settingArrayList.add(new MessageSetting(0, "@drawable/plane", type, "엄마 뭐해?"));

            settingArrayList.add(new MessageSetting(1, "@drawable/plane", type, "엄마 어디 아픈데 없지?"));

            settingArrayList.add(new MessageSetting(2, "@drawable/plane", type, "엄마 뭐 필요한거 있어?"));

            settingArrayList.add(new MessageSetting(3, "@drawable/plane", type, "엄마 요새 바빠?"));
        }else if(type == MessageSetting.Type.IN_PERSON){

        }


        settingAdapter = new SettingAdapter(MessageSettingActivity.this,  settingArrayList);

        lv_setting.setAdapter(settingAdapter);

    }

    public void listUpdate(){
        Log.d(TAG, "listUpdate");
        settingAdapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.
    }
}
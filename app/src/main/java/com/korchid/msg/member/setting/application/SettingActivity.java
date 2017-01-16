package com.korchid.msg.member.setting.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-28.
 */

// Main setting
public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";

    private ArrayList settingArrayList;
    private SettingAdapter settingAdapter;



    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        setList();
    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Setting");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }

        return true;
    }

    private void setList(){
        Log.d(TAG, "setList");
        settingArrayList = new ArrayList<> ();

        ListView lv_setting = (ListView)findViewById(R.id.lv_setting);

        settingArrayList.add(new Setting(0, "@drawable/mainsetting", "메시지 설정"));

        settingArrayList.add(new Setting(1, "@drawable/mainsetting", "사용자 추가"));

        settingArrayList.add(new Setting(2, "@drawable/mainsetting", "환경 설정"));

        settingArrayList.add(new Setting(3, "@drawable/mainsetting", "돌아가기"));


        settingAdapter = new SettingAdapter(SettingActivity.this, settingArrayList, SettingAdapter.Type.SETTING);

        lv_setting.setAdapter(settingAdapter);
    }



    public void listUpdate(){
        Log.d(TAG, "listUpdate");
        settingAdapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.
    }
}
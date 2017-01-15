package com.korchid.msg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.korchid.msg.adapter.RestfulAdapter;
import com.korchid.msg.retrofit.response.Res;
import com.korchid.msg.retrofit.response.ReservationMessage;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.MessageSetting;
import com.korchid.msg.R;
import com.korchid.msg.adapter.SettingAdapter;
import com.korchid.msg.ui.StatusBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.USER_INFO_CHECK;
import static com.korchid.msg.global.QuickstartPreferences.USER_NICKNAME;
import static com.korchid.msg.global.QuickstartPreferences.USER_PROFILE;
import static com.korchid.msg.global.QuickstartPreferences.USER_SEX;

/**
 * Created by mac0314 on 2016-11-30.
 */

// Setting - Reservation message
public class MessageSettingActivity extends AppCompatActivity {
    private static final String TAG = "MessageSettingActivity";
    private String nickname;
    private String title;

    private ArrayList<Object> settingArrayList;
    private ArrayList<String> messageArrayList;
    private SettingAdapter settingAdapter;

    private MessageSetting.Type type;

    static final int REQUEST_ADD_POLITE = 0;
    static final int REQUEST_ADD_IMPOLITE = 1;

    static int num = 0;

    Handler handler = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        Intent intent = getIntent();
        // Enum data - intent
        type = (MessageSetting.Type) intent.getSerializableExtra("Type");

        if(type == null){
            type = MessageSetting.Type.POLITE;
        }

        Log.d(TAG, "Type : " + type);

        setList(type);
    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Message setting");
    }

    private void setList(final MessageSetting.Type type){
        Log.d(TAG, "setList");


        settingArrayList = new ArrayList<>();

        int contentType;
        if(type == MessageSetting.Type.IN_PERSON){

        }else{
            loadReservationMessage(type);
        }


        ListView lv_setting = (ListView)findViewById(R.id.lv_setting);
        lv_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected : " + i);
                // parent는 AdapterView의 속성의 모두 사용 할 수 있다.
                String tv = (String)adapterView.getAdapter().getItem(i);
                Log.d(TAG, "tv : " + tv);
                Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();



                // view는 클릭한 Row의 view를 Object로 반환해 준다.
//                TextView tv_view = (TextView)view.findViewById(R.id.tv_row_title);
//                tv_view.setText("바꿈");
                for(int n=0; n<5; n++){

                }

                // Position 은 클릭한 Row의 position 을 반환해 준다.
                Toast.makeText(getApplicationContext(), "" + i, Toast.LENGTH_SHORT).show();
                // l_Position 은 클릭한 Row의 long type의 position 을 반환해 준다.
                Toast.makeText(getApplicationContext(), "l = " + l, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        num = 0;


        settingAdapter = new SettingAdapter(MessageSettingActivity.this,  settingArrayList, SettingAdapter.Type.MESSAGE_SETTING);


        lv_setting.setAdapter(settingAdapter);


    }


    private void loadReservationMessage(final MessageSetting.Type type){
        int reservationMessageType = 0;
        if(type == MessageSetting.Type.POLITE){
            reservationMessageType = 1;
        }else if(type == MessageSetting.Type.IMPOLITE){
            reservationMessageType = 2;
        }
        Call<List<ReservationMessage>> reservationMessageCall = RestfulAdapter.getInstance().reservationMessage(reservationMessageType);

        reservationMessageCall.enqueue(new Callback<List<ReservationMessage>>() {
            @Override
            public void onResponse(Call<List<ReservationMessage>> call, Response<List<ReservationMessage>> response) {
                List<ReservationMessage> reservationMessageList = response.body();

                for(int i=0; i<reservationMessageList.size(); i++){
                    int reservationMessageId = reservationMessageList.get(i).getReservation_message_id();
                    int reservationMessageTypeId = reservationMessageList.get(i).getReservation_message_type_id();
                    String content = reservationMessageList.get(i).getContent_txt();

                    MessageSetting messageSetting = new MessageSetting(reservationMessageId, "", type, content,"", false);

                    settingArrayList.add(messageSetting);
                }

            }

            @Override
            public void onFailure(Call<List<ReservationMessage>> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_complete, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        Log.d(TAG, "onOptionsItemSelected");

        int id = item.getItemId();

        switch (id){
            case android.R.id.home: {
                this.finish();
                break;
            }
            case R.id.itemComplete:{
                Intent intent = new Intent();


                Log.d(TAG, "size : " + settingArrayList.size());
                String result = "";
                for(int i = 0; i<settingArrayList.size(); i++){
                    MessageSetting messageSetting = (MessageSetting) settingArrayList.get(i);



                    Log.d(TAG, "check : " + messageSetting.isCheck());
                    if(messageSetting.isCheck()){
                        result += messageSetting.getTitle() + "/";
                        Log.d(TAG, messageSetting.getTitle());
                    }

                }

                intent.putExtra("message", result);
                setResult(RESULT_OK, intent);

                this.finish();
                break;
            }
            default:
                break;
        }

        return true;
    }



    public void listUpdate(){
        Log.d(TAG, "listUpdate");

        settingAdapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.
    }
}
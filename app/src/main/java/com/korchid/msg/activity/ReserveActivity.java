package com.korchid.msg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.korchid.msg.adapter.RestfulAdapter;
import com.korchid.msg.retrofit.Res;
import com.korchid.msg.retrofit.UserData;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.MessageSetting;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;

// Register reservation message and the number of sending
public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "ReserveActivity";
    public static final int TOTAL_WEEK = 7;

    private Switch sw_pushEnable;
    private Switch sw_enable;
    private Switch sw_alert;

    private NumberPicker np_week;
    private NumberPicker np_number;

    private Button btn_polite;
    private Button btn_impolite;
    private Button btn_inPerson;
    private Button btn_reserve;

    private TextView tv_message1;
    private TextView tv_message2;
    private TextView tv_message3;
    private TextView tv_message4;
    private TextView tv_message5;


    private String message;
    private String messageData;
    private int viewId;
    private int weekNum;
    private int times;
    private int textNum;
    private Boolean isEnable;
    private Boolean isAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);


        initView();

    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Reserve");

        btn_polite = (Button) findViewById(R.id.btn_polite);
        btn_polite.setOnClickListener(this);
        btn_polite.setEnabled(false);

        btn_impolite = (Button) findViewById(R.id.btn_impolite);
        btn_impolite.setOnClickListener(this);
        btn_impolite.setEnabled(false);

        btn_inPerson = (Button) findViewById(R.id.btn_inPerson);
        btn_inPerson.setOnClickListener(this);
        btn_inPerson.setEnabled(false);

        btn_reserve = (Button) findViewById(R.id.btn_reserve);
        btn_reserve.setOnClickListener(this);

        sw_pushEnable = (Switch) findViewById(R.id.sw_pushEnable);
        if(sw_pushEnable != null){
            sw_pushEnable.setOnCheckedChangeListener(this);
        }

        sw_enable = (Switch) findViewById(R.id.sw_enable);
        if (sw_enable != null) {
            sw_enable.setOnCheckedChangeListener(this);
        }

        sw_alert = (Switch) findViewById(R.id.sw_alert);
        if(sw_alert != null){
            sw_alert.setOnCheckedChangeListener(this);
        }

        np_week = (NumberPicker) findViewById(R.id.np_week);
        np_number = (NumberPicker) findViewById(R.id.np_number);

        np_week.setMinValue(1);
        np_week.setMaxValue(2);
        np_week.setWrapSelectorWheel(false);

        np_number.setMinValue(1);
        np_number.setMaxValue(7);
        np_number.setWrapSelectorWheel(false);

        np_week.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                weekNum = view.getValue();

                np_number.setMaxValue(TOTAL_WEEK * weekNum);
            }
        });


        tv_message1 = (TextView) findViewById(R.id.tv_message1);
        tv_message2 = (TextView) findViewById(R.id.tv_message2);
        tv_message3 = (TextView) findViewById(R.id.tv_message3);
        tv_message4 = (TextView) findViewById(R.id.tv_message4);
        tv_message5 = (TextView) findViewById(R.id.tv_message5);

    }


    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_polite:{
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                intent.putExtra("Type", MessageSetting.Type.POLITE);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.btn_impolite:{
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                intent.putExtra("Type", MessageSetting.Type.IMPOLITE);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.btn_inPerson:{
                Intent intent = new Intent(getApplicationContext(), InPersonMessageSettingActivity.class);
                intent.putExtra("Type", MessageSetting.Type.IN_PERSON);
                startActivityForResult(intent, 0);

                break;
            }
            case R.id.btn_reserve:{

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_LOGIN, 0);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                final int userId = sharedPreferences.getInt(USER_ID_NUMBER, 0);
                final Boolean messageAlert = sw_pushEnable.isChecked();
                final Boolean reserveEnable = sw_enable.isChecked();
                final Boolean reserveAlert = sw_alert.isChecked();
                final int weekNumber = np_week.getValue();
                final int reserveNumber = np_number.getValue();
                final String message = " ";

                Log.d(TAG, "userId : " + userId);

                Call<Res> userSettingCall = RestfulAdapter.getInstance().userSetting(userId, messageAlert, reserveEnable, reserveAlert, weekNumber, reserveNumber);

                userSettingCall.enqueue(new Callback<Res>() {
                    @Override
                    public void onResponse(Call<Res> call, Response<Res> response) {
                        Log.d(TAG, "nickname : " + response);
                    }

                    @Override
                    public void onFailure(Call<Res> call, Throwable t) {
                        Log.d(TAG, "onFailure");
                    }
                });

/*
                String stringUrl = "https://www.korchid.com/msg-message-setting";
                HashMap<String, String> params = new HashMap<>();
                params.put("swEnable", swEnable);
                params.put("swAlert", swAlert);
                params.put("weekNum", weekNum);
                params.put("userId", userId);
                params.put("times", times);
                params.put("message", message);


                Handler httpHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_RESERVATION_SETTING , 0);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        Log.d(TAG, "handleMessage");

                        String response = msg.getData().getString("response");

                        String[] line = response.split("\n");

                        //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        if (line[0].equals("OK")) {
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                            // http://mommoo.tistory.com/38
                            // Use Environmental variable 'SharedPreference'


                            editor.putBoolean(RESERVATION_CHECK, true);
                            editor.putString(RESERVATION_WEEK_NUMBER, weekNum);
                            editor.putString(RESERVATION_MESSAGE, message);
                            editor.putString(RESERVATION_TIMES, times);
                            editor.putString(RESERVATION_ENABLE, swEnable);
                            editor.putString(RESERVATION_MESSAGE_ALERT, swAlert);


                            editor.commit(); // Apply file


                            // Delete preference value
                            // 1. Remove "key" data
                            //editor.remove("key");

                            // 2. Remove xml data
                            //editor.clear();


                            // if sharedPreferences.getString value is 0, assign 2th parameter
                            Log.d(TAG, "SharedPreference");
                            Log.d(TAG, "USER_INFO : " + sharedPreferences.getBoolean(RESERVATION_CHECK, false));
                            Log.d(TAG, "TIMES : " + sharedPreferences.getString(RESERVATION_TIMES, ""));
                            Log.d(TAG, "WEEK_NUMBER : " + sharedPreferences.getString(RESERVATION_WEEK_NUMBER, ""));
                            Log.d(TAG, "MESSAGE : " + sharedPreferences.getString(RESERVATION_MESSAGE, ""));
                            Log.d(TAG, "ENABLE : " + sharedPreferences.getString(RESERVATION_ENABLE, "false"));
                            Log.d(TAG, "ALERT : " + sharedPreferences.getString(RESERVATION_MESSAGE_ALERT, "false"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                };



                HttpPost httpPost = new HttpPost(stringUrl, params, httpHandler);
                httpPost.start();

*/


                //GlobalApplication.getGlobalApplicationContext().setWeekNum(weekNum);
                //GlobalApplication.getGlobalApplicationContext().setTimes(times);

                Log.d(TAG, "Week : " + weekNum + ", times : " + times);
                Intent intent = new Intent();
                setResult(RESULT_OK);
                finish();
                break;
            }
            default:{
                break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case RESULT_OK:{
                switch (requestCode){
                    case 0:{//polite
                        messageData = data.getStringExtra("message");
                        String[] messageReserved = messageData.split("/");
                        Log.d(TAG, "messageReserved length : " + messageReserved.length);
                        tv_message1.setText("");
                        tv_message2.setText("");
                        tv_message3.setText("");
                        tv_message4.setText("");
                        tv_message5.setText("");
                        for(int i=0; i<messageReserved.length; i++){
                            switch (i){
                                case 0:{
                                    tv_message1.setText(messageReserved[i]);
                                    break;
                                }
                                case 1:{
                                    tv_message2.setText(messageReserved[i]);
                                    break;
                                }
                                case 2:{
                                    tv_message3.setText(messageReserved[i]);
                                    break;
                                }
                                case 3:{
                                    tv_message4.setText(messageReserved[i]);
                                    break;
                                }
                                case 4:{
                                    tv_message5.setText(messageReserved[i]);
                                    break;
                                }
                                default:{
                                 break;
                                }
                            }

                        }

                        break;
                    }
                    case 1:{//impolite

                        break;
                    }
                    default:{
                        break;
                    }
                }
                break;
            }
            case RESULT_CANCELED:{

                break;
            }
            default:{
                break;
            }
        }


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

    public void onCheckedChanged(CompoundButton buttonView, boolean isSWOn) {
        int swId = buttonView.getId();

        switch (swId){
            case R.id.sw_pushEnable:{

                Toast.makeText(this, "sw_pushEnable " + (isSWOn ? "on" : "off"),
                        Toast.LENGTH_SHORT).show();
                if(isSWOn){

                }else{

                }
                break;
            }
            case R.id.sw_enable:{
                this.isEnable = isSWOn;
                Toast.makeText(this, "The sw_enable is " + (isSWOn ? "on" : "off"),
                        Toast.LENGTH_SHORT).show();
                if(isSWOn) {
                    //do stuff when Switch is ON
                    btn_polite.setEnabled(true);
                    btn_polite.setBackgroundResource(R.color.colorPrimary);
                    btn_impolite.setEnabled(true);
                    btn_impolite.setBackgroundResource(R.color.colorPrimary);
                    btn_inPerson.setEnabled(true);
                    btn_inPerson.setBackgroundResource(R.color.colorPrimary);
                } else {
                    //do stuff when Switch is OFF
                    btn_polite.setEnabled(false);
                    btn_polite.setBackgroundResource(R.color.colorTransparent);
                    btn_impolite.setEnabled(false);
                    btn_impolite.setBackgroundResource(R.color.colorTransparent);
                    btn_inPerson.setEnabled(false);
                    btn_inPerson.setBackgroundResource(R.color.colorTransparent);
                }

                break;
            }
            case R.id.sw_alert: {
                this.isAlert = isSWOn;
                Toast.makeText(this, "The sw_alert is " + (isSWOn ? "on" : "off"),
                        Toast.LENGTH_SHORT).show();
                if(isSWOn) {
                    //do stuff when Switch is ON
                } else {
                    //do stuff when Switch if OFF
                }

                break;
            }
            default:{
                break;
            }
        }



    }



}

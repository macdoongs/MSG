package com.korchid.msg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

// Register reservation message and the number of sending
public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "ReserveActivity";
    public static final int TOTAL_WEEK = 7;

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

                SharedPreferences sharedPreferences = getSharedPreferences("LOGIN", 0);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                final String weekNum = "" + np_week.getValue();
                final String times = "" + np_number.getValue();
                final String swEnable = "" + sw_enable.isEnabled();
                final String swAlert = "" + sw_alert.isEnabled();
                final String userId = sharedPreferences.getString("USER_ID_NUM", "");
                final String message = messageData;

                Log.d(TAG, "userId : " + userId);


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
                        SharedPreferences sharedPreferences = getSharedPreferences("USER_INFO", 0);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        Log.d(TAG, "handleMessage");

                        String response = msg.getData().getString("response");

                        String[] line = response.split("\n");

                        //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        if (line[0].equals("OK")) {
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();

                            // http://mommoo.tistory.com/38
                            // Use Environmental variable 'SharedPreference'


                            editor.putBoolean("USER_INFO", true);
                            editor.putString("WEEK_NUMBER", weekNum);
                            editor.putString("MESSAGE", message);
                            editor.putString("TIMES", times);
                            editor.putString("ENABLE", swEnable);
                            editor.putString("ALERT", swAlert);


                            editor.commit(); // Apply file

                            /*
                            // Delete preference value
                            // 1. Remove "key" data
                            editor.remove("key");

                            // 2. Remove xml data
                            editor.clear();
                            */

                            // if sharedPreferences.getString value is 0, assign 2th parameter
                            Log.d(TAG, "SharedPreference");
                            Log.d(TAG, "USER_INFO : " + sharedPreferences.getBoolean("USER_INFO", false));
                            Log.d(TAG, "MESSAGE : " + sharedPreferences.getString("USER_ID_NUM", ""));
                            Log.d(TAG, "TIMES : " + sharedPreferences.getString("TIMES", ""));
                            Log.d(TAG, "WEEK_NUMBER : " + sharedPreferences.getString("WEEK_NUMBER", ""));
                            Log.d(TAG, "MESSAGE : " + sharedPreferences.getString("MESSAGE", ""));
                            Log.d(TAG, "ENABLE : " + sharedPreferences.getString("ENABLE", "false"));
                            Log.d(TAG, "ALERT : " + sharedPreferences.getString("ALERT", "false"));
                        } else {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                };



                HttpPost httpPost = new HttpPost(stringUrl, params, httpHandler);
                httpPost.start();




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

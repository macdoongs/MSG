package com.korchid.msg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mac0314 on 2016-11-30.
 */

public class MessageSettingActivity extends AppCompatActivity {
    private static final String TAG = "MessageSettingActivity";
    private String nickname;
    private String title;

   ArrayList<Object> settingArrayList;
    private SettingAdapter settingAdapter;

    private MessageSetting.Type type;

    static final int REQUEST_ADD_POLITE = 0;
    static final int REQUEST_ADD_IMPOLITE = 1;

    static int num = 0;

    Handler handler = null;

    MessageSettingHandler messageSettingHandler = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        Intent intent = getIntent();
        // Enum data - intent
        type = (MessageSetting.Type) intent.getSerializableExtra("Type");

        if(type == null){
            type = MessageSetting.Type.POLITE;
        }

        Log.d(TAG, "Type : " + type);

        setList(type);
    }

    private void setList(final MessageSetting.Type type){
        Log.d(TAG, "setList");


        settingArrayList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "thread");
                HttpURLConnection connection;

                URL url = null;
                String response = null;

                try {
                    url = new URL("https://www.korchid.com/msg-reservation");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    String line = "";

                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();

                    int i = 0;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                        // From Thread to Activity
                        Message message = Message.obtain(messageSettingHandler);
                        Bundle data = new Bundle();
                        data.putString("line", line);
                        message.setData(data);
                        messageSettingHandler.sendMessage(message);



//
//
//                        //Log.d(TAG, "typeNum : " + typeNum + ", time : " + time + ", title : " + title);
//
//
//                        switch (type){
//                            case POLITE:
//                            {
//                                Log.d(TAG, "POLITE");
//                                if(typeNum.equals("0")){
//
//                                }
//                                break;
//
//                            }
//                            case IMPOLITE:
//                            {
//                                Log.d(TAG, "IMPOLITE");
//                                if(typeNum.equals("1")){
//
//                                }
//                                break;
//                            }
//                            case IN_PERSON:
//                            {
//
//                            }
//                            default:
//                            {
//                                break;
//                            }
//                        }
//
//                        i++;

                        //Log.d(TAG, line);
                    }
                    isr.close();
                    reader.close();

                    //Log.d(TAG, sb.toString());

                } catch (IOException e) {
                    // Error
                    Log.e(TAG, "IOException : " + e.getMessage());
                } catch (Exception e) {
                    Log.e(TAG, "Exception : " + e.getMessage());
                    e.printStackTrace();
                }

            }
        });

        thread.start();

        ListView lv_setting = (ListView)findViewById(R.id.lv_setting);
/*
        if(type == MessageSetting.Type.POLITE){
            settingArrayList.add(new MessageSetting(0, "@drawable/plane", type, "엄마 뭐하세요?", "2016-12-15 04:10"));

            settingArrayList.add(new MessageSetting(1, "@drawable/plane", type, "엄마 어디 아픈데 없죠?", "2016-12-15 04:10"));

            settingArrayList.add(new MessageSetting(2, "@drawable/plane", type, "엄마 뭐 필요한거 있어요?", "2016-12-15 04:10"));

            settingArrayList.add(new MessageSetting(3, "@drawable/plane", type, "엄마 요새 바쁘세요?", "2016-12-15 04:10"));
        }else if(type == MessageSetting.Type.IMPOLITE){
            settingArrayList.add(new MessageSetting(0, "@drawable/plane", type, "엄마 뭐해?", "2016-12-15 04:10"));

            settingArrayList.add(new MessageSetting(1, "@drawable/plane", type, "엄마 어디 아픈데 없지?", "2016-12-15 04:10"));

            settingArrayList.add(new MessageSetting(2, "@drawable/plane", type, "엄마 뭐 필요한거 있어?", "2016-12-15 04:10"));

            settingArrayList.add(new MessageSetting(3, "@drawable/plane", type, "엄마 요새 바빠?", "2016-12-15 04:10"));
        }else if(type == MessageSetting.Type.IN_PERSON){

        }*/


        num = 0;

        messageSettingHandler = new MessageSettingHandler();

/*
        handler = new Handler(){
            public void handleMessage (Message message){
                Log.d(TAG, "handleMessage");

                String line = message.getData().getString("line");
                Log.d(TAG, line);

                String[] ReturnList = line.split(",");
                String typeNum = ReturnList[0];
                String time = ReturnList[1];
                String title = ReturnList[2];

                switch (type){
                    case POLITE:{
                        if(typeNum.equals("0")){
                            Log.d(TAG, "Add typeNum 0");
                            settingArrayList.add(new MessageSetting(num, "@drawable/plane", type, title, time));
                        }
                        break;
                    }
                    case IMPOLITE:{
                        if(typeNum.equals("1")){
                            Log.d(TAG, "Add typeNum 1");
                            settingArrayList.add(new MessageSetting(num, "@drawable/plane", type, title, time));
                        }
                        break;
                    }
                    case IN_PERSON:{
                        break;
                    }
                }


                num++;
            }
        };
*/

        for(int i=0; i<settingArrayList.size(); i++){
            Log.d(TAG, "List " + i + "th : " + settingArrayList.get(i).toString());
        }


        settingAdapter = new SettingAdapter(MessageSettingActivity.this,  settingArrayList, SettingAdapter.Type.MESSAGE_SETTING);

        lv_setting.setAdapter(settingAdapter);

    }

    class MessageSettingHandler extends Handler{
        ArrayList messageSettingArrayList;

        public MessageSettingHandler() {
            super();
            settingArrayList = new ArrayList();
        }

        public void handleMessage (Message message){
            Log.d(TAG, "handleMessage");

            String line = message.getData().getString("line");
            Log.d(TAG, line);

            String[] ReturnList = line.split(",");
            String typeNum = ReturnList[0];
            String time = ReturnList[1];
            String title = ReturnList[2];


            Log.d(TAG, "" + settingArrayList.size());
            switch (type){
                case POLITE:{
                    if(typeNum.equals("0")){
                        Log.d(TAG, "Add typeNum 0");
                        settingArrayList.add(new MessageSetting(num, "@drawable/plane", type, title, time));
                        Log.d(TAG, "" + settingArrayList.size());
                    }
                    break;
                }
                case IMPOLITE:{
                    if(typeNum.equals("1")){
                        Log.d(TAG, "Add typeNum 1");
                        settingArrayList.add(new MessageSetting(num, "@drawable/plane", type, title, time));
                    }
                    break;
                }
                case IN_PERSON:{
                    break;
                }
            }


            num++;

            listUpdate();
        }

    }

    public void listUpdate(){
        Log.d(TAG, "listUpdate");
        settingAdapter.notifyDataSetChanged(); //​리스트뷰 값들의 변화가 있을때 아이템들을 다시 배치 할 때 사용되는 메소드입니다.
    }
}
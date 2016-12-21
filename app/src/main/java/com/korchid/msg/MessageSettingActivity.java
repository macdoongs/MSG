package com.korchid.msg;

import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

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
    public ArrayList<String> messageArrayList;
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

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Message setting");

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

        messageSettingHandler = new MessageSettingHandler();




        settingAdapter = new SettingAdapter(MessageSettingActivity.this,  settingArrayList, SettingAdapter.Type.MESSAGE_SETTING);


        lv_setting.setAdapter(settingAdapter);


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
                        settingArrayList.add(new MessageSetting(num, "@drawable/plane", type, title, time, false));
                        Log.d(TAG, "" + settingArrayList.size());
                    }
                    break;
                }
                case IMPOLITE:{
                    if(typeNum.equals("1")){
                        Log.d(TAG, "Add typeNum 1");
                        settingArrayList.add(new MessageSetting(num, "@drawable/plane", type, title, time, false));
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
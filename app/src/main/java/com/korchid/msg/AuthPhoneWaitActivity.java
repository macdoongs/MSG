package com.korchid.msg;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthPhoneWaitActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "AuthPhoneWaitActivity";
    Button btn_confirm;
    Button btn_reSend;
    EditText et_authCode;

    String sms_token = "dNb4wEIZKv8:APA91bGwcxOlb-_kLZCbNzR6GVxTh9CW7Hb8mnTmo1iBp_Vfr0UEWe3ZsLL6vv02bMMLpi27hL6A57dCRJaFG5Cy4k-kc6QN8ecoT5Uf8V4jzT6J5qkBdZ8ZQoC4O-WgJt566NL-5AnE";
    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    SMSReceiver smsReceiver;
    String smsMessage;
    String phoneNumber;
    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone_wait);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content);

        final String userId = getIntent().getExtras().getString("phoneNumber");
        phoneNumber = userId;

        smsMessage = "";

        Log.d(TAG, "phoneNumber : " + phoneNumber);

        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

        btn_reSend = (Button) findViewById(R.id.btn_reSend);
        btn_reSend.setOnClickListener(this);

        et_authCode = (EditText) findViewById(R.id.et_authCode);

        smsReceiver = new SMSReceiver();
        IntentFilter intentFilter = new IntentFilter(SMS_RECEIVED);
        registerReceiver(smsReceiver, intentFilter);

        String url = "https://www.korchid.com/sms-sender/";

        HashMap<String, String> params = new HashMap<>();
        params.put("phoneNumber", phoneNumber);
        params.put("token", sms_token);


        HttpPost httpPost = new HttpPost(url, params, new Handler());
        httpPost.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( smsReceiver != null ) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_confirm:{
                String url = "https://www.korchid.com/sms-check";
                String authCode = et_authCode.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("phoneNumber", phoneNumber);
                params.put("authCode", authCode);

                Handler httpHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        String response = msg.getData().getString("response");

                        Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                        String[] line = response.split("\n");

                        if(line[0].equals("OK")){
                            Intent intent = new Intent(getApplicationContext(), JoinPhoneActivity.class);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivityForResult(intent, 0);
                        }else{
                            Toast.makeText(getApplicationContext(), "Check your Auth code" + response, Toast.LENGTH_LONG).show();
                        }
                    }
                };

                HttpPost httpPost = new HttpPost(url, params, httpHandler);
                httpPost.start();

                break;
            }
            case R.id.btn_reSend:{
                AlertDialog.Builder builder = new AlertDialog.Builder(AuthPhoneWaitActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Send auth sms message.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "https://www.korchid.com/sms-sender/";

                        HashMap<String, String> params = new HashMap<>();
                        params.put("phoneNumber", phoneNumber);
                        params.put("token", sms_token);


                        HttpPost httpPost = new HttpPost(url, params, new Handler());
                        httpPost.start();
                    }
                });
                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case RESULT_OK:{
                Intent intent = new Intent();
                //intent.putExtra("result_msg", "결과가 넘어간다 얍!");
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            default:{
                break;
            }
        }
    }


    public class SMSReceiver  extends BroadcastReceiver {
        /**
         * 로깅을 위한 태그
         */
        public static final String TAG = "SMSBroadcastReceiver";

        /**
         * 시간 포맷을 위한 형식
         */
        public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive() 메소드 호출됨.");

            // SMS 수신 시의 메시지인지 다시 한번 확인합니다.
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Log.i(TAG, "SMS를 수신하였습니다.");

                // SMS 메시지를 파싱합니다.
                Bundle bundle = intent.getExtras();
                Object[] objs = (Object[])bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[objs.length];

                int smsCount = objs.length;
                for(int i = 0; i < smsCount; i++) {
                    // PDU 포맷으로 되어 있는 메시지를 복원합니다.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // API 23 이상
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
                    }
                }

                // SMS 수신 시간 확인
                Date receivedDate = new Date(messages[0].getTimestampMillis());
                Log.i(TAG, "SMS received date : " + receivedDate.toString());

                // SMS 발신 번호 확인
                String sender = messages[0].getOriginatingAddress();
                Log.i(TAG, "SMS sender : " + sender);

                // SMS 메시지 확인
                String contents = messages[0].getMessageBody().toString();
                Log.i(TAG, "SMS contents : " + contents);
                smsMessage = contents;

                et_authCode.setText(smsMessage);

            }

        }
    }
}

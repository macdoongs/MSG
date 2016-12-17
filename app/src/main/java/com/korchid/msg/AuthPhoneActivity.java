package com.korchid.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthPhoneActivity extends AppCompatActivity{
    private static String TAG = "AuthPhoneActivity";

    private EditText et_phoneNumber;
    private Button btn_register;
    private Button btn_dupCheck;

    private String phoneNumber = "";
    private int viewId;
    private boolean isDuplicate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content);

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);

        btn_dupCheck = (Button) findViewById(R.id.btn_dupCheck);
        btn_dupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = et_phoneNumber.getText().toString();

                if(phoneNumber.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuthPhoneActivity.this);

                    builder.setTitle("Warning");
                    builder.setMessage("Check your phone number.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    Handler httpHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            String response = msg.getData().getString("response");

                            String[] line = response.split("\n");

                            //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                            if(line[0].equals("No")){
                                Toast.makeText(getApplicationContext(), "This ID is available.", Toast.LENGTH_LONG).show();
                                isDuplicate = false;
                            }else{
                                Toast.makeText(getApplicationContext(), "Already join!", Toast.LENGTH_LONG).show();
                                isDuplicate = true;
                            }
                        }
                    };

                    HttpGet httpGet = new HttpGet("https://www.korchid.com/msg-signup/" + phoneNumber, httpHandler);
                    httpGet.start();
                }

            }
        });

        btn_register = (Button) findViewById(R.id.btn_sendSMS);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Develop mode
                //isDuplicate = false;

                if(isDuplicate){
                    Toast.makeText(getApplicationContext(), "Please duplicate check", Toast.LENGTH_LONG).show();
                }else {
                    phoneNumber = et_phoneNumber.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(AuthPhoneActivity.this);

                    if (phoneNumber.equals("")) {
                        builder.setTitle("Warning");
                        builder.setMessage("Check your phone number.");
                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                    } else {
                        builder.setTitle("Confirm");
                        builder.setMessage("Send auth sms message.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(), "Phone Number : " + phoneNumber + " Password : " + password, Toast.LENGTH_LONG).show();

                                // DB check


                                Intent intent = new Intent(getApplicationContext(), AuthPhoneWaitActivity.class);
                                intent.putExtra("phoneNumber", phoneNumber);

                                startActivityForResult(intent, 0);
                            }
                        });
                        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                    }

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }   // onCreate End

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
    } // onActivityResult End

    public class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener(){
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    System.out.println("incomingNumber : "+incomingNumber);

                    phoneNumber = incomingNumber;
                }
            },PhoneStateListener.LISTEN_CALL_STATE);
        }
    }


}

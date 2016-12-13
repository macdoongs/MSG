package com.korchid.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthPhoneActivity extends AppCompatActivity {
    private EditText et_phoneNumber;
    private EditText et_password;
    private Button btn_back;
    private Button btn_register;

    String phoneNumber = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        btn_back = (Button) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register = (Button) findViewById(R.id.btn_sendSMS);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = et_phoneNumber.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(AuthPhoneActivity.this);

                if(phoneNumber.equals("")){
                    builder.setTitle("Warning");
                    builder.setMessage("Check your phone number.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else{
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
        });
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

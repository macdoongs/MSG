package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SMSAuth extends AppCompatActivity {
    SMSReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsauth);

        smsReceiver = new SMSReceiver();


    }
}

package com.korchid.msg;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kakao.kakaolink.AppActionBuilder;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity {
    private static final String TAG = "InviteActivity";

    private Button btn_inviteParent;
    private Button btn_inviteChild;
    private Button btn_send;
    private Button btn_contactList;
    private Button btn_kakaoLink;

    private TextView tv_role;
    private EditText et_nickname;
    private EditText et_phoneNumber;

    private KakaoLink kakaoLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);


        btn_contactList = (Button) findViewById(R.id.btn_contactList);
        btn_contactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
            }


        });

        btn_inviteParent = (Button) findViewById(R.id.btn_inviteParent);
        btn_inviteParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_role.setText("Invite Parent.");
            }
        });

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = et_nickname.getText().toString();
                String phoneNumber = et_phoneNumber.getText().toString();
                String message = "Invite MSG.  http://www.korchid.com/dropbox-release";

                // SMS Compose
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", message);
                startActivity(intent);

                /*
                // SMS Auto-sender
                SmsManager smsManager =     SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                */
            }
        });

        btn_inviteChild = (Button) findViewById(R.id.btn_inviteChild);
        btn_inviteChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_role.setText("Invite Child.");
            }
        });

        btn_kakaoLink = (Button) findViewById(R.id.btn_kakaoLink);
        btn_kakaoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KakaoLinkActivity.class);
                startActivity(intent);
            }
        });


        tv_role = (TextView) findViewById(R.id.et_role);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);



        ContactUtil contactUtil = new ContactUtil(getApplicationContext());

        ArrayList<Contact> contactList = contactUtil.getContactList();

        for(int i=0; i<contactList.size(); i++){
            Log.d("CONTACT", contactList.get(i).getName());
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();

            et_nickname.setText(cursor.getString(0));   // contact - name
            et_phoneNumber.setText(cursor.getString(1));    // contact - phone number
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

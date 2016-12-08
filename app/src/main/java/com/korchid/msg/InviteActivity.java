package com.korchid.msg;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity {
    Button btn_inviteParent;
    Button btn_inviteChild;
    Button btn_send;
    Button btn_contactList;
    TextView tv_role;
    EditText et_phoneNumber;

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
                //TODO invitation message send

            }
        });

        btn_inviteChild = (Button) findViewById(R.id.btn_inviteChild);
        btn_inviteChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_role.setText("Invite Child.");
            }
        });


        tv_role = (TextView) findViewById(R.id.et_role);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);



        ContactUtil contactUtil = new ContactUtil(getApplicationContext());

        ArrayList<Contact> contactList = contactUtil.getContactList();

        for(int i=0; i<contactList.size(); i++){
            Log.e("CONTACT", contactList.get(i).getName());
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

            et_phoneNumber.setText(cursor.getString(1));
            //cursor.getString(0);        //이름 얻어오기
            //cursor.getString(1);     //번호 얻어오기
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

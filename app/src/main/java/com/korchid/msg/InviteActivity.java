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
import android.widget.TextView;

import java.util.ArrayList;

public class InviteActivity extends AppCompatActivity {
    Button contactList;
    TextView mName;
    TextView mNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        mName = (TextView) findViewById(R.id.mName);
        mNumber = (TextView) findViewById(R.id.mNumber);
        contactList = (Button) findViewById(R.id.contact_list);

        contactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
            }


        });



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
            mName.setText(cursor.getString(0));        //이름 얻어오기
            mNumber.setText(cursor.getString(1));     //번호 얻어오기
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

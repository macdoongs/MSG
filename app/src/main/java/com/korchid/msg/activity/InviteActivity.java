package com.korchid.msg.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kakao.kakaolink.KakaoLink;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener{
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

    private String userRole = "child";
    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Invite");

        btn_contactList = (Button) findViewById(R.id.btn_contactList);
        btn_contactList.setOnClickListener(this);

        btn_inviteParent = (Button) findViewById(R.id.btn_inviteParent);
        btn_inviteParent.setOnClickListener(this);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        btn_inviteChild = (Button) findViewById(R.id.btn_inviteChild);
        btn_inviteChild.setOnClickListener(this);

        btn_kakaoLink = (Button) findViewById(R.id.btn_kakaoLink);
        btn_kakaoLink.setOnClickListener(this);


        tv_role = (TextView) findViewById(R.id.et_role);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);

        btn_send.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                String input2 = et_nickname.getText().toString();

                if(input.length() > 0 && input2.length() > 0){
                    btn_send.setEnabled(true);
                    btn_send.setBackgroundResource(R.color.colorPrimary);
                }else{
                    btn_send.setEnabled(false);
                    btn_send.setBackgroundResource(R.color.colorTransparent);
                }
            }
        };

        et_phoneNumber.addTextChangedListener(textWatcher);

        /*
        ContactUtil contactUtil = new ContactUtil(getApplicationContext());

        ArrayList<Contact> contactList = contactUtil.getContactList();

        for(int i=0; i<contactList.size(); i++){
            Log.d("CONTACT", contactList.get(i).getName());
        }
        */
    }


    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_contactList:{
                // Show phone contact list
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.btn_inviteParent:{
                userRole = "child";
                tv_role.setText("Invite Parent.");
                btn_inviteParent.setBackgroundResource(R.color.colorPrimary);
                btn_inviteChild.setBackgroundResource(R.color.colorTransparent);
                break;
            }
            case R.id.btn_inviteChild:{
                userRole = "parent";
                tv_role.setText("Invite Child.");
                btn_inviteParent.setBackgroundResource(R.color.colorTransparent);
                btn_inviteChild.setBackgroundResource(R.color.colorPrimary);
                break;
            }
            case R.id.btn_send:{
                AlertDialog.Builder builder = new AlertDialog.Builder(InviteActivity.this);

                builder.setTitle("재확인");
                builder.setMessage("초대 메시지를 보내시겠습니까?");
                builder.setPositiveButton("네!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickname = et_nickname.getText().toString();
                        String parentPhoneNumber = et_phoneNumber.getText().toString();
                        String message = "혜윰 초대해요~ 연락 자주하고 싶어요!! http://www.korchid.com/dropbox-release";


                        // SMS Compose
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + parentPhoneNumber));
                        intent.putExtra("sms_body", message);
                        startActivity(intent);

/*
                        // TODO convert function
                        // SMS Auto-sender
                        Intent intent;
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
*/

                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(USER_ROLE, userRole);
                        Log.d(TAG, "USER_ROLE : " + userRole);

                        editor.commit();

                        long waitTime = System.currentTimeMillis();
                        intent = new Intent(getApplicationContext(), KakaoLinkActivity.class);
                        intent.putExtra("parentPhoneNumber", parentPhoneNumber);
                        intent.putExtra("waitTime", waitTime);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("아니오..", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();



                break;
            }
            case R.id.btn_kakaoLink:{
                // Wait to connect parent, child and send K
                Intent intent = new Intent(getApplicationContext(), KakaoLinkActivity.class);
                startActivity(intent);
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

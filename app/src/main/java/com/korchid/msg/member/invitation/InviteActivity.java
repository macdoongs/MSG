package com.korchid.msg.member.invitation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.KakaoLink;
import com.korchid.msg.R;
import com.korchid.msg.storage.server.retrofit.RestfulAdapter;
import com.korchid.msg.storage.server.retrofit.response.Res;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.ui.StatusBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.INVITATION_CHECK;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_CONNECTION;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.USER_ID_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;

public class InviteActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "InviteActivity";

    private Spinner sp_nationCode;

    private Button btn_inviteParent;
    private Button btn_inviteChild;
    private Button btn_send;
    private Button btn_contactList;
    private Button btn_kakaoLink;

    private TextView tv_role;
    private EditText et_nickname;
    private EditText et_phoneNumber;

    private KakaoLink kakaoLink;

    private Boolean isChangedEtNickname = false;
    private Boolean isChangedEtPhoneNumber = false;

    private int userId;
    private String userRole = "child";
    private String nationCode = "";
    private String opponentUserName = "";
    private String opponentUserPhoneNumber = "";
    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        userId = getIntent().getIntExtra(USER_ID_NUMBER, 0);

        initView();




        /*
        // Log console - Contact
        ContactUtil contactUtil = new ContactUtil(getApplicationContext());

        ArrayList<Contact> contactList = contactUtil.getContactList();

        for(int i=0; i<contactList.size(); i++){
            Log.d("CONTACT", contactList.get(i).getName());
        }
        */
    }

    private void initView(){
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

        // if EditText is Blank, it doesn't work.
        btn_send.setEnabled(false);

        TextWatcher etNicknameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                isChangedEtNickname = true;

                if(input.length() > 0){
                    isChangedEtNickname = true;
                }else{
                    isChangedEtNickname = false;
                }

                if(isChangedEtNickname && isChangedEtPhoneNumber){
                    btn_send.setEnabled(true);
                    btn_send.setBackgroundResource(R.drawable.rounded_button_p_2r);
                }else{
                    btn_send.setEnabled(false);
                    btn_send.setBackgroundResource(R.drawable.rounded_button_t_2r);
                }
            }
        };

        et_nickname.addTextChangedListener(etNicknameWatcher);

        TextWatcher etPhoneWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();

                if(input.length() > 0){
                    isChangedEtPhoneNumber = true;
                }else{
                    isChangedEtPhoneNumber = false;
                }

                if(isChangedEtNickname && isChangedEtPhoneNumber){
                    btn_send.setEnabled(true);
                    btn_send.setBackgroundResource(R.color.colorPrimary);
                }else{
                    btn_send.setEnabled(false);
                    btn_send.setBackgroundResource(R.color.colorTransparent);
                }
            }
        };

        et_phoneNumber.addTextChangedListener(etPhoneWatcher);

        // Setting nation code spinner
        final String[] option = getResources().getStringArray(R.array.spinnerNationCode);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, option);
        sp_nationCode = (Spinner) findViewById(R.id.sp_nationCode);
        sp_nationCode.setAdapter(arrayAdapter);
        sp_nationCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), option[i], Toast.LENGTH_LONG).show();
                String content = option[i];
                // Delete nation name
                nationCode = content.split(" ")[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_CONNECTION, 0);
        Boolean inviteCheck = sharedPreferences.getBoolean(INVITATION_CHECK, false);

        if(inviteCheck){
            btn_kakaoLink.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        // Button onClick function
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_contactList:{
                int requestCode = 0;

                // Show phone contact list
                // User pick one contact
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

                startActivityForResult(intent, requestCode);
                break;
            }
            case R.id.btn_inviteParent:{
                // Invite parent
                // User role : child
                userRole = "child";
                tv_role.setText("함께 이용할 부모님을 초대해주세요.");
                btn_inviteParent.setBackgroundResource(R.drawable.rounded_button_p_2r);
                btn_inviteParent.setTextColor(Color.parseColor("#e6000000"));
                btn_inviteChild.setBackgroundResource(R.drawable.rounded_button_t_2r);
                btn_inviteChild.setTextColor(Color.parseColor("#40000000"));
                break;
            }
            case R.id.btn_inviteChild:{
                // Invite child
                // User role : parent
                userRole = "parent";
                tv_role.setText("함께 이용할 자녀분을 초대해주세요.");
                btn_inviteParent.setBackgroundResource(R.drawable.rounded_button_t_2r);
                btn_inviteParent.setTextColor(Color.parseColor("#40000000"));
                btn_inviteChild.setBackgroundResource(R.drawable.rounded_button_p_2r);
                btn_inviteChild.setTextColor(Color.parseColor("#e6000000"));
                break;
            }
            case R.id.btn_send:{
                // check if user want to send invitation sms message
                AlertDialog.Builder builder = new AlertDialog.Builder(InviteActivity.this);

                builder.setTitle("재확인");
                builder.setMessage("초대 메시지를 보내시겠습니까?");
                builder.setPositiveButton("네!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        opponentUserName = et_nickname.getText().toString();
                        opponentUserPhoneNumber = et_phoneNumber.getText().toString();
                        String opponentInternationalPhoneNumber = nationCode + opponentUserPhoneNumber.substring(1); // Remove phoneNumber idx 0;


                        String inviteTime = getCurrentTimeStamp();

                        Call<Res> userCall = RestfulAdapter.getInstance().userInvitation(userId, opponentInternationalPhoneNumber, userRole);

                        userCall.enqueue(new Callback<Res>() {
                            @Override
                            public void onResponse(Call<Res> call, Response<Res> response) {
                                Res res = response.body();

                                Log.d(TAG, "response : " + res.toString());

                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString(USER_ROLE, userRole);
                                Log.d(TAG, "USER_ROLE : " + userRole);

                                editor.apply();

                            }

                            @Override
                            public void onFailure(Call<Res> call, Throwable t) {
                                Log.d(TAG, "onFailure");
                            }
                        });


                        Intent intent = new Intent(getApplicationContext(), KakaoLinkActivity.class);

                        intent.putExtra(USER_ROLE, userRole);
                        intent.putExtra("receiverNickname", opponentUserName);
                        intent.putExtra("receiverPhoneNumber", opponentInternationalPhoneNumber);
                        intent.putExtra("waitTime", inviteTime);

                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_CONNECTION, 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putBoolean(INVITATION_CHECK, true);

                        editor.apply();

                        startActivityForResult(intent, 2);

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
                int requestCode = 1;

                // Wait to connect parent, child and send Kakao link
                Intent intent = new Intent(getApplicationContext(), KakaoLinkActivity.class);

                intent.putExtra(USER_ID_NUMBER, userId);
                intent.putExtra(USER_ROLE, userRole);
                intent.putExtra("receiverNickname", "");
                intent.putExtra("receiverPhoneNumber", "");
                intent.putExtra("waitTime", "");


                startActivityForResult(intent, requestCode);
                break;
            }
            default:{
                break;
            }
        }
    }


    // http://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar menu click event

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                // Back
                this.finish();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode){
            case RESULT_OK:{
                switch (requestCode){
                    // Contact list
                    case 0:{
                        Cursor cursor = getContentResolver().query(data.getData(),
                                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                        cursor.moveToFirst();

                        // Set the contact in EditText
                        et_nickname.setText(cursor.getString(0));   // contact - name
                        et_phoneNumber.setText(cursor.getString(1));    // contact - phone number
                        cursor.close();

                        break;
                    }
                    case 1:{

                    }
                    case 2:{
                        setResult(RESULT_OK, data);

                        finish();
                    }
                    default:{
                        break;
                    }

                }

            }
            case RESULT_CANCELED:{
                switch (requestCode){

                }
            }
            default:{
                break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}

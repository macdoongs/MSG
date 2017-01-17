package com.korchid.msg.member.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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
import android.widget.Toast;

import com.korchid.msg.R;
import com.korchid.msg.storage.server.retrofit.RestfulAdapter;

import com.korchid.msg.storage.server.retrofit.response.DuplicateCheck;
import com.korchid.msg.storage.server.retrofit.response.User;
import com.korchid.msg.ui.CustomActionbar;

import com.korchid.msg.ui.StatusBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.AUTH_MODE;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;

// SMS authentification - request sms message
public class AuthPhoneActivity extends AppCompatActivity{
    private static String TAG = "AuthPhoneActivity";
    private static final int NUMBER_MIN_LENGTH = 6;

    private EditText et_phoneNumber;
    private Button btn_register;
    private Button btn_dupCheck;
    private Spinner sp_nationCode;

    private String phoneNumber = "";
    private String internationalPhoneNumber = "";
    private String nationCode = "";
    private int viewId;
    private boolean isDuplicateCheck = false; // false - No check state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);

        initView();


    }   // onCreate End

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Phone Number");

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);

        btn_register = (Button) findViewById(R.id.btn_sendSMS);
        btn_dupCheck = (Button) findViewById(R.id.btn_dupCheck);

        // Setting nation code spinner
        final String[] option = getResources().getStringArray(R.array.spinnerNationCode);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, option);
        sp_nationCode = (Spinner) findViewById(R.id.sp_nationCode);
        sp_nationCode.setAdapter(arrayAdapter);
        sp_nationCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), option[i], Toast.LENGTH_LONG).show();
                String content = option[i];
                // Delete nation name
                nationCode = content.split(" ")[0];
                isDuplicateCheck = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_dupCheck.setEnabled(false);
        btn_register.setEnabled(false);

        // http://egloos.zum.com/killins/v/3008925
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

                if(input.length() > 0){
                    btn_dupCheck.setEnabled(true);
                    btn_dupCheck.setBackgroundResource(R.drawable.rounded_button_p_2r);
                }else{
                    btn_dupCheck.setEnabled(false);
                    btn_register.setEnabled(false);
                }
            }
        };

        et_phoneNumber.addTextChangedListener(textWatcher);

        btn_dupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = et_phoneNumber.getText().toString();

                phoneNumber = phoneNumber.trim();

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
                }else if(phoneNumber.length() < NUMBER_MIN_LENGTH){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuthPhoneActivity.this);

                    builder.setTitle("Warning");
                    builder.setMessage("Min length : 7");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    String fullPhoneNumber = nationCode + phoneNumber.substring(1); // Remove phoneNumber idx 0

                    String[] stringArray = fullPhoneNumber.split("-");
                    internationalPhoneNumber = "";
                    for(int i=0; i<stringArray.length; i++){
                        if(stringArray[i] != null){
                            internationalPhoneNumber += stringArray[i];
                        }
                    }
                    Log.d(TAG, "internationalPhoneNumber" + internationalPhoneNumber);

                    Toast.makeText(getApplicationContext(), internationalPhoneNumber, Toast.LENGTH_SHORT).show();

                    Call<DuplicateCheck> userDuplicateCheck = RestfulAdapter.getInstance().duplicateUserCheck(internationalPhoneNumber);

                    userDuplicateCheck.enqueue(new Callback<DuplicateCheck>() {
                        @Override
                        public void onResponse(Call<DuplicateCheck> call, Response<DuplicateCheck> response) {
                            Log.d(TAG, "response : " + response.body());
                            Boolean isDuplicate = response.body().getDuplicate();
                            if(isDuplicate){
                                Toast.makeText(getApplicationContext(), "이미 가입된 번호입니다.", Toast.LENGTH_LONG).show();
                                isDuplicateCheck = false;
                            }else{
                                Toast.makeText(getApplicationContext(), "이 아이디는 사용 가능합니다.", Toast.LENGTH_LONG).show();
                                isDuplicateCheck = true;

                                btn_register.setBackgroundResource(R.drawable.rounded_button_p_2r);
                                btn_register.setEnabled(true);
                            }

                        }

                        @Override
                        public void onFailure(Call<DuplicateCheck> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onFailure");
                        }
                    });

                }

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Develop mode
                //isDuplicateCheck = false;

                if(isDuplicateCheck){
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

                                Intent intent = new Intent(getApplicationContext(), AuthPhoneWaitActivity.class);
                                intent.putExtra(AUTH_MODE, "join");
                                intent.putExtra(USER_PHONE_NUMBER, internationalPhoneNumber);

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
                }else {
                    Toast.makeText(getApplicationContext(), "Please duplicate check", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                intent.putExtra(USER_LOGIN_STATE, "LOGIN");
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

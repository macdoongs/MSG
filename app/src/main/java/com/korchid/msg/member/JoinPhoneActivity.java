package com.korchid.msg.member;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.korchid.msg.storage.server.retrofit.RestfulAdapter;
import com.korchid.msg.storage.server.retrofit.response.Res;
import com.korchid.msg.storage.server.retrofit.response.Signup;
import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;

// Register phone number and password
public class JoinPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "JoinPhoneActivity";

    private TextView tv_phoneNumber;

    private EditText et_password;
    private EditText et_passwordConfirm;
    private Button btn_register;

    private String phoneNumber = "";
    private String password = "";
    private String passwordConfirm = "";

    private int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_phone);

        initView();

        phoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        tv_phoneNumber.setText(phoneNumber);


    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);
        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Join");

        tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordConfirm = (EditText) findViewById(R.id.et_passwordConfirm);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);

        btn_register.setEnabled(false);

        et_passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                String input2 = et_password.getText().toString();

                if(input.length() > 0 && input2.length() > 0){
                    btn_register.setEnabled(true);
                    btn_register.setBackgroundResource(R.drawable.rounded_button_p_2r);
                }else{
                    btn_register.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_register:{
                password = et_password.getText().toString();
                passwordConfirm = et_passwordConfirm.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(JoinPhoneActivity.this);

                if(phoneNumber.equals("")){
                    builder.setTitle("Warning");
                    builder.setMessage("Check your phone number.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else if(password.equals("")) {
                    builder.setTitle("Warning");
                    builder.setMessage("Check your password.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else if(!password.equals(passwordConfirm)) {
                    builder.setTitle("Don't match");
                    builder.setMessage("Check your confirm password.");
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
                        public void onClick(final DialogInterface dialog, int which) {

                            Call<Signup> userDataCall = RestfulAdapter.getInstance().userSignup(phoneNumber, password);

                            userDataCall.enqueue(new Callback<Signup>() {
                                @Override
                                public void onResponse(Call<Signup> call, Response<Signup> response) {
                                    Log.d(TAG, "response : " + response.body());
                                    if(response.body().getDuplicate()){
                                        Toast.makeText(getApplicationContext(), "이 아이디는 이미 사용 중입니다.", Toast.LENGTH_LONG).show();
                                    }else{
                                        if(response.body().getSignup()){
                                            Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<Signup> call, Throwable t) {
                                    Log.d(TAG, "onFailure");
                                    Toast.makeText(getApplicationContext(), "잠시 후 다시 시도하세요.", Toast.LENGTH_LONG).show();
                                }
                            });

                            Intent intent = new Intent();
                            //intent.putExtra("result_msg", "Example");
                            setResult(RESULT_OK, intent);
                            finish();
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
            default:{
                break;
            }
        }
    }// onClick End



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
}

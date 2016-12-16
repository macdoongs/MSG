package com.korchid.msg;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "JoinPhoneActivity";

    private EditText et_phoneNumber;
    private EditText et_password;
    private EditText et_passwordConfirm;
    private Button btn_back;
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

        phoneNumber = getIntent().getExtras().getString("phoneNumber");

        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        et_phoneNumber.setText(phoneNumber);

        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordConfirm = (EditText) findViewById(R.id.et_passwordConfirm);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_back:{
                finish();
                break;
            }
            case R.id.btn_register:{
                phoneNumber = et_phoneNumber.getText().toString();
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
                            //Toast.makeText(getApplicationContext(), "Phone Number : " + phoneNumber + " Password : " + password, Toast.LENGTH_LONG).show();

                            // DB check

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HttpURLConnection connection;

                                    URL url = null;
                                    String response = null;

                                    try {
                                        url = new URL("https://www.korchid.com/msg-signup/" + phoneNumber);
                                        connection = (HttpURLConnection) url.openConnection();
                                        connection.setRequestMethod("GET");

                                        String line = "";

                                        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                                        BufferedReader reader = new BufferedReader(isr);
                                        StringBuilder sb = new StringBuilder();
                                        while ((line = reader.readLine()) != null) {
                                            sb.append(line + "\n");
                                        }
                                        isr.close();
                                        reader.close();

                                        Log.d(TAG, sb.toString());

                                    } catch (IOException e) {
                                        // Error
                                        Log.e(TAG, "IOException : " + e.getMessage());
                                    } catch (Exception e) {
                                        Log.e(TAG, "Exception : " + e.getMessage());
                                        e.printStackTrace();
                                    }

                                }
                            });

                            thread.start();


                            // http://mommoo.tistory.com/38
                            // Use Environmental variable 'SharedPreference'
                            SharedPreferences sharedPreferences = getSharedPreferences("login", 0);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("USER_LOGIN", "LOGIN");
                            editor.putString("USER_PHONE", phoneNumber);
                            editor.putString("USER_PASSWORD", password);
                            editor.commit(); // Apply file

                            /*
                            // Delete preference value
                            // 1. Remove "key" data
                            editor.remove("key");

                            // 2. Remove xml data
                            editor.clear();
                            */

                            // if sharedPreferences.getString value is 0, assign 2th parameter
                            Log.d(TAG, "SharedPreference");
                            Log.d(TAG, "USER_LOGIN : " + sharedPreferences.getString("USER_LOGIN", "LOGOUT"));
                            Log.d(TAG, "USER_PHONE : " + sharedPreferences.getString("USER_PHONE", "000-0000-0000"));
                            Log.d(TAG, "USER_PASSWORD : " + sharedPreferences.getString("USER_PASSWORD", "123123"));

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
    }
}

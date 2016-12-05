package com.korchid.msg;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthPhoneActivity extends AppCompatActivity {
    EditText et_phoneNumber;
    EditText et_password;
    Button btn_register;

    String phoneNumber = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_phone);

        et_phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        et_password = (EditText) findViewById(R.id.password);
        btn_register = (Button) findViewById(R.id.registerButton);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = et_phoneNumber.getText().toString();
                password = et_password.getText().toString();

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
                }else if(password.equals("")) {
                    builder.setTitle("Warning");
                    builder.setMessage("Check your password.");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }else{
                    builder.setTitle("Confirm");
                    builder.setMessage("Do you want to join?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Phone Number : " + phoneNumber + " Password : " + password, Toast.LENGTH_LONG).show();
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
        });
    }



}

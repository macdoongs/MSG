package com.korchid.msg;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

// Setting user information
public class UserInfoActivity extends AppCompatActivity {
    private static final String TAG = "UserInfoActivity";

    ImageView iv_profile;

    RadioGroup rbtnGroup;
    RadioButton rbtn_male;
    RadioButton rbtn_female;
    RadioButton rbtn_etc;

    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "User Information");


        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        rbtnGroup = (RadioGroup) findViewById(R.id.rbtnGroup);

        rbtn_male = (RadioButton) findViewById(R.id.rbtn_male);
        rbtn_female = (RadioButton) findViewById(R.id.rbtn_female);
        rbtn_etc = (RadioButton) findViewById(R.id.rbtn_etc);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setEnabled(false);

        rbtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(!btn_register.isEnabled()) {
                    btn_register.setBackgroundResource(R.color.colorPrimary);
                    btn_register.setEnabled(true);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = rbtnGroup.getCheckedRadioButtonId();

                switch (id){
                    case R.id.rbtn_male:{
                        Toast.makeText(getApplicationContext(), "Male", Toast.LENGTH_SHORT).show();

                        break;
                    }
                    case R.id.rbtn_female:{
                        Toast.makeText(getApplicationContext(), "Female", Toast.LENGTH_SHORT).show();

                        break;
                    }
                    case R.id.rbtn_etc:{
                        Toast.makeText(getApplicationContext(), "Etc", Toast.LENGTH_SHORT).show();

                        break;
                    }
                    default:{
                        break;
                    }
                }

                Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
                startActivityForResult(intent, 0);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_next, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리

        int id = item.getItemId();

        switch (id){
            case android.R.id.home: {
                this.finish();
                break;
            }
            case R.id.itemNext:{
                if(btn_register.isEnabled()){
                    int btn_id = rbtnGroup.getCheckedRadioButtonId();

                    Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);

                    switch (btn_id){
                        case R.id.rbtn_male:{
                            Toast.makeText(getApplicationContext(), "Male", Toast.LENGTH_SHORT).show();

                            intent.putExtra("Sex", "Male");
                            break;
                        }
                        case R.id.rbtn_female:{
                            Toast.makeText(getApplicationContext(), "Female", Toast.LENGTH_SHORT).show();

                            intent.putExtra("Sex", "Female");
                            break;
                        }
                        case R.id.rbtn_etc:{
                            Toast.makeText(getApplicationContext(), "Etc", Toast.LENGTH_SHORT).show();

                            intent.putExtra("Sex", "Etc");
                            break;
                        }
                        default:{
                            break;
                        }
                    }


                    startActivityForResult(intent, 0);
                }else{
                    Toast.makeText(this.getApplicationContext(), "Check radio button", Toast.LENGTH_SHORT).show();
                }
                //

                break;
            }
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode){
            case RESULT_OK:{
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            default:{
                break;
            }
        }

    }
}


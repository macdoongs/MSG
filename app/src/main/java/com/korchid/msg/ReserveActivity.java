package com.korchid.msg;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private static final String TAG = "ReserveActivity";
    public static final int TOTAL_WEEK = 7;

    private Switch sw_enable;

    private NumberPicker np_week;
    private NumberPicker np_number;

    private Button btn_polite;
    private Button btn_impolite;
    private Button btn_inPerson;
    private Button btn_reserve;

    private int viewId;
    private int weekNum;
    private int times;
    private Boolean isEnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content);

        btn_polite = (Button) findViewById(R.id.btn_polite);
        btn_polite.setOnClickListener(this);

        btn_impolite = (Button) findViewById(R.id.btn_impolite);
        btn_impolite.setOnClickListener(this);

        btn_inPerson = (Button) findViewById(R.id.btn_inPerson);
        btn_inPerson.setOnClickListener(this);

        btn_reserve = (Button) findViewById(R.id.btn_reserve);
        btn_reserve.setOnClickListener(this);


        sw_enable = (Switch) findViewById(R.id.sw_enable);
        if (sw_enable != null) {
            sw_enable.setOnCheckedChangeListener(this);
        }

        np_week = (NumberPicker) findViewById(R.id.np_week);
        np_number = (NumberPicker) findViewById(R.id.np_number);

        np_week.setMinValue(1);
        np_week.setMaxValue(2);
        np_week.setWrapSelectorWheel(false);

        np_number.setMinValue(1);
        np_number.setMaxValue(7);
        np_number.setWrapSelectorWheel(false);

        np_week.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                weekNum = view.getValue();

                np_number.setMaxValue(TOTAL_WEEK * weekNum);
            }
        });

    }

    @Override
    public void onClick(View v) {
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_polite:{
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                intent.putExtra("Type", MessageSetting.Type.POLITE);
                startActivity(intent);
                break;
            }
            case R.id.btn_impolite:{
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                intent.putExtra("Type", MessageSetting.Type.IMPOLITE);
                startActivity(intent);
                break;
            }
            case R.id.btn_inPerson:{
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                intent.putExtra("Type", MessageSetting.Type.IN_PERSON);
                startActivity(intent);
                break;
            }
            case R.id.btn_reserve:{
                weekNum = np_week.getValue();
                times = np_number.getValue();

                GlobalApplication.getGlobalApplicationContext().setWeekNum(weekNum);
                GlobalApplication.getGlobalApplicationContext().setTimes(times);

                Log.d(TAG, "Week : " + weekNum + ", times : " + times);
                finish();
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

    public void onCheckedChanged(CompoundButton buttonView, boolean isEnable) {
        Toast.makeText(this, "The Switch is " + (isEnable ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isEnable) {
            //do stuff when Switch is ON
        } else {
            //do stuff when Switch if OFF
        }
    }

}

package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class ReserveActivity extends AppCompatActivity {
    NumberPicker weekPicker;
    NumberPicker numberPicker;

    public static final int TOTAL_WEEK = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        weekPicker = (NumberPicker) findViewById(R.id.weekPicker);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);

        weekPicker.setMinValue(1);
        weekPicker.setMaxValue(2);
        weekPicker.setWrapSelectorWheel(false);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(7);
        numberPicker.setWrapSelectorWheel(false);

        weekPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker view, int scrollState) {
                int weekNum = view.getValue();

                numberPicker.setMaxValue(TOTAL_WEEK * weekNum);
            }
        });

    }
}

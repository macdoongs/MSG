package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MSG.db", null, 1);

        // 테이블에 있는 모든 데이터 출력
        final TextView tv_result = (TextView) findViewById(R.id.result);

        final EditText et_date = (EditText) findViewById(R.id.date);
        final EditText et_item = (EditText) findViewById(R.id.item);
        final EditText et_price = (EditText) findViewById(R.id.price);

        // 날짜는 현재 날짜로 고정
        // 현재 시간 구하기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        // 출력될 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        et_date.setText(simpleDateFormat.format(date));

        // DB에 데이터 추가
        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = et_date.getText().toString();
                String item = et_item.getText().toString();
                int price = Integer.parseInt(et_price.getText().toString());

                dbHelper.insert(date, item, price);
                tv_result.setText(dbHelper.getResult());
            }
        });

        // DB에 있는 데이터 수정
        Button update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = et_item.getText().toString();
                int price = Integer.parseInt(et_price.getText().toString());

                dbHelper.update(item, price);
                tv_result.setText(dbHelper.getResult());
            }
        });

        // DB에 있는 데이터 삭제
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = et_item.getText().toString();

                dbHelper.delete(item);
                tv_result.setText(dbHelper.getResult());
            }
        });

        // DB에 있는 데이터 조회
        Button select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText(dbHelper.getResult());
            }
        });
    }
}

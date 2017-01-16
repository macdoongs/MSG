package com.korchid.msg.storage.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.text.SimpleDateFormat;
import java.util.Date;


// sqlite testing page
public class DBTest extends AppCompatActivity {
    private static final String TAG = "DBTest";

    String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "DB Test");

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MSG.db", null, 4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper.onUpgrade(db, 3, 4) ;

        // 테이블에 있는 모든 데이터 출력
        final TextView tv_result = (TextView) findViewById(R.id.result);

        final EditText et_date = (EditText) findViewById(R.id.date);
        final EditText et_senderId = (EditText) findViewById(R.id.et_senderId);
        final EditText et_senderNickname = (EditText) findViewById(R.id.et_senderNickname);
        final EditText et_receiverId = (EditText) findViewById(R.id.et_receiverId);
        final EditText et_topic = (EditText) findViewById(R.id.et_topic);
        final EditText et_content = (EditText) findViewById(R.id.et_content);

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


                int senderId = Integer.parseInt(et_senderId.getText().toString());
                String senderNickname = et_senderNickname.getText().toString();
                int receiverId = Integer.parseInt(et_receiverId.getText().toString());
                topic = et_topic.getText().toString();
                String content = et_content.getText().toString();

                dbHelper.insertChatting(senderId, senderNickname, receiverId, topic, "message", content);

                tv_result.setText(senderId + "/" + senderNickname + "/" + receiverId + "/" + topic + "/" +content);
            }
        });

        Button select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic = et_topic.getText().toString();
                tv_result.setText(dbHelper.getTableChatting(topic));

            }
        });
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
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

}

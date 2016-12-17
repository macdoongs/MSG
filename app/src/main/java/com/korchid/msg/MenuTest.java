package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

// http://skymin2.tistory.com/45
public class MenuTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        String message = "";
        int id = item.getItemId();
        switch (id){
            case R.id.mainLogo:
                message = "Main Logo";
                break;
            case R.id.mainTitle:
                message = "Main Logo";
                break;
            default:
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }
}

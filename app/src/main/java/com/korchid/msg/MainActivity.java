package com.korchid.msg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by mac0314 on 2016-11-28.
 */

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Loading screen
        startActivity(new Intent(this,SplashActivity.class));

        //Next button
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener (){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SelectParent.class));
            }
        });


    }
}

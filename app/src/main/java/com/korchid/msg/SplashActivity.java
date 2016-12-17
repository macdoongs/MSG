package com.korchid.msg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by mac0314 on 2016-11-28.
 */


// Loading screen
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    int duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StatusBar statusBar = new StatusBar(this);

        duration = getIntent().getIntExtra("duration", 1000);

        Handler handler = new Handler();
        // Duration : 2 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, duration);
    }
}

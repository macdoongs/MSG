package com.korchid.msg;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by mac0314 on 2016-12-17.
 */

public class StatusBar {
    public StatusBar(Activity activity) {

        // Status bar apply color
        // setStatusBarColor -> API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Status bar apply color
            Window window = activity.getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            int colorPrimary = activity.getResources().getColor(R.color.colorPrimary);

            window.setStatusBarColor(colorPrimary);

        }
    }
}

package com.korchid.msg.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.korchid.msg.R;

/**
 * Created by mac0314 on 2016-12-21.
 */

// For checkbox - listview interworking
public class CheckableLinearLayout extends LinearLayout implements Checkable{
    // TODO Complete code - combining Checkbox and Linearlayout is not working

    private static final String TAG = "CheckableLinearLayout";

    // 만약 CheckBox가 아닌 View를 추가한다면 아래의 변수 사용 가능.
    private boolean mIsChecked ;


    public CheckableLinearLayout(Context context) {
        super(context);

        mIsChecked = false ;
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    // http://www.androidpub.com/609065
    // http://recipes4dev.tistory.com/68
    @Override
    public void setChecked(boolean checked) { // Checked 상태를 checked 변수대로 설정.
        Log.d(TAG, "setChecked");

        CheckBox cb = (CheckBox) findViewById(R.id.cb_option) ;

        if (cb.isChecked() != checked) {
            cb.setChecked(checked) ;
        }

        // CheckBox 가 아닌 View의 상태 변경.
    }


    @Override
    public boolean isChecked() { // 현재 Checked 상태를 리턴.
        Log.d(TAG, "isChecked");
        CheckBox cb = (CheckBox) findViewById(R.id.cb_option) ;

        //return cb.isChecked() ;
        return mIsChecked ;
    }

    @Override
    public void toggle() { // 현재 Checked 상태를 바꿈. (UI에 반영)
        Log.d(TAG, "toggle");

        CheckBox cb = (CheckBox) findViewById(R.id.cb_option) ;

        //setChecked(cb.isChecked() ? false : true) ;
        setChecked(mIsChecked ? false : true) ;

    }
}

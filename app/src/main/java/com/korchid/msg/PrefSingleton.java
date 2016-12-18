package com.korchid.msg;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mac0314 on 2016-12-18.
 */

// http://stackoverflow.com/questions/11552579/is-there-a-way-to-make-sharedpreferences-global-throughout-my-whole-android-app
public class PrefSingleton {
    private static PrefSingleton mInstance;
    private Context mContext;

    private SharedPreferences mMyPreferences;

    private PrefSingleton(){

    }

    public static PrefSingleton getInstance(){
        if (mInstance == null) {
            mInstance = new PrefSingleton();
        }
        return mInstance;
    }

    public void Initialize(Context context){
        mContext = context;

        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void writePreference(String key, String value){
        SharedPreferences.Editor editor = mMyPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}

package com.korchid.msg;


import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by mac0314 on 2016-12-17.
 */

public class CustomActionbar {

    public CustomActionbar (AppCompatActivity appCompatActivity, @LayoutRes int resource, String title){

        // http://choayo.tistory.com/145
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if(resource == R.layout.actionbar_main){
            actionBar.setDisplayHomeAsUpEnabled(false);
        }else{
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle(title);

        // Set custom view layout
        View mCustomView = LayoutInflater.from(appCompatActivity).inflate(resource, null);
        actionBar.setCustomView(mCustomView);
/*
        // Set no padding both side
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        // Set actionbar background image
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
*/
        // Set actionbar layout layoutparams
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);

    }

}

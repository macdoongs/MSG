package com.korchid.msg;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SelectParent extends AppCompatActivity {
    ViewPager pager;
    SeekBar seekBar;
    Button button, button2, button3, button4;


    // Temp Data Array
    String[] parent = {"Father", "Mother", "StepMother"};
    String[] phoneNum = {"010-0000-0001", "010-0000-0002", "010-0000-0003" };
    String[] topic = {"Sajouiot03", "Sajouiot02", "Sajouiot01"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parent);

        pager = (ViewPager) findViewById(R.id.pager);

        MyAdapter adapter = new MyAdapter();
        pager.setAdapter(adapter);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(pager.getAdapter().getCount() - 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pager.setCurrentItem(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                seekBar.setProgress(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return parent.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView textview = new TextView(getApplicationContext());
            textview.setText(parent[position]);
            textview.setTextSize(40.0f);

            layout.addView(textview);

            container.addView(layout);

            return layout;
        }
    }


}

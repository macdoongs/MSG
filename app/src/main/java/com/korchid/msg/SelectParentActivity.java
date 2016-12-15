package com.korchid.msg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SelectParentActivity extends AppCompatActivity {
    private static final String TAG = "SelectParentActivity";

    private ViewPager pager;
    private SeekBar seekBar;
    private Button btn_call, btn_chat, btn_chat_setting;
    private ImageView imageView;

    // Temp Data Array
    private String[] parent = {"Father", "Mother", "StepMother"};
    private String[] phoneNum = {"010-0000-0001", "010-0000-0002", "010-0000-0003" };
    private String[] topic = {"Sajouiot03", "Sajouiot02", "Sajouiot01"};
    private int[] imageId = {R.drawable.tempfa, R.drawable.tempmom, R.drawable.tempstepmom};


    private int viewId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parent);

        pager = (ViewPager) findViewById(R.id.pager);

        SelectParentActivity.MyAdapter adapter = new SelectParentActivity.MyAdapter();
        pager.setAdapter(adapter);

        //imageView = (ImageView) findViewById(R.id.imageView);

        //imageView.setImageResource(imageId[0]);

        btn_call = (Button) findViewById(R.id.btn_call);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_chat_setting = (Button) findViewById(R.id.btn_chat_setting);

        buttonListener(btn_call, btn_chat, btn_chat_setting, 0);

        // SeekBar setting
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


        // Pager setting
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                seekBar.setProgress(position);

                //imageView.setImageResource(imageId[position]);
                buttonListener(btn_call, btn_chat, btn_chat_setting, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    // Pager Adapter
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return parent.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.d(TAG, "MyAdapter : isViewFromObject");
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, "MyAdapter : destroyItem");
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d(TAG, "MyAdapter : instantiateItem");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });

            thread.start();


            LinearLayout layout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            layout.setBackgroundColor(Color.CYAN);

            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(params);

            TextView textview = new TextView(getApplicationContext());
            textview.setText(parent[position]);
            textview.setTextSize(40.0f);
            layout.addView(textview);

            RoundedImageView circularImageView = new RoundedImageView(getApplicationContext());
            circularImageView.setImageResource(imageId[position]);
            //circularImageView.setScaleType(ImageView.ScaleType.CENTER);

            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) layout.getLayoutParams();

            circularImageView.setLayoutParams(params1);



//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            params.weight = 1.0f;
//            params.gravity = Gravity.CENTER_HORIZONTAL;
//
//
//            circularImageView.setLayoutParams(params);



//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId[position]);
//            bitmap = circularImageView.getCroppedBitmap(bitmap, 300);
//            circularImageView.setImageBitmap(bitmap);
            layout.addView(circularImageView);

            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //linearLayout.setLayoutParams(params1);

            Button b1 = new Button(getApplicationContext());
            b1.setText("Button1");
            Button b2 = new Button(getApplicationContext());
            b2.setText("Button2");
            Button b3 = new Button(getApplicationContext());
            b3.setText("Button3");

            linearLayout.addView(b1);
            linearLayout.addView(b2);
            linearLayout.addView(b3);

            layout.addView(linearLayout);

            //ImageView imageView = new ImageView(getApplicationContext());
            //imageView.setImageResource(imageId[position]);


            //layout.addView(textview);
            //layout.addView(imageView);

            container.addView(layout);
            container.setBackgroundColor(Color.RED);

            return layout;
        }
    }

    public void buttonListener(Button b1, Button b2, Button b3, final int idx){
        // Call button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show dial
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phoneNum[idx]));

                //Direct call
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum[idx]));
                startActivity(intent);
            }
        });

        // Chatting button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), topic[position], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ChattingActivity.class);
                intent.putExtra("topic", topic[idx]);
                startActivity(intent);
            }
        });

        // Message Setting button
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}

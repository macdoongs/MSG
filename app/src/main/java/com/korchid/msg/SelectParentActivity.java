package com.korchid.msg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.Space;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import static com.korchid.msg.R.id.nav_header_container;

public class SelectParentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "SelectParentActivity";


    private ViewPager pager;
    private SeekBar seekBar;
    private Button btn_call, btn_chat, btn_chat_setting;
    private ImageView iv_profile;
    private TextView tv_myName;
    private TextView tv_myPhoneNumber;


    // Temp Data Array
    private String[] parent = {"Father", "Mother", "StepMother"};
    private String[] phoneNum = {"010-0000-0001", "010-0000-0002", "010-0000-0003" };
    private String[] topic = {"Sajouiot03", "Sajouiot02", "Sajouiot01"};
    private String[] message = {"아빠 뭐해?", "엄마 뭐해?", "엄마 뭐해요?"};
    private int[] imageId = {R.drawable.tempfa, R.drawable.tempmom, R.drawable.tempstepmom};


    private int viewId;
    private int profileWidth;
    private int profileHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);

/*
        getLayoutInflater().inflate(R.layout.nav_header_main, null);

        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_myName = (TextView) findViewById(R.id.tv_name);
        tv_myPhoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);

        tv_myName.setText(GlobalApplication.getGlobalApplicationContext().getUserId());
        tv_myPhoneNumber.setText(GlobalApplication.getGlobalApplicationContext().getUserId());


        try {
            Uri uri = GlobalApplication.getGlobalApplicationContext().getProfileImage();

            Log.d(TAG, "Uri : " + uri);
            if(uri != null){
                iv_profile.setImageURI(GlobalApplication.getGlobalApplicationContext().getProfileImage());
            }

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StatusBar statusBar = new StatusBar(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        pager = (ViewPager) findViewById(R.id.pager);

        SelectParentActivity.MyAdapter adapter = new SelectParentActivity.MyAdapter();
        pager.setAdapter(adapter);

        //imageView = (ImageView) findViewById(R.id.imageView);
        //imageView.setImageResource(imageId[0]);

        /*
        // static button
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_chat_setting = (Button) findViewById(R.id.btn_chat_setting);
        buttonListener(btn_call, btn_chat, btn_chat_setting, 0);
        */

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(parent[0]);

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

                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(parent[position]);

                //imageView.setImageResource(imageId[position]);
                //buttonListener(btn_call, btn_chat, btn_chat_setting, position);
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


            LinearLayout layout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,20,20,20);
            params.gravity = Gravity.CENTER;
            /*
            // layout test
            if(position == 0){
                layout.setBackgroundColor(Color.CYAN);
            }else if(position == 1){
                layout.setBackgroundColor(Color.RED);
            }else{
                layout.setBackgroundColor(Color.BLACK);
            }
            */
            layout.setGravity(Gravity.CENTER);

            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(params);


            TextView namespace = new TextView(getApplicationContext());
            namespace.setText(parent[position]);
            namespace.setTextSize(40.0f);
            //namespace.setGravity(Gravity.CENTER);
            layout.addView(namespace);



            RoundedImageView circularImageView = new RoundedImageView(getApplicationContext());

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId[position]);

            profileWidth = 600;
            profileHeight = 600;

            bitmap = resizeBitmap(bitmap, profileWidth, profileHeight);
            //bitmap = cropBitmap(bitmap, 300, 300);

            circularImageView.setImageBitmap(bitmap);


            circularImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            circularImageView.setCornerRadius((float)20);
            circularImageView.setBorderWidth((float)2);
            circularImageView.setBorderColor(Color.DKGRAY);
            circularImageView.mutateBackground(true);

            //circularImageView.setScaleType(ImageView.ScaleType.CENTER);

            //LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) layout.getLayoutParams();

            //circularImageView.setLayoutParams(params1);



//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            params.weight = 1.0f;
//            params.gravity = Gravity.CENTER_HORIZONTAL;
//
//
//            circularImageView.setLayoutParams(params);

            layout.addView(circularImageView);

            LinearLayout ll_messageBoxLayout = new LinearLayout(getApplicationContext());
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,20,20,20);
            params.gravity = Gravity.CENTER;
            ll_messageBoxLayout.setBackgroundResource(R.color.colorPrimary);
            ll_messageBoxLayout.setLayoutParams(params);
            ll_messageBoxLayout.setOrientation(LinearLayout.VERTICAL);

            // Round edge - message box
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(getResources().getColor(R.color.colorPrimary));
            gd.setCornerRadius(10);
            gd.setStroke(2, Color.WHITE);

            ll_messageBoxLayout.setBackground(gd);


            float textSize = 20.0f;
            int textColor = Color.BLACK;
            LinearLayout ll_timeLayout = new LinearLayout(getApplicationContext());

            params.setMargins(20,20,20,20);
            params.gravity = Gravity.CENTER;
            ll_timeLayout.setLayoutParams(params);
            ll_timeLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv_timeTitle = new TextView(getApplicationContext());
            tv_timeTitle.setText("발송예정");
            tv_timeTitle.setTextSize(textSize);
            tv_timeTitle.setTextColor(textColor);
            ll_timeLayout.addView(tv_timeTitle);


            TextView tv_timeReserved = new TextView(getApplicationContext());
            tv_timeReserved.setText("수요일 오후 9시경");
            tv_timeReserved.setTextSize(textSize);
            tv_timeReserved.setTextColor(textColor);
            tv_timeReserved.setGravity(Gravity.RIGHT);
            ll_timeLayout.addView(tv_timeReserved);


            LinearLayout ll_messageLayout = new LinearLayout(getApplicationContext());
            params.setMargins(20,20,20,20);
            params.gravity = Gravity.CENTER;
            ll_messageLayout.setLayoutParams(params);
            ll_messageLayout.setBackgroundResource(R.color.colorWhite);
            ll_messageLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv_messageReserved = new TextView(getApplicationContext());
            tv_messageReserved.setText(message[position]);
            tv_messageReserved.setTextSize(25.0f);
            tv_messageReserved.setTextColor(Color.BLACK);
            ll_messageLayout.addView(tv_messageReserved);

            ll_messageBoxLayout.addView(ll_timeLayout);
            ll_messageBoxLayout.addView(ll_messageLayout);

            layout.addView(ll_messageBoxLayout);


            // Button Line
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            //LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //linearLayout.setLayoutParams(params1);

            btn_call = new Button(getApplicationContext());
            btn_call.setBackgroundResource(R.drawable.call);
            btn_call.setPadding(0, 20, 20, 20);
            btn_chat = new Button(getApplicationContext());
            btn_chat.setBackgroundResource(R.drawable.message);
            btn_chat.setPadding(50, 20, 50, 20);
            btn_chat_setting = new Button(getApplicationContext());
            btn_chat_setting.setBackgroundResource(R.drawable.messagesetting);
            btn_chat_setting.setPadding(20, 20, 0, 20);

            buttonListener(btn_call, btn_chat, btn_chat_setting, position);


            linearLayout.addView(btn_call);
            linearLayout.addView(btn_chat);
            linearLayout.addView(btn_chat_setting);

            layout.addView(linearLayout);

            container.addView(layout);
            //container.setBackgroundColor(Color.RED);

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
                intent.putExtra("parentName", parent[idx]);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_messageSetting:{
                Intent intent = new Intent(getApplicationContext(), MessageSettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_gallery:{

                break;
            }
            case R.id.nav_setting:{
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_callCenter:{

                break;
            }
            case R.id.nav_contract:{

                break;
            }
            default:{
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    /**     http://bbulog.tistory.com/25
     *
     * 이미지를 주어진 너비와 높이에 맞게 리사이즈 하는 코드.
     * 원본 이미지를 크롭 하는게 아니라 리사이즈 하는 것이어서,
     * 주어진 너비:높이 의 비율이 원본 bitmap 의 비율과 다르다면 변환 후의 너비:높이의 비율도 주어진 비율과는 다를 수 있다.
     *
     * 가로가 넓거나 세로가 긴 이미지를 정사각형이나 원형의 view 에 맞추려 할 때,
     * 이 메쏘드를 호출한 후 반환된 bitmap 을 crop 하면 찌그러지지 않는 이미지를 얻을 수 있다.
     *
     * @param Bitmap bitmap 원본 비트맵
     * @param int width 뷰의 가로 길이
     * @param int height 뷰의 세로 길이
     *
     * @return Bitmap 리사이즈 된 bitmap

    public Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap.getWidth() != width || bitmap.getHeight() != height){
            float ratio = 1.0f;

            if (width > height) {
                ratio = (float)width / (float)bitmap.getWidth();
            } else {
                ratio = (float)height / (float)bitmap.getHeight();
            }

            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (int)(((float)bitmap.getWidth()) * ratio), // Width
                    (int)(((float)bitmap.getHeight()) * ratio), // Height
                    true);
        }

        return bitmap;
    }
    */
    public Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap.getWidth() != width || bitmap.getHeight() != height){
            float widthRatio = 1.0f;
            float heightRatio = 1.0f;

            widthRatio = (float)width / (float)bitmap.getWidth();

            heightRatio = (float)height / (float)bitmap.getHeight();


            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (int)(((float)bitmap.getWidth()) * widthRatio), // Width
                    (int)(((float)bitmap.getHeight()) * heightRatio), // Height
                    true);
        }

        return bitmap;
    }

    /**
     *
     * 이미지를 주어진 사이즈에 맞추어 crop 하는 코드
     * 원본의 가운데를 중심으로 crop 한다.
     *
     *
     * @param Bitmap bitmap 원본 비트맵
     * @param int width 뷰의 가로 길이
     * @param int height 뷰의 세로 길이
     *
     * @return Bitmap crop 된 bitmap
     */
    public Bitmap cropBitmap(Bitmap bitmap, int width, int height) {
        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // 이미지를 crop 할 좌상단 좌표
        int x = 0;
        int y = 0;

        if (originWidth > width) { // 이미지의 가로가 view 의 가로보다 크면..
            x = (originWidth - width)/2;
        }

        if (originHeight > height) { // 이미지의 세로가 view 의 세로보다 크면..
            y = (originHeight - height)/2;
        }

        Bitmap cropedBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
        return cropedBitmap;
    }

}

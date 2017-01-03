package com.korchid.msg.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.korchid.msg.R;
import com.korchid.msg.http.HttpGet;
import com.korchid.msg.ui.StatusBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import static com.korchid.msg.global.QuickstartPreferences.USER_PHONE_NUMBER;
import static com.korchid.msg.global.QuickstartPreferences.USER_ROLE;

public class SelectOpponentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "SelectOpponentActivity";
    private final int COUNT = 10;

    private SeekBar seekBar;
    private Button btn_call, btn_chat, btn_chat_setting;

    private int mPrevPosition;
    private LinearLayout mPageMark;

    // Temp Data Array
    private ArrayList<String> parentArrayList;

    private String[] parent = {"Father", "Mother", "StepMother"};
    private String[] phoneNum = {"010-0000-0001", "010-0000-0002", "010-0000-0003" };
    private String[] topic = {"Sajouiot03", "Sajouiot02", "Sajouiot01"};
    private String[] timeReserved = {"수요일 9시경", "목요일 5시경", "금요일 8시경"};
    private String[] message = {"아빠 뭐해?", "엄마 뭐해?", "엄마 뭐해요?"};
    private int[] imageId = {R.drawable.tempfa, R.drawable.tempmom, R.drawable.tempstepmom};


    private int viewId;
    private int profileWidth;
    private int profileHeight;
    private String userRole = "";
    private String userPhoneNumber = "";



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_opponent);

        // Remove previous activity
        MainActivity mainActivity = (MainActivity) MainActivity.activity;
        mainActivity.finish();

        userRole = getIntent().getStringExtra(USER_ROLE);
        userPhoneNumber = getIntent().getStringExtra(USER_PHONE_NUMBER);
        parentArrayList = new ArrayList<>();


        initView();


        String stringUrl = "https://www.korchid.com/msg-mapping/" + userPhoneNumber;

        Handler httpHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "handleMessage");

                String response = msg.getData().getString("response");

                String[] line = response.split("\n");

                //Toast.makeText(getApplicationContext(), "response : " + response, Toast.LENGTH_LONG).show();

                if (line[0].equals("Error")) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();



                } else {
                    String topic = line[0];



                    for(int i=0; i<line.length; i++){
                        String[] dataArray = line[i].split("/");

                        String[] content = dataArray[2].split(":");

                        parentArrayList.add(content[1]);
                        Log.d(TAG, "dataArray : " + dataArray[i]);
                    }

                    Toast.makeText(getApplicationContext(), "Topic : " + topic, Toast.LENGTH_LONG).show();

                }
            }
        };


        HttpGet httpGet = new HttpGet(stringUrl, httpHandler);
        httpGet.start();




    }

    private void initView(){
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

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//
//        btn_call = (Button) findViewById(R.id.btn_call);
//        btn_chat = (Button) findViewById(R.id.btn_chat);
//        btn_chat_setting = (Button) findViewById(R.id.btn_chat_setting);
//        buttonListener(btn_call, btn_chat, btn_chat_setting, 0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(parent[0]);

        // SeekBar setting
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(mViewPager.getAdapter().getCount() - 1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mViewPager.setCurrentItem(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Pager setting
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                seekBar.setProgress(position);

                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(parent[position]);

                // http://www.hardcopyworld.com/ngine/android/index.php/archives/164
                //이전 페이지에 해당하는 페이지 표시 이미지 변경
                mPageMark.getChildAt(mPrevPosition).setBackgroundResource(R.drawable.page_not);

                //현재 페이지에 해당하는 페이지 표시 이미지 변경
                mPageMark.getChildAt(position).setBackgroundResource(R.drawable.page_select);
                mPrevPosition = position;                //이전 포지션 값을 현재로 변경

                //imageView.setImageResource(imageId[position]);
                //buttonListener(btn_call, btn_chat, btn_chat_setting, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPageMark = (LinearLayout)findViewById(R.id.page_mark);

        initPageMark();
    }

    private void initPageMark(){
        for(int i=0; i < COUNT; i++)
        {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            if(i==0){
                iv.setBackgroundResource(R.drawable.page_select);
            }else{
                iv.setBackgroundResource(R.drawable.page_not);
            }

            mPageMark.addView(iv);
        }
        mPrevPosition = 0;
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
                intent.putExtra("topic", parentArrayList.get(idx));
                intent.putExtra("parentName", parent[idx]);
                startActivity(intent);
            }
        });

        // Message Setting button
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_opponent, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_messageSetting:{
                Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_select_opponent, container, false);




            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return parent.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return parent[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*
            TextView tv_timeTitle = (TextView) findViewById(R.id.tv_timeTitle);
            tv_timeTitle.setText("발송 예정");

            TextView tv_timeReserved = (TextView) findViewById(R.id.tv_timeReserved);
            tv_timeReserved.setText(timeReserved[position]);



            TextView tv_messageReserved = (TextView) findViewById(R.id.tv_messageReserved);
            tv_messageReserved.setText(message[position]);

            RoundedImageView circularImageView = (RoundedImageView) findViewById(R.id.riv_opponentProfile);

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

            if(userRole.equals("child")){
                btn_chat_setting.setVisibility(View.GONE);
            }else{
                btn_chat_setting.setVisibility(View.VISIBLE);
            }
*/

            return super.instantiateItem(container, position);
        }
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

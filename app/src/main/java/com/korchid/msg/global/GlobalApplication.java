package com.korchid.msg.global;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.korchid.msg.sqlite.DBHelper;

/**
 * Created by mac0314 on 2016-12-11.
 */


// 이미지를 캐시를 앱 수준에서 관리하기 위한 애플리케이션 객체이다.
// 로그인 기반 샘플앱에서 사용한다.
// kakao-open-android-sdk-sample
public class GlobalApplication extends Application {
    private static final String TAG = "GlobalApplication";
    private static volatile GlobalApplication instance = null;
    private ImageLoader imageLoader;

    private String userId;
    private String userPassword;
    private int weekNum;
    private int times;
    private Uri profileImage;
    private String[] parentId;

    /**
     * see NotePad tutorial for an example implementation of DataDbAdapter
     */
    public static DBHelper dbHelper;


    /**
     * 이미지 로더, 이미지 캐시, 요청 큐를 초기화한다.
     */

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        instance = this;

        //KakaoSDK.init(new KakaoSDKAdapter());

        dbHelper = new DBHelper(getApplicationContext(), "MSG.db", null, 1);
        dbHelper.getWritableDatabase();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(30);

            @Override
            public void putBitmap(String key, Bitmap value) {
                imageCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return imageCache.get(key);
            }
        };

        imageLoader = new ImageLoader(requestQueue, imageCache);
    }

    /**
     * singleton 애플리케이션 객체를 얻는다.
     * @return singleton 애플리케이션 객체
     */
    public static GlobalApplication getGlobalApplicationContext() {
        Log.d(TAG, "getGlobalApplicationContext");

        if(instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    public DBHelper getDBHelper() {
        return dbHelper;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String[] getParentId() {
        return parentId;
    }

    public void setParentId(String[] parentId) {
        this.parentId = parentId;
    }

    public Uri getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * 이미지 로더를 반환한다.
     * @return 이미지 로더
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * 애플리케이션 종료시 singleton 어플리케이션 객체 초기화한다.
     */
    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate");
        dbHelper.close();
        super.onTerminate();
        instance = null;
    }

}

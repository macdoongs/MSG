package com.korchid.msg;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChattingSubActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_userPhoto;
    private int id_view;
    private String absolutePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_sub);



    }

    // Take pictures function
    // http://jeongchul.tistory.com/287
    public void takePictureAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Temp path
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";


    }
}
